package com.victory.common.entity.search;

import com.victory.common.exception.search.InvlidSearchOperatorException;
import com.victory.common.exception.search.SearchException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * Created by ajkx
 * Date: 2017/7/19.
 * Time:14:35
 */
public class SearchCondition {

    //查询参数分隔符
    public static final String separator = "_";
    //查询字符串 property_op 如name_eq
    private String key;
    //属性名 如name
    private String property;
    //操作符
    private SearchOperator searchOperator;

    private Object value;

    private String join;

    public static SearchCondition newCondition(final String key, final Object value) {
        Assert.isNull(key,"Condition key must not null");

        String[] searchs = StringUtils.split(key, separator);

        if (searchs.length == 0) {
            throw new SearchException("Condition key format must be : property or property_op");
        }

        String searchProperty = searchs[0];

        SearchOperator operator = null;
        if (searchs.length == 1) {
            operator = SearchOperator.custom;
        }else{
            try {
                operator = SearchOperator.valueOf(searchs[1]);
            } catch (IllegalArgumentException e) {
                throw new InvlidSearchOperatorException(searchProperty, searchs[1]);
            }
        }

        return null;
    }
    private SearchCondition(final String property, final SearchOperator searchOperator, final Object value) {
        this.property = property;
        this.searchOperator = searchOperator;
        this.value = value;
    }
}
