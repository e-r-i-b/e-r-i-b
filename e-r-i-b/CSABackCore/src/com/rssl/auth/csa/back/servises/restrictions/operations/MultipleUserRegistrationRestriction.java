package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.UserAlreadyRegisteredException;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class MultipleUserRegistrationRestriction implements OperationRestriction<UserRegistrationOperation>
{
	private static final MultipleUserRegistrationRestriction INSTANCE = new MultipleUserRegistrationRestriction();

	public static MultipleUserRegistrationRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserRegistrationOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		if (
		ConfigFactory.getConfig(Config.class).isDenyMultipleRegistration())
		{
			//��������� ����������� ���������. � ���� � ��� ������������ ��� ����������?
			Long profileId = operation.getProfileId();
			if (!CSAConnector.findNotClosedByProfileID(profileId).isEmpty())
			{
				throw new UserAlreadyRegisteredException("��� ������� " + profileId + " ��� ���������� �����������������e ����������");
			}
		}
	}
}
