package com.rssl.phizic.business.basket.links;

/**
 * ���������� � �����.
 *
 * @author bogdanov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 */

public class LinkInfo
{
	private Long id;
	private String groupName;
	private String invoiceName;

	/**
	 * @return �������������.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������ �����.
	 */
	public String getGroupName()
	{
		return groupName;
	}

	/**
	 * @param groupName ������ �����.
	 */
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	/**
	 * @return �������� �������.
	 */
	public String getInvoiceName()
	{
		return invoiceName;
	}

	/**
	 * @param invoiceName �������� �������.
	 */
	public void setInvoiceName(String invoiceName)
	{
		this.invoiceName = invoiceName;
	}
}
