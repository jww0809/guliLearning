package com.junwei.educenter.mapper;

import com.junwei.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author jww
 * @since 2022-07-04
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //这里有多个参数的时候，使用@param注解来对应取值#{date_day}，#{aa}
    Integer selectRegisterCount(@Param("date_day") String day);
}
