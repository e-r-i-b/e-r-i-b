package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.business.services.ServiceServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Evgrafov Date: 30.09.2005 Time: 21:13:00
 */
public class AccessSchemeTest extends BusinessTestCaseBase
{
	public void testCreateCheckPermissionAndRemoveScheme () throws Exception
	{
		AccessSchemeService service = new AccessSchemeService();

		AccessScheme accessScheme = getTestScheme();

		AccessScheme scheme = service.findById(accessScheme.getId());
		assertTrue(scheme.getId().equals(accessScheme.getId()));
		assertNotNull(scheme.getServices());

		scheme = service.findByKey("testCode");
		assertNotNull(scheme);

		//TODO: что-то не согласуетс€ с именем
	}

	public void testCreateAndUpdate() throws BusinessException, BusinessLogicException
	{
		AccessSchemeService schemeService = new AccessSchemeService();
		ServiceService serviceService = new ServiceService();
		Service service1 = ServiceServiceTest.getTestService("test1");
		Service service2 = ServiceServiceTest.getTestService("test2");
		SharedAccessScheme scheme = new SharedAccessScheme();

		scheme.setKey("AccessSchemeTest1");
		scheme.setName("AccessSchemeTest1");
		scheme.setCategory("C");
		scheme.getServices().add(service1);
		scheme.getServices().add(service2);

		schemeService.save(scheme);

		schemeService.save(scheme);

		scheme.getServices().clear();
		scheme.getServices().add(service2);
		scheme.getServices().add(service1);
		schemeService.save(scheme);


		scheme.getServices().remove(service1);
		schemeService.save(scheme);

		serviceService.remove(service1, true); //удал€ем со св€з€ми

		schemeService.remove(scheme);

		serviceService.remove(service2);
	}

	public void testGetAll () throws BusinessException
	{
		AccessSchemeService service = new AccessSchemeService();
		final List<SharedAccessScheme> all = service.getAll();

		assertNotNull(all);

		if (all.size()>0)
		{
			AccessScheme scheme = all.get(0);
			List<Service> services = scheme.getServices();
			assertNotNull(services);
		}
	}

	public void testGetCategory () throws BusinessException
	{
		AccessSchemeService service = new AccessSchemeService();

		List<SharedAccessScheme> schemes1 = service.findByCategory(AccessCategory.CATEGORY_ADMIN);
		assertNotNull(schemes1);

		List<SharedAccessScheme> schemes2 = service.findByCategory(AccessCategory.CATEGORY_CLIENT);
		assertNotNull(schemes2);

	}

	/**
	 * “естирует удаление схемы
	 * @throws Exception
	 */
	public void testDelete () throws Exception
	{
		AccessSchemeService schemeService = new AccessSchemeService();
		PersonService personService = new PersonService();

		AccessScheme testScheme = getTestScheme();

		ActivePerson testPerson = PersonServiceTest.getTestPerson();
		// TODO AS
		//schemeOwnService.setScheme(testPerson.getLogin(), testScheme);

		// нужно удал€ть схемы которые "используютс€" удаленными пользовател€ми
		personService.markDeleted(testPerson);
		schemeService.remove(testScheme);

	}

	public static SharedAccessScheme getTestScheme () throws BusinessException
	{
		String code = "testCode";
		AccessSchemeService schemeService = new AccessSchemeService();
		SharedAccessScheme testScheme = schemeService.findByKey(code);
		if (testScheme==null)
		{
			testScheme = new SharedAccessScheme();
			testScheme.setName("testScheme");
			testScheme.setKey(code);
			testScheme.setCategory("C");

			schemeService.save(testScheme);
		}

		testScheme.getServices().clear();
		schemeService.save(testScheme);

		Service testService = ServiceServiceTest.getTestService();
		testScheme.getServices().add(testService);
		schemeService.save(testScheme);

		return testScheme;
	}

	public void testPersonalScheme () throws BusinessException, BusinessLogicException
	{
		AccessSchemeService schemeService = new AccessSchemeService();
		ServiceService serviceService = new ServiceService();
		PersonalAccessScheme accessScheme1 = schemeService
				.createPersonalScheme(getTestScheme().getCategory(), serviceService.findByCategory(AccessCategory.CATEGORY_ADMIN));
		PersonalAccessScheme accessScheme2 = schemeService
				.createPersonalScheme(getTestScheme().getCategory(), serviceService.findByCategory(AccessCategory.CATEGORY_CLIENT));
		schemeService.save(accessScheme1);
		assertNotNull(accessScheme1);
		schemeService.save(accessScheme2);
		assertNotNull(accessScheme2);
		schemeService.remove(accessScheme1);
		schemeService.remove(accessScheme2);
	}

}
