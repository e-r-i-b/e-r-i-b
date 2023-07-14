package com.rssl.phizic.operations.dictionaries.kbk;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.kbk.KBKService;

/**
 * @author akrenev
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class RemoveKBKOperation extends OperationBase implements RemoveEntityOperation
{
	private static final KBKService service = new KBKService();
	private KBK kbk;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		kbk = service.findById(id);
	}

	public void remove() throws BusinessException
	{
		service.remove(kbk);
	}

	public Object getEntity()
	{
		return kbk;
	}
}
