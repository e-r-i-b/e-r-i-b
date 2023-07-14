package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;

/**
 * @author Kosyakova
 * @ created 09.01.2007
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverService
{
	private static final SimpleService   simpleService   = new SimpleService();

	public void remove(final ContactReceiver receiver) throws BusinessException
	{
		simpleService.remove(receiver);
	}
	public ContactReceiver add(ContactReceiver receiver) throws BusinessException
	{
	    return simpleService.add(receiver);
	}

	public ContactReceiver update(ContactReceiver receiver) throws BusinessException
	{
	    return simpleService.update(receiver);
	}
	public ContactReceiver findById(Long id) throws BusinessException
	{
	    return simpleService.findById(ContactReceiver.class, id);
	}
}
