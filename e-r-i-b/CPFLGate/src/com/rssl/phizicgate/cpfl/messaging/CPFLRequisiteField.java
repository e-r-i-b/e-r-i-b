package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 22.02.2011
 * @ $Author$
 * @ $Revision$
 * Объектное представление поля реквизита.
 */
class CPFLRequisiteField
{
	//мапинг значений поля templates на типы полей ИКФЛ.
	private static final Map<String, FieldDataType> templatesMap = new HashMap<String, FieldDataType>();
	//мапинг значений поля templates на множество допустимых символов(рег выражение).
	private static final Map<String, String> templatesRegexpsMap = new HashMap<String, String>();

	static
	{
		templatesMap.put("D", FieldDataType.integer);
		templatesMap.put("M", FieldDataType.integer);
		templatesMap.put("G", FieldDataType.integer);
		templatesMap.put("9", FieldDataType.integer);
		templatesMap.put("R", FieldDataType.integer);
		templatesMap.put("K", FieldDataType.integer);
		templatesMap.put("T", FieldDataType.list);
		templatesMap.put("U", FieldDataType.list);
		templatesMap.put("P", FieldDataType.string);//цифры и пробел
		templatesMap.put("A", FieldDataType.string);//буквы и пробел
		templatesMap.put("N", FieldDataType.string);//буквы и цифры		
		templatesMap.put("X", FieldDataType.string);//любые печатные символы
		templatesMap.put("S", FieldDataType.string);//любые печатные символы

		templatesRegexpsMap.put("D", "\\d");
		templatesRegexpsMap.put("M", "\\d");
		templatesRegexpsMap.put("G", "\\d");
		templatesRegexpsMap.put("9", "\\d");
		templatesRegexpsMap.put("R", "\\d");
		templatesRegexpsMap.put("K", "\\d");
		templatesRegexpsMap.put("P", "[\\d ]");//цифры и пробел
		templatesRegexpsMap.put("A", "[a-zA-ZА-Яа-я ]");//буквы и пробел
		templatesRegexpsMap.put("N", "[a-zA-ZА-Яа-я\\d]");//буквы и цифры
		templatesRegexpsMap.put("X", ".");//любые печатные символы
		templatesRegexpsMap.put("S", ".");//любые печатные символы
	}

	private String prefix;
	private String postfix;
	private Long maxLength;
	private Long minLength;
	private String template;
	private List<String> menu;

	CPFLRequisiteField(Element element) throws GateException
	{
		//Если поле template сопровождается полем length, то максимальное количество символов для ввода определяется
		//значением length. Если поле length отсутствует, то максимальное количество символов для ввода
		//определяется длиной шаблона template. Поле template не может состоять из нескольких различных символов
		template = XmlHelper.getSimpleElementValue(element, "template");
		int templateLength = template.length();
		if (templateLength > 1)
		{
			template = template.substring(0, 1);
		}
		if (!templatesMap.containsKey(template))
		{
			throw new GateException("Неизвестный тип шаблона " + template);
		}

		if ("U".equals(template))
		{
			menu = new ArrayList<String>();
			try
			{
				XmlHelper.foreach(element, "./menu", new ForeachElementAction()
				{
					public void execute(Element element) throws Exception
					{
						menu.add(element.getTextContent());
					}
				});
			}
			catch (Exception e)
			{
				throw new GateException(e);
			}
		}
		else
		{
			//Данные поля не используются при template = «U»
			String lengthValue = XmlHelper.getSimpleElementValue(element, "length");
			maxLength = StringHelper.isEmpty(lengthValue) ? templateLength : Long.valueOf(lengthValue);
			String minlengthValue = XmlHelper.getSimpleElementValue(element, "minLength");
			minLength = StringHelper.isEmpty(minlengthValue) ? 0 : Long.valueOf(minlengthValue);
			prefix = XmlHelper.getSimpleElementValue(element, "prefix");
			postfix = XmlHelper.getSimpleElementValue(element, "postfix");
		}
	}

	FieldDataType getType()
	{
		return templatesMap.get(template);
	}

	// Если шаблон в поле template содержит символы отличные от P, A, S, X, то поле enteredData является
	boolean isRequired()
	{
		return !"P".equals(template) && !"A".equals(template) && !"S".equals(template) && !"X".equals(template);
	}

	String getPrefix()
	{
		return prefix;
	}

	String getPostfix()
	{
		return postfix;
	}

	Long getMaxLength()
	{
		return maxLength;
	}

	Long getMinLength()
	{
		return minLength;
	}

	String getTemplate()
	{
		return template;
	}

	List<String> getMenu()
	{
		return menu;
	}

	/**
	 * @return регулярное выражение, сообветствующее формату поля (на основе признаков template с учетом длин)
	 */
	String getRegexp()
	{
		if (FieldDataType.list == getType())
		{
			return null; //для выпадающих списков нет рег выражений.
		}
		return getAllowedChars() + "{" + minLength + "," + maxLength + "}";
	}

	/**
	 * @return рег выражение, описывающее множество допустимых символов.
	 */
	String getAllowedChars()
	{
		String regexp = templatesRegexpsMap.get(template);
		if (regexp == null)
		{
			throw new IllegalStateException("Неизвестно выражение, описывающее множество допустимых символов шаблона " + template);
		}
		return regexp;
	}
}
