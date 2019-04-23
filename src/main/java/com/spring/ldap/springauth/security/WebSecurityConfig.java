package com.spring.ldap.springauth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

import com.spring.ldap.springauth.model.LdapAuthStructure;

@Configuration
@EnableWebSecurity
@ComponentScan("com.spring.ldap.springauth.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private LdapAuthStructure ldapAuthStructure;
 
  private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/js/**");
    web.ignoring().antMatchers("/css/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
     http.authorizeRequests()
      	.antMatchers("/").permitAll()
	    .anyRequest().fullyAuthenticated().and()
      .formLogin().loginPage("/login").permitAll()
	    .defaultSuccessUrl("/privatePage",true)
	    .failureUrl("/login?error=true")
	    .and()
	  .logout()
	  	.permitAll().logoutSuccessUrl("/login?logout=true");
    logger.info("configure method is called to make the resources secure ...");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
    authManagerBuilder.ldapAuthentication()
    .userDnPatterns(ldapAuthStructure.getUserDnPattern())
    .userSearchBase(ldapAuthStructure.getUserSearchBase())
    .contextSource()
      .url(ldapAuthStructure.getLdapUrl()+"/"+ldapAuthStructure.getLdapBase())
      .managerDn(ldapAuthStructure.getLdapManagerDn())
      .managerPassword(ldapAuthStructure.getLdapManagerPwd())
      .and()
    .passwordCompare()
      .passwordEncoder(new LdapShaPasswordEncoder())
      .passwordAttribute("userPassword");
    
    logger.info("configure method is called to build Authentication manager ...");
  }  
  
}