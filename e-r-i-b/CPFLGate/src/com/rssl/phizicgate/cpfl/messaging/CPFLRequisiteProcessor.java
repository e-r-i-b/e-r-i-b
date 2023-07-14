package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 22.02.2011
 * @ $Author$
 * @ $Revision$
 * Обработчик реквизита ЦПФЛ.
 * Реквизит может состоять из нескольких полей.
 * Но для ИКФЛ(с точки зрения гейтовго интерфейса) - это все равно будет 1 поле, для ввода клиентом
 * в случае составного реквизита клинент должен будет вводить сам разделители:
 * пример реквизита:
 * <requisites>
 *   <name>N телефона:</name>
 *   <field>
 *     <template>P</template>
 *     <length>3</length>
 *     <postfix>-</postfix>
 *     <enteredData>499</enteredData>
 *   </field>
 *   <field>
 *     <template>9</template>
 *     <length>3</length>
 *     <postfix>-</postfix>
 *     <enteredData>722</enteredData>
 *   </field>
 *   <field>
 *     <template>9</template>
 *     <length>2</length>
 *     <postfix>-</postfix>
 *     <enteredData>33</enteredData>
 *   </field>
 *   <field>
 *     <template>9</template>
 *     <length>2</length>
 *     <enteredData>30</enteredData>
 *   </field>
 * </requisites>
 * Для даного реквизита должно сформироваться 1 поле для ввода значения в формате XXX-XXX-XX-XX
 *
 * Данный класс инкапсулирует логику формирования валидатора, подсказки для поля, а также логику получения значения
 * поля(как поля реквизита) из документа и некоторые другие методы. 
 */
class CPFLRequisiteProcessor
{
	private GateFactory factory;
	private int number;
	private int vvodNumber;
	private List<CPFLRequisiteField> fields;
	private String name;

	/**
	 * Конструктор обработчика
	 * @param factory фабрика для доступа к гейтовым сервисам.
	 * @param requisites узел requisites
	 * @param number номер реквизитав секции specialClient
	 * @param vvodNumber значение параметра vvodNumber секции specialClient
	 * @throws GateException
	 */
	CPFLRequisiteProcessor(GateFactory factory, Element requisites, int number, int vvodNumber) throws GateException
	{
		this.factory = factory;
		this.number = number;
		this.vvodNumber = vvodNumber;
		//парсим поля в объектное представление
		this.fields = parseFields(requisites);
		this.name = XmlHelper.getSimpleElementValue(requisites, "name");
		if (name.endsWith(":"))
		{
			name = name.substring(0, name.length()-1);//вырезаем конечные ":", если есть.
		}
	}

