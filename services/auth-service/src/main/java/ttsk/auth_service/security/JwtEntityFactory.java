package ttsk.auth_service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ttsk.auth_service.model.Customer;
import ttsk.auth_service.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {
    public static JwtEntity create(Customer customer) {
        return new JwtEntity(
                customer.getId(),
                customer.getUsername(),
                customer.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(customer.getRoles()))

        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
