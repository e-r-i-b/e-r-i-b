package com.rssl.phizic.security;

import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������ ������������� ��� ���-������ ����
 */
public class SmsConfirmEngine extends ConfirmEngineImpl
{
	/**
	 * ctor
	 * @param manager - �������� �������
	 */
	public SmsConfirmEngine(EngineManager manager)
	{
		super(manager);
	}

	@Override
	public PersonConfirmManager createPersonConfirmManager(Person person)
	{
		return new SmsPersonConfirmManager(getModule(), person);
	}
}
