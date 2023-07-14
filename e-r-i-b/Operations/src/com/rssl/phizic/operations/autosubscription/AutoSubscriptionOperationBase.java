package com.rssl.phizic.operations.autosubscription;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * ������� ����� �������� ��� ������ � ���������� ������������.
 * (��������� � ��� ���������� ������������ �������� �������,
 * �� �� ������ ��� �������� ������-���� ���-�� ��������� �������� �� ��� �������������)
 *
 * @author khudyakov
 * @ created 14.02.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class AutoSubscriptionOperationBase extends OperationBase
{
	private PersonData personData;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("PersonContext �� ������������������ ��������.");

		PersonData temp = PersonContext.getPersonDataProvider().getPersonData();
		if (temp.getPerson() == null)
			throw new BusinessException("PersonContext �� ������������������ ��������.");

		personData = temp;
	}

	public PersonData getPersonData()
	{
		return personData;
	}
	/**
	 * @return �������
	 */
	public ActivePerson getPerson()
	{
		return personData.getPerson();
	}
}
