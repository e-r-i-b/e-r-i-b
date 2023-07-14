package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Интерфейс счета.
 * Предназначен для передачи статических (неизменных на протяжении всей жизни счета) данных
 */
public interface Account extends Serializable
{
	/**
	 * Возвращает ID счёта во внешней системе
	 * Domain: ExternalID
	 *
	 * @return ID счёта во внешней системе
	 */
	String getId();

	/**
	 * Возвращает текстовое (краткое) описание типа счёта (Универсальн | До востреб | etc)
	 * Domain: Text
	 *
	 * @return тип счёта
	 */
	String getDescription();

	/**
	 * 
	 * Возвращает текстовое описание типа счёта (Срочный | До востребования | etc)
	 * Domain: Text
	 *
	 * @return тип счёта в виде строки
	 * @deprecated Используйте getDescription()
	 */
	@Deprecated
	String getType();

	/**
	 * Возвращает номер счёта
	 * Domain: AccountNumber
	 *
	 * @return номер счёта
	 */
	String getNumber();

	/**
	 * Возвращает валюту счёта
	 * @return валюта счёта
	 */
	Currency getCurrency();

	/**
	 * Возвращает дату открытия счёта
	 * Domain: Date
	 *
	 * @return дата открытия счёта
	 */
	Calendar getOpenDate();

	/**
     * Возвращает признак допустимости приходных операций по счёту
	 *
     * @return доступность приходных операций по счёту
     */
	public Boolean getCreditAllowed();

	/**
     * Возвращает признак допустимости расходных операций по счёту
	 * 
     * @return доступность расходных операций по счёту
     */
	public Boolean getDebitAllowed();

	/**
     * Возвращает процентную ставку по счёту
     *
     * @return процентная ставка по счёту или null если счёт - текущий
     */
	BigDecimal getInterestRate();

	/**
     * Возвращает остаток
     *
     * @return остаток в валюте счёта
     */
	Money getBalance();

	/**
	 * Возвращает максимальную сумму списания.
	 * Нельзя совершить платеж больше этой суммы.
	 *
	 * @return максимальная сумма списания или null, если ограничений нет
	 */
	Money getMaxSumWrite();

	//TODO: заполняется только из шины. Сделать для v6 и шлюза ЦОД, если возможно. ENH025315
	/**
	 * Возвращает неснижаемый остаток
	 *
	 * @return неснижаемый остаток
	 */
	Money getMinimumBalance();

	/**
	 * Возвращает статус счёта
	 *
	 * @return статус счёта
	 */
	AccountState getAccountState();

	/**
     * Возвращает подразделение, в котором открыт счёт
     *
     * @return подразделение, в котором открыт счёт или null, если нельзя получить эту информацию
     */
    Office getOffice();

	/**
	 * Возвращает вид вклада
	 *
	 * @return вид вклада
	 */
	Long getKind();

	/**
	 * Возвращает подвид вклада
	 * 
	 * @return подвид вклада
	 */
	Long getSubKind();

	/**
	 * Возвращает признак "является ли вклад бессрочным (до востребования)" (от demand deposit)
	 *
	 * @return true - вклад бессрочный (до востребования), false - срочный вклад
	 */
	Boolean getDemand();

	/**
	 * Возвращает признак наличия сбер. книжки по вкладу
	 *
	 * @return true - сберкнижка есть
	 */
	Boolean getPassbook();

	/**
	 * Возвращает номер договора
	 * Domain: Text
	 *
	 * @return номер договора
	 */
	String getAgreementNumber();

	/**
	 * Возвращает дату закрытия счёта
	 * Примечание: В отличие от даты открытия может менятся (появлятся) по этой причине находится тут.
	 * Domain: Date
	 *
	 * @return дата закрытия счёта
	 */
	Calendar getCloseDate();

	/**
	 * Возвращает период вклада
	 *
	 * @return период вклада по договору или null, если вклад до востребования
	 */
	DateSpan getPeriod();

	//TODO: заполняется только из шины. Сделать для v6 и шлюза ЦОД, если возможно. ENH025315
	/**
	 * Возвращает признак "Разрешено ли списание со счета в других ОСБ" (признак «Зеленой улицы»)
	 *
	 * @return разрешено (true), запрещено (false).
	 */
	Boolean getCreditCrossAgencyAllowed();

	//TODO: заполняется только из шины. Сделать для v6 и шлюза ЦОД, если возможно. ENH025315
	/**
	 * Возвращает признак "Разрешено ли зачисление на счет в других ОСБ" (признак «Зеленой улицы»)
	 *
	 * @return разрешено (true), запрещено (false).
	 */
	Boolean getDebitCrossAgencyAllowed();

	/**
	 * Возвращает признак "Возможно ли пролонгирование вклада на следующий срок"
	 *
	 * @return может быть продлен (true), не может быть продлен (false).
	 */
	Boolean getProlongationAllowed();

	//TODO: заполняется только из шины. Сделать для v6 и шлюза ЦОД, если возможно. ENH025315
	/**
	 * Возвращает номер счёта для перечисления процентов
	 *
	 * @return номер счёта для перечисления процентов или null, если счёт текущий
	 */
	String getInterestTransferAccount();

	//TODO: заполняется только из шины. Сделать для v6 и шлюза ЦОД, если возможно. ENH025315
	/**
	 * Возвращает номер карты для перечисления процентов
	 *
	 * @return номер карты для перечисления процентов
	 */
	String getInterestTransferCard();

	/**
	 * Возвращает остатка по вкладу без капитализации
	 *
	 * @return сумма остатка без капитализации, или null
	 */
	BigDecimal getClearBalance();

	/**
	 * Возвращает максимальную сумму вклада
	 *
	 * @return максимальная сумма вклада или null
	 */
	BigDecimal getMaxBalance();

	/**
	 * Возврашает время последней операции
	 * Domain: DateTime
	 *
	 * @return дата-время последней операции
	 */
	Calendar getLastTransactionDate();

	/**
	 * @return сведения о владельце
	 */
	Client getAccountClient();

	/**
	 * Возвращает дату пролонгации
	 * Domain: Date
	 *
	 * @return дата пролонгации
	 */
	Calendar getProlongationDate();

	/**
	 * Возвращает сегмент клиента
	 *
	 * @return сегмент клиента
	 */
	Long getClientKind();
}
