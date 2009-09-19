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

package br.com.arsmachina.accesslogger.services;

import java.io.IOException;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

/**
 * Tapestry 5 request filter that listens to the page accesses (visits).
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class AccessLoggerRequestFilter implements RequestFilter {

	private AccessLoggerHub accessLoggerHub;

	/**
	 * Name of the request attribute used to store the original, non-rewritten request.
	 */
	public static final String ORIGINAL_REQUEST_ATTRIBUTE = "AccessLoggerRequestFilter.originalRequest";
	
	/**
	 * Single constructor.
	 * 
	 * @param accessLoggerHub an {@link AccessLoggerHub}. It cannot be null.
	 */
	public AccessLoggerRequestFilter(AccessLoggerHub accessLoggerHub) {

		if (accessLoggerHub == null) {
			throw new IllegalArgumentException("Parameter accessLoggerHub cannot be null");
		}

		this.accessLoggerHub = accessLoggerHub;

	}

	public boolean service(Request request, Response response, RequestHandler handler)
			throws IOException {

		request.setAttribute(ORIGINAL_REQUEST_ATTRIBUTE, request);
		boolean handled = handler.service(request, response);
		accessLoggerHub.notifyAccess();

		return handled;

	}

}
