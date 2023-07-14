package com.rssl.phizic.test.web.mobile.addressbook;

import com.rssl.phizic.test.web.mobile.TestMobileForm;

/**
 * @author bogdanov
 * @ created 28.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileContactGetForm extends TestMobileForm
{
	private String phones;
	private boolean showBookmark;

	public String getPhones()
	{
		return phones;
	}

	public void setPhones(String phones)
	{
		this.phones = phones;
	}

	public boolean isShowBookmark()
	{
		return showBookmark;
	}

	public void setShowBookmark(boolean showBookmark)
	{
		this.showBookmark = showBookmark;
	}
}
