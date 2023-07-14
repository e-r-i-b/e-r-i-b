package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.exception.ExceptionEntryType;

import java.math.BigInteger;

/**
 * @author mihaylov
 * @ created 17.04.2013
 * @ $Author$
 * @ $Revision$
 * ����� ����������� ����������� �������� ������
 */
public class ExceptionEntryListForm extends ListFormBase
{
	private static final BigInteger OPERATION_TYPE_LENGHT = BigInteger.valueOf(50);
	public static final Form FILTER_FORM = createFilterForm();

	private boolean isDictionary = false;
	private boolean isDecoratedException = true;
	private ExceptionEntryType exceptionEntryType;

	public boolean isDictionary()
	{
		return isDictionary;
	}

	public void setDictionary(boolean dictionary)
	{
		isDictionary = dictionary;
	}

	public boolean isDecoratedException()
	{
		return isDecoratedException;
	}

	public void setDecoratedException(boolean decoratedException)
	{
		isDecoratedException = decoratedException;
	}

	/**
	 * @return �������� ��� ������, � ������� ��������
	 */
	public ExceptionEntryType getExceptionEntryType()
	{
		return exceptionEntryType;
	}

	/**
	 * ������ �������� ��� ������, � ������� ��������
	 * @param exceptionEntryType �������� ��� ������, � ������� ��������
	 */
	public void setExceptionEntryType(ExceptionEntryType exceptionEntryType)
	{
		this.exceptionEntryType = exceptionEntryType;
	}

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
		fieldBuilder.setDescription("ID ������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationType");
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new LengthFieldValidator(OPERATION_TYPE_LENGHT));
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("application");
		fieldBuilder.setDescription("����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
        fieldBuilder.addValidators(new ChooseValueValidator(ListUtil.fromArray(new String[]{"PhizIA", "PhizIC", "mobile5", "mobile6", "mobile7", "mobile8", "mobile9", "atm", "Scheduler", "socialApi", "WebAPI"})));
		formBuilder.addField(fieldBuilder.build());				

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("system");
		fieldBuilder.setDescription("�������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("systemName");
		fieldBuilder.setDescription("�������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("errorCode");
		fieldBuilder.setDescription("��� �� ������� �������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
