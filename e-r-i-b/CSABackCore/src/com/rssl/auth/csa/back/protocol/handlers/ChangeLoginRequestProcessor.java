package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.ChangeLoginOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author krenev
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ����� ������.
 * ����� ������� ���������� ������ ����� ��������������(�������� �����������), �� � ��������� ������� ������ �
 * ����������� ������ ��� ��� ��� IPAS �����������.
 * ��� ������� ������� ������ ��� MAPI ���������� � ����� �������� UnsupportedOperationException(�� ���������� ���������).
 * ���������� � ���� ���������� ���������� ��� �������� ������.
 *
 * ��������� �������:
 * SID		            ������������ ������. 	[1]
 * login		        �����	                [1]
 *
 * ��������� ������:
 */
public class ChangeLoginRequestProcessor extends LogableSessionContextProcessorBase<ChangeLoginOperation>
{
	public static final String REQUEST_TYPE = "changeLoginRq";
	public static final String RESPONCE_TYPE = "changeLogindRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected ResponseInfo processRequest(ChangeLoginOperation operation, RequestInfo requestInfo) throws Exception
	{
		String login = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), Constants.LOGIN_TAG);
		trace("������������� ����� �����");
		Connector connector = operation.execute(login);
		info("����� ����� ������� ���������� ��� ���������� " + connector.getGuid());
		return buildSuccessResponse();
	}

	protected ChangeLoginOperation createOperation(IdentificationContext identificationContext, String sid) throws Exception
	{
		ChangeLoginOperation operation = new ChangeLoginOperation(identificationContext);
		operation.initialize(sid);
		return operation;
	}
}