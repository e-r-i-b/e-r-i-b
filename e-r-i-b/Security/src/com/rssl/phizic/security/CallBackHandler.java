package com.rssl.phizic.security;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.messaging.OperationType;

/**
 * @author eMakarov
 * @ created 01.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface CallBackHandler
{
	public void setPassword(String str);

	public String getPassword();

	public void setLogin(Login login);

	public Login getLogin();

	public void setConfirmableObject(ConfirmableObject confirmableObject);

	public ConfirmableObject getConfimableObject();

	/**
	 * @return ��������� ������������
	 */
	public String proceed() throws Exception;

	public void setAdditionalCheck();

	/**
	 * ���������� ��� ��������
	 * @param operationType ��� ��������
	 */
	public void setOperationType(OperationType operationType);

	/**
	 * @param useRecipientMobilePhoneOnly - ������ "��� �������� ������������ ������ ������� � ������ �������"
	 */
	void setUseRecipientMobilePhoneOnly(boolean useRecipientMobilePhoneOnly);

	/**
	 * @param needWarningMessage ���������� �� ��������� � ��������������� ������������
	 */
	public void setNeedWarningMessage(boolean needWarningMessage);

	/**
	 * @param useAlternativeRegistrations ������������ �������������� ������ ��������� �����������
	 */
	public void setUseAlternativeRegistrations(boolean useAlternativeRegistrations);
}
