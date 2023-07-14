package com.rssl.phizic.config;

import com.rssl.auth.csa.front.operations.auth.GlobalLoginRestriction;
import com.rssl.auth.csa.front.operations.auth.RecoverPasswordRestriction;
import com.rssl.auth.csa.front.operations.auth.RegistrationRestriction;
import com.rssl.auth.csa.front.operations.auth.verification.VerificationState;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.auth.payOrder.PayOrderHelper;

import static com.rssl.phizic.einvoicing.EInvoicingConstants.UEC_PAY_INFO;

/**
 * ������ ��� ��������� �������� ������
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class FrontSettingHelper
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB);

	private static final GlobalLoginRestriction globalLoginRestriction          = new GlobalLoginRestriction();
	private static final RecoverPasswordRestriction recoverPasswordRestriction  = new RecoverPasswordRestriction();
	private static final RegistrationRestriction registrationRestriction        = new RegistrationRestriction();

	/**
	 * ����������� ��������������� ����������� �� ������ ��� ������ �����
	 * @return true - ��������������� ����������� ��������
	 */
	public static boolean isAccessInternalRegistration()
	{
		try
		{
			return registrationRestriction.check();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ��������� ����������� ��������������� �����������", e);
			return false;
		}
	}

	/**
	 * ����������� ��������������� ����������� �� ������ ��� ������ �����
	 * @return true - ��������������� ����������� ��������
	 */
	public static boolean isAccessExternalRegistration()
	{
		try
		{
			CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
			return config.isAccessExternalRegistration();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ��������� ����������� ��������������� �����������", e);
			return false;
		}
	}

	/**
	 * ����������� ���������������� �������������� ������
	 * @return true - �������������� ������ ��������
	 */
	public static boolean isAccessRecoverPassword()
	{
		try
		{
			return recoverPasswordRestriction.check();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ��������� ����������� ���������������� �������������� ������", e);
			return false;
		}
	}

	/**
	 * @return ����� ���������� ��� ����� ����� �����-������
	 */
	public static String getAuthTitle()
	{
		try
		{
			if (UEC_PAY_INFO.equals(PayOrderHelper.getPayOrderMode()))
				return "������������� ��������";
			return "���� � �������� ������";
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @return ���� � ����������� � ���������� ����� ����� �����-������ ������������ <skinUrl>
	 */
	public static String getLoginAuthImage()
	{
		try
		{
			if (UEC_PAY_INFO.equals(PayOrderHelper.getPayOrderMode()))
				return "/skins/sbrf/images/csa/uec.jpg";
			return "/skins/sbrf/images/csa/lock.jpg";
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * ���������� ����������� �����
	 * @return true - ���� ��� ���� ���������
	 */
	public static boolean isGlobalLoginRestriction()
	{
		try
		{
			return !globalLoginRestriction.check();
		}
		catch (Exception e)
		{
			log.error("������ �������� ����������� ����������� �����", e);
			return false;
		}

	}

	/**
	 * �������� �� ����������� ��� ������������
	 * @param connectorInfo ���������� � ���������� ������������, ����� �������������.
	 * @return ��/���
	 */
	public static boolean isRegistrationAllowed(ConnectorInfo connectorInfo)
	{
		if (connectorInfo == null)
		{
			return isAccessInternalRegistration();
		}
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return isAccessInternalRegistration() && !frontConfig.getOldCSACbCodesPattern().matcher(connectorInfo.getCbCode()).matches();
	}

	/**
	 * @return true -- ������������ ���������� ����������� ������ ��� ������� �����
	 */
	public static boolean isAccessToBusinessEnvironment()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.isAccessToBusinessEnvironment();
	}

	/**
	 * @return ��� ����� ������� �����
	 */
	public static String getBusinessEnvironmentMainURL()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getBusinessEnvironmentMainURL();
	}

	/**
	 * @return ��� �������� ������� � ������� �����
	 */
	public static String getBusinessEnvironmentUserURL()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getBusinessEnvironmentUserURL();
	}

	/**
	 * @param state ��������� �����������
	 * @return ��� �������� ����� �����������
	 */
	public static String getBusinessEnvironmentAfterVerifyURL(VerificationState state)
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getBusinessEnvironmentVerifyStateURL(state);
	}

	/**
	 * @return ��������� ��� �����������(��������� ��������� ��� ������������ ������)
	 */
	public static RegistrationAccessType getRegistrationAccessType()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getRegistrationAccessType();
	}

	public static boolean getAsyncCheckingFieldsIsEnabled()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getAsyncCheckingFieldsIsEnabled();
	}

	/**
	 * @return true - ���� � CSAFront �������� DMP-Pixel, false - �����
	 */
	public static boolean getPixelMetricActivity()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.isPixelMetricActivity();
	}
}
