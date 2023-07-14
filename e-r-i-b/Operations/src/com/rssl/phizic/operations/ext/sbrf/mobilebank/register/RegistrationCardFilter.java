package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.*;

/**
 * @author Erkin
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �����, �� ���������� ��� ����������� � ��
 * ����� �������, �� �����������:
 *  - �������� ����,
 *  - ��������������� ����,
 *  - �������������� ���� �������,
 *  - �������������,
 *  - � �������� ������ ��������,
 *  - �������� �� ������ ���������� ����.
 * �� ������ ������ ����������� ����������� � �� ��������� ����
 */
class RegistrationCardFilter extends CardFilterConjunction
{
	RegistrationCardFilter(Person person)
	{
		// �� ����������� �������� ����, ��������������� ����
		addFilter(new ActiveCardFilter());

		// �� ����������� �������������� ����
		addFilter(new MainCardFilter());

		// �� ����������� ���� � �������� ������ ��������
		addFilter(new NotExpiredCardFilter());

		// �� ����������� ����, �������� �� ������ ���������� ����
		addFilter(new CardOwnFilter(person));
	}
}
