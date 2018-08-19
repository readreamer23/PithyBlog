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
package com.lihui.blog.business.service;


import com.lihui.blog.business.entity.Config;

import java.util.Map;

/**
 * 系统配置
 *
 * @author lihui ()
 * @version 1.0
 * @website https://www.iotjike.com
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SysConfigService {

    /**
     * 获取系统配置
     *
     * @return
     */
    Config get();

    /**
     * 添加系统配置
     *
     * @param config
     * @return
     */
    Config insert(Config config);

    /**
     * 删除系统配置记录
     *
     * @param id
     */
    void remove(Long id);

    /**
     * 修改系统配置记录
     *
     * @param config
     */
    void update(Config config);

    /**
     * 获取网站详情
     *
     * @return
     */
    Map<String, Object> getSiteInfo();
}
