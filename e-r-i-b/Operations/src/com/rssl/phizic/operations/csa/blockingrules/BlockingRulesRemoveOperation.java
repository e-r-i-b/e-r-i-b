package com.rssl.phizic.operations.csa.blockingrules;

import com.rssl.auth.csa.wsclient.events.BlockingRulesClearCacheEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRules;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRulesService;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author vagin
 * @ created 19.11.2012
 * @ $Author$
 * @ $Revision$
 * Операция удаления правила блокировки.
 */
public class BlockingRulesRemoveOperation extends OperationBase implements RemoveEntityOperation
{
	private BlockingRules blockingRule = new BlockingRules();
	private static final BlockingRulesService blockingRulesService = new BlockingRulesService();
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		BlockingRules rule = blockingRulesService.findById(id);
		if (rule == null)
			throw new BusinessLogicException("Не найдено правило с id" + id);
		else
			blockingRule = rule;
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		blockingRulesService.remove(blockingRule);
		try
		{
			EventSender.getInstance().sendEvent(new BlockingRulesClearCacheEvent());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Object getEntity()
	{
		return blockingRule;
	}
}
