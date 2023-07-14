package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.TooManyRequestException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на начало регистрации пользователя startUserRegistrationRq.
 *
 * Упрощенный процесс регистрации выгляит сл образом:
 * 1) клиент начинает регистрацию, введя номер своей карты.
 * 2) идет запрос startUserRegistrationRq в ЦСА BACK
 * 3) происходит идентификация пользователя, проверка возможности регистрации и отсылка смс кода на подтверждение операции
 * 4) пользователь вводит код подтверждения
 * 5) идет запрос confirmOperationRq на подтверждение операции
 * 6) после успешного подтверждения пользователю предлагается завершить регистрацию вводом логина и пароля
 * 7) идет запрос finishUserRegistrationRq c передачей логина и пароля
 * 8) BACK по регистрирует пользователя(исполняет операцию регистрации).
 *
 * Параметры запроса:
 * CardNumber	        Номер карты пользователя.	[1]
 *
 * Параметры ответа:
 * OUID		            Идентфикатор операции.                                      [0..1]
 * ConfirmParameters    Параметры подтверждения	                                    [0..1]
 *      Timeout		    Таймаут ожидания подтверждения	                            [1]
 *      Attempts		Количество оставшихся попыток ввода кода подтверждения. 	[1]
 */

public class StartUserRegistrationRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE = "startUserRegistrationRq";
	private static final String RESPONCE_TYPE = "startUserRegistrationRs";

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
		trace("проверяем входные данные");
		String cardNumber = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_NUMBER_TAG);
		String confirmStrategyType = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CONFIRMATION_PARAM_NAME);
		return startRegistration(cardNumber, confirmStrategyType, context);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String cardNumber = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_NUMBER_TAG);
		return LogIdentificationContext.createByCardNumber(cardNumber);
	}

	private LogableResponseInfo startRegistration(String cardNumber, String confirmStrategyType, final IdentificationContext identificationContext) throws Exception
	{
		try
		{
			Long profileId = identificationContext.getProfile().getId();
			trace("создаем заявку на регистрацию для профиля " + profileId);
			UserRegistrationOperation userRegistrationOperation = createRegistrationOperation(identificationContext, cardNumber, confirmStrategyType);
			trace("строим и отправляем ответ об успешной обработке запроса");
			return new LogableResponseInfo(buildSuccessfullResponce(userRegistrationOperation));
		}
		catch (TooManyRequestException e)
		{
			error("ошибка инициализации операции ", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildTooManyUserRegistrationRequestResponse(),e);
		}
	}

	private ResponseInfo buildSuccessfullResponce(UserRegistrationOperation operation) throws Exception
	{
		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addConfirmParameters(operation)
				.addConnectorsInfo(operation.getNotClosedProfileConnectors())
				.end().getResponceInfo();
	}

	protected UserRegistrationOperation createRegistrationOperation(IdentificationContext identificationContext, String cardNumber, String confirmStrategyType) throws Exception
	{
		UserRegistrationOperation operation = new UserRegistrationOperation(identificationContext);
		operation.initialize(cardNumber, ConfirmStrategyType.valueOf(confirmStrategyType));
		return operation;
	}
}