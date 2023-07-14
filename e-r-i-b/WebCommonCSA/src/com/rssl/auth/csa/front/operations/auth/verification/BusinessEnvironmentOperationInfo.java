package com.rssl.auth.csa.front.operations.auth.verification;

import com.rssl.auth.csa.front.operations.auth.LoginOperationInfo;
import com.rssl.auth.csa.front.operations.auth.ConfirmableOperationInfo;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * »нформаци€ о текущей операции
 *
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class BusinessEnvironmentOperationInfo extends LoginOperationInfo implements ConfirmableOperationInfo
{
	private String clientExternalId;
	private ConfirmStrategyType preferredConfirmType;
	private ConfirmStrategyType confirmType;
	private String cardNumber;
	private VerificationState verificationState = VerificationState.NOT_START;
	private Map<String, String> cardConfirmationSource;
	private boolean pushAllowed;

	/**
	 * конструктор
	 * @param stage состо€ние
	 */
	public BusinessEnvironmentOperationInfo(Stage stage)
	{
		super(stage);
	}

	/**
	 * задать способ подтверждени€
	 * @param confirmType способ подтверждени€
	 */
	public void setConfirmType(ConfirmStrategyType confirmType)
	{
		this.confirmType = confirmType;
	}

	/**
	 * @return способ подтверждени€
	 */
	public ConfirmStrategyType getConfirmType()
	{
		return confirmType;
	}

	/**
	 * @return номер карты
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * задать номер карты
	 * @param cardNumber номер карты
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return внешний идентификатор клиента
	 */
	public String getClientExternalId()
	{
		return clientExternalId;
	}

	/**
	 * задать внешний идентификатор клиента
	 * @param clientExternalId внешний идентификатор клиента
	 */
	public void setClientExternalId(String clientExternalId)
	{
		this.clientExternalId = clientExternalId;
	}

	/**
	 * @return состо€ние верификации
	 */
	public VerificationState getVerificationState()
	{
		return verificationState;
	}

	/**
	 * задать состо€ние верификации
	 * @param verificationState состо€ние верификации
	 */
	public void setVerificationState(VerificationState verificationState)
	{
		this.verificationState = verificationState;
	}

	/**
	 * @return предпочтительный способ подтверждени€
	 */
	public ConfirmStrategyType getPreferredConfirmType()
	{
		return preferredConfirmType;
	}

	public void setCardConfirmationSource(Map<String, String> cardConfirmationSource)
	{
		this.cardConfirmationSource = cardConfirmationSource;
	}

	public Map<String, String> getCardConfirmationSource()
	{
		return cardConfirmationSource;
	}

	/**
	 * задать предпочтительный способ подтверждени€
	 * @param preferredConfirmType предпочтительный способ подтверждени€
	 */
	public void setPreferredConfirmType(ConfirmStrategyType preferredConfirmType)
	{
		this.preferredConfirmType = preferredConfirmType;
	}

	public boolean isPushAllowed()
	{
		return pushAllowed;
	}

	public void setPushAllowed(boolean pushAllowed)
	{
		this.pushAllowed = pushAllowed;
	}
}
