package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.persons.Constants;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.config.limits.ClientSecurityLevelConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration3;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author osminin
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ������� ������������
 */
public class SecurityTypeHelper
{
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_CONFIRM_CC                     = "�������� ������ ������� ������������: ������ ���������� �������� � ��.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_IPAS                           = "�������� ������ ������� ������������: ������ ������ �� ������ TERMINAL.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_ATM                            = "�������� ������ ������� ������������: ������ ������ �� ������ ATM.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_EMPTY_CHANNELS_SETTINGS        = "�������� ������ ������� ������������: � ���������� ������� �� ����� ����� ��������� ������.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_DISPOSABLE                     = "�������� ������ ������� ������������: ��� ����� ������� ������������ ��������� ���������.";

	public static final String HIGHT_SECURITY_TYPE_DESCRIPTION_PENSIONER                    = "�������� ������� ������� ������������: ������ ���������.";
	public static final String HIGHT_SECURITY_TYPE_DESCRIPTION_EMPTY_REGISTRATION_CHANNEL   = "�������� ������� ������� ������������: � �� �� �������� ����� ���������/����������� ������.";
	public static final String HIGHT_SECURITY_TYPE_DESCRIPTION_REGISTRATION_DATE            = "�������� ������� ������� ������������: ������ ������� ����� ����� %s ���� �����.";

