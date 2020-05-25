package com.zz.springbootproject.utils;

import org.apache.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author chenxue
 */
public class ServerResponse extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public ServerResponse() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static ServerResponse error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static ServerResponse error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static ServerResponse error(int code, String msg) {
		ServerResponse r = new ServerResponse();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ServerResponse ok(String msg) {
		ServerResponse r = new ServerResponse();
		r.put("msg", msg);
		return r;
	}
	
	public static ServerResponse ok(Map<String, Object> map) {
		ServerResponse r = new ServerResponse();
		r.putAll(map);
		return r;
	}
	
	public static ServerResponse ok() {
		return new ServerResponse();
	}

	public ServerResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
