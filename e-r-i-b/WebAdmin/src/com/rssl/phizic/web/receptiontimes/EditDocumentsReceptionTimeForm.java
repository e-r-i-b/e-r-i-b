package com.rssl.phizic.web.receptiontimes;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.SqlTimeParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.calendar.WorkCalendar;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Pakhomova
 * @created: 18.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditDocumentsReceptionTimeForm extends EditFormBase
{
	private Map<String, String> payments = new HashMap<String, String>(); // ���� <��� �������, �������� �������> (�� ��� ������ �������� � ���������� �����)

	private List<WorkCalendar> calendars;       // ��� ������������ ������� ���������
	private boolean useRetailCalendar;          //���� ����������� ������������ ���.��������� (������) - � ���������� �� ����� ����������
	private boolean haveParentDepartment;       //���� �� ����������� �������������

	private static final String TIME_FORMAT = "HH:mm";

	// ���� ����
   //����� ������ ������ ����������
	protected static final String TIME_START_FIELD          = "timeStart";
	//����� ��������� ������ ����������
	protected static final String TIME_END_FIELD            = "timeEnd";
	//���������
    protected static final String CALENDAR_ID_FIELD         = "calendarId";
	//������������ ��������� ������������
    protected static final String USE_PARENT_SETTINGS_FIELD = "useParentSettings";
	//������� ��� ����� ������� ������������ �������������
    protected static final String PARENT                    = "parent";

	public Map<String, String> getPayments()
	{
		return payments;
	}

	public void setPayments(Map<String, String> payments)
	{
		this.payments.putAll(payments);
	}

	public List<WorkCalendar> getCalendars()
	{
		return calendars;
	}

	public void setCalendars(List<WorkCalendar> calendars)
	{
		this.calendars = calendars;
	}

	public boolean isUseRetailCalendar()
	{
		return useRetailCalendar;
	}

	public void setUseRetailCalendar(boolean useRetailCalendar)
	{
		this.useRetailCalendar = useRetailCalendar;
	}

	public boolean isHaveParentDepartment()
	{
		return haveParentDepartment;
	}

	public void setHaveParentDepartment(boolean haveParentDepartment)
	{
		this.haveParentDepartment = haveParentDepartment;
	}

	public void setTimeField(String key, Time time)
	{
		setField(key, formatTime(time));
	}

	private String formatTime(Time time)
	{
		if (time == null)
			return "";

		return DateFormatUtils.format(time, TIME_FORMAT);
	}

	public Form createForm()
	{
		FormBuilder formBuilder  = new FormBuilder();
		FieldBuilder fieldBuilder = null;

		for (String paymentType : payments.keySet())
		{
			String paymentStartTimeFieldName  = paymentType + EditDocumentsReceptionTimeForm.TIME_START_FIELD;
			String paymentEndTimeFieldName    = paymentType + EditDocumentsReceptionTimeForm.TIME_END_FIELD;
			String fieldCheckName             = paymentType + EditDocumentsReceptionTimeForm.USE_PARENT_SETTINGS_FIELD;
			String paymentDescription         = payments.get(paymentType);

			// ������������ �� ��������� ������������ �������������
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldCheckName);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fieldBuilder.setDescription("������������ ��������� ������������");
			formBuilder.addField(fieldBuilder.build());

			// ����� ������ ������ ����������
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(paymentStartTimeFieldName);
			fieldBuilder.setDescription("����� ������ ������ ����������");
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setParser(new SqlTimeParser());
			FieldValidator enabledFieldValidatorStart = new RequiredFieldValidator("������� ����� ������ ������ ���������� [" + paymentDescription + "]");
			enabledFieldValidatorStart.setEnabledExpression(new RhinoExpression("!form." + fieldCheckName));
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators
			(
				enabledFieldValidatorStart,
				new DateFieldValidator(TIME_FORMAT, "����� ������ ������ ���������� ������ ���� � ������� ��:��")
			);
			formBuilder.addField(fieldBuilder.build());

			// ����� ��������� ������ ����������
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(paymentEndTimeFieldName);
			fieldBuilder.setDescription("����� ��������� ������ ����������");
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setParser(new SqlTimeParser());
			FieldValidator enabledFieldValidatorEnd = new RequiredFieldValidator("������� ����� ��������� ������ ���������� [" + paymentDescription + "]");
			enabledFieldValidatorEnd.setEnabledExpression(new RhinoExpression("!form." + fieldCheckName));
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators
			(
				enabledFieldValidatorEnd,
				new DateFieldValidator(TIME_FORMAT, "����� ��������� ������ ���������� ������ ���� � ������� ��:��")
			);
			formBuilder.addField(fieldBuilder.build());

			// ��������� ��� ������ �����
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(paymentType + EditDocumentsReceptionTimeForm.CALENDAR_ID_FIELD);
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.setDescription("���������");
			formBuilder.addField(fieldBuilder.build());

			// ����� ������ ������ ���������� ������������� �������������
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(EditDocumentsReceptionTimeForm.PARENT + paymentStartTimeFieldName);
			formBuilder.addField(fieldBuilder.build());

			// ����� ��������� ������ ���������� ������������� �������������
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(EditDocumentsReceptionTimeForm.PARENT + paymentEndTimeFieldName);
			formBuilder.addField(fieldBuilder.build());

			//���� � ������ �������������, ���� � ������ �������� �������� � �������� ��������� ����� �� ������ �����
			MultiFieldsValidator requiredMultiFieldValidator = new RequiredAllMultiFieldValidator();
			requiredMultiFieldValidator.setBinding(EditDocumentsReceptionTimeForm.TIME_START_FIELD, EditDocumentsReceptionTimeForm.PARENT + paymentStartTimeFieldName);
			requiredMultiFieldValidator.setBinding(EditDocumentsReceptionTimeForm.TIME_END_FIELD,   EditDocumentsReceptionTimeForm.PARENT + paymentEndTimeFieldName);
			requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("form." + fieldCheckName));
			requiredMultiFieldValidator.setMessage("���������� ��������� ��������� ������ ������ ���������� [" + paymentDescription + "]. ������� ����� ������ ���������� ��� ������������ �������������");
			formBuilder.addFormValidators(requiredMultiFieldValidator);
		}
		return formBuilder.build();
	}
}
