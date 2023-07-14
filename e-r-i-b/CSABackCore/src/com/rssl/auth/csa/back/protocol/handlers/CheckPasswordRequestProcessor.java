package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.CheckPasswordOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author krenev
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� �������� ������ ������� ������
 *
 * ��������� �������:
 * SID		            ������������ ������. 	[1]
 * password		        ������	                [1]
 *
 * ��������� ������:
 * blockingTimeout      ����� ���������� �� ������                  [0..1]
 *                      ���������� � ��� � ������ ���� ������
 *                      FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 */
public class CheckPasswordRequestProcessor extends SessionContextRequestProcessorBase<CheckPasswordOperation>
{
	public static final String REQUEST_TYPE = "checkPasswordRq";
	public static final String RESPONCE_TYPE = "checkPasswordRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	protected ResponseInfo processRequest(CheckPasswordOperation operation, RequestInfo requestInfo) throws Exception
	{
		String password = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), Constants.PASSWORD_TAG);
		trace("�������� �������� ������");
		Connector connector = operation.execute(password);
		trace("������ ������� ��� ���������� " + connector.getGuid());
		return buildSuccessResponse();
	}

	protected CheckPasswordOperation createOperation(IdentificationContext identificationContext, String sid) throws Exception
	{
		CheckPasswordOperation operation = new CheckPasswordOperation(identificationContext);
		operation.initialize(sid);
		return operation;
	}
}
