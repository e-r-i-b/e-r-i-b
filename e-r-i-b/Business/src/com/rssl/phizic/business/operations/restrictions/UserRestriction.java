package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.FilterRestriction;

/**
 * ����������� �� ������ � ��������������
 * @author Evgrafov
 * @ created 24.08.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */
public interface UserRestriction extends Restriction, FilterRestriction
{
	/**
	 * �������� ����������� ������ � �������������
	 * @param person ������������ ��� ��������
	 */
	boolean accept(Person person) throws BusinessException;

}
