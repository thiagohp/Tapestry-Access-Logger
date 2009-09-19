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

import br.com.arsmachina.accesslogger.services.AccessFilterRule;

/**
 * {@link AccessFilterRule} that accepts requests which ends with a given string
 * and ignores the others.
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class URLEndingAccessFilterRule implements AccessFilterRule {

	final private String ending;

	/**
	 * Single constructor of this class. 
	 * @param ending a {@link String} containing the request ending. It cannot be null
	 * nor the empty string.
	 */
	public URLEndingAccessFilterRule(String ending) {
		
		if (ending == null) {
			throw new IllegalArgumentException("Parameter ending cannot be null");
		}

		ending = ending.trim();
		
		if (ending.length() == 0) {
			throw new IllegalArgumentException("Parameter ending cannot the empty string");
		}
		
		this.ending = ending;
		
	}

	/**
	 * @see br.com.arsmachina.accesslogger.services.AccessFilterRule#accept(java.lang.String)
	 */
	public Boolean accept(String path) {
		
		if (path.endsWith(ending)) {
			return true;
		}
		else {
			return null;
		}
		
	}

}
