package com.mascarpone.delivery.payload.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifierIdResponse {
    private Long id;

    public ModifierIdResponse(Modifier modifier) {
        this.id = modifier.getId();
    }
}
