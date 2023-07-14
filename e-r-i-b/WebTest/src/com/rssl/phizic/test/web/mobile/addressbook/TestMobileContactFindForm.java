package com.rssl.phizic.test.web.mobile.addressbook;

import com.rssl.phizic.test.web.mobile.TestMobileForm;

/**
 * @author bogdanov
 * @ created 28.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileContactFindForm extends TestMobileForm
{
	private String byName;
	private String byAlias;
	private String bySmallAlias;
	private String byPhone;
	private boolean useLike;

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

	public boolean isUseLike()
	{
		return useLike;
	}

	public void setUseLike(boolean useLike)
	{
		this.useLike = useLike;
	}
}
