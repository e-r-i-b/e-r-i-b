package com.rssl.phizic.web.client.ext.sevb.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.ext.sevb.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.web.common.client.abstr.PrintAccountAbstractAction;
import com.rssl.phizic.web.common.client.abstr.PrintAccountAbstractForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Omeliyanchuk
 * @ created 03.08.2006
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"ProhibitedExceptionDeclared"})
public class PrintAccountAbstractExtendedAction extends PrintAccountAbstractAction
{
	public static final String FORWARD_ABSTRACT = "PrintAbstract";
	public static final String FORWARD_INFORMATION = "PrintInformation";

	protected GetAccountAbstractOperation createGetAccountAbstractOperation(Long accountId, PrintAccountAbstractForm frm)
			throws BusinessException, BusinessLogicException
	{
		/**
		 * в зависимости от параметра формы сopying, выводится либо выписка(copying=false), либо справка о состоянии вклада.
		 */
		PrintAccountAbstractExtendedForm form = (PrintAccountAbstractExtendedForm)frm;
		if(!form.getCopying())
		{
			GetAccountAbstractOperation operation = createOperation(GetAccountAbstractOperation.class);
			operation.initialize(accountId);
			return operation;
		}
		else
		{
			GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
			operation.initialize(accountId);
			return operation;
		}
	}

	protected ActionForward findForward(PrintAccountAbstractForm frm, ActionMapping mapping)
	{
		PrintAccountAbstractExtendedForm form = (PrintAccountAbstractExtendedForm)frm;
		if(!form.getCopying())
		{
			return mapping.findForward(FORWARD_ABSTRACT);
		}
		else
		{
			return mapping.findForward(FORWARD_INFORMATION);
		}
	}

	protected PrintAccountAbstractForm createResponse(HttpServletRequest request, PrintAccountAbstractForm frm)
			throws Exception
	{
		Calendar fromDate;
		Calendar toDate;
		try
		{
			fromDate = frm.getFromDate();
			toDate = frm.getToDate();
		}
		catch (ParseException ex)
		{
			throw new IllegalArgumentException("неверный формат даты", ex);
		}

		Map<AccountLink, AccountAbstract> accountAbstract = new HashMap<AccountLink, AccountAbstract>();
		Map<CardLink, CardAbstract> cardAbstract = new HashMap<CardLink, CardAbstract>();

		String[] selectedResources = request.getParameterValues("sel");
		for (String res : selectedResources)
		{
			String sid = res.substring(2);
			Long id = new Long(sid);
			if (res.startsWith("a:") && super.checkAccess(GetAccountAbstractOperation.class))
			{
				GetAccountAbstractOperation op = createGetAccountAbstractOperation(id, frm);
				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				accountAbstract.put(op.getAccount(), op.getAccountAbstract());
				frm = fillAccountSpecificData(frm, op);
			}
			else if (res.startsWith("c:") && super.checkAccess(GetCardAbstractOperation.class) || super.checkAccess(GetAccountAbstractExtendedOperation.class))
			{
				GetCardAbstractOperation op = createOperation(GetCardAbstractOperation.class);
				op.initialize(id);
				//cardAbstract.put(op.getCard(), op.getCardAbstract(null));
				GetCardInfoOperation cardInfoOperation = createOperation(GetCardInfoOperation.class);
				cardInfoOperation.initialize(id);
			}
		}

		frm.setAccountAbstract(accountAbstract);
		frm.setCardAbstract(cardAbstract);
		return frm;
	}


	protected PrintAccountAbstractForm fillAccountSpecificData(PrintAccountAbstractForm frm, GetAccountAbstractOperation operation) throws Exception
	{
		PrintAccountAbstractExtendedForm form = (PrintAccountAbstractExtendedForm) frm;
		List<Account> accounts = form.getAccounts();

		if(accounts == null)
			accounts = new ArrayList<Account>();

		AccountLink accountLink = operation.getAccount();
		if (accountLink != null)
		{
			accounts.add(accountLink.getAccount());
		}

		form.setAccounts(accounts);
		return form;
	}
}
