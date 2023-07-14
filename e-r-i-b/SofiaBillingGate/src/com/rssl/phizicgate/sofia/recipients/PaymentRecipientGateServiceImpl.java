package com.rssl.phizicgate.sofia.recipients;

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
import com.rssl.phizicgate.sofia.documents.SofiaRequestHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author gladishev
 * @ created 06.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceImpl extends AbstractService implements PaymentRecipientGateService
{
	public PaymentRecipientGateServiceImpl(GateFactory factory)
	{
		super(factory);
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

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> keyFields, String debtCode) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		SofiaRequestHelper requestHepler = new SofiaRequestHelper(getFactory());
		Document response = requestHepler.getRecipientFieldsDocument(recipient);
		List<Field> recipientFields = new ArrayList<Field>();

		NodeList attributes = null;
		try
		{
			attributes = XmlHelper.selectNodeList(response.getDocumentElement(), "Attributes/Attribute");
		}
		catch (TransformerException ex)
		{
			throw new GateException(ex);
		}

		String recipientSynchKey = (String) recipient.getSynchKey();
		Document formDocument = requestHepler.sendDebtAndValuesRequest(recipientSynchKey, recipient.getService().getCode(), keyFields);
        try
        {
			for (int i = 0; i < attributes.getLength(); i++)
			{
				Element element = (Element) attributes.item(i);
				CommonField field = (CommonField) requestHepler.parseField(element);
				String fieldValue = XmlHelper.getElementValueByPath(formDocument.getDocumentElement(),
							"//Attribute" + "[NameBS" + "='" + field.getExternalId() + "']/Value");

					if (!StringHelper.isEmpty(fieldValue))
					{
						field.setDefaultValue(fieldValue);
					}
				recipientFields.add(field);
			}
        }
        catch (TransformerException e)
        {
	        throw new GateException(e);
        }

		return recipientFields;
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		SofiaRequestHelper requestHepler = new SofiaRequestHelper(getFactory());
		Document response = requestHepler.getRecipientFieldsDocument(recipient);
		return requestHepler.getExtendedFields(response, true);
	}

	public Client getCardOwner(String cardId, Billing billing)
			throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
