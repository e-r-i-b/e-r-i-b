package com.rssl.common.forms.processing;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;

/**
 * @author krenev
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 * —тратеги€ валидации формы при обработке форм процессором
 */
public interface ValidationStrategy
{
	/**
	 * Ќеобходимо ли использовать валидатор при обработке формы
	 * @param field поле
	 * @return да/нет
	 */
	boolean accepted(Field field);

	/**
	 * Ќеобходимо ли использовать валидатор при обработке формы
	 * @param validator валидатор
	 * @return да/нет
	 */
	boolean accepted(MultiFieldsValidator validator);

	/**
	 * Ќеобходимо ли использовать валидатор при обработке формы
	 * @param validator валидатор
	 * @param validatedValue введенное значение пол€
	 * @return да/нет
	 */
	boolean accepted(FieldValidator validator, String validatedValue);
}
