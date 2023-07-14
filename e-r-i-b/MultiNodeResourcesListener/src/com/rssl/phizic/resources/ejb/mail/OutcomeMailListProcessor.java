package com.rssl.phizic.resources.ejb.mail;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.gate.mail.IncomeMailListEntity;
import com.rssl.phizic.resources.ejb.MultiNodeListRequest;
import com.rssl.phizic.resources.ejb.MultiNodeProcessorBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
public class OutcomeMailListProcessor extends MultiNodeProcessorBase
{
	private static final String QUERY_NAME = "com.rssl.phizic.operations.mail.ListMailOperation.sentList";

	@Override
	protected Serializable getData(MultiNodeListRequest request) throws DataAccessException
	{
		BeanQuery query = new BeanQuery(QUERY_NAME);
		query.setParameters(request.getParameters());
		query.setMaxResults(request.getMaxResults());
		query.setFirstResult(request.getFirstResult());
		query.setOrderParameterList(request.getOrderParameters());
		List<IncomeMailListEntity> list = query.executeListInternal();
		return new ArrayList<IncomeMailListEntity>(list);
	}
}
