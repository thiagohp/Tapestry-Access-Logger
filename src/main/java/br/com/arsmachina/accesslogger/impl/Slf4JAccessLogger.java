// Copyright 2008-2009 Thiago H. de Paula Figueiredo
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package br.com.arsmachina.accesslogger.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arsmachina.accesslogger.Access;
import br.com.arsmachina.accesslogger.AccessLogger;
import br.com.arsmachina.authentication.entity.User;

/**
 * {@link AccessLogger} implementation using log4j.
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class Slf4JAccessLogger implements AccessLogger {

	final private static String SEPARATOR = "|";
	
	private Logger logger = LoggerFactory.getLogger(Slf4JAccessLogger.class);

//	/**
//	 * Single constructor of this class.
//	 * @param logger a {@link Logger}. It cannot be null.
//	 */
//	public Slf4JAccessLogger(Logger logger) {
//		
//		if (logger == null) {
//			throw new IllegalArgumentException("Parameter logger cannot be null");
//		}
//		
//		this.logger = logger;
//		
//	}

	/** 
	 * @see br.com.arsmachina.accesslogger.AccessLogger#log(br.com.arsmachina.accesslogger.Access)
	 */
	public void log(Access access) {

		User user = access.getUser();
		StringBuilder builder = new StringBuilder();
		
		String url = access.getUrl();
		
		append(builder, url);
		append(builder, access.getContextPath());
		append(builder, access.getPage());
		append(builder, access.getActivationContext());
		append(builder, access.getReferer());
		append(builder, access.getIp());
		append(builder, access.getSessionId());
		
		builder.append(user != null ? user.getLogin() : "");
		
		logger.info(builder.toString());
		
	}

	/**
	 * @param builder
	 * @param url
	 */
	private void append(StringBuilder builder, String url) {
		builder.append(url != null ? url : "");
		builder.append(SEPARATOR);
	}

}
