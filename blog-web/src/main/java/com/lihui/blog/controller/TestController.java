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

import com.lihui.blog.business.enums.QiniuUploadType;
import com.lihui.blog.business.service.BizArticleService;
import com.lihui.blog.business.service.BizCommentService;
import com.lihui.blog.business.service.SysLinkService;
import com.lihui.blog.business.service.SysNoticeService;
import com.lihui.blog.framework.object.ResponseVO;
import com.lihui.blog.plugin.QiniuApi;
import com.lihui.blog.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @PostMapping("/testQiniuToken")
    public ResponseVO testQiniuToken(@RequestParam(value = "key") String key)
    {
        String uptoken="";
        //File file = new File("C:\\Users\\lihui\\Desktop\\study\\a_test20190602.png");
        //String key="weixin/201906/20190603170916262.png";
        System.out.println("...............testQiniuToken-key="+key);
        try {
            /*String filePath = QiniuApi.getInstance()
                    .withFileName(file.getName(), QiniuUploadType.SIMPLE)
                    .upload(file);*/


            uptoken=(QiniuApi.getInstance().withFileNameByKey(key, QiniuUploadType.WEIXIN_IMAGE)).getUpToken();

            //uptoken=(QiniuApi.getInstance().withFileName(file.getName(), QiniuUploadType.SIMPLE)).getUpToken();

            System.out.println("-----uptoken="+uptoken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success("uptoken",uptoken);
    }

}
