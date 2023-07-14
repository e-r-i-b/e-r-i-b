package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * @author osminin
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ������� ������ ��������������� �����������
 */
public class StartUserSelfRegistrationRequestData extends StartUserRegistrationRequestData
{
	private static final String REQUEST_NAME = "startUserSelfRegistrationRq";

	public String getName()
	{
		return REQUEST_NAME;
	}
	/**
	 * �����������
	 * @param cardNumber ����� �����
	 * @param confirmStrategyType ��� ��������� �������������
	 */
	public StartUserSelfRegistrationRequestData(String cardNumber, ConfirmStrategyType confirmStrategyType)
	{
		super(cardNumber, confirmStrategyType);
	}
}
