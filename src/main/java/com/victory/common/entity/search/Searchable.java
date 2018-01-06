package com.victory.common.entity.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * Created by ajkx
 * Date: 2017/7/19.
 * Time:14:33
 */
public class Searchable {

    private Map<String, SearchCondition> searchConditionMap = Maps.newHashMap();

    /**
     * 保证添加的顺序
     */
    private List<SearchCondition> searchConditionList = Lists.newArrayList();

    private Pageable pageable;

    private Sort sort;

    public Map<String, SearchCondition> getSearchConditionMap() {
        return searchConditionMap;
    }

    public void setSearchConditionMap(Map<String, SearchCondition> searchConditionMap) {
        this.searchConditionMap = searchConditionMap;
    }

    public List<SearchCondition> getSearchConditionList() {
        return searchConditionList;
    }

    public void setSearchConditionList(List<SearchCondition> searchConditionList) {
        this.searchConditionList = searchConditionList;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
