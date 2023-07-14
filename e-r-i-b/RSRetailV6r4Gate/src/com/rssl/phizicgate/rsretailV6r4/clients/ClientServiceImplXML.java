package com.rssl.phizicgate.rsretailV6r4.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsretailV6r4.messaging.RetailXMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Omeliyanchuk
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class ClientServiceImplXML extends ClientServiceImpl 
{
	public ClientServiceImplXML()
	{
		super(null);
	}

	public ClientServiceImplXML(GateFactory factory)
	{
		super(factory);
	}

	public Client getClientById(final String id) throws GateException,GateLogicException
	{
		Document document = null;

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getClientById_q");
		message.addParameter("id", id);

		document = service.sendOnlineMessage(message, null);

		if(document==null)
			return null;

		return parseClient(document.getDocumentElement());
	}

	private Client parseClient(Element root) throws GateException
	{
		if(root ==null)
			return null;

		ClientImpl client = new ClientImpl();

		client.setId(RetailXMLHelper.getElementValue(root,"longId"));
		client.setDisplayId(RetailXMLHelper.getElementValue(root,"displayId"));
		client.setBirthDay(RetailXMLHelper.getElementDate(root,"birthDay"));
		client.setBirthPlace(RetailXMLHelper.getElementValue(root,"birthPlace"));
		client.setCitizenship(RetailXMLHelper.getElementValue(root,"citizenship"));
		client.setEmail(RetailXMLHelper.getElementValue(root,"email"));
		client.setFirstName(RetailXMLHelper.getElementValue(root,"firstName"));
		client.setFullName(RetailXMLHelper.getElementValue(root,"fullName"));
		client.setHomePhone(RetailXMLHelper.getElementValue(root,"homePhone"));
		client.setINN(RetailXMLHelper.getElementValue(root,"inn"));
		client.setJobPhone(RetailXMLHelper.getElementValue(root,"jobPhone"));
		client.setMobilePhone(RetailXMLHelper.getElementValue(root,"mobilePhone"));
		client.setPatrName(RetailXMLHelper.getElementValue(root,"patrName"));
		client.setResidentFromRetail(RetailXMLHelper.getElementValue(root,"resident"));
		client.setShortName(RetailXMLHelper.getElementValue(root,"shortName"));
		client.setSurName(RetailXMLHelper.getElementValue(root,"surName"));
		client.setMaleFromRetail(RetailXMLHelper.getElementValue(root,"gender"));
		client.setRealAddress(parseAddress((Element)root.getElementsByTagName("factAddress").item(0)));
		client.setLegalAddress(parseAddress((Element)root.getElementsByTagName("regAddress").item(0)));
		client.setDocuments(parseDocuments((Element)root.getElementsByTagName("documents").item(0)));

		return client;
	}

	private Address parseAddress(Element addressRoot)
	{
		if(addressRoot==null)
			return null;
		AddressImpl address = new AddressImpl();
		address.setBuilding(RetailXMLHelper.getElementValue(addressRoot,"building"));
		address.setCity(RetailXMLHelper.getElementValue(addressRoot,"city"));
		address.setDistrict(RetailXMLHelper.getElementValue(addressRoot,"district"));
		address.setFlat(RetailXMLHelper.getElementValue(addressRoot,"flat"));
		address.setHomePhone(RetailXMLHelper.getElementValue(addressRoot,"homePhone"));
		address.setHouse(RetailXMLHelper.getElementValue(addressRoot,"house"));
		address.setId(RetailXMLHelper.getElementValue(addressRoot,"id"));
		address.setMobilePhone(RetailXMLHelper.getElementValue(addressRoot,"mobilePhone"));
		address.setPostalCode(RetailXMLHelper.getElementValue(addressRoot,"postalCode"));
		address.setProvince(RetailXMLHelper.getElementValue(addressRoot,"province"));
		address.setStreet(RetailXMLHelper.getElementValue(addressRoot,"street"));
		address.setWorkPhone(RetailXMLHelper.getElementValue(addressRoot,"workPhone"));
		return address;
	}

	private List<? extends ClientDocument> parseDocuments(Element documentsRoot) throws GateException
	{
		if(documentsRoot==null)
			return null;
		NodeList nodes =  null;
		nodes = documentsRoot.getElementsByTagName("document");

		int count = nodes.getLength();

		List<ClientDocument> documents = new ArrayList<ClientDocument>(count);

		for (int i = 0; i < count; i++)
		{
			Element element = (Element) nodes.item(i);
			documents.add(parseDocument(element));
		}
		return documents;
	}

	private ClientDocument parseDocument(Element documentRoot) throws GateException
	{
		ClientDocumentImpl document = new ClientDocumentImpl();
		document.setDocIdentify("1".equals(RetailXMLHelper.getElementValue(documentRoot,"docIdentify")));
		document.setDocIssueBy(RetailXMLHelper.getElementValue(documentRoot,"docIssueBy"));
		document.setDocIssueByCode(RetailXMLHelper.getElementValue(documentRoot,"docIssueByCode"));
		String dateString = RetailXMLHelper.getElementValue(documentRoot,"docIssueDate");
		if(!("Undefined".equals(dateString) || dateString ==null))
			document.setDocIssueDate(RetailXMLHelper.getElementDate(documentRoot,"docIssueDate"));
		document.setDocNumber(RetailXMLHelper.getElementValue(documentRoot,"docNumber"));
		document.setDocSeries(RetailXMLHelper.getElementValue(documentRoot,"docSeries"));
		document.setDocTypeName(RetailXMLHelper.getElementValue(documentRoot,"docName"));
		document.setDocumentType(ClientDocumentTypeWrapper.parseState(RetailXMLHelper.getElementValue(documentRoot,"docType")));
		ClientId clientId = new ClientId();
		clientId.setPaperKind(Long.parseLong(RetailXMLHelper.getElementValue(documentRoot,"kPaperKind")));
		clientId.setPersonId(Long.parseLong(RetailXMLHelper.getElementValue(documentRoot,"kPersonId")));
		document.setLongId(clientId);
		return document;
	}

	public List<Client> getClientsByTemplate(final Client client, Office office, final int firstResult, final int maxResults) throws GateException,GateLogicException
	{
		Document document = null;

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getClientsByTemplate_q");
		message.addParameter("surName", client.getSurName());
		message.addParameter("firstName", client.getFirstName());
		message.addParameter("patrName", client.getPatrName());
		if (client.getId() != null && client.getId().length()>0)
			message.addParameter("personId", client.getId());
		if (client.getBirthDay() != null)
			message.addParameter("birthDay", XMLDatatypeHelper.formatDateWithoutTimeZone(client.getBirthDay()));
		message.addParameter("firstResult", firstResult);
		message.addParameter("maxResults", maxResults);

		document = service.sendOnlineMessage(message, null);

		NodeList nodes =  null;
		try
		{
			nodes = XmlHelper.selectNodeList(document.getDocumentElement(), "clients/client");
		}
		catch (TransformerException ex)
		{
			throw new GateException(ex);
		}

		int count = nodes.getLength();

		List<Client> clients = new ArrayList<Client>(count);

		for (int i = 0; i < count; i++)
		{
			Element element = (Element) nodes.item(i);
			clients.add(parseClient(element));
		}

		return clients;

	}
}
