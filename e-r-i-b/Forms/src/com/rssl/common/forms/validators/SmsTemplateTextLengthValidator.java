package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

import java.util.regex.Pattern;

/**
 *
 * Валидатор для проверки введенного сотрудником текста смс-шаблона
 *
 * @author  Balovtsev
 * @version 28.03.13 15:49
 */
public class SmsTemplateTextLengthValidator extends FieldValidatorBase
{
	/*
	 * Будь очень аккуратен, товарищ!
	 *
	 * Данный строковый шаблон ищет во введённом сотрудником тексте группы символов не относящиеся к тексту смс.
	 * Например, в строке "<@compress single_line=true>Сбербанк ОнЛ@йн. Пароль для подтверждения изменения логина – ${password}</@compress>"
	 * будут найдены следующие подстроки "<@compress single_line=true>", "${password}", "</@compress>".
	 *
	 * Шаблон необходим при подсчёте количества символов, используется для того чтобы отбросить
	 * описанные выше подстроки.
	 *
	 */
	private static final String PATTERN_VARIABLE       = "(</?)([@|#])([\\w \\.,!=\"'&?|()>/]+)|(\\$\\{[\\w\\.\\?\"'() :]+})|(\\{\\d})|(%(?=\\w)\\w)|(([ ]+)((?= )|(?=\\t))|([\\t]+))|(\\r?\\n?)";
	private static final String PATTERN_TRANSLIT       = "(<# *\\w+ +translit *[ =\"'\\w]*>)";
	private static final String PATTERN_ENGLISH_LETTER = "[A-z]*";

	private static final int    MAX_FIELD_LENGTH_WITHOUT_PARAMETERS = 499;

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
		{
			return true;
		}

		boolean hasTranslit = Pattern.compile(PATTERN_TRANSLIT).matcher(value).find();
		String  replaced    = Pattern.compile(PATTERN_VARIABLE).matcher(value).replaceAll("");

		if (hasTranslit)
		{
			replaced = replaced.replaceAll(PATTERN_ENGLISH_LETTER, "");
		}

		if (replaced.trim().length() > MAX_FIELD_LENGTH_WITHOUT_PARAMETERS)
		{
			setMessage("Текст сообщения без учета названий переменных значений не должен составлять более 499 символов");
			return false;
		}

		return true;
	}
}
