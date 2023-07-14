package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * @author osminin
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Данные по запросу начала самостоятельной регистрации
 */
public class StartUserSelfRegistrationRequestData extends StartUserRegistrationRequestData
{
	private static final String REQUEST_NAME = "startUserSelfRegistrationRq";

	public String getName()
	{
		return REQUEST_NAME;
	}
	/**
	 * конструктор
	 * @param cardNumber номер карты
	 * @param confirmStrategyType тип стратегии подтверждения
	 */
	public StartUserSelfRegistrationRequestData(String cardNumber, ConfirmStrategyType confirmStrategyType)
	{
		super(cardNumber, confirmStrategyType);
	}
}
