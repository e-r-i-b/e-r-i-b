package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.parsers.BooleanParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.operations.registration.SBNKDSettingsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.MapLogParametersReader;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

import java.util.HashMap;
import java.util.Map;
/**
 * Настройки СБНКД
 * @author basharin
 * @ created 08.01.15
 * @ $Author$
 * @ $Revision$
 */

public class SBNKDSettingsAction extends EditPropertiesActionBase
{
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("SBNKDSettingsOperation","SBNKDSettingsService");
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation op) throws Exception
	{
		super.updateFormAdditionalData(frm, op);
		SBNKDSettingsForm form = (SBNKDSettingsForm) frm;
		SBNKDSettingsOperation operation = (SBNKDSettingsOperation) op;
		form.setDepartments(operation.getDepartments());
	}
	@Override
	protected void addLogParameters(EditFormBase frm)
	{
		SBNKDConfig sbnkdConfig = ConfigFactory.getConfig(SBNKDConfig.class);
		Map<String, Object> values = new HashMap<String, Object>();
		Form form = getEditForm(frm);

		//заполняем мап имен настроек
		String name;
		for (Field field : form.getFields())
		{
			name = field.getName();
			if(!sbnkdConfig.getProperty(name).equals(frm.getField(name)))
			{
				String fieldValue = StringHelper.getEmptyIfNull(frm.getField(name));

				if (field.getType().getName().equals(BooleanType.INSTANCE.getName()))
					fieldValue = Boolean.valueOf(fieldValue).toString();

				values.put(field.getDescription(), fieldValue + "<br>(использованное ранее значение = " + sbnkdConfig.getProperty(name) + ")<br>");
			}
		}
		addLogParameters(new MapLogParametersReader("Логин сотрудника: "+ EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin().getUserId() +"<br>" + "Данные, введенные пользователем", values));
	}
}