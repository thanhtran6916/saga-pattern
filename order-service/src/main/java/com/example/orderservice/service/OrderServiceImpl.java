package com.example.orderservice.service;

import com.example.orderservice.dto.AccountDTO;
import com.example.orderservice.dto.LoansProfileDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final AccountService accountService;
    private final KafkaTemplate<String, String> template;

    @Value("${cloudkarafka.topic.payment.request}")
    private String paymentTopic;

    @Override
    public void createOrderDTO(OrderDTO orderDTO) {
        LoansProfileDTO loansProfileDTO = orderDTO.getLoansProfileDTO();
        String loansProfileString = Constant.gson.toJson(loansProfileDTO);
        log.info("LoansProfile ===========> " + loansProfileString);
        var callBack = template.send(paymentTopic, loansProfileString);
        callBack.addCallback(result -> {
            log.info("Success sent topic " + paymentTopic + " " + loansProfileString);

        }, throwable -> {
            log.info("Error sent topic " + paymentTopic + " " + loansProfileString);
        });
        accountService.insertAccount(orderDTO.getAccountDTO());
    }

    @KafkaListener(topics = "${cloudkarafka.topic.payment.response}")
    public void listenerTopic(String message) {
        log.info(message);
    }

}
