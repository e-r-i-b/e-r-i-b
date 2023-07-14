package com.rssl.phizic.gate.payments.systems;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.payments.AbstractBillingPayment;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

import java.util.List;

/**
 * Платеж через платежную систему
 * Для платежей с переменным числом параметров.
 *
 * @author Krenev
 * @ created 23.11.2007
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractPaymentSystemPayment extends AbstractJurTransfer, AbstractBillingPayment
{

	/**
	 * @return код биллинга, в который отправляется платеж
	 */
	String getBillingCode();

	/**
	 * Установить код биллинга
	 * @param billingCode код биллинга
	 */
	void setBillingCode(String billingCode);

	/**
	 * Получить идентификатор клиента в биллинге.
	 * Наличие данного поля в платеже позволит биллингу не запрашивать часть полей или заполнять их
	 * значениями уже извесными  биллингу. например, лицевой счет, фио, показания счетчиков и пр.
	 * @return идентифкатор клиента в биллинге. может отсутсвовать. в данном случае возвращать null.
	 */
	String getBillingClientId();

	/**
	 * Описание услуги в БС
	 *
	 * @return услуга
	 */
	Service getService();

	/**
	 * установить описмние услуги в БС
	 *
	 * @return услуга
	 */
	void setService(Service service);

	/**
	 * @return список дополнительных полей платежа
	 */
	//TODO бросать GateException
	List<Field> getExtendedFields() throws DocumentException;

	/**
	 * Установить значение номера поставщика
	 * @param receiverAccount номер поставщика
	 */
	void setReceiverAccount(String receiverAccount);

	/**
	 * @return транзитный счет получателя
	 */
	public String getReceiverTransitAccount();

	/**
	 * Установить транзитный счет получателя
	 * @param receiverTransitAccount транзитный счет получателя
	 */
	void setReceiverTransitAccount(String receiverTransitAccount);

	/**
	 * Установить ИНН получателя
	 * @param receiverINN ИНН получателя
	 */
	void setReceiverINN(String receiverINN);

	/**
	 * Установить КПП получателя
	 * @param receiverKPP КПП получателя
	 */
	void setReceiverKPP(String receiverKPP);

	/**
	 * Установить банк получателя
	 * @param receiverBank Банк получателя
	 */
	void setReceiverBank(ResidentBank receiverBank);

	/**
     * @return транзитный банк получателя
     */
	ResidentBank getReceiverTransitBank();

	/**
	 * @return телефон получателя для обращения клиентов
	 */
	String getReceiverPhone();

	/**
	 * Установить телефон получателя для обращения клиентов
	 * @param receiverPhone телефон получателя
	 */
	void setReceiverPhone(String receiverPhone);

	/**
	 * @return имя получателя для печати в чеке
	 */
	String getReceiverNameForBill();

	/**
	 * Установить имя получателя для печати в чеке
	 * @param receiverNameForBill имя получателя для печати в чеке
	 */
	void setReceiverNameForBill(String receiverNameForBill);

	/**
	 * @return признак необязательности предъявления банковских реквизитов получателя пользователю.
	 */
	boolean isNotVisibleBankDetails();

	/**
	 * Установить признак необязательности предъявления банковских реквизитов получателя пользователю.
	 * @param notVisibleBankDetails признак необязательности предъявления банковских реквизитов получателя пользователю.
	 */
	void setNotVisibleBankDetails(boolean notVisibleBankDetails);

	/**
     * @return код офиса, в котором заключен договор с получателем.
     */
	Code getReceiverOfficeCode();

    /**
     * Установить код офиса, в котором заключен договор с получателем.
     * @param code код офиса, в котором заключен договор с получателем.
     */
	void setReceiverOfficeCode(Code code);

	/**
	 * Установить дополнительные поля
	 *
	 * @param extendedFields упорядоченный список доп полей.
	 */

	void setExtendedFields(List<Field> extendedFields) throws DocumentException;

	/**
	 * получить идентификатор платежа во внешней системе
	 * @return идентификатор платежа во внешней системе
	 */
	String getIdFromPaymentSystem();

	/**
	 * установить идентификатор платежа во внешней системе
	 * @param id идентфикатор платежа во внешней системе
	 */
	void setIdFromPaymentSystem(String id);

	/**
	 * Установить код точки получателя.
	 * @param receiverPointCode код точки получателя.
	 */
	void setReceiverPointCode(String receiverPointCode);

	/**
	 * Получение текста чека
	 * @return текст чека
	 */
	String getSalesCheck();

	/**
	 * Установить текст чека
	 * @param salesCheck текст чека
	 */
	void setSalesCheck(String salesCheck);
}