package com.rssl.phizgate.common.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.List;

import static com.rssl.phizgate.common.payments.ExtendedFieldsHelper.serialize;

/**
 * @author krenev
 * @ created 11.01.2011
 * @ $Author$
 * @ $Revision$
 * хелпер дл€ сериализации десериализации полей
 */
public class ExtendedFieldsHelper
{

	/**
	 * —ериализаци€ списка полей в строку в формате xml
	 * @param fields список полей
	 * @return строка с описанием полей
	 */
	public static String serialize(List<? extends Field> fields) throws DocumentException
	{
		return serialize(DocumentConfig.FORMAT.XML, fields);
	}

	/**
	 * —ериализаци€ списка полей в строку в заданном формате
	 * ≈сли формат не задан, используетс€ формат из настройки
	 * @param fields список полей
	 * @param format формат
	 * @return строка с описанием полей
	 */
	public static String serialize(DocumentConfig.FORMAT format, List<? extends Field> fields) throws DocumentException
	{
		if (fields == null)
		{
			return null;
		}
		if (format == null)
		{
			format = ConfigFactory.getConfig(DocumentConfig.class).getDocumentsExtendedFieldsFormat();
		}
		switch (format)
		{
			case XML:
				return new ExtendedFieldsXMLSerializer(fields).serialize();
			case GSON:
				return serializeGSON(fields);
			default:
				throw new DocumentException("Ќеизвестный формат описани€ полей "+fields);
		}
	}

	private static String serializeGSON(List<? extends Field> fields) throws DocumentException
	{
		try
		{
			return new ExtendedFieldsGSONSerializer(fields).serialize();
		}
		catch (IOException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * десериализовать строку в список полей
	 * @param fields строка с данными о пол€х, полученна€ методом serialize
	 * @return десериализованна€ строка.
	 */
	public static List<Field> deserialize(String fields) throws DocumentException
	{
		if (StringHelper.isEmpty(fields))
		{
			return null;
		}

		switch (getFormat(fields))
		{
			case XML:
				return new ExtendedFieldsSAXSource(fields).getSource();
			case GSON:
				return parseGSON(fields);
			default:
				throw new DocumentException("Ќеизвестный формат описани€ полей " + fields);
		}
	}

	private static List<Field> parseGSON(String fields) throws DocumentException
	{
		try
		{
			return new ExtendedFieldsGSONParser(fields).parse();
		}
		catch (IOException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * десериализовать строку в DOM
	 * @param fields строка с данными о пол€х, полученна€ методом serialize
	 * @return DOM. десериализованна€ строка.
	 */
	public static Document deserializeToDOM(String fields) throws DocumentException
	{
		if (StringHelper.isEmpty(fields))
		{
			return null;
		}
		try
		{
			DocumentConfig.FORMAT format = getFormat(fields);
			if (format == DocumentConfig.FORMAT.GSON)
			{
				//ѕока делаем такие сериализации/десериализации... после, если потребуетс€, смотрим на стримингJSONапи...
				fields = serialize(DocumentConfig.FORMAT.XML, deserialize(fields));
			}
			return XmlHelper.parse(fields);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * перестроить описание расширенных полей в текущий формат... при необходимости.
	 * @param fields строка с данными о пол€х, полученна€ методом serialize
	 * @return строка с данными о пол€х в формате текущей настройки.
	 * @throws DocumentException
	 */
	public static String rebuild(String fields) throws DocumentException
	{
		if (StringHelper.isEmpty(fields))
		{
			return fields; // реформат не требуетс€.. испольуем входное заначение
		}
		DocumentConfig.FORMAT fieldsFormat = getFormat(fields);
		DocumentConfig.FORMAT currentFormat = ConfigFactory.getConfig(DocumentConfig.class).getDocumentsExtendedFieldsFormat();
		if (fieldsFormat == currentFormat)
		{
			return fields; // реформат не требуетс€.. испольуем входное заначение
		}
		return serialize(currentFormat, deserialize(fields)); //ѕереформатирвем в текущий формат..
	}

	/**
	 * получить формат сериализованного описани€ полей.
	 * @param fields строка с данными о пол€х, полученна€ методом serialize
	 * @return формат строки
	 */
	public static DocumentConfig.FORMAT getFormat(String fields)
	{
		if (StringHelper.isEmpty(fields))
		{
			return null;
		}
		if (fields.startsWith("<Attributes>"))
		{
			return DocumentConfig.FORMAT.XML;
		}
		return DocumentConfig.FORMAT.GSON;
	}
}
