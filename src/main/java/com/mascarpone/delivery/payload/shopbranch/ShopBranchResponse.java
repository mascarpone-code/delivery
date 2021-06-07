package com.mascarpone.delivery.payload.shopbranch;

import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopBranchResponse {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
    private boolean isActive;

    public ShopBranchResponse(ShopBranch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.latitude = branch.getLatitude();
        this.longitude = branch.getLongitude();
        this.address = branch.getAddress();
        this.isActive = branch.isActive();
    }
}
