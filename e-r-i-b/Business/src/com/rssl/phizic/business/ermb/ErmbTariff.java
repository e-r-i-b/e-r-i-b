package com.rssl.phizic.business.ermb;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.StringHelper;

/**
 * User: Moshenko
 * Date: 02.10.12
 * Time: 19:04
 * Сущность тариф ЕРМБ
 */
public class ErmbTariff
{
	private Long id;
	private String name;
	private Money connectionCost;
	private int chargePeriod = 1;
	private int gracePeriod = 0;

	private Money gracePeriodCost;
	private Money graceClass;
	private Money premiumClass;
	private Money socialClass;
	private Money standardClass;

	private ErmbOperationStatus noticeConsIncomCardOperation;
	private ErmbOperationStatus noticeConsIncomAccountOperation;
	private ErmbOperationStatus cardInfoOperation;
	private ErmbOperationStatus accountInfoOperation;
	private ErmbOperationStatus cardMiniInfoOperation;
	private ErmbOperationStatus accountMiniInfoOperation;
	private ErmbOperationStatus reIssueCardOperation;
	private ErmbOperationStatus jurPaymentOperation;
	private ErmbOperationStatus transfersToThirdPartiesOperation;
	private String code; //код типа тарифа(full, saving)
	private String description;

	private ErmbTariffStatus tariffStatus = ErmbTariffStatus.ACTIVE;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	/**
	 * @return наименование тарифа
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return плата за подключение
	 */
	public Money getConnectionCost()
	{
		return connectionCost;
	}

	public void setConnectionCost(Money connectionCost)
	{
		this.connectionCost = connectionCost;
	}

	/**
	 * @return Период взимания абонентской платы.
	 * Количество месяцев, за которые берется абонентская плата.
	 * По умолчанию - 1
	 */
	public int getChargePeriod()
	{
		return chargePeriod;
	}

	public void setChargePeriod(int chargePeriod)
	{
		this.chargePeriod = chargePeriod;
	}

	/**
	 * @return Льготный период.
	 * Размер льготного периода. Указывается количество календарных месяцев.
	 * По умолчанию - 0
	 */
	public int getGracePeriod()
	{
		return gracePeriod;
	}

	public void setGracePeriod(int gracePeriod)
	{
		this.gracePeriod = gracePeriod;
	}

	/**
	 * @return Размер платы в льготный период
	 */
	public Money getGracePeriodCost()
	{
		return gracePeriodCost;
	}

	public void setGracePeriodCost(Money gracePeriodCost)
	{
		this.gracePeriodCost = gracePeriodCost;
	}

	/**
	 * @return Абонентская плата. Класс продукта. Льготный.
	 */
	public Money getGraceClass()
	{
		return graceClass;
	}

	public void setGraceClass(Money graceClass)
	{
		this.graceClass = graceClass;
	}

	/**
	 * @return Абонентская плата. Класс продукта. Льготный. Премиум
	 */
	public Money getPremiumClass()
	{
		return premiumClass;
	}

	public void setPremiumClass(Money premiumClass)
	{
		this.premiumClass = premiumClass;
	}

	/**
	 * @return  Абонентская плата. Класс продукта. Льготный. Социальный
	 */
	public Money getSocialClass()
	{
		return socialClass;
	}

	public void setSocialClass(Money socialClass)
	{
		this.socialClass = socialClass;
	}

	/**
	 * @return Абонентская плата. Класс продукта. Льготный. Стандарт
	 */
	public Money getStandardClass()
	{
		return standardClass;
	}

	public void setStandardClass(Money standardClass)
	{
		this.standardClass = standardClass;
	}

	/**
	 * @return Предоставление по запросу клиента информации по счету
	 */
	public ErmbOperationStatus getAccountInfoOperation()
	{
		return accountInfoOperation;
	}

	public void setAccountInfoOperation(ErmbOperationStatus accountInfoOperation)
	{
		this.accountInfoOperation = accountInfoOperation;
	}

	/**
	 * @return Предоставление по запросу клиента краткой истории по счету (мини-выписка)
	 */
	public ErmbOperationStatus getAccountMiniInfoOperation()
	{
		return accountMiniInfoOperation;
	}

