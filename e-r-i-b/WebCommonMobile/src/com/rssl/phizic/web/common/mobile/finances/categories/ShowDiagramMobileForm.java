package com.rssl.phizic.web.common.mobile.finances.categories;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BooleanTrueIfNullParser;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.finances.DiagramAbstract;
import com.rssl.phizic.web.component.DatePeriodFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Balovtsev
 * @since 28.12.2014.
 */
public class ShowDiagramMobileForm extends ShowOperationCategoriesMobileForm
{
	protected static final String DATE_FORMAT         = "MM.yyyy";
	protected static final Form   DIAGRAM_FILTER_FORM = createForm();

	private String onlyCash;
	private String internalTransfer;
	private String transfer;
	private String notTransfer;

	private List<DiagramAbstract> diagramAbstracts = new ArrayList<DiagramAbstract>();

	public List<DiagramAbstract> getDiagramAbstracts()
	{
		return Collections.unmodifiableList(diagramAbstracts);
	}

	public void setDiagramAbstracts(List<DiagramAbstract> diagramAbstracts)
	{
		this.diagramAbstracts.addAll(diagramAbstracts);
	}

	private static Form createForm()
	{
		List<Field> fields = new ArrayList<Field>();

		DateParser dataParser = new DateParser(DATE_FORMAT);

		DateFieldValidator datePeriodValidator = new DateFieldValidator(DATE_FORMAT);
		datePeriodValidator.setMessage("������� ���� � ���� ������ � ������� ��.����");

		DateInYearBeforeCurrentDateValidator dateFieldValidator = new DateInYearBeforeCurrentDateValidator(DATE_FORMAT);
		dateFieldValidator.setMessage("�� ������ ����������� �������� ������� ������ �� ��������� ���.");

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();

		// ������
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("from");
		fieldBuilder.setDescription("������");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("to");
		fieldBuilder.setDescription("������");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onlyCash");
		fieldBuilder.setDescription("��������� ��������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("internalTransfer");
		fieldBuilder.setDescription("���������� �������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setParser(new BooleanTrueIfNullParser());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("transfer");
		fieldBuilder.setDescription("�������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setParser(new BooleanTrueIfNullParser());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("noTransfer");
		fieldBuilder.setDescription("�� �������. ������ ��� �����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setParser(new BooleanTrueIfNullParser());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedId");
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d*)$"));
		fields.add(fieldBuilder.build());

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1,   DatePeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2,   DatePeriodFilter.TO_DATE);
		compareDateValidator.setMessage("�������� ���� ������ ���� ������ ���������!");

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);
		formBuilder.setFormValidators(compareDateValidator);

		return formBuilder.build();
	}

	public String isOnlyCash()
	{
		return onlyCash;
	}

	public void setOnlyCash(String onlyCash)
	{
		this.onlyCash = onlyCash;
	}

	public String isInternalTransfer()
	{
		return internalTransfer;
	}

	public void setInternalTransfer(String internalTransfer)
	{
		this.internalTransfer = internalTransfer;
	}

	public String isTransfer()
	{
		return transfer;
	}

	public void setTransfer(String transfer)
	{
		this.transfer = transfer;
	}

	public String isNotTransfer()
	{
		return notTransfer;
	}

	public void setNotTransfer(String notTransfer)
	{
		this.notTransfer = notTransfer;
	}
}
