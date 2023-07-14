package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ext.sbrf.payment.MoneyBoxListOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 * Список копилок клиента.
 */
public class ListMoneyBoxAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MoneyBoxListOperation operation = createOperation("MoneyBoxListOperation", "MoneyBoxManagement");
		ListMoneyBoxForm frm = (ListMoneyBoxForm) form;

		//если фильтрация по карте И вкладу
		if (frm.getCardId() != null && frm.getAccountId() != null)
		{
			CardLink cardLink = PersonContext.getPersonDataProvider().getPersonData().getCard(frm.getCardId());
			AccountLink accountLink = PersonContext.getPersonDataProvider().getPersonData().getAccount(frm.getAccountId());
			operation.initialize(cardLink, accountLink);
		}
		//если фильтрация по карте
		else if (frm.getCardId() != null)
		{
			CardLink cardLink = PersonContext.getPersonDataProvider().getPersonData().getCard(frm.getCardId());
			operation.initialize(cardLink, true);
		}
		//если фильтрация по вкладу
		else if (frm.getAccountId() != null)
		{
			AccountLink accountLink = PersonContext.getPersonDataProvider().getPersonData().getAccount(frm.getAccountId());
			operation.initialize(accountLink);
		}
		//если нет условий фильрации.
		else
		{
			operation.initialize();
		}
		frm.setData(operation.getData());
		return mapping.findForward(FORWARD_START);
	}
}
