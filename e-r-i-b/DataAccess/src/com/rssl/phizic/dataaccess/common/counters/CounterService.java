package com.rssl.phizic.dataaccess.common.counters;

import com.rssl.phizic.common.types.exceptions.GroupException;
import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.StatelessSession;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Kosyakova
 * Date: 21.09.2006
 * Time: 19:16:31
 */
public class CounterService
{
	/**
	 * @param counter счетчик
	 * @param instanceName инстанс бд
	 * @return следующий номер
	 * @throws CounterException
	 */
	public Long getNext(final Counter counter, String instanceName) throws CounterException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateActionStateless<Long>()
			{
				public Long run(StatelessSession session) throws GroupException
				{
					return getNext(counter, session);
				}
			});
		}
		catch (GroupException e)
		{
			throw new CounterException(e);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param counter счетчик
	 * @return следующий номер
	 * @throws CounterException
	 */
	public Long getNext(final Counter counter) throws CounterException
	{
		return getNext(counter, (String) null);
	}

	private void createSequence(Counter counter, StatelessSession session) throws SQLException
	{
		Statement statement = session.connection().createStatement();
		try
		{
			statement.execute(
					"call create_sequence('" + counter.getName() + "', " + counter.getMaxval() + ")"
			);
		}
		finally
		{
			statement.close();
		}
	}

	private Long sequenceNext(Counter counter, StatelessSession session) throws SQLException
	{
		Statement statement = session.connection().createStatement();
		ResultSet result = null;
		//Пытаемся спросить nextval у сиквенса (в большинстве случаев будет выполняться только этот блок)
		try
		{
			result = statement.executeQuery("select " + counter.getName() + ".nextval from dual");
			if (!result.next())
				throw new SQLException("Результат запроса пуст! Counter:" + counter.getName());
			BigDecimal curValue = result.getBigDecimal(1);
			return curValue.longValue();
		}
		finally
		{
			if (result != null)
				result.close();
			statement.close();
		}
	}

	/**
	 *
	 * @param counter счетчик
	 * @param session сессия в рамках которой н еобходимо получить значение.
	 * @return значение счетчика
	 */
	private Long getNext(Counter counter, StatelessSession session) throws GroupException
	{
		List<SQLException> exceptions;
		//Пытаемся спросить nextval у сиквенса (в большинстве случаев будет выполняться только этот блок)
		try
		{
			return sequenceNext(counter, session);
		}
		catch (SQLException e)
		{
			exceptions = new ArrayList<SQLException>();
			exceptions.add(e);
		}

		//Пытаемся создать сиквенс (если предыдущий запрос свалился - значит сиквенса нет)
		//если создание сиквенса сваливается, то, возможно, он создан за мгновение до этого момента
		try
		{
			createSequence(counter, session);
		}
		catch (SQLException e)
		{
			exceptions.add(e);
		}

		//Пытаемся снова спросить nextval у сиквенса - и если падаем - кидаем групповой эксепшн.
		try
		{
			return sequenceNext(counter, session);
		}
		catch (SQLException e)
		{
			exceptions.add(e);
		}
		//если и в этот раз словили эксепшн, кидаем групповой
		throw new GroupException(exceptions);
	}

	/**
	 * Получить имена ежедневных сиквенсов
	 * @return список имен
	 * @throws CounterException
	 */
	public List<String> getDailySequencesNames() throws CounterException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateActionStateless<List<String>>()
			{
				public List<String> run(StatelessSession session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.dataaccess.common.counters.CounterService.removeOldSequences");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new CounterException(e);
		}
	}

	/**
	 * Удалить сиквенс
	 * @param sequenceName имя сиквенса
	 * @throws CounterException
	 */
	public void removeSequence(final String sequenceName) throws CounterException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateActionStateless<Void>()
			{
				public Void run(StatelessSession session) throws Exception
				{
					removeSequence(session,sequenceName);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new CounterException(e);
		}
	}

	private void removeSequence(StatelessSession session, String sequenceName) throws SQLException
	{
		Statement statement = session.connection().createStatement();
		try
		{
			statement.execute("drop sequence " + sequenceName);
		}
		finally
		{
			statement.close();
		}
	}
}
