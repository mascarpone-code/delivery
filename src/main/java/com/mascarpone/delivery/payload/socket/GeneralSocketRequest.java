package com.mascarpone.delivery.payload.socket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mascarpone.delivery.entity.enums.AccountActionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralSocketRequest<T> {
    @JsonProperty(value = "action")
    private AccountActionType accountActionType;

    @JsonProperty(value = "object")
    private T object;
}
