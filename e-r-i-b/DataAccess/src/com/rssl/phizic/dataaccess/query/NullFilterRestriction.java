package com.rssl.phizic.dataaccess.query;

import org.hibernate.Session;

/**
 * @author Evgrafov
 * @ created 24.08.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1923 $
 */

public class NullFilterRestriction implements FilterRestriction
{
	public static final FilterRestriction INSTANCE = new NullFilterRestriction();
	private NullFilterRestriction()
	{
	}

	public void applyFilter(Session session)
	{
	}
}