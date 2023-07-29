package com.kullop.inventory.controllers;

import com.kullop.inventory.responses.InventoryStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/inventories")
public class InventoryController {

    private final Map<String, InventoryStatus> statusMap = Map.of("1", new InventoryStatus(true), "2", new InventoryStatus(false));

    @GetMapping
    public InventoryStatus getInventoryDetails(@RequestParam("productId") String productId) {
        return this.statusMap.getOrDefault(productId, new InventoryStatus(false));
    }
}
