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
 * Хелпер для работы с уровнем безопасности
 */
public class SecurityTypeHelper
{
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_CONFIRM_CC                     = "Присвоен низкий уровень безопасности: клиент подтвердил операцию в КЦ.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_IPAS                           = "Присвоен низкий уровень безопасности: клиент входит по логину TERMINAL.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_ATM                            = "Присвоен низкий уровень безопасности: клиент входит по логину ATM.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_EMPTY_CHANNELS_SETTINGS        = "Присвоен низкий уровень безопасности: в настройках системы не задан канал изменения номера.";
	public static final String LOW_SECURITY_TYPE_DESCRIPTION_DISPOSABLE                     = "Присвоен низкий уровень безопасности: для входа клиента используется временный коннектор.";

	public static final String HIGHT_SECURITY_TYPE_DESCRIPTION_PENSIONER                    = "Присвоен высокий уровень безопасности: клиент пенсионер.";
	public static final String HIGHT_SECURITY_TYPE_DESCRIPTION_EMPTY_REGISTRATION_CHANNEL   = "Присвоен высокий уровень безопасности: в МБ не определён канал изменения/подключения номера.";
	public static final String HIGHT_SECURITY_TYPE_DESCRIPTION_REGISTRATION_DATE            = "Присвоен высокий уровень безопасности: клиент изменил номер менее %s дней назад.";

	public static final String SECURITY_TYPE_LOGIN_TYPE_DESCRIPTION                         = "Клиент входит по логину ";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	/**
	 * Рассчитать уровень безопасности
	 * @param userInfo информация о пользователе
	 * @return уровень безопасности
	 */
	public static SecurityType calcSecurityType(CSAUserInfo userInfo) throws GateLogicException, GateException
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}
		return calcSecurityType(userInfo.getBirthdate(), userInfo.getCardNumber(), null);
	}
	/**
	 * Рассчитать уровень безопасности
	 * @param userInfo информация о пользователе
	 * @param connectorType тип коннектора
	 * @return уровень безопасности
	 */
	public static SecurityType calcSecurityType(CSAUserInfo userInfo, ConnectorType connectorType) throws GateLogicException, GateException
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}
		return calcSecurityType(userInfo.getBirthdate(), userInfo.getCardNumber(), connectorType);
	}

	/**
	 * Рассчитать уровень безопасности
	 * @param profile профиль
	 * @param cardNumber номер карты
	 * @param connectorType тип коннектора
	 * @return уровень безопасности
	 */
	public static SecurityType calcSecurityType(Profile profile, String cardNumber, ConnectorType connectorType) throws GateLogicException, GateException
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("Номер карты не может быть null");
		}
		return calcSecurityType(profile.getBirthdate(), cardNumber, connectorType);
	}

	private static SecurityType calcSecurityType(Calendar birthDate, String cardNumber, ConnectorType connectorType) throws GateException, GateLogicException
	{
		//Если cardNumber пуст, то выбираем максимальный уровень.
		if (StringHelper.isEmpty(cardNumber))
		{
			logSecurityType(HIGHT_SECURITY_TYPE_DESCRIPTION_EMPTY_REGISTRATION_CHANNEL, connectorType);
			return SecurityType.HIGHT;
		}

		// Если клиент пенсионер - высокий уровень
		if (DateHelper.yearsDiff(birthDate, Calendar.getInstance()) >= Constants.PENSION_AGE)
		{
			logSecurityType(HIGHT_SECURITY_TYPE_DESCRIPTION_PENSIONER, connectorType);
			return SecurityType.HIGHT;
		}
		// Данные настройки уровня безопасности
		ClientSecurityLevelConfig config = ClientSecurityLevelConfig.getInstance();
		Date startDate = config.getStartDate();
		boolean inERIB = config.isChangePhoneByERIB();
		boolean inVSP = config.isChangePhoneByVSP();
		boolean inATM = config.isChangePhoneByATM();
		boolean inCC = config.isChangePhoneByCC();
		// Если канал не выбран, значит уровень безопасности низкий
		if (!(inATM || inCC || inERIB || inVSP))
		{
			logSecurityType(LOW_SECURITY_TYPE_DESCRIPTION_EMPTY_CHANNELS_SETTINGS);
			return SecurityType.LOW;
		}
		//Список регистраций карты в АС МБ
		List<MobileBankRegistration3> registrations = MobileBankService.getInstance().getRegistrations3(cardNumber);
		// уровень высокий присваивается, если хотя бы для одной регистрации по карте выполняется условие:
		// дата начала действия (startDate) <= дата последней регистрации через канал, отмеченный в настройке > текущая дата минус количество дней из настройки
		for (MobileBankRegistration3 registration : registrations)
		{
			if (registration.getLastRegistrationDate().before(getEndDate(config.getLessCountDays())))
			{
				logSecurityType(SecurityType.MIDDLE.getDescription());
				return SecurityType.MIDDLE;
			}
			else
			{
				// Если канал учета лимитов нулл, значит из мобильного банка пришло значение src 0 - нет данных, возвращаем высокий уровень безопасности
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
		// Во всех остальных случаях уровень безопасности - средний
		logSecurityType(SecurityType.MIDDLE.getDescription());
		return SecurityType.MIDDLE;
	}

	/**
	 * текущая дата за вычетом количества дней из настройки уровня безопасности
	 * @return конечная дата для проверки на высокий уровень
	 */
	private static Date getEndDate(int daysNumber)
	{
		return DateHelper.addDays(Calendar.getInstance(), -daysNumber).getTime();
	}

	/**
	 * @param channelType тип канала
	 * @param inERIB выбран ЕРИБ
	 * @param inATM  выбран УС
	 * @param inVSP  выбран ВСП
	 * @param inCC   выбран КЦ
	 * @return выбран ли тип канала в настройке
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
	 * Логируем присвоение уровня безопасности в SystemLog уровень ERROR
	 * @param message сообщение
	 */
	public static void logSecurityType(String message)
	{
		log.error(message);
	}

	/**
	 * Логируем присвоение уровня безопасности в SystemLog уровень ERROR
	 * @param message сообщение
	 * @param connectorType тип коннектора
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
