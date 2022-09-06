package com.loaning.loanrepay.services;

import com.loaning.loanrepay.entitites.Subscriber;
import com.loaning.loanrepay.models.requests.SubscriberRequest;
import com.loaning.loanrepay.models.responses.PagedResponse;
import com.loaning.loanrepay.repositories.SubscriberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriberService {

    private final Logger logger = LoggerFactory.getLogger(SubscriberService.class);
    private SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * adds a subscriber to the database
     * @param subscriberRequest
     * @return
     */
    public ResponseEntity<?> addSubscriber(SubscriberRequest subscriberRequest){
        try{
            //check if subscriber phone number is added.
            if (subscriberRepository.existsByPhoneNumber(subscriberRequest.getPhoneNumber())){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(subscriberRepository.save(new Subscriber(subscriberRequest.getPhoneNumber(),subscriberRequest.getUsername())),HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }

    /**
     * fetches all subscribers
     * @param page page you want to load
     * @param size items to load in each page
     * @return PagedResponse
     */
    public ResponseEntity<PagedResponse<Subscriber>> getAllSubscribers(
            int page,
            int size
            ){
        try {
            List<Subscriber> subscriberList = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<Subscriber> pageSubscriber = subscriberRepository.findAll(paging);
            subscriberList = pageSubscriber.getContent();

            PagedResponse<Subscriber> pagedResponse = new PagedResponse(subscriberList,0,pageSubscriber.getTotalElements(),pageSubscriber.getTotalPages());

            return  new ResponseEntity<>(pagedResponse,HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * gets a single subscriber
     * @param phoneNumber
     * @return
     */
    public ResponseEntity<Subscriber> getSingleSubscriberByPhoneNumber(
            String phoneNumber
            ){
        try{
            if(subscriberRepository.existsByPhoneNumber(phoneNumber)){
                return new ResponseEntity<>(subscriberRepository.findByPhoneNumber(phoneNumber).get(),HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * deletes a subscriber
     * @param phoneNumber
     * @return
     */
    public ResponseEntity<HttpStatus> deleteSubscriber(String phoneNumber){
        try{
            if(subscriberRepository.existsByPhoneNumber(phoneNumber)){

                Subscriber subscriber = subscriberRepository.findByPhoneNumber(phoneNumber).get();
                subscriberRepository.delete(subscriber);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
