package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.test.BusinessTestCaseBase;
import org.hibernate.Query;

import java.util.List;

/**
 * @author Kidyaev
 * @ created 28.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class DeleteEntityQueryBuilderTest extends BusinessTestCaseBase
{
    public void testDeleteEntityQueryBuilder() throws Exception
    {
        DeleteEntityQueryBuilder queryBuilder = new DeleteEntityQueryBuilder(CommonLogin.class);
        queryBuilder.setId((long) 0);
        List<Query>              queries      = queryBuilder.build();
        assertTrue( queries.size() > 0 );

        // �� Property ��� ������,
        // queryBuilder ������ ��������� 1 ������ ��� �������� ������ Property
        queryBuilder = new DeleteEntityQueryBuilder(Property.class);
        queryBuilder.setId((long) 0);
        queries      = queryBuilder.build();
        assertTrue(queries.size() == 1);

        // queryBuilder �� ������ ������� ��������
        queryBuilder = new DeleteEntityQueryBuilder(Property.class, false);
        queryBuilder.setId((long) 0);
        queries      = queryBuilder.build();
        assertTrue(queries.size() == 0);
    }
}
