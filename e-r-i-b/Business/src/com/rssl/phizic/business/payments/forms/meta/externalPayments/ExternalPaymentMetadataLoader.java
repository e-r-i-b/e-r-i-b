package com.rssl.phizic.business.payments.forms.meta.externalPayments;

import com.rssl.common.forms.*;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.RefundGoodsClaim;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.business.shop.ExternalPaymentDataServiceSources;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.business.shop.WebShopDataServiceSources;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

import static com.rssl.common.forms.PaymentFieldKeys.*;

/**
 * @author Mescheryakova
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ExternalPaymentMetadataLoader implements ExtendedMetadataLoader
{
	private static final String INTERNET_ORDER_FIELDS_DICTIONARY_NAME = "internetOrderFields.xml";
	private static final String AIRLINE_RESERVATION_DICTIONARY_NAME = "airlineReservation.xml";
	private static final String TICKETS_INFO_DICTIONARY_NAME = "ticketsInfo.xml";
	private static final String REFUND_GOODS_DICTIONARY_NAME = "refundGoods.xml";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	protected ExternalPaymentDataServiceSources getDataSource(String orderId) throws BusinessException
	{
		return new WebShopDataServiceSources(orderId);
	}

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);

		// 1. Поля
		ExternalPaymentDataServiceSources source = getDataSource(fieldSource.getValue(PaymentFieldKeys.ORDER_ID_KEY));
		FormBuilder formBuilder = getFormBuilder(metadata.getForm());
		// строим поля формы по сорцу

		Map<String, String> fieldFromSource = source.getAllValues();

		List<Field>  fields =  new ArrayList<Field> ();
		for (String key : fieldFromSource.keySet())
		{
		    FieldBuilder fieldBuilder = getFieldBuilder(key, fieldFromSource.get(key));
			fields.add(fieldBuilder.build());
		}
		formBuilder.addFields(fields);
		newMetadata.setExtendedForm(formBuilder.build());

		// 2. Справочники
		newMetadata.addAllDictionaries(metadata.getDictionaries());

		addDictionariesNew(source, fieldSource, newMetadata);

		return newMetadata;
	}

	protected void addDictionariesNew(ExternalPaymentDataServiceSources source, FieldValuesSource fieldSource, ExtendedMetadata newMetadata) throws BusinessException
	{
		addDictionaries(source, newMetadata);
	}

	private void addDictionaries(FieldValuesSource source, ExtendedMetadata newMetadata) throws BusinessException
	{
		// 2.1 Список полей интернет-заказа
		String internetOrderFields = getInternetOrderFields(source);
		if (StringHelper.isNotEmpty(internetOrderFields))
			newMetadata.addDictionary(INTERNET_ORDER_FIELDS_DICTIONARY_NAME, buildDictionaryFromXmlField(internetOrderFields));

		// 2.2 Для оплаты брони авиабилетов строим справочник "Данные о брони авиабилетов" по xml-полю
		String reservation = source.getValue(AIRLINE_RESERVATION);
		if (!StringHelper.isEmpty(reservation))
			newMetadata.addDictionary(AIRLINE_RESERVATION_DICTIONARY_NAME, buildDictionaryFromXmlField(reservation));

		// 2.3 Для оплаты брони авиабилетов строим справочник "Информация по авиабилетам" по xml-полю
        String ticketsInfo = source.getValue(TICKETS_INFO);
		newMetadata.addDictionary(TICKETS_INFO_DICTIONARY_NAME, buildDictionaryFromXmlField(buildTicketsInfo(ticketsInfo)));
	}

	private String getInternetOrderFields(FieldValuesSource source)
	{
		if (!StringHelper.isEmpty(source.getValue(ORDER_FIELDS)))
			return source.getValue(ORDER_FIELDS);

		if (StringHelper.isEmpty(source.getValue(ORDER_FIELDS_SIZE)))
			return "";

		int size = Integer.parseInt(source.getValue(ORDER_FIELDS_SIZE));
		StringBuilder sb = new StringBuilder(WebShopDataServiceSources.MAX_FIELD_SIZE*size);
		for (int i = 0; i < size; i++)
		{
			sb.append(source.getValue(ORDER_FIELDS + "_" + Integer.toString(i)));
		}

		return sb.toString();
	}

	protected void addDictionaries(FieldValuesSource source, BusinessDocument document, ExtendedMetadata newMetadata) throws BusinessException
	{
		addDictionaries(source, newMetadata);

		// 2.4 Список возвратов товаров.
		String refundGoodsInfo = buildRefundGoods(document);
		newMetadata.addDictionary(REFUND_GOODS_DICTIONARY_NAME, buildDictionaryFromXmlField(refundGoodsInfo));
	}

	private String buildTicketsInfo(String ticketsInfo)
	{
		if (!StringHelper.isEmpty(ticketsInfo))
		{
			return ticketsInfo;
		}

		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("empty");
		builder.closeEntityTag("empty");
		return builder.toString();
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException
	{
		AbstractPaymentSystemPayment paymentSystemPayment = (AbstractPaymentSystemPayment) document;
		FieldValuesSource source = new DocumentFieldValuesSource(metadata,document);

		ExtendedMetadata newMetadata = new ExtendedMetadata();

		newMetadata.setOriginal(metadata);

		// 1. Поля
		FormBuilder formBuilder = getFormBuilder(metadata.getForm());

		List<Field>  fields =  new ArrayList<Field>();
		newMetadata.setExtendedForm(formBuilder.build());

		try
		{
			List<com.rssl.phizic.gate.payments.systems.recipients.Field> extendedFields = paymentSystemPayment.getExtendedFields();
			if (CollectionUtils.isEmpty(extendedFields))
			{
				return newMetadata;
			}

			for (com.rssl.phizic.gate.payments.systems.recipients.Field fieldExtFlds : paymentSystemPayment.getExtendedFields())
			{
				FieldBuilder field = getFieldBuilder(fieldExtFlds.getExternalId(), (String) fieldExtFlds.getValue());
				fields.add(field.build());
			}
			fields.add(getFieldBuilder(JurPayment.BILLING_CODE_ATTRIBUTE_NAME, paymentSystemPayment.getBillingCode()).build());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		formBuilder.addFields(fields);
		newMetadata.setExtendedForm(formBuilder.build());

		// 2. Справочники
		newMetadata.addAllDictionaries(metadata.getDictionaries());

		addDictionaries(source, document, newMetadata);

		return newMetadata;
	}

	/**
	 * @param document документ.
	 * @return информация о списке возвратов.
	 */
	private String buildRefundGoods(BusinessDocument document)
	{
		try
		{
			String uuid = ((JurPayment) document).getOrderUuid();
			XmlEntityBuilder builder = new XmlEntityBuilder();
			builder.openEntityTag("RefundList");

			for (BusinessDocument doc : ShopHelper.get().getRecallsByOrder(uuid))
			{
				if (!(doc instanceof RefundGoodsClaim) && !doc.getState().getCode().equals("EXECUTED"))
					continue;

				RefundGoodsClaim claim = (RefundGoodsClaim) doc;
				String goods = claim.getReturnedGoods();

				Money money = claim.getChargeOffAmount();
				builder.openEntityTag("Refund")
						.append(goods)
						.openEntityTag("Price")
						.createEntityTag("Amount", money.getDecimal().toString())
						.createEntityTag("Currency", money.getCurrency().getCode())
						.closeEntityTag("Price")
						.closeEntityTag("Refund");
			}

			builder.closeEntityTag("RefundList");

			return builder.toString();
		}
		catch (Exception ex)
		{
			log.error("ошибка при построении информации о списке возвратов", ex);
			return "<RefundList></RefundList>";
		}
	}

	public Metadata load(Metadata metadata, TemplateDocument template)
	{
		throw new UnsupportedOperationException();
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template)
	{
		throw new UnsupportedOperationException();
	}

	private Element buildDictionaryFromXmlField(String xmlField) throws BusinessException
	{
		try
		{
			Document document = XmlHelper.parse(xmlField);
			return document.getDocumentElement();
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
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	private FormBuilder getFormBuilder(Form form)
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(form.getDescription());
		formBuilder.addFields(form.getFields());
		formBuilder.setFormValidators(form.getFormValidators());

		return formBuilder;
	}

	private FieldBuilder getFieldBuilder(String name, String value)
	{
		FieldBuilder field = new FieldBuilder();
		field.setName(name);
		field.setType(new StringType().getName());
		if (value != null)
		{
			Expression expression = new ConstantExpression(value);
			field.setValueExpression(expression);
			field.setInitalValueExpression(expression);
		}
		field.setSource(String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, name));

		return field;
	}
}
