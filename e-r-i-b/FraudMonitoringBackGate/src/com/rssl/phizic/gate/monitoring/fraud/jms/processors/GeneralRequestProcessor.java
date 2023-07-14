package com.rssl.phizic.gate.monitoring.fraud.jms.processors;

import com.rssl.phizic.gate.monitoring.fraud.jaxb.RequestBuilder;
import com.rssl.phizic.gate.monitoring.fraud.jms.senders.GeneralJMSSender;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.Request_Type;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.gate.monitoring.fraud.Constants.GENERAL_QUEUE_NAME;
import static com.rssl.phizic.gate.monitoring.fraud.Constants.PLACED_IN_QUEUE_MESSAGE;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_GATE;

/**
 * Процессор для очереди всех запросов
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class GeneralRequestProcessor implements RequestProcessor
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_GATE);

	private Request_Type request_type;
	private String correlationId;

	public GeneralRequestProcessor(Request_Type _request_type)
	{
		request_type = _request_type;
		correlationId = _request_type.getIdentificationData().getClientTransactionId();
	}

	public boolean isEnabled()
	{
		return true;
	}

	public void process() throws JMSException, JAXBException
	{
		GeneralJMSSender.sendToQueue(RequestBuilder.getInstance().append(request_type).build(), correlationId);
		log.info(String.format(PLACED_IN_QUEUE_MESSAGE, correlationId, GENERAL_QUEUE_NAME));
	}

	public void rollback() {}
}
