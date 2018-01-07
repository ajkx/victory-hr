package com.victory.attendance.repository;

import com.victory.attendance.web.vo.ResultCollect;
import com.victory.common.web.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface CollectorRepository {

    List collectResult(Date beginDate, Date endDate, SearchVo searchVo, Pageable pageable);
}
