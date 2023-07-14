package com.rssl.phizic.web.atm.ext.sbrf.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.web.atm.ShowAccountsAction;
import com.rssl.phizic.web.atm.ShowAccountsForm;
import org.apache.struts.action.*;

import java.util.List;
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

	    if (checkAccess(GetAccountAbstractOperation.class) && checkAccess(GetAccountsOperation.class))
	        frm.setAccounts(getPersonAccountLinks(frm));

        frm.setUser(person);

	    if(frm.isAllAccountDown())
	    {
		    ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по вкладам из АБС временно недоступна. Повторите операцию позже.", false));
		    saveMessages(request, msgs);
	    }

	    return mapping.findForward(ShowAccountsAction.FORWARD_SHOW);
    }

	protected List<AccountLink> getPersonAccountLinks(ShowAccountsForm form)  throws BusinessException, BusinessLogicException
	{
		GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
		List<AccountLink> personAccountLinks = operationAccounts.getActiveAccounts();
		form.setAllAccountDown(personAccountLinks.size()==0 && operationAccounts.isBackError());
		return personAccountLinks;

	}

}