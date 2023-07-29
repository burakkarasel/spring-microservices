package com.kullop.inventory.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryStatus {
    private Boolean exists;
}
