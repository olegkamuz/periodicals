package com.training.periodical.controller.command;

import com.training.periodical.model.dao.DBManager;
import com.training.periodical.model.service.ServiceFactory;
import com.training.periodical.model.service.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class CommandFactory implements AutoCloseable {
    private final Connection connection;
    private final ServiceFactory serviceFactory;
    private final TransactionManager transactionManager;

    public CommandFactory() {
        connection = DBManager.getInstance().getConnection();
        serviceFactory = new ServiceFactory(connection);
        transactionManager = new TransactionManager(connection);
    }

    public Command create(String command) {
        switch (command) {
            // common commands
            case "login":
                return new LoginCommand();
            case "login-check":
                return new LoginCheckCommand();

            // client commands
            case "index":
                return new ListByCategoryMenuCommand();
            case "one-category-magazines":
                return new ListByOneCategoryMenuCommand();
            case "create-subscription":
                return new SubscriptionCommand(serviceFactory.createUserService(), serviceFactory.createSubscriptionService(), transactionManager);
            default:
                throw new IllegalArgumentException("Illegal Command");
        }
    }

    @Override
    public void close() throws CommandException{
        try {
            connection.close();
        } catch (SQLException e){
            throw new CommandException("exception in close method at CommandFactory",e);
        }
    }
}
