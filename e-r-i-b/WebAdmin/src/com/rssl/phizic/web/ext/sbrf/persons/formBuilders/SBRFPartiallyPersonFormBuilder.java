package com.rssl.phizic.web.ext.sbrf.persons.formBuilders;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.persons.formBuilders.PartiallyPersonFormBuilder;

import java.util.List;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFPartiallyPersonFormBuilder extends PartiallyPersonFormBuilder
{
	 protected void initializeValidators()
    {
        super.initializeValidators();

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
	    // отчество
//	    fieldValidators.get(PATR_NAME_FIELD).add(requiredFieldValidator);

	    // дата рождения
	    fieldValidators.get(BIRTH_DAY_FIELD).add(requiredFieldValidator);

	    // вид документа
	    fieldValidators.get(DOCUMENT_TYPE_FIELD).add(requiredFieldValidator);

	    // мобильный телефон
	    // При редактировании УДБО и карточных клиентов стандартная проверка номера телефона не проходит,
	    // потому что он хранится в том же виде, в котором приходит из шины. А парсить его нельзя, т.к.
	    // нет договоренности о его формате. Для СБОЛ клиентов сработает валидатор на полной форме.
	    List<FieldValidator> mobileFieldValidators = fieldValidators.get(MOBILE_PHONE_FIELD);
	    mobileFieldValidators.clear();
	    mobileFieldValidators.add(new RegexpFieldValidator(".{0,16}", "Поле [" + MOBILE_PHONE_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
    }
}
