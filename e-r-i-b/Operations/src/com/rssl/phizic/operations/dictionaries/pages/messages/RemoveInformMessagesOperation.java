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
 * �������� ������� ��������������� ���������.
 * @author komarov
 * @ created 26.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class RemoveInformMessagesOperation extends OperationBase implements RemoveEntityOperation
{
	private static final String MESSAGE = "�� �� ������ %s, ��� ��� �� ������ ������� �� ���� ��������������"+
			                        " �����, ��� ������� %s.";
	private static final InformMessageService informMessageService = new InformMessageService();
	private static final DepartmentService departmentService = new DepartmentService();
	private InformMessage informMessage;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		informMessage = informMessageService.findInformMessageById(id);
		if (informMessage == null)
			throw new BusinessLogicException("�������������� ��������� � id = " + id + " �� �������");

		List<String> allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

	    if(!allowedDepartments.containsAll(informMessage.getDepartments()))
 	    {
		    String message_part1 = "����� � ���������� ������ �������������� ���������";
		    String message_part2 = "��� ���� �������";
		    if(informMessage.getState()== InformMessageState.TEMPLATE)
		    {
			    message_part1 = "������� ������ ������";
			    message_part2 = "�� ��� ������";
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
