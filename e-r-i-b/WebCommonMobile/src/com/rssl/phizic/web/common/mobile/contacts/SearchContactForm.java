package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма поиска контактов в адресной книге
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class SearchContactForm extends ActionFormBase
{
	private String byPhone;
	private String byName;
	private String byAlias;
	private String bySmallAlias;

	private boolean useLike;
	private List<Contact> contacts;

	public String getByAlias()
	{
		return byAlias;
	}

	public void setByAlias(String byAlias)
	{
		this.byAlias = byAlias;
	}

	public String getByName()
	{
		return byName;
	}

	public void setByName(String byName)
	{
		this.byName = byName;
	}

	public String getByPhone()
	{
		return byPhone;
	}

	public void setByPhone(String byPhone)
	{
		this.byPhone = byPhone;
	}

	public String getBySmallAlias()
	{
		return bySmallAlias;
	}

	public void setBySmallAlias(String bySmallAlias)
	{
		this.bySmallAlias = bySmallAlias;
	}

	public List<Contact> getContacts()
	{
		return contacts;
	}

	public void setContacts(List<Contact> contacts)
	{
		this.contacts = contacts;
	}

	public boolean isUseLike()
	{
		return useLike;
	}

	public void setUseLike(boolean useLike)
	{
		this.useLike = useLike;
	}
}
