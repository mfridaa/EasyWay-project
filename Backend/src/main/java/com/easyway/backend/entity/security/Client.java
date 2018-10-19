package com.easyway.backend.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Client {
	
	@Id
	@Column(name = "CLIENT_ID")
	private String cliend_id;
	
	@Column(name = "RESOURCE_IDS")
	private String resource_id;
	
	@Column(name = "CLIENT_SECRET")
	private String client_secret;
	
	@Column(name = "SCOPE")
	private String scope;
	
	@Column(name = "AUTHORIZED_GRANT_TYPES")
	private String authorizedGrandTypes;
	
	@Column(name = "AUTHORITIES")
	private String authorities;
	
	@Column(name = "ACCESS_TOKEN_VALIDITY")
	private int accesTokenValidity;
	
	@Column(name = "REFRESH_TOKEN_VALIDITY")
	private int refreshTokenValidity;
	
}
