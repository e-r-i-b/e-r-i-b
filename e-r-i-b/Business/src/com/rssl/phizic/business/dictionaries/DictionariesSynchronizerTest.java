package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateSingleton;

/**
 * @author Evgrafov
 * @ created 17.08.2007
 * @ $Author: egorova $
 * @ $Revision: 12382 $
 */

public class DictionariesSynchronizerTest extends BusinessTestCaseBase
{
    public void test(){}

    public void manualTest() throws BusinessException, BusinessLogicException
    {
        initializeRsV51Gate();
        initializeRSBankV50Gate();
        new DictionariesSynchronizer(GateSingleton.get().getFactory()).synchronizeAll();
    }
}