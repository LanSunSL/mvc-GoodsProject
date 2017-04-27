package com.lans.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
@WebFilter(urlPatterns="/*", initParams={
		@WebInitParam(name="charset", value="UTF-8")
})
public class EncodingFilter implements Filter {
	private String charset;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.charset = filterConfig.getInitParameter("charset");
		if (charset == null || "".equals(charset)) {
			charset = "UTF-8";
		}
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding(charset);
		resp.setCharacterEncoding(charset);
		chain.doFilter(req, resp);
	}

}
