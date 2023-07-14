package com.rssl.phizic.operations.dictionaries.pages.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessage;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessageService;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessageState;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

import java.util.List;

/**
 * ќпераци€ удал€ет информационного сообщени€.
 * @author komarov
 * @ created 26.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class RemoveInformMessagesOperation extends OperationBase implements RemoveEntityOperation
{
	private static final String MESSAGE = "¬ы не можете %s, так как не имеете доступа ко всем подразделени€м"+
			                        " банка, дл€ которых %s.";
	private static final InformMessageService informMessageService = new InformMessageService();
	private static final DepartmentService departmentService = new DepartmentService();
	private InformMessage informMessage;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		informMessage = informMessageService.findInformMessageById(id);
		if (informMessage == null)
			throw new BusinessLogicException("»нформационное сообщение с id = " + id + " не найдено");

		List<String> allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

	    if(!allowedDepartments.containsAll(informMessage.getDepartments()))
 	    {
		    String message_part1 = "сн€ть с публикации данное информационное сообщение";
		    String message_part2 = "оно было создано";
		    if(informMessage.getState()== InformMessageState.TEMPLATE)
		    {
			    message_part1 = "удалить данный шаблон";
			    message_part2 = "он был создан";
		    }
		    throw new AccessException(String.format(MESSAGE, message_part1, message_part2));
	    }
	}

	public void remove() throws BusinessException
	{
		informMessageService.remove(informMessage);
	}

	public Object getEntity()
	{
		return informMessage;
	}
}
