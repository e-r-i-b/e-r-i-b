package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.auth.csa.back.servises.restrictions.UnsupportedOperationRestriction;
import com.rssl.auth.csa.back.servises.restrictions.security.MAPIPasswordSecurityRestriction;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

/**
 * @author krenev
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MAPIConnector extends PasswordBasedConnector
{
	public MAPIConnector() {}

	public MAPIConnector(String ouid, String userId, String cbCode, String cardNumber, String deviceInfo, String deviceState, Profile profile, String deviceId)
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
		setGuid(ouid);
		setUserId(userId);
		setProfile(profile);
		setCbCode(cbCode);
		setCardNumber(cardNumber);
		setDeviceInfo(deviceInfo);
		setDeviceState(deviceState);
		setRegistrationType(RegistrationType.SELF);
		setDeviceId(deviceId);
	}

	public MAPIConnector(String ouid, String userId, String cbCode, String cardNumber, String deviceInfo, String deviceState, Profile profile, String deviceId, LoginType registrationLoginType)
	{
		this(ouid, userId, cbCode, cardNumber, deviceInfo, deviceState, profile, deviceId);
		if (registrationLoginType == null)
		{
			throw new IllegalArgumentException("Тип логина, по которому регистрируется приложение, не может быть null");
		}
		setRegistrationLoginType(registrationLoginType);
	}

	public ConnectorType getType()
	{
		return ConnectorType.MAPI;
	}

	public boolean isMigrated()
	{
		return false;  //мапи коннекторы пока ниоткуда не мигрируются
	}

	public void generatePassword(Set<String> excludedPhones) throws Exception
	{
		throw new UnsupportedOperationException("Генерация пароля для mAPIконнектора не предусмотрена");
	}

	protected boolean setPassword(String passwordValue) throws Exception
	{
		Password prevPassword = getPassword();
		Profile profile = getProfile();
		profile.setMapiPassword(new Password(passwordValue));
		profile.save();
		return prevPassword != null && !prevPassword.check(passwordValue);
	}

	protected Password getPassword()
	{
		return getProfile().getMapiPassword();
	}

	public Restriction<String> getPasswordRestriction()
	{
		return MAPIPasswordSecurityRestriction.getInstance();
	}

	public Restriction<String> getLoginRestriction()
	{
		return new UnsupportedOperationRestriction<String>("mAPI коннектор не поддерживает смену логина");
	}

	@Override
	public SecurityType calcSecurityType() throws Exception
	{
		//Если мАпи регистрировалось по iPas логину или алиасу, то возвращается низкий уровень безопасности
		if (getRegistrationLoginType() == null || getRegistrationLoginType() == LoginType.TERMINAL)
		{
			return SecurityType.LOW;
		}

		SecurityType profileSecurityType = getProfile().getSecurityType();
		//Если уровень безопасности пустой и для профиля и для коннектора, пересчитываем заново и выставляем профилю
		if (profileSecurityType == null && getSecurityType() == null)
		{
			SecurityType securityType = SecurityTypeHelper.calcSecurityType(asUserInfo(), getType());
			getProfile().setSecurityType(securityType);
			getProfile().save();
			return securityType;
		}
		//В остальных случаях в первую очредь смотрим на уровень безопасности профиля
		SecurityType result = profileSecurityType == null ? getSecurityType() : profileSecurityType;
		SecurityTypeHelper.logSecurityType(result.getDescription());
		return result;
	}

	/**
	 * Установить всем незакрытым коннекторам профиля уровень безопасности профиля
	 * @param profileId Идентификатор профиля
	 * @param securityType уровень безопасности
	 * @return число обновленных коннекторов
	 * @throws Exception
	 */
	public static int setSecurityTypeToNotClosed(final Long profileId, final SecurityType securityType) throws Exception
	{
		return setSecurityTypeToNotClosed(profileId, securityType, ConnectorType.MAPI);
	}

	/**
	 * Получить список незакрытых коннекторов профиля
	 * @param profileId идентифкатор профиля
	 * @return список коннекторов или пустой список
	 * @throws Exception
	 */
	public static List<MAPIConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, MAPIConnector.class);
	}

	/**
	 * Получить список всех незакрытых mAPI коннекторов по ФИО+ДУЛ+ДР клиента
	 * @return список коннекторов
	 * @throws Exception
	 * @param userInfo - клиент
	 */
	public static List<MAPIConnector> findNotClosedByClient(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Список профилей не может быть пустым");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<MAPIConnector>>()
		{
			public List<MAPIConnector> run(org.hibernate.Session session) throws Exception
			{
				return (List<MAPIConnector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.findNotClosedMAPIByClient")
						.setParameter("surname", userInfo.getSurname())
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.list();
			}
		});
	}

	/**
	 * @param userInfo информация о клиенте
	 * @return имеется ли у клиента хотя бы одно мАпи ПРО-версии в любом из подразделений
	 * @throws Exception
	 */
	public static boolean isContainsPRO(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о клиенте не может быть null.");
		}

		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				return !session.getNamedQuery("com.rssl.auth.csa.back.services.connectors.MAPIConnector.isContainsPRO")
						.setParameter("surname", userInfo.getSurname())
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.list().isEmpty();
			}
		});
	}
}
