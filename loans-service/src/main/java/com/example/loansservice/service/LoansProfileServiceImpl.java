package com.example.loansservice.service;

import com.example.loansservice.dto.LoansProfileDTO;
import com.example.loansservice.entity.LoansProfile;
import com.example.loansservice.mapper.LoansProfileMapper;
import com.example.loansservice.repository.LoansProfileRepository;
import com.example.loansservice.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoansProfileServiceImpl implements LoansProfileService{

    private final LoansProfileRepository loansProfileRepository;
    private final KafkaTemplate<String, String> template;
    private final LoansProfileMapper loansProfileMapper;

    @Value("${cloudkarafka.topic.payment.response}")
    private String topicResponse;

    @Override
    public LoansProfileDTO findByAccountId(Integer accountId) {
        LoansProfile loansProfile = loansProfileRepository.findLoansProfileByAccountId(accountId);
        if (ObjectUtils.isEmpty(loansProfile)) return null;

        return loansProfileMapper.toLoansProfileDTO(loansProfile);
    }

    @Override
    public Integer updateLoansProfile(LoansProfileDTO loansProfileDTO) {
        LoansProfile loansProfile = loansProfileRepository.findLoansProfileByAccountId(loansProfileDTO.getAccountId());
        if (ObjectUtils.isEmpty(loansProfile)) return null;

        loansProfileDTO.setCreatedDate(loansProfile.getCreatedDate());
        loansProfileDTO.setId(loansProfile.getId());
        loansProfile = loansProfileRepository.save(loansProfile);
        if (ObjectUtils.isEmpty(loansProfile)) return null;
        return loansProfile.getId();
    }

    @Override
    public Integer insertLoansProfile(LoansProfileDTO loansProfileDTO) {
        LoansProfile loansProfile = loansProfileRepository.save(loansProfileMapper.toLoansProfile(loansProfileDTO));
        if (ObjectUtils.isEmpty(loansProfile)) return null;
        return loansProfile.getId();
    }

    @KafkaListener(topics = "${cloudkarafka.topic.payment.request}")
    public void insertLoansProfileKafka(String message,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                                           @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                                           @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LoansProfileDTO loansProfileDTO = Constant.gson.fromJson(message, LoansProfileDTO.class);
        LoansProfile loansProfile = loansProfileRepository.save(loansProfileMapper.toLoansProfile(loansProfileDTO));
        var messageCallback = template.send(topicResponse, Constant.gson.toJson(loansProfile));
        messageCallback.addCallback(result -> {
            template.send(topicResponse, Constant.gson.toJson(loansProfile));
            log.info("success");
        }, throwable -> {
            log.info("error");
        });
    }
}
