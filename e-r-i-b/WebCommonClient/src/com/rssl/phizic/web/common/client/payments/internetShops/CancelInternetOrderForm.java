package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ����� ��� ������ ��������-������ ��� ����� �����������.
 *
 * @author bogdanov
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class CancelInternetOrderForm extends ActionFormBase
{
	private String orderId;
	private Long id;
	private String pageType;
	private String delayDate;

	/**
	 * @return ������������� ������.
	 */
	public String getOrderId()
	{
		return orderId;
	}

	/**
	 * @param orderId ������������� ������.
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	/**
	 * @return id ���������.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id id ���������.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPageType()
	{
		return pageType;
	}

	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}

	public String getDelayDate()
	{
		return delayDate;
	}

	public void setDelayDate(String delayDate)
	{
		this.delayDate = delayDate;
	}
}
