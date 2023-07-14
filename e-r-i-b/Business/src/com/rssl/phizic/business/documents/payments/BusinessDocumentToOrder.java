package com.rssl.phizic.business.documents.payments;

/**
 * Отображение бизнес документа на идентификатор заказа.
 *
 * @author bogdanov
 * @ created 19.11.13
 * @ $Author$
 * @ $Revision$
 */

public class BusinessDocumentToOrder
{
	private Long id;
	private String orderUuid;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getOrderUuid()
	{
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid)
	{
		this.orderUuid = orderUuid;
	}
}
