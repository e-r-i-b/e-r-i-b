package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.exceptions.GuestConfirmationFailedException;
import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.log.CSAActionLogHelper;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.auth.csa.back.servises.operations.LogProfileIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SimpleLogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.guest.GuestConfirmableOperation;
import com.rssl.phizic.common.types.csa.IdentificationType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 15.02.15
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmGuestOperationRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "confirmGuestOperationRq";
	public static final String RESPONCE_TYPE = "confirmGuestOperationRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String confirmationCode = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CONFIRMATION_CODE_TAG);

		LogProfileIdentificationContext context = new SimpleLogIdentificationContext(IdentificationType.OUID, ouid);

		try
		{
			GuestConfirmableOperation confirmableOperation =
					GuestOperation.findLifeByOUID(GuestConfirmableOperation.class, ouid, GuestConfirmableOperation.getConfirmationLifeTime());

			if(confirmableOperation == null)
				throw new IdentificationFailedException("Не найдена заявка с ouid = " + ouid);

			confirmableOperation.checkConfirmCode(confirmationCode);
		}
		catch (GuestConfirmationFailedException e)
		{
			CSAActionLogHelper.writeToGuestActionLog(requestInfo, context, e);
			return getFailureResponseBuilder().buildFailureConfirmResponse(e.getOperation());
		}

		CSAActionLogHelper.writeToGuestActionLog(requestInfo, context);

		return buildSuccessResponse();
	}
}
