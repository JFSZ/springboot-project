/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.zz.springbootproject.validator;

import com.zz.springbootproject.exception.ServerException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new ServerException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new ServerException(message);
        }
    }
}
