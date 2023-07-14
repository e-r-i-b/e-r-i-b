package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;


/**
 * @author akrenev
 * @ created 14.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ProvidersTest extends BusinessTestCaseBase
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final ServiceProviderRegionService serviceProviderRegionService = new ServiceProviderRegionService();
	private static final SimpleService simpleService = new SimpleService();

	public void testDublicateServiceProviderException(ServiceProviderBase serviceProvider) throws BusinessException
	{
		try
		{
			serviceProviderService.addOrUpdate(serviceProvider);
		}
		catch (DublicateServiceProviderException e)
		{
			return;
		}
		fail();
	}

	public void testServiceProviderService() throws Exception
	{
		BillingServiceProvider serviceProvider = new BillingServiceProvider();
		serviceProvider.setSynchKey("213213");
		serviceProvider.setCode("SERVICE_PROVIDER1");
		serviceProvider.setName("SERVICE_PROVIDER1");
		serviceProvider.setINN("1234567890");
		serviceProvider.setAccount("56456810023423432423");
		serviceProvider.setBIC("044585672");
		serviceProvider.setBankName("¿¡ \"¡œ‘\" («¿Œ)");
		serviceProvider.setCorrAccount("30101810400000000672");
		serviceProvider.setDeptAvailable(true);
		serviceProvider.setTransitAccount("213124214124");
		serviceProvider.setAttrDelimiter("|");
		serviceProvider.setAttrValuesDelimiter("@");
		serviceProvider.setState(ServiceProviderState.ACTIVE);

		List<Billing> billingList = simpleService.getAll(Billing.class);
		assertFalse(billingList.isEmpty());
		serviceProvider.setBilling(billingList.get(0));

		List<PaymentService> paymentServiceList = simpleService.getAll(PaymentService.class);
		assertFalse(paymentServiceList.isEmpty());

		List<Department> departmentList = simpleService.getAll(Department.class);
		assertFalse(departmentList.isEmpty());
		serviceProvider.setDepartmentId(departmentList.get(0).getId());

		serviceProviderService.addOrUpdate(serviceProvider);

		List<BillingServiceProvider> list = simpleService.getAll(BillingServiceProvider.class);
		assertNotNull(list);
		Long id = serviceProvider.getId();
		serviceProvider.setId(null);
 	    testDublicateServiceProviderException(serviceProvider);
		serviceProvider.setId(id);
		serviceProviderService.remove(serviceProvider);
		assertTrue(simpleService.getAll(BillingServiceProvider.class).isEmpty());
	}

	public void testServiceProviderRegionService() throws Exception
	{
		List<ServiceProviderBase> serviceProviderList = simpleService.getAll(ServiceProviderBase.class);
		assertFalse(serviceProviderList.isEmpty());
		ServiceProviderBase serviceProvider = serviceProviderList.get(0);

		List<Region> regionList = simpleService.getAll(Region.class);
		assertFalse(regionList.isEmpty());
		Region region = regionList.get(0);
		ServiceProviderRegion serviceProviderRegion = new ServiceProviderRegion(serviceProvider, region);
		serviceProviderRegionService.addOrUpdate(serviceProviderRegion);
		assertNotNull(simpleService.getAll(ServiceProviderRegion.class));
		serviceProviderRegionService.remove(serviceProviderRegion);
		assertTrue(simpleService.getAll(ServiceProviderRegion.class).isEmpty());
	}
}
