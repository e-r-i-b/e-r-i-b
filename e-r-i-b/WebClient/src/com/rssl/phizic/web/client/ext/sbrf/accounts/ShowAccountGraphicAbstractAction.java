package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.bankroll.TransactionComparator;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;

import java.util.Collections;

/**
 * @author Erkin
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowAccountGraphicAbstractAction extends ShowAccountOperationsAction
{
	protected Form getFilterForm()
	{
		return ShowAccountGraphicAbstractForm.FILTER_FORM;
	}

	@Override
	protected String getAbstarctMessageError()
	{
		return StoredResourceMessages.getUnreachableGraphStatement();
	}

	protected void updateFormData(ShowAccountOperationsForm form, GetAccountAbstractExtendedOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateFormData(form, operation);
		// для графиков необходим обратный порядок транзакция от прошлого к будушему
		if (form.getAccountAbstract() != null && form.getAccountAbstract().getTransactions() != null)
			Collections.sort(form.getAccountAbstract().getTransactions(), new TransactionComparator(TransactionComparator.DESC));
	}
}
