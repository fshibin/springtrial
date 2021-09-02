package com.thefans.sdjdbc4mysql.model;

import org.springframework.data.annotation.Id;

public class Product {
    @Id
    private long id;
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String name;
    private String description;
}
