package com.xc.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired

    /**认证，内存配置用户信息
    测试，使用内存用户存储配置了两个用户
    withUser()的调用启动用户的配置，赋予用户名
    password() authorities()设定密码和权限
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("xc")
                    .password("xc123")
                    .authorities("ROLE_USER")
                .and()
                    .withUser("xcv")
                    .password("xcv123")
                    .authorities("ROLE_USER");
    }
    */
    /**
     * 基于JDBC的用户存储,需要配置数据源
     * 配置数据源，默认会去查询users、authorities、groups、group_members、group_authorities
     * 查询检索用户的用户名、密码以及是否启用它们，此信息用于对用户进行身份验证
     * 查询users表，select username,password,enabled from users where username = ?
     * 查询查询用户授予的权限，以进行授权
     * 查询authorities表，select username,authority from authorities where username = ?
     * 查询作为组的成员授予用户的权限
     * select g.id, g.group_name, ga.authority
     *     from groups g, group_members gm, group_authorities ga
     *     where gm.username = ?
     *     and g.id = ga.group_id
     *     and g.id = gm.group_id
     * 上述是Spring Security的默认配置，但用户可自定义
     * usersByUsernameQuery(sql语句)，设置自定义的读取用户名、密码语句
     * authoritiesByUsernameQuery(sql语句)，设置自定义的依据用户名读取权限
     * 存在用户组的话，可以使用groupAuthoritiesByUsername(),重写组权限查询
     * 在将默认 SQL 查询替换为自己设计的查询时，一定要遵守查询的基本约定。它们都以用户名作为唯一参数。
     * 身份验证查询选择用户名、密码和启用状态；授权查询选择包含用户名和授予的权限的零个或多个行的数据；
     * 组权限查询选择零个或多个行数据，每个行有一个 group id、一个组名和一个权限
     * 使用编码密码，使用passwordEncoder(PasswordEncoder接口)加密，可自定义PasswordEncoder实现，或者使用现成的
     * public interface PasswordEncoder {
     *     String encode(CharSequence rawPassword);
     *     boolean matches(CharSequence rawPassword, String encodedPassword);
     * }
     * 数据库中的密码永远不会被解码。相反，用户在登录时输入的密码使用相同的算法进行编码，
     * 然后将其与数据库中编码的密码进行比较。
     * 比较是在 PasswordEncoder 的 matches() 方法中执行的

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from Users"
                +"where username=?")
                .authoritiesByUsernameQuery("select username,authority from UserAuthorities"
                        +"where username=?")
                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
    }*/
    /**
     * 使用LDAP(轻量级目录访问协议)接入用户存储
     *userSearchFilter() 和 groupSearchFilter() 方法用于为基本 LDAP 查询提供过滤器，这些查询用于搜索用户和组
     * 默认情况下，用户和组的基本查询都是空的，这表示将从 LDAP 层次结构的根目录进行搜索。但你可以通过指定一个查询基数
     * 指定base，即查询开始的地方。
     * 这个示例不是从根目录进行搜索，而是指定要搜索用户所在的组织单元是 people，组应该搜索组织单元所在的 group
     * 配置密码比较
     * 针对 LDAP 进行身份验证的默认策略是执行绑定操作，将用户通过 LDAP 服务器直接进行验证。
     * 另一种选择是执行比较操作，这包括将输入的密码发送到 LDAP 目录，并要求服务器将密码与用户的密码属性进行比较。
     * 因为比较是在 LDAP 服务器中进行的，所以实际的密码是保密的
     *希望通过密码比较进行身份验证，可以使用 passwordCompare() 方法进行声明
     * 默认情况下，登录表单中给出的密码将与用户 LDAP 条目中的 userPassword 属性值进行比较。
     * 如果密码保存在不同的属性中，可以使用 passwordAttribute() 指定密码属性的名称
     * 引用远程LDAP服务器
     * 默认情况下，Spring Security 的 LDAP 身份验证假设 LDAP 服务器正在本地主机上监听端口 33389。
     * 但是，如果 LDAP 服务器位于另一台机器上，则可以使用 contextSource() 方法来配置位置
     * 如果没有 LDAP 服务器去做身份验证，Spring Security 可提供一个嵌入式 LDAP 服务器。
     * 可以通过 root() 方法为嵌入式服务器指定根后缀，而不是将 URL 设置为远程 LDAP 服务器
     * 嵌入式服务器，将尝试从类路径中找到的任何 LDIF 文件进行数据加载。
     * LDIF（LDAP 数据交换格式）是在纯文本文件中表示 LDAP 数据的标准方法，每个记录由一个或多个行组成，每个行包含一个 name:value 对，
     * 记录之间用空行分隔。
     *

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                    .userSearchBase("ou=people")
                    .userSearchFilter("(uid={0})")
                    .groupSearchBase("ou=groups")
                    .groupSearchFilter("member={0}")
                    .passwordCompare()
                    .passwordEncoder(new BCryptPasswordEncoder())
                //指定LDAP中密码字段属性名
                    .passwordAttribute("passcode");

    }
    */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }
    @Bean
    public PasswordEncoder encoder(){
        return new StandardPasswordEncoder("53cr3t");
    }

    /**需要配置安全规则，开放部分请求
     * 方法接受HttpSecurity对象，可使用该对象处置如何在web级别处理安全性
     * 可配置属性包含
     * 1、在允许服务请求之前，满足特定的安全条件
     * 2、配置自定义登录界面
     * 3、使用户可以退出用户程序
     * 4、配置跨站请求伪造保护
     * authorizeRequests()的调用返回一个对象（ExpressionInterceptUrlRegistry）
     * 可在该对象上指定URL路径和模式以及这些路径的安全需求
     * 安全规则声明越早，优先级越高
     * 安全规则设定，access（）方法可以使用SpEL，完成复杂有趣的访问配置
     * and()方法表示已经完成了授权配置，但需要一些额外的HTTP配置
     * and()方法可以多次使用
     * 调用 formLogin() 开始配置自定义登录表单。之后对 loginPage() 的调用指定了将提供自定义登录页面的路径。
     * 当 Spring Security 确定用户未经身份验证并且需要登录时，它将把用户重定向到此路径。
     * 需要提供一个控制器来处理该路径上的请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/**").permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")//指定登录页是“/login”
                    .defaultSuccessUrl("/list")//登录成功后默认跳转到“list”
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/home")//退出登录后的默认url是/home
                    .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题,静态资源一般设置为resource/static下的路径
        web.ignoring().antMatchers("/**");
    }
    /**
     * 认证之后，主体对象的获取
     * 将主体对象注入控制器方法，可使用Principal对象
     * 将身份验证对象Authentication对象注入控制器方法，
     * 通过SecurityContext获取安全上下文
     * SecurityContextHolder.getContext().getAuthentication().getPrincipal()
     * 使用@AuthenticationPrincipal注解方法参数
     */
}
