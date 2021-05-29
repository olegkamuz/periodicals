package com.training.periodical.entity;

/**
 * Root of all entities which have identifier field.
 * 
 */
public abstract class Entity {

	private Long id;

	private String tableName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() { return this.tableName;}

}
