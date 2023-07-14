package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizgate.common.ws.AnyType;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

/**
 * @author gulov
 * @ created 21.02.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRResponseHelper extends BaseResponseHelper
{
	/**
	 * Количество попыток рандома для генерации существования выписки из ПФР
	 */
	private static final int RANDOM_RESPONSE_EXISTS = 10;

	/**
	 * Количество попыток рандома для генерации statusCode
	 */
	private static final int RANDOM_STATUS_CODE = 10;

	/**
	 * Файл xml
	 */
	private static final String XML_FILE_NAME = "com/rssl/phizic/test/webgate/esberib/utils/xml/pfrInfo.xml";

	/**
	 * Варианты извещений
	 */
	private static final String NOTE_6_1 = "СЗИ-6-1";

	private static final String NOTE_6_2 = "СЗИ-6-2";

	private static final String NOTE_6_3 = "СЗИ-6-3";

	private static final String NOTE_6_4 = "СЗИ-6-4";

	private static final String NOTE_6_5 = "СЗИ-6-5";

	private static final String NOTE_6_6 = "СЗИ-6-6";

	private static final String TAG_PREFIX = "ПачкаИсходящихДокументов/ИЗВЕЩЕНИЕ_О_СОСТОЯНИИ_ИЛС";

	private static final Map<Integer, String> STATUS_CODE_SPEC;

	static
	{
		STATUS_CODE_SPEC = new HashMap<Integer, String>(5);

		STATUS_CODE_SPEC.put(-801, "Вы не зарегистрированы в системе информационного обмена Пенсионного фонда. " +
			"Для получения выписки необходимо подать заявление в любом подразделении Сбербанка России.");
		STATUS_CODE_SPEC.put(-802, "Вы указали некорректный страховой номер индивидуального лицевого счета");
		STATUS_CODE_SPEC.put(-803, "Есть клиенты с указанным ФИО, но среди них нет ни одного c указанной серией и номером паспорта (документа)");
		STATUS_CODE_SPEC.put(-804, "Вы не зарегистрированы в системе информационного обмена Пенсионного фонда. " +
			"Для получения выписки необходимо подать заявление в любом подразделении Сбербанка России.");
		STATUS_CODE_SPEC.put(-810, "На ваш запрос получен отказ из Пенсионного Фонда. Внутренняя ошибка");
	}


	public IFXRs_Type createHasInfoInqRs(IFXRq_Type parameters)
	{
		PfrHasInfoInqRq_Type request = parameters.getPfrHasInfoInqRq();
		PfrHasInfoInqRs_Type response_type = new PfrHasInfoInqRs_Type();

		response_type.setRqUID(request.getRqUID());
		response_type.setRqTm(getRqTm());
		response_type.setOperUID(request.getOperUID());

		PfrResult_Type result = new PfrResult_Type();
		result.setSendTime(getStringDateTime(getRandomDate()));
		result.setReceiveTime(getStringDateTime(getRandomDate()));
		result.setResponseExists(getResponseExists());
		response_type.setResult(result);
		response_type.setStatus(getPFRStatus());

		IFXRs_Type response = new IFXRs_Type();
		response.setPfrHasInfoInqRs(response_type);

		return response;
	}

	public IFXRs_Type createGetInfoInqRs(IFXRq_Type parameters) throws Exception
	{
		PfrGetInfoInqRq_Type request = parameters.getPfrGetInfoInqRq();
		PfrGetInfoInqRs_Type response_type = new PfrGetInfoInqRs_Type();

		response_type.setRqUID(request.getRqUID());
		response_type.setRqTm(getRqTm());
		response_type.setOperUID(request.getOperUID());

		PfrResult_Type result = new PfrResult_Type();
		result.setSendTime(getStringDateTime(getRandomDate()));
		result.setReceiveTime(getStringDateTime(getRandomDate()));
		result.setResponseExists(getResponseExists());
		response_type.setResult(result);
		response_type.setStatus(getPFRStatus());
		response_type.setPfrInfo(makePFRInfo());

		IFXRs_Type response = new IFXRs_Type();
		response.setPfrGetInfoInqRs(response_type);

		return response;
	}

	private Object makePFRInfo() throws Exception
	{
		Document document = XmlHelper.loadDocumentFromResource(XML_FILE_NAME);

		String noteType = getNoteType();
		findAndSetElementText(document, TAG_PREFIX + "/ВариантИзвещения", noteType);
		findAndSetElementText(document, TAG_PREFIX + "/ДатаПоСостояниюНа", getStringDate(getRandomDate()));

		findAndSetElementText(document, TAG_PREFIX + "/СтраховойНомер", "123-123-123 45");
		findAndSetElementText(document, TAG_PREFIX + "/ФИО/Фамилия", "Заглушко");
		findAndSetElementText(document, TAG_PREFIX + "/ФИО/Имя", "Пенсион");
		findAndSetElementText(document, TAG_PREFIX + "/ФИО/Отчество", "Фондович");

		Element prefixElement = XmlHelper.selectSingleNode(document.getDocumentElement(), TAG_PREFIX);

		XmlHelper.appendSimpleElement(prefixElement, "ДатаПоследнегоОбновления", getStringDate(getRandomDate()));

		if (!noteType.equals(NOTE_6_6))
		{
			XmlHelper.appendSimpleElement(prefixElement, "Год", getRandomYear());
			XmlHelper.appendSimpleElement(prefixElement, "НакопительныеЗаГод", getRandomDecimal().toString());
			XmlHelper.appendSimpleElement(prefixElement, "ГодДохода", getRandomYear());
			XmlHelper.appendSimpleElement(prefixElement, "ДоходЗаГод", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_1) || noteType.equals(NOTE_6_2))
		{
			XmlHelper.appendSimpleElement(prefixElement, "НаименованиеУК", "НаименованиеУК");
			XmlHelper.appendSimpleElement(prefixElement, "СуммаСПНпереданныхВУК", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_1))
		{
			XmlHelper.appendSimpleElement(prefixElement, "ПериодИнвестирования", getRandomYear());
			XmlHelper.appendSimpleElement(prefixElement, "РезультатИнвестирования", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_2) || noteType.equals(NOTE_6_3))
		{
			XmlHelper.appendSimpleElement(prefixElement, "ДоходОтИнвестирования", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_3) || noteType.equals(NOTE_6_4))
		{
			XmlHelper.appendSimpleElement(prefixElement, "НаименованиеНПФ", "НаименованиеНПФ");
		}

		if (noteType.equals(NOTE_6_3))
		{
			XmlHelper.appendSimpleElement(prefixElement, "СуммаСПНпереданныхВНПФ", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_5))
		{
			XmlHelper.appendSimpleElement(prefixElement, "СуммаСПНпереданныхИзНПФ", getRandomDecimal().toString());
		}

		makeTaxPayment(document);

		Element last = XmlHelper.appendSimpleElement(prefixElement, "СведениеОбИнформировании");
		XmlHelper.appendSimpleElement(last, "ПризнакВыдачиДокумента");
		XmlHelper.appendSimpleElement(last, "ТипВыданногоДокумента");
		XmlHelper.appendSimpleElement(last, "СпособВыдачиДокумента");
		XmlHelper.appendSimpleElement(last, "ДатаВыдачиДокумента");

		XmlHelper.appendSimpleElement(prefixElement, "ПорядковыйНомерЗапросаВтекущемГоду", String.valueOf(new Random().nextInt(1000) + 1));

		Map<String,String> properties = new HashMap<String, String>();
		properties.put(OutputKeys.INDENT, "yes");
		properties.put(OutputKeys.OMIT_XML_DECLARATION, "yes");
		return new AnyType(XmlHelper.setPropsAndConvertDomToText(document, properties));
	}

	protected BigDecimal getRandomDecimal()
	{
		return super.getRandomDecimal().setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	private void makeTaxPayment(Document document) throws TransformerException
	{
		Random random = new Random();

		int counter = random.nextInt(9) + 1;

		Element prefixElement = XmlHelper.selectSingleNode(document.getDocumentElement(), TAG_PREFIX);

		Element root;

		for (int i = 0; i <= counter; i++)
		{
			root = XmlHelper.appendSimpleElement(prefixElement, "СведенияОвзносахНаСтраховую");

			XmlHelper.appendSimpleElement(root, "Год", getRandomYear());
			XmlHelper.appendSimpleElement(root, "ПоступилоНа01.01.года", getRandomDecimal().toString());
			XmlHelper.appendSimpleElement(root, "ПоступилоЗаГод", getRandomDecimal().toString());
		}
	}

	private String getRandomYear()
	{
		Random random = new Random();

		String value = String.valueOf(random.nextInt(10) + 1);

		return "20" + (value.length() < 2 ? "0" + value : value);
	}

	private String getNoteType()
	{
		Random random = new Random();

		int value = random.nextInt(6) + 1;

		return "СЗИ-6-" + String.valueOf(value);
	}

	private void findAndSetElementText(Document document, String elementName, String text) throws TransformerException
	{
		Element element = XmlHelper.selectSingleNode(document.getDocumentElement(), elementName);

		if (element != null)
			setElementText(element, text);
	}

	private void setElementText(Element element, String text)
	{
		String tagText = element.getTextContent();

		if (tagText.equals(""))
			element.setTextContent(text);
	}

	private boolean getResponseExists()
	{
		Random random = new Random();

		int i = random.nextInt(RANDOM_RESPONSE_EXISTS);

		return i == 0 ? false : true;
	}

	private Status_Type getPFRStatus()
	{
		Random random = new Random();

		int i = random.nextInt(RANDOM_STATUS_CODE);

		if (i == 0)
		{
			i = random.nextInt(5);
			int j = 0;

			for (Iterator<Map.Entry<Integer, String>> iterator = STATUS_CODE_SPEC.entrySet().iterator(); iterator.hasNext(); j++)
			{
				if (j == i)
				{
					Map.Entry<Integer, String> entry = iterator.next();

					return new Status_Type(entry.getKey(), entry.getValue(), "", SERVER_STATUS_DESC);
				}
			}
		}
		return new Status_Type(0, "", "", "");
	}

	private String getSNILS()
	{
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		String format = String.format("%%0%dd", 3);
		sb.append(String.format(format, random.nextInt(999) + 1));
		sb.append("-");
		sb.append(String.format(format, random.nextInt(999) + 1));
		sb.append("-");
		sb.append(String.format(format, random.nextInt(999) + 1));
		sb.append(" ");
		format = String.format("%%0%dd", 2);
		sb.append(String.format(format, random.nextInt(99) + 1));
		return sb.toString();
	}

	//Возвращает дату в формате 15.03.2011
	protected String getStringDate(Calendar date)
	{
		return new SimpleDateFormat("dd.MM.yyyy").format(DateHelper.toDate(date));
	}
}
