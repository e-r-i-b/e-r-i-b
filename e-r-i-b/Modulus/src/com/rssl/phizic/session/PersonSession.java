package com.rssl.phizic.session;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.store.Store;

/**
 * @author Erkin
 * @ created 29.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������
 */
public interface PersonSession extends Store
{
	/**
	 * @return ������ (never null)
	 */
	Person getPerson();
}
