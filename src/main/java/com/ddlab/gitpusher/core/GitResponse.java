/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.gitpusher.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class GitResponse.
 *
 * @author Debadatta Mishra
 */
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class GitResponse {

	/** The status code. */
	private String statusCode;

	/** The response text. */
	private String responseText;

}
