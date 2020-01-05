package cn.edu.tzc.blog.domain;

import java.sql.Timestamp;

public class Type {
	private int id;
	private String name;
	private Timestamp created_at;
	
	public Type() {
		super();
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

	public Type(int id, String name, Timestamp created_at) {
		super();
		this.id = id;
		this.name = name;
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + ", created_at=" + created_at + "]";
	}
	
}
