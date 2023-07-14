package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.web.LastPaymentsWidget;
import com.rssl.phizic.operations.ext.sbrf.payment.ListPopularPaymentsOperation;

import java.util.List;

/** ќпераци€ виджета "ѕоследние платежи"
 * @ author Rtischeva
 * @ created 17.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LastPaymentsWidgetOperation extends WidgetOperation<LastPaymentsWidget>
{
	//поле - операци€ дл€ вызова метода из операции не-виджетовой версии
	private ListPopularPaymentsOperation  listPopularPaymentsOperation;

	/**
	 * ѕолучаем список последних исполненных платежных документов клиента (количество платежных документов получаем из виджета)
	 * @return список документов
	 * @throws BusinessException
	 */
	public List<BusinessDocumentBase> getUserPayments() throws BusinessException
	{
		int count = getWidget().getNumberOfShowItems();
        return listPopularPaymentsOperation.getUserPayments(count);
	}

	public ListPopularPaymentsOperation getListPopularPaymentsOperation()
	{
		return listPopularPaymentsOperation;
	}

	public void setListPopularPaymentsOperation(ListPopularPaymentsOperation listPopularPaymentsOperation)
	{
		this.listPopularPaymentsOperation = listPopularPaymentsOperation;
	}
}
