package com.rssl.phizic.test.wsgateclient.shop;

import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.gate.einvoicing.OrderKind;
import com.rssl.phizic.gate.einvoicing.TypeOrder;
import com.rssl.phizic.test.wsgateclient.shop.generated.*;
import com.rssl.phizic.test.wsgateclient.shop.generated.mock.Document;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.xml.rpc.ServiceException;

/**
 * @author gulov
 * @ created 19.01.2011
 * @ $Authors$
 * @ $Revision$
 */
public class ShopRegServiceWrapper
{
	private static final String SP_NAME = "Yandex.Money.Fasilitator";
	private static final String[][] AIRLINE_TICKET_VARIANTS = {
			new String[] { "567890001", "567890002", "567890003", },
			new String[] { "239392123", "239392120", },
			new String[] { "232392393", "659393492", "282818212", "900032267", },
	};

	/**
	 * ��������� ����� ����������� ������
	 */
	private final DocRegService stub;

	private final Random random = new Random();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param url
	 * @throws ServiceException
	 */
	public ShopRegServiceWrapper(String url) throws ServiceException
	{
		DocRegServiceImplLocator locator = new DocRegServiceImplLocator();
		locator.setDocRegServicePortEndpointAddress(url);

		stub = locator.getDocRegServicePort();
	}

