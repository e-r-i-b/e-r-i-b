package com.rssl.auth.csa.wsclient.requests;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� �������������� ������������ ����� socialApi
 */
public class FinishCreateSocialSessionRequestData extends FinishCreateMobileSessionRequestData
{
	private static final String REQUEST_DATA_NAME = "finishCreateSocialSessionRq";

	/**
	 * �����������
	 * @param ouid ������������� ��������
	 */
	public FinishCreateSocialSessionRequestData(String ouid)
	{
		super(ouid);
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

}
