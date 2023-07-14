package com.rssl.auth.csa.wsclient.requests;

/**
 * ������ �� ���������� �������� �������������� ������ ��� �����
 * @author niculichev
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FinishGuestRestorePasswordRequestData extends FinishRestorePasswordRequestData
{
	private static final String GUEST_REQUEST_NAME = "finishGuestRestorePasswordRq";

	/**
	 * ctor
	 * @param ouid ������������� ��������
	 * @param password ����� ������
	 */
	public FinishGuestRestorePasswordRequestData(String ouid, String password)
	{
		super(ouid, password);
	}

	public String getName()
	{
		return GUEST_REQUEST_NAME;
	}
}
