package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.phizic.utils.MoneyHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 29.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Валидатор сравнивающий соотношение суммы в одном поле к сумме в другом поле.
 * Пример: сумма в поле 1 должны быть в 5 раз больше суммы в поле 2.
 */
public class CompareSumValidator extends CompareValidator
{
	public static final String FIELD_O1_FACTOR = "obj1_factor";
	public static final String FIELD_O2_FACTOR = "obj2_factor";
	public static final String NEED_UPDATE_MESSAGE = "needUpdateMessage";

	private static final BigDecimalParser bigDecimalParser = new BigDecimalParser();
	private ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		BigDecimal field1 = ((BigDecimal)retrieveFieldValue(FIELD_O1, values));
		BigDecimal field2 = ((BigDecimal)retrieveFieldValue(FIELD_O2, values));

		try
		{
			BigDecimal field1_factor = bigDecimalParser.parse(getParameter(FIELD_O1_FACTOR));
			if(field1_factor != null)
				field1 = field1.multiply(field1_factor);
			BigDecimal field2_factor = bigDecimalParser.parse(getParameter(FIELD_O2_FACTOR));
			if(field2_factor != null)
				field2 = field2.multiply(field2_factor);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Ошибка при преобразовании параметра.",e);
		}

		String operator = getParameter(OPERATOR);
        if (operator == null)
            throw new RuntimeException("Параметр '" + OPERATOR + "' не определен!");
		
		int result = field1.compareTo(field2);

		int[] criterias = resultTable.get(operator);

        for (int criteria : criterias)
        {
            if (criteria == result)
            {
                return true;
            }
        }

		String needUpdateMessage = getParameter(NEED_UPDATE_MESSAGE);
		if(StringHelper.isNotEmpty(needUpdateMessage) && BooleanUtils.toBoolean(needUpdateMessage))
			//пока что подставляем в текстовку только значение 2-го поля умноженное на коэффициент.
			setMessage(String.format(getMessage(), MoneyHelper.formatAmount(field2)));
        return false;
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setMessage(String value)
	{
		message.set(value);
	}

}
