package com.rssl.phizgate.messaging.internalws.server.protocol.handlers;

import com.rssl.phizgate.messaging.internalws.InternalServiceConfig;
import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.exceptions.ExceptionHandler;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.logging.Log;
import org.xml.sax.SAXException;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class RequestProcessorBase implements RequestProcessor
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	/**
	 * @return ��� ������
	 */
	protected abstract String getResponceType();

	public final ResponseInfo process(RequestInfo requestInfo) throws Exception
	{
		try
		{
			return processRequest(requestInfo);
		}
		catch (Exception e)
		{
			log.error("������ �� ����� ��������� �������", e);
			//���������� ��������� ����������, ��� ������������� ������� ��������� ����������� �����
			InternalServiceConfig instance = InternalServiceConfig.getInstance();
			ExceptionHandler exceptionHandler = instance.getExceptionHandler(e);
			if (exceptionHandler != null)
				return exceptionHandler.handle(getResponceType(), e);

			return instance.getDefaultExceptionHandler().handle(getResponceType(), e);
		}
	}

	/**
	 * ���������� ������ � ������� ���������
	 * @param requestInfo ���������� � �������
	 * @return ���������� �� ������
	 */
	protected abstract ResponseInfo processRequest(RequestInfo requestInfo) throws Exception;

	/**
	 * �������� ���������� ��������� ������
	 * @return ���������� ��������� ������
	 * @throws SAXException
	 */
	protected ResponseBuilder getSuccessResponseBuilder() throws SAXException
	{
		return new ResponseBuilder(getResponceType());
	}

	/**
	 * �������� ������ �������� �����
	 * @return ������ �������� �����
	 * @throws SAXException
	 */
	protected ResponseInfo buildSuccessResponse() throws SAXException
	{
		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
