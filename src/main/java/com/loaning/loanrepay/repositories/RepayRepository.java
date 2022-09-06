package com.loaning.loanrepay.repositories;

import com.loaning.loanrepay.entitites.Repay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepayRepository extends JpaRepository<Repay,Long> {
    Page<Repay> findBySubscriberId(UUID subscriberId, Pageable pageable);
}
