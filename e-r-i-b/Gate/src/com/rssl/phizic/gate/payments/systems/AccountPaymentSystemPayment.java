package com.rssl.phizic.gate.payments.systems;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * @author krenev
 * @ created 02.12.2010
 * @ $Author$
 * @ $Revision$
 * Биллинговый платеж со счета
 */
public interface AccountPaymentSystemPayment extends AbstractPaymentSystemPayment, AbstractAccountTransfer
{
}
