package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadCatchBlock"})
public class CellTypeListOperation extends OperationBase implements ListEntitiesOperation
{

	public List<Office> getOfficeList(final Long cellTypeId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Office>>()
			{
			    public List<Office> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.bankcells.OfficeListByCellType");
				    query.setParameter("cellTypeId", cellTypeId);
				    //noinspection unchecked
				    return query.list();
			    }
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
