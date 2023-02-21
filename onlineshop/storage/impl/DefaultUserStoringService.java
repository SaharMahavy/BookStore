package onlineshop.storage.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import onlineshop.enteties.User;
import onlineshop.enteties.impl.DefaultUser;
import onlineshop.storage.UserStoringService;

public class DefaultUserStoringService implements UserStoringService {

	private static final String USER_INFO_STORAGE = "users.csv";
	private static final String CURRENT_TASK_RESOURCE_FOLDER = "onlinestore";
	private static final String RESOURCES_FOLDER = "resources";
	private static final int USER_EMAIL_INDEX = 4;
	private static final int USER_PASSWORD_INDEX = 3;
	private static final int USER_LASTNAME_INDEX = 2;
	private static final int USER_FIRSTNAME_INDEX = 1;
	private static final int USER_ID_INDEX = 0;
	
	private static DefaultUserStoringService instance;

	@Override
	public void saveUser(User user) {
		try { 
			Files.writeString(Paths.get(RESOURCES_FOLDER, CURRENT_TASK_RESOURCE_FOLDER, USER_INFO_STORAGE)
					,System.lineSeparator() + convertToString(user)
					,StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		  }
	}
	
	private String convertToString(User user) {
		return user.getId() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.getPassword()
		+ "," + user.getEmail();
	}
	
	public List<User> loadUsers(){
		try( var stream = Files.lines(Paths.get(RESOURCES_FOLDER, CURRENT_TASK_RESOURCE_FOLDER, USER_INFO_STORAGE))) {
			return stream.filter(Objects::nonNull)
					.filter(line -> !line.isEmpty())
					.map(line -> {
						String [] users = line.split(",");
						return new DefaultUser(Integer.valueOf(users[USER_ID_INDEX]), 
								users[USER_FIRSTNAME_INDEX], users[USER_LASTNAME_INDEX],
								users[USER_PASSWORD_INDEX], users[USER_EMAIL_INDEX]); 
					}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}
	
	public static DefaultUserStoringService getInstance() {
		if (instance == null) {
			instance = new DefaultUserStoringService();
		}
		return instance;
	}
	
}