package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.*;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.messages.MessageInfoImpl;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.auth.csa.back.servises.operations.CSASmsResourcesOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.*;

import java.util.Calendar;
import java.util.List;

/**
 * Базовый класс для операций полноценного клиента
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class Operation extends OperationBase
{

	private static final String REPLACING_ENTRY_DELIMITER_SEQUENCE = "&{delEntry}&";
	private static final String REPLACING_VALUE_DELIMITER_SEQUENCE = "&{delValue}&";

	private Long profileId;
	private String firstname;
	private String patrname;
	private String surname;
	private Calendar birthdate;
	private String passport;
	private String cbCode;

	public Operation() {}

	public Operation(IdentificationContext identificationContext)
	{
		if (identificationContext == null)
		{
			throw new IllegalArgumentException("Контекст идентификации не может быть null");
		}
		Profile profile = identificationContext.getProfile();
		this.profileId = profile.getId();
		this.firstname = profile.getFirstname();
		this.patrname = profile.getPatrname();
		this.surname = profile.getSurname();
		this.birthdate = profile.getBirthdate();
		this.passport = profile.getPassport();
		this.cbCode = identificationContext.getCbCode();
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
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

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	/**
	 * Заблокировать профиль в текущей транзакции(SELECT FOR UPDATE NO WAIT).
	 * @return заблокированный профиль или null, если профиль не найден.
	 * @throws Exception
	 */
	public Profile lockProfile() throws Exception
	{
		return Profile.findById(profileId, LockMode.UPGRADE_NOWAIT);
	}

	/**
	 * @return профиль, в рамках которого совершалась операция
	 * @throws Exception
	 */
	public Profile getProfile() throws Exception
	{
		return Profile.findById(profileId, null);
	}

	/**
	 * Отправить оповещение на все телефоны, номера которых зарегистрированы в Мобильном банке для всех основных карт клиента.
	 * Оповещения носят чисто информационный характер, при проблемах все исключения глушатся с логированием.
	 * ВАЖНО: Используем только для случаев, где ошибки доставки сообщений не влияют на алгоритм действий.
	 * @param cardNumber номер карты, относительно которой ищутся регистрации.
	 */
	protected void sendNotification(String cardNumber) throws Exception
	{
		try
		{
			String   key  = getClass().getName() + ".notification";
			Object[] args = {
					            DateHelper.formatDateToStringWithSlash2(Calendar.getInstance()),
								Utils.maskCard(cardNumber),
								getIO()
							};

			String text = CSASmsResourcesOperation.getFormattedSmsResourcesText(key, args);
			SendMessageInfo sendMessageInfo = new SendMessageInfo(getProfileId(), cardNumber, new MessageInfoImpl(text, text), false, GetRegistrationMode.SOLF);

			SendMessageRouter.getInstance().sendMessage(sendMessageInfo);
		}
		catch (Exception e)
		{
		    log.error("Не удалось отправить оповещения по номеру карты " +  Utils.maskCard(cardNumber), e);
		}
	}

	protected String getIO()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(firstname);
		builder.append(" ");
		builder.append(patrname);
		return builder.toString();
	}

	/**
	 * Найти операцию по идентифкатору.
	 * @param ouid идентфикатор операции
	 * @return операция или null если не найдена.
	 */
	public static Operation findByOUID(final String ouid) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Operation>()
		{
			public Operation run(org.hibernate.Session session) throws Exception
			{
				return (Operation) session.getNamedQuery("com.rssl.auth.csa.back.servises.Operation.getByOUID")
						.setParameter("ouid", ouid)
						.uniqueResult();
			}
		});
	}

	/**
	 * Получить количество операций совершенных пользователем определенного класса в задданных статусах за период времени, начиная от текущего времени минус lifeTime
	 * @param profileId идентфикатор профиля пользователя
	 * @param aClass класс операции
	 * @param lifeTime lifeTime время жизни операции/глубина поиска в секундах
	 * @param states статусы
	 * @return количество этих самых операций.
	 */
	public static int getCount(final Long profileId, final Class<? extends Operation> aClass, final int lifeTime, final OperationState... states) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		if (aClass == null)
		{
			throw new IllegalArgumentException("Класс операции не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				Calendar endDate = Calendar.getInstance();
				return (Integer) session.getNamedQuery("com.rssl.auth.csa.back.servises.Operation.getCount")
						.setParameter("profile_id", profileId)
						.setParameter("type", aClass.getSimpleName())
						.setParameterList("states", states)
						.setParameter("end_date", endDate)
						.setParameter("start_date", DateHelper.addSeconds(endDate, -lifeTime))
						.uniqueResult();
			}
		});
	}

	/**
	 * Получить определенное количество дат создания определенных операций
	 * @param profileId идентфикатор профиля пользователя
	 * @param aClass класс операции
	 * @param count глубина выборки
	 * @param states статусы
	 * @return количество этих самых операций.
	 */
	public static List<Calendar> getCreateOperationLastDates(final Long profileId, final Class<? extends Operation> aClass, final int count, final OperationState... states) throws Exception
	{
		if (profileId == null)
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");

		if (aClass == null)
			throw new IllegalArgumentException("Класс операции не может быть null");

		if(ArrayUtils.isEmpty(states))
			throw new IllegalArgumentException("Состояния операций должны быть заданы");

		return getHibernateExecutor().execute(new HibernateAction<List<Calendar>>()
		{
			public List<Calendar> run(org.hibernate.Session session) throws Exception
			{
				Criteria criteria = session.createCriteria(Operation.class);
				criteria.add(Expression.eq("profileId", profileId));
				criteria.add(Expression.eq("class", aClass.getSimpleName()));
				criteria.add(Expression.in("state", states));
				criteria.addOrder(Order.desc("creationDate"));
				criteria.setMaxResults(count);

				criteria.setProjection(Property.forName("creationDate"));

				return (List<Calendar>) criteria.list();
			}
		});
	}

	/**
	 * Найти коннектор по GUID
	 * @param guid собственно guid
	 * @param lockMode режим блокировки. если не задан - без блокировки
	 * @return найденный коннектор. в случае отсутсвия - ConnectorNotFoundException
	 */
	protected Connector findConnectorByGuid(String guid, LockMode lockMode) throws Exception
	{
		Connector connector = Connector.findByGUID(guid, lockMode);
		if (connector == null)
		{
			throw new ConnectorNotFoundException("Не найден коннектор " + guid);
		}
		return connector;
	}

	/**
	 * Найти активный коннектор по GUID
	 * @param guid собственно guid
	 * @param lockMode режим блокировки. если не задан - без блокировки
	 * @return найденный коннектор. в случае неактивности - исключение
	 */
	protected Connector findActiveConnectorByGuid(String guid, final LockMode lockMode) throws Exception
	{
		Connector connector = findConnectorByGuid(guid, lockMode);
		if (connector.isClosed())
		{
			throw new ConnectorNotFoundException("Коннектор " + guid + " закрыт.");
		}
		if (connector.isBlocked())
		{
			throw new AuthenticationFailedException(connector);
		}
		return connector;
	}

	/**
	 * Найти активный коннектор по GUID определенного типа
	 * @param guid собственно guid
	 * @param lockMode режим блокировки. если не задан - без блокировки
	 * @param expectedTypes список допустимых типов
	 * @return найденный коннектор. в случае неактивности или нодопустимости типа - исключение
	 */
	protected Connector findActiveConnectorByGuid(String guid, LockMode lockMode, ConnectorType... expectedTypes) throws Exception
	{
		Connector connector = findActiveConnectorByGuid(guid, lockMode);
		if (!ArrayUtils.contains(expectedTypes, connector.getType()))
		{
			throw new ConnectorNotFoundException("Коннектор " + guid + " имеет недопустимый тип " + connector.getType());
		}
		return connector;
	}

	/**
	 * Найти незакрытый пригодный для аутентификации коннектор по логину
	 * Логин может быть iPas - 10 значный. либо собственный заданный клиентом.
	 * В случае логина iPas ищется коннектор с типом TERMINAL
	 * Для остальных случаев ищется логин по значению login (по алиасу)
	 * @param login собственно логин
	 * @return найденный коннектор или null.
	 */
	public static final Connector findAuthenticableConnecorByLogin(String login) throws Exception
	{
		Connector connector;
		if (Utils.isIPasLogin(login))
		{
			connector = TerminalConnector.findNotClosedByUserId(login);
		}
		else
		{
			connector = Connector.findByAlias(login);
		}
		if (connector == null)
		{
			throw new ConnectorNotFoundException("Не найднен коннектор по логину " + login);
		}
		if (connector.isClosed())
		{
			throw new ConnectorNotFoundException("Коннектор " + connector.getGuid() + " закрыт");
		}
		return connector;
	}

	/**
	 * Перепривязать операции от профиля oldProfile к профилю actualProfile.
	 * @param oldProfile - профиль, от которого отвязываются операции
	 * @param actualProfile - профиль, к которому перепривязывается операции
	 * @return Количество перепривязанных операций. 0 - если ни один операция не перепривязан
	 */
	public static Integer changeProfile(final Profile oldProfile, final Profile actualProfile) throws Exception
	{
		if (oldProfile == null)
		{
			throw new IllegalArgumentException("Старый профиль не может быть null");
		}
		if (actualProfile == null)
		{
			throw new IllegalArgumentException("Новый профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Operation.changeProfile")
						.setParameter("old_profile", oldProfile.getId())
						.setParameter("new_profile", actualProfile.getId())
						.executeUpdate();
			}
		});
	}

	/**
	 * Добавить параметр с заменой символов-разделителей (во избежание коллизий разделителей с символами, содержащимися в value)
	 * @param name - ключ параметра
	 * @param value - значение параметра
	 */
	public void addParameterWithReplacingDelimiter (String name, String value)
	{
		if (value == null)
		{
			value = "";
		}
		String actualValue = value.replace(PARAMETERS_ENTRY_DELIMETER, REPLACING_ENTRY_DELIMITER_SEQUENCE);
		actualValue = actualValue.replace(PAREMETERS_VALUE_DELIMETER, REPLACING_VALUE_DELIMITER_SEQUENCE);
		addParameter(name, actualValue);
	}

	/**
	 * Получить значение параметра с восстановлением символов
	 * @param name - ключ параметра
	 */
	public String getParameterWithReplacingDelimiter(String name)
	{
		String value = getParameter(name);
		if (value == null)
		{
			return null;
		}
		value = value.replace(REPLACING_ENTRY_DELIMITER_SEQUENCE, PARAMETERS_ENTRY_DELIMETER);
		value = value.replace(REPLACING_VALUE_DELIMITER_SEQUENCE, PAREMETERS_VALUE_DELIMETER);
		return value;
	}
}
