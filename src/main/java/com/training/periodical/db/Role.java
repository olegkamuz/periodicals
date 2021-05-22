package com.training.periodical.db;

import com.training.periodical.db.entity.User;

/**
 * Role entity.
 * 
 * @author D.Kolesnikov
 * 
 */

public enum Role {
	ADMIN, CLIENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
