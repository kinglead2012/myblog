package com.kinglead.demo.entity;

import com.github.pagehelper.PageRowBounds;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author kinglead
 * @date 2020-09-27 下午 16:41
 * @describe {请写具体描述}
 */
@Data
public class PageRowInfo {

    /**
     * 页数
     */
    @Range(min = 1, message = "最小是第一页")
    private int pageNum = 1;

    /**
     * 每页条数
     */
    @Range(min = 5, message = "每页最小5条")
    private int pageSize = 1;

    public int getPageNum() {
        return (pageNum - 1) * pageSize;
    }

    public int getCurPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 初始化 PageRowBounds
     */
    public PageRowBounds toPageRowBounds() {
        return new PageRowBounds(getPageNum(), getPageSize());
    }
}
