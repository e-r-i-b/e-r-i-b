package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.auth.passwordcards.PasswordCard;

import java.util.List;

/**
 * @author Roshka
 * @ created 23.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class GetPasswordCardsOperationTest extends BusinessTestCaseBase
{
    public void testGetPasswordCardsOperationTest() throws BusinessException, DataAccessException
    {
        GetPasswordCardsOperation operation = new GetPasswordCardsOperation();

        Query query = operation.createQuery("list");
        query.setParameter("number", "");
        query.setParameter("fromDate", null);
        query.setParameter("toDate", null);
        query.setParameter("passwordsCount", "");

        List<PasswordCard> reservedCards = query.executeList();
        assertNotNull(reservedCards);
    }
}
