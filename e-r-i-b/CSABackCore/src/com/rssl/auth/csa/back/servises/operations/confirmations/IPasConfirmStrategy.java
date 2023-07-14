package com.rssl.auth.csa.back.servises.operations.confirmations;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.auth.csa.back.integration.UserInfoProvider;
import com.rssl.auth.csa.back.integration.ipas.IPasPasswordInformation;
import com.rssl.auth.csa.back.integration.ipas.IPasService;
import com.rssl.auth.csa.back.integration.ipas.ServiceUnavailableException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.mobilebank.UserInfo;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������������� ������� � ����
 */

public class IPasConfirmStrategy implements ConfirmStrategy<IPasPasswordInformation, IPasConfirmationInfo>
{
	private String cardNumber;
	private IPasPasswordInformation confirmInformation;
	private String ouid;

	/**
	 * �����������
	 * @param ouid ������������� �������� (��� �����)
	 * @param cardNumber ����� ����� (��� ��������� ������ �� iPas)
	 * @param confirmInformation ������ ������ (��� ���������)
	 */
	public IPasConfirmStrategy(String ouid, String cardNumber, IPasPasswordInformation confirmInformation)
	{
		this.ouid = ouid;
		this.confirmInformation = confirmInformation;
		this.cardNumber = cardNumber;
	}

	public void initialize() throws Exception
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByCardNumber(cardNumber);
		if (userInfo == null)
			throw new MobileBankRegistrationNotFoundException("������ ��������� ���������� �� ���������� ����� �� ����� " + Utils.maskCard(cardNumber) + ".");

		String userId = userInfo.getUserId();
		confirmInformation = IPasService.getInstance().prepareOTP(userId);
		if (confirmInformation == null)
			throw new SystemException("������ ��������� ���������� �� ����������� ������ �� IPas ��� ������ " + userId);
	}

	public IPasPasswordInformation getConfirmCodeInfo()
	{
		return confirmInformation;
	}

	public void publishCode(){}

	public void checkConfirmAllowed()
	{
		if (isFailed())
			throw new IllegalOperationStateException("�������� " + ouid + " �� ����� ���� ������������.\n " +
					"���������� ���������� �������: " + confirmInformation.getLastAtempts());
	}

	public boolean check(String password) throws SystemException, ServiceUnavailableException
	{
		Integer attempts = IPasService.getInstance().verifyOTP(confirmInformation.getSID(), password);
		if (attempts != null)
			confirmInformation.setLastAtempts(attempts);
		return attempts == null;
	}

	public boolean isFailed()
	{
		return getConfirmationInfo().getLastAtempts() <= 0;
	}

	public IPasConfirmationInfo getConfirmationInfo()
	{
		Integer lastAtempts = confirmInformation.getLastAtempts();
		return new IPasConfirmationInfo(confirmInformation.getPasswordNo(), confirmInformation.getReceiptNo(),
				confirmInformation.getPasswordsLeft(), lastAtempts == null? 3 : lastAtempts);
	}
}
