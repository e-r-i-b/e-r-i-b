package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.RestorePasswordOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestRestorePasswordOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 15.02.15
 * @ $Author$
 * @ $Revision$
 */
public class StartGuestRestorePasswordRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "startRestorePasswordRq";
	public static final String RESPONCE_TYPE = "startRestorePasswordRs";

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
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);

		GuestRestorePasswordOperation operation = createOperation(login);
		return buildSuccessResponce(operation);
	}

	private GuestRestorePasswordOperation createOperation(String login) throws Exception
	{
		GuestRestorePasswordOperation operation = new GuestRestorePasswordOperation();
		operation.initialize(login);
		return operation;
	}

	private ResponseInfo buildSuccessResponce(GuestRestorePasswordOperation operation) throws Exception
	{
		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addConfirmParameters(operation)
				.end().getResponceInfo();
	}
}
