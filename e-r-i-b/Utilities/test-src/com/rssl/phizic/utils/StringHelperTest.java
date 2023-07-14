package com.rssl.phizic.utils;

import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Puzikov
 * @ created 01.12.14
 * @ $Author$
 * @ $Revision$
 */

public class StringHelperTest extends TestCase
{
	public void testGetPostfixes() throws Exception
	{
		assertEquals(StringHelper.getPostfixes("PREFIX qwe", " .-()", 1), Arrays.asList("qwe"));
		assertEquals(StringHelper.getPostfixes(" qwe  ", " ", 1), Arrays.asList("qwe  "));
		assertEquals(StringHelper.getPostfixes("PREFIX qwe rty", " ", 1), Arrays.asList("rty", "qwe rty"));
		assertEquals(StringHelper.getPostfixes("PREFIX  qwe rty", " ", 1), Arrays.asList("rty", "qwe rty"));
		assertEquals(StringHelper.getPostfixes("PREFIX str1 str2.str3", " .", 1), Arrays.asList("str1 str2.str3", "str2.str3", "str3"));
		assertEquals(StringHelper.getPostfixes("    ", " ", 1), Arrays.<String>asList());
		assertEquals(StringHelper.getPostfixes("PREFIX qwe     rty  ", " ", 1), Arrays.asList("qwe     rty  ", "rty  "));
		assertEquals(StringHelper.getPostfixes("имени К.Маркса-Пушкина", " .,-", 1), Arrays.asList("Пушкина", "Маркса-Пушкина", "К.Маркса-Пушкина"));
		assertEquals(StringHelper.getPostfixes("Большая Полянка", " .,-", 1), Arrays.asList("Полянка"));
		assertEquals(StringHelper.getPostfixes("3", " .,-", 1), Arrays.<String>asList());
		assertEquals(StringHelper.getPostfixes("Полянка (Ганусовское с/п)", " .,-()", 1), Arrays.asList("Ганусовское с/п)", "с/п)"));
	}

	public void testGetPostfixesWithLength()
	{
		assertEquals(StringHelper.getPostfixes("PREFIX", " .,-", 3), Arrays.<String>asList());
		assertEquals(StringHelper.getPostfixes("PREFIX ", " .,-", 3), Arrays.<String>asList());
		assertEquals(StringHelper.getPostfixes("PREFIX 1", " .,-", 3), Arrays.<String>asList());
		assertEquals(StringHelper.getPostfixes("PREFIX 12", " .,-", 3), Arrays.<String>asList());
		assertEquals(StringHelper.getPostfixes("PREFIX 123", " .,-", 3), Arrays.asList("123"));
	}

	private <T> void assertEquals(List<T> first, List<T> second)
	{
		boolean assertion = first.containsAll(second) && second.containsAll(first);
		if (!assertion)
		{
			System.out.println("actual >" + StringUtils.join(first, ',') + '<');
			System.out.println("expctd >" + StringUtils.join(second, ',') + '<');
		}
		assertTrue(assertion);
	}
}
