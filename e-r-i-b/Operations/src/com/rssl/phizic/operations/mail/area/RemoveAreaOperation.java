package com.rssl.phizic.operations.mail.area;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.business.mail.area.ContactCenterAreaService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class RemoveAreaOperation extends RemoveDictionaryEntityOperationBase<ContactCenterArea, Restriction>
{
	private ContactCenterArea area;
	private ContactCenterAreaService service = new ContactCenterAreaService();

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		area = service.getById(id, getInstanceName());
	}

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		service.remove(area, getInstanceName());
	}

	public ContactCenterArea getEntity()
	{
		return area;
	}
}
