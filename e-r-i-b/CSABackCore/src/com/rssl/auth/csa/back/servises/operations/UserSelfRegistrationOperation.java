package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;

/**
 * @author osminin
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������������� ����������� �������
 */
public class UserSelfRegistrationOperation extends UserRegistrationOperation
{
	/**
	 * ��������� �����������
	 */
	public UserSelfRegistrationOperation(){}

	/**
	 * �����������
	 * @param identificationContext �������� �������������
	 */
	public UserSelfRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	protected RegistrationType getRegistrationType()
	{
		return RegistrationType.SELF;
	}

	protected SecurityType getSecurityType(Profile profile, ConnectorType connectorType)
	{
		return SecurityType.LOW;
	}
}
