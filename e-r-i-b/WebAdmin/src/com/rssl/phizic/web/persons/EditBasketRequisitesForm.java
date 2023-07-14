package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionSyncUtil;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.profile.ProfileConfig;

/**
 * Форма для редактирования реквизитов документов клиента
 * @author miklyaev
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketRequisitesForm extends EditPersonForm
{
	public static final Form EDIT_BASKET_REQUISITES_FORM = createBasketRequisitesLogicalForm();
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	public static final String SERIES_FIELD_DL = InvoiceSubscriptionSyncUtil.SERIES_FIELD_DL;
	public static final String NUMBER_FIELD_DL = InvoiceSubscriptionSyncUtil.NUMBER_FIELD_DL;
	public static final String ISSUE_DATE_FIELD_DL = InvoiceSubscriptionSyncUtil.ISSUE_DATE_FIELD_DL;
	public static final String EXPIRE_DATE_FIELD_Dl = InvoiceSubscriptionSyncUtil.EXPIRE_DATE_FIELD_Dl;
	public static final String ISSUE_BY_FIELD_DL = InvoiceSubscriptionSyncUtil.ISSUE_BY_FIELD_DL;

	public static final String SERIES_FIELD_RC = InvoiceSubscriptionSyncUtil.SERIES_FIELD_RC;
	public static final String NUMBER_FIELD_RC = InvoiceSubscriptionSyncUtil.NUMBER_FIELD_RC;

	private ActivePerson activePerson;
	private boolean modified = false;
	private boolean accessDL;
	private boolean accessRC;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	/**
	 * Установить текущего клиента
	 * @param activePerson - текущий клиент
	 */
	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	/**
	 * @return true, если режим работы клиента - shadow
	 */
	public boolean isModified()
	{
		return modified;
	}

	/**
	 * Установить признак теневого режима работы клиента
	 * @param modified - признак
	 */
	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	/**
	 * @return признак доступности водительского удостоверения как идентификатора корзины
	 */
	public boolean isAccessDL()
	{
		return accessDL;
	}

	/**
	 * @return признак Свидетельства о регистрации транспортного средства как идентификатора корзины
	 */
	public boolean isAccessRC()
	{
		return accessRC;
	}

	/**
	 * Установить признак доступности водительского удостоверения как идентификатора корзины
	 * @param accessDL - признак доступности
	 */
	public void setAccessDL(boolean accessDL)
	{
		this.accessDL = accessDL;
	}


	/**
	 * Установить признак доступности Свидетельства о регистрации транспортного средства  как идентификатора корзины
	 * @param accessRC - признак доступности
	 */
	public void setAccessRC(boolean accessRC)
	{
		this.accessRC = accessRC;
	}

	/**
	 * @return логическую форму
	 */
	private static Form createBasketRequisitesLogicalForm()
	{
		ProfileConfig config = ConfigFactory.getConfig(ProfileConfig.class);
		int maxLengthForReq;
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		//Номер водительского удостоверения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUMBER_FIELD_DL);
		fieldBuilder.setDescription("Номер");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("numberDL");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d{" + maxLengthForReq +"}$",
				"Номер ВУ должен содержать " + maxLengthForReq + " цифр"));
		formBuilder.addField(fieldBuilder.build());

		//Серия водительского удостоверения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SERIES_FIELD_DL);
		fieldBuilder.setDescription("Серия");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("seriesDL");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^\\d{" + maxLengthForReq + "}$",
                  "Поле Серия ВУ должно содержать " + maxLengthForReq + " символа"));
		formBuilder.addField(fieldBuilder.build());

		DateParser dataParser = new DateParser(DATE_FORMAT);

		//Дата выдачи водительского удостоверения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(ISSUE_DATE_FIELD_DL);
		fieldBuilder.setParser(dataParser);
		fieldBuilder.setDescription("Дата выдачи");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new DateFieldValidator("dd.MM.yyyy", "Пожалуйста, укажите Дату выдачи в формате ДД.ММ.ГГГГ."));
		formBuilder.addField(fieldBuilder.build());

		//Время окончания действия водительского удостоверения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(EXPIRE_DATE_FIELD_Dl);
		fieldBuilder.setParser(dataParser);
		fieldBuilder.setDescription("Действует до");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new DateFieldValidator("dd.MM.yyyy", "Пожалуйста, укажите значение поля Действует до в формате ДД.ММ.ГГГГ."));
		formBuilder.addField(fieldBuilder.build());

		//Кем выдано водительского удостоверения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ISSUE_BY_FIELD_DL);
		fieldBuilder.setDescription("Кем выдан");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("issueOrgDL");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0," + maxLengthForReq + "}",
				"Поле Кем выдан должено содержать не более " + maxLengthForReq + " символов"));
		formBuilder.addField(fieldBuilder.build());

		//Номер Свидетельства о регистрации транспортного средства
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUMBER_FIELD_RC);
		fieldBuilder.setDescription("Номер");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("numberRC");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d{" + maxLengthForReq +"}$",
				"Номер Свидетельства о регистрации транспортного средства должен содержать " + maxLengthForReq + " цифр"));
		formBuilder.addField(fieldBuilder.build());

		//Серия Свидетельства о регистрации транспортного средства
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SERIES_FIELD_RC);
		fieldBuilder.setDescription("Серия");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("seriesRC");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^\\d{" + maxLengthForReq + "}$",
				"Поле Серия Свидетельства о регистрации транспортного средства должно содержать " + maxLengthForReq + " символа"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
