package com.rssl.phizic.business.departments;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.OfficeServiceTest;
import com.rssl.phizic.business.dictionaries.offices.common.DepartmentImpl;
import com.rssl.phizic.config.ConfigurationContext;

import java.math.BigDecimal;
import java.sql.Time;

/**
 * @author Evgrafov
 * @ created 15.08.2006
 * @ $Author: egorova $
 * @ $Revision: 12199 $
 */

@SuppressWarnings({"ALL"})
public class DepartmentTest extends BusinessTestCaseBase
{
    private static SimpleService service = new SimpleService();
	private static DepartmentService departmentService = new DepartmentService();

	private static Department department;

    public static Department getTestDepartment() throws Exception {
	    if(department!=null)
	    {
		    return department;
	    }

	    department = createTestDepartment();
	    return department;
    }

    private static Department createTestDepartment() throws Exception
    {
	    ConfigurationContext configurationContext = ConfigurationContext.getIntstance();

	    if(configurationContext.getActiveConfiguration().equals("sbrf") || configurationContext.getActiveConfiguration().equals("sevb"))
	        createDepartment(true);
		else
			createDepartment(false);

	    throw new BusinessException("Неизвестная конфигурация");
    }

	private static Department createDepartment(Boolean isExtendedDepartment) throws Exception
	{
		Department depart = departmentService.findBySynchKey((String) OfficeServiceTest.getTestOffice().getSynchKey());
		if (depart!=null)
			return depart;

	    depart = new DepartmentImpl();
		initDepartment(depart);
		return service.add(depart);
	}

	private static DepartmentImpl createTestDepartmentImpl() throws Exception
	{
		DepartmentImpl depart = (DepartmentImpl) departmentService.findBySynchKey((String)OfficeServiceTest.getTestOffice().getSynchKey());
		if(depart!=null)
			return depart;

	    depart = new DepartmentImpl();
		initDepartment(depart);
		return service.add(depart);
	}

	private static void initDepartment(Department depart) throws Exception
	{
        depart.setName("test");

        depart.setCity("Город");
        depart.setLocation("location");
        depart.setAddress("post address");
        depart.setTelephone("23444");

        depart.setConnectionCharge(new BigDecimal("12.22"));
        depart.setReconnectionCharge(new BigDecimal("10.00"));
        depart.setMonthlyCharge(new BigDecimal("3.00"));

        depart.setWeekOperationTimeBegin(Time.valueOf("10:00:00"));
        depart.setWeekOperationTimeEnd(Time.valueOf("17:00:00"));

        depart.setWeekendOperationTimeBegin(Time.valueOf("12:00:00"));
        depart.setWeekendOperationTimeEnd(Time.valueOf("15:00:00"));

        depart.setFridayOperationTimeBegin(Time.valueOf("11:00:00"));
        depart.setFridayOperationTimeEnd(Time.valueOf("16:00:00"));
		
	}

/* TODO: Тестовый Department используется тестовым пользователем
    public void testCreateRemoveDepartment() throws Exception
    {
        Department testDepartment = createTestDepartment();
        service.remove(testDepartment);
    }
*/

    public void testDepartment() throws Exception
	{
		Department depart = getTestDepartment();
	}
}
