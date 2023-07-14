package com.rssl.phizic.resources.ejb;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.mail.Constants;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.jms.JmsService;

import java.io.Serializable;
import javax.jms.JMSException;

/**
 * @author mihaylov
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class MultiNodeProcessorBase
{
	private static final JmsService jmsService = new JmsService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * @param requestString - xml запроса на получение списка входящих писем.
	 * @param jmsCorrelationID - иденитфикатор запроса на получение списка писем. Необходим для связывания запроса и ответа на стороне ЦСА Аднмин.
	 * @throws com.rssl.phizic.common.types.exceptions.SystemException
	 */
	public void process(String requestString, String jmsCorrelationID) throws SystemException
	{
		try
		{
			MultiNodeListRequest request = new MultiNodeListRequest(requestString);
			Serializable data = getData(request);
			sendData(data, jmsCorrelationID);
		}
		catch (JMSException e)
		{
			throw new SystemException(e);
		}
		catch (Exception e)
		{
			sendError(e, jmsCorrelationID);
		}
	}

	protected abstract Serializable getData(MultiNodeListRequest request) throws DataAccessException;

	protected void sendData(Serializable object, String jmsCorrelationID) throws JMSException
	{
		jmsService.sendObjectToQueue(object, Constants.QUEUE_NAME,Constants.FACTORY_NAME,null,jmsCorrelationID);
	}

	protected void sendError(Exception e, String jmsCorrelationID) throws SystemException
	{
		log.error(e.getMessage(), e);
		try
		{
			jmsService.sendObjectToQueue(e, Constants.QUEUE_NAME,Constants.FACTORY_NAME,null,jmsCorrelationID);
		}
		catch(JMSException ex)
		{
			throw new SystemException(ex);
		}
	}

}
