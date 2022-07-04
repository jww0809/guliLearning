package com.junwei.educms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.commonutils.R;
import com.junwei.educms.entity.CrmBanner;
import com.junwei.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 后台控制器
 * </p>
 *
 * @author jww
 * @since 2022-07-02
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    //增删改查（分页查询）

    //(无条件的)分页查询
    @GetMapping("{page}/{limit}")
    public R getAllBanner(@PathVariable("page") long page,@PathVariable("limit") long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        //QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        bannerService.page(bannerPage,null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    //新增banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
         bannerService.save(crmBanner);
         return R.ok().message("添加banner成功");
    }

    //删除banner
    @DeleteMapping("removeBanner/{id}")
    public R deleteBanner(@PathVariable("id") String id){
        bannerService.removeById(id);
        return R.ok().message("删除banner成功");
    }

    //修改banner
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok().message("修改banner成功");
    }


}

