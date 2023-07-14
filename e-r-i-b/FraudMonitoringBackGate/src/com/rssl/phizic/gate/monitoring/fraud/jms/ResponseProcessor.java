package com.rssl.phizic.gate.monitoring.fraud.jms;

import com.rssl.phizic.gate.monitoring.fraud.ResponseHelper;
import com.rssl.phizic.gate.monitoring.fraud.jms.processors.BlockClientRequestProcessor;
import com.rssl.phizic.gate.monitoring.fraud.jms.processors.CompositeRequestProcessor;
import com.rssl.phizic.gate.monitoring.fraud.jms.processors.GeneralRequestProcessor;
import com.rssl.phizic.gate.monitoring.fraud.jms.validators.ClientTransactionIdValidator;
import com.rssl.phizic.gate.monitoring.fraud.jms.validators.CompositeRequestValidator;
import com.rssl.phizic.gate.monitoring.fraud.jms.validators.RequestValidator;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.Request_Type;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.Response_Type;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.gate.monitoring.fraud.Constants.*;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_GATE;

/**
 * Класс, обрабатывающий сообщение от ВС ФМ
 *
 * @author khudyakov
 * @ created 12.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ResponseProcessor
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_GATE);

	private Request_Type request_type;

	public ResponseProcessor(Request_Type request_type)
	{
		this.request_type = request_type;
	}

	/**
	 * Обработать сообщение от ВС ФМ
	 * @return ответ
	 */
	public Response_Type process()
	{
		try
		{
			RequestValidator validator = new CompositeRequestValidator(new ClientTransactionIdValidator(request_type));
			if (!validator.validate())
			{
				log.error(validator.getMessage());
				return ResponseHelper.buildErrorResponse(validator.getMessage());
			}

			new CompositeRequestProcessor(new GeneralRequestProcessor(request_type), new BlockClientRequestProcessor(request_type)).process();
			log.info(String.format(PROCESSING_REQUEST_SUCCESS_MESSAGE, request_type.getIdentificationData().getClientTransactionId()));
		}
		catch (JMSException e)
		{
			log.error(String.format(SAVE_REQUEST_ERROR_MESSAGE, request_type.getIdentificationData().getClientTransactionId()), e);
			return ResponseHelper.buildErrorResponse(PROCESSING_REQUEST_ERROR_CODE_DESCRIPTION);
		}
		catch (JAXBException e)
		{
			log.error(String.format(TRANSFORM_REQUEST_ERROR_MESSAGE, request_type.getIdentificationData().getClientTransactionId()), e);
			return ResponseHelper.buildErrorResponse(PROCESSING_REQUEST_ERROR_CODE_DESCRIPTION);
		}
		catch (Exception e)
		{
			log.error(String.format(PROCESSING_REQUEST_ERROR_MESSAGE, request_type.getIdentificationData().getClientTransactionId()), e);
			return ResponseHelper.buildErrorResponse(PROCESSING_REQUEST_ERROR_CODE_DESCRIPTION);
		}

		return ResponseHelper.buildSuccessResponse();
	}
}