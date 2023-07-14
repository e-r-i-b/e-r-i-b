package com.rssl.phizgate.messaging.internalws.server.protocol.handlers.exceptions;

import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;

/**
 * ��������� ���������� ����������, ��������� ��� ��������� ��������
 * @author gladishev
 * @ created 17.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionHandler
{
	private int errorCode;
	private String errorDescription;

	/**
	 * ctor
	 * @param errorCode - ��� ������, ������������ � ������ �� ������
	 * @param errorDescription - �������� ������, ������������ � ������
	 */
	public ExceptionHandler(int errorCode, String errorDescription)
	{
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * ��������� ����������
	 * @param responceType - ��� ������
	 * @param e - ����������
	 * @return ���������� �� ������
	 */
	public ResponseInfo handle(String responceType, Exception e) throws Exception
	{
		return new ResponseBuilder(responceType, errorCode, errorDescription + ":" + e).end();
	}
}
