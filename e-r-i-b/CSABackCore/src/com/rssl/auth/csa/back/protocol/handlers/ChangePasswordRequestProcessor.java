package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.ChangePasswordOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author krenev
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на смену пароля.
 * Смена паролей происходит только после аутентификации(проверки подлинности), те в контексте текущей сессии и
 * допускается только для ЦСА и МАПИ коннекторов.
 * При попытке сменить пароль для IPAS коннектора в ответ прилетит UnsupportedOperationException(не соблюдение протокола).
 * Информация о типе коннектора передается при открытии сессии. 
 *
 * Параметры запроса:
 * SID		            Идентфикатор сессии. 	[1]
 * password		        пароль	                [1]
 *
 * Параметры ответа:
 */
public class ChangePasswordRequestProcessor extends LogableSessionContextProcessorBase<ChangePasswordOperation>
{
	public static final String REQUEST_TYPE = "changePasswordRq";
	public static final String RESPONCE_TYPE = "changePasswordRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected ResponseInfo processRequest(ChangePasswordOperation operation, RequestInfo requestInfo) throws Exception
	{
		String password = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), Constants.PASSWORD_TAG);
		trace("устанавливаем новый пароль");
		Connector connector = operation.execute(password);
		info("новый пароль успешно установлен для коннектора " + connector.getGuid());
		return buildSuccessResponse();
	}

	protected ChangePasswordOperation createOperation(IdentificationContext identificationContext, String sid) throws Exception
	{
		ChangePasswordOperation operation = new ChangePasswordOperation(identificationContext);
		operation.initialize(sid);
		return operation;
	}
}