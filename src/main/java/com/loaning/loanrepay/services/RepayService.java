package com.loaning.loanrepay.services;

import com.loaning.loanrepay.entitites.Repay;
import com.loaning.loanrepay.entitites.Subscriber;
import com.loaning.loanrepay.models.responses.ErrorResponse;
import com.loaning.loanrepay.models.responses.PagedResponse;
import com.loaning.loanrepay.repositories.RepayRepository;
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
public class RepayService {

    private final Logger logger = LoggerFactory.getLogger(RepayService.class);

    private SubscriberService subscriberService;

    private RepayRepository repayRepository;

    public RepayService(SubscriberService subscriberService, RepayRepository repayRepository) {
        this.subscriberService = subscriberService;
        this.repayRepository = repayRepository;
    }

    /**
     * gets all repayments in paged format
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<PagedResponse<Repay>> getAllRepayments(
            int page,
            int size
    ){
        try {
            List<Repay> repayList;
            Pageable pageable = PageRequest.of(page,size);

            Page<Repay> pagedRepayments = repayRepository.findAll(pageable);
            repayList = pagedRepayments.getContent();

            PagedResponse<Repay> repaidResponse = new PagedResponse<>(repayList,page,pagedRepayments.getTotalElements(),pagedRepayments.getTotalPages());
            return new ResponseEntity<>(repaidResponse, HttpStatus.OK);
        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * get repayments by specific subscriber
     * @param phoneNumber
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<PagedResponse<Repay>> getRepaymentsBySubscriberId(
            String phoneNumber,
            int page,
            int size
    ){
        try{
            if(subscriberService.subscriberExistsByPhoneNumber(phoneNumber)){

                Subscriber subscriber = subscriberService.getSubscriberByPhoneNumber(phoneNumber);

                List<Repay> repayList;
                Pageable pageable = PageRequest.of(page,size);

                Page<Repay> loansPage = repayRepository.findBySubscriberId(subscriber.getId(),pageable);
                repayList = loansPage.getContent();

                PagedResponse<Repay> repayPagedResponse = new PagedResponse<>(repayList,page,loansPage.getTotalElements(),loansPage.getTotalPages());
                return new ResponseEntity<>(repayPagedResponse, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * repay a loan
     * @param phoneNumber
     * @param repayAmount
     * @return
     */
    public ResponseEntity<?> repayLoan(
            String phoneNumber,
            BigDecimal repayAmount
    ){
        try{
            if (subscriberService.subscriberExistsByPhoneNumber(phoneNumber)){
                Subscriber subscriber = subscriberService.getSubscriberByPhoneNumber(phoneNumber);
                if(subscriber != null){
                    BigDecimal loanAmount = subscriber.getLimitAmount().subtract(subscriber.getBorrowableAmount());
                    logger.info("loan-amount: "+ loanAmount);
                    if(repayAmount.compareTo(loanAmount) <= 0){

                        Repay repay = new Repay(subscriber,repayAmount);
                        BigDecimal balance = subscriber.getBorrowableAmount().add(repayAmount);
                        subscriber.setBorrowableAmount(balance);
                        subscriberService.saveSubscriber(subscriber);
                        return new ResponseEntity<Repay>(repayRepository.save(repay),HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new ErrorResponse("the amount exceeds the loan. Your loan amount:  "+ loanAmount),HttpStatus.BAD_REQUEST);
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
