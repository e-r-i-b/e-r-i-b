package com.rssl.phizic.shoplistener.generated.registration;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.einvoicing.CurrencyImpl;
import com.rssl.phizic.einvoicing.ShopDetailInfoBuilder;
import com.rssl.phizic.einvoicing.ShopOrderImpl;
import com.rssl.phizic.gate.einvoicing.OrderKind;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.shoplistener.generated.DocRegRqType;
import com.rssl.phizic.shoplistener.generated.RegRqDocumentType;
import com.rssl.phizic.utils.BeanFormatter;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.shopclient.generated.CurrencyType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gulov
 * @ created 12.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ѕолна€ регистраци€ - сохран€етс€ вс€ информаци€ о заказе
 */
public class Full extends Partial
{
	private static final Map<Class, Class> corellationTypes = new HashMap<Class, Class>();
	private static final Map<Class, BeanFormatter> enumCorellationTypes = new HashMap<Class, BeanFormatter>();
	private static final Map<CurrencyType, com.rssl.phizic.shoplistener.generated.CurrencyType> currencyEnumCorrelationType = new HashMap<CurrencyType, com.rssl.phizic.shoplistener.generated.CurrencyType>();

	static {
		corellationTypes.put(com.rssl.phizicgate.shopclient.generated.FieldsType.class,             com.rssl.phizic.shoplistener.generated.FieldsType.class);
		corellationTypes.put(com.rssl.phizicgate.shopclient.generated.ItemType.class,               com.rssl.phizic.shoplistener.generated.ItemType.class);
		corellationTypes.put(com.rssl.phizicgate.shopclient.generated.AirlineReservationType.class, com.rssl.phizic.shoplistener.generated.AirlineReservationType.class);
		corellationTypes.put(com.rssl.phizicgate.shopclient.generated.PassengerType.class,          com.rssl.phizic.shoplistener.generated.PassengerType.class);
		corellationTypes.put(com.rssl.phizicgate.shopclient.generated.RouteType.class,              com.rssl.phizic.shoplistener.generated.RouteType.class);
		corellationTypes.put(com.rssl.phizicgate.shopclient.generated.DepartureType.class,          com.rssl.phizic.shoplistener.generated.DepartureType.class);
		corellationTypes.put(com.rssl.phizicgate.shopclient.generated.ArrivalType.class,            com.rssl.phizic.shoplistener.generated.ArrivalType.class);

		currencyEnumCorrelationType.put(CurrencyType.RUB, com.rssl.phizic.shoplistener.generated.CurrencyType.RUB);
		currencyEnumCorrelationType.put(CurrencyType.EUR, com.rssl.phizic.shoplistener.generated.CurrencyType.EUR);
		currencyEnumCorrelationType.put(CurrencyType.USD, com.rssl.phizic.shoplistener.generated.CurrencyType.USD);

		enumCorellationTypes.put(CurrencyType.class, new BeanFormatter()
		{
			public Object format(Object object) throws Exception
			{
				if (object == null)
					return null;

				return currencyEnumCorrelationType.get(object);
			}
		});
	}

	private RegRqDocumentType document;

	/**
	 * «аполнить заказ поданным из xml-описани€ и кода поставщика
	 * @param document xml-описание заказа
	 * @param order - заказ
	 */
	public void fillOrder(com.rssl.phizicgate.shopclient.generated.RegRqDocumentType document, ShopOrderImpl order) throws Exception
	{
		DocRegRqType request = new DocRegRqType();
		request.setSPName(order.getReceiverCode());
		RegRqDocumentType regRqDocument = new RegRqDocumentType();
		BeanHelper.copyPropertiesWithDifferentTypes(regRqDocument, document, corellationTypes, enumCorellationTypes);
		request.setDocument(regRqDocument);
		fillFullOrder(request, order);
		order.setType(getType());
	}

	@Override
	protected ShopOrder fillOrder(DocRegRqType request)
	{
		ShopOrderImpl order = (ShopOrderImpl) super.fillOrder(request);
		return fillFullOrder(request, order);
	}

	private ShopOrder fillFullOrder(DocRegRqType request, ShopOrderImpl order)
	{
		document = request.getDocument();
		order.setAmount(new Money(document.getAmount(), new CurrencyImpl(document.getAmountCur().getValue())));
		order.setDescription(document.getDesc());
		order.setReceiverAccount(document.getAccount());
		order.setCorrAccount(document.getCorrespondent());
		order.setBic(document.getRecipient());
		order.setInn(document.getTaxId());
		order.setKpp(document.getKPP());
		order.setReceiverName(document.getRecipientName());
		order.setPrintDescription(document.getPrintDesc());
		order.setBackUrl(document.getBackUrl());
		return order;
	}

	@Override
	protected com.rssl.phizic.gate.einvoicing.TypeOrder getType()
	{
		return com.rssl.phizic.gate.einvoicing.TypeOrder.F;
	}

	@Override
	protected ShopOrder registerOrder(DocRegRqType request) throws GateException
	{
		ShopOrder order = super.registerOrder(request);
		saveOrderDetailInfo(order);
		return order;
	}

	public void saveOrderDetailInfo(ShopOrder order) throws GateException
	{
		if (document == null)
			throw new GateException("Ќе заполнено описание заказа");

		if (order.getKind() == OrderKind.INTERNET_SHOP)
		{
			String goods = ShopDetailInfoBuilder.fillShopGoodsInfo(document.getFields().getShop());
			ShopDetailInfoBuilder.updateOrder(order.getUuid(), goods);
		}
	    else
		{
			String airline = ShopDetailInfoBuilder.fillAirlineReservation(document.getFields().getAirlineReservation());
			ShopDetailInfoBuilder.updateOrder(order.getUuid(), airline);
		}
	}

	@Override
	protected void checkFields(DocRegRqType request) throws GateException
	{
		super.checkFields(request);

		RegRqDocumentType doc = request.getDocument();
		if (doc.getAmount() == null ||
				doc.getAmountCur() == null ||
				StringHelper.isEmpty(doc.getDesc()) ||
				StringHelper.isEmpty(doc.getAccount()) ||
				StringHelper.isEmpty(doc.getCorrespondent()) ||
				StringHelper.isEmpty(doc.getRecipient()) ||
				StringHelper.isEmpty(doc.getTaxId()) ||
				StringHelper.isEmpty(doc.getRecipientName()) ||
				doc.getFields() == null)
			throw new IllegalArgumentException("Ќе указан один или несколько об€зательных параметров.");
	}
}
