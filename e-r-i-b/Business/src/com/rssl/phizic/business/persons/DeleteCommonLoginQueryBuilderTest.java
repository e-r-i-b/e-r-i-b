package com.rssl.phizic.business.persons;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.config.Property;
import org.hibernate.Query;

import java.util.List;

/**
 * @author Kidyaev
 * @ created 28.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class DeleteCommonLoginQueryBuilderTest extends BusinessTestCaseBase
{
    public void testDeleteCommonLoginQueryBuilder() throws Exception
    {
        DeleteCommonLoginQueryBuilder queryBuilder = new DeleteCommonLoginQueryBuilder();
        queryBuilder.setLoginId((long) 0);
        List<Query>                   queries      = queryBuilder.build();
        assertTrue( queries.size() > 0 );
    }
}
