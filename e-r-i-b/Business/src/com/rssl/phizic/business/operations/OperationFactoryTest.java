package com.rssl.phizic.business.operations;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.test.MockRestrictionProvider;

import java.util.Properties;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 29.09.2005 Time: 17:07:22 */
@SuppressWarnings({"JavaDoc"})
public class OperationFactoryTest extends BusinessTestCaseBase
{
    private OperationFactory getFactory()
    {
        return new OperationFactoryImpl(new MockRestrictionProvider());
    }

    public void testTryCreateOperation() throws Exception
    {
	    AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class, AccessType.employee.getApplication());
	    AccessPolicy policy = authenticationConfig.getPolicy(AccessType.employee);

	    String className1 = "com.rssl.phizic.operations.employees.GetEmployeeListOperation";
        Class operationClass1 = Class.forName(className1);
        String className2 = "com.rssl.phizic.operations.employees.EditEmployeeOperation";
        Class operationClass2 = Class.forName(className2);

	    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
	    SecurityService securityService = new SecurityService();
	    CommonLogin superLogin = securityService.getLogin(securityConfig.getDefaultAdminName(), SecurityService.SCOPE_EMPLOYEE);

	    AuthModule.setAuthModule(new AuthModule(new PrincipalImpl(superLogin, policy, new Properties())));

	    try
	    {
		    getFactory().create(operationClass2);

		    getFactory().create(operationClass1);

		    getFactory().create(operationClass1);
	    }
	    finally
	    {
		    AuthModule.setAuthModule(null);
	    }

    }
}
