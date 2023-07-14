package com.rssl.phizic.resources.ejb.mail.statistics;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.resources.ejb.MultiNodeListRequest;
import com.rssl.phizic.resources.ejb.MultiNodeProcessorBase;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор получения среднего времени обработки письма в блоке.
 */
public class MailAverageTimeProcessor extends MultiNodeProcessorBase
{
	private static final String QUERY_NAME = "com.rssl.phizic.operations.ext.sbrf.mail.MailStatisticsOperation.getAverageTime";

	@Override
	protected Serializable getData(MultiNodeListRequest request) throws DataAccessException
	{
		BeanQuery query = new BeanQuery(QUERY_NAME);
		query.setParameters(request.getParameters());
		return query.executeUnique();
	}
}
