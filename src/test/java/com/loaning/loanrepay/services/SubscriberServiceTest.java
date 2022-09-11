package com.loaning.loanrepay.services;

import com.loaning.loanrepay.entitites.Subscriber;
import com.loaning.loanrepay.models.requests.SubscriberRequest;
import com.loaning.loanrepay.repositories.SubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    SubscriberRepository subscriberRepository;

    @InjectMocks
    SubscriberService subscriberService;

    Subscriber subscriber;

    @BeforeEach
    public void setup(){
        subscriber = new Subscriber("254721450267","someone");
    }

    @Test()
    public void addSubscriberwhen_save_subscriber_it_should_return_subscriber() throws NullPointerException{

        SubscriberRequest subscriberRequest = new SubscriberRequest();
        subscriberRequest.setUsername("username");
        subscriberRequest.setPhoneNumber("254700895538");

        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(new Subscriber());

        Subscriber createdSub = subscriberService.addSubscriber(subscriberRequest);
        assertThat(createdSub.getUserName()).isSameAs(subscriberRequest.getUsername());
    }

    @DisplayName("JUnit test for fetching by phone number")
    @Test
    public void givenSubscriberPhoneNumber_whenGetSubscriberByPhoneNumber_thenReturnSubscriber(){

        given(subscriberRepository.findByPhoneNumber(subscriber.getPhoneNumber())).willReturn(Optional.of(subscriber));

        Subscriber sub = subscriberService.getSubscriberOptionalByPhoneNumber(subscriber.getPhoneNumber()).get();

        assertThat(sub).isNotNull();
    }
}