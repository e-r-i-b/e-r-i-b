package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.CancelSocialRegistrationOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 * Запрос на отмену регистраци социального приложения(сброс коннектора)
 * Парметры запроса:
 * GUID		            идентификатор социального приложения(mGUID). 	[1]
 *
 * Параметры ответа:
 */
public class CancelSocialRegistrationRequestProcessor extends CancelMobileRegistrationRequestProcessor
{
	public static final String REQUEST_TYPE = "cancelSocialRegistrationRq";
	public static final String RESPONCE_TYPE = "cancelSocialRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected LogableResponseInfo cancelRegistration(String guid, final IdentificationContext identificationContext) throws Exception
	{
		try
		{
			trace("cоздаем заявку на отмену регистрации для профиля " + identificationContext.getProfile().getId());
            CancelSocialRegistrationOperation operation = createCancelSocialRegistration(identificationContext, guid);
			trace("проводим отмену регистрации социального приложения по заявке " + operation.getOuid());
			Connector connector = operation.execute();
			info("регистрация социального приложения " + connector.getGuid() + " успешно отменена по заявке " + operation.getOuid());
			return new LogableResponseInfo(buildSuccessResponse());
		}
		catch (ConnectorNotFoundException e)
		{
			error("ошибка отмены регистрации", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildBadMGUIDResponse(), e);
		}
	}

	private CancelSocialRegistrationOperation createCancelSocialRegistration(IdentificationContext identificationContext, String guid) throws Exception
	{
        CancelSocialRegistrationOperation operation = new CancelSocialRegistrationOperation(identificationContext);
		operation.initialize(guid);
		return operation;
	}
}
