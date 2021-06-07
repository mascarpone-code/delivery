package com.mascarpone.delivery.payload.modifier;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModifierListResponse {
    private long totalModifierCount;
    private List<ModifierResponse> modifiers;

    public ModifierListResponse(List<ModifierResponse> modifiers, long count) {
        totalModifierCount = count;
        this.modifiers = modifiers;
    }
}
