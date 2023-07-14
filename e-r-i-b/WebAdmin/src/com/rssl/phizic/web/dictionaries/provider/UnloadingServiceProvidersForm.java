package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;

/**
 * @author hudyakov
 * @ created 27.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class UnloadingServiceProvidersForm extends EditFormBase
{
	public static Form UNLOADING_FORM = createForm();

	private boolean  useNotActiveProviders; //использовать ли для выгрузки поставщиков со статусом обслуживание приостановлено

	private Long[] departments  = new Long[]{};
	private Long[] providers    = new Long[]{};
	private Long[] services = new Long[]{};


	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator()
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.setName("toDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator()
			);
		fb.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toDate");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		fb.addFormValidators(dateTimeCompareValidator);

		return fb.build();
	}

	public boolean isUseNotActiveProviders()
	{
		return useNotActiveProviders;
	}

	public void setUseNotActiveProviders(boolean useNotActiveProviders)
	{
		this.useNotActiveProviders = useNotActiveProviders;
	}

	public Long[] getDepartments()
	{
		return departments;
	}

	public void setDepartments(Long[] departments)
	{
		this.departments = departments;
	}

	public Long[] getProviders()
	{
		return providers;
	}

	public void setProviders(Long[] providers)
	{
		this.providers = providers;
	}

	public Long[] getServices()
	{
		return services;
	}

	public void setServices(Long[] services)
	{
		this.services = services;
	}
}
