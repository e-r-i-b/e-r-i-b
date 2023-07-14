package com.rssl.phizic.utils.xml;

import junit.framework.TestCase;

import javax.xml.transform.TransformerException;
import javax.xml.transform.Source;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1564 $
 */
public class StaticResolverTest extends TestCase
{
	public void test() throws TransformerException
	{
		StaticResolver resolver = new StaticResolver();

		Source source = resolver.resolve("static://include/functions/set/set.distinct.function.xsl", null);
		assertNotNull(source);
	}
}