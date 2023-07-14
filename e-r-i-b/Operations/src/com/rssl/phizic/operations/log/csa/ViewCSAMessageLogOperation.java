package com.rssl.phizic.operations.log.csa;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.translateMessages.TypeMessageTranslate;
import com.rssl.phizic.operations.log.MessageLogOperation;

import java.util.List;

/**
 * @author vagin
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 * Операция просмотра журнала сообщений ЦСА
 */
public class ViewCSAMessageLogOperation extends MessageLogOperation
{
	public Query createQuery(String name)
	{
		return new BeanQuery(this, ViewCSAMessageLogOperation.class.getName() +"."+ name, Constants.CSA_DB_LOG_INSTANCE_NAME);
	}
	protected String getInstanceName()
	{
		return Constants.CSA_DB_LOG_INSTANCE_NAME;
	}

	public List<String> getMessTranslateList(String text, TypeMessageTranslate type) throws BusinessException
	{
		return messageTranslateService.getCodeOrTranslateList(text, type, Constants.CSA_DB_LOG_INSTANCE_NAME);
	}
}
