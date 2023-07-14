package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.TooManyConnectorsException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginOperation extends LoginOperationBase
{
	private static final String GLOBAL_LOGIN_RESTRICTION_MESSAGE = "�� ����������� �������� ���� � ������� �������� ���������. ���������� ����� �������";
	private static final GlobalLoginRestriction globalLoginRestriction = new GlobalLoginRestriction();

	/**
	 * ������������� ��������
	 * @param info �������� ��������
	 * @param login �����
	 * @param password ������
	 */
	public void initialize(OperationInfo info, String login, String password)
	{
		super.initialize(info);
		operationInfo.setLogin(login);
		operationInfo.setPassword(password);
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		try
		{
			return CSABackRequestHelper.sendStartCreateSessionRq(operationInfo.getLogin(), operationInfo.getPassword());
		}
		catch (TooManyConnectorsException e)
		{
			// ��������� �������� �������� ������������ � ��������������� ��������
			operationInfo.setConnectorInfos(e.getConnectorInfos());
			operationInfo.setOUID(e.getOuid());
			return null;
		}
	}

	protected void checkRestrict() throws FrontLogicException, FrontException
	{
		// ��������� ���������� ����������� �����
		if(!globalLoginRestriction.check())
			throw new FrontLogicException(GLOBAL_LOGIN_RESTRICTION_MESSAGE);
	}
}
