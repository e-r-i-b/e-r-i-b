package com.rssl.phizic.web.common.client.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.GetCommonPaymentListOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentListFormBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.Date;
import java.util.Map;

/**
 * @author krenev
 * @ created 29.03.2013
 * @ $Author$
 * @ $Revision$
 * Базовый класс общего списка документов клиента.
 */
public abstract class ClientDocumentsListActionBase extends SaveFilterParameterAction
{
	/**
	 * Максимальное количество переменный во фразах in.
	 */
	public static final int MAX_BIND_VARS_FOR_INT_COUNT = 10;

	protected String getQueryName(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return "clientHistory";
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessLogicException, BusinessException
	{
		return createOperation(GetCommonPaymentListOperation.class, "PaymentList");
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ViewDocumentListFormBase frm = (ViewDocumentListFormBase) form;
		return mapping.getPath() + "/" + frm.getStatus();
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		Period period = FilterPeriodHelper.getPeriod(
				(String) filterParams.get(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.FROM_DATE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.TO_DATE_FIELD_NAME)
		);

		query.setParameter("fromDate", period.getFromDate());
		query.setParameter("toDate", period.getToDate());
		query.setParameter("showPfp", !ApplicationUtil.isApi());
		query.setParameter("showIssueCard", !ApplicationUtil.isApi());
		query.setParameter("pfpSelected", false);
		query.setParameter("issueCardSelected", false);
		query.setParameter("showFir", !ApplicationUtil.isApi());
		query.setParameter("firSelected", false);

		query.setParameter("clientState", null);
		query.setListParameters("formId", (Object[]) null, MAX_BIND_VARS_FOR_INT_COUNT);
		query.setParameter("creationType", null);
		query.setParameter("autoPayment", null);
		query.setParameter("fromAmount", null);
		query.setParameter("toAmount", null);
		query.setParameter("amountCurrency", null);
		query.setParameter("account", null);
		query.setParameter("receiverName", null);
		query.setParameter("paymentStatus", null);
		query.setParameter("extLoanClaimSelected", false);
		query.setParameter("loanCardClaimSelected", false);
	}
}