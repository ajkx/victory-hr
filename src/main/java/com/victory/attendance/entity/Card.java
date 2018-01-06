package com.victory.attendance.entity;

import com.victory.common.entity.DateEntity;
import com.victory.hrm.entity.HrmResource;

import javax.persistence.*;

/**
 * Created by ajkx
 * Date: 2017/7/3.
 * Time:15:08
 */
@Entity
@Table(name="EHR_Card")
public class Card extends DateEntity<Long> {

    @Column(name = "card_no")
    private String cardNo;

    @OneToOne(targetEntity = HrmResource.class)
    @JoinColumn(name= "resource_id",referencedColumnName = "id")
    private HrmResource resource;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public HrmResource getResource() {
        return resource;
    }

    public void setResource(HrmResource resource) {
        this.resource = resource;
    }
}
