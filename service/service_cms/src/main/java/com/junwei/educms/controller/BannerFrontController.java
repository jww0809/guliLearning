package com.junwei.educms.controller;


import com.junwei.commonutils.R;
import com.junwei.educms.entity.CrmBanner;
import com.junwei.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-07-02
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getHotBanner")
    public R getAllBanner(){
        List<CrmBanner> bannerList = bannerService.selectHotBanner();
        return R.ok().data("bannerList",bannerList);
    }

}

