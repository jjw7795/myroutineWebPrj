package com.myroutine.web.dao.entity;

public class ComplainCategory {
	private int id;
	private String type;
	
	public ComplainCategory() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param type
	 */
	public ComplainCategory(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ComplainCategory [id=" + id + ", type=" + type + ", getId()=" + getId() + ", getType()=" + getType()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
	
}
