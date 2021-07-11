package works.red_eye.guest_book.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import works.red_eye.guest_book.entity.user.Role;
import works.red_eye.guest_book.entity.user.User;
import works.red_eye.guest_book.service.UserService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableWebSecurity
public class WebSecurityConfig {
    @Configuration
    public static class WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private PasswordEncoder encoder;
        @Autowired
        private UserService userService;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(encoder);
        }

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic().disable()
                    .sessionManagement()
                .and()
                    .authorizeRequests()
                    .antMatchers("/ws/**", "/comment/**", "/js/**", "/css/**").permitAll()
                    .antMatchers("/moderate/**", "/secure/ws/**").hasAnyAuthority(Role.MODER.name(), Role.ADMIN.name())
                    .antMatchers("/admin/**", "/user/**").hasAnyAuthority(Role.ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .permitAll();
        }
    }
}
