package com.rssl.phizic.business.dictionaries.pfp.products.types;

/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��� �� ��������
 */

public class DiagramStep
{
	private long from;
	private long to;
	private String name;

	/**
	 * @return ����������� �������� ����
	 */
	public long getFrom()
	{
		return from;
	}

	/**
	 * ������ ����������� �������� ����
	 * @param from ����������� �������� ����
	 */
	public void setFrom(long from)
	{
		this.from = from;
	}

	/**
	 * @return ������������ �������� ����
	 */
	public long getTo()
	{
		return to;
	}

	/**
	 * ������ ������������ �������� ����
	 * @param to ������������ �������� ����
	 */
	public void setTo(long to)
	{
		this.to = to;
	}

	/**
	 * @return �������� ����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ �������� ����
	 * @param name �������� ����
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
