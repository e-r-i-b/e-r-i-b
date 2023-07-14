package com.rssl.auth.csa.back.protocol;

/**
 * @author vagin
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 * ������� ��� ResponseInfo ��� ������ ����������.
 */
public class LogableResponseInfo
{
	private Throwable error;
	private ResponseInfo responseInfo;

	public LogableResponseInfo(ResponseInfo responseInfo, Throwable error)
	{
		this.error = error;
		this.responseInfo = responseInfo;
	}

	public LogableResponseInfo(ResponseInfo responseInfo)
	{
		this.responseInfo = responseInfo;
	}

	public Throwable getError()
	{
		return error;
	}

	public ResponseInfo getResponseInfo()
	{
		return responseInfo;
	}
}
