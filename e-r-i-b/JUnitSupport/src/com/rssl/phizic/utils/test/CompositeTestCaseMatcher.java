package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.ListUtil;

import java.util.List;

/**
 * @author Roshka
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class CompositeTestCaseMatcher<O> implements TestCaseMatcher<O>
{
	private List<TestCaseMatcher> matchers;

	public CompositeTestCaseMatcher(TestCaseMatcher... filters)
	{
		this.matchers = ListUtil.fromArray(filters);
	}

	public boolean match(O object)
	{
		for (TestCaseMatcher filter : matchers)
		{
			if( !filter.match(object) )
				return false;
		}
		return true;
	}
}