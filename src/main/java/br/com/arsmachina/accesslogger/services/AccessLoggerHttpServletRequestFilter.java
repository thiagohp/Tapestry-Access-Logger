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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;


/**
 * Tapestry 5 request filter that listens to the page accesses (visits).
 * 
 * @author Thiago H. de Paula Figueiredo (ThiagoHP)
 */
public class AccessLoggerHttpServletRequestFilter implements HttpServletRequestFilter {

	private AccessLoggerHub accessLoggerHub;

	/**
	 * Single constructor.
	 * 
	 * @param accessLoggerHub an {@link AccessLoggerHub}. It cannot be null.
	 */
	public AccessLoggerHttpServletRequestFilter(AccessLoggerHub accessLoggerHub) {

		if (accessLoggerHub == null) {
			throw new IllegalArgumentException("Parameter accessLoggerHub cannot be null");
		}

		this.accessLoggerHub = accessLoggerHub;

	}

	public boolean service(HttpServletRequest request, HttpServletResponse response,
			HttpServletRequestHandler handler) throws IOException {
		
		boolean handled = handler.service(request, response);
		accessLoggerHub.notifyAccess(request);
		
		return handled;
		
	}
	
}
