package com.rssl.phizic.business.deposits;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;

import java.util.List;
import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Kidyaev
 * @ created 05.05.2006
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductSynchronizerTest extends BusinessTestCaseBase
{
    public void manualTestUpdateDepositDescriptors() throws IOException, SAXException, BusinessException,
		                                                    DublicateDepositProductNameException
    {
        List<DepositProduct>      descriptions = DepositProductLoaderTest.getDepositDescriptions();
        DepositProductSynchronizer synchronizer = new DepositProductSynchronizer(descriptions);

        synchronizer.update();
    }

    public void test()
    {
    }
}
