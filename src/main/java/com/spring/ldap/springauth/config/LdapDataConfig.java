package com.spring.ldap.springauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.spring.ldap.springauth.config" })
public class LdapDataConfig {
  
  @Value("${spring.ldap.urls}")
  private String ldapUrls;

  @Value("${spring.ldap.base}")
  private String ldapBase;

  @Value("${spring.ldap.password}")
  private String ldapManagerPwd;
    
  @Value("${spring.ldap.username}")
  private String ldapManagerUserName;
  
  @Bean("ldapAuthStructure")
  public LdapAuthStructure getLDAPAuthStructure() {
    LdapAuthStructure authStructure = new LdapAuthStructure();
    
    authStructure.setLdapUrl(ldapUrls);
    authStructure.setLdapBase(ldapBase);
    authStructure.setLdapManagerDn(ldapManagerUserName);
    authStructure.setLdapManagerPwd(ldapManagerPwd);
    authStructure.setUserDnPattern("uid={0},ou=users");
    authStructure.setUserSearchBase("ou=roles");

    return authStructure;
  }
}