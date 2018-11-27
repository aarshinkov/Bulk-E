package com.bulke.security;

import com.bulke.entity.Role;
import com.bulke.entity.User;
import com.bulke.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Looking for user with username: " + username);

        User user = usersRepository.findUserByUsername(username);

        if (user == null) {
            log.debug("Username '" + username + "' not found in the database!");
            throw new UsernameNotFoundException("Username '" + username + "' not found in the database!");
        } else {
            log.debug("User found in the database, userId: " + user.getUserId());
            log.debug("Roles: " + user.getRoles());

            if (user.getRoles().isEmpty()) {
                throw new UsernameNotFoundException("User has no roles!");
            }
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
        }

        SiteUser siteUser = new SiteUser(username, user.getPassword(),
                true, true, true, true,
                authorities, user.getName(), user.getUserId());

        return siteUser;
    }
}
