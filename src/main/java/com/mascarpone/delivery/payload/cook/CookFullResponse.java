package com.mascarpone.delivery.payload.cook;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CookFullResponse {
    private Long cookId;
    private String login;
    private Long dateCreate;
    private Long shopBranchId;

    public CookFullResponse(User cook) {
        this.cookId = cook.getId();
        this.login = cook.getLogin();
        this.dateCreate = cook.getDateCreate().getTime();
        shopBranchId = cook.getShopBranch().getId();
    }
}
