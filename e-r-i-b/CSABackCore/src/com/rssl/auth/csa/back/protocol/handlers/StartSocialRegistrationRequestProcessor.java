package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.exceptions.TooManyRequestException;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SocialRegistrationOperation;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.LoginType;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ������ ����������� ���������� ���������� startMobileRegistrationRq.
 *
 * ���������� ������� ����������� ���������� ���������� �������� �� �������:
 * 1) ������ �������� ����������� ���������� ����������, ����� ���� �����.
 * 2) ���� ������ startMobileRegistrationRq � ��� BACK � ��������� ������ � ���������� � ��������� ����������
 * 3) ���������� ������������� ������������, ������� ��� ���� �� ������������� ��������.
 *    ��� Back ���������� ������ � ����������� ������������� ���������� � ������������.
 * 4) ������������ ������ ��� ������������� � ������ �� ������ � �����������
 * 5) ���� ������ confirmOperationRq �� ������������� ��������
 * 6) ����� ��������� �������������, ���� ������ ��������� ����������� ���������� ���������� finishMobileRegistrationRq � ��������� ������ �� ���� � ����������.
 * 7) ��� Back ���������� ����������� ������ ���������� ��� ���������� ���������� � ���������� mGUID. �������� ��������� ����������� �� �������� � �������������� �������������� ������� � ��� � �������� ������.
 * 8) ��� �������� ������ � ��� ��������� ������� ������ �� �� �������� CreateMobileSessionRq c ��������� mGUID. � ����� �� ������ ������ ������������� ������ � ���������� � ������������.
 *
 *
 * ��������� �������:
 * login		        ����� ������������. 	    [1]
 * deviceInfo           ���������� �� ����������    [1]
 * registrationIPasAvailable    ����������� ����������� �� ������ iPas              [1]
 * devID                ������������� ������������    [0..1]
 *
 * ��������� ������:
 * OUID		            ������������ ��������.                                      [0..1]
 * UserInfo		        ���������� � ������������                                   [0..1]
 *      firstname       ��� ������������                                            [1]
 *      patrname        �������� ������������                                       [0..1]
 *      surname         ������� ������������                                        [1]
 *      birthdate       ���� �������� ������������                                  [1]
 *      passport        ��� ������������                                            [1]
 * ConfirmParameters    ��������� �������������	                                    [0..1]
 *      Timeout		    ������� �������� �������������	                            [1]
 *      Attempts		���������� ���������� ������� ����� ���� �������������. 	[1]
 */
public class StartSocialRegistrationRequestProcessor extends StartMobileRegistrationRequestProcessor
{
	public static final String REQUEST_TYPE = "startSocialRegistrationRq";
	public static final String RESPONCE_TYPE = "startSocialRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected LogableResponseInfo startRegistration(final IdentificationContext identificationContext, String login, String deviceInfo, String confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName) throws Exception
	{
		try
		{
			Connector connector = Operation.findAuthenticableConnecorByLogin(login);
			if (!registrationIPasAvailable && connector.getType() == ConnectorType.TERMINAL)
			{
				throw new RestrictionException("��� ����������� ���������� ���������� ���������� ������������ ����� � ������, �������� ��� ����������� � �������� ������");
			}

			trace("������� ������ �� ����������� ����������� ���������� ��� ������� " + identificationContext.getProfile().getId());
			LoginType loginType = connector.getType() == ConnectorType.TERMINAL ? LoginType.TERMINAL : LoginType.CSA;
			SocialRegistrationOperation operation = createSocialRegistrationOperation(identificationContext, deviceInfo, confirmStrategyType, deviceId, loginType, appName);

			checkCardsLastFourNumbers(card, operation.getUserCards());

			trace("������ � ���������� ����� �� �������� ��������� �������");
			return new LogableResponseInfo(buildSuccessResponse(operation));
		}
		catch (TooManyRequestException e)
		{
			error("������ ������������� �������� ", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildTooManyMobileRegistrationRequestResponse(), e);
		}
		catch (TooManyMobileConnectorsException e)
		{
			error("������ ������������� �������� ", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildTooManyMobileConnectorsResponse(), e);
		}
		catch (ConnectorNotFoundException e)
		{
			error("������ ��������������", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildLoginNotFoundResponse(), e);
		}
	}

	private ResponseInfo buildSuccessResponse(SocialRegistrationOperation operation) throws Exception
	{
		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addUserInfo(operation.getProfile())
				.addConfirmParameters(operation)
				.end().getResponceInfo();
	}

	private SocialRegistrationOperation createSocialRegistrationOperation(IdentificationContext identificationContext, String deviceInfo, String confirmStrategyType, String deviceId, LoginType registrationLoginType, String appName) throws Exception
	{
        SocialRegistrationOperation operation = new SocialRegistrationOperation(identificationContext);
		operation.initialize(deviceInfo, ConfirmStrategyType.valueOf(confirmStrategyType), deviceId, appName, registrationLoginType);
		return operation;
	}
}