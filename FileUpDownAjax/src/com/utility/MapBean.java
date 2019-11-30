package com.utility;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pijus
 *
 */
public class MapBean {
	Object obj = null;

	/**
	 * 
	 */
	public MapBean() {
	}
	/**
	 * @param Map
	 *            Map<String,String[]> or Map<String,String> from request parameter
	 *            of form
	 * @param classpath
	 *            class path = package name+class name e.g package com.net; class
	 *            Student
	 * 
	 *            classpath = com.net.Student;
	 */
	public MapBean(Map<String, String[]> req, String classpath) {
		try {

			// Set<String> key = req.keySet();

			Class c = Class.forName(classpath);
			obj = c.newInstance();

			Field[] fds = c.getDeclaredFields();

			if (fds != null) {
				for (int i = 0; i < fds.length; i++) {
					Field fd = fds[i];
					String fdname = fd.getName();
					String mthd = getSetter(fdname);
					Type tp = fd.getGenericType();
					boolean arr = isArrayType(tp.getTypeName());
					Method m = c.getDeclaredMethod(mthd, new Class[] { (Class) tp });

					Object[] data = req.get(fdname);
					if (data != null) {
						if (arr) {
							m.invoke(obj, (Object) data);
						} else {
							m.invoke(obj, data[0]);
						}
					}
				}
			}

		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | ClassNotFoundException
				| InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Map<String, Object> getMappedDataFromBean(Object obj) {
		Map<String, Object> mb = new HashMap<String, Object>();
		try {
			Class c = obj.getClass();

			Field[] fds = c.getDeclaredFields();

			if (fds != null) {
				for (int i = 0; i < fds.length; i++) {
					Field fd = fds[i];
					String fdname = fd.getName();
					//Type tp = fd.getGenericType();
					Object oo = null;
					if (Modifier.isPrivate(fd.getModifiers())) {
			        	fd.setAccessible(true);
			        	
			        }
					oo = fd.get(obj);
					mb.put(fdname, oo);
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mb;
	}

	/**
	 * 
	 * @param objTo
	 * @param obFrom
	 * @return
	 */
	public static Object MergeBean(Object objTo,Object obFrom) {
		try {
			Map<String, Object> mb = getMappedDataFromBean(obFrom);
			Class ct = objTo.getClass();
			Field[] fdsT = ct.getDeclaredFields();

			if (fdsT != null) {
				for (int i = 0; i < fdsT.length; i++) {
					Field fd = fdsT[i];
					String fdname = fd.getName();
					String mthd = getSetter(fdname);
					Type tp = fd.getGenericType();
//					boolean arr = isArrayType(tp.getTypeName());
					Method m = ct.getDeclaredMethod(mthd, new Class[] { (Class) tp });

					Object data = mb.get(fdname);
					if(data!=null) {
						m.invoke(objTo, data);
					}
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objTo;
	}
	private static String getSetter(String fieldName) {
		String sn = "";
		if (fieldName != null) {
			sn = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
		}
		return sn;
	}

	private String getGetter(String fieldName) {
		String sn = "";
		if (fieldName != null) {
			sn = "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
		}
		return sn;
	}

	public Object getObject() {
		return obj;
	}

	boolean isArrayType(String name) {
		int n = name.length();
		if (name.substring(n - 2).equals("[]"))
			return true;
		return false;
	}

}
