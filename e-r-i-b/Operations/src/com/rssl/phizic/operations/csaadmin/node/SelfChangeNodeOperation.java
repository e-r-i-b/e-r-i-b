package com.rssl.phizic.operations.csaadmin.node;

import com.rssl.phizic.business.BusinessException;

import java.util.Collections;

/**
 * @author mihaylov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция самостоятельной смены блока
 */
public class SelfChangeNodeOperation extends ChangeNodeOperation
{
	/**
	 * Инициализация операции
	 * @param nodeId - идентификатор блока в который необходимо перейти
	 * @throws com.rssl.phizic.business.BusinessException
	 * @throws com.rssl.phizic.operations.csaadmin.node.NodeNotAvailableException -- целевой блок не доступен
	 */
	public void initialize(Long nodeId) throws BusinessException, NodeNotAvailableException
	{
		super.initialize(nodeId,null, Collections.<String,String>emptyMap());
	}

}
