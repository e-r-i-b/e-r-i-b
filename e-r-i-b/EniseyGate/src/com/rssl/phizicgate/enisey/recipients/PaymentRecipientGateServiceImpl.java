package com.rssl.phizicgate.enisey.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.enisey.documents.EniseyRequestHelper;
import com.rssl.phizicgate.enisey.messaging.Constants;
import org.w3c.dom.Document;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author mihaylov
 * @ created 12.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class PaymentRecipientGateServiceImpl extends AbstractService implements PaymentRecipientGateService
{
	public PaymentRecipientGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		return null;
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		return null;
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		EniseyRequestHelper requestHepler = new EniseyRequestHelper(getFactory());

		// получаем список доп. полей
		Document response = requestHepler.getRecipientExtendedFieldsDocument(recipient);
		List<Field> listFields = requestHepler.getExtendedFields(response, false);
		Document  formDocument = requestHepler.sendDebtAndValuesRequest(recipient.getSynchKey().toString(), recipient.getService().getCode(), keyFields);

		// проставляем в defaultValue каждого поля значение из документа
		// это делается для отображения значений полей на форме платежа
        try
        {
			for (Field field : listFields)
			{
				CommonField fieldPtr = (CommonField) field;
				String fieldValue = XmlHelper.getElementValueByPath(formDocument.getDocumentElement(),
						"//" + Constants.ATTRIBUTE_TAG + "[" + Constants.NAMEBS_TAG + "='" + fieldPtr.getExternalId() + "']/" + Constants.VALUE_TAG);

				if (!StringHelper.isEmpty(fieldValue))
				{
					fieldPtr.setDefaultValue(fieldValue);
				}
			}
        }
        catch(TransformerException te)
        {
	        throw new GateException(te);
        }

		return listFields;
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		EniseyRequestHelper requestHepler = new EniseyRequestHelper(getFactory());
		Document response = requestHepler.getRecipientExtendedFieldsDocument(recipient);
		return requestHepler.getExtendedFields(response, true);
	}

	public Client getCardOwner(String cardId, Billing billing)
			throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Найти получателей в биллинге по счету, БИКу и ИНН
	 * @param account счет получателя
	 * @param bic бик банка получателя
	 * @param inn инн получателя
	 * @param billing биллинг, в котором нуджно найти получателя
	 * @return список получателей удовлетворяющих результату. если получтели не найдены - пустой список
	 */
	public List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
