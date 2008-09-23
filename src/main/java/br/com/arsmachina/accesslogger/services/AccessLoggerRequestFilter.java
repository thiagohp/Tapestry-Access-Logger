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

package br.com.arsmachina.accesslogger.services;

import java.io.IOException;

import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import br.com.arsmachina.authentication.entity.User;


/**
 * Tapestry 5 request filter that sets the .
 * 
 * @author Thiago H. de Paula Figueiredo (ThiagoHP)
 */
public class AccessLoggerRequestFilter implements RequestFilter {
	
	public static final String LOGGED_USER_REQUEST_ATTRIBUTE = "tapestry-access-logger.loggedUser";

	private ApplicationStateManager applicationStateManager;

	/**
	 * Single constructor of this class.
	 * @param applicationStateManager an {@link ApplicationStateManager}. It cannot be null.
	 */
	public AccessLoggerRequestFilter(ApplicationStateManager applicationStateManager) {
		
		if (applicationStateManager == null) {
			throw new IllegalArgumentException("Parameter applicationStateManager cannot be null");
		}
		
		this.applicationStateManager = applicationStateManager;
		
	}

	/**
	 * @see org.apache.tapestry5.services.RequestFilter#service(org.apache.tapestry5.services.Request,
	 * org.apache.tapestry5.services.Response, org.apache.tapestry5.services.RequestHandler)
	 */
	public boolean service(Request request, Response response, RequestHandler requestHandler)
			throws IOException {
		
		boolean handled = requestHandler.service(request, response);
		
		User user = applicationStateManager.getIfExists(User.class);
		request.setAttribute(LOGGED_USER_REQUEST_ATTRIBUTE, user);
		
		return handled;
		
	}

}
