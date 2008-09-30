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

package br.com.arsmachina.accesslogger;

import java.util.Date;

import br.com.arsmachina.authentication.entity.User;

/**
 * Class that represents an user access (visit).
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class Access {

	private User user;

	private Date timestamp = new Date();

	private String contextPath;

	private String page;

	private String activationContext;

	private String queryString;

	private String sessionId;

	private String referer;

	private String ip;

	private String remoteHost;

	private String locale;

	private String url;

	private String userAgent;

	/**
	 * Default constructor.
	 */
	public Access() {
	}

	/**
	 * Returns the value of the <code>user</code> property.
	 * 
	 * @return a {@link User}.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Changes the value of the <code>user</code> property.
	 * 
	 * @param user a {@link User}.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the value of the <code>timestamp</code> property.
	 * 
	 * @return a {@link Date}.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Changes the value of the <code>timestamp</code> property.
	 * 
	 * @param timestamp a {@link Date}.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns the value of the <code>context</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * Changes the value of the <code>context</code> property.
	 * 
	 * @param contextPath a {@link String}.
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * Returns the value of the <code>page</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Changes the value of the <code>page</code> property.
	 * 
	 * @param page a {@link String}.
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * Returns the value of the <code>queryString</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * Changes the value of the <code>queryString</code> property.
	 * 
	 * @param queryString a {@link String}.
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * Returns the value of the <code>sessionId</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Changes the value of the <code>sessionId</code> property.
	 * 
	 * @param sessionId a {@link String}.
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Returns the value of the <code>referrer</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * Changes the value of the <code>referrer</code> property.
	 * 
	 * @param referrer a {@link String}.
	 */
	public void setReferer(String referrer) {
		this.referer = referrer;
	}

	/**
	 * Returns the value of the <code>activationContext</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getActivationContext() {
		return activationContext;
	}

	/**
	 * Changes the value of the <code>activationContext</code> property.
	 * 
	 * @param activationContext a {@link String}.
	 */
	public void setActivationContext(String activationContext) {

		if (activationContext == null || activationContext.trim().length() == 0) {
			activationContext = null;
		}

		this.activationContext = activationContext;

	}

	/**
	 * Returns the value of the <code>ip</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Changes the value of the <code>ip</code> property.
	 * 
	 * @param ip a {@link String}.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Returns the value of the <code>url</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Changes the value of the <code>url</code> property.
	 * 
	 * @param url a {@link String}.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the value of the <code>userAgent</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * Changes the value of the <code>userAgent</code> property.
	 * 
	 * @param userAgent a {@link String}.
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * Returns the value of the <code>remoteAddress</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * Changes the value of the <code>remoteHost</code> property.
	 * 
	 * @param remoteHost a {@link String}.
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	/**
	 * Returns the value of the <code>locale</code> property.
	 * 
	 * @return a {@link String}.
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Changes the value of the <code>locale</code> property.
	 * 
	 * @param locale a {@link String}.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

}
