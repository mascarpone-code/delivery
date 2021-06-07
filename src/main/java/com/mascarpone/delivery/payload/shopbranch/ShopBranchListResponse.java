package com.mascarpone.delivery.payload.shopbranch;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopBranchListResponse {
    private long totalBranchesCount;
    private List<ShopBranchResponse> shopBranches;

    public ShopBranchListResponse(List<ShopBranchResponse> shopBranches, long count) {
        this.totalBranchesCount = count;
        this.shopBranches = shopBranches;
    }
}
