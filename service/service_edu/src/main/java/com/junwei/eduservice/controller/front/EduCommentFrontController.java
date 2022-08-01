package com.junwei.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.commonutils.JwtUtils;
import com.junwei.commonutils.R;
import com.junwei.eduservice.client.UcenterClient;
import com.junwei.eduservice.entity.EduComment;
import com.junwei.eduservice.entity.UcenterMember;
import com.junwei.eduservice.service.EduCommentService;
import com.junwei.entity.vo.CenterMemberNew;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/eduservice/educomment")
//@CrossOrigin
public class EduCommentFrontController {

    @Autowired
    private EduCommentService commentService;

    //注入调用ucenter模块中方法的接口
    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 根据课程id（分页）查询课程评论列表
     */
    @GetMapping("getCommentList/{currentPage}/{limit}")
    public R getCommentList(@PathVariable("currentPage") long currentPage,@PathVariable long limit,
                            @ApiParam(name = "courseQuery", value = "查询对象", required = false)String courseId){
        Page<EduComment> page = new Page<>(currentPage,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(page,wrapper);

        List<EduComment> commentList = page.getRecords();

        Map<String,Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", page.getCurrent());
        map.put("pages", page.getPages());
        map.put("size", page.getSize());
        map.put("total", page.getTotal());
        map.put("hasNext", page.hasNext());
        map.put("hasPrevious", page.hasPrevious());
        return R.ok().data(map);
    }


    /**
     * (前端)添加评论
     */
    @PostMapping("/addComment")
    public R addCourseComment(HttpServletRequest request,@RequestBody EduComment comment){
        //评论前 应该需要检测是否登录
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().code(28004).message("请先登录");
        }

        comment.setMemberId(memberId);

        //调用ucenter模块
        CenterMemberNew memberNew = ucenterClient.getInfoUser(memberId);
        comment.setAvatar(memberNew.getAvatar());
        comment.setNickname(memberNew.getNickname());

        commentService.save(comment);
        return R.ok().message("添加课程评论成功！");
    }



}

