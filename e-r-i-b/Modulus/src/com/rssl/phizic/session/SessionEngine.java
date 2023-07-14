package com.rssl.phizic.session;

import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ������ ������������
 * ����� ���������/�������� ������ ������������
 */
public interface SessionEngine
{
	/**
	 * ����������/������ ������ �������
	 * @param person - ������
	 * @param createIfNone - ������ "��������� ������, ���� �� �������"
	 * @return ������ ��� null, ���� <createIfNone>=false � ������� ������ �� �������
	 * �����! ���������/��������� ������ ����� �������� � ���������� ������ (����-�����)
	 * (����������� WorkManager.setSession)
	 */
	PersonSession getSession(Person person, boolean createIfNone);

	/**
	 * ������� ������
	 * @param session - ������, ������� ����� ������� (can be null)
	 */
	void destroySession(PersonSession session);
}
