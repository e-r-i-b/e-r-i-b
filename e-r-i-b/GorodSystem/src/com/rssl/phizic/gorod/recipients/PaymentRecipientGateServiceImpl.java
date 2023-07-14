package com.rssl.phizic.gorod.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.RecipientInfoImpl;
import com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.gorod.GorodRequestHelper;
import com.rssl.phizic.gorod.client.AddressImpl;
import com.rssl.phizic.gorod.client.ClientImpl;
import com.rssl.phizic.gorod.messaging.Constants;
import com.rssl.phizic.gorod.messaging.GorodConfigImpl;
import com.rssl.phizic.gorod.messaging.XMLMessagingService;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Gainanov
 * @ created 15.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceImpl extends AbstractService implements PaymentRecipientGateService
{
	private static final String ADD_SERVICE_PREFIX = "AddService";

	public PaymentRecipientGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		XMLMessagingService service = XMLMessagingService.getInstance(getFactory());
		GateMessage msg = service.createRequest("ReqXObject");
		Document doc = msg.getDocument();
		Element root = doc.getDocumentElement();
		Element xObject = doc.createElement("XObject");
		xObject.setAttribute("id", recipientId);
		root.appendChild(xObject);
		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);
		Element moreInfo = doc.createElement("moreInfo");
		moreInfo.setAttribute("request", "true");
		moreInfo.setAttribute("maxItems", "all");
		moreInfo.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/Service/");
		moreInfo.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo);
		Element moreInfo1 = (Element) moreInfo.cloneNode(false);
		moreInfo1.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/Service/Provider/Org");
		listOfMoreInfo.appendChild(moreInfo1);
		Element moreInfo2 = (Element) moreInfo1.cloneNode(false);
		moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/AddService/Service/");
		listOfMoreInfo.appendChild(moreInfo2);
		Element moreInfo3 = (Element) moreInfo2.cloneNode(false);
		moreInfo3.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/AddService/Service/Provider/Org");
		listOfMoreInfo.appendChild(moreInfo3);
		Document response = service.sendOnlineMessage(msg, null);
		com.rssl.phizgate.common.routable.RecipientImpl recipient = new com.rssl.phizgate.common.routable.RecipientImpl();
		try
		{
			Element node = XmlHelper.selectSingleNode(response.getDocumentElement(), "XObject");
			if(node != null)
			{
				//TODO разобраться с кодом сервиса в задаче доработка биллинговых шлюзов.
				recipient.setService(new ServiceImpl("ServiceCode",XmlHelper.selectSingleNode(node, "Service").getAttribute("name")));
				recipient.setSynchKey(node.getAttribute("id"));
				recipient.setName(XmlHelper.selectSingleNode(node, "Service/Provider/Org").getAttribute("name"));
				recipient.setMain(true);
			}
			else
			{
				//TODO разобраться с кодом сервиса в задаче доработка биллинговых шлюзов.
				node = XmlHelper.selectSingleNode(response.getDocumentElement(), "XObject/AddService");
				recipient.setService(new ServiceImpl("ServiceCode",XmlHelper.selectSingleNode(node, "Service").getAttribute("name")));
				recipient.setSynchKey(node.getAttribute("id"));
				recipient.setName(XmlHelper.selectSingleNode(node, "Service/Provider/Org").getAttribute("name"));
				recipient.setMain(false);
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}

		return recipient;
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		GorodConfigImpl config = ConfigFactory.getConfig(GorodConfigImpl.class);
		return getPersonalRecipientList(config.getClientPan(), billing);
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		String codeService =  recipient.getService().getCode();
		return getRecipientFields(codeService, keyFields);
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		List<Field> fields = new ArrayList<Field>();
		String codeService = recipient.getService().getCode();

		GorodRequestHelper requestHelper = new GorodRequestHelper(getFactory());
		boolean mainService = requestHelper.isMainService(codeService);
		if (!mainService)
			return fields;

		//запрашиваем из исппн Город заполненные значениями по умолчанию доп. поля по платежу
		List<Field> gateFields = requestHelper.getExtendedFields(codeService, mainService);
		CommonField licAccountField = (CommonField) requestHelper.getLicAccountField(gateFields);

		if (licAccountField == null)
		{
			licAccountField = requestHelper.createLicAccountField();
			licAccountField.setMaxLength(Constants.FIELD_MAX_LENGTH);
			licAccountField.setMinLength(Constants.FIELD_MIN_LENGTH);
			licAccountField.setType(FieldDataType.string);
		}

		fields.add(licAccountField);

		return fields;
	}

	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		XMLMessagingService service = XMLMessagingService.getInstance(getFactory());
		GateMessage msg = service.createRequest("ReqCard");

		msg.addParameter("Card/pan", cardId);
		Document resp = service.sendOnlineMessage(msg, null);
		try
		{
			ClientImpl client = new ClientImpl();
            client.setRealAddress(getOwnerAddress(resp));
			setName(client, resp);
			return client;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private void setName(ClientImpl owner, Document resp) throws TransformerException
	{
		Element abonentInfo = XmlHelper.selectSingleNode(resp.getDocumentElement(), "Card/AbonentInfo");
		Element person = XmlHelper.selectSingleNode(abonentInfo, "Person");
		String firstName = person.getAttribute("firstName");
		String lastName = person.getAttribute("lastName");
		String middleName = person.getAttribute("middleName");
		owner.setFullName(lastName + " " + firstName + " " + middleName);
		owner.setId(person.getAttribute("id"));
		owner.setFirstName(firstName);
		owner.setSurName(lastName);
		owner.setPatrName(middleName);
	}

	private Address getOwnerAddress(Document resp) throws TransformerException
	{
		AddressImpl address = new AddressImpl();

		Element abonentInfo = XmlHelper.selectSingleNode(resp.getDocumentElement(), "Card/AbonentInfo");
		Element addressElem = XmlHelper.selectSingleNode(abonentInfo, "Address");
		if (addressElem == null)
			return null;
		String apartment = addressElem.getAttribute("apartment");
		address.setFlat(apartment);
		String building = addressElem.getAttribute("building");
		address.setBuilding(building);
		Element streetEl = XmlHelper.selectSingleNode(addressElem, "Street");
		if (streetEl != null)
		{
			String street = streetEl.getAttribute("name");
			address.setStreet(street);

			Element cityEl = XmlHelper.selectSingleNode(streetEl, "City");
			if (cityEl != null)
			{
				String city = cityEl.getAttribute("name");
				address.setCity(city);
				Element countryEl = XmlHelper.selectSingleNode(cityEl, "Country");
				if (countryEl != null)
				{
					String country = countryEl.getAttribute("name");
					address.setProvince(country);
				}
				Element regionEl = XmlHelper.selectSingleNode(cityEl, "Region");
				if (regionEl != null)
				{
					String region = regionEl.getAttribute("name");
					address.setDistrict(region);
					if (countryEl == null)
					{
						Element countryElement = XmlHelper.selectSingleNode(regionEl, "Country");
						if (countryElement != null)
						{
							String country = countryElement.getAttribute("name");
							address.setProvince(country);
						}
					}
				}
			}
		}
		return address;
	}

	private List<Field> getRecipientFields(String codeService,  List<Field> keyFields) throws GateException, GateLogicException
    {
        GorodRequestHelper requestHelper = new GorodRequestHelper(getFactory());
	    boolean mainService = requestHelper.isMainService(codeService);
        if (mainService)
        {
            if (keyFields.size() != 1)
                throw new GateException("Некорректное количество ключевых полей, должно быть одно поле - Лицевой счет");

            String abonentId = requestHelper.getAbonentId(codeService, (String) keyFields.get(0).getValue());
            //запрашиваем из исппн Город заполненные значениями по умолчанию доп. поля
            List<Field> fields = requestHelper.getExtendedFields(abonentId, codeService, mainService);
            if (requestHelper.getLicAccountField(fields) == null)
            {
                fields.add(keyFields.get(0));
            }
            return fields;
        }
        else
        {
            return requestHelper.getExtendedFields(codeService, mainService);
        }
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
		XMLMessagingService service = XMLMessagingService.getInstance(getFactory());
		GateMessage msg = buildServiceRequest(billingClientId, service);

		Document response = service.sendOnlineMessage(msg, null);
		List<Recipient> list = new ArrayList<Recipient>();
		try
		{
			NodeList nodeList = XmlHelper.selectNodeList(response.getDocumentElement(), "Card/ListOfLinkAbonent/Abonent");
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);
				com.rssl.phizgate.common.routable.RecipientImpl recipient = new com.rssl.phizgate.common.routable.RecipientImpl();
				//TODO разобраться с кодом сервиса в задаче доработка биллинговых шлюзов.
				recipient.setService(new ServiceImpl("ServiceCode",XmlHelper.selectSingleNode(node, "Service").getAttribute("name")));
				recipient.setSynchKey(XmlHelper.selectSingleNode(node, "Service").getAttribute("id").replace("Service-",""));
				recipient.setName(XmlHelper.selectSingleNode(node, "Service/Provider/Org").getAttribute("name"));
				list.add(recipient);
				recipient.setMain(true);
			}
			nodeList = XmlHelper.selectNodeList(response.getDocumentElement(), "Card/ListOfAddService/AddService");
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);
				com.rssl.phizgate.common.routable.RecipientImpl recipient = new com.rssl.phizgate.common.routable.RecipientImpl();
				//TODO разобраться с кодом сервиса в задаче доработка биллинговых шлюзов.
				recipient.setService(new ServiceImpl("ServiceCode",XmlHelper.selectSingleNode(node, "Service").getAttribute("name")));
				recipient.setSynchKey(XmlHelper.selectSingleNode(node, "Service").getAttribute("id").replace("AddService-",""));
				recipient.setName(XmlHelper.selectSingleNode(node, "Service/Provider/Org").getAttribute("name"));
				list.add(recipient);
				recipient.setMain(false);
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}

		return list;
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		GorodRequestHelper requestHelper = new GorodRequestHelper(getFactory());

		CommonField licAccount = requestHelper.createLicAccountField(recipient.getService().getCode(), billingClientId);
		licAccount.setValue(licAccount.getValues().get(0));

		List<Field> keyFields = new ArrayList<Field>();
		keyFields.add(licAccount);

		List<Field> fields = getRecipientFields(recipient.getService().getCode(), keyFields);
		if (requestHelper.getLicAccountField(fields) == null)
		{
			fields.add(licAccount);
		}

		return fields;
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		return getRecipientInfo((String)recipient.getSynchKey());
	}

	public RecipientInfo getRecipientInfo(String recipientId) throws GateException, GateLogicException
	{
		XMLMessagingService service = XMLMessagingService.getInstance(getFactory());
		GateMessage msg = buildRecipientinfoRequest(recipientId, service);
		Document response = service.sendOnlineMessage(msg, null);
		RecipientInfoImpl recipientInfo = new RecipientInfoImpl();
		try
		{
			Element root = response.getDocumentElement();
			Element debtElement;
			if(!recipientId.contains(ADD_SERVICE_PREFIX))
				debtElement = XmlHelper.selectSingleNode(root, "/AnsXObject/XObject/Abonent/Debt");
			else
				debtElement = null;
			Element org;
			if(!recipientId.contains(ADD_SERVICE_PREFIX))
				org = XmlHelper.selectSingleNode(root, "/AnsXObject/XObject/Abonent/Service/Provider/Org");
			else
				org = XmlHelper.selectSingleNode(root, "/AnsXObject/XObject/AddService/Service/Provider/Org");

			recipientInfo.setINN(org.getAttribute("inn"));
			recipientInfo.setKPP(org.getAttribute("kpp"));
			ResidentBank bank = new ResidentBank();

			Element bankElement = XmlHelper.selectSingleNode(org, "ListOfOrgAccount/OrgAccount/Bank");
			if(bankElement != null )
			{
				bank.setBIC(bankElement.getAttribute("bic"));
				bank.setName(bankElement.getAttribute("name"));
				bank.setAccount(bankElement.getAttribute("account"));
				recipientInfo.setBank(bank);
				recipientInfo.setAccount(XmlHelper.selectSingleNode(org, "ListOfOrgAccount/OrgAccount").getAttribute("account"));
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		return recipientInfo;
	}


	private GateMessage buildServiceRequest(String cardId, XMLMessagingService service) throws GateException
	{
		GateMessage msg = service.createRequest("ReqCard");
		Document doc = msg.getDocument();
		Element root = doc.getDocumentElement();
		Element card = doc.createElement("Card");
		root.appendChild(card);
		Element pan = doc.createElement("pan");
		pan.setTextContent(cardId);
		card.appendChild(pan);
		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);
		Element moreInfo = doc.createElement("moreInfo");
		moreInfo.setAttribute("request", "true");
		moreInfo.setAttribute("maxItems", "all");
		moreInfo.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfLinkAbonent/Abonent/Service");
		moreInfo.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo);
		Element moreInfo1 = (Element) moreInfo.cloneNode(false);
		moreInfo1.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfLinkAbonent/Abonent/Service/Provider/Org");
		listOfMoreInfo.appendChild(moreInfo1);
		Element moreInfo2 = (Element) moreInfo.cloneNode(false);
		moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfAddService/AddService/Service/Provider/Org/ListOfOrgAccount/OrgAccount");
		listOfMoreInfo.appendChild(moreInfo2);
		return msg;
	}

	/**
	 * построить запрос на получение информации по получателю
	 * @param recipientId ид получателя
	 * @param service сервис
	 * @return гейтовое сообщение
	 * @throws GateException
	 */
	private GateMessage buildRecipientinfoRequest(String recipientId, XMLMessagingService service) throws GateException
	{
		GateMessage msg = service.createRequest("ReqXObject");
		Document doc = msg.getDocument();
		Element root = doc.getDocumentElement();
		Element xObject = doc.createElement("XObject");
		xObject.setAttribute("id", recipientId);
		root.appendChild(xObject);
		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);
		Element moreInfo = doc.createElement("moreInfo");
		moreInfo.setAttribute("request", "true");
		moreInfo.setAttribute("maxItems", "all");
		moreInfo.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/Abonent/Debt");
		moreInfo.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo);
		Element moreInfo1 = doc.createElement("moreInfo");
		moreInfo1.setAttribute("request", "true");
		moreInfo1.setAttribute("maxItems", "all");
		moreInfo1.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/Abonent/Service/Provider/Org/ListOfOrgAccount/OrgAccount/Bank");
		moreInfo1.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo1);
		Element moreInfo2 = doc.createElement("moreInfo");
		moreInfo2.setAttribute("request", "true");
		moreInfo2.setAttribute("maxItems", "all");
		moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/Abonent/Service/AgrPU/ListOfAttrVal/val");
		moreInfo2.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo2);
		if( recipientId.contains(ADD_SERVICE_PREFIX) )
		{
			Element moreInfo3 = (Element)moreInfo2.cloneNode(false);
			moreInfo3.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/AddService/Service/AgrPU/ListOfAttrVal/val");
			listOfMoreInfo.appendChild(moreInfo3);
			Element moreInfo4 = (Element)moreInfo2.cloneNode(false);
			moreInfo4.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/AddService/Service/Provider/Org/ListOfOrgAccount/OrgAccount/Bank");
			listOfMoreInfo.appendChild(moreInfo4);
		}

		return msg;
	}
}
