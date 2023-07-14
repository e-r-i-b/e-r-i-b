package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class Profile extends ActiveRecord
{
	private Long id;
	private String firstname;
	private String patrname;
	private String surname;
	private Calendar birthdate;
	private String passport;
	private Password password;
	private String tb;
	private boolean incognito;
	private String agreementNumber;
	private CreationType creationType;
	private String phone;
	private SecurityType securityType;

	public Profile()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getPatrname()
	{
		return patrname;
	}

	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return нормализованное ФИО (без лишних пробелов и пр ереси)
	 */
	public String getNormalizedFIO()
	{
		return normalizeFIO(getSurname(), getFirstname(), getPatrname());
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return нормализованный паспорт(без проблеов и пр ереси)
	 */
	public String getNormalizedPassport()
	{
		return normalizePassport(getPassport());
	}

	public Password getMapiPassword()
	{
		return password;
	}

	public void setMapiPassword(Password password)
	{
		this.password = password;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public boolean isIncognito()
	{
		return incognito;
	}

	public void setIncognito(boolean incognito)
	{
		this.incognito = incognito;
	}

	/**
	 * @return номер договора
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * задать номер договора
	 * @param agreementNumber номер договора
	 */
	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	/**
	 * @return тип договора
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * задать тип договора
	 * @param creationType тип договора
	 */
	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	/**
	 * @return номер телефона
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * задать номер телефона
	 * @param phone номер телефона
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * Создать профиль по информации о пользователе
	 * При создании профиль сохраняется в БД.
	 * @param userInfo информация оп опльзователе
	 * @return созданнный профиль
	 * @throws Exception
	 */
	public static Profile create(CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("информация о пользователе не должен быть null");
		}
		return create(userInfo, SecurityTypeHelper.calcSecurityType(userInfo));
	}

	/**
	 * Создать профиль по информации о пользователе
	 * При создании профиль сохраняется в БД.
	 * @param userInfo информация оп опльзователе
	 * @param securityType уровень безопасности
	 * @return созданнный профиль
	 * @throws Exception
	 */
	public static Profile create(CSAUserInfo userInfo, SecurityType securityType) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("информация о пользователе не должен быть null");
		}

		return create(userInfo.getFirstname(), userInfo.getPatrname(), userInfo.getSurname(), userInfo.getBirthdate(), userInfo.getPassport(), Utils.getCutTBByCBCode(userInfo.getCbCode()), securityType);
	}

	/**
	 * Создать профиль по информации о пользователе
	 * При создании профиль сохраняется в БД.
	 * @param firstname имя
	 * @param patrname отчество
	 * @param surname фамилия
	 * @param birthdate дата рождения
	 * @param passport ДУЛ
	 * @param tb ТБ
	 * @param securityType уровень безопасности
	 * @return созданнный профиль
	 * @throws Exception
	 */
	public static Profile create(String firstname, String patrname, String surname, Calendar birthdate, String passport, String tb, SecurityType securityType) throws Exception
	{
		Profile profile = new Profile();
		profile.setFirstname(firstname);
		profile.setPatrname(patrname);
		profile.setSurname(surname);
		profile.setBirthdate(birthdate);
		profile.setPassport(passport);
		profile.setTb(Utils.getMainTB(tb));
		profile.setSecurityType(securityType);
		profile.save();
		return profile;
	}

	/**
	 * Удалить текущий профиль
	 * Если передан актуальный профиль все связанные с профилем сущности будут перепривязаны к нему.
	 * @param actualProfile профиль, к которому требуется перепривязка связанных сущностей(правоприемник)
	 */
	public void delete(final Profile actualProfile) throws Exception
	{
		if (actualProfile == null)
		{
			super.delete();
		}
		executeAtomic(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				log.info("Начинаем процедуру мержа профиля " + getId() + " в профиль " + actualProfile.getId());
				session.lock(Profile.this, LockMode.UPGRADE_NOWAIT);
				session.lock(actualProfile, LockMode.UPGRADE_NOWAIT);

				Profile oldProfile = Profile.this;
				log.trace("Помещаем в историю информацию о профиле " + oldProfile.getId());
				ProfileHistory.create(oldProfile);

				//устанавливаем уровень безопасности текущего профиля всем незакрытым ЦСА и МАПИ коннекторам текущего профиля
				CSAConnector.setSecurityTypeToNotClosed(oldProfile);
				MAPIConnector.setSecurityTypeToNotClosed(oldProfile.getId(), oldProfile.getSecurityType());

				//перепривязываем к актуальному профилю сущности от текущего
				Integer count = Connector.changeProfile(oldProfile, actualProfile);
				log.info("К профилю " + actualProfile.getId() + " перепривязано " + count + " коннекторов профиля " + oldProfile.getId());
				count = Operation.changeProfile(oldProfile, actualProfile);
				log.info("К профилю " + actualProfile.getId() + " перепривязано " + count + " заявок профиля " + oldProfile.getId());
				count = ProfileHistory.changeProfile(oldProfile, actualProfile);
				log.info("К профилю " + actualProfile.getId() + " перепривязано " + count + " записей историй изменений профиля " + oldProfile.getId());
				count = ProfileLock.changeProfile(oldProfile, actualProfile);
				log.info("К профилю " + actualProfile.getId() + " перепривязано " + count + " записей о блокировках профиля " + oldProfile.getId());
				count = ProfileNode.changeProfile(oldProfile, actualProfile);
				log.info("К профилю " + actualProfile.getId() + " перепривязано " + count + " записей о связи клиента с блоками " + oldProfile.getId());

				log.trace("Актуализируем пароль для профиля " + actualProfile.getId());
				Password mapiPassword1 = oldProfile.getMapiPassword();
				if (mapiPassword1 != null)
				{
					Password mapiPassword2 = actualProfile.getMapiPassword();
					if (mapiPassword2 == null || mapiPassword1.getCreationDate().after(mapiPassword2.getCreationDate()))
					{
						log.info("Пароль на доступ к мобильному приложению в профиле " + oldProfile.getId() + " актуальнее пароля профиля " + actualProfile.getId() + ". Обновляем пароль на доступ к мобильному приложению для профиля " + actualProfile.getId());
						actualProfile.setMapiPassword(mapiPassword1);
					}
				}

				log.info("Удален профиль " + oldProfile.getId());
				mergeMDMId(actualProfile.getId(), oldProfile.getId());
				delete();
				return null;
			}
		});
	}

	/**
	 * Обновить профиль информацией о пользователе
	 * @param userInfo информация о пользователе
	 */
	public void update(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не должен быть null");
		}
		executeAtomic(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				ProfileHistory.create(Profile.this);

				setFirstname(userInfo.getFirstname());
				setPatrname(userInfo.getPatrname());
				setSurname(userInfo.getSurname());
				setBirthdate(userInfo.getBirthdate());
				setPassport(userInfo.getPassport());
				//Если в фиктивном профиле мигрированного клиента ТБ=00 сетим значение из ЦБ-кода
				if (tb.equals("00") && userInfo.getCbCode() != null)
					setTb(Utils.getCutTBByCBCode(userInfo.getCbCode()));
				save();
				return null;
			}
		});
	}

	/**
	 * Заблокировать профиль для входа
	 * @param from дата начала блокировки
	 * @param to дата окончания блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио сотрудника, блокирующего запись.
	 */
	public void lock(Calendar from, Calendar to, String reason, String blockerFIO) throws Exception
	{
		ProfileLock.create(this, from, to, reason, blockerFIO);
	}

	/**
	 * Добавить метку о блокировке входа клиента в ЕРИБ.
	 * Блокировки профиля в ЦСА не происходит.
	 * @param from дата начала блокировки
	 * @param to дата окончания блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио сотрудника, блокирующего запись.
	 */
	public void lockForCHG071536(Calendar from, Calendar to, String reason, String blockerFIO) throws Exception
	{
		ProfileLockCHG071536.create(this, from, to, reason, blockerFIO);
	}

	/**
	 * Снять все блокировки с профиля.
	 * Под снятием блокировки понимается простановка даты окончания дествия блокировки текущим таймстампом.
	 */
	public void unlock() throws Exception
	{
		ProfileLock.unblock(this);
	}

	/**
	 * @return активные на текущий момент блокировки пользователя или пустой список
	 */
	public List<ProfileLock> getActiveLocks() throws Exception
	{
		return ProfileLock.findActiveByProfile(this);
	}

	/**
	 * Найти профиль по идентификатору
	 * @param profileId идентифкатор
	 * @param lockMode режим блокировки
	 * @return найденный профиль или null
	 * @throws Exception
	 */
	public static Profile findById(Long profileId, LockMode lockMode) throws Exception
	{
		return findById(Profile.class, profileId, lockMode, getInstanceName());
	}

	private static Profile getProfileByUID(final String firstname, final String patrname, final String surname, final Calendar birthdate, final String passport, final String tb, final boolean withLock) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Profile>()
		{
			public Profile run(org.hibernate.Session session) throws Exception
			{
				return (Profile) session.getNamedQuery(withLock ? "com.rssl.auth.csa.back.servises.Profile.getByUniversalId.withLock" : "com.rssl.auth.csa.back.servises.Profile.getByUniversalId")
						.setParameter("firstname", firstname)
						.setParameter("patrname", patrname)
						.setParameter("surname", surname)
						.setParameter("passport", passport)
						.setParameter("birthdate", birthdate)
						.setParameter("tb", tb)
						.uniqueResult();
			}
		});
	}

	/**
	 * Получить идентификатор профиля по информации о пользователе
	 * @param userInfo информация о пользователе
	 * @return id профиля
	 * @throws Exception
	 */
	public static Long getProfileIdByUID(final CSAUserInfo userInfo) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Long>()
		{
			public Long run(org.hibernate.Session session) throws Exception
			{
				return (Long) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getProfileIdByUniversalId")
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("surname", userInfo.getPatrname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.setParameter("tb", null)
						.uniqueResult();
			}
		});
	}

	private static List<Profile> getProfilesByUID(final String firstname, final String patrname, final String surname, final Calendar birthdate, final String passport, final boolean withLock) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Profile>>()
		{
			public List<Profile> run(org.hibernate.Session session) throws Exception
			{
				//noinspection unchecked
				return (List<Profile>) session.getNamedQuery(withLock ? "com.rssl.auth.csa.back.servises.Profile.getByUniversalId.withLock" : "com.rssl.auth.csa.back.servises.Profile.getByUniversalId")
						.setParameter("firstname", firstname)
						.setParameter("patrname", patrname)
						.setParameter("surname", surname)
						.setParameter("passport", passport)
						.setParameter("birthdate", birthdate)
						.setParameter("tb", null)
						.list();
			}
		});
	}

	/**
	 * получить профиль по информации о пользователе
	 * @param userInfo информация о пользовател
	 * @param withLock выполнять ли блокировку
	 * @return профиль, не может быть null.
	 * @throws Exception
	 */
	public static Profile getByUserInfo(final CSAUserInfo userInfo, final boolean withLock) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}

		return getProfileByUID(
				userInfo.getFirstname(),
				userInfo.getPatrname(),
				userInfo.getSurname(),
				userInfo.getBirthdate(),
				userInfo.getPassport(),
				Utils.getMainTBByCBCode(userInfo.getCbCode()),
				withLock);
	}

	/**
	 * получить профиля по информации о пользователе((без тб)
	 * @param userInfo информация о пользовател
	 * @param withLock выполнять ли блокировку
	 * @return профиль, не может быть null.
	 * @throws Exception
	 */
	public static List<Profile> getByUserInfoNoTb(final CSAUserInfo userInfo, final boolean withLock) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}


		return getProfilesByUID(
				userInfo.getFirstname(),
				userInfo.getPatrname(),
				userInfo.getSurname(),
				userInfo.getBirthdate(),
				userInfo.getPassport(),
				withLock);
	}

	/**
	 * получить профиль по шаблону
	 * @param template информация о пользовател
	 * @param withLock выполнять ли блокировку
	 * @return профиль, не может быть null.
	 * @throws Exception
	 */
	public static Profile getByTemplate(final Profile template, final boolean withLock) throws Exception
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Шаблон профиля не может быть null");
		}

		return getProfileByUID(
				template.getFirstname(),
				template.getPatrname(),
				template.getSurname(),
				template.getBirthdate(),
				template.getPassport(),
				Utils.getMainTB(template.getTb()),
				withLock);
	}

	/**
	 * Найти все профили, для которых есть коннектор с заданным логином ipas
	 * @param userId логин iPas
	 * @return список профилей или пустой список
	 * @throws Exception
	 */
	public static List<Profile> findByUserId(final String userId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Profile>>()
		{
			public List<Profile> run(org.hibernate.Session session) throws Exception
			{
				return (List<Profile>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getByUserId")
						.setParameter("user_id", userId)
						.list();
			}
		});
	}

	/**
	 * Найти все профили, для которых есть коннектор с заданной картой
	 * @param cardNumber номер карты
	 * @return список профилей или пустой список
	 * @throws Exception
	 */
	public static Collection<? extends Profile> findByCardNumber(final String cardNumber) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Profile>>()
		{
			public List<Profile> run(org.hibernate.Session session) throws Exception
			{
				return (List<Profile>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getByCardNumber")
						.setParameter("card_number", cardNumber)
						.list();
			}
		});
	}

	public static boolean isExistProfile(final String cardNumber, final String userId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.isExistProfileByUserInfo")
						.setParameter("card_number", cardNumber)
						.setParameter("user_id", userId)
						.list().isEmpty();
			}
		});
	}




	/**
	 * Нормализовать паспорт(вырезать все пробелы).
	 * REPLACE(PASSPORT,' ','')
	 * @param passport для нормализации
	 * @return нормализованный паспорт.
	 */
	public static String normalizePassport(String passport)
	{
		if (passport == null)
		{
			return null;
		}
		return passport.replace(" ", "");
	}

	/**
	 * Нормализовать ФИО (вырезать повторяющиеся пробелы и привести к апперкейсу).
	 * UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' ')))
	 * @param surname фамилия
	 * @param firstname имя
	 * @param patrname отчество
	 * @return нормализованное ФИО.
	 */
	public static String normalizeFIO(String surname, String firstname, String patrname)
	{
		if (surname == null && firstname == null && patrname == null)
		{
			return null;
		}
		String fio = StringHelper.getEmptyIfNull(surname) + " " + StringHelper.getEmptyIfNull(firstname) + " " + StringHelper.getEmptyIfNull(patrname);

		return fio.trim().replaceAll("( )+", " ").toUpperCase();
	}

	public SecurityType getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(SecurityType securityType)
	{
		this.securityType = securityType;
	}

	/**
	 * Получение списка номеров карт профиля
	 * @param profileTemlate - данные профиля
	 * @return список номеров карт
	 * @throws Exception
	 */
	public static List<String> getCardNumberList(final Profile profileTemlate) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(org.hibernate.Session session) throws Exception
				{
					return (List<String>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getCardNumberList")
							.setParameter("firstname", profileTemlate.getFirstname())
							.setParameter("patrname", profileTemlate.getPatrname())
							.setParameter("surname", profileTemlate.getSurname())
							.setParameter("passport", profileTemlate.getPassport())
							.setParameter("birthdate", profileTemlate.getBirthdate())
							.setParameter("tb", profileTemlate.getTb())
							.list();
				}
			});
	}

	/**
	 * Обновление профиля данными из мдм
	 * @param profileId идентификатор профиля
	 * @param mdmId идентификатор в мдм
	 * @throws Exception
	 */
	public static void updateClientMDMInfo(final Long profileId, final String mdmId) throws Exception
	{
		ExecutorQuery queryUpdate = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Profile.updateClientMDMInfo");
		queryUpdate.setParameter("PROFILE_ID", profileId);
		queryUpdate.setParameter("MDM_ID", mdmId);
		if(queryUpdate.executeUpdate() != 0)
			return;

		ExecutorQuery queryInsert = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Profile.insertClientMDMInfo");
		queryInsert.setParameter("PROFILE_ID", profileId);
		queryInsert.setParameter("MDM_ID", mdmId);
		queryInsert.executeUpdate();
	}

	/**
	 * Обновление профиля данными из мдм
	 * @param actualProfileId идентификатор профиля
	 * @param oldProfileId идентификатор удаляемого профиля
	 */
	public static void mergeMDMId(Long actualProfileId, Long oldProfileId)
	{
		try
		{
			ExecutorQuery queryUpdate = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Profile.mergeMDMId");
			queryUpdate.setParameter("NEW_PROFILE_ID", actualProfileId);
			queryUpdate.setParameter("OLD_PROFILE_ID", oldProfileId);
			queryUpdate.executeUpdate();
		}
		catch (Exception e)
		{
			log.error("Не удалось смержить идентификатор МДМ", e);
		}
	}

	/**
	 * поставить лок на профиль для исполнения документа сотрудником
	 * @return поставился ли лок
	 * @throws Exception
	 */
	public boolean lockForExecuteDocument() throws Exception
	{
		return ProfileNode.lockProfileForExecuteDocument(this);
	}

	/**
	 * снять лок с профиля после исполнения документа сотрудником
	 * @return снялся ли лок
	 * @throws Exception
	 */
	public boolean unlockForExecuteDocument() throws Exception
	{
		return ProfileNode.unlockProfileForExecuteDocument(this);
	}

}