	public static final String SECURITY_TYPE_LOGIN_TYPE_DESCRIPTION                         = "������ ������ �� ������ ";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	/**
	 * ���������� ������� ������������
	 * @param userInfo ���������� � ������������
	 * @return ������� ������������
	 */
	public static SecurityType calcSecurityType(CSAUserInfo userInfo) throws GateLogicException, GateException
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}
		return calcSecurityType(userInfo.getBirthdate(), userInfo.getCardNumber(), null);
	}
	/**
	 * ���������� ������� ������������
	 * @param userInfo ���������� � ������������
	 * @param connectorType ��� ����������
	 * @return ������� ������������
	 */
	public static SecurityType calcSecurityType(CSAUserInfo userInfo, ConnectorType connectorType) throws GateLogicException, GateException
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}
		return calcSecurityType(userInfo.getBirthdate(), userInfo.getCardNumber(), connectorType);
	}

	/**
	 * ���������� ������� ������������
	 * @param profile �������
	 * @param cardNumber ����� �����
	 * @param connectorType ��� ����������
	 * @return ������� ������������
	 */
	public static SecurityType calcSecurityType(Profile profile, String cardNumber, ConnectorType connectorType) throws GateLogicException, GateException
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("����� ����� �� ����� ���� null");
		}
		return calcSecurityType(profile.getBirthdate(), cardNumber, connectorType);
	}

	private static SecurityType calcSecurityType(Calendar birthDate, String cardNumber, ConnectorType connectorType) throws GateException, GateLogicException
	{
		//���� cardNumber ����, �� �������� ������������ �������.
		if (StringHelper.isEmpty(cardNumber))
		{
			logSecurityType(HIGHT_SECURITY_TYPE_DESCRIPTION_EMPTY_REGISTRATION_CHANNEL, connectorType);
			return SecurityType.HIGHT;
		}

		// ���� ������ ��������� - ������� �������
		if (DateHelper.yearsDiff(birthDate, Calendar.getInstance()) >= Constants.PENSION_AGE)
		{
			logSecurityType(HIGHT_SECURITY_TYPE_DESCRIPTION_PENSIONER, connectorType);
			return SecurityType.HIGHT;
		}
		// ������ ��������� ������ ������������
		ClientSecurityLevelConfig config = ClientSecurityLevelConfig.getInstance();
		Date startDate = config.getStartDate();
		boolean inERIB = config.isChangePhoneByERIB();
		boolean inVSP = config.isChangePhoneByVSP();
		boolean inATM = config.isChangePhoneByATM();
		boolean inCC = config.isChangePhoneByCC();
		// ���� ����� �� ������, ������ ������� ������������ ������
		if (!(inATM || inCC || inERIB || inVSP))
		{
			logSecurityType(LOW_SECURITY_TYPE_DESCRIPTION_EMPTY_CHANNELS_SETTINGS);
			return SecurityType.LOW;
		}
		//������ ����������� ����� � �� ��
		List<MobileBankRegistration3> registrations = MobileBankService.getInstance().getRegistrations3(cardNumber);
		// ������� ������� �������������, ���� ���� �� ��� ����� ����������� �� ����� ����������� �������:
		// ���� ������ �������� (startDate) <= ���� ��������� ����������� ����� �����, ���������� � ��������� > ������� ���� ����� ���������� ���� �� ���������
		for (MobileBankRegistration3 registration : registrations)
		{
			if (registration.getLastRegistrationDate().before(getEndDate(config.getLessCountDays())))
			{
				logSecurityType(SecurityType.MIDDLE.getDescription());
				return SecurityType.MIDDLE;
			}
			else
			{
				// ���� ����� ����� ������� ����, ������ �� ���������� ����� ������ �������� src 0 - ��� ������, ���������� ������� ������� ������������
				if (registration.getChannelType() == null)
				{
					logSecurityType(HIGHT_SECURITY_TYPE_DESCRIPTION_EMPTY_REGISTRATION_CHANNEL, connectorType);
					return SecurityType.HIGHT;
				}

				if (isChannelTypeChecked(registration.getChannelType(), inERIB, inATM, inVSP, inCC) &&
						(registration.getLastRegistrationDate().after(startDate) || registration.getLastRegistrationDate().equals(startDate)))
				{
					String message = String.format(HIGHT_SECURITY_TYPE_DESCRIPTION_REGISTRATION_DATE, config.getLessCountDays());
					logSecurityType(message, connectorType);
					return SecurityType.HIGHT;
				}
			}
		}
		// �� ���� ��������� ������� ������� ������������ - �������
		logSecurityType(SecurityType.MIDDLE.getDescription());
		return SecurityType.MIDDLE;
	}

	/**
	 * ������� ���� �� ������� ���������� ���� �� ��������� ������ ������������
	 * @return �������� ���� ��� �������� �� ������� �������
	 */
	private static Date getEndDate(int daysNumber)
	{
		return DateHelper.addDays(Calendar.getInstance(), -daysNumber).getTime();
	}

	/**
	 * @param channelType ��� ������
	 * @param inERIB ������ ����
	 * @param inATM  ������ ��
	 * @param inVSP  ������ ���
	 * @param inCC   ������ ��
	 * @return ������ �� ��� ������ � ���������
	 */
	private static boolean isChannelTypeChecked(ChannelType channelType, boolean inERIB, boolean inATM, boolean inVSP, boolean inCC)
	{
		boolean checkedATM  = inATM && channelType == ChannelType.SELF_SERVICE_DEVICE;
		boolean checkedERIB = inERIB && channelType == ChannelType.INTERNET_CLIENT;
		boolean checkedVSP  = inVSP && channelType == ChannelType.VSP;
		boolean checkedCC   = inCC && channelType == ChannelType.CALL_CENTR;

		return checkedATM || checkedERIB || checkedVSP || checkedCC;
	}

	/**
	 * �������� ���������� ������ ������������ � SystemLog ������� ERROR
	 * @param message ���������
	 */
	public static void logSecurityType(String message)
	{
		log.error(message);
	}

	/**
	 * �������� ���������� ������ ������������ � SystemLog ������� ERROR
	 * @param message ���������
	 * @param connectorType ��� ����������
	 */
	public static void logSecurityType(String message, ConnectorType connectorType)
	{
		StringBuilder builder = new StringBuilder(message);
		if (connectorType != null)
		{
			builder.append(" ").append(SECURITY_TYPE_LOGIN_TYPE_DESCRIPTION).append(connectorType.name());
		}
		log.error(builder.toString());
	}
}
