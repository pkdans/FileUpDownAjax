package com.utility;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonService extends Helper{
	public Map<String, String[]> m;
	public String err, succ;
//	Helper h;
	String topage=null;
	Object obj=null;
	HttpServletRequest request;
	HttpServletResponse response;
//	CommonDAO cd;
	/**
	 * 
	 */
	public CommonService(HttpServletRequest request) {
		this.request = request;
		m = request.getParameterMap();
//		h = new Helper();
		
	}
	public CommonService(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
		m = request.getParameterMap();
		/*String[] k = m.get("pkey");
		String[] d = m.get("pinf");
		for(int i=0;i<k.length;i++) {
			System.out.println(k[0]+"  //  "+d[0]);
		}*/
//		h = new Helper();
		
	}
	
	
	/**
	 * @param topage
	 * redirect to this page
	 * @param request
	 * @param response
	 */
	public CommonService(String topage, HttpServletRequest request, HttpServletResponse response) {
		this.topage = topage;
		this.request = request;
		this.response = response;
	}
	public Object getMappedObj(String classpath) {//BeanInfos.contactBean
		MapBean mb = new MapBean(m, classpath);
		obj = mb.getObject();
		return obj;
	}

	
	public boolean Param(String param) {
		return m.get(param)!=null;
	}
	
	
	
	public void forward() throws ServletException, IOException {
		if (topage != null) {
			request.setAttribute("error", err);
			request.setAttribute("success", succ);
			request.getRequestDispatcher(topage).forward(request, response);
		}
	}
	public Map<String, String[]> getM() {
		return m;
	}
	public void setM(Map<String, String[]> m) {
		this.m = m;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public String getSucc() {
		return succ;
	}
	public void setSucc(String succ) {
		this.succ = succ;
	}
	/*public Helper getH() {
		return h;
	}
	public void setH(Helper h) {
		this.h = h;
	}*/
	public String getTopage() {
		return topage;
	}
	public void setTopage(String topage) {
		this.topage = topage;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public boolean hasError() {
		// TODO Auto-generated method stub
		return err != null;
	}
	/**
	 * 
	 * Send an object & it will print for you. Nothing else;
	 * @param ob
	 */
	public void printS(Object ob) {
		System.out.println(new Date().toLocaleString()+" <<|>> "+ob);
	}
	public void setPopulateBeanScript(Object ob,String attr) {
		String s = getPopulateBeanScript(ob);
		request.setAttribute(attr, s);
	}
	public void setPopulateBeanScript(Object ob) {
		String s = getPopulateBeanScript(ob);
		request.setAttribute("bdata", s);
	}
	public String getPopulateBeanScript(Object ob) {
		Map<String,Object> gm = MapBean.getMappedDataFromBean(ob);
		String s = "";
		s += "var ind=[";
		Set<String> ks = gm.keySet();
		int n = ks.size();
		int i=0;
		for(String k:ks) {
			i++;
			s+="\""+k+"\"";
			if(i!=n)
				s+=",";
		}
		s+="];\n";
		i=0;
		s += "var vls=[";
		for(String k:ks) {
			i++;
			s+="\""+gm.get(k)+"\"";
			if(i!=n)
				s+=",";
		}
		s += "];\n";
				
				
		
		return s;
	}
}
