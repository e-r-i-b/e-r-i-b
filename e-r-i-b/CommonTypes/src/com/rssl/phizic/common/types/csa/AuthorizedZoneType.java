package com.rssl.phizic.common.types.csa;

/**
 * @author osminin
 * @ created 10.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ��� ���� ����� ������������
 * ���� ������ ���� ��������� ����� mobileN-authentication-modes.xml, ��� N - ������� ������ ����
 */
public enum AuthorizedZoneType
{
	POST_REGISTRATION("/postCSALogin.do"),    //�������������� ����
	AUTHORIZATION("/postCSALogin.do"),    //�������������� ����
	PRE_AUTHORIZATION("/login.do");       //���������������� ����

	private String actionPath;

	AuthorizedZoneType(String actionPath)
	{
		this.actionPath = actionPath;
	}

	/**
	 * @return ����� ����
	 */
	public String getActionPath()
	{
		return actionPath;
	}
}
