package com.mascarpone.delivery.payload.socket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mascarpone.delivery.entity.enums.AccountActionType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralSocketResponse<T> {
    @JsonProperty(value = "action")
    private AccountActionType accountActionType;

    @JsonProperty(value = "result")
    private T result;
}
