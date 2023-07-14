package com.rssl.phizic.test;

import com.rssl.phizic.business.BusinessException;
import junit.framework.TestCase;

import java.io.InvalidClassException;

/**
 * @author Evgrafov
 * @ created 24.11.2005
 * @ $Author$
 * @ $Revision$
 */

public class OperarionsLoaderTest extends TestCase
{
    public void manualTestOperarionsLoader() throws Exception
    {
        new OperationsLoader().initAndLoad();
    }

    /* just for green JUnit ;) */
    public void testStub(){}
}
