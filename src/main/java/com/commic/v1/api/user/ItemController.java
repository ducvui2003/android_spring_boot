package com.commic.v1.api.user;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.requests.ExchangeRequest;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.RedeemRewardResponse;
import com.commic.v1.entities.Item;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.attendance.ExchangeStatus;
import com.commic.v1.services.attendance.ItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired
    ItemServices itemServices;

    @GetMapping()
    public APIResponse<DataListResponse<Item>> getItems(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        APIResponse<DataListResponse<Item>> apiResponse = new APIResponse<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        DataListResponse<Item> items = itemServices.getItems(pageable);
        if (items.getData().isEmpty()) {
            apiResponse.setCode(ErrorCode.NOT_FOUND.getCode());
            apiResponse.setMessage(ErrorCode.NOT_FOUND.getMessage());
        } else {
            apiResponse.setCode(ErrorCode.FOUND.getCode());
            apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        }
        apiResponse.setResult(items);
        return apiResponse;
    }

    @PostMapping()
    public APIResponse<ExchangeStatus> exchangeItem(@RequestBody ExchangeRequest request) {
        APIResponse<ExchangeStatus> apiResponse = new APIResponse<>();
        ExchangeStatus isCreated = itemServices.exchangeItem(request.getItemId());
        apiResponse.setCode(ErrorCode.CREATE_SUCCESS.getCode());
        apiResponse.setMessage(ErrorCode.CREATE_SUCCESS.getMessage());
        apiResponse.setResult(isCreated);
        return apiResponse;
    }

    @GetMapping("/user")
    public APIResponse<DataListResponse<RedeemRewardResponse>> getItemsByUser(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        APIResponse<DataListResponse<RedeemRewardResponse>> apiResponse = new APIResponse<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        DataListResponse<RedeemRewardResponse> items = itemServices.getItemsByUser(pageable);
        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        apiResponse.setResult(items);
        return apiResponse;
    }

    @GetMapping("/user/use/{id}")
    public APIResponse useItem(@PathVariable("id") Integer id) {
        APIResponse apiResponse = new APIResponse<>();
        boolean isUsed = itemServices.useItem(id);
        if (isUsed) {
            apiResponse.setCode(ErrorCode.CREATE_SUCCESS.getCode());
            apiResponse.setMessage(ErrorCode.CREATE_SUCCESS.getMessage());
        } else {
            apiResponse.setCode(ErrorCode.CREATE_FAILED.getCode());
            apiResponse.setMessage(ErrorCode.CREATE_FAILED.getMessage());
        }
        return apiResponse;
    }

}
