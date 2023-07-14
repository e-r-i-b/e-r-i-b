package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConfirmationFailedException;
import com.rssl.auth.csa.back.log.CSAActionLogHelper;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на подтверждение операции
 *
 * Параметры запроса:
 * OUID		            Идентфикатор подтверждаемой операции. 	[1]
 * ConfirmationCode		Код подтверждения	                    [1]
 *
 * Параметры ответа:
 * ConfirmParameters    Параметры подтверждения	                                    [0..1]
 *      Timeout		    Таймаут ожидания подтверждения	                            [1]
 *      Attempts		Количество оставшихся попыток ввода кода подтверждения. 	[1]
 *
 */

public class ConfirmOperationRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "confirmOperationRq";
	public static final String RESPONCE_TYPE = "confirmOperationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String confirmationCode = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CONFIRMATION_CODE_TAG);

		final LogIdentificationContext context = LogIdentificationContext.createByOperationUID(ouid);
		final IdentificationContext identificationContext = context.getIdentificationContext();
		ConfirmableOperationBase confirmableOperation = null;

		try
		{
			confirmableOperation = identificationContext.findOperation(ConfirmableOperationBase.class,ouid, ConfirmableOperationBase.getCongirmationLifeTime());
			trace("проверяем код подтверждения заявки " + confirmableOperation.getOuid());
			confirmableOperation.checkConfirmCode(confirmationCode);
			info("заявка " + confirmableOperation.getOuid() + " успешно подтверждена.");
			CSAActionLogHelper.writeToActionLog(confirmableOperation, requestInfo, context,  null);
			return buildSuccessResponse(confirmableOperation);
		}
		catch (ConfirmationFailedException e)
		{
			trace("ошибка подтверждения операции", e);
			CSAActionLogHelper.writeToActionLog(confirmableOperation, requestInfo, context,  e);
			return getFailureResponseBuilder().buildFailureConfirmResponse(e.getOperaton());
		}
		catch (Exception e)
		{
			CSAActionLogHelper.writeToActionLog(confirmableOperation, requestInfo, context,  e);
			throw e;
		}
	}

	protected ResponseInfo buildSuccessResponse(ConfirmableOperationBase operation) throws Exception
	{
		return getSuccessResponseBuilder()
				.addUserInfo(operation.getProfile())
				.addProfileId(operation.getProfile())
				.end().getResponceInfo();
	}
}
