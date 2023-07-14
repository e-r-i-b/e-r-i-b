package com.rssl.phizic.dataaccess.query;

import org.hibernate.Session;

/**
 * @author Evgrafov
 * @ created 24.08.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1918 $
 */

public interface FilterRestriction
{
	/**
	 * ���������� �������� � ������� ���������� ������������
	 * @param session hibernate session
	 */
	void applyFilter(Session session);
}
