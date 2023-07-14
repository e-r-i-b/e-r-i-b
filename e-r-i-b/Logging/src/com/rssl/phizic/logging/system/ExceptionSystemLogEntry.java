package com.rssl.phizic.logging.system;

import com.rssl.phizic.logging.system.guest.GuestSystemLogEntry;

/**
 * @author osminin
 * @ created 17.03.15
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��� ��� �������� ���������� �� ������ ��� ��������
 */
public class ExceptionSystemLogEntry extends GuestSystemLogEntry
{
	private String hash;
	private String operation;
	private String system;
	private String errorCode;
	private String detail;
	private String kind;

	/**
	 * ctor
	 */
	public ExceptionSystemLogEntry()
	{
	}

	/**
	 * ctor
	 * @param entry ������ � ������
	 */
	public ExceptionSystemLogEntry(GuestSystemLogEntry entry)
	{
		super(entry);
	}

	/**
	 * ctor
	 * @param entry ������ � ������
	 */
	public ExceptionSystemLogEntry(SystemLogEntry entry)
	{
		super(entry);
	}

	/**
	 * @return ���
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @param hash ���
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @return �������� ��������
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation �������� ��������
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * @return �������
	 */
	public String getSystem()
	{
		return system;
	}

	/**
	 * @param system �������
	 */
	public void setSystem(String system)
	{
		this.system = system;
	}

	/**
	 * @return ��� ������
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @param errorCode ��� ������
	 */
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return ������ ������
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * @param detail ������ ������
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * @return ��� ������
	 */
	public String getKind()
	{
		return kind;
	}

	/**
	 * @param kind ��� ������
	 */
	public void setKind(String kind)
	{
		this.kind = kind;
	}
}
