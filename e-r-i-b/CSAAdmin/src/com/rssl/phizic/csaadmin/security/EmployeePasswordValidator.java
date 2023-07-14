package com.rssl.phizic.csaadmin.security;

import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.security.GOSTUtils;
import com.rssl.phizic.utils.StringUtils;

/**
 * @author mihaylov
 * @ created 13.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������ ����������
 */
public class EmployeePasswordValidator
{
	private String clientRnd;
	private String serverRnd;

	/**
	 * ���������������� ���������
	 * @param clientRnd - ��������� �������� ��������������� �� �������
	 * @param serverRnd - ��������� �������� ��������������� �� �������
	 */
	public void initialize(String clientRnd, String serverRnd)
	{
		this.clientRnd = clientRnd;
		this.serverRnd = serverRnd;
	}

	/**
	 * ��������� ���������� ������
	 * @param login - �����, ��� �������� ��������� ������
	 * @param password - ��� ������
	 * @return true/false
	 */
	public boolean checkValidity(Login login, String password)
	{
		byte[] passwordHash = StringUtils.fromHexString(String.valueOf(login.getPassword()));
		byte[] dataBytes = StringUtils.fromHexString(serverRnd + clientRnd);
        byte[] hmacBytes = GOSTUtils.hmac(passwordHash, dataBytes);

		String hmacHex = StringUtils.toHexString(hmacBytes);

		return hmacHex.equals(password);
	}

}
