package com.rssl.phizic.business.ermb.sms.delayed;

import com.google.gson.Gson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.json.BasicGsonSingleton;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Gulov
 * @ created 17.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * —ервис дл€ работы с удаленными командами
 */
public class DelayedCommandService
{
	private static final Gson gson = BasicGsonSingleton.getGson();
	private static final SimpleService service = new SimpleService();

	/**
	 * —оздать и сохранить отложенную команду
	 * @param userId - уникальный код пользовател€
	 * @param command - люба€ команда, котора€ не выполнилась, будет выполнена впоследствии джобом
	 */
	public void save(Long userId, Command command)
	{
		DelayedCommand result = create(userId, command);
		try
		{
			service.add(result);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("ќшибка сохранени€ отложенной команды", e);
		}
	}

	/**
	 * Ќайти все невыполненные команды
	 * @param maxRecordCount - максимальное количество записей
	 * @return - список невыполненных команд
	 */
	public List<DelayedCommand> find(final int maxRecordCount)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<DelayedCommand>>()
		    {
		        public List<DelayedCommand> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.sms.delayed.findAll");
			        query.setMaxResults(maxRecordCount);
			        //noinspection unchecked
			        return (List<DelayedCommand>) query.list();
				}
		    });
		}
		catch (Exception e)
		{
		    throw new InternalErrorException("ќшибка поиска списка отложенных команд", e);
		}
	}

	/**
	 * ”далить отложенную команду
	 * @param command - отложенна€ команда
	 */
	public void remove(DelayedCommand command)
	{
		try
		{
			service.remove(command);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("ќшибка удалени€ отложенной команды", e);
		}
	}

	/**
	 * —оздать команду из отложенной команды
	 * @param command - отложенна€ команда
	 * @return - команда
	 */
	public Command createFrom(DelayedCommand command)
	{
		return gson.fromJson(command.getCommandBody(), command.getCommandClass());
	}

	private DelayedCommand create(Long userId, Command command)
	{
		DelayedCommand result = new DelayedCommand();
		result.setUserId(userId);
		result.setCommandClass(command.getClass());
		result.setCommandBody(gson.toJson(command));
		return result;
	}
}