	public void setAccountMiniInfoOperation(ErmbOperationStatus accountMiniInfoOperation)
	{
		this.accountMiniInfoOperation = accountMiniInfoOperation;
	}

	/**
	 * @return Предоставление по запросу клиента информации по карте
	 */
	public ErmbOperationStatus getCardInfoOperation()
	{
		return cardInfoOperation;
	}

	public void setCardInfoOperation(ErmbOperationStatus cardInfoOperation)
	{
		this.cardInfoOperation = cardInfoOperation;
	}

	/**
	 * @return Предоставление по запросу клиента краткой истории по карте (мини-выписка)
	 */
	public ErmbOperationStatus getCardMiniInfoOperation()
	{
		return cardMiniInfoOperation;
	}

	public void setCardMiniInfoOperation(ErmbOperationStatus cardMiniInfoOperation)
	{
		this.cardMiniInfoOperation = cardMiniInfoOperation;
	}

	/**
	 * @return Платежи в пользу организаций (оплата услуг)
	 */
	public ErmbOperationStatus getJurPaymentOperation()
	{
		return jurPaymentOperation;
	}

	public void setJurPaymentOperation(ErmbOperationStatus jurPaymentOperation)
	{
		this.jurPaymentOperation = jurPaymentOperation;
	}

	/**
	 * @return Уведомление о расходных/приходных операциях по счету вклада
	 */
	public ErmbOperationStatus getNoticeConsIncomAccountOperation()
	{
		return noticeConsIncomAccountOperation;
	}

	public void setNoticeConsIncomAccountOperation(ErmbOperationStatus noticeConsIncomAccountOperation)
	{
		this.noticeConsIncomAccountOperation = noticeConsIncomAccountOperation;
	}

	/**
	 * @return Уведомление о  расходных/приходных операциях, авторизациях по счету карты
	 */
	public ErmbOperationStatus getNoticeConsIncomCardOperation()
	{
		return noticeConsIncomCardOperation;
	}

	public void setNoticeConsIncomCardOperation(ErmbOperationStatus noticeConsIncomCardOperation)
	{
		this.noticeConsIncomCardOperation = noticeConsIncomCardOperation;
	}

	/**
	 * @return Блокировка/запрос на перевыпуск карты по запросу клиента
	 */
	public ErmbOperationStatus getReIssueCardOperation()
	{
		return reIssueCardOperation;
	}

	public void setReIssueCardOperation(ErmbOperationStatus reIssueCardOperation)
	{
		this.reIssueCardOperation = reIssueCardOperation;
	}

	/**
	 * @return Переводы в пользу третьих лиц (по шаблону)
	 */
	public ErmbOperationStatus getTransfersToThirdPartiesOperation()
	{
		return transfersToThirdPartiesOperation;
	}

	public void setTransfersToThirdPartiesOperation(ErmbOperationStatus transfersToThirdPartiesOperation)
	{
		this.transfersToThirdPartiesOperation = transfersToThirdPartiesOperation;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return Описание тарифа.
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return Статус тарифа
	 */
	public ErmbTariffStatus getTariffStatus()
	{
		return tariffStatus;
	}

	public void setTariffStatus(ErmbTariffStatus tariffStatus)
	{
		this.tariffStatus = tariffStatus;
	}

	/**
	 * Получить стоимость абонентской платы в зависимости от класса продуктов клиента
	 * @param ermbProductClass класс продуктов клиента
	 * @return абонентская плата
	 */
	public Money getCost(ErmbProductClass ermbProductClass)
	{
		switch (ermbProductClass)
		{
			case preferential:
				return getGraceClass();
			case premium:
				return getPremiumClass();
			case social:
				return getSocialClass();
			case standart:
				return getStandardClass();
			default:
				throw new IllegalArgumentException("Неизвестный класс продуктов клиента");
		}
	}

    public String toString ()
    {
        return name;
    }

    public int hashCode ()
    {
        return code.hashCode();
    }

    public boolean equals (Object o)
    {
        if (this == o) return true;
        if (o == null || !(o instanceof ErmbTariff)) return false;

        final ErmbTariff that = (ErmbTariff) o;

        if (!StringHelper.equalsNullIgnore(name, that.name))
            return false;

        return true;
    }
}
