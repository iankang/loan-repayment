package com.loaning.loanrepay.repositories;

import com.loaning.loanrepay.entitites.Repay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepayRepository extends JpaRepository<Repay,Long> {
}
