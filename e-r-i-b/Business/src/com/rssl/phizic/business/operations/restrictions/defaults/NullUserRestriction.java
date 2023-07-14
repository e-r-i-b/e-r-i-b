package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import org.hibernate.Session;

/**
 * @author Evgrafov
 * @ created 12.09.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */

public class NullUserRestriction implements UserRestriction
{
	public static final UserRestriction INSTANCE = new NullUserRestriction();

	public NullUserRestriction()
	{
	}

	public boolean accept(Person person)
	{
		return true;
	}

	public void applyFilter(Session session)
	{
	}
}