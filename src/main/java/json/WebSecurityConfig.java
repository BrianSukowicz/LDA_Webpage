package json;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/uploads").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }



    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String[] credentials = new String[4];
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        credentials = Files.readAllLines(Paths.get("credentials.txt")).toArray(credentials);
//        credentials = Files.readAllLines(Paths.get("src\\main\\java\\json\\credentials.txt")).toArray(credentials);
        String user_username = credentials[0];
        String user_password = credentials[1];
        String admin_username = credentials[2];
        String admin_password = credentials[3];

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(user_username).password(user_password).roles("USER");
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(admin_username).password(admin_password).roles("ADMIN");

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("password")
//                        .roles("ADMIN")
//                        .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

}
