package com.zz.springbootproject.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zz.springbootproject.common.Constant;

import java.util.Map;
import java.util.Objects;

public class Query<T> {
    private IPage<T> page;

    public Query(Map<String, Object> params) {
        //默认起始页为 1
        long currPage = 1;
        //默认每页显示记录数
        long pageSize = 10;
        if (Objects.nonNull(params)) {
            //如果页面参数有 page
            if (params.containsKey(Constant.PAGE)) {
                currPage = Long.valueOf(Objects.toString(params.get(Constant.PAGE), ""));
            }
            //如果页面参数有 limit
            if (params.containsKey(Constant.LIMIT)) {
                pageSize = Long.valueOf(Objects.toString(params.get(Constant.LIMIT), ""));
            }
            this.page = new Page<T>(currPage, pageSize);
            params.put(Constant.PAGE, this.page);
        }
    }

    public IPage<T> getPage() {
        return page;
    }

    public void setPage(IPage<T> page) {
        this.page = page;
    }
}
