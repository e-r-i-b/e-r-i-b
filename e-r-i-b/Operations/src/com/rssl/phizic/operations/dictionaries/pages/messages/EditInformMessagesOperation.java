package com.rssl.phizic.operations.dictionaries.pages.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.pages.Page;
import com.rssl.phizic.business.dictionaries.pages.PageService;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessage;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessageService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.*;

/**
 * �������� �������������� ��������������� ���������.
 * @author komarov
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditInformMessagesOperation extends OperationBase implements EditEntityOperation
{
	private static final PageService          pageService = new PageService();
    private static final DepartmentService    departmentService = new DepartmentService();
	private static final InformMessageService informMessageService = new InformMessageService();
	private InformMessage informMessage;
	private List<String> allowedDepartments;

	/**
	 * �������������� �������������� ���������.
	 * @param id - �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		informMessage = informMessageService.findInformMessageById(id);
		if (informMessage == null)
			throw new BusinessLogicException("�������������� ��������� � id = " + id + " �� �������");

		Iterator<String> it = informMessage.getDepartments().iterator();
		for(;it.hasNext();)
		{
			if(departmentService.getDepartmentTBByTBNumber(it.next()) == null)
				it.remove();
		}

		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

	    if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(new ArrayList<String>(informMessage.getDepartments())))
 	    {
		   throw new AccessException("�� �� ������ ��������������� ������ �������������� ���������,"+
				     " ��� ��� �� ������ ������� �� ���� �������������� �����, ��� ������� ��� ���� �������.");
	    }
	}

	/**
	 * ������ ����� �������������� ���������.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		informMessage = new InformMessage();
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();
	}

	/**
	 * �������� ������ ��������� �������������
	 * @return ������������.
	 * @throws BusinessException
	 */
	public Set<String> getAllowedDepartments() throws BusinessException
	{
		return new HashSet<String>(allowedDepartments);
	}

	/**
	 * ������������� ��������� � ������������ �� �������� id
	 * @param pageIds �������������� �������
	 * @param departmentsIds �������������� �������������
	 * @throws BusinessException
	 */
	public void setPagesAndDepartments(Long[] pageIds, String[] departmentsIds) throws BusinessException
	{ 		
		informMessage.setPages(getPages(pageIds));
		informMessage.setDepartments(getDepartments(departmentsIds));
	}

	/**
	 * �������� �������� �� pageIds
	 * @param pageIds �������������� �������
	 * @return ��������� �������
	 * @throws BusinessException
	 */
	public Set<Page> getPages(Long[] pageIds) throws BusinessException
	{
		return new HashSet<Page>(getAllSelectedPages(Arrays.asList(pageIds)));
	}

	/**
	 * �������� ������������ �� departmentIds
	 * @param departmentIds �������������� �������������
	 * @return ��������� �������������
	 * @throws BusinessException
	 */
	private Set<String> getDepartments(String[] departmentIds) throws BusinessException
	{
		Set<String> departments = new HashSet<String>();
		departments.addAll(getAllowedDepartmentsFromIds(departmentIds));
		return departments;
	}

	/**
	 * @param departmentIds ids �������������
	 * @return ������ ��������� �������������
	 */
	public Set<String> getAllowedDepartmentsFromIds(String[] departmentIds)
	{
		Set<String> departments = new HashSet<String>();

		List<String> departmentsIdsList = Arrays.asList(departmentIds);
		for (String department : allowedDepartments)
		{
			if(departmentsIdsList.contains(department))
				departments.add(department);
		}

		return departments;
	}

	public void save() throws BusinessException
	{
		informMessageService.addOrUpdate(informMessage);
	}

	public InformMessage getEntity() throws BusinessException, BusinessLogicException
	{
		return informMessage;
	}

	public List<Page> getAllSelectedPages (List<Long> selectedIds) throws BusinessException
	{
		return pageService.findPagesBySelectedIds(selectedIds);
	}
}
