package com.rssl.phizic.bankroll;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ���������� ���������
 */
public interface BankrollEngine extends Engine
{
	/**
	 * ������ ��������-��������� ��� ���������� �������
	 * @param person - ������ (never null)
	 * @return ����� ��������-�������� (never null)
	 */
	PersonBankrollManager createPersonBankrollManager(Person person);
}
