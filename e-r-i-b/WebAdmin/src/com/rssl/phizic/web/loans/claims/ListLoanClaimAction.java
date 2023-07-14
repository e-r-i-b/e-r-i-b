package com.rssl.phizic.web.loans.claims;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.loans.claims.ListLoanClaimOperation;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 20.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanClaimAction extends SaveFilterParameterAction
{

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.refuse", "refuse");
		map.put("button.accept", "accept");
		map.put("button.completion", "completion");
		map.put("button.archive", "archive");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListLoanClaimOperation.class);
	}

	protected Map<String, Object> getDefaultFilterParameters() throws BusinessException
	{
		Map<String, Object> filterParams = new HashMap<String, Object>();
		filterParams.put("state", "");
		Calendar currentDate = Calendar.getInstance();
		filterParams.put("fromDate", String.format("%1$td.%1$tm.%1$tY", currentDate));
		filterParams.put("toDate", String.format("%1$td.%1$tm.%1$tY", currentDate));

		return filterParams;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		ListLoanClaimOperation operation = createOperation(ListLoanClaimOperation.class);

		query.setParameter("archive", false);
		query.setParameter("fromDate", filterParams.get("fromDate"));
		query.setParameter("toDate", filterParams.get("toDate"));
		query.setParameter("state", filterParams.get("state"));
		query.setParameter("firstName", filterParams.get("firstName"));
		query.setParameter("surName", filterParams.get("surName"));
		query.setParameter("patrName", filterParams.get("patrName"));
		query.setParameter("document", filterParams.get("document"));
		query.setParameter("claimNumber", filterParams.get("claimNumber"));
		query.setParameter("fromClientRequestAmount", filterParams.get("fromClientRequestAmount"));
		query.setParameter("toClientRequestAmount", filterParams.get("toClientRequestAmount"));
		query.setParameter("loanKind", filterParams.get("loanKind"));
		query.setParameter("anonymousClientLogin", operation.getAnonymousClientLoginId());
		query.setParameter("clientType", filterParams.get("clientType"));

		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		query.setParameter("loginId", employeeData.getEmployee().getLogin().getId());

		LoanOffice loanOffice = operation.getEmployeeLoanOffice();
		if (loanOffice.isMain())
		{
			query.setParameter("office", filterParams.get("office"));
		}
		else
		{
			query.setParameter("office", loanOffice.getSynchKey().toString());
		}

		query.setMaxResults(webPageConfig().getListLimit()+1);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListLoanClaimForm frm = (ListLoanClaimForm) form;
		ListLoanClaimOperation op = (ListLoanClaimOperation) operation;

		LoanOffice loanOffice = op.getEmployeeLoanOffice();

		if (loanOffice.isMain())
			frm.setEmployeeFromMainOffice(true);
		else
			frm.setEmployeeFromMainOffice(false);

		frm.setLoanKinds(op.getAllLoanKinds());
		frm.setLoanOffices(op.getAllLoanOffices());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListLoanClaimForm.FILTER_FORM;
	}

	public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLoanClaimForm frm = (ListLoanClaimForm) form;
		String[] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "error.claim.noSelected", request);
			return filter(mapping, form, request, response);
		}

		for (String id : selectedIds)
		{
			Long currentId = Long.parseLong(id);
			ProcessDocumentOperation operation = initOperation(currentId);
			operation.refuse(null);

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		}
		frm.clearSelection();
		return filter(mapping, form, request, response);
	}

	public ActionForward accept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLoanClaimForm frm = (ListLoanClaimForm) form;
		String[] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "error.claim.noSelected", request);
			return filter(mapping, form, request, response);
		}

		for (String id : selectedIds)
		{
			Long currentId = Long.parseLong(id);
			ProcessDocumentOperation operation = initOperation(currentId);
			operation.accept();

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		}
		frm.clearSelection();
		return filter(mapping, form, request, response);
	}

	public ActionForward completion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLoanClaimForm frm = (ListLoanClaimForm) form;
		String[] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "error.claim.noSelected", request);
			return filter(mapping, form, request, response);
		}

		for (String id : selectedIds)
		{
			Long currentId = Long.parseLong(id);
			ProcessDocumentOperation operation = initOperation(currentId);
			operation.completion();

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		}
		frm.clearSelection();
		return filter(mapping, form, request, response);
	}

	public ActionForward archive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLoanClaimForm frm = (ListLoanClaimForm) form;
		String[] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "error.claim.noSelected", request);
			return filter(mapping, form, request, response);
		}
		else
		{
			for (String id : selectedIds)
			{
				Long currentId = Long.parseLong(id);
				ProcessDocumentOperation operation = initOperation(currentId);
				if (!operation.checkBeforeSendInArchive())
				{
					saveError(ActionMessages.GLOBAL_MESSAGE, "error.claim.notArchive", request);
					return filter(mapping, form, request, response);
				}
			}
		}

		for (String id : selectedIds)
		{
			Long currentId = Long.parseLong(id);
			ProcessDocumentOperation operation = initOperation(currentId);
			operation.sendInArchive();

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		}
		frm.clearSelection();
		return filter(mapping, form, request, response);
	}

	protected ProcessDocumentOperation initOperation(Long id) throws BusinessException, BusinessLogicException
	{
		ExistingSource source = new ExistingSource(id, new NullDocumentValidator());
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		operation.initialize(source);

		return operation;
	}
}
