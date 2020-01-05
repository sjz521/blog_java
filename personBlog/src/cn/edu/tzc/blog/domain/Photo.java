package cn.edu.tzc.blog.domain;

import java.sql.Timestamp;

public class Photo {
	private int id;
	private String name;
	private Timestamp created_at;
	private int uId;
	
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
	
}
