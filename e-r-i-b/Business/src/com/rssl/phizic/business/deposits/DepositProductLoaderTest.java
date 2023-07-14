package com.rssl.phizic.business.deposits;

import junit.framework.TestCase;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Kidyaev
 * @ created 05.05.2006
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductLoaderTest extends TestCase
{
    private static final String PATH = "Business/settings/deposits/products";

    public void testLoadDepositDescriptions() throws IOException, SAXException
    {
        List<DepositProduct> depositDescriptions = getDepositDescriptions();
        assertNotNull(depositDescriptions);
    }

    public static List<DepositProduct> getDepositDescriptions()
            throws IOException, SAXException
    {
        DepositProductLoader loader = new DepositProductLoader(PATH);
        return loader.load();
    }
}
