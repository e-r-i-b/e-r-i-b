package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.auth.Stage;

import java.util.List;

/**
 * Информация об операции регистарции пользователя
 * @author niculichev
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationOperationInfo extends OperationInfoBase
{
	private static final String DELIMITER = ",";

	private boolean additionalCheck;
	private String cardNumber;
	private List<ConnectorInfo> connectorInfos;
	private UserInfo userInfo;

	public RegistrationOperationInfo(Stage stage)
	{
		super(stage);
	}

	/**
	 * @return номер карты
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public List<ConnectorInfo> getConnectorInfos()
	{
		return connectorInfos;
	}

	public void setConnectorInfos(List<ConnectorInfo> connectorInfos)
	{
		this.connectorInfos = connectorInfos;
	}

	public boolean isAdditionalCheck()
	{
		return additionalCheck;
	}

	public void setAdditionalCheck(boolean additionalCheck)
	{
		this.additionalCheck = additionalCheck;
	}

	public UserInfo getUserInfo()
	{
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo)
	{
		this.userInfo = userInfo;
	}

	public String getKeyByUserInfo()
	{
		if(userInfo == null)
			return null;

		// ФИО+ДУЛ+ДР+ТБ
		return new StringBuilder()
				.append(userInfo.getFirstname()).append(DELIMITER)
				.append(userInfo.getSurname()).append(DELIMITER)
				.append(userInfo.getPatrname()).append(DELIMITER)
				.append(userInfo.getPassport()).append(DELIMITER)
				.append(DateHelper.formatDateDDMMYY(userInfo.getBirthdate())).append(DELIMITER)
				.append(userInfo.getTb())
				.toString();
	}
}
