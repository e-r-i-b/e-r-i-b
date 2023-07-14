package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 11:26:09
 * виртуальная форма загрузки предодобренных кредитных предложений
 */
public class LoanOfferForm {

	public static final Form FORM = createForm();

	private static Form createForm()
	{
        FormBuilder fb = new FormBuilder();

    	FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тербанк");
		fieldBuilder.setName(Constants.TERBANK);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
            (
                new RegexpFieldValidator(".{0,2}", "Длина поля 'Тербанк' не должна превышать 2 символов")
             );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ФИО");
		fieldBuilder.setName(Constants.FIO);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,250}", "Длина поля 'ФИО' не должна превышать 250 символов")
	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Пол");
		fieldBuilder.setName(Constants.SEX);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,1}", "Длина поля 'Пол' не должна превышать 1 символов")
	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setName(Constants.BIRTH_DATE);
		fieldBuilder.setType("date");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,10}", "Длина поля 'Дата рождения' не должна превышать 10 символов")
	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер паспорта");
		fieldBuilder.setName(Constants.PASPORT_NUMBER);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,6}", "Длина поля 'Номер паспорта' не должна превышать 6 символов")
    	    );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия паспорта");
		fieldBuilder.setName(Constants.SERIES);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,5}", "Длина поля 'Серия паспорта' не должна превышать 5 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата выдачи паспорта");
		fieldBuilder.setName(Constants.PASPORT_DATE);
		fieldBuilder.setType("date");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,10}", "Длина поля 'Дата выдачи паспорта' не должна превышать 10 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Место выдачи паспорта");
		fieldBuilder.setName(Constants.ISSUE_PLACE);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Место выдачи паспорта' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: регион");
		fieldBuilder.setName(Constants.REGION);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Адрес регистрации: регион' не должна превышать 205 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: Район");
		fieldBuilder.setName(Constants.DISTRICT);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'дрес регистрации: Район' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: Населенный пункт");
		fieldBuilder.setName(Constants.LOCATION);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Адрес регистрации: Населенный пункт' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: Индекс");
		fieldBuilder.setName(Constants.INDEX);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,6}", "Длина поля ''Адрес регистрации: Индекс' не должна превышать 6 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: улица");
		fieldBuilder.setName(Constants.STREET);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Адрес регистрации: улица' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: дом");
		fieldBuilder.setName(Constants.HOME);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "Длина поля 'Адрес регистрации: дом' не должна превышать 4 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: корпус");
		fieldBuilder.setName(Constants.HOUSING);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "Длина поля 'Адрес регистрации: корпус' не должна превышать 4 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации: квартира");
		fieldBuilder.setName(Constants.APARTMENT);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "Длина поля 'Адрес регистрации: квартира' не должна превышать 4 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: регион");
		fieldBuilder.setName(Constants.REGION2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Адрес проживания: регион' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: Район");
		fieldBuilder.setName(Constants.DISTRICT2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Адрес проживания: Район' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: Населенный пункт");
		fieldBuilder.setName(Constants.LOCATION2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Адрес проживания: Населенный пункт' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: Индекс");
		fieldBuilder.setName(Constants.INDEX2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,6}", "Длина поля 'Адрес проживания: Индекс' не должна превышать 6 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: улица");
		fieldBuilder.setName(Constants.STREET2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "Длина поля 'Адрес проживания: улица' не должна превышать 250 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: дом");
		fieldBuilder.setName(Constants.HOME2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "Длина поля 'Адрес проживания: дом' не должна превышать 4 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: корпус");
		fieldBuilder.setName(Constants.HOUSING2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "Длина поля 'Адрес проживания: корпус' не должна превышать 4 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес проживания: квартира");
		fieldBuilder.setName(Constants.APARTENT2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "Длина поля 'Адрес проживания: квартира' не должна превышать 4 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Телефон 1");
		fieldBuilder.setName(Constants.PHONE1);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,35}", "Длина поля 'Телефон 1' не должна превышать 35 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Телефон 2");
		fieldBuilder.setName(Constants.PHONE2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,35}", "Длина поля 'Телефон 2' не должна превышать 35 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Телефон 3");
		fieldBuilder.setName(Constants.PHONE3);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,35}", "Длина поля 'Телефон 3' не должна превышать 35 символов")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Лимит по новому кредиту: 6 мес.");
		fieldBuilder.setName(Constants.MONTH6);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "Длина поля 'Лимит по новому кредиту: 6 мес' не должна превышать 9 символов")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Лимит по новому кредиту: 12 мес.");
		fieldBuilder.setName(Constants.MONTH12);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "Длина поля 'Лимит по новому кредиту: 12 мес.' не должна превышать 9 символов")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Лимит по новому кредиту: 18 мес.");
		fieldBuilder.setName(Constants.MONTH18);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "Длина поля 'Лимит по новому кредиту: 18 мес.' не должна превышать 9 символов")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Лимит по новому кредиту: 24 мес.");
		fieldBuilder.setName(Constants.MONTH24);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "Длина поля 'Лимит по новому кредиту: 24 мес.' не должна превышать 9 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Лимит по новому кредиту: 36 мес.");
		fieldBuilder.setName(Constants.MONTH36);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "Длина поля 'Лимит по новому кредиту: 36 мес.' не должна превышать 9 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Лимит по новому кредиту: 48 мес.");
		fieldBuilder.setName(Constants.MONTH48);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "Длина поля 'Лимит по новому кредиту: 48 мес.' не должна превышать 9 символов")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Лимит по новому кредиту: 60 мес.");
		fieldBuilder.setName(Constants.MONTH60);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "Длина поля 'Лимит по новому кредиту: 60 мес.' не должна превышать 9 символов")

	        );
        fb.addField(fieldBuilder.build());



		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование кредитного продукта");
		fieldBuilder.setName(Constants.PRODUCT_NAME);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
			    new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,48}", "Длина поля 'Наименование кредитного продукта' не должна превышать 48 символов")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код продукта");
		fieldBuilder.setName(Constants.PRODUCT_CODE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
				(
						new RegexpFieldValidator(".{0,2}", "Длина поля 'Код продукта' не должна превышать 2 символов")

				);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код субпродукта");
		fieldBuilder.setName(Constants.SUB_PRODUCT_CODE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
				(
						new RegexpFieldValidator(".{0,5}", "Длина поля 'Код субпродукта' не должна превышать 5 символов")

				);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Процентная ставка");
		fieldBuilder.setName(Constants.PRACENT_RATE);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
			    new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,5}", "Длина поля 'Процентная ставка' не должна превышать 5 символов")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата окончания действия предложения");
		fieldBuilder.setName(Constants.END_DATE);
		fieldBuilder.setType("date");
		fieldBuilder.addValidators
				(
						new RequiredFieldValidator(),
						new RegexpFieldValidator(".{0,10}", "Длина поля 'Дата окончания действия предложения' не должна превышать 10 символов")
				);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Валюта");
		fieldBuilder.setName(Constants.CURRENCY);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
			    new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,3}", "Длина поля 'Валюта' не должна превышать 3 символов")

	        );

        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор участника кампании");
		fieldBuilder.setName(Constants.CAMPAIGN_MEMBER_ID);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
				(
						new RequiredFieldValidator(),
						new RegexpFieldValidator(".{0,50}", "Длина поля 'Идентификатор участника кампании' не должна превышать 50 символов")

				);

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
