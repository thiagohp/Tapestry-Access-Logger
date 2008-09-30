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

import br.com.arsmachina.accesslogger.services.AccessFilter;
import br.com.arsmachina.accesslogger.services.AccessFilterRule;

/**
 * Default {@link AccessFilterRule} implementation, being the last one to be used by
 * {@link AccessFilter}.
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class DefaultAccessFilterRule implements AccessFilterRule {

	/**
	 * @see br.com.arsmachina.accesslogger.services.AccessFilterRule#accept(java.lang.String)
	 */
	public Boolean accept(String path) {

		assert path != null;
		
		if (path.length() == 0 || path.equals("/")) {
			return true;
		}
		
		int lastSlash = path.lastIndexOf('/');
		int lastDot = path.lastIndexOf('.');

		return lastSlash > lastDot;
		
	}

}
