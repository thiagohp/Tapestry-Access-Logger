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

import java.util.List;

import br.com.arsmachina.accesslogger.services.AccessFilter;
import br.com.arsmachina.accesslogger.services.AccessFilterRule;

/**
 * Default {@link AccessFilter} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (ThiagoHP)
 */
public class AccessFilterImpl implements AccessFilter {

	final private List<AccessFilterRule> rules;

	/**
	 * Single constructor of this class.
	 * 
	 * @param rules a {@link List} of {@link AccessFilterRule}s. It cannot be null.
	 */
	public AccessFilterImpl(List<AccessFilterRule> rules) {

		if (rules == null) {
			throw new IllegalArgumentException("Parameter rules cannot be null");
		}

		this.rules = rules;
		rules.add(new DefaultAccessFilterRule());

	}

	/**
	 * @see br.com.arsmachina.accesslogger.services.AccessFilter#accept(java.lang.String)
	 */
	public boolean accept(String path) {

		Boolean response = null;

		for (AccessFilterRule rule : rules) {

			response = rule.accept(path);
			if (response != null) {
				break;
			}

		}

		assert response != null;

		return response;

	}

}
