package com.kullop.shipping.repositories;


import com.kullop.shipping.entities.Shipping;
import org.springframework.data.repository.CrudRepository;

public interface ShippingRepository extends CrudRepository<Shipping, Long> {
}
