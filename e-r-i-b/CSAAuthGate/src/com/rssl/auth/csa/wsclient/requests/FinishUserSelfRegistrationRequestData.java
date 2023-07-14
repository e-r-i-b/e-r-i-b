package com.rssl.auth.csa.wsclient.requests;

/**
 * @author osminin
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ������� ���������� ��������������� �����������
 */
public class FinishUserSelfRegistrationRequestData extends FinishUserRegistrationRequestData
{
	private static final String REQUEST_NAME = "finishUserSelfRegistrationRq";

	public String getName()
	{
		return REQUEST_NAME;
	}
	/**
	 * ������������
	 * @param ouid �������������
	 * @param login �����
	 * @param password ������
	 * @param notification ����� �� ������������ ����������
	 */
	public FinishUserSelfRegistrationRequestData(String ouid, String login, String password, boolean notification)
	{
		super(ouid, login, password, notification);
	}
}
