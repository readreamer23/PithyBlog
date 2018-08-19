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


import com.github.pagehelper.PageInfo;
import com.lihui.blog.business.entity.ArticleTags;
import com.lihui.blog.framework.object.AbstractService;
import com.lihui.blog.business.vo.ArticleTagsConditionVO;

/**
 * 文章标签
 *
 * @author lihui ()
 * @version 1.0
 * @website https://www.iotjike.com
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface BizArticleTagsService extends AbstractService<ArticleTags, Integer> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<ArticleTags> findPageBreakByCondition(ArticleTagsConditionVO vo);

    /**
     * 通过文章id删除文章-标签关联数据
     *
     * @param articleId
     * @return
     */
    int removeByArticleId(Long articleId);

    /**
     * 批量添加
     *
     * @param tagIds
     * @param articleId
     */
    void insertList(Long[] tagIds, Long articleId);
}
