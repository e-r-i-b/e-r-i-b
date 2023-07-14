package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Session;
import com.rssl.auth.csa.back.servises.operations.CheckSessionOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� �������� ���������� ������(����)
 *
 * ��������� �������:
 * SID		            ������������ ������. 	[1]
 *
 * ��������� ������:
 */
public class CheckSessionRequestProcessor extends SessionContextRequestProcessorBase<CheckSessionOperation>
{
	public static final String REQUEST_TYPE = "checkSessionRq";
	public static final String RESPONCE_TYPE = "checkSessionRs";

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

	protected ResponseInfo processRequest(CheckSessionOperation operation, RequestInfo requestInfo) throws Exception
	{
		trace("��������� ����� �� �������� ������");
		Session session = operation.execute();
		trace("������� " + session.getGuid() + " �������");
		return buildSuccessResponse();
	}

	protected CheckSessionOperation createOperation(IdentificationContext identificationContext, String sid) throws Exception
	{
		CheckSessionOperation operation = new CheckSessionOperation(identificationContext);
		operation.initialize(sid);
		return operation;
	}
}