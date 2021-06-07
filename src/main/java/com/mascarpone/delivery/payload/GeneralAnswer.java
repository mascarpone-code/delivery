package com.mascarpone.delivery.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralAnswer<T> {
    private String status;
    private String error;
    private T object;
}
