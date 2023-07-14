package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * форма для отмены интернет-заказа или брони авиабилетов.
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
	 * @return идентификатор заказа.
	 */
	public String getOrderId()
	{
		return orderId;
	}

	/**
	 * @param orderId идентификатор заказа.
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	/**
	 * @return id документа.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id id документа.
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
