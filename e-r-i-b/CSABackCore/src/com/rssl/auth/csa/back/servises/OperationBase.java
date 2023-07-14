package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.exceptions.OperationNotFoundException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.restrictions.operations.OperationRestrictionProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.MapUtil;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Базовый класс для операций любых клиентов
 * @author niculichev
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class OperationBase extends ActiveRecord
{
	protected static final String PARAMETERS_ENTRY_DELIMETER = ";";
	protected static final String PAREMETERS_VALUE_DELIMETER = "=";

	private String ouid;
	private OperationState state = OperationState.NEW;
	private Calendar creationDate; // дата создания проинициализируется при вставке в бд
	private String ipAddress;
	private String info;
	private Map<String, String> parameters = new TreeMap<String, String>();

	protected OperationBase()
	{
	}

	/**
	 * Найти операцию заданного типа по идентифкатору. Поиск происходит за время жизни операции, начиная от текущего времени минус lifeTime
	 * @param clazz класс операции
	 * @param ouid идентфикатор операции
	 * @param lifeTime время жизни операции/глубина поиска в секундах
	 * @return непротухшая операция .
	 * @throws com.rssl.auth.csa.back.exceptions.OperationNotFoundException если операция не найдена.
	 */
	public static <T extends OperationBase> T findLifeByOUID(Class<T> clazz, String ouid, int lifeTime) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
				.add(Expression.eq("ouid", ouid))
				.add(Expression.sql("{alias}.CREATION_DATE <= SYSDATE"))
				.add(Expression.sql("{alias}.CREATION_DATE >= SYSDATE - INTERVAL '" + lifeTime + "' SECOND"));
		OperationBase result = findSingle(criteria, null);
		if (result == null)
		{
			throw new OperationNotFoundException(ouid, clazz);
		}
		return (T) result;
	}

	/**
	 * проверить состояние операции  на допустимости исполнения.
	 * По умолчанию для исполнения допустимы операции в статусе NEW.
	 * Конкретные операции могут переопределять метод дла реализации собственных критериев допустимости исполнения.
	 * @throws com.rssl.auth.csa.back.exceptions.IllegalOperationStateException в случае недопустимости исполнения.
	 */
	public void checkExecuteAllowed() throws IllegalOperationStateException
	{
		if (OperationState.NEW != getState())
		{
			throw new IllegalOperationStateException("Операция " + getClass().getName() + " " + getOuid() + " не может быть исполнена. Текуший статус операции:" + getState());
		}
	}

	public OperationState getState()
	{
		return state;
	}

	public void setState(OperationState state)
	{
		this.state = state;
	}

	public String getOuid()
	{
		return ouid;
	}

	public void setOuid(String ouid)
	{
		this.ouid = ouid;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	/**
	 * Отказать (пометить отказанной)операцию
	 * @param e исключение (причина отказа)
	 */
	protected void refused(Exception e) throws Exception
	{
		state = OperationState.REFUSED;
		setInfo(ExceptionUtil.printStackTrace(e));
		save();
	}

	/**
	 * Исполнить (пометить исполненной) операцию.
	 * @throws Exception
	 */
	private void executed() throws Exception
	{
		state = OperationState.EXECUTED;
		save();
	}

	protected final <T> T initialize(final HibernateAction<T> initializationAction) throws Exception
	{
		setIpAddress(LogThreadContext.getIPAddress());
		T result = getHibernateExecutor().execute(new HibernateAction<T>()
		{
			public T run(org.hibernate.Session session) throws Exception
			{
				//исполняем зявку
				T result = initializationAction.run(session);
				save();
				return result;
			}
		});
		try
		{
			OperationRestrictionProvider.getInstance().checkBeforeInitialization(this);
		}
		catch (RestrictionException e)
		{
			log.error("Ошибка проверки ограничейни для заявки " + getOuid(), e);
			refused(e);
			throw e;
		}
		return result;
	}

	/**
	 * Исполнить операцию. Метод выполняет обработку статусов заявок блокировки и пр
	 * ConstraintViolationException не обрабатывается и пробрачивается as is, заявка не отказывается и не исполняется.
	 * Вслучае необходимости отказывать должен вызывающий код.
	 * @param executionAction действие, которое необходимо выполнить.
	 * @param <T> тип результата
	 * @return результат выполения операции
	 * @throws Exception
	 */
	protected final <T> T execute(final HibernateAction<T> executionAction) throws Exception
	{
		return execute(executionAction, null);
	}

	/**
	 * Исполнить операцию. Метод выполняет обработку статусов заявок блокировки и пр
	 * @param executionAction действие, которое необходимо выполнить.
	 * @param noRefuseExceptions список исключений, выбрасывание которых не должно приводить к отказу заявоки.
	 * По умолчанию это ConstraintViolationException и IllegalOperationStateException
	 * @param <T> тип результата
	 * @return результат выполения операции
	 * @throws Exception
	 */
	protected final <T> T execute(final HibernateAction<T> executionAction, Class... noRefuseExceptions) throws Exception
	{
		try
		{
			OperationRestrictionProvider.getInstance().checkBeforeExecution(this);
			return getHibernateExecutor().execute(new HibernateAction<T>()
			{
				public T run(org.hibernate.Session session) throws Exception
				{
					//блокируем заявку для избежания повторного исполнения.
					session.refresh(OperationBase.this, LockMode.UPGRADE_NOWAIT);
					//проверям допустимость исполнения
					checkExecuteAllowed();
					//исполняем зявку
					T result = executionAction.run(session);
					executed();
					return result;
				}
			});
		}
		catch (IllegalOperationStateException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error("Ошибка исполнения операции " + getClass().getName() + " " + getOuid(), e);
			if (noRefuseExceptions == null)
			{
				refused(e);
				throw e;
			}
			//перебираем исключения. вдруг для которого-то отказ не требуется
			for (Class noRefuseException : noRefuseExceptions)
			{
				if (noRefuseException.isInstance(e))
				{
					//Действиетльно не требуется - сразу пробрасываем дальше.
					throw e;
				}
			}
			refused(e);
			throw e;
		}
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return сериализованные параметры (для hibernate)
	 */
	public String getParameters()
	{
		return MapUtil.format(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
	}

	/**
	 * Установить сериализованные параметры операции (для hibernate)
	 * @param parameters сериализованные параметры операции
	 */
	public void setParameters(String parameters)
	{
		this.parameters = MapUtil.parse(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
	}

	/**
	 * Получить параметр операции
	 * @param name имя параметра
	 * @return значение параметра или null
	 */
	protected String getParameter(String name)
	{
		return parameters.get(name);
	}

	/**
	 * Добавить параметр операции
	 * @param name имя параметра
	 * @param value значение, не может содержать '=' и ';'
	 * @return предыдущее значение параметра с зпданным именем или null, если параметр новый
	 */
	protected String addParameter(String name, String value)
	{
		if (value != null && (value.contains(PARAMETERS_ENTRY_DELIMETER) || (value.contains(PAREMETERS_VALUE_DELIMETER))))
		{
			throw new IllegalArgumentException("Значение содержит недопустимые символы");
		}
		return parameters.put(name, value);
	}

	/**
	 * Добавить параметр операции
	 * @param name имя параметра
	 * @param value значение, не может содержать '=' и ';'
	 * @return предыдущее значение параметра с зпданным именем или null, если параметр новый
	 */
	protected Object addParameter(String name, Object value)
	{
		if (value == null)
		{
			return addParameter(name, (String) null);
		}
		return addParameter(name, String.valueOf(value));
	}
}
