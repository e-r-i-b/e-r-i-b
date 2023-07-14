package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.loans.claims.ListLoanClaimOperation;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.LoanClaim;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author emakarov
 * @ created 09.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class AddLoanClaimCommentAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CANCEL = "Cancel";
	private static final String FORWARD_COMMENT = "Comment";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.save", "comment");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AddLoanClaimCommentForm frm =(AddLoanClaimCommentForm)form;
		ProcessDocumentOperation operation;

		Long ids[] = frm.getClaimIds();

		if (ids == null)
		{
			// если не было передано ни одного id-заявки, то переходим к списку заявок
			saveError(ActionMessages.GLOBAL_MESSAGE, "error.claim.noSelected", request);
			return mapping.findForward(FORWARD_CANCEL);
		}
		else if (ids.length == 1) {
			operation = initOperation(ids[0]);
			if (operation.getEntity() instanceof LoanClaim)
				frm.setComment(((LoanClaim)operation.getEntity()).getComment());
			else
				throw new BusinessException("Неверный тип документа " + operation.getEntity().getClass().getName() + ". Ожидается LoanClaim");

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		}
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward comment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AddLoanClaimCommentForm frm =(AddLoanClaimCommentForm)form;

		ProcessDocumentOperation operation;
		String comment = frm.getComment();

		Long ids[] = frm.getClaimIds();
		for (int i = 0; i < ids.length; i++)
		{
			operation = initOperation(ids[i]);
			if (operation.getEntity() instanceof LoanClaim)
			{
				operation.setComment(comment);

				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
			}
			else
				throw new BusinessException("Неверный тип документа " + operation.getEntity().getClass().getName() + ". Ожидается LoanClaim");
		}

		return mapping.findForward(FORWARD_COMMENT);
	}

	protected ProcessDocumentOperation initOperation(Long id) throws BusinessException, BusinessLogicException
	{
		ExistingSource source = new ExistingSource(id, new NullDocumentValidator());
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		operation.initialize(source);

		ListLoanClaimOperation operationLO = createOperation(ListLoanClaimOperation.class);
		LoanOffice employeeLoanOffice = operationLO.getEmployeeLoanOffice();
		String claimLoanOffice = ((LoanClaim)operation.getEntity()).getOfficeExternalId();
		if (!(employeeLoanOffice.isMain() || claimLoanOffice.equals(employeeLoanOffice.getSynchKey().toString()))) {
			throw new BusinessException("Неверный номер кредитного офиса:" + employeeLoanOffice.getSynchKey().toString() + ". Заявка принадлежит другому кредитному офису.");
		}

		return operation;
	}
}
