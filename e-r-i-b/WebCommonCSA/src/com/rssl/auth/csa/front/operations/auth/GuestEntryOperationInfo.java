package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.phizic.web.auth.Stage;

import javax.servlet.http.HttpSession;

/**
 * @author tisov
 * @ created 24.12.14
 * @ $Author$
 * @ $Revision$
 * [�������� ����] ���� �� �������� �������������� ����� �� ������ ��������
 */
public class GuestEntryOperationInfo extends OperationInfoBase
{

	private String phoneNumber;                     //����� ��������
	private boolean userRegistered;                 //����� �� ������������ ����� � ����
	private String host;                            //���� �����

	public GuestEntryOperationInfo(Stage stage)
	{
		super(stage);
	}

	public Long getConfirmAttemptsCount()
	{
		return (Long)getConfirmParams().get(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME);
	}

	public void decreaseConfirmAttempts()
	{
		Long attempts = getConfirmAttemptsCount() - 1;
		getConfirmParams().put(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME, attempts);
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public boolean isUserRegistered()
	{
		return userRegistered;
	}

	public void setUserRegistered(boolean userRegistered)
	{
		this.userRegistered = userRegistered;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}
}
