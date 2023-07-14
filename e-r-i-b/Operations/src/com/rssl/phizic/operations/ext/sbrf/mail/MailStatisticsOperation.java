package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.business.mail.area.ContactCenterAreaService;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mail.statistics.EmployeeStatisticsRecord;
import com.rssl.phizic.gate.mail.statistics.MailDateSpan;
import com.rssl.phizic.gate.mail.statistics.MailStatisticsRecord;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizicgate.csaadmin.service.mail.MailStatisticsService;

import java.util.*;

/**
 * @author komarov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class MailStatisticsOperation  extends OperationBase implements ListEntitiesOperation
{
	private static final String QUERY_PREFIX = MailStatisticsOperation.class.getName() + '.';
	private static final ContactCenterAreaService areaService = new ContactCenterAreaService();
	private static final Map<String, CustomListExecutor> executorMap = new HashMap<String, CustomListExecutor>(3);

	static
	{
		executorMap.put("statistics",new CustomListExecutor<MailStatisticsRecord>()
		{
			public List<MailStatisticsRecord> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
			{
				try
				{
					return new MailStatisticsService().getMailStatistics(parameters);
				}
				catch (GateException e)
				{
					throw new DataAccessException(e);
				}
				catch (GateLogicException e)
				{
					throw new DataAccessException(e);
				}
			}
		});
		executorMap.put("employeeStatistics",new CustomListExecutor<EmployeeStatisticsRecord>()
		{
			public List<EmployeeStatisticsRecord> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
			{
				try
				{
					return new MailStatisticsService().getMailEmployeeStatistics(parameters);
				}
				catch (GateException e)
				{
					throw new DataAccessException(e);
				}
				catch (GateLogicException e)
				{
					throw new DataAccessException(e);
				}
			}
		});
		executorMap.put("getAverageTime",new CustomListExecutor<MailDateSpan>()
		{
			public List<MailDateSpan> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
			{
				try
				{
					MailDateSpan mailDateSpan = new MailStatisticsService().getAverageTime(parameters);
					if (mailDateSpan == null)
						return Collections.emptyList();

					return Collections.singletonList(mailDateSpan);
				}
				catch (GateException e)
				{
					throw new DataAccessException(e);
				}
				catch (GateLogicException e)
				{
					throw new DataAccessException(e);
				}
			}
		});
		executorMap.put("getFirstMailDate",new CustomListExecutor<Calendar>()
		{
			public List<Calendar> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
			{
				try
				{
					Calendar firstMailDate = new MailStatisticsService().getFirstMailDate(parameters);
					return Collections.singletonList(firstMailDate);
				}
				catch (GateException e)
				{
					throw new DataAccessException(e);
				}
				catch (GateLogicException e)
				{
					throw new DataAccessException(e);
				}
			}
		});
	}


	@Override
	public Query createQuery(String name)
	{
		if (MultiBlockModeDictionaryHelper.isMailMultiBlockMode())
			return new CustomExecutorQuery(this, executorMap.get(name));
		else
			return new BeanQuery(this, QUERY_PREFIX + name, getInstanceName());
	}

	/**
	 * Возвращает все площадки КЦ
	 * @return список площадок
	 * @throws BusinessException
	 */
	public List<ContactCenterArea> getAreas() throws BusinessException
	{
		return areaService.getAreas(MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

}