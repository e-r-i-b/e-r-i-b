package com.rssl.phizic.logging.exceptions;


/**
 * @author akrenev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * �����, ����������� ������� ������
 */

public class ExternalExceptionEntry extends ExceptionEntry
{
	private static final String KIND = "E";

	private String errorCode;
	private String adapterUUID;

	/**
	 * �����������
	 */
	public ExternalExceptionEntry()
	{}

	/**
	 * �����������
	 * @param errorCode ��� ������
	 * @param detail �������� ������
	 * @param operation �������� ��� ���������� �������� ��������� ������
	 * @param adapterUUID ������� �������
	 */
	public ExternalExceptionEntry(String errorCode, String detail, String operation, String adapterUUID)
	{
		setHash(adapterUUID.concat(DELIMITER).concat(operation).concat(DELIMITER).concat(errorCode));
		setDetail(detail);
		setOperation(operation);
		this.adapterUUID = adapterUUID;
		this.errorCode = errorCode;
	}

	/**
	 * @return ������� �������
	 */
	public String getSystem()
	{
		return adapterUUID;
	}

	/**
	 * @return ��� ������ �� ������� �������
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	@Override
	public String getKind()
	{
		return KIND;
	}
}
