package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandler;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;

/**
 * @author Kosyakova
 * @ created 16.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class ConvertCurrencyLongOfferPaymentSender extends CODPaymentSenderBase  implements BusinessDocumentHandler
{
   //TODO реализовать buildRequest() после получения формата от разработчиков шлюза к ЦОД и убрать send()

    protected GateMessage buildRequest(PersonData personData, BusinessDocument payment) throws GateException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void send(PersonData personData, BusinessDocument payment) throws BusinessException, GateLogicException
    {
    }

}
