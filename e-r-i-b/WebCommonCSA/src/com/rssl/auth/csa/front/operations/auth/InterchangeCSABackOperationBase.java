package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.business.promo.PromoterAndPromoClientSessionService;
import com.rssl.auth.csa.front.exceptions.*;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.wsclient.exceptions.InvalidCodeConfirmException;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;
import org.w3c.dom.Document;

/**
 * Базовая операция для обмена с ЦСА Бэком
 * @author niculichev
 * @ created 25.01.2013
 * @ $Author$
 * @ $Revision$
 */
abstract public class InterchangeCSABackOperationBase implements Operation
{
	protected static final PromoterAndPromoClientSessionService promoClientService = new PromoterAndPromoClientSessionService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void execute() throws FrontLogicException, FrontException
	{
		try
		{
			// проверка ограничений
			checkRestrict();
			// отправка запроса
			Document responce = doRequest();
			// обработка ответа, если требуется
			if(responce != null)
				processResponce(responce);
		}
		catch (UserAlreadyRegisteredException e)
		{
			throw new UserAlreadyRegisteredInterruptStageException(ErrorMessages.getMessage(this, e), e);
		}
		catch (WrongRegionException e)
		{
			throw new BlockingRuleException(ErrorMessages.getMessage(this, e), e);
		}
		catch (UserProfileBlockedException e)
		{
			throw new InterruptStageException(ErrorMessages.getMessage(this, e), e);
		}
		catch (RestrictionViolatedException e)
		{
			throw new InterruptStageException(ErrorMessages.getMessage(this, e), e);
		}
		catch (IllegalOperationStateByInvalidCodeException e)
		{
			throw new SmsWasNotConfirmedInterruptStageException(ErrorMessages.getMessage(this, e), e);
		}
		catch (IllegalOperationStateException e)
		{
			throw new InterruptStageException(ErrorMessages.getMessage(this, e), e);
		}
		catch (AuthentificationReguiredException e)
		{
			throw new ResetStageException(ErrorMessages.getMessage(this, e, true), e);
		}
		catch (InvalidCodeConfirmException e)
		{
			throw new com.rssl.auth.csa.front.exceptions.InvalidCodeConfirmException(ErrorMessages.getMessage(this, e.getConfirmStrategyType().name(), e, e.getAttempts()), e.getAttempts());
		}
		catch (IPasUnavailableException e)
		{
			throw new FrontLogicException(ErrorMessages.getMessage(FrontSettingHelper.isRegistrationAllowed(e.getConnectorInfo())?
					("com.rssl.auth.csa.iPas.unavailable.message.with.registration.request." + FrontSettingHelper.getRegistrationAccessType()) :
					"com.rssl.auth.csa.iPas.unavailable.default.message"));
		}
		catch (CardIsNotMainException e)
		{
			throw new CardIsNotMainInterruptStageException(ErrorMessages.getMessage(this, e), e);
		}
		catch (CardIsInvalidException e)
		{
			throw new CardIsNotValidInterruptStageException(ErrorMessages.getMessage(this, e), e);
		}
		catch (BackLogicException e)
		{
			throw new FrontLogicException(ErrorMessages.getMessage(this, e), e);
		}
		catch (BackException e)
		{
			throw new FrontException(e);
		}
		catch (InactiveExternalSystemException e)
		{
			throw new FrontLogicException(e.getMessage());
		}
	}

	/**
	 * Проверка ограничений на выполение операции
	 * @throws FrontLogicException
	 * @throws FrontException
	 */
	protected void checkRestrict() throws FrontLogicException, FrontException
	{
	}

	/**
	 * Отправить запрос в ЦСА Бэк
	 * @return ответ из ЦСА Бэк
	 * @throws BackLogicException
	 * @throws BackException
	 */
	abstract protected Document doRequest() throws BackLogicException, BackException;

	/**
	 * Обработка ответа из ЦСА Бэк
	 * @param responce ответ из ЦСА Бэк
	 * @throws FrontLogicException
	 * @throws FrontException
	 */
	protected void processResponce(Document responce) throws FrontLogicException, FrontException {}

	/**
	 * Логирование входа привлеченного промоутером клиента (используется для отчетов об активности промоутеров)
	 * @param connectorInfo - инфоормация о коннекторе
	 * @param operationOuid - OUID операции клиента
	 */
	protected void updatePromoClientLog(ConnectorInfo connectorInfo, String operationOuid)
	{
		String promoterSessionId = PromoterContext.getShift();
		if (StringHelper.isNotEmpty(promoterSessionId) && (connectorInfo != null || StringHelper.isNotEmpty(operationOuid)))
		{
			try
			{
				promoClientService.addPromoClientLog(connectorInfo != null ? connectorInfo.getGuid() : null,
						promoterSessionId, operationOuid);
			}
			catch (SystemException e)
			{
				log.error("Ошибка записи в журнал входов клиентов промоутера", e);
			}
		}
	}
}
