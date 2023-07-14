package com.rssl.phizic.operations.csa.blockingrules;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRulesService;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * Операция просмотра списка правил блокировки.
 */
public class BlockingRulesListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final BlockingRulesService blockingRulesService = new BlockingRulesService();

	public Query createQuery(String name)
	{
		return new BeanQuery(this, BlockingRulesListOperation.class.getName() +"."+ name, Constants.DB_CSA);
	}
	/**
	 * Выставлено ли глобальное ограничение на вход
	 * @return true/false
	 * @throws BusinessException
	 */
	public boolean isGlobalBlock() throws BusinessException
	{
		return blockingRulesService.isGlobalBlock();	
	}
}