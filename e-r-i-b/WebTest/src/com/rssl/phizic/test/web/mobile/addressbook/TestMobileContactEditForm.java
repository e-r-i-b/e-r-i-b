package com.rssl.phizic.test.web.mobile.addressbook;

import com.rssl.phizic.test.web.mobile.TestMobileForm;

/**
 * @author bogdanov
 * @ created 28.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileContactEditForm extends TestMobileForm
{
	private long id;
	private String name;
	private String alias;
	private String smallalias;
	private String cardnubmer;
	private String category;
	private boolean trusted;

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getCardnubmer()
	{
		return cardnubmer;
	}

	public void setCardnubmer(String cardnubmer)
	{
		this.cardnubmer = cardnubmer;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
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

	public boolean isTrusted()
	{
		return trusted;
	}

	public void setTrusted(boolean trusted)
	{
		this.trusted = trusted;
	}
}
