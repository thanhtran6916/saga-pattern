package com.example.orderservice.api;

import com.example.orderservice.dto.AccountDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.message.BaseResponse;
import com.example.orderservice.service.AccountService;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final AccountService accountService;

    @GetMapping("/get-account/{accountId}")
    public ResponseEntity<BaseResponse> getAccount(@PathVariable Integer accountId) {
        AccountDTO result = accountService.findAccountByAccountId(accountId);
        BaseResponse baseResponse = new BaseResponse();
        if (ObjectUtils.isEmpty(result)) {
            baseResponse.setMessage("Có lỗi xảy ra");
            baseResponse.setErrorCode(Constant.ERROR_CODE);
        }
        baseResponse.setData(result);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/insert-account")
    public ResponseEntity<BaseResponse> insertAccount(@RequestBody AccountDTO accountDTO) {
        Integer result = accountService.insertAccount(accountDTO);
        BaseResponse baseResponse = new BaseResponse();
        if (ObjectUtils.isEmpty(result)) {
            baseResponse.setMessage("Có lỗi xảy ra");
            baseResponse.setErrorCode(Constant.ERROR_CODE);
        }
        baseResponse.setData(result);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/update-account")
    public ResponseEntity<BaseResponse> updateAccount(@RequestBody AccountDTO accountDTO) {
        Integer result = accountService.updateAccount(accountDTO);
        BaseResponse baseResponse = new BaseResponse();
        if (ObjectUtils.isEmpty(result)) {
            baseResponse.setMessage("Có lỗi xảy ra");
            baseResponse.setErrorCode(Constant.ERROR_CODE);
        }
        baseResponse.setData(result);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/create-order")
    public ResponseEntity<BaseResponse> createOrder(@RequestBody OrderDTO orderDTO) {
        orderService.createOrderDTO(orderDTO);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Đã lưu!");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}

