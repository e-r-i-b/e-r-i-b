package com.rssl.phizic.operations.bannedAccount;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bannedAccount.BannedAccount;
import com.rssl.phizic.business.bannedAccount.BannedAccountService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class RemoveBannedAccountOperation extends RemoveDictionaryEntityOperationBase
{
	private static final BannedAccountService bannedAccountService = new BannedAccountService();
	private BannedAccount bannedAccount;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id != null)
		{
			bannedAccount = bannedAccountService.getById(id, getInstanceName());
		    if(bannedAccount == null)
			    throw new BusinessException("Не найдена маска счета с id=" + id);
		}
		else
			throw new BusinessLogicException("Данной маски по счету не существует");
	}

	@Override
	public void doRemove() throws BusinessException
	{
		bannedAccountService.remove(bannedAccount, getInstanceName());
	}

	public Object getEntity()
	{
		return bannedAccount;
	}
}
