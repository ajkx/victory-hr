package com.victory.attendance.web.controller;

import com.victory.attendance.entity.Card;
import com.victory.attendance.service.CardService;
import com.victory.common.domain.result.Response;
import com.victory.common.web.controller.BaseController;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/card")
public class CardController extends BaseController<Card,Long>{


    public CardController() {
        setResourceIdentity("attendance:card");
    }

    private CardService getService() {
        return (CardService) baseService;
    }

    @Override
    protected boolean checkEntity(Card entity, Response response) {
        return super.checkEntity(entity, response);
    }

    /**
     * name 模糊匹配卡号，人名，人拼音
     * @param specification
     * @param pageable
     * @return
     */
    @RequiresPermissions("attendance:card:view")
    @RequestMapping(method = RequestMethod.GET)
    public Page<Card> list(
            @Or(value = {
                @Spec(path = "cardNo",params="name", spec = Like.class),
                @Spec(path = "resource.name", params = "name", spec = Like.class),
                @Spec(path = "resource.shortName", params = "name", spec = Like.class)
            })
                    Specification<Card> specification,
            @PageableDefault() Pageable pageable) {
        return getService().findAll(specification,pageable);
    }

}
