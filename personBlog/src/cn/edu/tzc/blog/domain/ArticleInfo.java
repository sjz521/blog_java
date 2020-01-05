package cn.edu.tzc.blog.domain;

public class ArticleInfo extends Article {
	private String author;
	private String typeName;
	
	public ArticleInfo() {
		super();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
