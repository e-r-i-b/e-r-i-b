package com.rssl.phizic.business.ermb.messaging;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.sms.messaging.imsi.CheckIMSIState;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.messaging.ermb.TransportMessageSerializer;
import com.rssl.phizic.messaging.ermb.jms.ErmbCheckIMSIResult;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.synchronization.types.CheckImsiResponse;
import com.rssl.phizic.utils.PhoneNumberFormat;

import static com.rssl.phizic.logging.messaging.System.ERMB_SOS;

/**
 * @author EgorovaA
 * @ created 04.04.2013
 * @ $Author$
 * @ $Revision$
 */
class TransportSmsResponseProcessor extends MessageProcessorBase<ErmbXmlMessage>
{
	private final SimpleService simpleService = new SimpleService();

	private final TransportMessageSerializer messageSerializer = new TransportMessageSerializer();

	///////////////////////////////////////////////////////////////////////////

	TransportSmsResponseProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(ErmbXmlMessage xmlRequest)
	{
		CheckImsiResponse response = xmlRequest.getCheckImsiResponse();

		try
		{
			ErmbCheckIMSIResult result = new ErmbCheckIMSIResult();
			result.setSmsRqUid(response.getCorrelationID().toString());
			result.setPhone(PhoneNumberFormat.MOBILE_INTERANTIONAL.format(response.getPhone()));
			result.setResult(CheckIMSIState.getBooleanFromType(response.getResultCode()));
			simpleService.addOrUpdate(result);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			writeMessageLog(response, xmlRequest.getMessage());
		}
	}

	private void writeMessageLog(CheckImsiResponse response, String responseXML)
	{
		try
		{
			MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setMessageRequestId((response != null) ? response.getRqUID().toString() : "unexpected-id");
			logEntry.setMessageRequest(responseXML);
			logEntry.setMessageType(CheckImsiResponse.class.getSimpleName());
			logEntry.setApplication(Application.ErmbTransportChannel);
			logEntry.setSystem(ERMB_SOS);

			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}

	@Override
	public boolean writeToLog()
	{
		// собственное логирование
		return false;
	}
}
