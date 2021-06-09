package com.training.periodical.model.command;

import com.training.periodical.model.repository.RepositoryException;

import java.io.Serializable;

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

    CommandException createCommandException(String methodName, Exception e);
    CommandException createCommandException(String methodName);
}