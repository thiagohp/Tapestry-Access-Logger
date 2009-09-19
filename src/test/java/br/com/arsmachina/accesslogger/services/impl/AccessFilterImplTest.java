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

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.arsmachina.accesslogger.services.AccessFilter;
import br.com.arsmachina.accesslogger.services.AccessFilterRule;


/**
 * Test class for {@link AccessFilterImpl}.
 * 
 * @author Thiago H. de Paula Figueiredo
 */
public class AccessFilterImplTest {

	private static final String ENDING = ".jar";
	
	private static final String OTHER_ENDING = ".bar";

	@Test
	public void testAccept() {
	
		URLEndingAccessFilterRule rule = new URLEndingAccessFilterRule(ENDING);

		List<AccessFilterRule> rules = new ArrayList<AccessFilterRule>();
		rules.add(rule);
		
		AccessFilter filter = new AccessFilterImpl(rules);
		
		Assert.assertTrue(filter.accept("/"));
		Assert.assertTrue(filter.accept("/adfasdfasdf"));
		Assert.assertTrue(filter.accept("/adfasdfasdf/asdfasdfasdf"));
		Assert.assertTrue(filter.accept("/adfasdfa.sdf/asdfasdfasdf"));
		Assert.assertTrue(filter.accept("/adfasdfa.sdf/asdfasdf.asd/"));
		Assert.assertTrue(filter.accept("/adfasdfa.sdf/asdfasdf.asd/adfasdf"));
		
		Assert.assertTrue(filter.accept("/adfasdfa" + ENDING));
		Assert.assertTrue(filter.accept("/adfasdfasdf/asdfasdfa" + ENDING));
		Assert.assertTrue(filter.accept("/adfasdfasdf/asdfasdfa.sdf" + ENDING));

		Assert.assertFalse(filter.accept("/adfasdfa" + OTHER_ENDING));
		Assert.assertFalse(filter.accept("/adfasdfasdf/asdfasdfa" + OTHER_ENDING));
		Assert.assertFalse(filter.accept("/adfasdfasdf/asdfasdfa.sdf" + OTHER_ENDING));

	}

}
