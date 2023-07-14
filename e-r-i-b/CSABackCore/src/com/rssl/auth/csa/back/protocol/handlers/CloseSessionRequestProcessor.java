package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Session;
import com.rssl.auth.csa.back.servises.operations.CloseSessionOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� �������� ������
 *
 * ��������� �������:
 * SID		            ������������ ������. 	[1]
 *
 * ��������� ������:
 */
public class CloseSessionRequestProcessor extends SessionContextRequestProcessorBase<CloseSessionOperation>
{
	public static final String REQUEST_TYPE = "closeSessionRq";
	public static final String RESPONCE_TYPE = "closeSessionRs";

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

	protected ResponseInfo processRequest(CloseSessionOperation operation, RequestInfo requestInfo) throws Exception
	{
		trace("��������� ����� �� �������� ������");
		Session session = operation.execute();
		info("������� ������� " + session.getGuid() + " ��� ���������� " + session.getConnectorGuid());
		return buildSuccessResponse();
	}

	protected CloseSessionOperation createOperation(IdentificationContext identificationContext, String sid) throws Exception
	{
		CloseSessionOperation operation = new CloseSessionOperation(identificationContext);
		operation.initialize(sid);
		return operation;
	}
}
