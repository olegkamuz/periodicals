package com.training.periodical.controller.command;

import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.service.ServiceException;

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
public interface Command extends Serializable{

	/**
	 * Execution method for command.
	 * @return Address to go once the command is executed.
	 */
	String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    CommandException createCommandException(String methodName, ServiceException e);
}