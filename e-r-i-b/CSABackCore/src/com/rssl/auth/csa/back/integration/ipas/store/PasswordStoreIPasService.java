package com.rssl.auth.csa.back.integration.ipas.store;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.RetryIPasUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.AdjacentServiceUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.IPasService;
import com.rssl.auth.csa.back.integration.ipas.ServiceUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.generated.IPASWSSoap;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

/**
 * @author krenev
 * @ created 12.09.2013
 * @ $Author$
 * @ $Revision$
 * ������ IPas � �������������� �������.
 */

public class PasswordStoreIPasService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	//������ ����� �������, ��������� ����������� ������ �� ����������� ������.
	private static final List<String> ERROR_CODES_FOR_STORED_PASSWORD_VERIFICATION = Arrays.asList("ERR_PRMFMT","ERROR");
	private static final PasswordStoreIPasService INSTANCE = new PasswordStoreIPasService();

	private PasswordStoreIPasService()
	{
	}

	/**
	 * @return ������� �������
	 */
	public static PasswordStoreIPasService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * ����������� ������� � iPas � ��������������
	 * @param connector ���������
	 * @param password ������
	 * @return ���� � ������������ � ������ �������� ��������������, null � ������ ��������� ��������������
	 * @throws Exception
	 */
	public CSAUserInfo verifyPassword(TerminalConnector connector, String password) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("��������� �� ����� ���� null");
		}
		String login = connector.getUserId();

		if (!ConfigFactory.getConfig(Config.class).isIPasAuthenticationAllowed())
		{
			return verifyByStoredPassword(connector, password);
		}

		CSAUserInfo userInfo = null;
		try
		{
			userInfo = IPasService.getInstance().verifyPassword(login, password);
		}
		catch (AdjacentServiceUnavailableException e)
		{
			if (ERROR_CODES_FOR_STORED_PASSWORD_VERIFICATION.contains(e.getErrorCode()))
			{
				log.error("������ ��� ����������� ������ � iPas", e);
				return verifyByStoredPassword(connector, password);
			}
			throw e;
		}
		catch (ServiceUnavailableException e)
		{
			if (e.getCause() instanceof RemoteException)
			{
				log.error("������ ��� ����������� ������ � iPas", e);
				return verifyByStoredPassword(connector, password);
			}
			throw e;
		}

		if (userInfo != null)
		{
			storeAuthData(login, password);
		}
		return userInfo;
	}

	private CSAUserInfo verifyByStoredPassword(TerminalConnector connector, String password) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("��������� �� ����� ���� null");
		}
		String login = connector.getUserId();

		log.trace("���������� ������� ����������� ������ �� ����������� ������ ��� ������ " + login);
		StoredPassword storedPassword = StoredPassword.findByLogin(login);
		if (storedPassword == null)
		{
			log.trace("��� ������������ ������. ������������� �� �������� ��� ������ " + login);
			throw new RetryIPasUnavailableException("���� � �������� ������ �������� ����������. ����������, ��������� ������� ����� 30 �����.");
		}
		if (!storedPassword.check(password))
		{
			log.debug("�� �������� ����������� ������ �� ����������� ������ ��� ������ " + login);
			return null;
		}
		log.debug("����������� ������ �� ����������� ������ ��� ������ " + login + " �������� �������.");
		return connector.asUserInfo();
	}

	private void storeAuthData(String login, String password)
	{
		if (!ConfigFactory.getConfig(Config.class).isIPasPasswordStoreAllowed())
		{
			log.trace("��������� '�������������' �������. ���������� ��������� ��� ������ " + login);
			return;
		}

		try
		{
			StoredPassword storedPassword = StoredPassword.findByLogin(login);
			if (storedPassword == null)
			{
				log.trace("��� ������������ ������ ��� ������ " + login + " ��������� ������. ������������ �������� ������������������� ������.");
				StoredPassword.create(login, password);
				return;
			}
			if (storedPassword.check(password))
			{
				log.trace("��� ������ " + login + " ���������� ���������� ������, �� ������ �� ���������");
				return;
			}
			log.debug("��� ������ " + login + " ������� ����������� ������");
			storedPassword.changePassword(password);
		}
		catch (Exception e)
		{
			log.error("������ ��� '�������������' ������ ��� ������ " + login, e);
		}
	}
}
