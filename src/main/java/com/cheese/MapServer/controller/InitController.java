package com.cheese.MapServer.controller;

import com.cheese.MapServer.bean.InitResult;
import com.cheese.MapServer.bean.ThreadReqParamInfo;
import com.cheese.MapServer.service.InitService;
import com.cheese.MapServer.utils.InitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/init")
public class InitController
{
    @Autowired
    InitService initService;

    @RequestMapping("/initMap")
    public InitResult initMap(Double left, Double right, Double top,
                              Double bottom, Integer level, String type)
    {
        List<ThreadReqParamInfo> errorList = initService.getLevelPic(InitUtils.getTypeByName(type),
                level, left, right, top, bottom);

        InitResult initResult = new InitResult();
        initResult.setLevel(level);
        initResult.setSuccess(errorList.isEmpty());
        initResult.setType(type);
        initResult.setErrorList(errorList);

        return initResult;
    }
}
