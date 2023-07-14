package com.rssl.phizic.messaging;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * ������ �������� ���-���������
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public interface MessagingEngine extends Engine
{
	/**
	 * ������ ���-���������� ��� ���������� �������
	 * @param person - ������ (never null)
	 * @return ����� ���-��������� (never null)
	 */
	PersonSmsMessanger createPersonSmsMessanger(Person person);
}
