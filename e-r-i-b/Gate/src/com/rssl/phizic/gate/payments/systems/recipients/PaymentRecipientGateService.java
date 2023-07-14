package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * —ервис дл€ работы с получател€ми из внешней системы.
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface PaymentRecipientGateService extends Service
{

	/**
	 * Ќайти получателей в биллинге по счету, Ѕ» у и »ЌЌ
	 * @param account счет получател€
	 * @param bic бик банка получател€
	 * @param inn инн получател€
	 * @param billing биллинг, в котором нуджно найти получател€
	 * @return список получателей удовлетвор€ющих результату. если получтели не найдены - пустой список
	 */
	List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException;

	/**
	 * ѕолучить список персональных получателй(шаблонов) клиента
	 *
	 * @param billingClientId »дентификатор клиента в биллнге.
	 * @param billing биллнг
	 * @return список получателей или пустой спискок, если их нет
	 */
	List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException;

	/**
	 * ѕолучить список значенийе заполененных полей персонального получател€(шаблона)
	 * @param recipient получатель
	 * @param billingClientId идентификатор клиента в биллинге
	 * @return список значений полей или пустой спискок, если их нет
	 */
	List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException;

	/**
	 * ѕолучить дополнительную информацию по получателю.
	 *
	 * @param recipient получатель
	 * @param fields список дополнительных полей
	 * @param debtCode - код задолженности, может быть null
	 * @return дополнительна€ информци€ по получателю
	 * @exception GateException
	 * @exception GateLogicException
	 */
	RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException;

	/**
	 * ѕолучение получател€ платежа по его идентификатору.
	 * GateException - если получатель не найден.
	 *
	 * @param recipientId »дентификатор получател€.
	 * @return получатель платежа
	 * @exception GateException
	 * @exception GateLogicException
	 */
	Recipient getRecipient(String recipientId) throws GateException, GateLogicException;

	/**
	 * ѕолучить список получателей заведенных в биллинге
	 * @param billing биллинг
	 * @return список получателей или пустой спискок, если их нет
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException;

	/**
	 * ѕолучить список доп. полей дл€ получател€
	 * @param recipient получатель
	 * @param keyFields пол€
	 * @return список доп полей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException;

	/**
	 * ѕолучить список ключевых полей дл€ получател€
	 * @param recipient получатель
	 * @throws GateException
	 * @throws GateLogicException
	 * @return список ключевых полей 
	 */
	List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException;

	/**
	 * ѕолучить владельца карты персональных платежей
	 * @param cardId - номер карты персональных платежей
	 * @param billing - биллинг
	 * @return владелец карты персональных платежей
	 * @exception GateException
	 * @exception GateLogicException
	 */
	Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException;
}