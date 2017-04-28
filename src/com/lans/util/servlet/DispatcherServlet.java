package com.lans.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.lans.util.web.ActionBeanUtil;
import com.lans.util.web.ModelAndView;
import com.lans.util.web.RequestUrlUtil;
import com.lans.util.web.ServletContextApplicationUtil;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestContentType = request.getContentType();
		if (requestContentType != null) {
			if (requestContentType.contains("multipart/form-data")) {
				SmartUpload smart = new SmartUpload();
				smart.initialize(super.getServletConfig(), request, response);
				try {
					smart.upload();
					
				} catch (SmartUploadException e) {
					e.printStackTrace();
				}
				ServletContextApplicationUtil.setSmartUpload(smart);
			}
		}
		
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
