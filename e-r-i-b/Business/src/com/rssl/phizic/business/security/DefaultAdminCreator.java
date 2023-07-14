package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.DefaultAdminLoginGenerator;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.crypto.CryptoProviderService;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * ����� ������� �������������� �� ��������� (����� - admin, ������ - admin)
 * @author Kidyaev
 * @ created 12.01.2006
 * @ $Author$
 * @ $Revision$
 */
public class DefaultAdminCreator
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private SecurityService securityService = new SecurityService();
	private SchemeOwnService schemeOwnService = new SchemeOwnService();

	private static final int ADMIN_PASSWORD_LIFE_TIME = 100; // years

	public void create() throws Exception
	{
		RSSLTestCaseBase.initializeEnvironment();

		String loginAdmin = ConfigFactory.getConfig(SecurityConfig.class).getDefaultAdminName();

		if ( loginAdmin == null )
		{
			throw new Exception("�� ������ USER ID ��� ���������� ������� ������ ��������������");
		}
		else
		{
			CryptoProviderService cryptoProviderService = null;
			try
			{
				//���������������
				cryptoProviderService = new CryptoProviderService();
				cryptoProviderService.start();

				BankLogin login = createLogin(loginAdmin, loginAdmin);
				setAccessScheme(login);
			}
			finally
			{
				if(cryptoProviderService != null)
					cryptoProviderService.stop();
			}
		}
		log.info("���������� ������� ���������� ������� ������ �������������� ���������");
	}

	private void setAccessScheme(BankLogin login) throws BusinessException
	{
		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
		AccessScheme scheme = schemeOwnService.findScheme(login, AccessType.employee);

		if(scheme == null)
		{
			scheme = schemesConfig.getBuildinAdminAccessScheme();

			if(scheme != null)
			{
				schemeOwnService.setScheme(login, AccessType.employee, scheme);
				log.info("����������� ����� ���� ������� ��� ���������� ������� ������ ��������������");
			}
			else
			{
				throw new RuntimeException("�� ������� ����� ������� ��� ���������� ������� ������ ��������������");
			}
		}
		else
		{
			log.info("����� ���� ������� ��� ���������� ������� ������ �������������� ��� ���������");
		}
	}


	/**
	 * ������� �����
	 * @param loginValue - �����
	 * @throws Exception
	 */
	private BankLogin createLogin(final String loginValue, final String password) throws Exception
	{
		BankLogin login = securityService.getBankLogin(loginValue);

		if (login == null)
		{
			DefaultAdminLoginGenerator generator = new DefaultAdminLoginGenerator(loginValue, password);
			generator.setCsaUserId(loginValue);
			Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.YEAR, ADMIN_PASSWORD_LIFE_TIME);
			generator.setExpireDate(calendar);
			login = generator.generate();
			log.info("������ ����� ��� ���������� ������� ������ ��������������");
		}
		else
		{
			log.info("����� ��� ���������� ������� ������ ��������������, ��� ��������. �������� ���������");
		}

		return login;
	}

}
