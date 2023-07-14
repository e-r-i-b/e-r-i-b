package com.rssl.phizic.web.security;

import org.apache.struts.action.ActionForm;

/**
 * @author mihaylov
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����������� ������� � ������� ����� �������������� � CSAAdmin
 */
public class PostCSAAdminLoginForm extends ActionForm
{
	private String token;

	/**
	 * @return ����� ������ � CSAAdmin
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * ���������� ����� ������ � CSAAdmin
	 * @param token - �����
	 */
	public void setToken(String token)
	{
		this.token = token;
	}
}
