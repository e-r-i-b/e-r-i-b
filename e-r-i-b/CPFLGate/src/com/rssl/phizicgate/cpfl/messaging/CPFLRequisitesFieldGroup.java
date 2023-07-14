package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author krenev
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 * Группа однородных полей реквизита.
 * см описание com.rssl.phizicgate.cpfl.messaging.CPFLRequisiteProcessor#getHint()
 */
class CPFLRequisitesFieldGroup
{
	private static final int MAX_GROUP_TEMPLATE_SIZE = 15;

	private final List<CPFLRequisiteField> fields = new ArrayList<CPFLRequisiteField>();
	private Set<String> singularSet;

	CPFLRequisitesFieldGroup(CPFLRequisiteField... fields)
	{
		if (this.fields != null)
		{
			for (CPFLRequisiteField field : fields)
			{
				add(field);
			}
		}
	}

	//список однородных множеств полей.
	private static final List<Set<String>> similarTemplates = new ArrayList<Set<String>>();

	static
	{
		Set<String> singularSet = new HashSet<String>();
		singularSet.add("D");
		singularSet.add("M");
		singularSet.add("G");
		singularSet.add("9");
		singularSet.add("R");
		singularSet.add("K");
		singularSet.add("P");
		similarTemplates.add(singularSet);
		singularSet = new HashSet<String>();
		singularSet.add("X");
		singularSet.add("S");
		similarTemplates.add(singularSet);
		singularSet = new HashSet<String>();
		singularSet.add("A");
		similarTemplates.add(singularSet);
		singularSet = new HashSet<String>();
		singularSet.add("N");
		similarTemplates.add(singularSet);
	}

	/**
	 * Однородно ли поле группе
	 * @param field поле
	 * @return да/нет
	 */

	boolean isSimilar(CPFLRequisiteField field)
	{
		if (singularSet == null)
		{
			//делаем ленивую инициализацию множества однородных шаблонов для данной группы
			for (Set<String> set : similarTemplates)
			{
				if (set.contains(field.getTemplate()))
				{
					singularSet = set;
				}
			}
		}
		if (singularSet == null)
		{
			throw new IllegalArgumentException("не найдено множество однородных шаблонов для template=" + field.getTemplate());
		}
		return singularSet.contains(field.getTemplate());
	}

	void add(CPFLRequisiteField field)
	{
		if (!isSimilar(field))
		{
			throw new IllegalArgumentException("Поле неоднородно");
		}
		fields.add(field);
	}

	/**
	 * @return имеются ли внутренние разделители(префиксы/постфиксы) у группы
	 */
	boolean hasDelimiters()
	{
		int count = getFieldsCount();
		if (count < 2)
		{
			return false;
		}
		CPFLRequisiteField field = fields.get(0);
		if (!StringHelper.isEmpty(field.getPostfix()))
		{
			return true;
		}
		for (int i = 1; i < count - 1; i++)
		{
			field = fields.get(i);
			if (!StringHelper.isEmpty(field.getPrefix()) || !StringHelper.isEmpty(field.getPostfix()))
			{
				return true;
			}
		}
		field = fields.get(count - 1);
		if (!StringHelper.isEmpty(field.getPrefix()))
		{
			return true;
		}
		return false;
	}

	int getFieldsCount()
	{
		return fields.size();
	}

	/**
	 * Получить шаблон ввода
	 * @param templateChar - символ для шаблона
	 * @return шаблон ввода. например XXX-XXX-X-X
	 */
	String getTemplate(char templateChar)
	{
		int count = getFieldsCount();
		if (count == 0)
		{
			throw new IllegalStateException("группа пуста");
		}
		StringBuilder result = new StringBuilder();
		for (CPFLRequisiteField field : fields)
		{
			if (!StringHelper.isEmpty(field.getPrefix()))
			{
				result.append(field.getPrefix());
			}
			result.append(makeString(templateChar, Math.min(field.getMaxLength(), MAX_GROUP_TEMPLATE_SIZE)));
			if (!StringHelper.isEmpty(field.getPostfix()))
			{
				result.append(field.getPostfix());
			}
		}
		return result.toString();
	}

