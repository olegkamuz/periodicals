package com.training.periodical.entity;

/**
 * Theme entity.
 * 
 */
public class Theme extends Entity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Theme [name=" + name + ", getId()=" + getId() + "]";
	}

}
