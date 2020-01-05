package cn.edu.tzc.blog.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class User {
	private int id;
	private String email;
	private String name;
	private String password;
	private int role;
	private String photo;
	private String introduction;
	private Timestamp created_at;
	private Timestamp lasttime;
	
	public User(int id, String email, String name, String password, int role, String photo, String introduction,
			Timestamp lasttime) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = role;
		this.photo = photo;
		this.introduction = introduction;
		this.lasttime = lasttime;
	}
	
	
	public Timestamp getCreated_at() {
		return created_at;
	}


	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}


	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public User() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Timestamp getLasttime() {
		return lasttime;
	}
	public void setLasttime(Timestamp lasttime) {
		this.lasttime = lasttime;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", role=" + role
				+ ", introduction=" + introduction + ", lasttime=" + lasttime + "]";
	}
}
