package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.web.client.abstr.ShowAccountAbstractAction;
import com.rssl.phizic.web.client.abstr.ShowAccountAbstractForm;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountAbstractExtendedAction extends ShowAccountAbstractAction
{
	public static final String FORWARD_ABSTRACT = "ShowAbstract";
	public static final String FORWARD_INFORMATION = "ShowInformation";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ShowAccountAbstractForm frm = (ShowAccountAbstractForm) form;

		//заполняем форму
 		Calendar currDate = DateHelper.getCurrentDate();
		Calendar startDate = DateHelper.getCurrentDate();

		if(request.getParameter(DAY_PERIOD)==null)
			startDate.set(Calendar.DAY_OF_MONTH,1);

		frm.setFromDate(startDate);
		frm.setToDate(currDate);

		//заполняем список счетов и карт для фильтра
		String list = request.getParameter("list");
		if (list != null)
		{
			String[] accounts = new String[0];
			String[] cards = new String[0];
			String[] deposits = new String[0];
			if ("selected".equals(list))
			{
				if(frm.getAccountIds() != null)
					accounts = frm.getAccountIds();
				if(frm.getCardIds() != null)
					cards = frm.getCardIds();
				if(frm.getDepositIds() != null)
					deposits = frm.getDepositIds();
			}else{
				if ("cards".equals(list) || "all".equals(list))
				{
					PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
					cards = getExternalResourceIdArray(personData.getCards());
				}
				if ("accounts".equals(list) || "all".equals(list))
				{
					GetAccountsOperation operation = createOperation(GetAccountsOperation.class);
					accounts = getExternalResourceIdArray(operation.getActiveAccounts());
				}
				if ("deposits".equals(list) || "all".equals(list))
				{
					PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
					deposits = getExternalResourceIdArray(personData.getDeposits());
				}
			}
			String[] selected = new String[accounts.length + cards.length+deposits.length];
			int i = 0;
			for (String id : accounts)
			{
				selected[i++] = "a:" + id;
			}
			for (String id : cards)
			{
				selected[i++] = "c:" + id;
			}
			for (String id : deposits)
			{
				selected[i++] = "d:" + id;
			}
			frm.setSelectedResources(selected);
		}

		return doFilter(frm, mapping);
	}

	protected void setUserInfo(ShowAccountAbstractForm frm) throws Exception
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		GetAccountsOperation operation = createOperation(GetAccountsOperation.class);
		ActivePerson user = personData.getPerson();
		frm.setUser(user);
		frm.setAccountLinks(operation.getActiveAccounts());
	}

	protected GetAccountAbstractOperation createGetAccountAbstractOperation(Long accountId, ShowAccountAbstractForm frm) throws BusinessException, BusinessLogicException
	{
		/**
		 * в зависимости от параметра формы сopying, выводится либо выписка(copying=false), либо справка о состоянии вклада.
		 */
		ShowAccountAbstractExtendedForm form = (ShowAccountAbstractExtendedForm)frm;
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

	protected void buildAbstract(ShowAccountAbstractForm frm) throws Exception
	{
		Calendar fromDate = frm.getFromDate();
		Calendar toDate   = frm.getToDate();

		Map<AccountLink, AccountAbstract> accountAbstract = new HashMap<AccountLink, AccountAbstract>();
		Map<CardLink, CardAbstract>       cardAbstract    = new HashMap<CardLink, CardAbstract>();

		String[] selectedResources = frm.getSelectedResources();
		for (String res : selectedResources)
		{
			String sid = res.substring(2);

			if(res.startsWith("no")) return;

			Long id = new Long(sid);
			if (res.startsWith("a:") && (super.checkAccess(GetAccountAbstractOperation.class) || super.checkAccess(GetAccountAbstractExtendedOperation.class)))
			{
				GetAccountAbstractOperation op = createGetAccountAbstractOperation(id,frm);
				op.setDateFrom(fromDate);
				op.setDateTo(toDate);
				accountAbstract.put(op.getAccount(), op.getAccountAbstract());
				frm = fillAccountSpecificData(frm, op);

			}
			if (res.startsWith("c:") && (super.checkAccess(GetCardAbstractOperation.class)))
			{
				GetCardAbstractOperation op = createOperation(GetCardAbstractOperation.class);
				op.initialize(id);
				op.setDateFrom(fromDate);
				op.setDateTo(toDate);

				cardAbstract.put(op.getCard(), op.getCardAbstract());

			}
		}
		addLogParameters(new SimpleLogParametersReader("Начальная дата", frm.getFromDateString()));
		addLogParameters(new SimpleLogParametersReader("Конечная дата", frm.getToDateString()));
		addLogParameters(new CollectionLogParametersReader("Список карт", cardAbstract.keySet()));
		addLogParameters(new CollectionLogParametersReader("Список счетов", accountAbstract.keySet()));

		frm.setAccountAbstract(accountAbstract);
		frm.setCardAbstract(cardAbstract);

		frm = fillSpecificData(frm);
	}

	protected ActionForward findForward(ShowAccountAbstractForm frm, ActionMapping mapping)
	{
		ShowAccountAbstractExtendedForm form = (ShowAccountAbstractExtendedForm)frm;
		if(!form.getCopying())
		{
			return mapping.findForward(FORWARD_ABSTRACT);
		}
		else
		{
			return mapping.findForward(FORWARD_INFORMATION);
		}
	}

	protected ShowAccountAbstractForm fillAccountSpecificData(ShowAccountAbstractForm frm, GetAccountAbstractOperation operation) throws Exception
	{
		ShowAccountAbstractExtendedForm form = (ShowAccountAbstractExtendedForm) frm;
		List<Account> accounts = form.getAccounts();
		if(accounts == null)
		   accounts = new ArrayList<Account>();

		AccountLink accountLink = operation.getAccount();
		accounts.add(accountLink.getAccount());

		form.setAccounts(accounts);
		if(form.getCopying())
		{
			GetAccountAbstractExtendedOperation oper = (GetAccountAbstractExtendedOperation)operation;

			Map<Account, DateSpan> periods = form.getPeriodToClose();
			if(periods==null)
				periods = new HashMap<Account, DateSpan>();

			periods.put(accountLink.getAccount(), oper.getPeriod());
			form.setPeriodToClose(periods);
		}

		return form;
	}
}
