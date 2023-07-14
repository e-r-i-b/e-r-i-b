package com.rssl.phizicgate.mdm.common;

/**
 * @author akrenev
 * @ created 06.08.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������� ����������
 */

public class UpdateClientInfoResult
{
	private static final UpdateClientInfoResult OK_RESULT = new UpdateClientInfoResult(0, null);

	private final long code;
	private final String description;

	/**
	 * �����������
	 * @param code        ���
	 * @param description ��������
	 */
	private UpdateClientInfoResult(long code, String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * @return ��������� �������� ����������
	 */
	public static UpdateClientInfoResult getOkResult()
	{
		return OK_RESULT;
	}

	/**
	 * @return ���
	 */
	public long getCode()
	{
		return code;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}
}
