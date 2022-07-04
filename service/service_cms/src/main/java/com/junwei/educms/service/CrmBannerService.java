package com.junwei.educms.service;

import com.junwei.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author jww
 * @since 2022-07-02
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectHotBanner();
}
