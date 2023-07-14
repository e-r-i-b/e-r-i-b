package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.IPasUnavailableException;
import com.rssl.auth.csa.back.exceptions.RetryIPasUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.AdjacentServiceUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.ServiceUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.store.PasswordStoreIPasService;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @author krenev
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class TerminalConnector extends Connector
{
	//Коды ошибки сигнализирующее о недоступности сервиса iPas(временная непредвиденая ошибка)
	private static final List<String> RETRY_IPAS_UNAVAILABLE = Arrays.asList("ERR_PRMFMT", "ERR_FORMAT", "ERROR");;

	public TerminalConnector() {}

	public TerminalConnector(String userId, String cbCode, String cardNumber, Profile profile)
	{
		if (StringHelper.isEmpty(userId))
		{
			throw new IllegalArgumentException("Логин iPas не может быть null");
		}
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("cbCode не может быть null");
		}
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("Номер карты не может быть null");
		}
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		setState(ConnectorState.ACTIVE);
		setGuid(Utils.generateGUID());
		setUserId(userId);
		setCbCode(cbCode);
		setCardNumber(cardNumber);
		setProfile(profile);
		setRegistrationType(RegistrationType.REMOTE);
		setSecurityType(SecurityType.LOW);
	}

	public ConnectorType getType()
	{
		return ConnectorType.TERMINAL;
	}

	public boolean isMigrated()
	{
		//Критерием мигрированности коннектора является забитый в качестве номера карты ipas логин.
		//Ну не откуда взять из старой ЦСА номер карты.
		return Utils.isIPasLogin(getCardNumber());
	}

	public CSAUserInfo checkPassword(String password) throws Exception
	{
		try
		{
			return PasswordStoreIPasService.getInstance().verifyPassword(this, password);
		}
		catch (AdjacentServiceUnavailableException e)
		{
			if (RETRY_IPAS_UNAVAILABLE.contains(e.getErrorCode()))
				throw new RetryIPasUnavailableException("К сожалению, соединение не было установлено. Пожалуйста, повторите вход в \"Сбербанк Онлайн\".", e);
			throw new IPasUnavailableException(e, this);
		}
		catch (ServiceUnavailableException e)
		{
			throw new IPasUnavailableException(e, this);
		}
	}

	public void generatePassword(Set<String> excludedPhones) throws Exception
	{
		//При восстановлении пароля для входа в ЕРИБ, если пароль клиента хранится в iPAS и клиент подключен к ЕРМБ, вызываем процедуру генерации пароля для ЕРМБ-клиента
		List<ERMBConnector> ermbConnectors = ERMBConnector.findNotClosedByProfileID(getProfile().getId());
		if (CollectionUtils.isNotEmpty(ermbConnectors))
		{
			String phoneNumber = ermbConnectors.get(0).getPhoneNumber();
			if (excludedPhones == null || !excludedPhones.contains(phoneNumber))
				new ERMBPasswordGenerator(getCardNumber(), phoneNumber).generatePassword();
		}

		//Если пароль клиента хранится в iPAS и клиент подключен к МБК, вызываем процедуру генерации пароля для МБК-клиента
		else
			new MBPasswordGenerator(getCardNumber()).generatePassword();
	}

	public Calendar getPasswordCreationDate()
	{
		return null;
	}

	/**
	 * Найти незакрытый коннектор на доступ в вебверсию по логину iPas
	 * @param userId логин Ipas
	 * @return коннектор или null
	 * @throws Exception
	 */
	public static TerminalConnector findNotClosedByUserId(final String userId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<TerminalConnector>()
		{
			public TerminalConnector run(org.hibernate.Session session) throws Exception
			{
				return (TerminalConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.TerminalConnector.getNotClosedByUserID")
						.setParameter("user_id", userId)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти незакрытый коннектор на доступ в вебверсию по номеру карты
	 * @param cardNumber номер карты
	 * @return коннектор или null
	 * @throws Exception
	 */
	public static TerminalConnector findNotClosedByCardNumber(final String cardNumber) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<TerminalConnector>()
		{
			public TerminalConnector run(org.hibernate.Session session) throws Exception
			{
				return (TerminalConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.TerminalConnector.getNotClosedByCardNumber")
						.setParameter("card_number", cardNumber)
						.uniqueResult();
			}
		});
	}

	@Override
	protected String getLogSecurityTypeMessage()
	{
		return SecurityTypeHelper.LOW_SECURITY_TYPE_DESCRIPTION_IPAS;
	}

	/**
	 * Получить список незакрытых коннекторов профиля
	 * @param profileId идентифкатор профиля
	 * @return список коннекторов или пустой список
	 * @throws Exception
	 */
	public static List<TerminalConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, TerminalConnector.class);
	}
}
