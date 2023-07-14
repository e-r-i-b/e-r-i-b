package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactCategory;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ‘орма редактировани€ контактов в адресной книге.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactForm extends ActionFormBase
{
	private long id;
	private String name;
	private String alias;
	private String smallalias;
	private String cardnumber;
	private ContactCategory category;
	private Boolean trusted;
	private Contact contact;

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getCardnumber()
	{
		return cardnumber;
	}

	public void setCardnumber(String cardnumber)
	{
		this.cardnumber = cardnumber;
	}

	public String getCategory()
	{
		return category.name();
	}

	public ContactCategory getCategoryEnum()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = ContactCategory.valueOf(category);
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSmallalias()
	{
		return smallalias;
	}

	public void setSmallalias(String smallalias)
	{
		this.smallalias = smallalias;
	}

	public Boolean getTrusted()
	{
		return trusted;
	}

	public void setTrusted(Boolean trusted)
	{
		this.trusted = trusted;
	}

	public Contact getContact()
	{
		return contact;
	}

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}
}
