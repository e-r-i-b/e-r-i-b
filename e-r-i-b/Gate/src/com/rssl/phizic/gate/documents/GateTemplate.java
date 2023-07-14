package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.claims.SecuritiesTransferClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.attribute.Attributable;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizic.gate.payments.LoanTransfer;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;

/**
 * Шаблон документа
 *
 * @author khudyakov
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */
public interface GateTemplate extends PossibleAddingOperationUIDObject, Attributable,
		AbstractPhizTransfer, AbstractJurTransfer, AbstractAccountTransfer, AbstractCardTransfer, AccountPaymentSystemPayment, CardPaymentSystemPayment, LoanTransfer, CardsTransfer, SecuritiesTransferClaim
{
	/**
	 * Установить идентификатор шаблона документа
	 * @param id идентификатор шаблона документа
	 */
	void setId(Long id);

	/**
	 * @return информация по шаблону
	 */
	TemplateInfo getTemplateInfo();

	/**
	 * Установить информацию по шаблону
	 * @param templateInfo информация по шаблону
	 */
	void setTemplateInfo(TemplateInfo templateInfo);

	/**
	 * @return владелец/клиент
	 */
	Client getClientOwner() throws GateException;

	/**
	 * Установить владельца
	 * @param client владелец/клиент
	 */
	void setClientOwner(Client client) throws GateException, GateLogicException;

	/**
	 * @return информация о напоминании
	 */
	ReminderInfo getReminderInfo();

	/**
	 * @param reminderInfo информация о напоминании
	 */
	void setReminderInfo(ReminderInfo reminderInfo);
}
