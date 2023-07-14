package com.rssl.phizicgate.asbc.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.RecipientInfoImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.asbc.documents.ASBCRequestHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author gladishev
 * @ created 17.05.2010
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

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		if (fields == null || fields.size() != 1)
		{
			throw new GateException("Должно быть ровно одно идентифицирующее поле");
		}
		ASBCRequestHelper requestHelper = new ASBCRequestHelper(getFactory());
		Document response = requestHelper.makeFindCreditsRequest((String) recipient.getSynchKey(), fields.get(0));
		RecipientInfoImpl recipientInfo = new RecipientInfoImpl();
		Element resultElement;

		String xpath = "//result/result_element";
		if (!StringHelper.isEmpty(debtCode))
		{
		   xpath+="[creditId='"+debtCode+"']";
		}
		try
		{
			resultElement = XmlHelper.selectSingleNode(response.getDocumentElement(), xpath);
		}
		catch (TransformerException ex)
		{
			throw new GateException(ex);
		}
		if (resultElement == null)
		{
			throw new GateException("Не найдена информация о поставщике для выбранной задолженности "+debtCode);
		}
		recipientInfo.setINN(XmlHelper.getSimpleElementValue(resultElement, "inn"));
		recipientInfo.setKPP(XmlHelper.getSimpleElementValue(resultElement, "kpp"));
		recipientInfo.setAccount(XmlHelper.getSimpleElementValue(resultElement, "settlementAccount"));

		ResidentBank bank = new ResidentBank();
		bank.setAccount(XmlHelper.getSimpleElementValue(resultElement, "correspondentAccount"));
		bank.setBIC(XmlHelper.getSimpleElementValue(resultElement, "bic"));
		recipientInfo.setBank(bank);

		return recipientInfo;
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
		throw new UnsupportedOperationException();
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Client getCardOwner(String cardId, Billing billing)
			throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
