package com.rssl.phizic.business.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"JavaDoc"})
public class OperationDescriptorServiceTest extends BusinessTestCaseBase
{
	private static OperationDescriptorService operationDescriptorService = new OperationDescriptorService();
	public void testOperationDesc() throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();

				String prefix = String.valueOf(System.currentTimeMillis()) + "_";
				for (int i = 0; i < 2; i++)
				{
					OperationDescriptor operationDescriptor = new OperationDescriptor();
					operationDescriptor.setKey(prefix + i);
					operationDescriptor.setOperationClassName("OperationClassName_" + i);
					operationDescriptor.setName("OperationName_" + i);
					operations.add(operationDescriptor);

					operationDescriptorService.add(operationDescriptor);
				}

				for (OperationDescriptor descriptor : operations)
				{
					operationDescriptorService.remove(descriptor);
				}

				return null;
			}
		});
	}

	public void testGetListQueries() throws BusinessException
	{
		List<OperationDescriptor> operations = operationDescriptorService.getAll();
		assertNotNull(operations);
	}
}
