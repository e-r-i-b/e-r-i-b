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
	    // ��������
//	    fieldValidators.get(PATR_NAME_FIELD).add(requiredFieldValidator);

	    // ���� ��������
	    fieldValidators.get(BIRTH_DAY_FIELD).add(requiredFieldValidator);

	    // ��� ���������
	    fieldValidators.get(DOCUMENT_TYPE_FIELD).add(requiredFieldValidator);

	    // ��������� �������
	    // ��� �������������� ���� � ��������� �������� ����������� �������� ������ �������� �� ��������,
	    // ������ ��� �� �������� � ��� �� ����, � ������� �������� �� ����. � ������� ��� ������, �.�.
	    // ��� �������������� � ��� �������. ��� ���� �������� ��������� ��������� �� ������ �����.
	    List<FieldValidator> mobileFieldValidators = fieldValidators.get(MOBILE_PHONE_FIELD);
	    mobileFieldValidators.clear();
	    mobileFieldValidators.add(new RegexpFieldValidator(".{0,16}", "���� [" + MOBILE_PHONE_FIELD_DESCRIPTION + "] �� ������ ��������� 16 ��������"));
    }
}
