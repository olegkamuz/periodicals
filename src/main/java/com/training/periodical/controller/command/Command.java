package com.training.periodical.controller.command;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for the Command pattern implementation.
 * 
 */
public interface Command {
//	private static final long serialVersionUID = 8879403039606311780L;

	/**
	 * Execution method for command.
	 * @return Address to go once the command is executed.
	 */
	String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}