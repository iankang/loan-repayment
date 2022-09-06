package com.loaning.loanrepay.repositories;

import com.loaning.loanrepay.entitites.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<Subscriber> findByPhoneNumber(String phoneNumber);
}
