package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.ERoles;
import ra.model.entity.Roles;
import ra.model.repository.RoleRepository;
import ra.model.service.RoleService;

import java.util.Optional;
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Roles> findByRoleName(ERoles roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
