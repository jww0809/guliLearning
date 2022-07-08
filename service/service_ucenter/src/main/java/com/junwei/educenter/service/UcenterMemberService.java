package com.junwei.educenter.service;

import com.junwei.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junwei.educenter.entity.vo.LoginVo;
import com.junwei.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author jww
 * @since 2022-07-04
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getLoginInfo(String memberId);

    UcenterMember getOpenIdInfo(String openid);
}
