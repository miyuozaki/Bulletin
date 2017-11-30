package bulletin.beans;

import java.io.Serializable;
import java.util.Date;

public class UserComments implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int user_id;
	private int post_id;
	private String name;
	private String text;
	private Date created_at;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
