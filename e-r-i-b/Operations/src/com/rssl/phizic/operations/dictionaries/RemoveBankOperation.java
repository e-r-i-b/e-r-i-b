package com.rssl.phizic.operations.dictionaries;


import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author: Pakhomova
 * @created: 18.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class RemoveBankOperation extends RemoveDictionaryEntityOperationBase
{
	private static BankDictionaryService bankService = new BankDictionaryService();

	private ResidentBank bank;

	/**
	 * инициализация операции
	 * @param synchKey   synchKey банка
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */

	// TODO использовать initialize(Long id) после выполнения задачи 
	// TODO "Использовать в справочниках в качестве Primary key значение, генерируемое в ИКФЛ, а не значение из внешней системы"
	public void initialize(Comparable synchKey) throws BusinessException, BusinessLogicException
	{
		ResidentBank temp = bankService.findBySynchKey(synchKey, getInstanceName());

		if(temp == null)
			throw new BusinessLogicException("Банк с synchKey= " + synchKey + " не найден");

		bank = temp;
	}

	public ResidentBank getEntity()
	{
		return bank;
	}


	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * удаляет банк
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		try
		{
		    bankService.remove(bank, getInstanceName());
		}
		catch (ConstraintViolationException e)
		{
		   throw new BusinessLogicException("Невозможно удалить банк.");
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
