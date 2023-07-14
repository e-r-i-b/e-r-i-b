package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.exceptions.InvalidSessionException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Element;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� �������� ������ �� ������� ���������� ������������.
 * ���������� ������������ ������������� ������ ��� ������� �� ������ � ���� � ��� ���.
 * � ������ ������� � ��������� IPas ������ �������� UnsupportedOperationException.
 *
 * ��������� �������:
 * SID		            ������������ ������, � ��������� ������� ���������� �������� ������. 	[1]
 * password		        ������	                [1]
 *
 * ��������� ������:
 */
public class ValidatePasswordRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "validatePasswordRq";
	public static final String RESPONCE_TYPE = "validatePasswordRs";

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

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element root = requestInfo.getBody().getDocumentElement();
		String sid = XmlHelper.getSimpleElementValue(root, Constants.SID_TAG);
		String ouid = XmlHelper.getSimpleElementValue(root, Constants.OUID_TAG);

		String password = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), Constants.PASSWORD_TAG);

		ValidatePasswordOperation operation = null;
		if(StringHelper.isNotEmpty(ouid))
		{
			operation = new ValidatePasswordOperation(IdentificationContext.createByOperationUID(ouid));
			operation.initializeByOUID(ouid);
		}
		else if(StringHelper.isNotEmpty(sid))
		{
			operation = new ValidatePasswordOperation(IdentificationContext.createBySessionId(sid));
			operation.initializeBySID(sid);
		}
		else
		{
			throw new IdentificationFailedException("��� ������ ��� ������������ �������.");
		}

		return processRequest(operation, password);
	}

	protected ResponseInfo processRequest(ValidatePasswordOperation operation, String password) throws Exception
	{
		trace("��������� ����� �� �������� ������");
		operation.execute(password);
		trace("����� �� �������� ������ ������� ���������");
		return buildSuccessResponse();
	}
}