package com.junwei.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.commonutils.JwtUtils;
import com.junwei.commonutils.MD5;
import com.junwei.educenter.entity.UcenterMember;
import com.junwei.educenter.entity.vo.LoginVo;
import com.junwei.educenter.entity.vo.RegisterVo;
import com.junwei.educenter.mapper.UcenterMemberMapper;
import com.junwei.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-07-04
 */
@Service
@Slf4j
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    
    @Autowired
    private RedisTemplate redisTemplate;
    //登录
    @Override
    public String login(LoginVo loginVo) {

        //验证手机号、密码、是否禁用
        String mobile = loginVo.getMobile();
        String password =  loginVo.getPassword();
        if(StringUtils.isEmpty(mobile) ||StringUtils.isEmpty(password)){
            log.error("手机号或密码为空，登录失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if(null == member){
            log.error("该用户不存在，请先进行注册");
        }
        if(!MD5.encrypt(password).equals(member.getPassword())){
            log.error("密码错误，登录失败");
        }

        if(member.getIsDisabled()){
            log.error("用户已被禁用，登录失败");
        }
        //登录成功，使用jwt返回成功的token
        return JwtUtils.getJwtToken(member.getId(),member.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();

        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password) ||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(code)){
            log.error("填入的信息为空，注册失败");
        }

        //验证码
        String redisCode = (String) redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            log.error("验证码错误，注册失败");
        }

        //判断手机号是否已经存在数据库中，防止重复注册
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            log.error("手机号已经存在于数据库中，注册失败");
        }
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(ucenterMember);
    }

    @Override
    public UcenterMember getLoginInfo(String memberId) {
        return baseMapper.selectById(memberId);

    }

    @Override
    public UcenterMember getOpenIdInfo(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }
}
