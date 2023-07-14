package com.rssl.phizic.logging.monitoring;

/**
 * Типы документов.
 *
 * @author bogdanov
 * @ created 25.02.15
 * @ $Author$
 * @ $Revision$
 */

public enum MonitoringDocumentType
{
	/**
	 * ServiceProviderPayment (платеж поставщику)
	 */
	SPP,
	/**
	 * ServiceProviderAutopayment (автоплатеж поставщику)
	 */
	SPAP,
	/**
	 * ServiceProviderTemplate (шаблон поставщику)
	 */
	SPT,
	/**
	 * ServiceProviderPaymentByTemplate (платеж поставщику по шаблону)
	 */
	SPPBT,
	/**
	 * ServiceProviderPaymentByReminder (платеж поставщику через корзину)
	 */
	SPPBR,

	/**
	 * Карта-Карта Сбербанка
	 */
	CCS,
	/**
	 * Карта-Вклад
	 */
	CA,
	/**
	 * Вклад-Карта
	 */
	AC,
	/**
	 * Вклад-Вклад
	 */
	AA,
	/**
	 * Карта на счет в другом ТБ
	 */
	CAOTB,
	/**
	 * Карта на счет в другом банке
	 */
	CAOB,
	/**
	 * Карта-Карта не Сбербанка (ММС, VMT)
	 */
	CCNS,

	/**
	 * Открытие вклада
	 */
	AOC,
	/**
	 * Открытие вклада через АЛФ.
	 */
	AOC_ALF,
	/**
	 * Заявка на кредит.
	 */
	CREDIT,
	/**
	 * Решения о заявке на кредит.
	 */
	CRD_DCSN,
	/**
	 * Перевод с карты на карту.
	 */
	CARD_TRANSFER,
	/**
	 * Открытие бессрочного вклада без первоначального взноса.
	 */
	TDO,
	/**
	 * Открытие нового вклада и перевод средств с карты клиента
	 */
	TCDO,
	/**
	 * Открытие нового вклада и перевод средств с другого вклада клиента
	 */
	TDDO,
	/**
	 * Открытие нового ОМС и перевод средств со вклада клиента
	 */
	TDIO,
	/**
	 * Открытие ОМС с переводом на него денежных средств с МБК того же клиента
	 */
	TCIO,
	/**
	 * Покупка ОМС за счет средств на карте клиента
	 */
	TCI,
	/**
	 * Продажа ОМС с зачислением средств на карту клиента
	 */
	TIC,
	/**
	 * Перевод с карты на вклад
	 */
	TCD,
	/**
	 * Перевод со вклада на карту
	 */
	TDC,
	/**
	 * Перевод iqwave
	 */
	IQWAVE;

	/**
	 * @return документ - оплата услуг.
	 */
	public boolean isPayment()
	{
		switch (this)
		{
			case SPP:
			case SPAP:
			case SPT:
			case SPPBT:
			case SPPBR:
			case CARD_TRANSFER:
				return true;
			default:
				return false;
		}
	}

	/**
	 * @return внутреняя операция.
	 */
	public boolean isInternal()
	{
		switch (this)
		{
			case CCS:
			case CA:
			case AC:
			case AA:
			case CAOTB:
			case CAOB:
			case CCNS:
				return true;
			default:
				return false;
		}
	}

	/**
	 * @return открытие вклада.
	 */
	public boolean isAccountOpen()
	{
		return this == AOC || this == AOC_ALF;
	}
}
