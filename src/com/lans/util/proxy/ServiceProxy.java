package com.lans.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.lans.util.dbc.DatabaseConnection;

public class ServiceProxy implements InvocationHandler {
	private Object target;
	public Object bind(Object target) {
		this.target = target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this);
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object retObject = null ;	// 接收返回对象
		String methodName = method.getName() ;
		boolean flag = methodName.startsWith("add") ||  methodName.startsWith("edit") 
				||  methodName.startsWith("delete") ||  methodName.startsWith("login");
	
		try {
			if (flag) {
				DatabaseConnection.getConnection().setAutoCommit(false);
				retObject = method.invoke(this.target, args) ;
				DatabaseConnection.getConnection().commit(); 
			}else {
				retObject = method.invoke(this.target, args) ;
			}
		} catch (Exception e) {
			if (flag) {
				DatabaseConnection.getConnection().rollback(); 
			}
			throw e ;
		} finally {
			DatabaseConnection.close(); 
		}
			
		return retObject;
	}

}