	public DocRegRsType ordersRegistration(TypeOrder typeOrder, ShopSystemName shopName, String backUrl, String mobilePhone, boolean mobileCheckout, boolean facilitator,
	                                        String eShopIdBySP, String eShopURL, CurrencyType currency, String systemId) throws RemoteException
	{
		DocRegRqType docReg = new DocRegRqType();

		DateFormat simpleDateFormat = ShopConstants.getDateFormat();

		docReg.setRqUID(new RandomGUID().getStringValue());
		docReg.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));
		docReg.setMode(typeOrder.toString());
		if (typeOrder == TypeOrder.O)
		{
			docReg.setPhone(mobilePhone);
			docReg.setMobileCheckout(mobileCheckout);
		}
		docReg.setSPName(shopName.toString());
		if (StringHelper.isNotEmpty(systemId))
			docReg.setSystemId(systemId);

		docReg.setDocument(getDocument(typeOrder, shopName, backUrl, currency));

		if (facilitator)
		{
			docReg.setEShopIdBySP(eShopIdBySP);
			docReg.setEShopURL(eShopURL);
		}
		try
		{
			return stub.docReg(docReg);
		}
		catch(RemoteException e)
		{
			e.printStackTrace();
			throw e;
		}

	}

	private RegRqDocumentType getDocument(TypeOrder typeOrder, ShopSystemName shopName, String backUrl,CurrencyType currency)
	{
		RegRqDocumentType document;
		if (typeOrder == TypeOrder.P) // ��������� �����������
		{
			document = new RegRqDocumentType();
			document.setId("part" + new RandomGUID().getStringValue());

			return document;
		}
		else // ������ �����������
		{
			document = new Document(shopName).getDocument();
		}

		document.setAmountCur(currency);

		if (!StringHelper.isEmpty(backUrl))
			document.setBackUrl(backUrl);
		return document;
	}

	/**
	 * �������� ���������� � ���������� �����������
	 *
	 * @param eribUUID - ������������� ��������� � ����
	 * @param ticketsStatusCode - ������ ������� �������
	 * @param itineraryUrl - URL, �� �������� ����� �������� ������ �������-���������
	 * @param systemId
	 * @return ������ ��������� ���������
	 */
	public StatusType sendAirlineTicketsInfo(String eribUUID, String ticketsStatusCode, String itineraryUrl, String systemId) throws RemoteException
	{
		AirlineTicketStatus status = AirlineTicketStatus.fromCode(ticketsStatusCode);
		DateFormat simpleDateFormat = ShopConstants.getDateFormat();

		DocFlightsInfoRqType request = new DocFlightsInfoRqType();
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));

		if (StringHelper.isNotEmpty(systemId))
		{
			request.setSPName(SP_NAME);
			request.setSystemId(systemId);
		}

		DocFlightsInfoRqDocumentType document = new DocFlightsInfoRqDocumentType();
		document.setERIBUID(eribUUID);
		document.setTicketsStatus(status.getCode());
		if (status == AirlineTicketStatus.OK) {
			int variant = random.nextInt(AIRLINE_TICKET_VARIANTS.length);
			document.setTicketsList(AIRLINE_TICKET_VARIANTS[variant]);
			document.setItineraryUrl(itineraryUrl);
			document.setTicketsDesc(random.nextDouble() < 0.8 ? status.getText() : null);
		}
		else {
			document.setStatusDesc(status.getText());
		}
		request.setDocuments(new DocFlightsInfoRqDocumentType[] {document});

		DocFlightsInfoRsType response = stub.docFlightsInfo(request);
		return response.getDocuments()[0].getStatus();
	}

	/**
	 * �������� ������� �������� ������
	 *
	 * @param eribUUID ������������� ��������� � ����
	 * @param spName - ������������� �������� � ����
	 * @param systemId
	 * @return �����
	 */
	public GoodsReturnRsResultType sendGoodsReturnRq(String eribUUID, String spName, String eShopIdBySP, String systemId) throws RemoteException
	{
		eribUUID = eribUUID.trim();
		GoodsReturnRqDocumentType document = new GoodsReturnRqDocumentType();
		document.setAmount(Document.getRandomTotalAmount());
		document.setAmountCur(Document.getCurrency());
		document.setERIBUID(eribUUID);
		document.setGoodsReturnId(new RandomGUID().getStringValue());

		if (OrderKind.AEROFLOT.name().equals(spName))
		{
			GoodsReturnRqDocumentFieldType fields = new GoodsReturnRqDocumentFieldType();

			TicketItemType[] tickets = {new TicketItemType("567890001", Document.getRandomItemAmount(), Document.getCurrency()),
										new TicketItemType("567890002", Document.getRandomItemAmount(), Document.getCurrency())};
			fields.setTicketsList(tickets);
			document.setFields(fields);
		}
		else
		{
			GoodsReturnRqDocumentFieldType fields = new GoodsReturnRqDocumentFieldType();
			String[][] items = {Document.SHOP_TITLES[1], Document.SHOP_TITLES[3]};
			fields.setShop(Document.generateShopItems(items));
			document.setFields(fields);
		}

		GoodsReturnRqType request = new GoodsReturnRqType();
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(ShopConstants.getDateFormat().format(Calendar.getInstance().getTime()));
		request.setSPName(spName);
		if (StringHelper.isNotEmpty(systemId))
			request.setSystemId(systemId);

		request.setEShopIdBySP(eShopIdBySP);
		request.setDocument(document);
		GoodsReturnRsType response = stub.goodsReturn(request);
		return response.getResult();
	}

	/**
	 * �������� ������� ������ ������� ������
	 * @param eribUUID ������������� ��������� � ����
	 * @param spName - ������������� �������� � ����
	 * @param eShopIdBySP - ������������� ��� (��� �������������)
	 * @return �����
	 */
	public DocRollbackRsResultType sendDocRollbackRq(String eribUUID, String spName, String eShopIdBySP) throws RemoteException
	{
		eribUUID = eribUUID.trim();

		DocRollbackRqDocumentType document = new DocRollbackRqDocumentType();
		document.setAmount(Document.getRandomTotalAmount());
		document.setAmountCur(Document.getCurrency());
		document.setERIBUID(eribUUID);
		document.setDocRollbackId(new RandomGUID().getStringValue());

		DocRollbackRqType request = new DocRollbackRqType();
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(ShopConstants.getDateFormat().format(Calendar.getInstance().getTime()));
		request.setSPName(spName);
		request.setEShopIdBySP(eShopIdBySP);
		request.setDocument(document);
		DocRollbackRsType response = stub.docRollback(request);
		return response.getResult();
	}

	/**
	 * �������� ������� �������� ������ ��������
	 * @param sPName - ������������� ������������
	 * @param eShopIdBySP - ������������� ��� � ������� ������������
	 * @param recipientName - �������� ���
	 * @param URL - ��� ���
	 * @param INN - ��� ���
	 * @param phone - ����� �������� ��� ��������
	 * @return - ��������� ��������
	 */
	public ClientCheckRsType sendClientCheckRq(String sPName, String eShopIdBySP, String recipientName, String URL, String INN, String phone) throws RemoteException
	{
		ClientCheckRqType doc = new ClientCheckRqType();
		doc.setRqUID(new RandomGUID().getStringValue());
		doc.setRqTm(ShopConstants.getDateFormat().format(Calendar.getInstance().getTime()));
		doc.setSPName(sPName);
		doc.setEShopIdBySP(eShopIdBySP);
		doc.setRecipientName(recipientName);
		doc.setURL(URL);
		doc.setINN(INN);
		doc.setPhone(phone);

		ClientCheckRsType response = stub.clientCheck(doc);
		return response;
	}
}
