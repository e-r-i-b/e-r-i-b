package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.CancelMobileRegistrationOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.LogThreadContext;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 * ������ �� ������ ���������� ���������� ����������(����� ����������)
 * �������� �������:
 * GUID		            ������������� ���������� ����������(mGUID). 	[1]
 *
 * ��������� ������:
 */
public class CancelMobileRegistrationRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "cancelMobileRegistrationRq";
	public static final String RESPONCE_TYPE = "cancelMobileRegistrationRs";

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
		String guid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.GUID_TAG);
		LogThreadContext.setMGUID(guid);
		return cancelRegistration(guid, context);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String guid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.GUID_TAG);
		return LogIdentificationContext.createByConnectorUID(guid, true);
	}

	protected LogableResponseInfo cancelRegistration(String guid, final IdentificationContext identificationContext) throws Exception
	{
		try
		{
			trace("c������ ������ �� ������ ����������� ��� ������� " + identificationContext.getProfile().getId());
			CancelMobileRegistrationOperation operation = createCancelMobileRegistration(identificationContext, guid);
			trace("�������� ������ ����������� ���������� ���������� �� ������ " + operation.getOuid());
			Connector connector = operation.execute();
			info("����������� ���������� ���������� " + connector.getGuid() + " ������� �������� �� ������ " + operation.getOuid());
			return new LogableResponseInfo(buildSuccessResponse());
		}
		catch (ConnectorNotFoundException e)
		{
			error("������ ������ �����������", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildBadMGUIDResponse(), e);
		}
	}

	private CancelMobileRegistrationOperation createCancelMobileRegistration(IdentificationContext identificationContext, String guid) throws Exception
	{
		CancelMobileRegistrationOperation operation = new CancelMobileRegistrationOperation(identificationContext);
		operation.initialize(guid);
		return operation;
	}
}
