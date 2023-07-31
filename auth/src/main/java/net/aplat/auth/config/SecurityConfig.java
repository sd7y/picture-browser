package net.aplat.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedPage("/unauth.html"); // 403 的时候访问指定页面
        http.formLogin() // 自定义自己编写的登录页面
                .loginPage("/login.html") // 登录页面设置
                .loginProcessingUrl("/user/login") // 登录访问路径, 只是一个路径, 逻辑会由 SpringSecurity 处理
                .defaultSuccessUrl("/login_success.html").permitAll() // 登录成功后跳转的地址
                .and().authorizeRequests().antMatchers("/", "/test/hello", "/user/login").permitAll() // 访问这些路径的时候不需要认证
                .antMatchers("/test/index").hasAuthority("admins") // 只有 admins 权限的才能访问
                .antMatchers("/test/index2").hasAnyAuthority("admins", "manager") // admins 和 manager 都能访问
                .antMatchers("/test/index3").hasRole("sale") // 有 sale 角色, 但是设置角色的时候需要设置 ROLE_sale 才行, 源码中是加了个 ROLE_ 前缀了
                .antMatchers("/test/index4").hasAnyRole("sale1", "sale2") // 有 sale 或 sale2 才能访问
                .anyRequest().authenticated()
                .and().csrf().disable(); // 关闭 csrf 防护

        // logout 相关配置
        http.logout().logoutUrl("/logout") // 只是一个地址, SpringSecurity 会处理它
                .logoutSuccessUrl("/test/hello").permitAll(); // 退出后跳转的页面

    }

    @Bean
    PasswordEncoder password() {
        // 对应的解密用这个 Bean
        return new BCryptPasswordEncoder();
    }
}
