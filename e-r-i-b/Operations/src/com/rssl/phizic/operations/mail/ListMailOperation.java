package com.rssl.phizic.operations.mail;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.CustomExecutorQuery;
import com.rssl.phizic.dataaccess.query.CustomListExecutor;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mail.IncomeMailListEntity;
import com.rssl.phizic.gate.mail.OutcomeMailListEntity;
import com.rssl.phizic.gate.mail.RemovedMailListEntity;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;
import com.rssl.phizicgate.csaadmin.service.mail.MailListService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kligina
 * @ created 29.04.2010
 * @ $Author$
 * @ $Revision$
 *
 * Операция получения списка входящих писем сотрудником.
 */
public class ListMailOperation extends ListForEmployeeOperation implements ListEntitiesOperation
{
	private static final Map<String, CustomListExecutor> executorMap = new HashMap<String, CustomListExecutor>(3);

	static
	{
		executorMap.put("list",new CustomListExecutor<IncomeMailListEntity>()
							{
								public List<IncomeMailListEntity> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
								{
									try
									{
										return new MailListService().getIncomeList(parameters,size,offset,orderParameters);
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
		executorMap.put("sentList",new CustomListExecutor<OutcomeMailListEntity>()
							{
								public List<OutcomeMailListEntity> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
								{
									try
									{
										return new MailListService().getOutcomeList(parameters,size,offset,orderParameters);
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
		executorMap.put("removedList",new CustomListExecutor<RemovedMailListEntity>()
							{
								public List<RemovedMailListEntity> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
								{
									try
									{
										return new MailListService().getRemovedList(parameters,size,offset,orderParameters);
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
			return new CustomExecutorQuery(this,executorMap.get(name));
		else
			return super.createQuery(name);
	}
}
