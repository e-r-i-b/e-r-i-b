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
 * ���������� ������� �� ������ ����������� ������������ startUserRegistrationRq.
 *
 * ���������� ������� ����������� ������� �� �������:
 * 1) ������ �������� �����������, ����� ����� ����� �����.
 * 2) ���� ������ startUserRegistrationRq � ��� BACK
 * 3) ���������� ������������� ������������, �������� ����������� ����������� � ������� ��� ���� �� ������������� ��������
 * 4) ������������ ������ ��� �������������
 * 5) ���� ������ confirmOperationRq �� ������������� ��������
 * 6) ����� ��������� ������������� ������������ ������������ ��������� ����������� ������ ������ � ������
 * 7) ���� ������ finishUserRegistrationRq c ��������� ������ � ������
 * 8) BACK �� ������������ ������������(��������� �������� �����������).
 *
 * ��������� �������:
 * CardNumber	        ����� ����� ������������.	[1]
 *
 * ��������� ������:
 * OUID		            ������������ ��������.                                      [0..1]
 * ConfirmParameters    ��������� �������������	                                    [0..1]
 *      Timeout		    ������� �������� �������������	                            [1]
 *      Attempts		���������� ���������� ������� ����� ���� �������������. 	[1]
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
		trace("��������� ������� ������");
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
			trace("������� ������ �� ����������� ��� ������� " + profileId);
			UserRegistrationOperation userRegistrationOperation = createRegistrationOperation(identificationContext, cardNumber, confirmStrategyType);
			trace("������ � ���������� ����� �� �������� ��������� �������");
			return new LogableResponseInfo(buildSuccessfullResponce(userRegistrationOperation));
		}
		catch (TooManyRequestException e)
		{
			error("������ ������������� �������� ", e);
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