/**
 * MIT License
 * Copyright (c) 2018 lihui
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lihui.blog.controller;

import com.github.pagehelper.PageInfo;
import com.lihui.blog.business.entity.UpdateRecorde;
import com.lihui.blog.business.enums.ResponseStatus;
import com.lihui.blog.framework.object.PageResult;
import com.lihui.blog.framework.object.ResponseVO;
import com.lihui.blog.util.ResultUtil;
import com.lihui.blog.business.service.SysUpdateRecordeService;
import com.lihui.blog.business.vo.UpdateRecordeConditionVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统更新日志
 *
 * @author lihui ()
 * @version 1.0
 * @website https://www.iotjike.com
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/update")
public class RestUpdateController {
    @Autowired
    private SysUpdateRecordeService updateRecordeService;

    @RequiresPermissions("updateLogs")
    @PostMapping("/list")
    public PageResult list(UpdateRecordeConditionVO vo) {
        PageInfo<UpdateRecorde> pageInfo = updateRecordeService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @RequiresPermissions("updateLog:add")
    @PostMapping(value = "/add")
    public ResponseVO add(UpdateRecorde updateRecorde) {
        updateRecordeService.insert(updateRecorde);
        return ResultUtil.success("成功");
    }

    @RequiresPermissions(value = {"updateLog:batchDelete", "updateLog:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            updateRecordeService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个更新记录");
    }

    @RequiresPermissions("updateLog:get")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.updateRecordeService.getByPrimaryKey(id));
    }

    @RequiresPermissions("updateLog:edit")
    @PostMapping("/edit")
    public ResponseVO edit(UpdateRecorde updateRecorde) {
        try {
            updateRecordeService.updateSelective(updateRecorde);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("更新记录修改失败！");
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

}
