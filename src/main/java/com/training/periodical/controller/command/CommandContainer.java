package com.training.periodical.controller.command;

import java.util.Map;
import java.util.TreeMap;

import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.service.MagazineService;
import com.training.periodical.model.service.SubscriptionService;
import com.training.periodical.model.service.ThemeService;
import com.training.periodical.model.service.UserService;
import com.training.periodical.model.service.UserSubscriptionService;
import org.apache.log4j.Logger;

/**
 * Holder for all commands.
 * 
 */
public class CommandContainer {
	
	private static final Logger log = Logger.getLogger(CommandContainer.class);
	
	private static final Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
        commands.put("login-check", new LoginCheckCommand(new UserService()));
//        commands.put("login_session", new LoginSessionCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("updateSettings", new UpdateSettingsCommand());

		// out of control
        commands.put("index", new IndexCommand(new ThemeService(), new MagazineService()));
        commands.put("registration", new RegistrationCommand());
        commands.put("registration-save", new RegistrationSaveCommand(new UserService(), new UserBuilder()));

		// client commands
		commands.put("user-cabinet", new UserCabinetCommand(new UserSubscriptionService(),new UserService()));
        commands.put("one-category-magazines", new ListByOneCategoryMenuCommand(new MagazineService()));
        commands.put("create-subscription",
                new SubscriptionCommand(
                        new SubscriptionService(),
                        new UserService(), new MagazineService()));

		// admin commands
		commands.put("admin-cabinet", new AdminCabinetCommand(new UserService()));
		
		log.debug("Command container was successfully initialized");
		log.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}