package br.cubas.usercntroljwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.cubas.usercntroljwt.dto.Role;

public interface RoleRepository extends JpaRepository<Role, Long>  {
	
	Role findByRole(String role);

}
