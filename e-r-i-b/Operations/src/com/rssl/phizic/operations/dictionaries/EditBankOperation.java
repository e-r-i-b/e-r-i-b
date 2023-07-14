package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author: Pakhomova
 * @created: 15.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditBankOperation extends EditDictionaryEntityOperationBase
{
	private ResidentBank bank;
	private static BankDictionaryService bankService = new BankDictionaryService();
	private boolean isNew;
	/**
	 * @return банк
	 */
	public ResidentBank getEntity()
	{
		return bank;
	}

	/**
	 * @param bank устанавливает банк
	 */
	public void setBank(ResidentBank bank)
	{
		this.bank = bank;
	}

	public boolean isNew()
	{
		return isNew;
	}
	
	public void initialize(Comparable bankKey) throws BusinessException
	{
		bank = bankService.findBySynchKey(bankKey, getInstanceName());
		isNew = false;
	}

	public void initializeNew() throws BusinessException
	{
		bank = new ResidentBank();
		isNew = true;
	}

	@Override
	public void doSave() throws BusinessException
	{
		if (bank.getSynchKey() == null)
			bankService.addResidentBank(bank, getInstanceName());
		else
			bankService.updateResidentBank(bank, getInstanceName());
	}
}
