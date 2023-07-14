package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.security.permissions.OperationPermission;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessSchemeTest;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.security.AllPermission;
import java.util.List;
import java.util.Properties;

/**
 * @author Roshka
 * @ created 12.04.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class PrincipalOperationPermissionTest extends BusinessTestCaseBase
{
	public void testPrincipalOperationPermission() throws Exception
	{
		ServiceService serviceService = new ServiceService();
		SchemeOwnService schemeOwnService = new SchemeOwnService();

		SharedAccessScheme testScheme = AccessSchemeTest.getTestScheme();
		ActivePerson testPerson = PersonServiceTest.getTestPerson();

		AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class);
		AccessPolicy policy = authenticationConfig.getPolicy(AccessType.simple);

		try
		{
			schemeOwnService.setScheme(testPerson.getLogin(), policy.getAccessType(), testScheme);

			List<Service> services = testScheme.getServices();
			Service service = services.get(0);

			List<ServiceOperationDescriptor> serviceOperations = serviceService.getServiceOperations(service);
			ServiceOperationDescriptor serviceOperationDescriptor = serviceOperations.get(0);

			PrincipalOperationPermission principalOperationPermission =
					new PrincipalOperationPermission(new PrincipalImpl(testPerson.getLogin(), policy, new Properties()));

			OperationPermission operationPermission =
					new OperationPermission(serviceOperationDescriptor.getOperationDescriptor().getOperationClassName(),
							service.getKey());

			assertTrue( principalOperationPermission.implies(operationPermission) );
			assertFalse(principalOperationPermission.implies(new AllPermission()));

			OperationDescriptor dummyOperationDescriptor = new OperationDescriptor();
			dummyOperationDescriptor.setOperationClassName(PrincipalOperationPermissionTest.class.getName());


			OperationPermission dummyOperationPermission =
					new OperationPermission(service.getKey(),
							dummyOperationDescriptor.getOperationClassName());
			
			assertFalse(principalOperationPermission.implies(dummyOperationPermission));

		}
		finally
		{
			schemeOwnService.removeScheme(testPerson.getLogin(), policy.getAccessType());
		}
	}

}
