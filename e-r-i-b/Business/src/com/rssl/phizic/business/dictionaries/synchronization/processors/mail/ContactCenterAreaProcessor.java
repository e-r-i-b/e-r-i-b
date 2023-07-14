package com.rssl.phizic.business.dictionaries.synchronization.processors.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.mail.area.ContactCenterArea;

import java.util.HashSet;
import java.util.Set;

/**
 * @author akrenev
 * @ created 02.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей контактных центров
 */

public class ContactCenterAreaProcessor extends ProcessorBase<ContactCenterArea>
{
	@Override
	protected Class<ContactCenterArea> getEntityClass()
	{
		return ContactCenterArea.class;
	}

	@Override
	protected ContactCenterArea getNewEntity()
	{
		ContactCenterArea contactCenterArea = new ContactCenterArea();
		contactCenterArea.setDepartments(new HashSet<String>());
		return contactCenterArea;
	}

	@Override
	protected void update(ContactCenterArea source, ContactCenterArea destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setName(source.getName());

		Set<String> departments = destination.getDepartments();
		departments.clear();
		departments.addAll(source.getDepartments());
	}
}
