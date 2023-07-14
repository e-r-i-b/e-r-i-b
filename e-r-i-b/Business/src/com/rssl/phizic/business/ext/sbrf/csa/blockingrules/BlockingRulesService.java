package com.rssl.phizic.business.ext.sbrf.csa.blockingrules;

import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * —ервис дл€ работы с правилами блокировки
 */
public class BlockingRulesService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * —охранение в Ѕƒ правила блокировки
	 * @param rule - правило дл€ сохранени€
	 * @throws BusinessException
	 */
	public void save(BlockingRules rule) throws BusinessException
	{
		simpleService.addOrUpdateWithConstraintViolationException(rule, getInstanceName());
	}

	/**
	 * ”даление правила блкировки
	 * @param rule - правило дл€ удалени€
	 */
	public void remove(BlockingRules rule) throws BusinessException
	{
		simpleService.remove(rule,getInstanceName());
	}

	/**
	 * ѕоиск правила блокировки по id
	 * @param id - идентификатор правила
	 * @return найденое правило
	 * @throws BusinessException
	 */
	public BlockingRules findById(Long id) throws BusinessException
	{
		return simpleService.findById(BlockingRules.class, id, getInstanceName());
	}

	/**
	 * ѕолучение глобальной блокировки. «апись в таблице с departments = 'global'-запись о глобальной блокировке.
	 * @return true/false
	 * @throws BusinessException
	 */
	public BlockingRules getGlobalBlocking() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BlockingRules.class);
		criteria.add(Expression.eq("departments", "global"));
		return simpleService.findSingle(criteria, getInstanceName());
	}

	/**
	 * ¬ыставлено ли глобальное ограничение на вход
	 * @return true/false
	 * @throws BusinessException
	 */
	public boolean isGlobalBlock() throws BusinessException
	{
		BlockingRules global = getGlobalBlocking();
		//если записи о глобальной блокировке не найдено считаем, что блокировки нет.
		if (global == null)
			return false;
		return global.getState() == BlockingState.blocked;
	}

	private static String getInstanceName()
	{
		return Constants.DB_CSA;
	}
}
