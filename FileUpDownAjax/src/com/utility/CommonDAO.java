package com.utility;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CommonDAO {
	String error = null;
	String success = null;
	String classPath = null;
	Class cs = null;
	Object obj = null;

	public Object getObj() {
		return obj;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * 
	 */
	public CommonDAO() {
	}

	/**
	 * @param obj
	 */
	public CommonDAO(Object obj) {
		initClass(obj);
	}

	public void initClass(Object obj) {
		this.obj = obj;
		this.cs = obj.getClass();
	}

	// ------------------------------------------------------------
	private Session session = null;
	private SessionFactory factory = null;

	private boolean OpenSessionFactory() {
		error = null;
		try {
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml").addAnnotatedClass(cs);
			factory = cfg.buildSessionFactory();
			session = factory.openSession();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error = e.getMessage();
		}
		return false;
	}

	private boolean closeSessionFactory() {
		try {
			session.close();
			factory.close();
			return true;
		} catch (Exception e) {
			error = e.getMessage();
		}
		return false;
	}

	public String insert() {
		error = null;
		try {
			OpenSessionFactory();
			session.save(obj);
			Transaction tx = session.beginTransaction();
			tx.commit();
			closeSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			error = e.getMessage();
		}
		return error;
	}

	public Object getDataById(String id) {
		try {
			OpenSessionFactory();
			obj = session.get(cs, id);

			Transaction tx = session.beginTransaction();
			tx.commit();
			closeSessionFactory();
		} catch (Exception e) {
			error = e.getMessage();
		}
		return obj;
	}

	public String delete(String id) {
		try {
			OpenSessionFactory();
			Object u0 = session.get(cs, id);
			Transaction tx = session.beginTransaction();
			session.delete(u0);
			tx.commit();
			closeSessionFactory();
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	public String updateMerge(Object obs,String id) {
		try {
			OpenSessionFactory();
			Object u0 = session.get(cs, id);
			Transaction tx = session.beginTransaction();
			if(u0!=null) {
				MapBean.MergeBean(u0, obs);
			}
			tx.commit();
			closeSessionFactory();
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	public List<Object> getListByConditionObject(String tableName) {
		String sql = "select p from "+tableName+" p "+getWhereQuery();
		List<Object> l = null;
		try {
			OpenSessionFactory();
			Query qry = session.createQuery(sql);
			l = qry.list();
			Transaction tx = session.beginTransaction();
			tx.commit();
			closeSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	private String getWhereQuery() {
		Map<String,Object> mb = MapBean.getMappedDataFromBean(obj);
		if(mb!=null) {
			Set<String> ks = mb.keySet();
			String sql = " where ";
			int i=0;
			for(String k:ks) {
				Object val = mb.get(k);
				if(val!=null) {
					if(i!=0) {
						sql += " and ";
					}
					sql += "p."+k+"='"+val+"'";
					i++;
				}
			}
			return sql;
		}
		
		return null;
	}
	//-----------------------Misc-------------------
	public List<Object> getAllObject(String tableName) {
		List<Object> l = null;
		try {
			OpenSessionFactory();
			Query qry = session.createQuery("Select p from " + tableName + " p");
			l = qry.list();
			Transaction tx = session.beginTransaction();
			tx.commit();
			closeSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	public int executeUpdate(String sql) {
		try {
			OpenSessionFactory();
			Query qry = session.createQuery(sql);
			int i = qry.executeUpdate();
			System.out.println("rows updated: " + i);
			Transaction tx = session.beginTransaction();
			tx.commit();
			closeSessionFactory();
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List getListData(String sql) {
		// TODO Auto-generated method stub
		List l = null;
		try {
			OpenSessionFactory();
			Query qry = session.createQuery(sql);
			l = qry.list();
			Transaction tx = session.beginTransaction();
			tx.commit();
			closeSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			error = e.getMessage();
		}
		return l;
	}

	
}
