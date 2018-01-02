package br.cubas.usercntroljwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cubas.usercntroljwt.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);


}
