package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.loans.claims.ListLoanClaimOperation;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
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
 * @ created 03.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanClaimArchiveAction extends ListLoanClaimAction
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.fromarchive", "returnFromArchive");
		return map;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation operation)
	{
		Map<String, Object> filterParams = new HashMap<String, Object>();
		filterParams.put("state", "");
		Calendar currentDate = DateHelper.getCurrentDate();
		filterParams.put("fromDate", String.format("%1$td.%1$tm.%1$tY", currentDate));
		filterParams.put("toDate", String.format("%1$td.%1$tm.%1$tY", currentDate));

		return filterParams;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);
		query.setParameter("archive", true);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListLoanClaimForm frm = (ListLoanClaimForm) form;
		ListLoanClaimOperation op = (ListLoanClaimOperation) operation;

		frm.setFilter("state", "");
		Calendar currentDate = DateHelper.getCurrentDate();
		frm.setFilter("fromDate", String.format("%1$td.%1$tm.%1$tY", currentDate));
		frm.setFilter("toDate", String.format("%1$td.%1$tm.%1$tY", currentDate));

		LoanOffice loanOffice = op.getEmployeeLoanOffice();

		if (loanOffice.isMain())
			frm.setEmployeeFromMainOffice(true);
		else
			frm.setEmployeeFromMainOffice(false);

		frm.setLoanKinds(op.getAllLoanKinds());
		frm.setLoanOffices(op.getAllLoanOffices());
	}

	public ActionForward returnFromArchive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
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
			operation.returnFromArchive();

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		}
		frm.clearSelection();
		return filter(mapping, form, request, response);
	}
}
