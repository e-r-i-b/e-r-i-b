package com.rssl.phizic.interactive;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������������� � �������������
 */
public interface UserInteractEngine extends Engine
{
	/**
	 * ������ ����������-��������� ��� ���������� �������
	 * @param person - ������ (never null)
	 * @return ����� ����������-�������� (never null)
	 */
	PersonInteractManager createPersonInteractManager(Person person);
}
