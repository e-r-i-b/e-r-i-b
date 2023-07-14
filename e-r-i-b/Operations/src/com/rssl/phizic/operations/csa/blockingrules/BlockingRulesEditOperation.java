package com.rssl.phizic.operations.csa.blockingrules;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRules;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRulesService;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingState;
import com.rssl.auth.csa.wsclient.events.BlockingRulesClearCacheEvent;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * ќпераци€ создани€/редактировани€ правила блокировки
 */
public class BlockingRulesEditOperation extends OperationBase implements EditEntityOperation
{
	private BlockingRules blockingRule;
	private static final BlockingRulesService blockingRulesService = new BlockingRulesService();

	public void initializeNew()
	{
		blockingRule = new BlockingRules();
	}

	public void initialize(Long id) throws BusinessException
	{
		BlockingRules result = blockingRulesService.findById(id);
		if (result != null)
			blockingRule = result;
		else
			throw new BusinessException("Ќе найдено правило блокировки с id = " + id);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		blockingRulesService.save(blockingRule);
		try
		{
			EventSender.getInstance().sendEvent(new BlockingRulesClearCacheEvent());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return blockingRule;
	}

	/**
	 * —оздать и сохранить запись о глобальной блокировке
	 * @param state - статус блокировки (blocked - все заблокировано, unblocked - сни€тие глобального ограничени€)
	 */
	public void createGlobalBlockingRecord(BlockingState state) throws BusinessException, BusinessLogicException
	{
		BlockingRules global = blockingRulesService.getGlobalBlocking();
		if (global != null)
			initialize(global.getId());
		else
		{
			initializeNew();
			blockingRule.setName("global");
			blockingRule.setDepartments("global");
			blockingRule.setERIBMessage("ѕо техническим причинам вход в систему временно ограничен. ѕопробуйте войти позднее");
			blockingRule.setResumingTime(Calendar.getInstance());
		}
		blockingRule.setState(state);
		save();
	}

	/**
	 * »зменение статуса блокировки.
	 * @param id - идентификатор блокировки
	 * @param state - статус в ктороый необходимо установить блокировку.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void changeState(Long id, BlockingState state) throws BusinessException, BusinessLogicException
	{
		if (id != null)
		{
			initialize(id);
			blockingRule.setState(state);
			save();
		}	
	}
}
