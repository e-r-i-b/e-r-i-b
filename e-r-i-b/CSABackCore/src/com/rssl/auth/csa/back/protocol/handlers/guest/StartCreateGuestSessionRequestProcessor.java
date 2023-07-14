package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.log.CSAActionLogHelper;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.operations.LogProfileIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SimpleLogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.guest.GuestPhoneAuthenticationOperation;
import com.rssl.phizic.common.types.csa.IdentificationType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author tisov
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Обработчик первоначального запроса гостевого входа
 */
public class StartCreateGuestSessionRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "guestEntryInitialRq";
	public static final String RESPONCE_TYPE = "guestEntryInitialRs";

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
		String phone = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PHONE_NUMBER_TAG);

		try
		{
			GuestPhoneAuthenticationOperation operation = createAuthenticationOperation(phone);
			ResponseInfo result = getSuccessResponseBuilder()
					.addOUID(operation)
					.addGuestAuthParameters(operation)
					.end().getResponceInfo();
			return result;
		}
		catch (Exception e)
		{
			throw e;
		}

	}

	private GuestPhoneAuthenticationOperation createAuthenticationOperation(String phone) throws Exception
	{
		GuestPhoneAuthenticationOperation operation = new GuestPhoneAuthenticationOperation();
		operation.initialize(phone);
		return operation;
	}
}
