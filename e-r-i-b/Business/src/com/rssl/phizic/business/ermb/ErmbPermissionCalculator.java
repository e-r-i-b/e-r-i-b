package com.rssl.phizic.business.ermb;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;

/**
 * Расчет прав доступа к операциям мобильного банка
 * @author Puzikov
 * @ created 30.07.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbPermissionCalculator
{
	private final ErmbTariff tariff;
	private final boolean paymentBlock;

	/**
	 * ctor
	 * @param profile профиль ЕРМБ
	 */
	public ErmbPermissionCalculator(ErmbProfileImpl profile)
	{
		this.tariff = profile.getTarif();
		this.paymentBlock = profile.isPaymentBlocked();
	}

	/**
	 * ctor
	 * @param profile профиль ЕРМБ
	 * @param tariff тариф
	 */
	public ErmbPermissionCalculator(ErmbProfileImpl profile, ErmbTariff tariff)
	{
		this.tariff = tariff;
		this.paymentBlock = profile.isPaymentBlocked();
	}

	/**
	 * @return Уведомление о расходных/приходных операциях, авторизациях по счету карты.
	 */
	public boolean impliesCardNotification()
	{
		return impliesOperation(tariff.getNoticeConsIncomCardOperation());
	}

	/**
	 * @return Уведомление о расходных/приходных операциях по счету вклада.
	 */
	public boolean impliesAccountNotification()
	{
		return impliesOperation(tariff.getNoticeConsIncomAccountOperation());
	}

	/**
	 * !!! В отличие от других прав настраивается не тарифом, а настройкой
	 *
	 * @return Уведомление по кредитам
	 */
	public boolean impliesLoanNotification()
	{
		return ConfigFactory.getConfig(ErmbConfig.class).getLoanNotificationAvailability();
	}

	/**
	 * @return Предоставление по запросу клиента информации по карте.
	 */
	public boolean impliesCardInfoOperation()
	{
		return impliesOperation(tariff.getCardInfoOperation());
	}

	/**
	 * @return Предоставление по запросу клиента информации по счету.
	 */
	public boolean impliesAccountInfoOperation()
	{
		return impliesOperation(tariff.getAccountInfoOperation());
	}

	/**
	 * @return Предоставление по запросу клиента краткой истории по карте (мини-выписка)
	 */
	public boolean impliesCardHistoryOperation()
	{
		return impliesOperation(tariff.getCardMiniInfoOperation());
	}

	/**
	 * @return Предоставление по запросу клиента краткой истории по счету (мини-выписка).
	 */
	public boolean impliesAccountHistoryOperation()
	{
		return impliesOperation(tariff.getAccountMiniInfoOperation());
	}

	/**
	 * @return Блокировка/запрос на перевыпуск карты по запросу клиента.
	 */
	public boolean impliesReIssueCardOperation()
	{
		return impliesOperation(tariff.getReIssueCardOperation());
	}

	/**
	 * @return Платежи в пользу организаций (оплата услуг).
	 */
	public boolean impliesJurPaymentOperation()
	{
		return impliesOperation(tariff.getJurPaymentOperation());
	}

	/**
	 * @return Переводы в пользу третьих лиц (по шаблону).
	 */
	public boolean impliesTransfersToThirdPartiesOperation()
	{
		return impliesOperation(tariff.getTransfersToThirdPartiesOperation());
	}

	private boolean impliesOperation(ErmbOperationStatus operation)
	{
		switch (operation)
		{
			case PROVIDED:
				return true;
			case NOT_PROVIDED:
				return false;
			case NOT_PROVIDED_WHEN_NO_PAY:
				return !paymentBlock;
			default:
				throw new IllegalArgumentException("Неизвестные права операции ЕРМБ");
		}
	}
}
