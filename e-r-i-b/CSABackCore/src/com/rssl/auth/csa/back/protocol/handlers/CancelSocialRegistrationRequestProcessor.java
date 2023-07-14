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
 * ������ �� ������ ���������� ����������� ����������(����� ����������)
 * �������� �������:
 * GUID		            ������������� ����������� ����������(mGUID). 	[1]
 *
 * ��������� ������:
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
			trace("c������ ������ �� ������ ����������� ��� ������� " + identificationContext.getProfile().getId());
            CancelSocialRegistrationOperation operation = createCancelSocialRegistration(identificationContext, guid);
			trace("�������� ������ ����������� ����������� ���������� �� ������ " + operation.getOuid());
			Connector connector = operation.execute();
			info("����������� ����������� ���������� " + connector.getGuid() + " ������� �������� �� ������ " + operation.getOuid());
			return new LogableResponseInfo(buildSuccessResponse());
		}
		catch (ConnectorNotFoundException e)
		{
			error("������ ������ �����������", e);
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
