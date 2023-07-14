package com.rssl.phizic.auth;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * @author Erkin
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��������������
 */
public interface AuthEngine extends Engine
{
	/**
	 * ����������������� ������� �� ������ ��������
	 * @param phone - ����� ��������
	 * @return ���������� ������ (never null)
	 * @throws UserErrorException - ������ ��������������, ������� ��� ������ ������������
	 * @throws InternalErrorException - ��������� ����
	 */
	PersonSession authenticateByPhone(PhoneNumber phone);

	/**
	 * ����������������� �������
	 * @param person - ������
	 * @return ���������� ������ (never null)
	 * @throws UserErrorException - ������ ��������������, ������� ��� ������ ������������
	 * @throws InternalErrorException - ��������� ����
	 */
	PersonSession authenticateByPerson(Person person);
}
