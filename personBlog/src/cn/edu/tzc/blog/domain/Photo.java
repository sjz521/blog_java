package cn.edu.tzc.blog.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;

public class Photo {
	private int id;
	private String name;
	private Timestamp created_at;
	private int uId;
	private String realName;

	public Photo() {
		super();
	}

	public Photo(int id, String name, Timestamp created_at, int uId) {
		super();
		this.id = id;
		this.name = name;
		this.created_at = created_at;
		this.uId = uId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setRealName();
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}
	
	public String getRealName() {
		return realName;
	}

	private void setRealName() {
		//解决图片名字为中文时不显示的问题
		try {
			String tmpStr = this.name.replace("-", "%");
			this.realName = URLDecoder.decode(tmpStr,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
