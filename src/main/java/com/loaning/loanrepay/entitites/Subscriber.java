package com.loaning.loanrepay.entitites;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "subscriber_tbl")
public class Subscriber extends Auditable implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "msisdn", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    private String userName;
    private BigDecimal limitAmount;
    private BigDecimal borrowableAmount;
    public Subscriber(String phoneNumber, String userName){

        SecureRandom random = new SecureRandom();
        BigDecimal randDecimal = new BigDecimal((5000 + random.nextInt(5000)));
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.limitAmount = randDecimal;
        this.borrowableAmount = randDecimal;
    }
}
