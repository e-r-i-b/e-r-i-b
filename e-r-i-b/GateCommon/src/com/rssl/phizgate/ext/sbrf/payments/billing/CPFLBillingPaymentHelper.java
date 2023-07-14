package com.rssl.phizgate.ext.sbrf.payments.billing;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.ListField;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 21.02.2011
 * @ $Author$
 * @ $Revision$
 * содержит утилитные функции для работы с секциями NplatRequisites.
 * Данная секция необходима при отправке запросов на подготовку платежа в ЦПФЛ и на исполнение в ЦОД
 */
public class CPFLBillingPaymentHelper extends BillingPaymentHelper
{
	//псевдокод недоговорного получателя ЦПФЛ.
	public static final Comparable NOT_CONTRACTUAL_RECEIVER_CODE = "$NCRC180804$";

	//Максимальное длина для хранения значения доп поля (например если хранить в оракле - это 4000 байт для varchar2)
	private static final int MAX_FIELD_VALUE_SIZE = 4000;//используем макс значение для varchar2 оракла

	private static final String NPLAT_REQUISITES_FIELD_NAME = "NplatRequisites";
	protected static final String SPECAL_CLIENT_FIELD_PEFIX = "SpecalClient-";
	private static final String ACK_CLIENT_BANK_CAN_CHANGE_SUMM_FIELD_NAME = "AckClientBankCanChangeSumm";
	private static final String COMMISSION_LABEL_FIELD_NAME = "commissionLabel";
	private static final String SERVICE_CODE_FIELD_NAME = "serviceCode";
	private static final String SERVICE_KIND_FIELD_NAME = "ServiceKind";
	private static final String TARIFF_VALUE_FIELD_NAME = "tariffValue";
	public static final String RECEIVER_NAME_FIELD_NAME = "temporaryReceiverName";

	/**
	 * Обновить (или создать при отсутсвии) секцию с информацией о реквизитах для наличных платежей
	 * @param payment платеж, в который надо сохранить секцию
	 * @param element элемент nplatRequisites
	 */
	public static void updateDocumentNplatRequisitesInfo(AbstractPaymentSystemPayment payment, Element element) throws GateException, GateLogicException
	{
		storeDocumentHiddenFieldValue(payment, NPLAT_REQUISITES_FIELD_NAME, encodeBase64(element));
	}

	/**
	 * Получить узел секции NplatRequisites из документа
	 * @param payment документ
	 * @return NplatRequisites. может отсутсвовать(в случае первого запроса в ЦПФЛ на подготовку платежа)
	 */
	public static Document getNplatRequisites(AbstractPaymentSystemPayment payment) throws GateException
	{
		String value = restoreDocumentHiddenFieldValue(payment, NPLAT_REQUISITES_FIELD_NAME);
		return parseXml(decodeBase64(value));
	}

