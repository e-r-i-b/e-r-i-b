package com.rssl.phizic.web.common.mobile.basket.invoices;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * Форма списка счетов в корзине
 * @ author: Gololobov
 * @ created: 24.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileListInvoiceForm extends ListFormBase
{
	private Long showInvoicesCount; //кол-во счетов которые нужно отобразить. Если не указано, то все

	public Long getShowInvoicesCount()
	{
		return showInvoicesCount;
	}

	public void setShowInvoicesCount(Long showInvoicesCount)
	{
		this.showInvoicesCount = showInvoicesCount;
	}
}
