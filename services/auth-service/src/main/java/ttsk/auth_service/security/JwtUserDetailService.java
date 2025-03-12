package ttsk.auth_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ttsk.auth_service.model.Customer;
import ttsk.auth_service.service.CustomerService;

@Service
public class JwtUserDetailService implements UserDetailsService {
    private final CustomerService customerService;

    @Autowired
    public JwtUserDetailService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerService.getByUserName(username);
        return JwtEntityFactory.create(customer);
    }
}
