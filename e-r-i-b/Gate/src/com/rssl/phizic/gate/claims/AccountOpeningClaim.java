package com.rssl.phizic.gate.claims;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.payments.AccountClosingPayment;
import com.rssl.phizic.gate.payments.AccountOrIMAOpeningClaimBase;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 03.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * Открытие вклада путем перевода денежных средств со счета
 */
public interface AccountOpeningClaim extends AccountClosingPayment, AccountOrIMAOpeningClaimBase, AccountChangeInterestDestinationClaim
{
	/**
    * Дата окончания срока действия вклада
    *
    * @return дата
    */
	Calendar getClosingDate();

	/**
    * Срок, на который открывается вклад
    *
    * @return срок
    */
	DateSpan getPeriod();

	/**
    * Валюта, в которой открывается вклад
    *
    * @return валюта
    */
	Currency getCurrency();

	/**
    * Вид вклада
    *
    * @return вид вклада
    */
	Long getAccountType();

	/**
    * Подвид вклада
    *
    * @return подвид вклада
    */
	Long getAccountSubType();

	/**
	 * Код группы вкладного продукта
	 *
	 * @return код группы
	 */
	String getAccountGroup();

	/**
	 * Установка кода группы вкладного продукта
	 * @param accountGroup
	 */
	void setAccountGroup(String accountGroup);

	/**
    * Признак операции открытия с закрытием
    *
    * @return true - операция открытия с закрытием
    */
	boolean isWithClose();

	/**
    * Установка номера открытого вклада
    *
    */
	void setReceiverAccount(String accountNumber);

	/**
	 * @return сумма неснижаемого остатка, либо null, если вклад без неснижаемого остатка
	 */
	BigDecimal getIrreducibleAmmount();

	/**
	 * Порядок уплаты процентов (на карту "card" на счет вклада "account")
	 * @return
	 */
	String getPercentTransferSource();

	/**
	 * Код карты для перечисления процентов по вкладу, если порядок уплаты процентов "на карту"
	 * @return
	 */
	String getPercentTransferCardSource();

	/**
	 *  Тербанк в котором нужно открыть счет
	 * @return
	 */
	String getAccountTb();

	/**
	 * Установка тербанка, в котором в результате был открыт вклад
	 * @param tb
	 */
	void setAccountTb(String tb);

	/**
	 * Филиал банка, в котором нужно открыть вклад
	 * @return
	 */
	String getAccountOsb();

	/**
	 * Установка филиала банка, в котором в результате был открыт вклад
	 * @param osb
	 */
	void setAccountOsb(String osb);

	/**
	 * Отделение филиала банка, в котором нужно открыть вклад
	 * @return
	 */
	String getAccountVsp();

	/**
	 * Установка отделения филиала банка, в котором в результате был открыт вклад
	 * @param vsp
	 */
	void setAccountVsp(String vsp);

	/**
	 * Сообщение об ошибке при открытии вклада
	 * @return
	 */
	String getClaimErrorMsg();

	/**
	 * Установка сообщения об ошибке при формировании запроса в шину
	 * или при получении ответа из шины на открытие вклада
	 * @param claimErrorMsg
	 */
	void setClaimErrorMsg(String claimErrorMsg);

	/**
	 * @return - ИД промоутера или клиентского менеджера
	 */
	String getCuratorId();

	/**
	 * @return - Тип "КМ" или "Промоутер"
	 */
	String getCuratorType();

	/**
	 * @return - ТБ менеджера или промоутера
	 */
	String getCuratorTb();

	/**
	 * Установка ТБ менеджера или промоутера
	 * @param curatorTb
	 */
	void setCuratorTb(String curatorTb);

	/**
	 * @return - ОСБ менеджера или промоутера
	 */
	String getCuratorOsb();

	/**
	 * Установка ОСБ клиентского менеджера или промоутера
	 * @param curatorOsb
	 */
	void setCuratorOsb(String curatorOsb);

	/**
	 * @return - ВСП менеджера или промоутера
	 */
	String getCuratorVsp();

	/**
	 * Установка ВСП клиентского менеджера или промоутера
	 * @param curatorVsp
	 */
	void setCuratorVsp(String curatorVsp);

    void setAtmPlace(String atmPlace);
    String getAtmPlace();

    void setAtmTB(String atmTB);
    String getAtmTB();


    void setAtmOSB(String atmOSB);
    String getAtmOSB();


    void setAtmVSP(String atmOSB);
    String getAtmVSP();

	boolean isPension();

	/**
	 * Сегмент клиента (признак промоставок)
	 * @param segment
	 */
	void setSegment(String segment);
	String getSegment();

	boolean isUsePromoRate();

	/**
	 * @return документ помеченный как главный
	 */
	ClientDocument getMainDocument();
}
