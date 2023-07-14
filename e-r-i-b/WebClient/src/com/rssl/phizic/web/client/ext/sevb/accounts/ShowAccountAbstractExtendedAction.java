package com.rssl.phizic.web.client.ext.sevb.accounts;

import com.rssl.phizic.web.client.abstr.ShowAccountAbstractAction;
import com.rssl.phizic.web.client.abstr.ShowAccountAbstractForm;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.ext.sevb.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.*;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;

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
		ShowAccountAbstractExtendedForm form = (ShowAccountAbstractExtendedForm)frm;
		
		Calendar fromDate = frm.getFromDate();
		Calendar toDate = frm.getToDate();

		Map<AccountLink, AccountAbstract> accountAbstract = new HashMap<AccountLink, AccountAbstract>();
		Map<CardLink, CardAbstract> cardAbstract = new HashMap<CardLink, CardAbstract>();

		String[] selectedResources = frm.getSelectedResources();
		for (String res : selectedResources)
		{
			String sid = res.substring(2);

			if(res.startsWith("no")) return;

			Long id = new Long(sid);
			if (res.startsWith("a:") && (super.checkAccess(GetAccountAbstractOperation.class) || super.checkAccess(GetAccountAbstractExtendedOperation.class)))
			{
				String accountNumber = getAccountById(frm.getAccountLinks(), id.toString()).getNumber();
				if ((accountNumber.startsWith("40817") || accountNumber.startsWith("40820")) && form.getCopying()){
					ActionMessages msgs = new ActionMessages();
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Справка о состоянии по текущим счетам не выдается", false));
					saveMessages(WebContext.getCurrentRequest(), msgs);
		        }else{
					GetAccountAbstractOperation op = createGetAccountAbstractOperation(id,frm);
					op.setDateFrom(fromDate);
					op.setDateTo(toDate);
					accountAbstract.put(op.getAccount(), op.getAccountAbstract());
					frm = fillAccountSpecificData(frm, op);

					addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", op.getAccount()));
				}
			}
			if ( res.startsWith("c:") && super.checkAccess(GetCardAbstractOperation.class) )
			{
				GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
				operation.initialize(id);
//не используем				
//				cardAbstract.put(operation.getCard(),operation.getCardAbstract(10l));

				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getCard()));
			}
		}

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
		return form;
	}

	public AccountLink getAccountById(List<AccountLink> accounts, String id){
		for (AccountLink acc: accounts){
			if (acc.getId().toString().equals(id)){
				return acc;
			}
		}
		return null;
	}
}
