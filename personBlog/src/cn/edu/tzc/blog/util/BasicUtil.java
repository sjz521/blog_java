package cn.edu.tzc.blog.util;

import java.util.List;

public class BasicUtil {
	
	/**
	 * 将Integer类型的List数组转为string数组
	 * @param ids
	 * @return
	 */
	public String[] integerListToStrings(List<Integer> ids) {
		String[] strs = new String[ids.size()];
		for(int i=0;i<ids.size();i++) {
			strs[i] = String.valueOf(ids.get(i));
		}
		return strs;
	}
}
