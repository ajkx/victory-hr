package com.victory.attendance.repository;

import com.victory.attendance.entity.AttendanceRecord;
import com.victory.attendance.entity.Card;
import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:10:25
 */
@Repository
public interface CardRepository extends BaseRepository<Card,Long> {

    Card findByCardNo(String cardNo);

    Card findByResource(HrmResource resource);

    List<Card> findByResourceIn(Collection<HrmResource> resources);
}
