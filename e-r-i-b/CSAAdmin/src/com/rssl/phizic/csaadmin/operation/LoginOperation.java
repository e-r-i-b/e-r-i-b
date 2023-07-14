package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.*;
import com.rssl.phizic.csaadmin.security.EmployeePasswordValidator;
import com.rssl.phizic.csaadmin.security.LoginBlockValidator;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.util.SecurityUtil;
import com.rssl.phizic.utils.StringUtils;

import java.security.SecureRandom;
import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 13.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� ������������
 */
public class LoginOperation extends OpenSessionOperationBase
{
	private static final LoginBlockService loginBlockService = new LoginBlockService();
	private static final String WRONG_PASSWORD_MESSAGE = "������������ ���� ������. ";
	private static final LoginService loginService = new LoginService();

	private String password;
	private String serverRandom;
	private String clientRandom;

	/**
	 * ������������� ��������
	 * @param loginName - ��� ������������
	 * @param password - ��� ������
	 * @param clientRandom - ��������� ����� ��������������� �� ���������� �������
	 * @param serverRandom - ��������� ����� ��������������� �� ��������� �������
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public void initialize(String loginName, String password, String clientRandom, String serverRandom) throws AdminException, AdminLogicException
	{
		this.login = loginService.findByName(loginName);
		if(this.login == null)
			throw new AdminLogicException("������ �����������. ������ � ������� ��������.");
		this.password = password;
		this.clientRandom = clientRandom;
		this.serverRandom = serverRandom;
	}

	/**
	 * ��������� ������ ������������
	 * @throws AdminLogicException
	 */
	public void validate() throws AdminLogicException, AdminException
	{
		blockingByLongInactivity(login.getName());
		LoginBlockValidator loginBlockValidator = new LoginBlockValidator();
		if(!loginBlockValidator.validate(login))
		{
			throw new AdminLogicException(loginBlockValidator.getMessage());
		}

		EmployeePasswordValidator passwordValidator = new EmployeePasswordValidator();
		passwordValidator.initialize(clientRandom,serverRandom);
		if(!passwordValidator.checkValidity(login,password))
		{
			checkLoginAttempts();
			throw new AdminLogicException("������ �����������. ������ � ������� ��������.");
		}

		loginService.clearWrongAttempts(login);
	}

	private void blockingByLongInactivity(String loginName) throws AdminLogicException, AdminException
	{
		Login login = loginService.findByName(loginName);
		if(login != null)
		{
			Calendar lastLogonDate = login.getLastLogonDate() != null ? login.getLastLogonDate() : login.getLastUpdateDate();
				if(SecurityUtil.needBlockByLongInactivity(lastLogonDate))
				{
					StringBuilder messageBuilder = new StringBuilder(BlockType.longInactivity.getPrefix());
					messageBuilder.append(".");
					throw new AdminLogicException(messageBuilder.toString());
				}
		}
	}

	private void checkLoginAttempts() throws AdminLogicException, AdminException
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		int maxLoginAttempts = securityConfig.getLoginAttempts();
		login.incWrongLoginAttempts();
		if(login.getWrongLoginAttempts() >= maxLoginAttempts)
		{
			loginService.clearWrongAttempts(login);
			LoginBlock loginBlock = loginBlockService.wrongLogonLock(login, securityConfig.getBlockedTimeout());
			throw new AdminLogicException(WRONG_PASSWORD_MESSAGE + loginBlock.getMessage());
		}
		loginService.save(login);
	}

	/**
	 * @return ���������� ��������� ������
	 */
	public String generateServerRandom()
	{
		byte[] serverRandomArray = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(serverRandomArray);

		return StringUtils.toHexString(serverRandomArray);
	}

	/**
	 * @return ������������
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * ���������� ������
	 */
	public void save() throws AdminException
	{
		loginService.save(login);
	}
}
