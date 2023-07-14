package com.rssl.phizic.web.client.depo;

import com.rssl.common.forms.Form;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.depo.GetDepoAccountListOperation;
import com.rssl.phizic.operations.depo.GetDepositaryDocumentsListOperation;
import com.rssl.phizic.operations.payment.RecallDocumentOperation;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 27.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountDocumentsListAction extends ListActionBase
{
	private static final String MESSAGE = "Удалять можно только документы со статусом «Введен». Для отзыва документов воспользуйтесь кнопкой «Отозвать»";
	private static final String MESSAGE_RECALL = "Вы не можете отозвать этот документ";

	protected static final String BACK_ERROR_DEPO_ACCOUNT_MESSAGE  = "Операция временно недоступна. Повторите попытку позже.";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.withdraw", "recall");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowDepoAccountDocumentsListForm form = (ShowDepoAccountDocumentsListForm) frm;
		Long linkId = form.getId();

		GetDepositaryDocumentsListOperation operation = createOperation(GetDepositaryDocumentsListOperation.class);
		operation.initialize(linkId);

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowDepoAccountDocumentsListForm.FILTER_FORM;
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Period period = FilterPeriodHelper.getWeekPeriod();
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME, FilterPeriodHelper.PERIOD_TYPE_WEEK);
		parameters.put(FilterPeriodHelper.FROM_DATE_FIELD_NAME, period.getFromDate().getTime());
		parameters.put(FilterPeriodHelper.TO_DATE_FIELD_NAME, period.getToDate().getTime());
		return parameters;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		Period period = FilterPeriodHelper.getPeriod(
				(String) filterParams.get(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.FROM_DATE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.TO_DATE_FIELD_NAME)
		);

		query.setParameter("fromDate", period.getFromDate());
		query.setParameter("toDate", period.getToDate());

		query.setParameter("number", filterParams.get("docNumber"));
		query.setParameter("state", filterParams.get("docState"));
		query.setParameter("formName", filterParams.get("docType"));

		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ShowDepoAccountDocumentsListForm form = (ShowDepoAccountDocumentsListForm) frm;
		GetDepositaryDocumentsListOperation op = (GetDepositaryDocumentsListOperation) operation;
		form.setDepoAccountLink(op.getDepoAccountLink());
		setAnotherDepoAccounts(form, op.getDepoAccountLink());

		if (op.isUseStoredResource())
		{
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) op.getDepoAccountLink().getDepoAccount()));
		}
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowDepoAccountDocumentsListForm frm = (ShowDepoAccountDocumentsListForm) form;
		Long id = frm.getSelectedId();
		DocumentSource source = new ExistingSource(id, new IsOwnDocumentValidator());
		return createOperation(RemoveDocumentOperation.class,source.getDocument().getFormName());
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		RemoveDocumentOperation op = (RemoveDocumentOperation)operation;
		ActionMessages msgs = new ActionMessages();
		try
		{
			DocumentSource source = new ExistingSource(id, new IsOwnDocumentValidator());
			op.initialize(source);
			op.remove();
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MESSAGE, false));
		}
		return msgs;
	}

	public ActionForward recall(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowDepoAccountDocumentsListForm frm = (ShowDepoAccountDocumentsListForm) form;
		RecallDocumentOperation operation = createOperation(RecallDocumentOperation.class, "RecallDepositaryClaim");
		operation.initialize(frm.getSelectedId());

		if (operation.getDocumentState().equals(new State("DELAYED_DISPATCH")))
		{
			operation.recall();
		}
		else
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MESSAGE_RECALL, false));
			saveErrors(request, errors);
		}
        return filter(mapping, form, request, response);
	}

	private void setAnotherDepoAccounts(ShowDepoAccountDocumentsListForm frm, DepoAccountLink link) throws BusinessException, BusinessLogicException
	{
		GetDepoAccountListOperation listOperation = createOperation(GetDepoAccountListOperation.class);
	    List<DepoAccountLink> anotherDepoAccounts = listOperation.getDepoAccounts();
	    anotherDepoAccounts.remove(link);
	    frm.setAnotherDepoAccounts(anotherDepoAccounts);

		if (listOperation.isBackError())
		{
			saveMessage(currentRequest(),BACK_ERROR_DEPO_ACCOUNT_MESSAGE);
		}
	}
}
