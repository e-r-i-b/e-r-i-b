package com.rssl.phizic.business.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 19.10.2005 Time: 14:29:25 */
public class OperationDescriptorService
{
    private static MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();

    public OperationDescriptor add(OperationDescriptor operationDescriptor) throws BusinessException
    {
        return simpleService.add(operationDescriptor, null);
    }

    public void remove(final OperationDescriptor od, final boolean ignoreUsages) throws BusinessException
    {
	    try
	    {
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (ignoreUsages)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.operations.OperationDescriptorService.deleteUsages");
						query.setParameter("operationDescriptor", od);
						query.executeUpdate();
					}

					session.delete(od);
					return null;
				}
			});
	    }
	    catch (Exception e)
	    {
		    throw new BusinessException("Ошибка при удалении операции " + od, e);
	    }
    }

	public void remove(final OperationDescriptor od) throws BusinessException
	{
		remove(od, false);
	}
	/**
	 * Обновить описание операции
	 * @param operationDescriptor
	 * @throws BusinessException
	 */
	public void update(OperationDescriptor operationDescriptor) throws BusinessException
	{
		simpleService.addOrUpdate(operationDescriptor, null);
	}

	public List<OperationDescriptor> getAll() throws BusinessException
    {
        return simpleService.getAll(OperationDescriptor.class, null);
    }

    public OperationDescriptor findById(Long operationId) throws BusinessException
    {
        return simpleService.findById(OperationDescriptor.class, operationId, null);
    }

	public void replicateAll(String toInstance) throws BusinessException
	{
		List<OperationDescriptor> list = getAll();
		for (OperationDescriptor descriptor : list)
		{
			simpleService.replicate(descriptor, toInstance);
		}
	}
}
