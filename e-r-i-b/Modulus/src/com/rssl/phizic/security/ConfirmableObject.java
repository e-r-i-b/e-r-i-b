package com.rssl.phizic.security;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

import java.util.Map;

/**
 * @author Krenev
 * @ created 27.11.2008
 * @ $Author$
 * @ $Revision$
 */
public interface ConfirmableObject
{
	/**
	 * @return идентификатор подтверждаемого объекта
	 */
	public Long getId();

	/**
	 * @return подпись
	 * @throws SecurityException
	 * @throws SecurityLogicException
	 */
	public byte[] getSignableObject() throws SecurityException, SecurityLogicException;

	/**
	 * Установить тип стратегии подтверждения
	 * @param confirmStrategyType тип стратегии
	 */
	void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType);
}
