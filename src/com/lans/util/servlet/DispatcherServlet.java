package com.lans.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lans.util.web.ActionBeanUtil;
import com.lans.util.web.ModelAndView;
import com.lans.util.web.RequestUrlUtil;
import com.lans.util.web.ServletContextApplicationUtil;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContextApplicationUtil.setRequest(request);
		ServletContextApplicationUtil.setResponse(response);
		String[] urlResult = RequestUrlUtil.requestUri(request);
		try {
			ModelAndView mav = ActionBeanUtil.actionHandle(urlResult);
			if (mav != null) {
				request.getRequestDispatcher(mav.getUrl()).forward(request, response); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			String url = ActionBeanUtil.actionHandler(urlResult);
//			if (url != null) {
//				request.getRequestDispatcher(url).forward(request, response); 
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
