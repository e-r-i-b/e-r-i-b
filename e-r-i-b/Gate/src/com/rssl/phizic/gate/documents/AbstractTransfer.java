package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author khudyakov
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$

 * Абстрактный перевод со счета или карты клиента куда либо
 * В переводе должна быть задана либо ChargeOffAmount, либо DestinationAmount,
 * но не оба вместе, и курсы при наличии.
 *
 * По умолчанию если в описании конкретного интерфейса не указано возможен
 * только способ взымания комиссии со счета списания (getChargeOffAccount)
 */
public interface AbstractTransfer extends SynchronizableDocument, Serializable
{
	/**
	 * Сумма списания (сумма платежа) без учета комиссии.
	 * Конкретные платежи могут налагать ограничения на размер и валюту.
	 *
	 * @return сумма платежа.
	 */
	Money getChargeOffAmount();

	/**
	 * Установить сумму списания(в том случае, если она изменилась в процессе исполнения платежа)
	 * @param amount сумма списания
	 */
	void setChargeOffAmount(Money amount);

	/**
	 * Сумма зачисления. (без комиссии)
	 *
	 * @return сумма зачисления.
	 */
	Money getDestinationAmount();

	/**
	 * Установить сумму зачисления(в том случае, если она изменилась в процессе исполнения платежа)
	 * @param amount сумма зачисления
	 */
	void setDestinationAmount(Money amount);

	/**
	 * Возващает тип  введенной суммы: сумма списания или сумма зачисления
	 * @return
	 */
	InputSumType getInputSumType();

	/**
	 * @return Курс продажи банком клиенту для валюты списания
	 */
	CurrencyRate getDebetSaleRate();

	/**
	 * @return Курс продажи банком клиенту для валюты списания
	 */
	CurrencyRate getDebetBuyRate();

	/**
	 * @return Курс продажи банком клиенту для валюты зачисления
	 */
	CurrencyRate getCreditSaleRate();

	/**
	 * @return Курс покупки банком у клиента для валюты зачисления
	 */
	CurrencyRate getCreditBuyRate();

	/**
	 * @return Курс конверсии из валюты списания в валюту зачисления
	 */
	BigDecimal getConvertionRate();

	/**
	 * @return Код операции
	 */
	String getOperationCode();

	/**
	 * @return Основание/назначение платежа (при отсутствии возвращаем null)
	 */
	String getGround();

	/**
	 * установить значение основания платежа
	 * @param ground основание
	 */
	void setGround(String ground);

	/**
	 * Имя плательщика ИВАНОВ ИВАН ИВАНОВИЧ
	 * @return - фио
	 */
	String getPayerName();

	/**
	 * тип тарифного плана, по которому пришла комиссия
	 * @param tariffPlanESB - тип тарифного плана
	 */
	void setTariffPlanESB(String tariffPlanESB);

}
