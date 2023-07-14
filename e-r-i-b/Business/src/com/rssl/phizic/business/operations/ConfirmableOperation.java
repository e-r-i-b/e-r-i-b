package com.rssl.phizic.business.operations;

import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: khudyakov $
 * @ $Revision: 83241 $
 */

public interface ConfirmableOperation<R extends Restriction> extends Operation<R>
{
	/**
	 * @return объект для подтверждения.
	 */

	ConfirmableObject getConfirmableObject();

	/**
	 * Создать запрос на подтверждение операции
	 * @param sessionId идентификатор текущей сессии
	 * @return новый запрос
	 */
	ConfirmRequest createConfirmRequest(String sessionId) throws BusinessException, BusinessLogicException;

	/**
	 * @param request установить ранее созданный запрос на подтверждение
	 */
	void setConfirmRequest(ConfirmRequest request);

	/**
	 * @return запрпос на подтверждение операции
	 */
	ConfirmRequest getRequest();

	/**
	 * @param confirmResponse подтверждение операции
	 */
	void setConfirmResponse(ConfirmResponse confirmResponse);

	/**
	 * @return стратегия подтверждения
	 */
	ConfirmStrategy getConfirmStrategy();

	/**
	 * @return имя стратегии аутентификации
	 */
	ConfirmStrategyType getStrategyType();

	/**
	 * Сбросить стратегию подтверждения
	 * @throws BusinessException
	 */
	void resetConfirmStrategy() throws BusinessException;

	/**
	 * @return ридер стратегии
	 */
	ConfirmResponseReader getConfirmResponseReader();

	/**
	 * @param callBackHandler хендлер
	 * @return PreConfirmObject
	 */
	PreConfirmObject preConfirm(CallBackHandler callBackHandler) throws SecurityLogicException, BusinessException, BusinessLogicException;

	/**
	 * выпольнить подтвержение.
	 * @throws SecurityLogicException
	 * @throws BusinessException
	 */
	public void confirm() throws SecurityLogicException, BusinessException, BusinessLogicException;
}
