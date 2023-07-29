package com.kullop.order.clients;

import com.kullop.order.core.InventoryStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8092/api/v1/inventories", name = "inventories")
public interface InventoryClient {
    @GetMapping
    InventoryStatus exists(@RequestParam String productId);
}
