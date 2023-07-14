package com.rssl.auth.csa.back.protocol.handlers.verification;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.integration.dasreda.FailVerifyBusinessEnvironmentException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.LogableProcessorBase;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.verification.VerifyBusinessEnvironmentOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.config.ConfigFactory;
import org.w3c.dom.Document;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class VerifyBusinessEnvironmentRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "verifyBusinessEnvironmentRq";
	public static final String RESPONCE_TYPE = "verifyBusinessEnvironmentRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return doVerify(context, ouid);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

	private LogableResponseInfo doVerify(final IdentificationContext identificationContext, String ouid) throws Exception
	{
		Config config = ConfigFactory.getConfig(Config.class);
		VerifyBusinessEnvironmentOperation confirmableOperation = identificationContext.findOperation(VerifyBusinessEnvironmentOperation.class, ouid, config.getAuthenticationTimeout());
		try
		{
			trace("начинаем верификацию данных в деловой среде " + confirmableOperation.getOuid());
			confirmableOperation.execute();
			info("верификация " + confirmableOperation.getOuid() + " успешно проведена.");
			return new LogableResponseInfo(buildSuccessResponse());
		}
		catch (FailVerifyBusinessEnvironmentException e)
		{
			trace("ошибка верификации данных в деловой среде", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildBusinessEnvironmentRefuseResponse(e.getMessage()), e);
		}
		catch (Exception e)
		{
			trace("ошибка верификации данных в деловой среде", e);
			ResponseInfo errorResponce = getFailureResponseBuilder().buildBusinessEnvironmentErrorResponse("Операция не может быть выполнена. Повторите попытку позже. Вы можете вернуться на сайт портала «Деловая среда».");
			return new LogableResponseInfo(errorResponce, e);
		}
	}
}