/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.gitpusher.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class UserAccount.
 *
 * @author Debadatta Mishra
 */
@Slf4j
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class UserAccount {

	/** The user name. */
	private String userName;

	/** The password. */
	private String password;
}
