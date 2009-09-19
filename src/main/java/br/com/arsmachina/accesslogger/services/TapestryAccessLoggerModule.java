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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;

import br.com.arsmachina.accesslogger.AccessLogger;
import br.com.arsmachina.accesslogger.impl.Slf4JAccessLogger;
import br.com.arsmachina.accesslogger.services.impl.AccessFactoryImpl;
import br.com.arsmachina.accesslogger.services.impl.AccessFilterImpl;
import br.com.arsmachina.accesslogger.services.impl.AccessLoggerHubImpl;

/**
 * Tapestry-IoC module class.
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class TapestryAccessLoggerModule {

	/**
	 * Bind the {@link AccessLoggerRequestFilter} and {@link AccessFactory} services.
	 * 
	 * @param binder
	 */
	public static void bind(ServiceBinder binder) {
		binder.bind(Slf4JAccessLogger.class);
		binder.bind(AccessLoggerRequestFilter.class);
		binder.bind(AccessFactory.class, AccessFactoryImpl.class);
	}

	/**
	 * Creates an {@link AccessFilter}.
	 * 
	 * @param filters a {@link List} of {@link AccessFilterRule}s.
	 * @return an {@link AccessFilter}.
	 */
	public static AccessFilter buildAccessFilter(final List<AccessFilterRule> filters,
			ChainBuilder chainBuilder) {
		return new AccessFilterImpl(filters);
	}

	/**
	 * Creates an {@link AccessLoggerHub}.
	 * 
	 * @param loggers a {@link List} of {@link AccessLogger}s.
	 * @return an {@link AccessLoggerHub}.
	 */
	public static AccessLoggerHub buildAccessLoggerHub(final List<AccessLogger> loggers,
			AccessFactory accessFactory, AccessFilter accessFilter,
			HttpServletRequest httpServletRequest) {

		return new AccessLoggerHubImpl(loggers, accessFactory, accessFilter, httpServletRequest);

	}

	/**
	 * Contributes the {@link AccessLoggerRequestFilter} to the {@link RequestHandler}
	 * service.
	 * 
	 * @param configuration an {@link OrderedConfiguration}.
	 * @param filter an {@link AccessLoggerRequestFilter}.
	 */
	 public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration, 
			 AccessLoggerRequestFilter filter) {

		configuration.add("logger", filter, "before:URLRewriter");

	}

}
