package com.rssl.phizic.operations.payment.internetShops;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.shop.*;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.OrderKind;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.util.MoneyFunctions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Операция для составления детальной информации по интернет-платежу или брони аэрофлота.
 *
 * @author bogdanov
 * @ created 06.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class InternetOrderDetailInfoOperation extends OperationBase 
{
	private ShopOrder orderInfo;
	private boolean isAirline;

	public void initialize(Long orderId) throws BusinessException
	{
		if (orderId == null)
			throw new BusinessException("Не задан идентификатор заказа");

		try
		{
			initializeInner(GateSingleton.getFactory().service(ShopOrderService.class).getOrder(orderId));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initialize(String orderUuid) throws BusinessException
	{
		initializeInner(ShopHelper.get().getShopOrder(orderUuid));
	}

	private void initializeInner(ShopOrder orderInfo) throws BusinessException
	{

		if (orderInfo == null)
		{
			return;
		}

		if (!ShopHelper.get().isSameClient(orderInfo.getProfile(), AuthenticationContext.getContext()))
			return;

		this.orderInfo = orderInfo;
		this.isAirline = orderInfo.getKind() == OrderKind.AEROFLOT;
	}

	/**
	 *
	 * @return информация по брони аэробилетов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Set<AirlineInfo> getAirlinesInfo() throws BusinessException, BusinessLogicException
	{
		Set<AirlineInfo> airlinesInfo = new HashSet<AirlineInfo>();

		if (orderInfo == null)
		{
			return airlinesInfo;
		}

		try
		{
			BusinessDocument businessDocument = DocumentHelper.getPaymentByOrder(orderInfo.getUuid());

			Integer tickets = null;
			if (businessDocument != null && businessDocument instanceof JurPayment)
			{
				JurPayment payment = (JurPayment) businessDocument;
				if (StringHelper.isNotEmpty(payment.getTicketInfo()))
				{
					Document ticketInfo = XmlHelper.parse(payment.getTicketInfo());
					tickets = XmlHelper.selectNodeList(ticketInfo.getDocumentElement(), "//TicketsInfo/TicketsList/TicketNumber").getLength();
				}
			}

			String responseXml = ShopHelper.get().getOrderDetailInfo(orderInfo.getUuid());
			if (StringHelper.isEmpty(responseXml))
			{
				return airlinesInfo;
			}

			Document routesDoc = XmlHelper.parse(responseXml);
			NodeList routes    = XmlHelper.selectNodeList(routesDoc.getDocumentElement(), "//AirlineReservation/RoutesList/Route");

			for (int i = 0; i < routes.getLength(); i++)
			{
				Element element = (Element) routes.item(i);

				AirlineInfo info = new AirlineInfo();
				info.setTicketCount(tickets);
				info.setArrivalAirport(XmlHelper.getElementValueByPath(element, "Arrival/Airport"));
				info.setArrivalLocation(XmlHelper.getElementValueByPath(element, "Arrival/Location"));
				info.setDepartureAirport(XmlHelper.getElementValueByPath(element, "Departure/Airport"));
				info.setDepartureLocation(XmlHelper.getElementValueByPath(element, "Departure/Location"));
				info.setArrivalDate(XmlHelper.getElementValueByPath(element, "Arrival/DateTime"));
				info.setDepartureDate(XmlHelper.getElementValueByPath(element, "Departure/DateTime"));
				airlinesInfo.add(info);
			}

			return airlinesInfo;
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @return информация по заказам в магазинах
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Set<ShopOrdersInfo> getShopOrdersInfo() throws BusinessException, BusinessLogicException
	{
		Set<ShopOrdersInfo> shopOrdersInfoSet = new HashSet<ShopOrdersInfo>();

		if (orderInfo == null)
		{
			return shopOrdersInfoSet;
		}

		try
		{
			String responseXml = ShopHelper.get().getOrderDetailInfo(orderInfo.getUuid());

			if (StringHelper.isEmpty(responseXml))
			{
				return shopOrdersInfoSet;
			}

			Document fieldsDoc = XmlHelper.parse(responseXml);
			NodeList fields    = XmlHelper.selectNodeList(fieldsDoc.getDocumentElement(), "//fields/field");

			for (int i = 0; i < fields.getLength(); i++)
			{
				Element element = (Element) fields.item(i);

				ShopOrdersInfo shopOrdersInfo = new ShopOrdersInfo();
				shopOrdersInfo.setShopCount(getEscapedForElement(element, "count"));
				shopOrdersInfo.setProductName(getEscapedForElement(element, "name"));
				shopOrdersInfo.setProductDescription(getEscapedForElement(element, "description"));

				String amount   = XmlHelper.getElementValueByPath(element, "price/amount");
				String currency = XmlHelper.getElementValueByPath(element, "price/currency");

				if (StringHelper.isNotEmpty(amount) && StringHelper.isNotEmpty(currency))
				{
					shopOrdersInfo.setShopAmount(new BigDecimal(amount));
					shopOrdersInfo.setShopCurrency(currency);
				}

				shopOrdersInfoSet.add(shopOrdersInfo);
			}
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}

		return shopOrdersInfoSet;
	}

	/**
	 * @return json-список покупок в интернет магазине.
	 * @throws BusinessException
	 */
	public String getOrderDetailInfoFields() throws BusinessException, BusinessLogicException
	{
		if (orderInfo == null)
			return "{\"productName\": \"\", \"productDescription\": \"\", \"shopCount\" : \"\", \"shopSum\" : \"\"}";

		StringBuilder sb = new StringBuilder(100);

		try
		{
			Document fieldsDoc = XmlHelper.parse(ShopHelper.get().getOrderDetailInfo(orderInfo.getUuid()));
			NodeList fields = XmlHelper.selectNodeList(fieldsDoc.getDocumentElement(), "//fields/field");
			for (int i = 0; i < fields.getLength(); i++)
			{
				Element element = (Element) fields.item(i);
				BigDecimal amount = new BigDecimal(XmlHelper.getElementValueByPath(element, "price/amount"));
								String currency = XmlHelper.getElementValueByPath(element, "price/currency");
				sb.append("{");
				sb.append("\"productName\":\"").append(getEscapedForElement(element, "name")).append("\",")
						.append("\"productDescription\":\"").append(getEscapedForElement(element, "description")).append("\",")
						.append("\"shopCount\":\"").append(getEscapedForElement(element, "count")).append("\",")
						.append("\"shopSum\":\"").append(StringUtils.escapeSymbols(
							MoneyFunctions.formatAmount(amount, 2) + "&nbsp;" + MoneyFunctions.getCurrencySign(currency)
						)).append("\"")
						.append("}");
				if (i < fields.getLength() - 1)
					sb.append(",");
			}
			return sb.toString();
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return json-список маршрутов для авиабилетов.
	 */
	public String getAirlineInfoFields() throws BusinessException, BusinessLogicException
	{
		if (orderInfo == null)
			return "{\"airlineInfo\": \"\", \"airlineDate\": \"\", \"productDescription\": \"\", \"shopCount\" : \"\", \"shopSum\" : \"\"}";

		try
		{
			Set<AirlineInfo> airlineInfos = new TreeSet<AirlineInfo>();
			BusinessDocument businessDocument = DocumentHelper.getPaymentByOrder(orderInfo.getUuid());

			Integer numOfTicket = null;
			if (businessDocument != null && businessDocument instanceof JurPayment)
			{
				JurPayment payment = (JurPayment)businessDocument;
				if (StringHelper.isNotEmpty(payment.getTicketInfo()))
				{
					Document ticketInfo = XmlHelper.parse(payment.getTicketInfo());
					numOfTicket = XmlHelper.selectNodeList(ticketInfo.getDocumentElement(), "//TicketsInfo/TicketsList/TicketNumber").getLength();
				}
			}

			Document routesDoc = XmlHelper.parse(ShopHelper.get().getOrderDetailInfo(orderInfo.getUuid()));
			NodeList routes = XmlHelper.selectNodeList(routesDoc.getDocumentElement(), "//AirlineReservation/RoutesList/Route");
			for (int i = 0; i < routes.getLength(); i++)
			{
				AirlineInfo info = new AirlineInfo();
				Element element = (Element) routes.item(i);
				info.setTicketCount(numOfTicket);
				info.setDepartureAirport(XmlHelper.getElementValueByPath(element, "Departure/Airport"));
				info.setDepartureLocation(XmlHelper.getElementValueByPath(element, "Departure/Location"));
				info.setDepartureDate(StringHelper.formatDateStringAirlinePayment(XmlHelper.getElementValueByPath(element, "Departure/DateTime")));
				info.setArrivalAirport(XmlHelper.getElementValueByPath(element, "Arrival/Airport"));
				info.setArrivalLocation(XmlHelper.getElementValueByPath(element, "Arrival/Location"));
				info.setArrivalDate(StringHelper.formatDateStringAirlinePayment(XmlHelper.getElementValueByPath(element, "Arrival/DateTime")));
				airlineInfos.add(info);
			}

			boolean first = true;
			StringBuilder sb = new StringBuilder(100);
			for (AirlineInfo info : airlineInfos)
			{
				if (!first)
					sb.append(",");
				first = false;

				sb.append("{")
						.append("\"airlineInfo\":\"").append(buildAirlineInfo(info)).append("\",")
						.append("\"airlineDate\":\"").append(buildAirlineDate(info)).append("\",")
						.append("\"productDescription\":\"&nbsp;\",")
						.append("\"shopCount\":\"&nbsp;\",")
						.append("\"shopSum\":\"&nbsp;\"")
						.append("}");
			}

			return sb.toString();
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}

	private String buildAirlineInfo(AirlineInfo info)
	{
		StringBuilder sb = new StringBuilder(100);
		if (!StringHelper.isEmpty(info.getDepartureLocation()))
			sb.append(info.getDepartureLocation()).append(", ");
		sb.append(info.getDepartureAirport()).append(" - ");
		if (!StringHelper.isEmpty(info.getArrivalLocation()))
			sb.append(info.getArrivalLocation()).append(", ");
		sb.append(info.getArrivalAirport());

		return StringUtils.escapeSymbols(sb.toString());
	}

	private String buildAirlineDate(AirlineInfo info)
	{
		StringBuilder sb = new StringBuilder(info.getDepartureDate().length() + info.getArrivalDate().length() + 3);
		return StringUtils.escapeSymbols(sb.append(info.getDepartureDate()).append(" - ").append(info.getArrivalDate()).toString());
	}

	private String getEscapedForElement(Element element, String path) throws TransformerException
	{
		String val = XmlHelper.getElementValueByPath(element, path);
		return StringHelper.isEmpty(val) ? "" : StringUtils.escapeSymbols(val);
	}

	/**
	 * @return оплата брони аэробилетов.
	 */
	public boolean isAirline()
	{
		return isAirline;
	}
}
