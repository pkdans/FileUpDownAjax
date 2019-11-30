package com.utility;

import java.util.Date;

public class Helper {

	public String generateId() {
		Date d = new Date();
		return String.valueOf(d.getTime());
	}
	/**
	 * 
	 * @param val
	 * value which to find if null
	 * @return
	 * returns true if blank else false
	 */
	public boolean isBlank(String val) {
		boolean err = false;
		if(val==null)
			err = true;
		else err = val.trim().isEmpty();
		
		return err;
	}
	public String isBlankField(String field,String val) {
		String err = null;
		if(isBlank(val)) {
			err = field +" should not be blank";
		}
		return err;
	}
	public Double getDoubleValue(String val) {
		Double ans = 0.0;
//		System.out.println("val "+val);
		if(!isBlank(val)) {
			try {
				val = val.trim();
				ans = Double.valueOf(val);
			}catch(Exception e) {e.printStackTrace();
			}
		}
		return ans;
	}
	/**
	 * 
	 * @param a
	 * first string
	 * @param b
	 * second string
	 * @return
	 * return false if any of them null or empty or not equal
	 */
	public boolean isEqualString(String a,String b) {
		if(a==null || b==null)
			return false;
		if(a.isEmpty() || b.isEmpty())
			return false;
		if(a.equals(b))
			return true;
		return false;
	}
	public int getIntegerValue(Object object) {
		int v = -1;
		try {
			if(object!=null) {
				v = Integer.valueOf(object.toString());
			}
		}catch(Exception e) {
			v=-1;
		}
		return v;
	}
}
