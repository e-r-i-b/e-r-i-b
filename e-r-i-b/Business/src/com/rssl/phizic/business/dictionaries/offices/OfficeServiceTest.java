package com.rssl.phizic.business.dictionaries.offices;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.config.ConfigurationContext;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Kosyakov
 * @ created 07.08.2006
 * @ $Author: Kidyaev $
 * @ $Revision: 2709 $
 */
@SuppressWarnings({"JavaDoc"})
public class OfficeServiceTest extends BusinessTestCaseBase
{
    private static final String OFFICE_ID        = "1111";
	private static final String TEST_OFFICE_NAME = "testOffice";
	private static final String TEST_OFFICE_BRANCHCODE = "0000";
	private static final String TEST_OFFICE_REGIONBANK = "000";
	private static final String TEST_OFFICE_DEPARTEMNTCODE = "0000";

	private static final SimpleService simpleService = new SimpleService();
	private static final DepartmentService officeService = new DepartmentService();

    private static Department office;

    protected void setUp() throws Exception
    {
        super.setUp();

        office = getTestOffice();
    }

	public static Department getTestOffice() throws BusinessException, IOException
	{
		if(office != null)
		{
			return office;
		}
		
		ConfigurationContext configurationContext = ConfigurationContext.getIntstance();		

		if(configurationContext.getActiveConfiguration().equals("sbrf") || configurationContext.getActiveConfiguration().equals("sevb"))
	        createOffice(true);
		else
			createOffice(false);

		throw new BusinessException("Неизвестная конфигурация");
	}

	private static Office createOffice(Boolean isExtendedCode)  throws BusinessException
	{
		Map<String,Object> codeFields = new HashMap();
		if (isExtendedCode)
		{
			codeFields.put("region", TEST_OFFICE_REGIONBANK);
			codeFields.put("branch", TEST_OFFICE_BRANCHCODE);
			codeFields.put("office", TEST_OFFICE_DEPARTEMNTCODE);
		}
		else
		{
			codeFields.put("id", OFFICE_ID);
		}

//		ExtendedCodeImpl newCode   = new ExtendedCodeImpl(TEST_OFFICE_REGIONBANK, TEST_OFFICE_BRANCHCODE, TEST_OFFICE_DEPARTEMNTCODE);
//		if (office!=null)
//			return office;

		Office newOffice = new ExtendedDepartment();
		newOffice.setSynchKey(OFFICE_ID);
		newOffice.buildCode(codeFields);
		newOffice.setName(TEST_OFFICE_NAME);

		return simpleService.add(newOffice);
	}

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

//    public void testFindOfficeByCode() throws BusinessException
//    {
//        CodeImpl code   = new CodeImpl(OFFICE_ID);
//        OfficeImpl tmpOffice = (OfficeImpl) officeService.find(code);
//        assertNotNull(tmpOffice);
//        assertEquals(tmpOffice.getReceiverSurName(), TEST_OFFICE_NAME);
//	}
//
//	public void testFindOfficeBySynchKey() throws BusinessException
//	{
//	    Office tmpOffice = officeService.findBySinchKey(OFFICE_ID);
//	    assertNotNull(tmpOffice);
//	    assertEquals(tmpOffice.getReceiverSurName(), TEST_OFFICE_NAME);
//	}
}