	/**
	 * Создать скрытое доп поле
	 * @param name имя поля
	 * @return доп. поле
	 * @throws GateException
	 */
	public static CommonField createHiddenField(String name) throws GateException
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(name);
		field.setName(name);
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(false);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(false);
		return field;
	}

	/**
	 * Сохранить в документе в скрытом поле(полях) значение
	 * Значение может быть сохранено в нескольких полях (если превышвет MAX_FIELD_VALUE_SIZE).
	 * Сохраненные таким образом значения требуется получать вызовом restoreDocumentHiddenFieldValue.
	 * @param payment документ, в котором требуется сохранить значение
	 * @param name имя под которым сохраняется значение и под которым возможно будет восстановление.
	 * Если в платеже уже имется значение с таким именем, то старое значение будет заменено
	 * @param value значение для сохранения.
	 */
	protected static void storeDocumentHiddenFieldValue(AbstractPaymentSystemPayment payment, String name, String value) throws GateException, GateLogicException
	{
		//значения сохраняем документе порциями по MAX_FIELD_VALUE_SIZE в скрытых полях с именами name_i, где i - номер порции(части).
		try
		{
			String fieldNamePrefix = name + "_";
			List<Field> fields = payment.getExtendedFields();
			if (fields == null)
			{
				fields = new ArrayList<Field>();
			}
			//сначала грохнем все старые поля
			for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();)
			{
				Field field = iterator.next();
				if (field.getName().startsWith(fieldNamePrefix) && field.getExternalId().startsWith(fieldNamePrefix))
				{
					iterator.remove();
				}
			}
			//теперь создаем поля
			int partsCount = value.length() / MAX_FIELD_VALUE_SIZE + 1; //количество полей(частей), в которых будет сохранено значение.
			for (int i = 0; i < partsCount; i++)
			{
				CommonField field = createHiddenField(fieldNamePrefix + i);
				String part = value.substring(i * MAX_FIELD_VALUE_SIZE, Math.min((i + 1) * MAX_FIELD_VALUE_SIZE, value.length()));
				field.setDefaultValue(part);
				fields.add(field);
			}
			payment.setExtendedFields(fields);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * получить значение, сохраненное в скрытом поле документа методом storeDocumentHiddenFieldValue
	 * @param payment документ
	 * @param name имя, под которым сохранено значение
	 * @return сохраненное значение
	 */
	protected static String restoreDocumentHiddenFieldValue(AbstractPaymentSystemPayment payment, String name) throws GateException
	{
		StringBuilder result = new StringBuilder();
		for (int i = 0; ; i++)
		{
			Field field = getFieldById(payment, name + "_" + i);
			if (field == null)
			{
				if (i == 0)
				{
					return null;
				}
				return result.toString();
			}
			result.append(field.getDefaultValue());
		}
	}

	protected static String decodeBase64(String value)
	{
		if (value == null)
		{
			return null;
		}
		return new String(Base64.decodeBase64(value.getBytes()));
	}

	protected static String encodeBase64(Node element) throws GateException
	{
		if (element == null)
		{
			return null;
		}
		try
		{
			return new String(encodeBase64(XmlHelper.convertDomToText(element)));
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private static String encodeBase64(String value)
	{
		if (value == null)
		{
			return null;
		}
		return new String(Base64.encodeBase64(value.getBytes()));
	}

	protected static Document parseXml(String value) throws GateException
	{
		if (value == null)
		{
			return null;
		}
		try
		{
			return XmlHelper.parse(value);
		}
		catch (ParserConfigurationException e)
		{
			throw new GateException(e);
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * получаем код спецклиента:
	 * 1) берем значение NplatRequisites/specialClientCodeOrg, если есть
	 * 2) если нет - значение атрибута  specialClient/specialClientNplat/specialClientCode с максимальным значением  vvodNumber
	 * @param payment платеж
	 * @return код спецклиента или null, если его нет.
	 * @throws GateException
	 */
	public static String getSpecClientCode(CardPaymentSystemPayment payment) throws GateException
	{
		//пытаемся найти NplatRequisites/specialClientCodeOrg
		String specialClientCodeOrg = XmlHelper.getSimpleElementValue(getNplatRequisites(payment).getDocumentElement(), "specialClientCodeOrg");
		if (!StringHelper.isEmpty(specialClientCodeOrg))
		{
			return specialClientCodeOrg;
		}
		//находим последнюю секцию specialClient
		String specalClient = null;
		for (int i = 0; ; i++)
		{
			String tmpspecalClient = restoreDocumentHiddenFieldValue(payment, SPECAL_CLIENT_FIELD_PEFIX + (i + 1));//vvodNumber нумеруется с 1
			if (tmpspecalClient == null)
			{
				break;
			}
			specalClient = tmpspecalClient;
		}
		//не нашли - кода спец клиента нет.
		if (specalClient == null)
		{
			return null;
		}
		try
		{
			return XmlHelper.getElementValueByPath(parseXml(decodeBase64(specalClient)).getDocumentElement(), "/specialClient/specialClientNplat/specialClientCode");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * создать поле для хранения признака ackClientBankCanChangeSumm.
	 * @return поле.
	 */
	public static Field createAckClientBankCanChangeSumm() throws GateException
	{
		//запрос от клиента требуется - создаем поле-список.
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(ACK_CLIENT_BANK_CAN_CHANGE_SUMM_FIELD_NAME);
		field.setName("Разрешить изменение суммы платежа при изменении тарифа");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(true);
		field.setRequiredForConformation(true);
		List<ListValue> values = new ArrayList<ListValue>();
		values.add(new ListValue("Да", "true"));
		values.add(new ListValue("Нет", "false"));
		field.setValues(values);
		return field;
	}

	/**
	 * Получить значение поля ackClientBankCanChangeSumm
	 * @param transfer платеж
	 * @return значение поля.
	 */
	public static String getAckClientBankCanChangeSumm(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, ACK_CLIENT_BANK_CAN_CHANGE_SUMM_FIELD_NAME);
		if (field == null)
		{
			return "false";
		}
		return (String) field.getValue();
	}

	/**
	 * создать скрытое поле для хранениея serviceCode
	 * @param serviceCode -serviceCode
	 * @return поле
	 * @throws GateException
	 */
	public static Field createHiddenServiceCode(String serviceCode) throws GateException
	{
		CommonField result = createHiddenField(SERVICE_CODE_FIELD_NAME);
		result.setDefaultValue(serviceCode);
		result.setValue(serviceCode);
		return result;
	}

	/**
	 * получить из платежа значение ServiceCode
	 * @param transfer платеж
	 * @return значние или null, если отсутсвует
	 * @throws GateException
	 */
	public static String getServiceCode(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, SERVICE_CODE_FIELD_NAME);
		if (field == null)
		{
			return null;
		}
		return (String) field.getValue();
	}

	/**
	 * создать скрытое поле для хранениея commissionLabel
	 * @param commissionLabel -commissionLabel
	 * @return поле
	 * @throws GateException
	 */
	public static Field createHiddenCommissionLabel(String commissionLabel) throws GateException
	{
		CommonField result = createHiddenField(COMMISSION_LABEL_FIELD_NAME);
		result.setDefaultValue(commissionLabel);
		result.setValue(commissionLabel);
		return result;
	}

	/**
	 * получить из платежа значение CommissionLabel
	 * @param transfer платеж
	 * @return значние или null, если отсутсвует
	 * @throws GateException
	 */
	public static String getCommissionLabel(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, COMMISSION_LABEL_FIELD_NAME);
		if (field == null)
		{
			return null;
		}
		return (String) field.getValue();
	}

	/**
	 * создать поля для тарифов. тариф в ЦПФЛ имеет 3 значения код, наименование, и занчение.
	 * наименование отображается клиенту.
	 * создается 2 поля-списка:  код-наименвоание(видимое поле), код-значение(скрытое поле)
	 * скрытое поле потом используется для извления значения.
	 * @param responce ответ ЦПФЛ
	 * @return поля или null, если тарифов в ответе нет.
	 * @throws GateException
	 */
	public static List<Field> createTariffFields(Document responce) throws GateException
	{
		try
		{
			NodeList tariffs = XmlHelper.selectNodeList(responce.getDocumentElement(), "tariff");
			if (tariffs.getLength() == 0)
			{
				//тарифов нет
				return null;
			}
			List<Field> result = new ArrayList<Field>();
			//создаем поле для выбора клиентом
			result.add(createServiceKind(tariffs));
			//создаем скрытое поле
			result.add(createHiddenTariffField(tariffs));
			return result;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private static Field createHiddenTariffField(NodeList tariffs)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(TARIFF_VALUE_FIELD_NAME);
		field.setName("Значение тарифа");
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(false);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(false);
		field.setRequiredForConformation(false);

		List<ListValue> values = new ArrayList<ListValue>();
		for (int i = 0; i < tariffs.getLength(); i++)
		{
			Element tariff = (Element) tariffs.item(i);
			values.add(new ListValue(XmlHelper.getSimpleElementValue(tariff, "tariffValue"), XmlHelper.getSimpleElementValue(tariff, "tariffCode")));
		}
		field.setValues(values);

		return field;
	}

	private static Field createServiceKind(NodeList tariffs)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(SERVICE_KIND_FIELD_NAME);
		field.setName("Тариф");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(true);
		field.setRequiredForConformation(true);

		List<ListValue> values = new ArrayList<ListValue>();
		for (int i = 0; i < tariffs.getLength(); i++)
		{
			Element tariff = (Element) tariffs.item(i);
			values.add(new ListValue(XmlHelper.getSimpleElementValue(tariff, "tariffName"), XmlHelper.getSimpleElementValue(tariff, "tariffCode")));
		}
		field.setValues(values);

		return field;
	}

	/**
	 * Получить код тарифа из платежа
	 * @param transfer платежа
	 * @return код кода или nul  в случае отсутствия.
	 * @throws GateException
	 */
	public static String getServiceKind(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, SERVICE_KIND_FIELD_NAME);
		if (field == null)
		{
			return null;
		}
		return (String) field.getValue();
	}

	/**
	 * Получить значение тарифа из платежа
	 * @param transfer платежа
	 * @return значение кода или nul  в случае отсутствия.
	 * @throws GateException
	 */
	public static String getTariff(CardPaymentSystemPayment transfer) throws GateException
	{
		//смотри есть ли поле со значениями тарифов
		ListField tariffValuefield = (ListField) getFieldById(transfer, TARIFF_VALUE_FIELD_NAME);
		if (tariffValuefield == null)
		{
			return null;
		}
		//получаем поле, которое выбирал клиент
		Field serviceKindField = getFieldById(transfer, SERVICE_KIND_FIELD_NAME);
		if (tariffValuefield == null)
		{
			throw new GateException("Неконсистентное состояние документа: поле с тарифами есть, а со значениями нет. Идентфикатор платежа: " + transfer.getId());
		}
		//ищем в скрытом поле значение тарифа соответсвующее, коду который выбрал клиент по наименванию... брррр...))
		String tariffCode = (String) serviceKindField.getValue();
		for (ListValue listValue : tariffValuefield.getValues())
		{
			if (tariffCode.equals(listValue.getId()))
			{
				return listValue.getValue();
			}
		}
		throw new GateException("Неконсистентное состояние документа: не найдено значение тарифа для коджа тарифа " + tariffCode + ". Идентфикатор платежа: " + transfer.getId());
	}

	/**
	 * Проверяет является ли переданный идентфикатор договорником
	 * @param receiverPointCode идентфикатор получателя
	 * @return да/нет.
	 */
	public static boolean isContractual(String receiverPointCode) {
		if (StringHelper.isEmpty(receiverPointCode))
		{
			return false;
		}
		if (receiverPointCode.startsWith("-"))
		{
			//По спецификации '-' это значение не долж-но использоваться в качестве уникального идентификатора организа-ции при взаимодействии с АС, т.е. организация должна считаться «недоговорной».
			return false;
		}
		return !CPFLBillingPaymentHelper.NOT_CONTRACTUAL_RECEIVER_CODE.equals(receiverPointCode);
	}

	/**
	 * создает временное поле для хранения названия получателя.
	 * @return
	 */
	public static Field createReceiverNameField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(RECEIVER_NAME_FIELD_NAME);
		field.setName("Наименование получателя");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(false);
		field.setRequiredForConformation(false);
		return field;
	}
}
