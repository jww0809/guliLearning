package com.junwei.eduservice.controller;


import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.EduChapter;
import com.junwei.eduservice.entity.chapter.ChapterVo;
import com.junwei.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.beans.binding.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;
    /**
     * 根据courId查询到所有的章节和小节，在前端想要修改之前填写的课程大纲时进行调用
     *
     */
    @ApiOperation(value = "查询所有章节及其对应小节")
    @GetMapping("/getAllChapterVideo/{courseId}")
    public R getAllChapterVideo(@PathVariable String courseId){
        List<ChapterVo> chapterVideoList = chapterService.findChapterVideo(courseId);
        return R.ok().data("chapterVideoList",chapterVideoList);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节id查询章节信息：作用是在添加章节的页面，每次点击“编辑章节”，需要回显当前章节的信息
    @GetMapping("{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    //更新章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        //这里删除章节时需要考虑小节的情况，所以不能直接调用chapterService.removeById(chapterId)
        boolean flag = chapterService.removeChapterById(chapterId);
        if(flag)
            return R.ok();
        return R.error().message("删除失败");

    }
}

