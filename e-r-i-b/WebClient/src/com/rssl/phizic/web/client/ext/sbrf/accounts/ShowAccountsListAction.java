package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollHelper;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.finances.targets.GetTargetOperation;
import com.rssl.phizic.web.client.ShowAccountsForm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 22.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountsListAction extends ShowAccountsExtendedAction
{

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
	    ShowAccountsForm frm = (ShowAccountsForm) form;
	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson person = personData.getPerson();

	    try
	    {
	        setAccountAbstract(frm);
		    if (frm.getActiveAccounts().isEmpty() && frm.getClosedAccounts().isEmpty())
		    {
			    ExternalSystemHelper.check(person.asClient().getOffice(), BankProductType.Deposit);
		    }
	    }
	    catch (InactiveExternalSystemException e)
	    {
		    saveInactiveESMessage(request, e);
	    }

        frm.setUser(person);
        frm.setPromoDivMaxLength(ConfigFactory.getConfig(PromoCodesDepositConfig.class).getMaxCountSymbols());

	    if(frm.isAllAccountDown())
	    {
		    ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по вкладам из АБС временно недоступна. Повторите операцию позже.", false));
		    saveMessages(request, msgs);
		    return mapping.findForward(FORWARD_SHOW);
	    }

	    List<AccountLink> closedAccounts = frm.getClosedAccounts();
	    if (CollectionUtils.isNotEmpty(closedAccounts))
	    {
		    List<String> accountNames = new ArrayList<String>();
		    GetAccountsOperation getAccountsOperation = createOperation(GetAccountsOperation.class);
		    for(AccountLink closedAccount: closedAccounts)
		    {
			    if (closedAccount.getClosedState() != null && closedAccount.getClosedState())
			    {
				    accountNames.add(closedAccount.getName());
				    getAccountsOperation.setAccountLinkFalseClosedState(closedAccount.getNumber());
			    }
		    }
		    if (CollectionUtils.isNotEmpty(accountNames))
		    {
			    BankrollHelper bankrollHelper = new BankrollHelper(GateSingleton.getFactory());
                Map<String, String> message = bankrollHelper.createBlockedLinkMessage(accountNames);

                ActionMessages msgs = new ActionMessages();
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("depositsBundle", message.get("captionKey")), false));
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message.get("bodyText") + getResourceMessage("depositsBundle", message.get("bodyKey")), false));
                saveMessages(request, msgs);
		    }
	    }

	    return mapping.findForward(FORWARD_SHOW);
    }

	protected List<AccountLink> getPersonAccountLinks(GetAccountsOperation operationAccounts)
	{
		return operationAccounts.getAllActiveAndClosedAccounts();
	}

	protected List<AccountTarget> getAccountTargetsLinks(GetTargetOperation operation) throws BusinessException
	{
		return operation.getTargetsWithAccounts();
	}
}
