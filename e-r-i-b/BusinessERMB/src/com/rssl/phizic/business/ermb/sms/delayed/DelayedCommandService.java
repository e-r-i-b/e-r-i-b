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
 * ������ ��� ������ � ���������� ���������
 */
public class DelayedCommandService
{
	private static final Gson gson = BasicGsonSingleton.getGson();
	private static final SimpleService service = new SimpleService();

	/**
	 * ������� � ��������� ���������� �������
	 * @param userId - ���������� ��� ������������
	 * @param command - ����� �������, ������� �� �����������, ����� ��������� ������������ ������
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
			throw new InternalErrorException("������ ���������� ���������� �������", e);
		}
	}

	/**
	 * ����� ��� ������������� �������
	 * @param maxRecordCount - ������������ ���������� �������
	 * @return - ������ ������������� ������
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
		    throw new InternalErrorException("������ ������ ������ ���������� ������", e);
		}
	}

	/**
	 * ������� ���������� �������
	 * @param command - ���������� �������
	 */
	public void remove(DelayedCommand command)
	{
		try
		{
			service.remove(command);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("������ �������� ���������� �������", e);
		}
	}

	/**
	 * ������� ������� �� ���������� �������
	 * @param command - ���������� �������
	 * @return - �������
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
