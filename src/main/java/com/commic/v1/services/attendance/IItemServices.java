package com.commic.v1.services.attendance;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.ExchangeResponse;
import com.commic.v1.dto.responses.RedeemRewardResponse;
import com.commic.v1.entities.Item;
import org.springframework.data.domain.Pageable;

public interface IItemServices {
    DataListResponse<Item> getItems(Pageable pageable);

    DataListResponse<RedeemRewardResponse> getItemsByUser(Pageable pageable);

    ExchangeResponse exchangeItem(Integer itemId);

    boolean useItem(Integer itemId);
}
