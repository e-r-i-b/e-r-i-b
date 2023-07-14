package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.InactiveCardRegistrationException;
import com.rssl.auth.csa.back.exceptions.NotMainCardRegistrationException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class UserRegistrationInactiveCardRestriction implements OperationRestriction<UserRegistrationOperation>
{
	private static final UserRegistrationInactiveCardRestriction INSTANCE = new UserRegistrationInactiveCardRestriction();
	private static final String INVALID_CARD_RESTRICTION_MESSAGE =
			"�� ������ ������ ����� ���������� �����������. ������� ������� ����������� ������ � ������� �������� �����. " +
			"���� �� ����������� ���������, ���������� � ���������� ����� ��������� �� �������: +7(495)500 55 50, 8(800)555 5550";

	public static UserRegistrationInactiveCardRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserRegistrationOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		if (!operation.isActiveCard())
		{
			throw new InactiveCardRegistrationException(INVALID_CARD_RESTRICTION_MESSAGE);
		}
	}
}