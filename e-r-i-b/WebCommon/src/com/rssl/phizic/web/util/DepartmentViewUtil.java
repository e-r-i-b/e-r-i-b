package com.rssl.phizic.web.util;

import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import javax.servlet.http.HttpSession;

/**
 * @author egorova
 * @ created 16.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentViewUtil
{
	private static final DepartmentService departmentService = new DepartmentService();

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/*
	* ������� getDepartmentById ���������� ������������� �� id
	*/
	public static Department getDepartmentById(Long id)
	{
		try
		{
			return departmentService.findById(id);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������������� �� id", e);
			return null;
		}
	}

	/*
	* ������� getDepartmentById ���������� ������������� �� id
	*/
	public static Department getDepartmentByIdConsiderMultiBlock(Long id)
	{
		try
		{
			return departmentService.findById(id, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������������� �� id", e);
			return null;
		}
	}

	/**
	 * ���������� ����� �� �� ��� ��������������.
	 * @param id �������������
	 * @return ����� ��
	 */
	public static String getTbNumberByIdConsiderMultiBlock(Long id)
	{
		try
		{
			return departmentService.getNumberTB(id, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������������� �� id", e);
			return null;
		}
	}

	/*
	* ������� getTerBank ���������� ��������������� ���� �� ���� �������������
	*/
	public static Department getTerBank(Code code)
	{
		try
		{
			Department department = null;

			department = departmentService.findByCode(code);

			return departmentService.getTB(department);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ���������������� �����. ", e);
			return null;
		}
	}

	/*
	* ������� isService ���������� true ���� ������������� � id ���������� � ��������� ������
	*/
	public static Boolean isService(com.rssl.phizic.business.departments.Department department)
	{
		if (department == null)
			return null;
		try
		{
			return department.isService();
		}
		catch (Exception e)
		{
			log.error("������ ����������� �������� �������������� ������������� � ��������� ������", e);
			return null;
		}
	}
	/*
	* ������� hasClients ���������� true ���� ������������� � id ����� ��������(�� �������� "��������" ��� "�����������")
	*/
	public static Boolean hasClients(com.rssl.phizic.business.departments.Department department)
	{
		try
		{
			if(departmentService.CountClients(department.getId()) > 0)
			{
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ �������� � �������������", e);
			return null;
		}
	}

	/*
	* ������� getOSB ���������� ��� ��� �������� �������������
	*/
	public static Department getOSB(Department department)
	{
		if (department == null)
		{
			return null;
		}
		try
		{
			if (DepartmentService.isTB(department))
			{
				return null; //������������ �������� �� ���������
			}
			if (DepartmentService.isOSB(department))
			{
				return department;
			}
			return departmentService.getDepartment(department.getRegion(), department.getOSB(), null);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ��� ��� �������������", e);
			return null;
		}
	}

	/**
	 * @param departmentId - id �������������
	 * @return - ������������ ��� ��� �������� �������������
	 */
	public static Department getOsb(String departmentId)
	{
		if (StringHelper.isEmpty(departmentId))
			return null;
		try
		{
			return getOSB(departmentService.findById(Long.valueOf(departmentId)));
		}
		catch (Exception e)
		{
			log.error("������ ����������� ��� ��� �������������", e);
			return null;
		}
	}

	public static String getNameFromOsb(Department department)
	{
		return department.getName();
	}

	public static String getBicFromOsb(Department department)
	{
		return department.getBIC();
	}

	/**
	 * @return �������� ��� �� ���������
	 */
	public static String getDefaultBankBic()
	{
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		return bankContext.getBankBIC();
	}

	/**
	 * @return �������� ��� ����� �� ���������
	 */
	public static String getDefaultBankName()
	{
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		return bankContext.getBankName();
	}

	/*
	* ������� getCorrByBIC ���������� ����. ���� �� ���
	*/
	public static java.lang.String getCorrByBIC(java.lang.String BIC)
	{
		try
		{
			BankDictionaryService bankService = new BankDictionaryService();
			ResidentBank bank = bankService.findByBIC(BIC);
			if (bank != null)
			{
				return bank.getAccount();
			}
			return null;
		}
		catch (Exception e)
		{
			log.error("������ ����������� ����. ����� �� ���", e);
			return "";
		}
	}

	/**
	 * ����������� ������������� � �������� �������� ������� ������������
	 * @return ������������
	 */
	public static Department getCurrentDepartment()
	{
		try
		{
			// �������� �������� ������������ � ���������� �����������, � �������� �� ��������
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData == null)
				return null;

			return personData.getDepartment();
		}
		catch (Exception e)
	    {
	        log.error("������ ��������� ������ ������������ �������", e);
		    return null;
	    }
	}

	/**
	 * @return ������ �������� ��� �������� ������������
	 */
	@Deprecated //�� ���� ���������� ����� �� ��������� ������������, �� ��� ���������� ������ �������� ����, � � ������ ������ ��� ����
	public static Long getCurrentDepartmentTBCode(HttpSession session)
	{
		try
		{
			Long departmentId = null;
			EmployeeData employeeData = (EmployeeData) session.getAttribute(EmployeeData.class.getName());
			if(employeeData != null)
				departmentId = employeeData.getEmployee().getDepartmentId();
			
			PersonData personData = (PersonData)session.getAttribute(StaticPersonData.class.getName());
			if(personData != null)
				departmentId = personData.getPerson().getDepartmentId();

			if(departmentId == null) // ���� �� ������� �������� ����� ������������, �� ������������ �� �������� � ������������
				return null;

			Department department = null;
			if (personData != null)
				department = personData.getDepartment();
			else
				department = employeeData.getDepartment();
			String tbCode = department.getCode().getFields().get("region");
			return Long.parseLong(tbCode);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������ �������� ��� ������������" + e);
			return null;
		}
	}

	/**
	 * ����� ������� ������������
	 * @return ������� ������������
	 */
	public static String getDepartmentPhoneNumber(GateExecutableDocument document)
	{
		if (document instanceof LoanCardOfferClaim)
		{
			LoanCardOfferClaim claim = (LoanCardOfferClaim) document;
			return getDepartmentPhoneNumber(claim.getDepartmentTb(), claim.getDepartmentOsb(), claim.getDepartmentVsp());
		}

		if (document instanceof LoanCardClaim)
		{
			LoanCardClaim claim = (LoanCardClaim) document;
			return getDepartmentPhoneNumber(claim.getTb(), claim.getOsb(), claim.getVsp());
		}

		return StringUtils.EMPTY;
	}

	/**
	 * ����� ������� ������������
	 * @param tb ����� ��
	 * @param osb ����� osb ��� null
	 * @param vsp ����� vsp ��� null
	 * @return ������� ������������
	 */
	public static String getDepartmentPhoneNumber(final String tb, final String osb, final String vsp)
	{
		try
		{
			ExtendedDepartment department = departmentService.getDepartment(tb, osb, vsp);
			return department.getTelephone();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������������. ", e);
			return null;
		}
	}

	/**
	 * �������� �������� ������������
	 * @param tb ��
	 * @param osb ���
	 * @param vsp ���
	 * @return �������� ������������
	 */
	public static String getDepartmentName(String tb, String osb, String vsp)
	{
		try
		{
			Department department = departmentService.getDepartment(tb, osb, vsp);
			return department.getName();
		}
		catch (Exception ex)
		{
			log.error("������ ��������� ������������. ", ex);
	        return "";
		}

	}

	/**
	 * ����� ��, ����� ','.
	 * @param tbList ������ ����� TB.
	 * @return
	 */
	public static String getTbNames(List<String> tbList)
	{
		StringBuilder stringBuilder = new StringBuilder();
		int size = tbList.size();
		for (int i=0; i < size; i++)
		{
			String name = getDepartmentName(tbList.get(i),null,null);
			stringBuilder.append(name);
			if (i+1 != size)
				stringBuilder.append(',');
		}
		return stringBuilder.toString();
	}

	/**
	 * @return ������ ���� ����� �������� �������������.
	 */
	public static List<String> getAllTb()
	{
		try
		{
			return departmentService.getTerbanksNumbers();
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� ������������.");
			return null;
		}
	}

	/**
	 * ����������� ������������� , � �������� �������� ������� ������������
	 * @return ������������. ����������� ������������ ���������� �� ���������. ���������� ��� ����, ��� ��������� ��������� ������������� �� ����� � �����: ��� �����, ����� ����...
	 *
	 */
	public static Department getCurrentDepartmentFromContext()
	{
		try
		{
			// �������� �������� ������������ � ���������� �����������, � �������� �� ��������
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData == null)
				return null;
			return  personData.getDepartment();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������ ������������ �������", e);
			return null;
		}
	}

	/**
	 * @return ������� �������. ������� ������� �� ��������� ������������
	 */
	public static Department getCurrentTerbankFromContext()
	{
		try
		{
			// �������� �������� ������������ � ���������� �����������, � �������� �� ��������
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData == null)
				return null;
			return  personData.getTb();
		}
		catch (Exception ex)
		{
			log.error("������ ��������� �������� �������. ", ex);
			return null;
		}
	}

	/**
	 * @return ��� �������� ��������. ������� ������� �� ��������� ������������
	 */
	public static String getCurrentTerbankName()
	{
		try
		{
			Department department = getCurrentTerbankFromContext();
			if (department != null)
				return department.getName();
			else
				return null;
		}
		catch (Exception ex)
		{
			log.error("������ ����� �������� �������. ", ex);
			return "";
		}

	}

	/**
	 * @param tbCode ��� ��.
	 * @return ��� ��������.
	 */
	public static String getCurrentTerbankNameByCode(String tbCode)
	{
		try
		{
			return  DepartmentViewUtil.getDepartmentName(tbCode, null, null);
		}
		catch (Exception ex)
		{
			log.error("������ ����� �������� �������. ", ex);
			return "";
		}
	}
}
