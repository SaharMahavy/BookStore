package onlineshop.storage;

import java.util.List;

import onlineshop.enteties.User;

public interface UserStoringService {
	
	void saveUser(User user);
	
	List<User> loadUsers();
	
}
