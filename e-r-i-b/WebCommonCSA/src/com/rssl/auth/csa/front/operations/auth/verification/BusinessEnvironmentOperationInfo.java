package com.rssl.auth.csa.front.operations.auth.verification;

import com.rssl.auth.csa.front.operations.auth.LoginOperationInfo;
import com.rssl.auth.csa.front.operations.auth.ConfirmableOperationInfo;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * ���������� � ������� ��������
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
	 * �����������
	 * @param stage ���������
	 */
	public BusinessEnvironmentOperationInfo(Stage stage)
	{
		super(stage);
	}

	/**
	 * ������ ������ �������������
	 * @param confirmType ������ �������������
	 */
	public void setConfirmType(ConfirmStrategyType confirmType)
	{
		this.confirmType = confirmType;
	}

	/**
	 * @return ������ �������������
	 */
	public ConfirmStrategyType getConfirmType()
	{
		return confirmType;
	}

	/**
	 * @return ����� �����
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * ������ ����� �����
	 * @param cardNumber ����� �����
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return ������� ������������� �������
	 */
	public String getClientExternalId()
	{
		return clientExternalId;
	}

	/**
	 * ������ ������� ������������� �������
	 * @param clientExternalId ������� ������������� �������
	 */
	public void setClientExternalId(String clientExternalId)
	{
		this.clientExternalId = clientExternalId;
	}

	/**
	 * @return ��������� �����������
	 */
	public VerificationState getVerificationState()
	{
		return verificationState;
	}

	/**
	 * ������ ��������� �����������
	 * @param verificationState ��������� �����������
	 */
	public void setVerificationState(VerificationState verificationState)
	{
		this.verificationState = verificationState;
	}

	/**
	 * @return ���������������� ������ �������������
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
	 * ������ ���������������� ������ �������������
	 * @param preferredConfirmType ���������������� ������ �������������
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
