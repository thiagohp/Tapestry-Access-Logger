// Copyright 2008 Thiago H. de Paula Figueiredo
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

package br.com.arsmachina.accesslogger.services.impl;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.arsmachina.accesslogger.Access;
import br.com.arsmachina.accesslogger.AccessLogger;
import br.com.arsmachina.accesslogger.services.AccessFactory;
import br.com.arsmachina.accesslogger.services.AccessFilter;
import br.com.arsmachina.accesslogger.services.AccessLoggerHub;

/**
 * Default {@link AccessLoggerHub} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (ThiagoHP)
 */
public class AccessLoggerHubImpl implements AccessLoggerHub {

	private List<AccessLogger> loggers;

	private AccessFactory accessFactory;

	private AccessFilter accessFilter;

	/**
	 * Single constructor.
	 * 
	 * @param loggers a {@link List} of {@link AccessLogger}s. It cannot be null.
	 * @param accessFactory an {@link AccessFactory}. It cannot be null.
	 * @param accessFilter an {@link AccessFilter}. It cannot be null.
	 * @param request an {@link HttpServletRequest}. It cannot be null.
	 */
	public AccessLoggerHubImpl(List<AccessLogger> loggers, AccessFactory accessFactory,
			AccessFilter accessFilter, HttpServletRequest request) {

		if (loggers == null) {
			throw new IllegalArgumentException("Parameter loggers cannot be null");
		}

		if (accessFactory == null) {
			throw new IllegalArgumentException("Parameter accessFactory) cannot be null");
		}
		
		if (accessFilter == null) {
			throw new IllegalArgumentException("Parameter accessFilter cannot be null");
		}

		this.loggers = Collections.unmodifiableList(loggers);
		this.accessFactory = accessFactory;
		this.accessFilter = accessFilter;

	}

	/**
	 * @see br.com.arsmachina.accesslogger.services.AccessLoggerHub#getAccessLoggers()
	 */
	public List<AccessLogger> getAccessLoggers() {
		return loggers;
	}

	/**
	 * @see br.com.arsmachina.accesslogger.services.AccessLoggerHub#notifyAccess(HttpServletRequest)
	 */
	public void notifyAccess(HttpServletRequest request) {

		// copied from RequestImpl.getPath
		
        String path = request.getPathInfo();

        if (path == null) {
        	path = request.getServletPath();
        }

        // Websphere 6.1 is a bit wonky (see TAPESTRY-1713), and tends to return the empty string
        // for the servlet path, and return the true path in pathInfo.

        path = path.length() == 0 ? "/" : path;
		path = path.trim();

		if (accessFilter.accept(path)) {

			Access access = accessFactory.create(request);

			for (AccessLogger logger : loggers) {
				logger.log(access);
			}

		}

	}

}
