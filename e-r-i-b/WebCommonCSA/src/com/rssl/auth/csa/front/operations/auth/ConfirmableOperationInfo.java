package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Map;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс контекста операции для подтверждения
 */

public interface ConfirmableOperationInfo
{
	/**
	 * @return токен аутентификации
	 */
	public String getAuthToken();

	/**
	 * задать предпочтительный способ подтверждения
	 * @param preferredConfirmType предпочтительный способ подтверждения
	 */
	public void setPreferredConfirmType(ConfirmStrategyType preferredConfirmType);

	/**
	 * @return предпочтительный способ подтверждения
	 */
	public ConfirmStrategyType getPreferredConfirmType();

	/**
	 * задать информацию о картах, как источнике подтверждения
	 * @param cardConfirmationSource информация о картах, как источнике подтверждения
	 */
	public void setCardConfirmationSource(Map<String, String> cardConfirmationSource);

	/**
	 * @return информация о картах, как источнике подтверждения
	 */
	public Map<String, String> getCardConfirmationSource();

	/**
	 * @return доступно ли подтверждение по push
	 */
	public boolean isPushAllowed();

	/**
	 * задать доступно ли подтверждение по push
	 * @param pushAllowed доступно ли подтверждение по push
	 */
	public void setPushAllowed(boolean pushAllowed);

}
