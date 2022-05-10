package com.example.orderservice.service;

import com.example.orderservice.dto.LoansProfileDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, String> template;

    @Value("${cloudkarafka.topic.payment}")
    private String paymentTopic;

    @Override
    public OrderDTO createOrderDTO(OrderDTO orderDTO) {
        LoansProfileDTO loansProfileDTO = orderDTO.getLoansProfileDTO();
        String loansProfileString = Constant.gson.toJson(loansProfileDTO);
        log.info("LoansProfile ===========> " + loansProfileString);
        var callBack = template.send(paymentTopic, loansProfileString);
        callBack.addCallback(result -> {
            log.info("Success sent topic " + paymentTopic + " " + loansProfileString);
        }, throwable -> {
            log.info("Error sent topic " + paymentTopic + " " + loansProfileString);
        });
        return null;
    }

    @KafkaListener(topics = "${cloudkarafka.topic.payment}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        String result = topics.get(0) + " " + partitions.get(0) + " " + offsets.get(0) + " " + message;
        System.out.printf(result);
    }
}
