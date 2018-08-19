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
package com.lihui.blog.framework.runner;

import com.lihui.blog.util.DateUtil;
import com.lihui.blog.business.consts.DateConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 程序启动后通过ApplicationRunner处理一些事务
 *
 * @author lihui ()
 * @version 1.0
 * @website https://www.iotjike.com
 * @date 2018/6/6 16:07
 * @since 1.0
 */
@Slf4j
@Component
public class BlogApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("博客部署启动完成，当前时间：" + DateUtil.date2Str(new Date(), DateConst.YYYY_MM_DD_HH_MM_SS_EN));
    }
}
