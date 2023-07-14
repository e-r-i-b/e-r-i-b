package com.rssl.phizic.operations.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;


/**
 * �������� �������������� ������ ���-������� ����
 * @author Rtischeva
 * @created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class ERMBSmsSettingsEditOperation extends OperationBase implements EditEntityOperation
{
	private SMSResourcesService service   = new SMSResourcesService();
	private MessageResource resource;

	public void initialize(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new BusinessException("�� ������ ������������� �������������� ���-�������");
		}

		resource = service.findSmsResourceById(id);

		if (resource == null)
		{
			throw new NotFoundException("���-������ � ��������������� " + id + " �� ������");
		}
	}
	public void save() throws BusinessException, BusinessLogicException
	{
		service.updateSmsResources(null, resource);
	}

	public Object getEntity()
	{
		return resource;
	}
}
