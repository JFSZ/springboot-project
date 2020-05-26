package com.zz.springbootproject.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

public class PageUtil<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    //总页数
    private long totalCount;

    //每页数据大小
    private long pageSize;

    //当前页数
    private long currPage;

    //总页数
    private long totalPage;

    //分页数据
    private List<T> list;


    public PageUtil() {
    }

    public PageUtil(IPage<T> page) {
        this.totalCount =  page.getTotal();
        this.currPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.list = page.getRecords();
        this.totalPage =(long)Math.ceil((double) totalCount/pageSize);
    }

    public PageUtil(long totalCount, long pageSize, long currPage, long totalPage, List<T> list) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = totalPage;
        this.list = list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCurrPage() {
        return currPage;
    }

    public void setCurrPage(long currPage) {
        this.currPage = currPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
