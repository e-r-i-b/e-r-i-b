package com.rssl.phizic.operations.registration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.connectUdbo.MultiInstanceUDBOClaimRulesService;
import com.rssl.phizic.business.connectUdbo.UDBOClaimRules;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Операция для редактирования условий заявления для подключения УДБО
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class EditUDBOClaimRulesOperation extends EditDictionaryEntityOperationBase
{

	private static final MultiInstanceUDBOClaimRulesService claimRulesService = new MultiInstanceUDBOClaimRulesService();
	private UDBOClaimRules claimRules;

	/**
	 * Инициализация операции
	 * @param id - идентификатор
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id != null)
			claimRules = claimRulesService.findById(id, getInstanceName());
		else
			claimRules = new UDBOClaimRules();
	}

	@Override
	protected void doSave() throws BusinessException, BusinessLogicException
	{
		if (claimRules.getId() == null)
			claimRulesService.add(claimRules, getInstanceName());
		else
			claimRulesService.update(claimRules, getInstanceName());
	}

	/**
	 * @return условия заявления
	 */
	public UDBOClaimRules getEntity()
	{
		return claimRules;
	}

	/**
	 * Удаление условий со статусом "Введён"
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void remove() throws Exception
	{
		claimRulesService.remove(claimRules, getInstanceName());
		MultiBlockModeDictionaryHelper.updateDictionary(getEntityClass());
	}

	/**
	 * Дата вступления в силу не должна совпадать с ранее введенной датой
	 * @param startDate - дата вступления в силу
	 * @return true - не совпадает, false - совпадает
	 * @throws BusinessException
	 */
	public boolean checkStartDate(Calendar startDate) throws BusinessException
	{
		List<Calendar> dateList = claimRulesService.getEnteredRulesDateList(getInstanceName());
		if (CollectionUtils.isNotEmpty(dateList))
			for (Calendar date : dateList)
				if (date.equals(startDate))
					return false;
		return true;
	}
}
