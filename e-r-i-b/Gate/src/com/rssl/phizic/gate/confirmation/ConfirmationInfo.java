package com.rssl.phizic.gate.confirmation;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о подтверждении операций клиентом
 */

public interface ConfirmationInfo
{
	/**
	 * @return предпочтительный способ подтверждения
	 */
	public ConfirmStrategyType getConfirmStrategyType();

	/**
	 * @return источники подтверждений
	 */
	public List<CardConfirmationSource> getConfirmationSources();

	/**
	 * @return доступно ли подтверждение по push
	 */
	public boolean isPushAllowed();
}
