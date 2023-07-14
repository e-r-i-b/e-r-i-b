package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.log.CSAActionLogHelper;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.operations.LogProfileIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SimpleLogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.guest.GuestLoginAuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestLogonOperation;
import com.rssl.auth.csa.back.servises.operations.guest.LoginGuestLogonOperation;
import com.rssl.phizic.common.types.csa.IdentificationType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * Процессор, обрабатывающий запрос на аутентификацию гостя по зарегистрированному ранее логину
 * @author niculichev
 * @ created 01.02.15
 * @ $Author$
 * @ $Revision$
 */
public class StartCreateGuestLoginSessionRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "startCreateSessionRq";
	public static final String RESPONCE_TYPE = "startCreateSessionRs";

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
		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);

		LogProfileIdentificationContext context = new SimpleLogIdentificationContext(IdentificationType.guestLogin, login);

		try
		{
			GuestLoginAuthenticationOperation operation = createAuthenticationOperation(login);
			operation.execute(password);

			ResponseInfo result = prepareLogon(operation, login);
			CSAActionLogHelper.writeToGuestActionLog(requestInfo, context);
			return result;
		}
		catch (Exception e)
		{
			CSAActionLogHelper.writeToGuestActionLog(requestInfo, context, e);
			throw e;
		}
	}

	protected ResponseInfo prepareLogon(GuestLoginAuthenticationOperation op, String login) throws Exception
	{
		GuestLogonOperation operation = createLogonOperation(op.getPhone(), login);
		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addUserLogonType(UserLogonType.GUEST)
				.addNodeInfo(Node.getFillingGuest())
				.end();
	}

	private GuestLogonOperation createLogonOperation(String phone, String login) throws Exception
	{
		LoginGuestLogonOperation operation = new LoginGuestLogonOperation();
		operation.initialize(phone, login);
		return operation;
	}

	private GuestLoginAuthenticationOperation createAuthenticationOperation(String login) throws Exception
	{
		GuestLoginAuthenticationOperation operation = new GuestLoginAuthenticationOperation();
		operation.initialize(login);
		return operation;
	}
}
