package com.rssl.phizic.web.client.payments;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.business.documents.payments.PaymentHistoryHelper;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.operations.payment.GetCommonPaymentListOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.PaymentFormsComparator;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.payments.ClientDocumentsListActionBase;
import com.rssl.phizic.web.component.DatePeriodFilter;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kosyakova
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowCommonPaymentListAction extends ClientDocumentsListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.withdraw", "withdraw");
		map.put("button.history.find", "filter");
		map.put("button.periodFilter", "filter");
		return map;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowCommonPaymentListForm.FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		ShowCommonPaymentListForm frm = (ShowCommonPaymentListForm)form;
		GetCommonPaymentListOperation listOperation = (GetCommonPaymentListOperation) operation;

		List<FilterPaymentForm> filterPaymentForms = listOperation.getFilterPaymentForms();
		Collections.sort(filterPaymentForms, new PaymentFormsComparator(filterPaymentForms, "paymentsBundle"));

		frm.setFilterPaymentForms(filterPaymentForms);
		frm.setAccounts(listOperation.getAccounts());
		frm.setCards(listOperation.getCards());
		frm.setImaccounts(listOperation.getIMAccounts());
	}

	public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowCommonPaymentListForm frm       = (ShowCommonPaymentListForm) form;
		ViewDocumentOperation     operation = createOperation(ViewDocumentOperation.class, "PaymentList");
		String[] selectedIds = frm.getSelectedIds();

		ActionMessages actionErrors = new ActionMessages();
		if (selectedIds.length != 1)
		{
			actionErrors.add("Необходимо выбрать ровно один элемент", new ActionMessage("Необходимо выбрать ровно один элемент", false));
		}

		for (String selectedId: selectedIds)
		{
			ExistingSource source = new ExistingSource(Long.parseLong(selectedId), new IsOwnDocumentValidator());
			operation.initialize(source);
			if (!operation.canWithdraw())
			{
				actionErrors.add("Выбранный документ нельзя отозвать", new ActionMessage("Выбранный документ нельзя отозвать", false));
			}
			break;
		}

		if (actionErrors.size() > 0)
		{
			saveErrors(request, actionErrors);
			return start(mapping, form, request, response);
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return new ActionForward("/private/payments/add.do" + "?form=RecallPayment&parentId="+selectedIds[0]);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		super.fillQuery(query, filterParams);

		String clientState = (String) filterParams.get("clientState");
		query.setParameter("clientState", clientState);
		List formIds = StringHelper.isEmpty((String) filterParams.get("formId")) ?
				Collections.emptyList() :
				ListUtil.fromArray(((String) filterParams.get("formId")).split(PaymentHistoryHelper.DELIMITER));

		boolean pfpSelected = formIds.remove(PaymentHistoryHelper.PFP_FILTER_PAYMENT_FORM.getIds());
		boolean firSelected = formIds.remove(PaymentHistoryHelper.FIR_FILTER_PAYMENT_FORM.getIds());
		boolean issueCardSelected = formIds.remove(PaymentHistoryHelper.DEBIT_CARD_FILTER_PAYMENT_FORM.getIds());
		boolean extLoanClaimSelected = formIds.remove(PaymentHistoryHelper.EXTENDED_LOAN_CLAIM_FORM.getIds());
		boolean loanCardClaimSelected = formIds.remove(PaymentHistoryHelper.LOAN_CARD_CLAIM_FORM.getIds());
		query.setListParameters("formId", formIds, MAX_BIND_VARS_FOR_INT_COUNT);
		query.setParameter("pfpSelected", pfpSelected);
		query.setParameter("firSelected", firSelected);
		query.setParameter("issueCardSelected", issueCardSelected);
		query.setParameter("extLoanClaimSelected", extLoanClaimSelected);
		query.setParameter("loanCardClaimSelected", loanCardClaimSelected);

		query.setParameter("autoPayment", filterParams.get("autoPayment"));

		BigDecimal fromAmount = (BigDecimal) filterParams.get("fromAmount");
		query.setParameter("fromAmount", fromAmount);
		BigDecimal toAmount = (BigDecimal) filterParams.get("toAmount");
		query.setParameter("toAmount", toAmount);
		String amountCurrency = (String) filterParams.get("amountCurrency");
		query.setParameter("amountCurrency", StringHelper.getNullIfEmpty(amountCurrency));

		ExternalResourceLink account = (ExternalResourceLink) filterParams.get("account");
		query.setParameter("account", (account == null) ? null : account.getNumber());

		String receiverName = (String) filterParams.get("receiverName");
		query.setParameter("receiverName", receiverName);
		Boolean showPfp = (!ApplicationUtil.isApi() &&
						   (account == null) &&
						   fromAmount == null &&
						   toAmount == null &&
						   StringHelper.isEmpty(amountCurrency)&&
						   ((formIds.isEmpty() && !firSelected && !issueCardSelected) || pfpSelected)
						  );
		query.setParameter("showPfp", showPfp);

		Boolean showIssueCard = (!ApplicationUtil.isApi() &&
						   (account == null) &&
						   fromAmount == null &&
						   toAmount == null &&
						   StringHelper.isEmpty(amountCurrency)&&
						   ((formIds.isEmpty() && !firSelected && !pfpSelected) || issueCardSelected)
						  );
		query.setParameter("showIssueCard", showIssueCard);


		Boolean showFir = (!ApplicationUtil.isApi() &&
						   (account == null) &&
						   (StringHelper.isEmpty(clientState)) &&
						   (StringHelper.isEmpty(amountCurrency) || amountCurrency.equals("RUB") || amountCurrency.equals("RUR")) &&
						   ((formIds.isEmpty() && !pfpSelected && !issueCardSelected) || firSelected)
						  );
		query.setParameter("showFir", showFir);
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String,Object> parameters = new HashMap<String, Object>();
		Calendar endDate = Calendar.getInstance();
		Calendar startDate = (Calendar) endDate.clone();
		startDate.add(Calendar.MONTH, -1);
		parameters.put(DatePeriodFilter.TO_DATE, endDate.getTime());
		parameters.put(DatePeriodFilter.FROM_DATE, startDate.getTime());
		parameters.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		return parameters;
	}
}

