package com.rssl.phizic.web.monitoring.thresholdvalues;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.phizic.business.monitoring.MonitoringThresholdValues;
import com.rssl.phizic.business.monitoring.MonitoringReport;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditMonitoringThresholdValuesForm  extends EditFormBase
{
	protected static final String WARNING_THRESHOLD   = "warningThreshold";
	protected static final String ERROR_THRESHOLD     = "errorThreshold";

	private List<MonitoringThresholdValues> values; // список пороговых значений для мониторинга

	public List<MonitoringThresholdValues> getValues()
	{
		return values;
	}

	public void setValues(List<MonitoringThresholdValues> values)
	{
		this.values = values;
	}

	public Form createForm()
	{
		FormBuilder formBuilder  = new FormBuilder();
		FieldBuilder fieldBuilder = null;

		for(MonitoringReport value : MonitoringReport.values())
		{
			String reportName = value.toString();
			String reportDesc = value.getDescription();

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(reportName + WARNING_THRESHOLD);
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.setDescription("Порог предупреждения");
			fieldBuilder.addValidators(new RequiredFieldValidator("Задайте порог предупреждения ["+ reportDesc +"]."));
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(reportName + ERROR_THRESHOLD);
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.setDescription("Порог ошибки. ");
			fieldBuilder.addValidators(new RequiredFieldValidator("Задайте порог ошибки ["+ reportDesc +"]."));
			formBuilder.addField(fieldBuilder.build());

			CompareValidator compareValidator = new CompareValidator();
			compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
			compareValidator.setBinding(CompareValidator.FIELD_O1, reportName + WARNING_THRESHOLD);
			compareValidator.setBinding(CompareValidator.FIELD_O2, reportName + ERROR_THRESHOLD);
			compareValidator.setMessage("Порог предупреждения должен быть меньше или равен порогу ошибки. ["+reportDesc+"].");
			formBuilder.addFormValidators(compareValidator);
		}
		return formBuilder.build();
	}
}
