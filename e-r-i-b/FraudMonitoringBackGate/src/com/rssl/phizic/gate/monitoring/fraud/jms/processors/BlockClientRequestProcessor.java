package com.rssl.phizic.gate.monitoring.fraud.jms.processors;

import com.rssl.phizic.gate.monitoring.fraud.jaxb.RequestBuilder;
import com.rssl.phizic.gate.monitoring.fraud.jms.senders.BlockClientJMSSender;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.Request_Type;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.RiskResult_Type;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.TriggeredRule_Type;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.BooleanUtils;

import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.gate.monitoring.fraud.Constants.BLOCKING_CLIENT_QUEUE_NAME;
import static com.rssl.phizic.gate.monitoring.fraud.Constants.PLACED_IN_QUEUE_MESSAGE;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_GATE;

/**
 * Процессор для очереди блокирования клиента
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class BlockClientRequestProcessor implements RequestProcessor
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_GATE);

	private Request_Type request_type;
	private String correlationId;

	public BlockClientRequestProcessor(Request_Type _request_type)
	{
		request_type = _request_type;
		correlationId = _request_type.getIdentificationData().getClientTransactionId();
	}

	public boolean isEnabled()
	{
		RiskResult_Type riskResult_type = request_type.getRiskResult();
		if (riskResult_type == null)
		{
			return false;
		}
		TriggeredRule_Type triggeredRule_type = riskResult_type.getTriggeredRule();
		if (triggeredRule_type == null)
		{
			return false;
		}
		return BooleanUtils.isTrue(triggeredRule_type.getBlockClient());
	}

	public void process() throws JMSException, JAXBException
	{
		BlockClientJMSSender.sendToQueue(RequestBuilder.getInstance().append(request_type).build(), correlationId);
		log.info(String.format(PLACED_IN_QUEUE_MESSAGE, correlationId, BLOCKING_CLIENT_QUEUE_NAME));
	}

	public void rollback() {}
}
