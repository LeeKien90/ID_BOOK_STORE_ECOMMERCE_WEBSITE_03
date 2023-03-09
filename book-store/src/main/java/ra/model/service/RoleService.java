package ra.model.service;


import ra.model.entity.ERoles;
import ra.model.entity.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(ERoles roleName);
}
