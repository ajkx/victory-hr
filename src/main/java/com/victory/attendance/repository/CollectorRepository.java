package com.victory.attendance.repository;

import com.victory.attendance.web.vo.PageInfo;
import com.victory.common.web.vo.SearchVo;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface CollectorRepository {

    PageInfo collectResult(Date beginDate, Date endDate, SearchVo searchVo, Pageable pageable);
}
