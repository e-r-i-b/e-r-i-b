package com.rssl.phizic.operations.dictionaries.pages.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessage;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessageService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

import java.util.List;

/**
 * Операция просмотра информационного сообщения.
 * @author komarov
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewInformMessagesOperation extends OperationBase implements ViewEntityOperation
{
	private static final InformMessageService informMessageService = new InformMessageService();
	private static final DepartmentService departmentService = new DepartmentService();
	private InformMessage informMessage;
	private List<String> allowedDepartments;

	/**
	 * Инициализация просматриваемого информационного сообщения.
	 * @param id идентификатор сообщения
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		informMessage = informMessageService.findInformMessageById(id);
		if(informMessage == null)
			throw new BusinessLogicException("Информационное сообщение с id " + id + " не найдено");

		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();
	}

	public Boolean canEdit()
	{
		return allowedDepartments.containsAll(informMessage.getDepartments());
	}

	public InformMessage getEntity() throws BusinessException, BusinessLogicException
	{
		return informMessage;
	}
}
