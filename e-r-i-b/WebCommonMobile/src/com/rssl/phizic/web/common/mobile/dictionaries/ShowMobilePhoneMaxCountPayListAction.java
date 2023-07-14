package com.rssl.phizic.web.common.mobile.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.userprofile.addressbook.ViewAddressBookOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен получени€ списка самых частых оплат сотовой св€зи
 *
 * @ author: Gololobov
 * @ created: 03.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ShowMobilePhoneMaxCountPayListAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("ќшибка при получении списка самых частых оплат сотовой св€зи.");
		ViewAddressBookOperation operation = createOperation(ViewAddressBookOperation.class, "AddressBook");

		List<Contact> phoneMaxCountPayList = operation.getClientAddressBookByPhoneMaxCountPay(PersonContext.getPersonDataProvider().getPersonData().getLogin());
		List<Pair<Boolean, Contact>> phoneMaxCountPayWithSelfPhonesList = new ArrayList<Pair<Boolean, Contact>>();
		if (CollectionUtils.isNotEmpty(phoneMaxCountPayList))
		{
			for (Contact contact : phoneMaxCountPayList)
				phoneMaxCountPayWithSelfPhonesList.add(new Pair<Boolean, Contact> (Boolean.valueOf(PersonHelper.isSelfPhone(contact.getPhone())), contact));
		}
		ShowMobilePhoneMaxCountPayListForm frm = (ShowMobilePhoneMaxCountPayListForm) form;
		frm.setPhoneMaxCountPayPairList(phoneMaxCountPayWithSelfPhonesList);

		return mapping.findForward(FORWARD_START);
	}
}
