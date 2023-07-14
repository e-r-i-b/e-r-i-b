package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.MobileRegistrationOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.LogThreadContext;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ���������� ����������� ���������� ���������� finishMobileRegistrationRq
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

public class FinishMobileRegistrationRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "finishMobileRegistrationRq";
	public static final String RESPONCE_TYPE = "finishMobileRegistrationRs";

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
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);
		String deviceState = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.DEVICE_STATE_TAG);
		String deviceId = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.DEVICE_ID_TAG);
		String appName = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.APP_NAME_TAG);
		return finishRegistration(context, ouid, password, deviceState, deviceId, appName);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

	protected LogableResponseInfo finishRegistration(final IdentificationContext identificationContext, String ouid, final String password, String deviceState, String deviceId, String appName) throws Exception
	{
		final MobileRegistrationOperation registrationOperation = identificationContext.findOperation(MobileRegistrationOperation.class, ouid, MobileRegistrationOperation.getLifeTime());
		try
		{
			trace("������ " + registrationOperation.getOuid() + " ������� ��������. ��������� ��.");
			Connector connector = registrationOperation.execute(password, deviceState, deviceId, appName);
			info("������ " + registrationOperation.getOuid() + " ������� ���������.");
			LogThreadContext.setMGUID(connector.getGuid());
			ResponseInfo responceInfo = getSuccessResponseBuilder()
					.addParameter(Constants.GUID_TAG, connector.getGuid())
					.addParameter(Constants.IS_NEW_PASSWORD_TAG, registrationOperation.isPasswordChanged())
					.addParameter(Constants.PROFILE_ID_TAG, identificationContext.getProfile().getId())
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