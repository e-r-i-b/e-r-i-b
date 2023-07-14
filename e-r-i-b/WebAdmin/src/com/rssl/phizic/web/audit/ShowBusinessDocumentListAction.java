package com.rssl.phizic.web.audit;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.business.documents.payments.PaymentHistoryHelper;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.statemachine.IllegalEventException;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.GetEmployeePaymentListOperation;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.operations.payment.SpecifyStatusDocumentOperation;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.PaymentFormsComparator;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.payments.ViewDocumentListActionBase;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 28.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowBusinessDocumentListAction extends ViewDocumentListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.execute", "process");
		map.put("button.specify", "specify");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessLogicException, BusinessException
	{
		return createOperation(GetEmployeePaymentListOperation.class, "ViewPaymentList");
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
			throws BusinessException
	{
		ShowBusinessDocumentListForm frm = (ShowBusinessDocumentListForm)form;
		GetEmployeePaymentListOperation listOperation = (GetEmployeePaymentListOperation) operation;

		List<FilterPaymentForm> filterPaymentForms = listOperation.getFilterPaymentForms();
		Collections.sort(filterPaymentForms, new PaymentFormsComparator(filterPaymentForms, "paymentsBundle"));

		frm.setFilterPaymentForms(filterPaymentForms);
		frm.setListLimit(webPageConfig().getListLimit());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowBusinessDocumentListForm.FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		super.fillQuery(query, filterParams);    //заполняем данные о периоде

		query.setParameter("receiverName", filterParams.get("receiverName"));
		query.setParameter("number", filterParams.get("number"));

		String states = (String) filterParams.get("state");
		query.setListParameters("state", StringHelper.isEmpty(states) ? null : states.split(PaymentHistoryHelper.DELIMITER), MAX_BIND_VARS_FOR_INT_COUNT);

		query.setParameter("hideDeleted", StringHelper.isEmpty(states) && BooleanUtils.isNotTrue(BooleanUtils.toBoolean((String) filterParams.get("showDeleted"))));
		query.setParameter("hideInitialPayments", StringHelper.isEmpty(states) && BooleanUtils.isNotTrue(BooleanUtils.toBoolean((String) filterParams.get("showInitialPayments"))));

		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter(ShowBusinessDocumentListForm.DUL_FIELD_NAME, StringUtils.deleteWhitespace((String) filterParams.get("dul")));
		query.setParameter("employeeFIO",filterParams.get("employeeFIO"));

		query.setParameter("birthday", filterParams.get("birthday"));
		query.setParameter("loginId", filterParams.get("loginId"));
		query.setParameter("nameOSB",filterParams.get("nameOSB"));
		query.setParameter("madeOperationId",filterParams.get("madeOperationId"));

		query.setParameter("autoPayment", filterParams.get("autoPayment"));

		String formIds = (String) filterParams.get("formId");
		List listFormIds = StringHelper.isEmpty(formIds) ? Collections.emptyList() :
						ListUtil.fromArray((formIds).split(PaymentHistoryHelper.DELIMITER));

		boolean extLoanClaimSelected = listFormIds.remove(PaymentHistoryHelper.EXTENDED_LOAN_CLAIM_FORM.getIds());
		query.setParameter("extLoanClaimSelected", extLoanClaimSelected);

		query.setListParameters("formId", listFormIds.isEmpty() ? null : listFormIds, MAX_BIND_VARS_FOR_INT_COUNT);

		query.setParameter("fromAmount", filterParams.get("fromAmount"));
		query.setParameter("toAmount", filterParams.get("toAmount"));

		String creationType = (String) filterParams.get(ShowBusinessDocumentListForm.CREATION_TYPE_FIELD_NAME);
		query.setParameter(ShowBusinessDocumentListForm.CREATION_TYPE_FIELD_NAME, StringHelper.getNullIfEmpty(creationType));
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ShowBusinessDocumentListForm frm = (ShowBusinessDocumentListForm)form;
		if (frm.getFields().size() != 0  || frm.getFilters().size() != 0)
			frm.setFromStart(false);

		//Грузим данные только если кликнули фильтр или если работаем в рамках функционала аудита
		if (!frm.isFromStart())
		{
			super.doFilter(filterParams, operation, frm);
		}	
		else
		{
			frm.setData(new ArrayList(0));
			frm.setFilters(filterParams);
			updateFormAdditionalData(frm, operation);
		}
	}

	public ActionForward process(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowBusinessDocumentListForm frm    = (ShowBusinessDocumentListForm) form;
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);

		ActionMessages msgs = new ActionMessages();

		for (Long id : StrutsUtils.parseIds(frm.getSelectedIds()))
		{
			try
			{
				DocumentSource source = new ExistingSource(id, new NullDocumentValidator());
				operation.initialize(source);
				State state = operation.getEntity().getState();
				operation.execute();

				//лог.
				addLogParameters(new BeanLogParemetersReader("Первоначальный статус", state));
				addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", operation.getEntity()));
			}
			catch (IllegalEventException e)
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("wrong.document.state.exception." +
					    (e.getStateObject().getState().equals(new State("UNKNOW")) || e.getStateObject().getState().equals(new State("SENT"))? e.getObjectEvent().getEvent() : "default"), true));

			}
			catch (BusinessLogicException e)
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			}
		}

		if (!msgs.isEmpty())
			saveErrors(currentRequest(), msgs);
		
		return filter(mapping, form, request, response);
	}

	public ActionForward specify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowBusinessDocumentListForm frm    = (ShowBusinessDocumentListForm) form;
		SpecifyStatusDocumentOperation operation = createOperation("SpecifyStatusDocumentOperation");

		ActionMessages msgs = new ActionMessages();
		try
		{
			for (Long id : StrutsUtils.parseIds(frm.getSelectedIds()))
			{
				try
				{
					DocumentSource source = new ExistingSource(id, new NullDocumentValidator());
					operation.initialize(source.getDocument());

					operation.specify();

					addLogParameters(new BeanLogParemetersReader("Данные сущности повторной отправки", operation.getEntity()));
				}
				catch (IllegalEventException e)
				{
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("wrong.document.state.exception." +  e.getObjectEvent().getEvent()));
				}
				catch (BusinessLogicException e)
				{
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				}
			}
		}
		finally
		{
			saveErrors(currentRequest(), msgs);
		}
		return filter(mapping, form, request, response);
	}

	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		//Проверяем перешли ли мы с анкеты клиента
		String loginId = (String) frm.getField(ShowBusinessDocumentListForm.LOGIN_ID_FIELD_NAME);
		if(!StringHelper.isEmpty(loginId))
		{
			//да. поле field(loginId) заполняется только при переходе из анкеты.
			//сеттим его вы параметры фильтра
			frm.setFilter(ShowBusinessDocumentListForm.LOGIN_ID_FIELD_NAME, loginId);
			//и добавляем дефолтные знаяения фильтра
			frm.addFilters(getDefaultFilterParameters(frm, operation));
		}
		return super.getFilterParams(frm, operation);
	}
}
