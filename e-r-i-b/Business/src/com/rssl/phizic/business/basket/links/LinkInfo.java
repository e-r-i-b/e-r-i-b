package com.rssl.phizic.business.basket.links;

/**
 * Информация о связи.
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
	 * @return идентификатор.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return группа услуг.
	 */
	public String getGroupName()
	{
		return groupName;
	}

	/**
	 * @param groupName группа услуг.
	 */
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	/**
	 * @return Название инвойса.
	 */
	public String getInvoiceName()
	{
		return invoiceName;
	}

	/**
	 * @param invoiceName Название инвойса.
	 */
	public void setInvoiceName(String invoiceName)
	{
		this.invoiceName = invoiceName;
	}
}
