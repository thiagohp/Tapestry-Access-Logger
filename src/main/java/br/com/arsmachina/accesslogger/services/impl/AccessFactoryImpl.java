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

package br.com.arsmachina.accesslogger.services.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentEventLinkEncoder;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;

import br.com.arsmachina.accesslogger.Access;
import br.com.arsmachina.accesslogger.services.AccessFactory;
import br.com.arsmachina.accesslogger.services.AccessLoggerRequestFilter;
import br.com.arsmachina.authentication.entity.User;

/**
 * Default {@link AccessFactory} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class AccessFactoryImpl implements AccessFactory {

//	final private Request request;

	final private HttpServletRequest httpServletRequest;

	final private ComponentEventLinkEncoder componentEventLinkEncoder;

	final private ApplicationStateManager applicationStateManager;

	/**
	 * Single constructor of this class.
	 *
	 * @param request a {@link Request}. It cannot be null.
	 * @param httpServletRequest an {@link HttpServletRequest}. It cannot be null.
	 * @param componentEventLinkEncoder a {@link ComponentEventLinkEncoder}. It cannot be null.
	 * @param applicationStateManager an {@link ApplicationStateManager}. It cannot be null.
	 */
	public AccessFactoryImpl(Request request, HttpServletRequest httpServletRequest,
			ComponentEventLinkEncoder componentEventLinkEncoder,
			ApplicationStateManager applicationStateManager) {

		if (request == null) {
			throw new IllegalArgumentException("Parameter request cannot be null.");
		}

		if (httpServletRequest == null) {
			throw new IllegalArgumentException("Parameter httpServletRequest cannot be null.");
		}

		if (componentEventLinkEncoder == null) {
			throw new IllegalArgumentException("Parameter componentEventLinkEncoder cannot be null");
		}

		if (applicationStateManager == null) {
			throw new IllegalArgumentException("Parameter applicationStateManager cannot be null.");
		}

		this.componentEventLinkEncoder = componentEventLinkEncoder;
//		this.request = request;
		this.httpServletRequest = httpServletRequest;
		this.applicationStateManager = applicationStateManager;

	}

	/**
	 * A template method that calls {@link #createObject()} and {@link #fill(Access)}.
	 * 
	 * @see br.com.arsmachina.accesslogger.services.AccessFactory#create()
	 * @return an {@link Access} object.
	 */
	final public Access create() {

		Access access = createObject();

		fill(access);

		return access;

	}

	/**
	 * Fills the newly-created {@link Access} instance.
	 * 
	 * @param access an {@link Access}.
	 */
	protected void fill(Access access) {

		User user = applicationStateManager.getIfExists(User.class);

		access.setUser(user);
		access.setContextPath(httpServletRequest.getContextPath());
		access.setIp(httpServletRequest.getRemoteAddr());
		access.setUserAgent(httpServletRequest.getHeader("User-Agent"));
		access.setRemoteHost(httpServletRequest.getRemoteHost());
		setLocale(access);
		access.setQueryString(httpServletRequest.getQueryString());

		setReferrer(access);
		setPageAndActivationContext(access);
		setUrl(access);

		HttpSession session = httpServletRequest.getSession(false);

		if (session != null && httpServletRequest.isRequestedSessionIdValid()) {
			access.setSessionId(session.getId());
		}

	}

	private void setLocale(Access access) {

		String acceptLanguage = httpServletRequest.getHeader("Accept-Language");

		if (acceptLanguage != null && acceptLanguage.indexOf(';') >= 0) {
			acceptLanguage = acceptLanguage.substring(0, acceptLanguage.indexOf(';'));
		}

		access.setLocale(acceptLanguage);

	}

	/**
	 * Creates the object. Can be overwritten by subclasses to create {@link Access} subclasses.
	 * 
	 * @return an {@link Access}.
	 */
	protected Access createObject() {
		return new Access();
	}

	private void setReferrer(Access access) {

		String referer = httpServletRequest.getHeader("Referer");

		if (referer != null) {
			referer = removeJsessionid(referer);
		}

		access.setReferer(referer);

	}

	private String removeJsessionid(String url) {
		int index = url.indexOf(";jsessionid=");
		if (index >= 0) {
			url = url.substring(0, index);
		}
		return url;
	}

	private void setUrl(Access access) {
		StringBuilder url = new StringBuilder();

		int port = -1;
		int requestPort = httpServletRequest.getLocalPort();

		if (getOriginalRequest().isSecure()) {

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
		url.append(httpServletRequest.getServerName());

		if (port != -1) {
			url.append(":");
			url.append(port);
		}

		url.append(access.getContextPath());
		url.append(httpServletRequest.getServletPath());

		String queryString = httpServletRequest.getQueryString();
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
	void setPageAndActivationContext(Access access) {

		assert access != null;
		assert httpServletRequest != null;

		Request originalRequest = getOriginalRequest();
		PageRenderRequestParameters prrr = componentEventLinkEncoder.decodePageRenderRequest(originalRequest);
		
		if (prrr != null) {
			
			access.setPage(prrr.getLogicalPageName());
			EventContext activationContext = prrr.getActivationContext();
			int count = activationContext.getCount();
			
			if (count > 0) {

				StringBuilder builder = new StringBuilder();
				
				for (int i = 0; i < count - 1; i++) {
					builder.append(activationContext.get(String.class, i));
					builder.append('/');
				}
				
				builder.append(activationContext.get(String.class, count - 1));
				
				access.setActivationContext(builder.toString());
				
			}
			
		}

	}
	
	protected Request getOriginalRequest() {
		return (Request) httpServletRequest.getAttribute(AccessLoggerRequestFilter.ORIGINAL_REQUEST_ATTRIBUTE);
	}

}