	private List<CPFLRequisiteField> parseFields(Element requisites) throws GateException
	{
		final List<CPFLRequisiteField> result = new ArrayList<CPFLRequisiteField>();
		try
		{
			XmlHelper.foreach(requisites, "./field", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					result.add(new CPFLRequisiteField(element));
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		return result;
	}

	/**
	 * Сгенерировать внешний идентфикатор для реквизита
	 * @return внешний идентфикатор реквизита.
	 */
	String getExternalId()
	{
		return "extended-field-" + number + "_" + vvodNumber;
	}

	/**
	 * Получить тип поля ИКФЛ.
	 * Реквизиты, состояшие из 1 поля, имеют тип соответсвующий признаку template
	 * Реквизиты, состояшие из из нескольких полей, имеют стринг.
	 * @return тип поля ИКФЛ.
	 */
	FieldDataType getType()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).getType();
		}
		return FieldDataType.string;
	}

	/**
	 * Формирование подсказки для заполнения.
	 * Определения:
	 * 1) Однородные поля - поля, дополнение пересечения множеств
	 * допустимых символов которых содержит только пробел.
	 * 2) Группа - это последовательность однородных полей. Для обозначения каждой группы используется свой символ X, Y, Z, A, B, С...
	 * Правила:
	 * 1) для списочных полей не используем подсказки для заполнениея .
	 * 2) если реквизит не содержит полей с префиксами/суффиксами и все поля однородные
	 * то используем подсказку вида:
	 * Укажите <имя реквизита>, <подсказка по количеству и множеству допустимых символов>
	 * Пример: Укажите ИНН получателя, 12 цифр.
	 * 3) если реквизит состоит из полей содержащих суффиксы/префиксы, все поля однородные и общая длина реквизита не более 20,
	 * то используем подсказку вида:
	 * Укажите <имя реквизита> в формате <префикс поля 1>XXX<суффикс поля 1><префикс поля 2>XXX<суффикс поля 3>, <подсказка по количеству и множеству допустимых символов>
	 * где XXX - строка из букв X длиной равной максимальной длине поля
	 * Пример: Укажите номер телефона в формате XXX-XXX-XX-XX, не более 10 цифр
	 * 4)в противных случаях используем формат вида:
	 * Укажите <имя реквизита> в формате <префикс поля 1><ГРУППА_1><суффикс поля 1><префикс поля 2><ГРУППА_1><суффикс поля 3>, где <подсказка по количеству и множеству допустимых символов>
	 * Пример:
	 * Укажите номер телефона в формате XXX-YYY-ZZ-AA, где XXX – не более 3 цифр, YYY- не более 3 цифр, ZZ – 2 буквы, АА –  2 цифры
	 * Длина группы в шаблоне(количество символов) должна быть минимумом из макс длины группы и 5.
	 *
	 * Подсказка по множеству допустимых символов в общем случае имеет вид:
	 * [<символ группы_1> -] [от <мин длина> до] [не более] <макс длина> <описание множества допустимых символов групы 1 за вычетом пробела>,
	 * [<символ группы_2> -] [от <мин длина> до] [не более] <макс длина> <описание множества допустимых символов групы 2 за вычетом пробела> ....
	 * Если реквизит однородный (равносильно, что группа ровно 1) символ групы не выводится и кличество допустимых не учитывает разделители.
	 * @return получить подсказку по заполнению поля
	 */
	String getHint()
	{
		if (FieldDataType.list == getType())
		{
			return null; //для выпадающих списков нет подсказок по заполнению. 
		}
		return generateHint();
	}

	private String generateHint()
	{
		//1) Разбиваем поля реквизитов на последовательные группы однородных полей
		List<CPFLRequisitesFieldGroup> requisiteGroups = new ArrayList<CPFLRequisitesFieldGroup>();
		//помещаем первое поле в первую группу
		CPFLRequisitesFieldGroup currentGroup = new CPFLRequisitesFieldGroup(fields.get(0));
		requisiteGroups.add(currentGroup);
		//ведем обработку со второго поля тк первое полепо умолчанию входит в первую группу
		for (int i = 1; i < fields.size(); i++)
		{
			CPFLRequisiteField curentField = fields.get(i);
			if (currentGroup.isSimilar(curentField))
			{
				//поле однородно текущей группе
				//добавляем его в текущую группу
				currentGroup.add(curentField);
			}
			else
			{
				//поле неоднородно с текущей группой
				//создаем новую группу
				currentGroup = new CPFLRequisitesFieldGroup(curentField);
				//добавляем в список групп.
				requisiteGroups.add(currentGroup);
			}
		}

		//2) Теперь работаем только с однородными группами
		int groupsCount = requisiteGroups.size();
		CPFLRequisitesFieldGroup curentGroup = requisiteGroups.get(0);
		StringBuilder hint = new StringBuilder("Укажите ").append(name).append(" ");
		if (groupsCount == 1)
		{
			if (curentGroup.hasDelimiters())
			{
				//если имеются разделители в группе, то выводим дополнительно формат.
				hint.append(" в формате ");
				hint.append(curentGroup.getTemplate('X'));
			}
			hint.append(", ");
			hint.append(curentGroup.getDescription());
			hint.append(".");
			return hint.toString();
		}
		//иначе попадаем под 4 случай требуется выводить формат и подсказку по множеcтву допустимых символов
		StringBuilder format = new StringBuilder();
		StringBuilder description = new StringBuilder();
		StringBuilder example = new StringBuilder();
		for (int i = 0; i < groupsCount; i++)
		{
			curentGroup = requisiteGroups.get(i);
			char templateChar = i < 3 ? (char) ('X' + i) : (char) ('A' + i - 3);
			format.append(curentGroup.getTemplate(templateChar));
			example.append(curentGroup.getExample());

			description.append(templateChar).append(" - ");
			description.append(curentGroup.getDescription());
			if (i < groupsCount - 1)
			{
				//для непоследней группы выводим ","
				description.append(", ");
			}
		}
		hint.append(" в формате ");
		hint.append(format).append(", где ").append(description).append(". ");
		hint.append("Например: ").append(example).append(".");
		return hint.toString();
	}

	/**
	 * Обязательно ли заполнение поля
	 * Обязательность заполнения определятеся признаком признаку template и
	 * наличием префиксов/суффиксов для составных реквизитов.
	 * @return обязательно.необязательно
	 */
	boolean isRequired()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).isRequired();
		}
		for (CPFLRequisiteField field : fields)
		{
			//если хотя бы 1 поле реквизита ЦПФЛ обязательно или содержит префиксы или поствиксы - юзер должен его заполнить.
			if (field.isRequired() || !StringHelper.isEmpty(field.getPrefix()) || !StringHelper.isEmpty(field.getPostfix()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * получить максимальную длину поля.
	 * Максималоная длина для простого реквизита  соответствует макс длине поля
	 * Для составных реквизитов максимальная длина равна сумме макс длин полей и сумме длинн их префиксов/посфвиксов(их клиент тоже вводит)
	 * @return максимальная длину поля
	 */
	Long getMaxLength()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).getMaxLength();
		}
		long maxLength = 0;
		for (CPFLRequisiteField field : fields)
		{
			maxLength += field.getMaxLength();
			if (field.getPrefix() != null)
			{
				maxLength += field.getPrefix().length();
			}
			if (field.getPostfix() != null)
			{
				maxLength += field.getPostfix().length();
			}
		}
		return maxLength;
	}

	/**
	 * получить минимальную длину поля.
	 * минимальная длина для простого реквизита соотвествует мин длине поля
	 * Для составных реквизитов минималоьную длина равна сумме мин длин полей и сумме длинн их префиксов/посфвиксов(их клиент тоже вводит)
	 * @return минимальная длину поля
	 */
	Long getMinLength()
	{
		if (fields.size() == 1)
		{
			return fields.get(0).getMinLength();
		}
		long maxLength = 0;
		for (CPFLRequisiteField field : fields)
		{
			maxLength += field.getMinLength();
			if (field.getPrefix() != null)
			{
				maxLength += field.getPrefix().length();
			}
			if (field.getPostfix() != null)
			{
				maxLength += field.getPostfix().length();
			}
		}
		return maxLength;
	}

	private static final String VALIDATOR_TYPE = "regexp";

	/**
	 * @return список валидаторов.
	 */
	List<FieldValidationRule> getValidationRules()
	{
		if (FieldDataType.list == getType())
		{
			return null; //для выпадающих списков нет валидаторов.
		}
		List<FieldValidationRule> result = new ArrayList<FieldValidationRule>();
		FieldValidationRuleImpl validator = new FieldValidationRuleImpl();
		String regexp = buildRegex();
		validator.setErrorMessage(getHint());
		validator.setFieldValidationRuleType(FieldValidationRuleType.REGEXP);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(VALIDATOR_TYPE, regexp);
		validator.setParameters(map);
		result.add(validator);
		return result;
	}

	/**
	 * получить список для элементов меню.
	 * Элементы меню могут быть только у простых реквизитов.
	 * если template = T – отсылаем запрос typePaymentList. Формируем для пользователя поле –выпадающий список со значениями из ответа typePaymentList
	 * если template = U – формируем для пользователя списочное поле с множеством знаяений из элементов меню.
	 * @return список элементов или null, если для поля не предусмотрены элементы
	 */
	List<ListValue> getMenu() throws GateLogicException, GateException
	{
		if (fields.size() != 1)
		{
			return null; //все составные реквизиты - строковые
		}
		CPFLRequisiteField simpleField = fields.get(0);
		if ("T".equals(simpleField.getTemplate()))
		{
			return getTypePaymentListMenu();
		}
		if ("U".equals(simpleField.getTemplate()))
		{
			List<ListValue> result = new ArrayList<ListValue>();
			for (String menuItem : simpleField.getMenu())
			{
				result.add(new ListValue(menuItem, menuItem));
			}
			return result;
		}
		return null;
	}

	/**
	 * получить из платежа значение поля для реквизита
	 * @param payment платеж с данными
	 * @param number номер поля в реквизите(соответсует номеру группы в pег выражении)
	 * @return значение
	 */
	String getRequisiteFieldValue(AbstractPaymentSystemPayment payment, int number) throws GateException, GateLogicException
	{
		//получаем поле
		Field field = CPFLBillingPaymentHelper.getFieldById(payment, getExternalId());
		if (field == null)
		{
			return null;
		}
		String value = (String) field.getValue();
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		if (fields.size() == 1)
		{
			return value; //простой реквизит возращается полностью.
		}
		//получаем регесп для реквизита
		String regexp = buildRegex();
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches())
		{
			throw new GateLogicException("Невалидное значение реквизита " + name);
		}
		if (matcher.groupCount() < number + 1)
		{
			throw new GateException("Для реквизита " + getExternalId() + " запрошена несуществующая группа " + (number + 1) +
					". regexp:" + regexp + ". значение: " + value);
		}
		return matcher.group(number + 1);//группы в регеспе нумеруются с 1.
	}

	/**
	 * Сформировать регулярное выражения соответсвующее значению поля.
	 * Данное выражение используется для валидации и получениея значений для ЦПФЛ полей реквизита.
	 * для простого реквизита регуляроное выражение соотвествует регулярному выражению поля.
	 * Для составного реквизита используется выражение вида:
	 * <префикс_поля_1>(<regexp_поля_1>)<суффикс_поля_1 ><regexp_поля_2>(<поле_2>)<суффикс_поля_2>...
	 * круглые скобки – это группировка внутри регекспов, для получения значения поля составного реквизита.
	 * @return регулярное выращение соответсвующее значению поля.
	 */
	private String buildRegex()
	{
		if (fields.size() == 1)
		{
			return "(" + fields.get(0).getRegexp() + ")";
		}
		StringBuilder builder = new StringBuilder();
		for (CPFLRequisiteField field : fields)
		{
			if (field.getPrefix() != null)
				builder.append(Pattern.quote(field.getPrefix()));
			builder.append("(");
			builder.append(field.getRegexp());
			builder.append(")");
			if (field.getPostfix() != null)
				builder.append(Pattern.quote(field.getPostfix()));
		}
		return builder.toString();
	}

	/**
	 * @return результат выполнения запроса typePaymentList, представленный в виде List<ListValue>
	 */
	private List<ListValue> getTypePaymentListMenu() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = factory.service(WebBankServiceFacade.class);
		GateMessage gateMessage = service.createRequest("typePaymentList_q");
		Document responce = service.sendOnlineMessage(gateMessage, null);
		final List<ListValue> result = new ArrayList<ListValue>();
		try
		{
			XmlHelper.foreach(responce.getDocumentElement(), "//typePayment", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String code = XmlHelper.getSimpleElementValue(element, "typePaymentCode");
					String value = XmlHelper.getSimpleElementValue(element, "name");
					result.add(new ListValue(value, code));
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		return result;
	}

	public CPFLRequisiteField getField()
	{
		if(fields == null)
			return null;
		if (fields.size() != 1)
			return null; //все составные реквизиты - строковые
		return fields.get(0);
	}
}