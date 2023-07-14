package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SocialRegistrationOperation;
import com.rssl.phizic.logging.LogThreadContext;

/**
 * @author ��������
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ���������� ����������� ����������� ���������� finishSocialRegistrationRq
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
 * ��������� �������:
 * OUID		            ������������� ��������.     [1]
 * Password		        ������ ������������	        [1]
 * deviceState		    ��������� ����������        [0..1]
 * devID                ������������� ����������    [0..1]
 *
 * ��������� ������:
 * GUID		            ������������ ���������� ����������.  [1]
 * newPassword		    ������� ����, ��� ����� ����� ������.  [1]
 *
 * */

public class FinishSocialRegistrationRequestProcessor extends FinishMobileRegistrationRequestProcessor
{
	public static final String REQUEST_TYPE = "finishSocialRegistrationRq";
	public static final String RESPONCE_TYPE = "finishSocialRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected LogableResponseInfo finishRegistration(final IdentificationContext identificationContext, String ouid, final String password, String deviceState, String deviceId, String appName) throws Exception
	{
		final SocialRegistrationOperation registrationOperation = identificationContext.findOperation(SocialRegistrationOperation.class, ouid, SocialRegistrationOperation.getLifeTime());
		try
		{
			trace("������ " + registrationOperation.getOuid() + " ������� ��������. ��������� ��.");
			Connector connector = registrationOperation.execute(password, deviceState, deviceId, appName);
			info("������ " + registrationOperation.getOuid() + " ������� ���������.");
			LogThreadContext.setMGUID(connector.getGuid());
			ResponseInfo responceInfo = getSuccessResponseBuilder()
					.addParameter(Constants.GUID_TAG, connector.getGuid())
					.addParameter(Constants.IS_NEW_PASSWORD_TAG, registrationOperation.isPasswordChanged())
					.end().getResponceInfo();
			return new LogableResponseInfo(responceInfo);
		}
		catch (TooManyMobileConnectorsException e)
		{
			error("������ ������������� �������� ", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildTooManyMobileConnectorsResponse(), e);
		}
	}
}