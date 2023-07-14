package com.rssl.phizic.business.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.config.ConfigFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 05.04.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57925 $
 */

public class ServiceServiceTest extends BusinessTestCaseBase
{

	public void testServiceService() throws Exception
	{
		SimpleService simpleService = new SimpleService();
		ServiceService serviceService = new ServiceService();

		Service temp = serviceService.findByKey("testCod2");
		if(temp != null)
			serviceService.remove(temp);

		Service service = new Service();
		service.setName("testNam2");
		service.setKey("testCod2");
		service.setCategory("S");

		serviceService.add(service);

		addTestServiceOperations(service);

		List<ServiceOperationDescriptor> serviceOperations;
		serviceOperations = serviceService.getServiceOperations(service);
		int servicesCount = serviceOperations.size();

		simpleService.remove(serviceOperations.get(0));

		serviceService.update(service);

		serviceOperations = serviceService.getServiceOperations(service);

		assertNotNull(serviceOperations);
		assertEquals(servicesCount - 1, serviceOperations.size());

		serviceService.remove(service);
	}

	public void testDelete() throws Exception
	{
	}

	public static Service getTestService() throws BusinessException
	{
		return getTestService("testCode");
	}

	public static Service getTestService(String key) throws BusinessException
	{
		SimpleService simpleService = new SimpleService();
		ServiceService serviceService = new ServiceService();

		Service testService = serviceService.findByKey(key);
		if (testService == null)
			testService = createTestService(key);

		List<ServiceOperationDescriptor> serviceOperations = serviceService.getServiceOperations(testService);
		simpleService.removeList(serviceOperations);

		addTestServiceOperations(testService);

		return testService;
	}

	private static Service createTestService(String code) throws BusinessException
	{
		ServiceService serviceService = new ServiceService();

		Service service = new Service();
		service.setName("testName");
		service.setKey(code);
		service.setCategory(AccessCategory.CATEGORY_ADMIN);

		serviceService.add(service);

		addTestServiceOperations(service);

		return service;
	}

	private static void addTestServiceOperations(Service service)
			throws BusinessException
	{
		SimpleService simpleService = new SimpleService();
		List<OperationDescriptor> ods = getSpreadedOperations();

		assertTrue("Для работы теста необходимы как миннимум три операции", ods.size() >= 3);

		ServiceOperationDescriptor sod1 = new ServiceOperationDescriptor();
		sod1.setOperationDescriptor(ods.get(0));
		sod1.setService(service);
		simpleService.add(sod1);

		ServiceOperationDescriptor sod2 = new ServiceOperationDescriptor();
		sod2.setOperationDescriptor(ods.get(1));
		sod2.setService(service);
		simpleService.add(sod2);

		ServiceOperationDescriptor sod3 = new ServiceOperationDescriptor();
		sod3.setOperationDescriptor(ods.get(2));
		sod3.setService(service);
		simpleService.add(sod3);
	}

	private static List<OperationDescriptor> getSpreadedOperations() throws BusinessException
	{
		DbOperationsConfig dbResourceConfig = DbOperationsConfig.get();

		return new ArrayList<OperationDescriptor>(dbResourceConfig.getSpreadedOperations());
	}
}
