package com.rssl.phizic.web.persons.list;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.clients.list.ClientInformation;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.lang.ArrayUtils;

import java.util.Map;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма получения списка клиентов из ЦСА Бек
 */

public class ListClientForm extends ListFormBase<ClientInformation>
{
	public static final String FIO_FIELD_NAME = "fio";
	public static final String DOCUMENT_FIELD_NAME = "document";
	public static final String BIRTHDAY_FIELD_NAME = "birthday";
	public static final String LOGIN_FIELD_NAME = "login";
	public static final String CREATION_TYPE_FIELD_NAME = "creationType";
	public static final String TB_FIELD_NAME = "tb";
	public static final String MOBILE_PHONE_FIELD_NAME = "mobilePhone";
	public static final String AGREEMENT_NUMBER_FIELD_NAME = "agreementNumber";

	public static final Form FILTER_FORM = createFilterForm();
	private Map<String, String> allowedTB;

	private String blockReason;
	private String blockStartDate;
	private String blockEndDate;

	private boolean isLock = false;
	private boolean isUnlock = false;
	private boolean failureIMSICheck = false;

	/**
	 * задать доступные подразделения
	 * @param allowedTB доступные подразделения
	 */
	public void setAllowedTB(Map<String, String> allowedTB)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.allowedTB = allowedTB;
	}

	/**
	 * @return доступные подразделения
	 */
	public Map<String, String> getAllowedTB()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return allowedTB;
	}

	/**
	 * @return причина блокировки
	 */
	public String getBlockReason()
	{
		return blockReason;
	}

	/**
	 * задть причину блокировки
	 * @param blockReason причина блокировки
	 */
	public void setBlockReason(String blockReason)
	{
		this.blockReason = blockReason;
	}

	/**
	 * @return начало блокировки
	 */
	public String getBlockStartDate()
	{
		return blockStartDate;
	}

	/**
	 * задать начало блокировки
	 * @param blockStartDate начало блокировки
	 */
	public void setBlockStartDate(String blockStartDate)
	{
		this.blockStartDate = blockStartDate;
	}

	/**
	 * @return окончание блокировки
	 */
	public String getBlockEndDate()
	{
		return blockEndDate;
	}

	/**
	 * задать окончание блокировки
	 * @param blockEndDate окончание блокировки
	 */
	public void setBlockEndDate(String blockEndDate)
	{
		this.blockEndDate = blockEndDate;
	}

	public boolean isLock()
	{
		return isLock;
	}

	public void setLock(boolean lock)
	{
		isLock = lock;
	}

	public boolean isUnlock()
	{
		return isUnlock;
	}

	public void setUnlock(boolean unlock)
	{
		isUnlock = unlock;
	}

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addField(getStringField(FIO_FIELD_NAME, "Клиент"));
		formBuilder.addField(getStringField(DOCUMENT_FIELD_NAME, "Документ(ДУЛ)"));
		formBuilder.addField(getStringField(TB_FIELD_NAME, "Террбанк"));
		formBuilder.addField(getStringField(LOGIN_FIELD_NAME, "Логин"));
		formBuilder.addField(getStringField(MOBILE_PHONE_FIELD_NAME, "Мобильный телефон", new RegexpFieldValidator("\\d*", "Поле Мобильный телефон должно содержать только цифры")));
		formBuilder.addField(getStringField(AGREEMENT_NUMBER_FIELD_NAME, "Номер договора"));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BIRTHDAY_FIELD_NAME);
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CREATION_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("Тип договора");
		fieldBuilder.setParser(new EnumParser<CreationType>(CreationType.class));
		fieldBuilder.addValidators(new EnumFieldValidator<CreationType>(CreationType.class, "Неизвестный тип договора."));
		formBuilder.addField(fieldBuilder.build());

		MultiFieldsValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(FIO_FIELD_NAME, FIO_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_FIELD_NAME, DOCUMENT_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(LOGIN_FIELD_NAME, LOGIN_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(AGREEMENT_NUMBER_FIELD_NAME, AGREEMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(MOBILE_PHONE_FIELD_NAME, MOBILE_PHONE_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("Пожалуйста, заполните хотя бы одно из полей фильтра: клиент, документ(ДУЛ), логин, номер договора, мобильный телефон.");
		formBuilder.addFormValidators(requiredMultiFieldValidator);

		return formBuilder.build();
	}

	private static Field getStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	public boolean isFailureIMSICheck()
	{
		return failureIMSICheck;
	}

	public void setFailureIMSICheck(boolean failureIMSICheck)
	{
		this.failureIMSICheck = failureIMSICheck;
	}
}
