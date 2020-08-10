package com.zz.springbootproject.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-05-15 11:46
 **/
@Configuration
public class MybatisPlusConfig {

    /**
     * @param
     * @Description: 分页插件
     * @Author: chenxue
     * @Date: 2020/5/15  11:48
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        //添加 攻击 SQL 阻断解析器,阻止全表更新以及删除操作
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser() {
            @Override
            public void processDelete(Delete delete) {
                //可以重写该方法,实现自己逻辑
                if ("user".equals(delete.getTable().getName())) {
                    //比如这样自定义跳过 某个表
                    return;
                }
                super.processDelete(delete);
            }

            @Override
            public void processUpdate(Update update) {
                super.processUpdate(update);
            }
        });
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

}
