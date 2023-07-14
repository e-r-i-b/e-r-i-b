package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ATMConnector extends Connector
{
	/**
	 * ctor
	 */
	public ATMConnector() {}

	/**
	 * ctor
	 * @param cardNumber номер карты
	 * @param userId логин iPas
	 * @param cbCode cbCode
	 * @param profile профиль
	 */
	public ATMConnector(String cardNumber, String userId, String cbCode, Profile profile)
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("Номер карты не может быть null");
		}
		if (StringHelper.isEmpty(userId))
		{
			throw new IllegalArgumentException("Логин iPas не может быть null");
		}
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("cbCode карты не может быть null");
		}
		if (profile == null)
		{
			throw  new IllegalArgumentException("Профиль не может быть null");
		}
		setCardNumber(cardNumber);
		setGuid(Utils.generateGUID());
		setUserId(userId);
		setProfile(profile);
		setCbCode(cbCode);
		setRegistrationType(RegistrationType.REMOTE);
		setState(ConnectorState.ACTIVE);
		setSecurityType(SecurityType.LOW);
	}

	@Override
	public ConnectorType getType()
	{
		return ConnectorType.ATM;
	}

	@Override
	public boolean isMigrated()
	{
		return false; //не поддерживается
	}

	@Override
	protected CSAUserInfo checkPassword(String password) throws Exception
	{
		return null;
	}

	@Override
	public void generatePassword(Set<String> excludedPhones) throws Exception
	{
		throw new UnsupportedOperationException("Генерация пароля для АТМ коннектора не предусмотрена");
	}

	@Override
	public Calendar getPasswordCreationDate() throws Exception
	{
		return null;
	}

	/**
	 * Найти АТМ коннектор по номеру карты
	 * @param cardNumber номер карты
	 * @param lockMode режим блокировки, если не задан, то без блокировки
	 * @return коннектор
	 * @throws Exception
	 */
	public static Connector findByCardNumber(final String cardNumber, final LockMode lockMode) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Connector>()
		{
			public Connector run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ATMConnector.findByCardNumber");
				query.setParameter("cardNumber", cardNumber);
				if (lockMode != null)
				{
					query.setLockMode("connector", lockMode);
				}
				return (Connector) query.uniqueResult();
			}
		});
	}

	@Override
	protected String getLogSecurityTypeMessage()
	{
		return SecurityTypeHelper.LOW_SECURITY_TYPE_DESCRIPTION_ATM;
	}

	/**
	 * Получить список незакрытых коннекторов профиля
	 * @param profileId идентифкатор профиля
	 * @return список коннекторов или пустой список
	 * @throws Exception
	 */
	public static List<ATMConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, ATMConnector.class);
	}
}
