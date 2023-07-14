package test.junit;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Barinov
 * @ created 28.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ExecutorQueryTest extends BusinessTestCaseBase
{
	public void testQuery()
	{
		ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "test.junit.ExecutorQueryTest.testQuery");
		List<Long> list = new ArrayList<Long>();
		Long param = 1L;
		for (int i = 0; i < 100; i++)
			list.add(Long.valueOf(i));
		query.setParameterList("ids", list);
		query.setParameter("val", param);
		int count=0;
		try
		{
			count = query.executeUpdate();
		}
		catch (DataAccessException e)
		{
			e.printStackTrace();
		}
		System.out.println(count);
	}
}
