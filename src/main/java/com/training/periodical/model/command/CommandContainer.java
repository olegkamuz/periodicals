package com.training.periodical.model.command;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.repository.MagazineRepository;
import com.training.periodical.model.repository.SubscriptionRepository;
import com.training.periodical.model.repository.ThemeRepository;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.model.repository.UserSubscriptionRepository;
import org.apache.log4j.Logger;

/**
 * Holder for all commands.
 * 
 */
public class CommandContainer implements Serializable {
    private static final long serialVersionUID = -5930883484357537731L;
	private static final Logger log = Logger.getLogger(CommandContainer.class);
	private static final Map<String, Command> commands = new TreeMap<String, Command>();

    static {
		// common commands
		commands.put("login", new LoginCommand());
        commands.put("", new LoginCommand());
        commands.put("login-check", new LoginCheckCommand(new UserRepository()));
		commands.put("logout", new LogoutCommand());
		commands.put("view-settings", new ViewSettingsCommand());
		commands.put("update-settings", new UpdateSettingsCommand(new UserRepository()));

		// out of control
        commands.put("index", new IndexCommand(new ThemeRepository(), new MagazineRepository()));
        commands.put("registration", new RegistrationCommand());
        commands.put("registration-save", new RegistrationSaveCommand(new UserRepository(), new UserBuilder()));

		// client commands
		commands.put("user-cabinet", new UserCabinetCommand(new UserSubscriptionRepository(),new UserRepository()));
        commands.put("create-subscription",
                new SubscriptionCommand(
                        new SubscriptionRepository(),
                        new UserRepository(), new MagazineRepository()));

		// admin commands
		commands.put("admin-cabinet", new AdminCabinetCommand(new UserRepository(), new MagazineRepository(), new MagazineBuilder()));
		
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