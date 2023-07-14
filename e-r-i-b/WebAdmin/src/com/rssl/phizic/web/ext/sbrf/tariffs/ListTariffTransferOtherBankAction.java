package com.rssl.phizic.web.ext.sbrf.tariffs;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.tariffs.ListTariffOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * Экшн списка тарифов перевода в другой банк
 * @author niculichev
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTariffTransferOtherBankAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ListTariffTransferOtherBankOperation", "TariffTransferOtherBankService");
	}

	protected void doStart(ListEntitiesOperation op, ListFormBase frm) throws Exception
	{
		ListTariffTransferOtherBankForm form = (ListTariffTransferOtherBankForm) frm;
		ListTariffOperation operation = (ListTariffOperation) op;
		// заполняем тарифы для перевода в другой банк
		form.setTariffsInOtherBank(operation.getTransOtherBank());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		// фильтра нет
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		doStart(operation, frm);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("RemoveTariffTransferOtherBankOperation", "TariffTransferOtherBankService");
	}
}
