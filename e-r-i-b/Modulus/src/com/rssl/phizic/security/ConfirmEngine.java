package com.rssl.phizic.security;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������������
 */
public interface ConfirmEngine extends Engine
{
	/**
	 * ������ ������������� ��������� �� ��������������
	 * @param person - ������
	 * @return ����� �������� �������������
	 */
	PersonConfirmManager createPersonConfirmManager(Person person);
}
