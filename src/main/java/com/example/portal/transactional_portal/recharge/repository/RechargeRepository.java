package com.example.portal.transactional_portal.recharge.repository;

import com.example.portal.transactional_portal.recharge.entity.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RechargeRepository extends JpaRepository<Recharge, UUID> {

}
