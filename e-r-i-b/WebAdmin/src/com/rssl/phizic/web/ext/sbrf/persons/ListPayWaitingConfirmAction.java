package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.erkc.context.Functional;
import com.rssl.phizic.operations.ext.sbrf.payment.ListPayWaitingConfirmOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.payments.ViewDocumentListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * action списка операций для подтверждения
 * @author niculichev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListPayWaitingConfirmAction extends ViewDocumentListActionBase
{
	private static final String DEFAULT_STATE = "WAIT_CONFIRM";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ERKCEmployeeUtil.setCurrentFunctionalInfo(Functional.paymentWaitConfirm);
		ListPayWaitingConfirmOperation operation = createOperation(ListPayWaitingConfirmOperation.class);
		ListPayWaitingConfirmForm form = (ListPayWaitingConfirmForm) frm;
		operation.initialize(form.getPerson());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListPayWaitingConfirmForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		super.fillQuery(query, filterParams);//заполняем период

		query.setParameter("receiverName", filterParams.get("receiverName"));
		query.setParameter("number", filterParams.get("number"));
		query.setParameterList("state", ((String) filterParams.get("state")).split(","));

		query.setParameter("additionConfirm", StringHelper.getNullIfEmpty((String) filterParams.get("additionConfirm")));
		query.setParameter("confirmEmployee", StringHelper.getEmptyIfNull((String) filterParams.get("confirmEmployee")));
		if ("LongOffer".equals(filterParams.get("formName")))
		{
			query.setParameter("formName", null);
			query.setParameter("long_offer", "1");
		}
		else
		{
			query.setParameter("formName", filterParams.get("formName"));
			query.setParameter("long_offer", null);
		}
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		ListPayWaitingConfirmOperation op = (ListPayWaitingConfirmOperation) operation;
		ListPayWaitingConfirmForm frm = (ListPayWaitingConfirmForm) form;
		frm.setActivePerson(op.getPerson());
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String, Object> result = new HashMap<String, Object>();

		// две недели назад(14 дней)
		Calendar twoWeekAgo = Calendar.getInstance();
		// вычитаем 13 дней, т.к. fromTime ставится в начало дня, а toTime в конец дня, таким образом получается 14 дней
		twoWeekAgo.add(Calendar.DAY_OF_MONTH, -13);
		result.put("fromDate", String.format("%1$td.%1$tm.%1$tY", twoWeekAgo));
		result.put("fromTime", DateHelper.BEGIN_DAY_TIME);

		Calendar currentDate = Calendar.getInstance();
		result.put("toDate", String.format("%1$td.%1$tm.%1$tY", currentDate));
		result.put("toTime", DateHelper.END_DAY_TIME);

		// статус по умолчанию в фильтре "Подтвержден"
		result.put("state", DEFAULT_STATE);

		return result;
	}
}
