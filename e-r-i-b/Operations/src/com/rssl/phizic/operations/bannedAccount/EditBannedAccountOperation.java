package com.rssl.phizic.operations.bannedAccount;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bannedAccount.BannedAccount;
import com.rssl.phizic.business.bannedAccount.BannedAccountService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditBannedAccountOperation extends EditDictionaryEntityOperationBase
{
	private static final BannedAccountService bannedAccountService = new BannedAccountService();
	private BannedAccount bannedAccount;

	public void initialize(Long id) throws BusinessException
	{
		bannedAccount = bannedAccountService.getById(id, getInstanceName());
		if(bannedAccount == null)
			throw new BusinessException("Не найдена маска счета с id=" + id);
	}
	public void initializeNew()
	{
		bannedAccount = new BannedAccount();
	}

	@Override
	public void doSave() throws BusinessException, BusinessLogicException
	{
		bannedAccountService.addOrUpdate(bannedAccount, getInstanceName());
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return bannedAccount;
	}
}
