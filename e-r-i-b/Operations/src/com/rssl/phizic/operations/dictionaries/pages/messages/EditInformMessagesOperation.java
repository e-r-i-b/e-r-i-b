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
 * ќпераци€ редактировани€ информационного сообщени€.
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
	 * »нициализирует информационное сообщение.
	 * @param id - идентификатор
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		informMessage = informMessageService.findInformMessageById(id);
		if (informMessage == null)
			throw new BusinessLogicException("»нформационное сообщение с id = " + id + " не найдено");

		Iterator<String> it = informMessage.getDepartments().iterator();
		for(;it.hasNext();)
		{
			if(departmentService.getDepartmentTBByTBNumber(it.next()) == null)
				it.remove();
		}

		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

	    if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(new ArrayList<String>(informMessage.getDepartments())))
 	    {
		   throw new AccessException("¬ы не можете отредактировать данное информационное сообщение,"+
				     " так как не имеете доступа ко всем подразделени€м банка, дл€ которых оно было создано.");
	    }
	}

	/**
	 * —оздаЄт новое информационное сообщение.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		informMessage = new InformMessage();
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();
	}

	/**
	 * ѕолучает список доступных департаментов
	 * @return департаменты.
	 * @throws BusinessException
	 */
	public Set<String> getAllowedDepartments() throws BusinessException
	{
		return new HashSet<String>(allowedDepartments);
	}

	/**
	 * ”станавливает странички и департаменты по заданным id
	 * @param pageIds идентификаторы страниц
	 * @param departmentsIds идентификаторы департаментов
	 * @throws BusinessException
	 */
	public void setPagesAndDepartments(Long[] pageIds, String[] departmentsIds) throws BusinessException
	{ 		
		informMessage.setPages(getPages(pageIds));
		informMessage.setDepartments(getDepartments(departmentsIds));
	}

	/**
	 * ѕолучает страницы по pageIds
	 * @param pageIds идентификаторы страниц
	 * @return множество страниц
	 * @throws BusinessException
	 */
	public Set<Page> getPages(Long[] pageIds) throws BusinessException
	{
		return new HashSet<Page>(getAllSelectedPages(Arrays.asList(pageIds)));
	}

	/**
	 * ѕолучает департаменты по departmentIds
	 * @param departmentIds идентификаторы департаментов
	 * @return множество департаментов
	 * @throws BusinessException
	 */
	private Set<String> getDepartments(String[] departmentIds) throws BusinessException
	{
		Set<String> departments = new HashSet<String>();
		departments.addAll(getAllowedDepartmentsFromIds(departmentIds));
		return departments;
	}

	/**
	 * @param departmentIds ids департаментов
	 * @return список доступных департаментов
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
