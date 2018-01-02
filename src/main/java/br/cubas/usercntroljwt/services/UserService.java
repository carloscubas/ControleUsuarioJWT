package br.cubas.usercntroljwt.services;

import br.cubas.usercntroljwt.dto.User;

public interface UserService {
	
	void save(User user);

	User findByUsername(String username);
	
}