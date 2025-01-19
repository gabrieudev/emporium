package br.com.gabrieudev.emporium.infrastructrure.config.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UsersRolesModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.RoleRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UsersRolesRepository;

@Configuration
@Profile("dev")
public class DatabaseLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UsersRolesRepository usersRolesRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;

    public DatabaseLoader(UserRepository userRepository, UsersRolesRepository usersRolesRepository,
            BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            UserModel user = new UserModel();
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("adminpassword"));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            RoleModel adminRole = new RoleModel();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Role de administrador");
            RoleModel userRole = new RoleModel();
            userRole.setName("USER");
            userRole.setDescription("Role de usuário comum");

            UsersRolesModel adminUsersRoles = new UsersRolesModel();
            adminUsersRoles.setRole(adminRole);
            adminUsersRoles.setUser(user);
            UsersRolesModel userUsersRoles = new UsersRolesModel();
            userUsersRoles.setRole(userRole);
            userUsersRoles.setUser(user);

            userRepository.save(user);
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            usersRolesRepository.save(userUsersRoles);
            usersRolesRepository.save(adminUsersRoles);

            CartModel cart = new CartModel();
            cart.setUser(user);
            cart.setTotal(BigDecimal.ZERO);
            cart.setCreatedAt(LocalDateTime.now());
            cart.setUpdatedAt(LocalDateTime.now());

            cartRepository.save(cart);
        }
    }
}
