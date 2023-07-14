package com.rssl.phizic.resources.ejb.mail.statistics;

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
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ѕроцессор дл€ получени€ статистики по письмам из блока в разрезе сотрудников.
 */
public class MailEmployeeStatisticProcessor extends MultiNodeProcessorBase
{
	private static final String QUERY_NAME = "com.rssl.phizic.operations.ext.sbrf.mail.MailStatisticsOperation.employeeStatistics";

	@Override
	protected Serializable getData(MultiNodeListRequest request) throws DataAccessException
	{
		BeanQuery query = new BeanQuery(QUERY_NAME);
		query.setParameters(request.getParameters());
		List<IncomeMailListEntity> list = query.executeListInternal();
		return new ArrayList<IncomeMailListEntity>(list);
	}
}
