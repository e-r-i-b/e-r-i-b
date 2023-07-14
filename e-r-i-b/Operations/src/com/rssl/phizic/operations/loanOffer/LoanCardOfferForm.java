package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * User: Moshenko
 * Date: 16.06.2011
 * Time: 9:40:32
 * виртуальная форма загрузки предодобренных  предложений по картам
 */
public class LoanCardOfferForm
{
    public static Form getForm()
    {
        FormBuilder fb = new FormBuilder();
        
        FieldBuilder fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Фамилия");
        fieldBuilder.setName("surname");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,20}", "Длина поля 'Фамилия' не должна превышать 20 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Имя");
        fieldBuilder.setName("name");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,20}", "Длина поля 'Имя' не должна превышать 20 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Отчество");
        fieldBuilder.setName("patrName");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,15}", "Длина поля 'Отчество' не должна превышать 15 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Серия и номер паспорта РФ");
        fieldBuilder.setName("seriesAndNumber");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,19}", "Длина поля 'Серия и номер паспорта РФ' не должна превышать 19 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Дата рождения");
        fieldBuilder.setName("birthDate");
        fieldBuilder.setType("date");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,10}", "Длина поля 'Дата рождения' не должна превышать 10 символов")
                );
        fb.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Тип предложения");
        fieldBuilder.setName("offerType");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,1}", "Длина поля 'Тип предложения' не должна превышать 1 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Лимит кредита");
        fieldBuilder.setName("creditLimit");
        fieldBuilder.setType("money");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,14}", "Длина поля 'Лимит кредита' не должна превышать 14 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Наименование компании");
        fieldBuilder.setName("companyName");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "Длина поля 'Наименование компании' не должна превышать 30 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Рабочий индекс");
        fieldBuilder.setName("index");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,6}", "Длина поля 'Рабочий индекс' не должна превышать 6 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Рабочий адрес1");
        fieldBuilder.setName("adres1");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "Длина поля 'Рабочий адрес1' не должна превышать 30 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Рабочий адрес2");
        fieldBuilder.setName("adres2");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "Длина поля 'Рабочий адрес2' не должна превышать 30 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Рабочий адрес3");
        fieldBuilder.setName("adres3");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "Длина поля 'Рабочий адрес3' не должна превышать 30 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Рабочий адрес4");
        fieldBuilder.setName("adres4");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "Длина поля 'Рабочий адрес4' не должна превышать 30 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("ID клиента");
        fieldBuilder.setName("idWay");
        fieldBuilder.setType("integer");
        fb.addField(fieldBuilder.build());
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,18}", "Длина поля 'ID клиента' не должна превышать 18 символов")
                );

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Номер карты");
        fieldBuilder.setName("cardNumber");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,19}", "Длина поля 'Номер карты' не должна превышать 19 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Банк");
        fieldBuilder.setName("terbank");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,2}", "Длина поля 'Банк' не должна превышать 2 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Отделение");
        fieldBuilder.setName("osb");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,4}", "Длина поля 'Отделение' не должна превышать 4 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("ВСП");
        fieldBuilder.setName("vsp");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,10}", "Длина поля 'ВСП' не должна превышать 10 символов")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Код типа клиента/ канала продажи");
        fieldBuilder.setName("clientCodeType");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,10}", "Длина поля 'Код типа клиента/ канала продажи' не должна превышать 10 символов")
                );
        fb.addField(fieldBuilder.build());

        return fb.build();
    }
}
