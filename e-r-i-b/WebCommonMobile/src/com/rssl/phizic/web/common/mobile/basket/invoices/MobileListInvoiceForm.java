package com.rssl.phizic.web.common.mobile.basket.invoices;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * ����� ������ ������ � �������
 * @ author: Gololobov
 * @ created: 24.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileListInvoiceForm extends ListFormBase
{
	private Long showInvoicesCount; //���-�� ������ ������� ����� ����������. ���� �� �������, �� ���

	public Long getShowInvoicesCount()
	{
		return showInvoicesCount;
	}

	public void setShowInvoicesCount(Long showInvoicesCount)
	{
		this.showInvoicesCount = showInvoicesCount;
	}
}
