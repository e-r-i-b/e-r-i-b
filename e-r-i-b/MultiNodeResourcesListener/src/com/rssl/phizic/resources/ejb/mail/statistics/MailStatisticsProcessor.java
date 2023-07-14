package com.rssl.phizic.resources.ejb.mail.statistics;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.gate.mail.statistics.MailStatisticsRecord;
import com.rssl.phizic.resources.ejb.MultiNodeListRequest;
import com.rssl.phizic.resources.ejb.MultiNodeProcessorBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор получения статистики по письмам в блоке.
 */
public class MailStatisticsProcessor extends MultiNodeProcessorBase
{
	private static final String QUERY_NAME = "com.rssl.phizic.operations.ext.sbrf.mail.MailStatisticsOperation.statistics";


	protected Serializable getData(MultiNodeListRequest request) throws DataAccessException
	{
		BeanQuery query = new BeanQuery(QUERY_NAME);
		query.setParameters(request.getParameters());
		List<MailStatisticsRecord> data = query.executeList();
		return new ArrayList<MailStatisticsRecord>(data);
	}
}
