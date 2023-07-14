package com.rssl.phizic.gate.payments.autopayment;

import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.payments.AbstractBillingPayment;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 * Заявка создания автоплатежа
 */
public interface CreateAutoPayment extends AutoPayment, AbstractBillingPayment, AbstractCardTransfer
{
}
