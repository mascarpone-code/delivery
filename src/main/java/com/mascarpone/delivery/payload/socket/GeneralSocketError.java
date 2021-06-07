package com.mascarpone.delivery.payload.socket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mascarpone.delivery.entity.enums.AccountActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralSocketError {
    @JsonProperty(value = "action")
    private AccountActionType accountActionType;

    @JsonProperty(value = "error")
    private String error;
}
