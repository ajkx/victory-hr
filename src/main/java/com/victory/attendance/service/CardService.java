package com.victory.attendance.service;

import com.victory.attendance.entity.AttendanceRecord;
import com.victory.attendance.entity.Card;
import com.victory.attendance.repository.AttendanceRecordRepository;
import com.victory.attendance.repository.CardRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.entity.HrmResource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CardService extends BaseService<Card,Long>{


    private CardRepository getRepository() {
        return (CardRepository) baseRepository;
    }

    public Card findByCardNo(String cardNo) {
        return getRepository().findByCardNo(cardNo);
    }

    public Card findByHrmResource(HrmResource resource) {
        return getRepository().findByResource(resource);
    }

    public List<Card> findByResourceIn(Collection<HrmResource> resources) {
        return getRepository().findByResourceIn(resources);
    }
    @Override
    public Object packEntity(Card card) {
        Map result = new HashMap();
        result.put("id", card.getId());
        result.put("cardNo", card.getCardNo());
        HrmResource resource = card.getResource();
        if (resource != null) {
            result.put("resource", resource.getId());
            result.put("resourceName", resource.getName());
            result.put("workCode", resource.getWorkCode());
            result.put("department", resource.getDepartment() == null ? "" : resource.getDepartment() .getName());
        }
        return result;
    }
}
