package com.rssl.phizic.operations.dictionaries.kbk;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.kbk.KBKService;

/**
 * @author akrenev
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditKBKOperation extends OperationBase implements EditEntityOperation
{
	private static final KBKService service = new KBKService();

	private KBK kbk;

	public void initializeNew() throws BusinessException
	{
		kbk = new KBK();
	}

	public void initialize(Long id) throws BusinessException
	{
		kbk = service.findById(id);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(kbk);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return kbk;
	}
}
