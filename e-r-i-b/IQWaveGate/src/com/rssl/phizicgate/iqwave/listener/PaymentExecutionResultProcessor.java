package com.rssl.phizicgate.iqwave.listener;

import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Krenev
 * @ created 26.05.2010
 * @ $Author$
 * @ $Revision$
 *
 * обработчик ответов на результат исполения
 * Результат исполнения:
 * Элемент	        Тип	        Комментарий	                                    Кратность
 * <AuthorizeCode>	AuthCode    Код авторизации	                                [0-1]
 * <RecAcc>	        AccNumber   Расчетный счет получателя платежа	            [0-1]
 * <RecBic>	        FNC-9       БИК банка получателя платежа	                [0-1]
 * <RecCorrAcc>	    AccNumber   Корреспондентский счет банка получателя платежа	[0-1]
 * <RecInn>	        InnType     ИНН получателя платежа	                        [0-1]
 * <RecCompName>	C-255       Наименование организации получателя платежа	    [0-1]
 * <RecCheque>	    C-?	        Текст чека для распечатки	                    [0-1]
 */
public class PaymentExecutionResultProcessor extends ExecutionResultProcessorBase
{
	public void fillPaymentData(SynchronizableDocument synchronizableDocument, Document bodyContent) throws GateException
	{
		if (synchronizableDocument.getType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("Документ " + synchronizableDocument.getExternalId() + " имеет неверный тип");
		}

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) synchronizableDocument;
		Element element = bodyContent.getDocumentElement();

		payment.setSalesCheck(XmlHelper.getSimpleElementValue(element, Constants.REC_CHEQUE_TAG));
		payment.setAuthorizeCode(XmlHelper.getSimpleElementValue(element, Constants.AUTHORIZE_CODE_FIELD_NAME));

		// сеттим идентификатор операции SVFE
		String operationIdentifier = XmlHelper.getSimpleElementValue(element, Constants.OPERATIOIN_IDENTIFIER);
		if (!StringHelper.isEmpty(operationIdentifier))
			payment.setIdFromPaymentSystem(operationIdentifier);

		String account = XmlHelper.getSimpleElementValue(element, Constants.REC_ACC_TAG);
		if (!StringHelper.isEmpty(account))
			payment.setReceiverAccount(account);

		String inn = XmlHelper.getSimpleElementValue(element, Constants.REC_INN_TAG);
		if (!StringHelper.isEmpty(inn))
			payment.setReceiverINN(inn);

		String bankBIC = XmlHelper.getSimpleElementValue(element, Constants.REC_BIC_TAG);
		String bankAccount = XmlHelper.getSimpleElementValue(element, Constants.REC_CORR_ACC_TAG);
		String bankName = XmlHelper.getSimpleElementValue(element, Constants.REC_COMP_NAME_TAG);

		if (!StringHelper.isEmpty(bankBIC) && !StringHelper.isEmpty(bankAccount) && !StringHelper.isEmpty(bankName))
		{
			payment.getReceiverBank().setBIC(bankBIC);
			payment.getReceiverBank().setAccount(bankAccount);
			payment.getReceiverBank().setName(bankName);
		}
	}
}
