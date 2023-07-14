package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.business.documents.payments.PaymentHistoryHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;

import java.util.List;

/**
 * @author mihaylov
 * @ created 26.12.13
 * @ $Author$
 * @ $Revision$
 * Операция отображения списка документов сотруднику
 */
public class GetEmployeePaymentListOperation extends ListForEmployeeOperation implements ListEntitiesOperation
{

	/**
	 * Формируем список FilterPaymentForm (идентификаторы форм:псевдоним формы)
	 * @return список FilterPaymentForm
	 */
	public List<FilterPaymentForm> getFilterPaymentForms() throws BusinessException
	{
		return PaymentHistoryHelper.getFilterPaymentForms();
	}

	@Override public Query createQuery(String name)
	{
		Query query = super.createQuery(name);
		query.setListTransformer(new EmployeePaymentListTransformer());
		return query;
	}
}
