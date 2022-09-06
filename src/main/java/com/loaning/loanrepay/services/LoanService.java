package com.loaning.loanrepay.services;

import com.loaning.loanrepay.entitites.Loans;
import com.loaning.loanrepay.entitites.Subscriber;
import com.loaning.loanrepay.models.responses.ErrorResponse;
import com.loaning.loanrepay.models.responses.PagedResponse;
import com.loaning.loanrepay.repositories.LoansRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class LoanService {
    private final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private LoansRepository loansRepository;
    private SubscriberService subscriberService;
    public LoanService(LoansRepository loansRepository,  SubscriberService subscriberService) {
        this.loansRepository = loansRepository;
        this.subscriberService = subscriberService;
    }

    /**
     * gets all loans in paged format
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<PagedResponse<Loans>> getAllLoans(
            int page,
            int size
    ){
        try {
            List<Loans> loansList;
            Pageable pageable = PageRequest.of(page,size);

            Page<Loans> pagedLoans = loansRepository.findAll(pageable);
            loansList = pagedLoans.getContent();

            PagedResponse<Loans> loansPagedResponse = new PagedResponse<>(loansList,page,pagedLoans.getTotalElements(),pagedLoans.getTotalPages());
            return new ResponseEntity<>(loansPagedResponse, HttpStatus.OK);
        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * get loans by specific subscriber
     * @param phoneNumber
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<PagedResponse<Loans>> getLoansBySubscriberId(
            String phoneNumber,
            int page,
            int size
    ){
        try{
            if(subscriberService.subscriberExistsByPhoneNumber(phoneNumber)){

                Subscriber subscriber = subscriberService.getSubscriberByPhoneNumber(phoneNumber);

                List<Loans> loansList;
                Pageable pageable = PageRequest.of(page,size);

                Page<Loans> loansPage = loansRepository.findBySubscriberId(subscriber.getId(),pageable);
                loansList = loansPage.getContent();

                PagedResponse<Loans> loansPagedResponse = new PagedResponse<>(loansList,page,loansPage.getTotalElements(),loansPage.getTotalPages());
                return new ResponseEntity<>(loansPagedResponse, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * requests a loan
     * @param phoneNumber
     * @param borrowAmount
     * @return
     */
    public ResponseEntity<?> requestLoan(
            String phoneNumber,
            BigDecimal borrowAmount
    ){
        try{
            if (subscriberService.subscriberExistsByPhoneNumber(phoneNumber)){
                Subscriber subscriber = subscriberService.getSubscriberByPhoneNumber(phoneNumber);
                if(subscriber != null){
                    if(borrowAmount.compareTo(subscriber.getBorrowableAmount()) < 0){

                        Loans loan = new Loans(subscriber,borrowAmount);
                        BigDecimal balance = subscriber.getBorrowableAmount().subtract(borrowAmount);
                        subscriber.setBorrowableAmount(balance);
                        subscriberService.saveSubscriber(subscriber);
                        return new ResponseEntity<Loans>(loansRepository.save(loan),HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new ErrorResponse("the amount exceeds the allowable limit. You can access Ksh. :  "+ subscriber.getBorrowableAmount()),HttpStatus.BAD_REQUEST);
                    }
                }
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
