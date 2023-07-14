package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 12.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Коннектор для аутентификации ЕРМБ. Обязательно наличие номера телефона.
 */
public class ERMBConnector extends Connector
{
	/**
	 * ctor
	 */
	public ERMBConnector()
	{}

	@Override
	public ConnectorType getType()
	{
		return ConnectorType.ERMB;
	}

	@Override
	public boolean isMigrated()
	{
		return false; //ЕРМБ коннекторы пока ни откуда не мигрируются
	}

	@Override
	protected CSAUserInfo checkPassword(String password) throws Exception
	{
		return null;
	}

	@Override
	public void generatePassword(Set<String> excludedPhones) throws Exception
	{
		throw new UnsupportedOperationException("Генерация пароля для ERMB коннектора не предусмотрена");
	}

	@Override
	public Calendar getPasswordCreationDate() throws Exception
	{
		return null;
	}

	/**
	 * Создать ЕРМБ коннектор
	 * @param context контекст идентификации
	 * @param phoneNumber номер телефона
	 * @param state статус
	 * @return коннектор
	 * @throws Exception
	 */
	public static ERMBConnector create(IdentificationContext context, String phoneNumber, ConnectorState state) throws Exception
	{
		if (context == null)
		{
			throw new IllegalArgumentException("Контекст идентификации не может быть null");
		}
		if (StringHelper.isEmpty(phoneNumber))
		{
			throw new IllegalArgumentException("Номер телефона не может быть null");
		}
		ERMBConnector connector = new ERMBConnector();
		connector.setPhoneNumber(phoneNumber);
		connector.setGuid(Utils.generateGUID());
		connector.setProfile(context.getProfile());
		connector.setCbCode(context.getCbCode());
		connector.setRegistrationType(RegistrationType.REMOTE);
		connector.setState(state == null ? ConnectorState.CLOSED : state);
		connector.save();
		return connector;
	}

