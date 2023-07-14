package com.rssl.phizic.logging.exceptions;

/**
 * ������ �� ����������� ������������� ������, ���������� ���������� ������.
 * @author mihaylov
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class ExceptionEntry
{
	public static final String DELIMITER = "|";

	private Long id;
	private String hash;
	private String operation;
	private String detail;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ������, ��� ������������� � ������� ������������ ������
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @param hash ��� ������, ��� ������������� � ������� ������������ ������
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @return �������� ��� ���������� �������� ��������� ������
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation �������� ��� ���������� �������� ��������� ������
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
 	 * @return �������� ������(���� ����� ��� ��������� �� ������� �������)
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * @param detail �������� ������(���� ����� ��� ��������� �� ������� �������)
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * @return ��� ������
	 */
	public abstract String getKind();
}
