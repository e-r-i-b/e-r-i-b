package com.rssl.phizic.web.client.abstr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.operations.pfr.ListPFRClaimOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получение выписки через Ajax.
 */
public class AsyncExtractAction extends OperationalActionBase
{
    public static final String FORWARD_ABSTRACT = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncExtractForm frm = (AsyncExtractForm)form;
		if(StringHelper.isEmpty(frm.getType()) || StringHelper.isEmpty(frm.getId()))
		{
			return errorPage(mapping,frm,request,response);
		}

		Long id = null;

		try
		{
			id = Long.parseLong(frm.getId());
		}
		catch (NumberFormatException ex)
		{
			return errorPage(mapping,frm,request,response);
		}

		addLogParameters(new SimpleLogParametersReader("Идентификатор сущности", id));
		addLogParameters(new SimpleLogParametersReader("Тип сущности", frm.getType()));

		try
		{
			if (AsyncExtractForm.Loan.equals(frm.getType()) && checkAccess(GetLoanAbstractOperation.class))
			{
				if(setLoanAbstract(id, frm))
					return errorPage(mapping,frm,request,response);
			}
			else if (AsyncExtractForm.Account.equals(frm.getType()) && checkAccess(GetAccountAbstractExtendedOperation.class))
			{
				if(setAccountAbstract(id, frm))
					return errorPage(mapping,frm,request,response);
			}
			else if (AsyncExtractForm.Card.equals(frm.getType()) && checkAccess(GetCardAbstractOperation.class))
			{
				if(setCardAbstract(id, frm))
					return errorPage(mapping,frm,request,response);
			}
			else if (AsyncExtractForm.Ima.equals(frm.getType()) && checkAccess("GetIMAccountShortAbstractOperation"))
			{
				if(setIMAccountAbstract(id, frm))
					return errorPage(mapping,frm,request,response);
			}
			else if (AsyncExtractForm.PFR.equals(frm.getType()) && checkAccess(ListPFRClaimOperation.class))
			{
				if(setPfrAbstract(frm))
					return errorPage(mapping, frm, request, response);
			}
			return mapping.findForward(FORWARD_ABSTRACT);

		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении выписки", ex);
			return errorPage(mapping,frm,request,response);
		}
		
	}

	private ActionForward errorPage(ActionMapping mapping, AsyncExtractForm form, HttpServletRequest request, HttpServletResponse response)
	{
		form.setError(true);
		return mapping.findForward(FORWARD_ABSTRACT);
	}

	protected boolean setAccountAbstract(Long id, AsyncExtractForm form) throws BusinessException, BusinessLogicException, DataAccessException
	{
		GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
		operation.initialize(id);
		Map<AccountLink, AccountAbstract> accountAbstract = operation.getAccountAbstract(Constants.MAX_COUNT_OF_TRANSACTIONS);
		AccountLink accountLink = operation.getAccount();
		form.setAccountAbstract(accountAbstract.get(accountLink));
		form.setAbstractMsgError(operation.getAccountAbstractMsgErrorMap().get(accountLink));
		return operation.isBackError() || operation.isError();
	}

	protected boolean setCardAbstract(Long id, AsyncExtractForm form) throws BusinessException, BusinessLogicException
	{
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(id);
		Map<CardLink, CardAbstract> cardAbstract = operation.getCardAbstract(Constants.MAX_COUNT_OF_TRANSACTIONS);
		CardLink cardLink = operation.getCard();
		form.setCardAbstract(cardAbstract.get(cardLink));
		form.setCardEntity(cardLink.getCard());
		form.setAbstractMsgError(operation.getCardAbstractMsgErrorMap().get(cardLink));
		return operation.isBackError() || operation.isError();
	}

	protected boolean setLoanAbstract(Long id, AsyncExtractForm form) throws BusinessException, BusinessLogicException
	{
		GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
		abstractOperation.initialize(id);
		Map<LoanLink, ScheduleAbstract> scheduleAbstract = abstractOperation.getScheduleAbstract(-Constants.MAX_COUNT_OF_TRANSACTIONS, Constants.MAX_COUNT_OF_TRANSACTIONS, true).getFirst();
		LoanLink loanLink = abstractOperation.getLoanLink();
		form.setLoanAbstract(scheduleAbstract.get(loanLink));
		form.setAbstractMsgError(abstractOperation.getLoanAbstractMsgErrorMap().get(loanLink));
		return abstractOperation.isError();
	}

	protected boolean setIMAccountAbstract(Long id, AsyncExtractForm form) throws BusinessException, BusinessLogicException
	{
		GetIMAccountAbstractOperation abstractOperation = createOperation("GetIMAccountShortAbstractOperation");
		abstractOperation.initialize(id, false);
		Map<IMAccountLink, IMAccountAbstract> imAccountAbstract = abstractOperation.getIMAccountAbstractMap(Constants.MAX_COUNT_OF_TRANSACTIONS);
		IMAccountLink imAccountLink = abstractOperation.getEntity();
		form.setImAccountAbstract(imAccountAbstract.get(imAccountLink));
		form.setAbstractMsgError(abstractOperation.getAccountAbstractMsgErrorMap().get(imAccountLink));
		return abstractOperation.isBackError() || abstractOperation.isError();
	}

	protected boolean setPfrAbstract(AsyncExtractForm form) throws BusinessException, BusinessLogicException
	{
		ListPFRClaimOperation operation = createOperation(ListPFRClaimOperation.class);
		operation.initialize();
		form.setPfrClaims(operation.getPfrClaims(Constants.MAX_COUNT_OF_TRANSACTIONS.intValue()));
		return operation.isError();
	}
}
