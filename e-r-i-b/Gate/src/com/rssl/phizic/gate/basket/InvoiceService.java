package com.rssl.phizic.gate.basket;

import com.rssl.phizic.common.types.basket.InvoiceState;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import com.rssl.phizic.gate.payments.longoffer.DelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.RecoveryCardPaymentSystemPaymentLongOffer;

import java.util.Calendar;
import java.util.List;

/**
 * @author vagin
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 * Сервис работы с инвойсами(счета к оплате).
 */
public interface InvoiceService extends Service
{
	/**
	 * Доабвление нового инвойса.
	 * @param invoice - новый инвойс.
	 * @param operUID - идентификатор заявки на подписку.
	 * @throws GateException
	 */
	void addInvoice(GateInvoice invoice, String operUID) throws GateException;

	/**
	 * Заменить все существующие инвойсы по подписке новым.
	 * @param invoice - новый инвойс.
	 * @param operUID - внутренний идентификатор заявки на подписку. OperUID в теле сообщения.
	 * @throws GateException
	 */
	void updateInvoice(GateInvoice invoice, String operUID) throws GateException;

	/**
	 * Удалить все задолженности в статусе New в рамках подписки на получение инвойсов.
	 * @param operUID - идентификатор заявки на подписку.
	 * @param autoSubscriptionId - внешний идентификатор подписки.
	 * @throws GateException
	 */
	void deleteInvoice(String operUID, String autoSubscriptionId) throws GateException;

	/**
	 * Обнвление статуса платежа-акцепта оплаты задолжености.
	 * @param externalPaymentId - идентификатор платежа в АС "AutoPay"(PaymentId)
	 * @param state - Код состояния платежа
	 * @param nonExecReasonDesc - причина для случая "Отказан"
	 * @param nonExecReasonCode - код причины неисполнения платежа. Присутствует только для платежей со статусом Отменен. (PaymentStatus = Canceled)
	 * @param execPaymentDate - дата получения задолжености.
	 * @param operUID - идентификатор заявки на подписку.
	 */
	void updatePaymentState(String externalPaymentId, InvoiceState state, String nonExecReasonCode, String nonExecReasonDesc, Calendar execPaymentDate, String operUID) throws GateException, GateLogicException;

	/**
	 * Обновление состояния платежа-акцепта задолжености по квитанции о принятии внешней системой(АС "AutoPay").
	 * @param externalId - externalId платежа(RqUID акцепта оплаты)
	 * @param statusCode - код ответа
	 * @param nonExecReasonDesc - описание статуса ответа.
	 */
	void updatePaymentStatus(String externalId, Long statusCode, String nonExecReasonDesc) throws GateException, GateLogicException;

	/**
	 * Активизировать подписку.
	 * @param autoSubscriptionId - внешний идентификатор
	 * @param UID - идентификатор заявки.
	 */
	void activateInvoiceSubscription(String autoSubscriptionId, String UID) throws GateException, GateLogicException;

	/**
	 * Создание подписки по заявке на приостановку автоплатежа
	 * @param claim заявка на приостановку автоплатежа
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void createInvoiceSubscription(DelayCardPaymentSystemPaymentLongOffer claim) throws GateException, GateLogicException;

	/**
	 * Возобновление работы автоплатежа связанного в автопоиском(по причине закрытия автопоиска)
	 * @param claim заявка на возобновление автоплатежа
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void recoverAutoSubscription(CloseInvoiceSubscription claim) throws GateException, GateLogicException;
}
