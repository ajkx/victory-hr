package com.victory.hrm.web.controller;

import com.victory.hrm.entity.HrmSubCompany;
import com.victory.hrm.service.OrganizationService;
import com.victory.hrm.web.vo.OrganizationTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/5/13.
 * Time:15:46
 */
@Controller
@RequestMapping(value = "/api/org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;


    @RequestMapping(value = "/tree")
    public
    @ResponseBody
    List<OrganizationTree> getTree() {
        List<HrmSubCompany> list = organizationService.findRootSubCompany();
        return organizationService.getSubTree(list);
    }

}
