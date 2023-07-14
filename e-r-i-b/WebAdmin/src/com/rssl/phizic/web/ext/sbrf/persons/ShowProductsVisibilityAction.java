package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.operations.person.GetResourcesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.ext.sbrf.products.ProductsSetupForm;
import com.rssl.phizic.web.persons.PersonUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 14.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowProductsVisibilityAction extends OperationalActionBase
{
	protected static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductsSetupForm frm = (ProductsSetupForm)form;

		GetResourcesOperation operation = createOperation(GetResourcesOperation.class);
		Long personId = PersonUtils.getPersonId(currentRequest());
		operation.setPersonId(personId);

		if (operation.getPerson() == null)
		{
			ActionMessage message = new ActionMessage("Не найден клиент с id =" + personId, false);
		    ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_START);
		}
		if (!CreationType.UDBO.equals(operation.getPerson().getCreationType()))
		{
			ActionMessage message = new ActionMessage("Для данного типа клиента настройка недоступна", false);
		    ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_START);
		}

		frm.setAccounts(operation.getAccounts());
		frm.setCards(operation.getCards());
		frm.setLoans(operation.getLoans());
		frm.setDepoAccounts(operation.getDepoAccounts());
		frm.setImAccounts(operation.getIMAccounts());
		frm.setPfrLink(operation.getPfrLink());
		frm.setSecurityAccounts(operation.getSecurityAccounts());
		frm.setSNILS(operation.getSNILS());
		frm.setActivePerson(operation.getPerson());

		ArrayList<String> selectedIds = new ArrayList<String>();
		for (AccountLink accountLink : frm.getAccounts())
		{
			if (accountLink.getShowInATM())
				selectedIds.add(accountLink.getId().toString());
		}
		frm.setSelectedAccountESIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (AccountLink accountLink : frm.getAccounts())
		{
			if (accountLink.getShowInMobile())
				selectedIds.add(accountLink.getId().toString());
		}
		frm.setSelectedAccountMobileIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (CardLink cardLink : frm.getCards())
		{
			if (cardLink.getShowInMobile())
				selectedIds.add(cardLink.getId().toString());
		}
		frm.setSelectedCardMobileIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (LoanLink loanLink : frm.getLoans())
		{
			if (loanLink.getShowInMobile())
				selectedIds.add(loanLink.getId().toString());
		}
		frm.setSelectedLoanMobileIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (IMAccountLink imAccountLink : frm.getImAccounts())
		{
			if (imAccountLink.getShowInMobile())
				selectedIds.add(imAccountLink.getId().toString());
		}
		frm.setSelectedIMAccountMobileIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (AccountLink accountLink : frm.getAccounts())
		{
			if (accountLink.getShowInSocial())
				selectedIds.add(accountLink.getId().toString());
		}
		frm.setSelectedAccountSocialIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (CardLink cardLink : frm.getCards())
		{
			if (cardLink.getShowInSocial())
				selectedIds.add(cardLink.getId().toString());
		}
		frm.setSelectedCardSocialIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (LoanLink loanLink : frm.getLoans())
		{
			if (loanLink.getShowInSocial())
				selectedIds.add(loanLink.getId().toString());
		}
		frm.setSelectedLoanSocialIds(selectedIds.toArray(new String[selectedIds.size()]));

		selectedIds.clear();
		for (IMAccountLink imAccountLink : frm.getImAccounts())
		{
			if (imAccountLink.getShowInSocial())
				selectedIds.add(imAccountLink.getId().toString());
		}
		frm.setSelectedIMAccountSocialIds(selectedIds.toArray(new String[selectedIds.size()]));

		frm.setSelectedAccountIds(getSelectedIds(frm.getAccounts()));
		frm.setSelectedCardIds(getSelectedIds(frm.getCards()));
		frm.setSelectedLoanIds(getSelectedIds(frm.getLoans()));
		frm.setSelectedDepoAccountIds(getSelectedIds(frm.getDepoAccounts()));
		frm.setSelectedIMAccountIds(getSelectedIds(frm.getImAccounts()));
		frm.setSelectedSecurityAccountIds(getSelectedIds(frm.getSecurityAccounts()));
		if (frm.getPfrLink() != null)
			frm.setPfrLinkSelected(frm.getPfrLink().getShowInSystem());

		return mapping.findForward(FORWARD_START); 
	}

	private String[] getSelectedIds(List<? extends EditableExternalResourceLink> links)
	{
		ArrayList<String> selectedIds = new ArrayList<String>();
		for (EditableExternalResourceLink link : links)
		{
			if (link.getShowInSystem())
				selectedIds.add(link.getId().toString());
		}
		return selectedIds.toArray(new String[selectedIds.size()]);
	}
}
