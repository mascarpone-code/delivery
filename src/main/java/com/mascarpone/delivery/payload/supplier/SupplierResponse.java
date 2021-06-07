package com.mascarpone.delivery.payload.supplier;

import com.mascarpone.delivery.entity.supplier.Supplier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierResponse {
    private String name;
    private String actualAddress;
    private String legalAddress;
    private String phoneNumber;
    private String email;
    private String ITN;
    private String IEC;
    private String PSRN;

    public SupplierResponse(Supplier supplier) {
        this.name = supplier.getName();
        this.actualAddress = supplier.getActualAddress();
        this.legalAddress = supplier.getLegalAddress();
        this.phoneNumber = supplier.getPhoneNumber();
        this.email = supplier.getEmail();
        this.ITN = supplier.getItn();
        this.IEC = supplier.getIec();
        this.PSRN = supplier.getPsrn();
    }
}
