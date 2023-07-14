package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestRestorePasswordOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 15.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FinishGuestRestorePasswordRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "finishGuestRestorePasswordRq";
	public static final String RESPONCE_TYPE = "finishGuestRestorePasswordRs";

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
		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);

		GuestRestorePasswordOperation operation =
				GuestOperation.findLifeByOUID(GuestRestorePasswordOperation.class, ouid, GuestRestorePasswordOperation.getLifeTime());

		if(operation == null)
			throw new IdentificationFailedException("Не найдена заявка с ouid = " + ouid);

		operation.execute(password);
		return buildSuccessResponse();
	}
}
