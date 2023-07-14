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
 * Экшн списка тарифов на перевод в другой ТБ
 * @author niculichev
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTariffTransferOtherTBAction extends ListActionBase
{

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ListTariffTransferOtherTBOperation", "TariffTransferOtherTBService");
	}

	protected void doStart(ListEntitiesOperation op, ListFormBase frm) throws Exception
	{
		ListTariffTransferOtherTBForm form = (ListTariffTransferOtherTBForm) frm;
		ListTariffOperation operation = (ListTariffOperation) op;
		// заполняем тарифы для перевода в другой тб на свой счет
		form.setTariffsOnOwnAccount(operation.getTransOtherTBOnOwnAccount());
		// заполняем тарифы на для перевода в другой тб на чужой счет
		form.setTariffsOnAnotherAccount(operation.getTransOtherTBOnAnotherAccount());
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
		return createOperation("RemoveTariffTransferOtherTBOperation", "TariffTransferOtherTBService");
	}
}
