package com.rssl.phizic.web.common.mobile.dictionaries;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма получения списка самых частых оплат сотовой связи.
 *
 * @ author: Gololobov
 * @ created: 03.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ShowMobilePhoneMaxCountPayListForm extends ActionFormBase
{
	private List<Pair<Boolean, Contact>> phoneMaxCountPayPairList;

	public List<Pair<Boolean, Contact>> getPhoneMaxCountPayPairList()
	{
		return phoneMaxCountPayPairList;
	}

	public void setPhoneMaxCountPayPairList(List<Pair<Boolean, Contact>> phoneMaxCountPayPairList)
	{
		this.phoneMaxCountPayPairList = phoneMaxCountPayPairList;
	}
}
