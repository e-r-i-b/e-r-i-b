package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.GuestAuthenticationFailedException;
import com.rssl.auth.csa.back.exceptions.GuestPasswordFailedException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.operations.guest.GuestLogonOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestPhoneAuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.guest.PhoneGuestLogonOperation;
import com.rssl.phizic.gate.bankroll.generated.impl.runtime.Util;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author tisov
 * @ created 26.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Процессор запроса на подтвеждение смс-пароля
 */
public class AuthGuestSessionRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "guestEntryConfirmationRq";
	public static final String RESPONCE_TYPE = "guestEntryConfirmationRs";

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

		GuestPhoneAuthenticationOperation operation =
				GuestOperation.findLifeByOUID(GuestPhoneAuthenticationOperation.class, ouid, GuestOperation.getOperationLifeTime());

		try
		{
			operation.execute(password);
		}
		catch (GuestPasswordFailedException e)
		{
			error("Неверно введенной пароль при гостевом входе", e);
			return getFailureResponseBuilder().buildGuestFailurePasswordResponse(operation);
		}

		return prepareLogon(operation);
	}

	protected ResponseInfo prepareLogon(GuestPhoneAuthenticationOperation op) throws Exception
	{
		GuestLogonOperation operation = createLogonOperation(op.getPhone());

		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addGuestInfo(null, operation)
				.addNodeInfo(Node.getFillingGuest())
				.end();
	}

	private GuestLogonOperation createLogonOperation(String phone) throws Exception
	{
		PhoneGuestLogonOperation operation = new PhoneGuestLogonOperation();
		operation.initialize(phone);
		return operation;
	}
}
