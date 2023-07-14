package com.rssl.phizic.business.web;

import junit.framework.TestCase;

/**
 * @author Erkin
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class WidgetSerializerTest extends TestCase
{
	private final WidgetSerializer serializer = new WidgetSerializer();

	@SuppressWarnings({"ReuseOfLocalVariable"})
	public void testLayout()
	{
		String beginLayoutString = "[[\"AccountBlock1\"], \"CardBlock1\", \"LoanBlock1\"]";
		String endLayoutString = "[[\"AccountBlock-7-100\"], \"LoanBlock1\"]";

		Layout beginLayout = serializer.deserializeLayout(beginLayoutString);
		Layout endLayout = serializer.deserializeLayout(endLayoutString);

		Layout layout = beginLayout;
		layout = layout.removeWidget("CardBlock1");
		layout = layout.renameWidget("AccountBlock1", "AccountBlock-7-100");

		assertEquals(layout, endLayout);
	}
}