	//описание для множества допустимых символов в единственном числе именительном падеже
	private static final Map<String, String> templatesDescriptionSingularNominative = new HashMap<String, String>();
	//описание для множества допустимых символов в единственном числе родительном падеже
	private static final Map<String, String> templatesDescriptionSingularGenitive = new HashMap<String, String>();
	//описание для множества допустимых символов во множественном числе родительном падеже
	private static final Map<String, String> templatesDescriptionMultipleGenitive = new HashMap<String, String>();

	static
	{
		templatesDescriptionSingularNominative.put("D", "цифра");
		templatesDescriptionSingularNominative.put("M", "цифра");
		templatesDescriptionSingularNominative.put("G", "цифра");
		templatesDescriptionSingularNominative.put("9", "цифра");
		templatesDescriptionSingularNominative.put("R", "цифра");
		templatesDescriptionSingularNominative.put("K", "цифра");
		templatesDescriptionSingularNominative.put("P", "цифра");
		templatesDescriptionSingularNominative.put("A", "буква или пробел");
		templatesDescriptionSingularNominative.put("N", "буква или цифра");
		templatesDescriptionSingularNominative.put("X", "символ");
		templatesDescriptionSingularNominative.put("S", "символ");

		templatesDescriptionSingularGenitive.put("D", "цифры");
		templatesDescriptionSingularGenitive.put("M", "цифры");
		templatesDescriptionSingularGenitive.put("G", "цифры");
		templatesDescriptionSingularGenitive.put("9", "цифры");
		templatesDescriptionSingularGenitive.put("R", "цифры");
		templatesDescriptionSingularGenitive.put("K", "цифры");
		templatesDescriptionSingularGenitive.put("P", "цифры");
		templatesDescriptionSingularGenitive.put("A", "буквы или пробела");
		templatesDescriptionSingularGenitive.put("N", "буквы или цифры");
		templatesDescriptionSingularGenitive.put("X", "символа");
		templatesDescriptionSingularGenitive.put("S", "символа");

		templatesDescriptionMultipleGenitive.put("D", "цифр");
		templatesDescriptionMultipleGenitive.put("M", "цифр");
		templatesDescriptionMultipleGenitive.put("G", "цифр");
		templatesDescriptionMultipleGenitive.put("9", "цифр");
		templatesDescriptionMultipleGenitive.put("R", "цифр");
		templatesDescriptionMultipleGenitive.put("K", "цифр");
		templatesDescriptionMultipleGenitive.put("P", "цифр");
		templatesDescriptionMultipleGenitive.put("A", "букв или пробелов");
		templatesDescriptionMultipleGenitive.put("N", "букв или цифр");
		templatesDescriptionMultipleGenitive.put("X", "символов");//любые печатные символы
		templatesDescriptionMultipleGenitive.put("S", "символов");//любые печатные символы
	}

	/**
	 * @return рускоязычное описание множества допустимых символов и их макс мин количества. 
	 */
	String getDescription()
	{
		if (getFieldsCount() == 0)
		{
			throw new IllegalStateException("группа пуста");
		}
		//считаем максимальную и минимальную длину символов для ввода группы.
		int max = 0;
		int min = 0;
		for (CPFLRequisiteField field : fields)
		{
			max += field.getMaxLength();
			min += field.getMinLength();
		}
		String template = fields.get(0).getTemplate();
		StringBuilder formatDestription = new StringBuilder();
		if (min == 0)
		{
			//мин длины нет-> надо ввести не более max символов.
			formatDestription.append(" не более ").append(max);
			if (max % 10 == 1 && max % 100 != 11)
			{
				//выводим в единственном числе род падежа: не более 101 символа, не более 31 символа....
				formatDestription.append(" ").append(templatesDescriptionSingularGenitive.get(template));
			}
			else
			{
				//выводим во множественном числе род падежа: не более 11 символов, не более 303 символов....
				formatDestription.append(" ").append(templatesDescriptionMultipleGenitive.get(template));
			}
		}
		else if (max == min)
		{
			//оба мин и макс равны -> надо ввести ровное количество символов.
			formatDestription.append(min);

			int lastDigit = max % 10;
			int last2digits = max % 100;
			if (lastDigit == 1 && last2digits != 11)
			{
				//выводим в единственном числе им падежа: 101 символ, 31 символ....
				formatDestription.append(" ").append(templatesDescriptionSingularNominative.get(template));
			}
			else if (2 <= lastDigit && lastDigit <= 4 && last2digits >20)
			{
				//выводим в единственном числе род падежа: 102 символа, 33 символа....
				formatDestription.append(" ").append(templatesDescriptionSingularGenitive.get(template));
			}
			else
			{
				//выводим во множественном числе род падежа: 11 символов, 306 символов....
				formatDestription.append(" ").append(templatesDescriptionMultipleGenitive.get(template));
			}
		}
		else
		{
			//не равны. пишем от мин до мах
			formatDestription.append(" oт ").append(min).append(" до ").append(max);
			if (max % 10 == 1 && max % 100 != 11)
			{
				//выводим в единственном числе род падежа: от 1 до 101 символа, от 4 до 31 символа....
				formatDestription.append(" ").append(templatesDescriptionSingularGenitive.get(template));
			}
			else
			{
				//выводим во множественном числе род падежа: от 1 до 11 символов, от 1 до 303 символов....
				formatDestription.append(" ").append(templatesDescriptionMultipleGenitive.get(template));
			}
		}
		return formatDestription.toString();
	}

