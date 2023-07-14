package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.IMSICheckException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.operations.CheckIMSIOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * Обработчик запроса на проверку IMSI
 * @author Jatsky
 * @ created 28.01.15
 * @ $Author$
 * @ $Revision$
 */

public class CheckIMSIRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "checkIMSIRq";
	public static final String RESPONCE_TYPE = "checkIMSIRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		trace("проверяем входные данные");
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		return checkIMSI(login, context);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		return LogIdentificationContext.createByLogin(login, false);
	}

	private LogableResponseInfo checkIMSI(String login, final IdentificationContext identificationContext) throws Exception
	{
		try
		{
			CheckIMSIOperation operation = new CheckIMSIOperation(identificationContext);
			operation.checkIMSI(login);
			return new LogableResponseInfo(buildSuccessResponse());
		}
		catch (IMSICheckException e)
		{
			error(e.getMessage(), e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildIMSICheckResponse(e.getMessage(), e.getErrorPhones()), e);
		}
	}
}
