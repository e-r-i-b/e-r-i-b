package com.rssl.phizic.business.ermb.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Хендлер, проверяющий достаточность реквизитов в многошаговом платеже для смс-канала ЕРМБ
 * @author Rtischeva
 * @created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ErmbBillingPaymentRequisitesSufficientHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (!(document instanceof GateExecutableDocument))
			{
				throw new BusinessException("Неверный тип объекта " + document.getClass().getName());
			}
			GateDocument gateDocument = ((GateExecutableDocument) document).asGateDocument();

			if (!AbstractPaymentSystemPayment.class.isAssignableFrom(gateDocument.getType()))
				throw new BusinessException("Неверный тип платежа - ожидается наслежник AbstractPaymentSystemPayment");

			AbstractPaymentSystemPayment paymentSystemPayment = (AbstractPaymentSystemPayment) gateDocument;

			if (StringHelper.isEmpty(paymentSystemPayment.getIdFromPaymentSystem()))
			{
				if (paymentSystemPayment instanceof JurPayment)
				{
					MessageBuilder messageBuilder = new MessageBuilder();
					String message = null;

					JurPayment jurPayment = (JurPayment) paymentSystemPayment;
					if (jurPayment.isByTemplate())
					{
						message = messageBuilder.buildNotSufficientBillingPaymentByTemplateRequisitesMessage(jurPayment);
						throw new DocumentLogicException(message);
					}
					else
					{
						List<String> requiredFields = new ArrayList<String>();
						List<String> errorFields = new ArrayList<String>();

						List<Field> extendedFields = paymentSystemPayment.getExtendedFields();
						for(Field extendedField : extendedFields)
						{
							if (extendedField.isEditable() && extendedField.isVisible() && extendedField.isRequired())
							{
								if (StringHelper.isEmpty((String) extendedField.getValue()))
									requiredFields.add(extendedField.getName());
								else
								{
									//проверка для полей главной суммы, что в значении не ноль
									if (extendedField.isMainSum())
									{
										BigDecimal sum = new BigDecimal((String)extendedField.getValue());
										if (BigDecimal.ZERO.compareTo(sum) == 0)
											requiredFields.add(extendedField.getName());
									}
								}
							}

							if (!StringHelper.isEmpty(extendedField.getError()))
							{
								errorFields.add(extendedField.getName());
								extendedField.setValue(null);
							}
						}
						paymentSystemPayment.setExtendedFields(extendedFields);

						//отправляем смс о недостаточности реквизитов
						if (!requiredFields.isEmpty())
						{
							message = messageBuilder.buildNotSufficientBillingPaymentRequisitesMessage(jurPayment.getReceiverSmsAlias(), requiredFields, extendedFields).getText();
						}
						else if (!errorFields.isEmpty())
						{
							message = messageBuilder.buildNotSufficientBillingPaymentRequisitesMessage(jurPayment.getReceiverSmsAlias(), errorFields, extendedFields).getText();
						}
					}

					if (StringHelper.isNotEmpty(message))
					{
                        ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
                        SmsTransportService smsTransportService = MessagingSingleton.getInstance().getErmbSmsTransportService();
                        smsTransportService.sendSms(person.getErmbProfile().getMainPhoneNumber(), message, message, TextMessage.DEFAULT_PRIORITY);
					}
				}
			}
		}

		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		catch (IKFLMessagingException e)
		{
			throw new InternalErrorException(e);
		}
	}
}
