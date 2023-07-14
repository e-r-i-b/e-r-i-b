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
 * ������ ���������
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
	 * ���������� ������������� ������� ���������
	 * @param id ������������� ������� ���������
	 */
	void setId(Long id);

	/**
	 * @return ���������� �� �������
	 */
	TemplateInfo getTemplateInfo();

	/**
	 * ���������� ���������� �� �������
	 * @param templateInfo ���������� �� �������
	 */
	void setTemplateInfo(TemplateInfo templateInfo);

	/**
	 * @return ��������/������
	 */
	Client getClientOwner() throws GateException;

	/**
	 * ���������� ���������
	 * @param client ��������/������
	 */
	void setClientOwner(Client client) throws GateException, GateLogicException;

	/**
	 * @return ���������� � �����������
	 */
	ReminderInfo getReminderInfo();

	/**
	 * @param reminderInfo ���������� � �����������
	 */
	void setReminderInfo(ReminderInfo reminderInfo);
}
