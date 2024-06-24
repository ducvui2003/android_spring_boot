package com.commic.v1.services.attendance;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.ExchangeResponse;
import com.commic.v1.dto.responses.RedeemRewardResponse;
import com.commic.v1.entities.Item;
import com.commic.v1.entities.RedeemReward;
import com.commic.v1.entities.User;
import com.commic.v1.exception.AppException;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.repositories.IItemRepository;
import com.commic.v1.repositories.IRedeemRewardRepository;
import com.commic.v1.repositories.IRewardPointRepository;
import com.commic.v1.repositories.IUserRepository;
import com.commic.v1.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServices implements IItemServices {
    @Autowired
    IItemRepository itemRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRedeemRewardRepository redeemRewardRepository;
    @Autowired
    IRewardPointRepository rewardPointRepository;

    @Override
    public DataListResponse<Item> getItems(Pageable pageable) {
        DataListResponse<Item> response = new DataListResponse();
        Page<Item> page = itemRepository.findAll(pageable);
        response.setData(page.getContent());
        response.setCurrentPage(pageable.getPageNumber() + 1);
        response.setTotalPages(page.getTotalPages());
        return response;
    }

    @Override
    public ExchangeResponse exchangeItem(Integer itemId) {
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        redeemRewardRepository.existsByUserIdAndItemId(user.getId(), itemId);
        ExchangeResponse response = new ExchangeResponse();
        if (redeemRewardRepository.existsByUserIdAndItemId(user.getId(), itemId)) {
            response.setStatus(ExchangeStatus.EXIST_IN_REPO);
            return response;
        }
        //     Check point enough
        Double totalScore = rewardPointRepository.sumPointByUserId(user.getId()).orElse(null);
        Double totalScoreExchange = redeemRewardRepository.sumPointByUserId(user.getId()).orElse(0.0);
        if (totalScore == null) {
            response.setStatus(ExchangeStatus.EXCHANGE_FAIL);
            return response;
        }
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Double score = totalScore - totalScoreExchange;
        if (score < item.getPoint()) {
            response.setStatus(ExchangeStatus.POINT_NOT_ENOUGH);
            return response;
        }
        RedeemReward redeemReward = RedeemReward.builder()
                .date(new Date(System.currentTimeMillis()))
                .item(item)
                .user(user)
                .build();
        redeemRewardRepository.save(redeemReward);
        response.setStatus(ExchangeStatus.EXCHANGE_SUCCESS);
        response.setTotalScore(score - item.getPoint());
        return response;
    }

    @Override
    public DataListResponse<RedeemRewardResponse> getItemsByUser(Pageable pageable) {
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        DataListResponse<RedeemRewardResponse> response = new DataListResponse();
        Page<Item> page = itemRepository.findAll(pageable);
        if (page.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        List<RedeemRewardResponse> data = new ArrayList<>();
        for (Item item : page.getContent()) {
            boolean isExchange = redeemRewardRepository.existsByUserIdAndItemId(user.getId(), item.getId());
            data.add(RedeemRewardResponse.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .point(item.getPoint())
                    .image(item.getImage())
                    .date(redeemRewardRepository.findDateByUserIdAndItemId(user.getId(), item.getId()))
                    .isExchange(isExchange)
                    .build());
        }
        response.setData(data);
        response.setCurrentPage(pageable.getPageNumber() + 1);
        response.setTotalPages(page.getTotalPages());
        return response;
    }

    @Override
    public boolean useItem(Integer itemId) {
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        boolean exist = redeemRewardRepository.existsByUserIdAndItemId(user.getId(), itemId);
        if (!exist) {
            return false;
        }
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        user.setAvatar(item.getImage());
        userRepository.save(user);
        return true;
    }
}
