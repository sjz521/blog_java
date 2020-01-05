package cn.edu.tzc.blog.domain;

import java.sql.Timestamp;

public class Article {
	private int id;
	private String title;
	private String introduction;
	private String content;
	private Timestamp created_at;
	private String photo;
	private int uid;
	private int tid;
	
	public Article() {
		super();
	}
	public Article(int id, String title, String introduction, String content, Timestamp created_at, String image,
			int uid, int tid) {
		super();
		this.id = id;
		this.title = title;
		this.introduction = introduction;
		this.content = content;
		this.created_at = created_at;
		this.photo = image;
		this.uid = uid;
		this.tid = tid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	
	
	
}
