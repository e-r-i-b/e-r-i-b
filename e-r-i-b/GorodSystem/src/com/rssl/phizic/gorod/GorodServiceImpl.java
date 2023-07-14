package com.rssl.phizic.gorod;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizic.gate.payments.systems.gorod.GorodCard;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gorod.messaging.XMLMessagingService;
import com.rssl.phizic.gorod.client.ClientImpl;
import com.rssl.phizic.gorod.client.AddressImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerException;

/**
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodServiceImpl extends AbstractService implements GorodService
{
	public GorodServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public GorodCard getCard(String cardId, Office office) throws GateException, GateLogicException
	{
		XMLMessagingService service = XMLMessagingService.getInstance(getFactory());
		GateMessage msg = service.createRequest("ReqCard");

		msg.addParameter("Card/pan", cardId);
		Document resp = service.sendOnlineMessage(msg, null);
		try
		{
			ClientImpl owner = new ClientImpl();
            owner.setRealAddress(getOwnerAddress(resp));
			setName(owner, resp);
			GorodCardImpl card = new GorodCardImpl();
			card.setId(cardId);
			card.setOwner(owner);
			return card;
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
}