	//маппинг "типов"(template) полей на множество допустимых симвлов для генерации примера.
	private static final Map<String, String> exampleAllowedCharsMap = new HashMap<String, String>();

	static
	{
		exampleAllowedCharsMap.put("D", "0123456789");
		exampleAllowedCharsMap.put("M", "0123456789");
		exampleAllowedCharsMap.put("G", "0123456789");
		exampleAllowedCharsMap.put("9", "0123456789");
		exampleAllowedCharsMap.put("R", "0123456789");
		exampleAllowedCharsMap.put("K", "0123456789");
		exampleAllowedCharsMap.put("P", "0123456789");//цифры и пробел
		exampleAllowedCharsMap.put("A", "ABC123 DEFJHIGKLMNOPQRSTUVWXYZ");//буквы и пробел
		exampleAllowedCharsMap.put("N", "ABC123DEFJHIGKLMNOPQRSTUVWXYZ0123456789");//буквы и цифры
		exampleAllowedCharsMap.put("X", "ABC123 DEFJHIGKLMNOPQRSTUVWXYZ0123456789");//любые печатные символы
		exampleAllowedCharsMap.put("S", "ABC123 DEFJHIGKLMNOPQRSTUVWXYZ0123456789");//любые печатные символы
	}

	/**
	 * @return сгенерировать пример заполнения группы
	 */
	String getExample()
	{
		int count = getFieldsCount();
		if (count == 0)
		{
			throw new IllegalStateException("группа пуста");
		}
		StringBuilder result = new StringBuilder();
		for (CPFLRequisiteField field : fields)
		{
			if (!StringHelper.isEmpty(field.getPrefix()))
			{
				result.append(field.getPrefix());
			}
			result.append(generateExampleString((int) Math.min(field.getMaxLength(), MAX_GROUP_TEMPLATE_SIZE)));
			if (!StringHelper.isEmpty(field.getPostfix()))
			{
				result.append(field.getPostfix());
			}
		}
		return result.toString();
	}

	private String generateExampleString(int n)
	{
		int count = getFieldsCount();
		if (count == 0)
		{
			throw new IllegalStateException("группа пуста");
		}
		CPFLRequisiteField firstField = fields.get(0); // брать можно любое поле, тк они все однородны
		String exampleChars = exampleAllowedCharsMap.get(firstField.getTemplate());
		if (exampleChars == null)
		{
			throw new IllegalStateException("Не найдено множество символов для примера template=" + firstField.getTemplate());
		}

		StringBuffer buffer = new StringBuffer(n);
		int length = exampleChars.length();

		char[] charsArray = new char[length];
		exampleChars.getChars(0, length, charsArray, 0);

		for (int i = 0; i < n; i++)
		{
			buffer.append(charsArray[i % length]);
		}
		return buffer.toString();
	}

	String makeString(char templateChar, long count)
	{
		StringBuilder format = new StringBuilder();
		for (int j = 0; j < count; j++)
		{
			format.append(templateChar);
		}
		return format.toString();
	}
}
