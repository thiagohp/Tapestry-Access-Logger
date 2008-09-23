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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tapestry5.services.ComponentClassResolver;

import br.com.arsmachina.accesslogger.Access;
import br.com.arsmachina.accesslogger.services.AccessFactory;
import br.com.arsmachina.accesslogger.services.AccessLoggerRequestFilter;
import br.com.arsmachina.authentication.entity.User;

/**
 * Default {@link AccessFactory} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (ThiagoHP)
 */
public class AccessFactoryImpl implements AccessFactory {

	private ComponentClassResolver componentClassResolver;

	/**
	 * Single constructor of this class.
	 * 
	 * @param componentClassResolver a {@link ComponentClassResolver}. It cannot be null.
	 */
	public AccessFactoryImpl(ComponentClassResolver componentClassResolver) {

		if (componentClassResolver == null) {
			throw new IllegalArgumentException("Parameter componentClassResolver cannot be null");
		}

		this.componentClassResolver = componentClassResolver;

	}

	/**
	 * A template method that calls {@link #createObject()} and
	 * {@link #fill(Access, HttpServletRequest)}.
	 * 
	 * @see br.com.arsmachina.accesslogger.services.AccessFactory#create(javax.servlet.http.HttpServletRequest)
	 * @return an {@link Access} object.
	 */
	final public Access create(HttpServletRequest request) {

		Access access = createObject();
		
		fill(access, request);

		return access;

	}

	/**
	 * Fills the newly-created {@link Access} instance.
	 * @param access an {@link Access}.
	 * @param request a {@link HttpServletRequest}.
	 */
	protected void fill(Access access, HttpServletRequest request) {

		User user = loggedUser(request);

		access.setUser(user);
		access.setContextPath(request.getContextPath());
		access.setIp(request.getRemoteAddr());
		access.setUserAgent(request.getHeader("User-Agent"));
		access.setRemoteHost(request.getRemoteHost());
		setLocale(access, request);
		access.setQueryString(request.getQueryString());

		setReferrer(request, access);
		setPageAndActivationContext(access, request);
		setUrl(request, access);

		HttpSession session = request.getSession(false);

		if (session != null && request.isRequestedSessionIdValid()) {
			access.setSessionId(session.getId());
		}
		
	}

	/**
	 * @param access
	 * @param request
	 */
	private void setLocale(Access access, HttpServletRequest request) {
		
		String acceptLanguage = request.getHeader("Accept-Language");
		
		if (acceptLanguage != null && acceptLanguage.indexOf(';') >= 0) {
			acceptLanguage = acceptLanguage.substring(0, acceptLanguage.indexOf(';'));
		}
		
		access.setLocale(acceptLanguage);
		
	}

	/**
	 * Method used by {@link #create(HttpServletRequest)} to get the logged user.
	 * 
	 * @param request a {@link HttpServletRequest}.
	 * @return an {@link User} or <code>null</code>.
	 */
	protected User loggedUser(HttpServletRequest request) {
		return (User) request.getAttribute(AccessLoggerRequestFilter.LOGGED_USER_REQUEST_ATTRIBUTE);
	}

	/**
	 * Creates the object. Can be overwritten by subclasses to create {@link Access} subclasses.
	 * 
	 * @return an {@link Access}.
	 */
	protected Access createObject() {
		return new Access();
	}

	/**
	 * @param httpServletRequest
	 * @param access
	 */
	private void setReferrer(HttpServletRequest httpServletRequest, Access access) {

		String referer = httpServletRequest.getHeader("Referer");

		if (referer != null) {
			referer = removeJsessionid(referer);
		}

		access.setReferer(referer);

	}

	/**
	 * @param referer
	 * @return
	 */
	private String removeJsessionid(String referer) {
		int index = referer.indexOf(";jsessionid=");
		if (index >= 0) {
			referer = referer.substring(0, index);
		}
		return referer;
	}

	/**
	 * @param request
	 * @param httpServletRequest
	 * @param access
	 */
	private void setUrl(HttpServletRequest request, Access access) {
		StringBuilder url = new StringBuilder();

		int port = -1;
		int requestPort = request.getLocalPort();

		if (request.isSecure()) {

			url.append("https");

			if (requestPort != 443) {
				port = requestPort;
			}

		}
		else {

			url.append("http");

			if (requestPort != 80) {
				port = requestPort;
			}

		}
		url.append("://");
		url.append(request.getServerName());

		if (port != -1) {
			url.append(":");
			url.append(port);
		}

		url.append(access.getContextPath());
		url.append(request.getServletPath());

		String queryString = request.getQueryString();
		if (queryString != null) {
			url.append("?");
			url.append(queryString);
		}

		access.setUrl(url.toString());

	}

	/**
	 * Sets the page and activation context properties of a given {@link Access} object.
	 * 
	 * @param access an {@link Access}. It cannot be null.
	 * @param request a {@link HttpServletRequest}. It cannot be null.
	 */
	void setPageAndActivationContext(Access access, HttpServletRequest request) {

		assert access != null;
		assert request != null;

		// copied from RequestImpl.getPath()

		String path = request.getPathInfo();

		if (path == null) {
			path = request.getServletPath();
		}

		// Websphere 6.1 is a bit wonky (see TAPESTRY-1713), and tends to return the empty string
		// for the servlet path, and return the true path in pathInfo.

		path = path.length() == 0 ? "/" : path;
		path = path.trim();

		if (path.equals("/")) {
			access.setPage(path);
			access.setActivationContext(null);
		}
		else {

			String extendedName = path.length() == 0 ? path : path.substring(1);

			// Copied and adapted from Tapestry's PageRenderDispatcher

			// Ignore trailing slashes in the path.
			while (extendedName.endsWith("/")) {
				extendedName = extendedName.substring(0, extendedName.length() - 1);
			}

			int slashx = extendedName.length();
			boolean atEnd = true;

			while (slashx > 0) {

				String pageName = extendedName.substring(0, slashx);
				String pageActivationContext = atEnd ? "" : extendedName.substring(slashx + 1);

				if (componentClassResolver.isPageName(pageName)) {

					access.setPage("/" + pageName);
					access.setActivationContext(pageActivationContext);
					break;

				}

				// Work backwards, splitting at the next slash.
				slashx = extendedName.lastIndexOf('/', slashx - 1);

				atEnd = false;

			}

		}

	}

}