	/**
	 * Сделать текущий активный коннектор неактинвым
	 * @param profileId идентификатор профиля
	 * @return количество изменненых коннекторов
	 * @throws Exception
	 */
	public static Integer resetProfileActiveConnector(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.resetProfileActiveConnector")
						.setParameter("profile_id", profileId)
						.executeUpdate();
			}
		});
	}

	/**
	 * сделать первый попавшийся ЕРМБ коннектор активным
	 * @param profileId иднетификатор профиля
	 * @throws Exception
	 */
	public static void setActiveFirstConnectorByProfile(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		getHibernateExecutor().execute(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.setActiveFirstConnectorByProfile")
						.setParameter("profile_id", profileId)
						.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * Установить коннектор активным по телефону и идентификатору профиля
	 * @param profileId идентификатор профиля
	 * @param phoneNumber номер телефона
	 * @return количество изменненых коннекторов
	 * @throws Exception
	 */
	public static Integer setProfileActiveConnector(final Long profileId, final String phoneNumber) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.setProfileActiveConnector")
						.setParameter("profile_id", profileId)
						.setParameter("phone_number", phoneNumber)
						.executeUpdate();
			}
		});
	}

	/**
	 * Удалить коннекторы по списку телефонов
	 * @param phones список телефонов
	 * @param profileId идентификатор профиля
	 * @throws Exception
	 */
	public static void removeByPhonesAndProfileId(final List<String> phones, final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		getHibernateExecutor().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.removeByPhonesAndProfileId")
						.setParameterList("phones", phones)
						.setParameter("profile_id", profileId)
						.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * Получить список уже зарегистрированных телефонов
	 * @param phones список добавляемых телефонов
	 * @return список дубликатов
	 * @throws Exception
	 */
	public static List<String> findDuplicatesPhones(final List<String> phones) throws Exception
	{
		if (CollectionUtils.isEmpty(phones))
		{
			throw new IllegalArgumentException("Список телефонов не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<String>>()
		{
			public List<String> run(Session session) throws Exception
			{
				return (List<String>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.findDuplicatesPhones")
						.setParameterList("phones", phones)
						.list();
			}
		});
	}

	/**
	 * Найти все коннекторы по идентификатору профиля
	 * @param profileId идентификаторы профиля
	 * @return ермб коннекторы
	 * @throws Exception
	 */
	public static List<ERMBConnector> getByProfileId(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ERMBConnector>>()
		{
			public List<ERMBConnector> run(Session session) throws Exception
			{
				return (List<ERMBConnector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.getByProfileId")
						.setParameter("profile_id", profileId)
						.list();
			}
		});
	}

	/**
	 * Найти все коннекторы по номеру карты:
	 * По номеру карты ищутся коннекторы, из найденных записей берется любая
	 * по идентификатору профиля записи происходит поиск ЕРМБ коннекторов
	 * @param cardNumber номер карты
	 * @return ермб конекторы
	 * @throws Exception
	 */
	public static List<ERMBConnector> getByCardNumber(final String cardNumber) throws Exception
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("Номер карты не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ERMBConnector>>()
		{
			public List<ERMBConnector> run(Session session) throws Exception
			{
				return (List<ERMBConnector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.getByCardNumber")
						.setParameter("card_number", cardNumber)
						.list();
			}
		});
	}

	/**
	 * Имеются ли у профиля незакрытие ЕРМБ коннекторы
	 * @param profileId идентификатор профиля
	 * @return true - существуют
	 * @throws Exception
	 */
	public static boolean isExistNotClosedByProfileId(final Long profileId) throws Exception
	{
		if (profileId == null)
			throw new IllegalArgumentException("profileId не может быть null");

		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.isExistNotClosedByProfileId")
						.setParameter("profileId", profileId)
						.setMaxResults(1)
						.uniqueResult() != null;
			}
		});
	}

	/**
	 * Получить список незакрытых коннекторов по идентфикатору профиля
	 * @param profileId идентифкатор профиля
	 * @return список незакрытых конекторов или пустой список
	 * @throws Exception
	 */
	public static List<ERMBConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, ERMBConnector.class);
	}

	/**
	 * Найти все коннекторы по ФИО ДУЛ ДР:
	 * @param surname фамилия
	 * @param firstname имя
	 * @param patrname отчество
	 * @param birthdate дата рождения
	 * @param passports список дул
	 * @return ермб конекторы
	 * @throws Exception
	 */
	public static List<ERMBConnector> findNotClosedByClientInAnyTB(final String surname, final String firstname, final String patrname, final Calendar birthdate, final Collection<String> passports) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<ERMBConnector>>()
		{
			public List<ERMBConnector> run(Session session)
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.findNotClosedByClientInAnyTB");
				query.setString("surname", surname);
				query.setString("firstname", firstname);
				query.setString("patrname", patrname);
				query.setCalendar("birthdate", birthdate);
				query.setParameterList("passports", StringHelper.deleteWhitespaces(passports));
				// noinspection unchecked
				return query.list();
			}
		});
	}

	/**
	 * Определить наличие ЕРМБ коннекторов для клиента
	 * @param userInfo информация по клиенту
	 * @return признак наличия
	 * @throws Exception
	 */
	public static boolean isExistNotClosedByClientInAnyTB(final CSAUserInfo userInfo) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session)
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.isExistNotClosedByClientInAnyTB");
				query.setString("surname", userInfo.getSurname());
				query.setString("firstname", userInfo.getFirstname());
				query.setString("patrname", userInfo.getPatrname());
				query.setCalendar("birthdate", userInfo.getBirthdate());
				query.setString("passport", userInfo.getPassport());
				String cbCode = userInfo.getCbCode();
				query.setString("tb", StringHelper.isNotEmpty(cbCode)? Utils.getMainTBByCBCode(cbCode): null);

				return BooleanUtils.isTrue((Boolean) query.uniqueResult());
			}
		});
	}
}
