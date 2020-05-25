package com.zz.springbootproject.common;

/**
 * @Description: 配置常量
 * @Author: chenxue
 * @Date: 2020/5/25  14:47
 */
public class Constant {
    /**
     * 当前页码
     */
    public static final String PAGE = "page";

    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";

    /**
     * 排序方式
     */
    public static final String ORDER = "order";

    /**
     * 状态 常量
     */
    public static final String ZERO = "0";

    /**
     * 状态 常量
     */
    public static final String ONE = "1";

    /**
     * token 过期时间 默认12小时
     */
    public static final int EXPIRE = 12;

    /**
     * @Description: 角色枚举类
     * @Author: chenxue
     * @Date: 2020/5/23  15:19
     */
    public enum RoleEnum {
        ADMIN(1,"系统管理员");
        private int value;
        private String name;

        RoleEnum(int code, String name) {
            this.value = code;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * @Description: 菜单枚举类
     * @Author: chenxue
     * @Date: 2020/5/23  15:19
     */
    public enum MenuEnum{
        /**
         * 目录
         */
        CATALOG(0,"目录"),
        /**
         * 菜单
         */
        MENU(1,"菜单"),
        /**
         * 按钮
         */
        BUTTON(2,"按钮");;
        private int value;
        private String name;

        MenuEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

}
