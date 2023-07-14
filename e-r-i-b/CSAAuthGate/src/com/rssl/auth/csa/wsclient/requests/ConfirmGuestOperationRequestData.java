package com.rssl.auth.csa.wsclient.requests;

/**
 * ������ �� ������������� �������� ��������
 * @author niculichev
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmGuestOperationRequestData extends ConfirmOperationRequestData
{
	private static final String GUEST_REQUEST_NAME = "confirmGuestOperationRq";

	/**
	 * ctor
	 * @param ouid ������������� ��������
	 * @param confirmationCode ��� �������������
	 */
	public ConfirmGuestOperationRequestData(String ouid, String confirmationCode)
	{
		super(ouid, confirmationCode);
	}

	public String getName()
	{
		return GUEST_REQUEST_NAME;
	}
}
