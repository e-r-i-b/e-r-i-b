package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.payments.forms.FormParser;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: vagin
 * @ created: 05.03.2012
 * @ $Author$
 * @ $Revision$
 * ��������� ��������� �������������� ����, �� ���� � �� ��� ��������� �� �������� � ���������� ����� ����� ������ �����.
 */
public class DublicateFieldNameValidator extends FieldValidatorBase
{
	private static final PaymentFormService formService = new PaymentFormService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String formName = "RurPayJurSB";
	private static final List<Field> formFields = initFormFields();

	static List<Field> initFormFields()
	{
		try
		{
			MetadataBean form = formService.findByName(formName);
			if (form == null)
				throw new FormException("�� ������� ���������� �������: RurPayJurSB");

			FormParser formParser = new FormParser();
			formParser.parse(form.getDefinition());
			//�������� ���� �� ����� RurPayJurSB
			return formParser.getForm().getFields();	
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return new ArrayList<Field>();
		}
	}

	public DublicateFieldNameValidator(){}

	public DublicateFieldNameValidator(String message)
	{
		this.setMessage(message);
	}

	/**
	 * �������� �������� ����� �� ���������� ����� �� ������������ � ������ ��������� � �����.
	 * @param nameField - ��� ���.����
	 * @return true-��� ����������, false - ���� ���������.
	 * @throws TemporalDocumentException
	 */
	public boolean validate(String nameField)
	{
		for (Field field : formFields)
		{
			if (field.getName().equalsIgnoreCase(nameField))
				return false;
		}
		return true;
	}
}
