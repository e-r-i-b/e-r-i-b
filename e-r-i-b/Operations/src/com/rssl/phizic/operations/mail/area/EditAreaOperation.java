package com.rssl.phizic.operations.mail.area;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.business.mail.area.ContactCenterAreaService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * ����������� �������� ��
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditAreaOperation  extends EditDictionaryEntityOperationBase<ContactCenterArea, Restriction>
{
	private static final String DEPARTMENT_CONSTRAINT = "I_C_CENTER_AREAS_DEPARTMENTS";
	private static final String NAME_CONSTRAINT       = "I_CONTACT_CENTER_AREAS_NAME";
	private ContactCenterArea area;
	private ContactCenterAreaService service = new ContactCenterAreaService();

	/**
	 * �������������� �������� �������� ��
	 */
	public void initialize() throws BusinessException
	{
		area = new ContactCenterArea();
	}

	/**
	 * �������������� �������� �������� �� �� ��������������
	 * @param id �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		area = service.getById(id, getInstanceName());
		if(area == null)
			throw new BusinessLogicException("�������� ����������� ������ � id="+id+" �� �������.");

		if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(area.getDepartments()))
        {
		   throw new AccessException("�� �� ������ ��������������� ������ ��������,"+
				     " ��� ��� �� ������ ������� �� ���� �������������� �����, ��� ������� ��� ���� �������.");
	    }
	}

	/**
	 * ����� ��������� ��� ������� ��� ������� �������� ��.
	 * @return ��������
	 * @throws BusinessException
	 */
	private String getDublicateTRB() throws BusinessException
	{
		List<String> departmentsWithPanels = service.findTRBWithCCA(getInstanceName());
		StringBuilder s = new StringBuilder((departmentsWithPanels.size() > 1 ? "��": "�"));
		boolean first = true;
		for(String department : departmentsWithPanels)
		{
			s.append((first ? " " : ", "));
			s.append(department);
			first = false;
		}
		return s.toString();
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			service.addOrUpdate(area, getInstanceName());
		}
		catch(ConstraintViolationException cve)
		{
			if(cve.getCause().getMessage().toUpperCase().contains(DEPARTMENT_CONSTRAINT))
			{
				throw new BusinessLogicException("�������� ��� �������"+ getDublicateTRB() +" ��� ���������. ����������, �������� ������ ���������.", cve);
			}

			if(cve.getCause().getMessage().toUpperCase().contains(NAME_CONSTRAINT))
			{
				throw new BusinessLogicException("�������� �� � ����� ��������� ��� ����������. ����������, ������� ������ ��������.", cve);
			}
		}

	}

	public ContactCenterArea getEntity() throws BusinessException, BusinessLogicException
	{
		return area;
	}

	/**
	 * ������������� ������������ �� departmentIds
	 * @param departmentTbs ������ �������������
	 * @throws BusinessException
	 */
	public void setDepartments(String[] departmentTbs) throws BusinessException
	{
		area.setDepartments(new HashSet<String>(Arrays.asList(departmentTbs)));
	}
}
