package com.rssl.phizic.utils.xml;

import junit.framework.TestCase;

import javax.xml.transform.Source;


/**
 * @author Roshka
 * @ created 30.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class ClassPathURIResolverTest extends TestCase
{
    public void testClassPathURIResolver() throws Exception
    {
        ClassPathURIResolver resolver = new ClassPathURIResolver("com/rssl/phizic/business/payments/forms/resources/");
        Source source = resolver.resolve("controls.xslt", "");
        assertNotNull(source);
        assertNull(resolver.resolve("foobarcontrols.xslt", ""));
    }
}
