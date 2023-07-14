package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.List;

/**
 * @author Kosyakov
 * @ created 16.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class GetBankListOperationTest extends BusinessTestCaseBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    public void testBankListOperation () throws BusinessException, DataAccessException
    {
        GetBankListOperation operation=new GetBankListOperation();

	    Query query = operation.createQuery("list");
	    query.setParameter("name","РКЦ");
	    List<Bank> list = query.executeList();


        log.info("Записей в списке банков: "+list.size());

        assertFalse("Список банков пуст", list.isEmpty());
    }

}

