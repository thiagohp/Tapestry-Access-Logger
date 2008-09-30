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

/**
 * Interface that defines a single rule whether a request to a given path must be logged or not.
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public interface AccessFilterRule {

	/**
	 * Tells if a request to a given <code>path</code> must be logged or not. This method can
	 * return <code>null</code> to say it cannot decide to log to request or not, leaving the
	 * decision for other {@link AccessFilterRule}s.
	 * 
	 * @param path a {@link String}. It cannot be null.
	 * @return <code>true</code>, <code>false</code> or <code>null</code>.
	 */
	Boolean accept(String path);

}
