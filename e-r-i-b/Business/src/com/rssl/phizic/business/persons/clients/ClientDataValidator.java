package com.rssl.phizic.business.persons.clients;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.BeanHelper;

import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 07.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ClientDataValidator
{
	Client client;
	List<String> errors;

	public ClientDataValidator(Client client)
	{
		this.client = client;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	public boolean validate() throws BusinessException
	{
		if(client==null)
			throw new BusinessException("Не установлен клиент");

		Map map = BeanHelper.createMapFromProperties(client);

		Address residenceAddress = client.getRealAddress();
		if (residenceAddress != null)
		{
			map.put("residenceAddress.postalCode", residenceAddress.getPostalCode());
			map.put("residenceAddress.province", residenceAddress.getProvince());
			map.put("residenceAddress.district", residenceAddress.getDistrict());
			map.put("residenceAddress.city", residenceAddress.getCity());
			map.put("residenceAddress.street", residenceAddress.getStreet());
			map.put("residenceAddress.house", residenceAddress.getHouse());
			map.put("residenceAddress.building", residenceAddress.getBuilding());
			map.put("residenceAddress.flat", residenceAddress.getFlat());
		}
		Address registrationAddress = client.getLegalAddress();
		if (registrationAddress != null)
		{
			map.put("registrationAddress.postalCode", registrationAddress.getPostalCode());
			map.put("registrationAddress.province", registrationAddress.getProvince());
			map.put("registrationAddress.district", registrationAddress.getDistrict());
			map.put("registrationAddress.city", registrationAddress.getCity());
			map.put("registrationAddress.street", registrationAddress.getStreet());
			map.put("registrationAddress.house", registrationAddress.getHouse());
			map.put("registrationAddress.building", registrationAddress.getBuilding());
			map.put("registrationAddress.flat", registrationAddress.getFlat());
		}

		MapValuesSource fieldsSource = new MapValuesSource(map);

		FormProcessor processor= new FormProcessor<List<String>, StringErrorCollector>(fieldsSource,createForm(), new StringErrorCollector(), DefaultValidationStrategy.getInstance());
		if(processor.process())
		{
			return true;
		}
		else
		{
			errors = (List<String>)processor.getErrors();
			return false;
		}
	}

	private Form createForm()
	{
		//todo возможно можно было использовать один из PersonFormBuilder
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(),new RegexpFieldValidator(".{0,42}", "Поле Имя не должно превышать 42 символов") );

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фамилия");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,42}", "Поле Фамилия не должно превышать 42 символов") );

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Отчество");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,42}", "Поле Отчество не должно превышать 42 символов") );

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Место рождения");
		fieldBuilder.setName("birthPlace");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new RegexpFieldValidator(".{0,128}", "Поле Место рождения не должно превышать 128 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("id документа");
		fieldBuilder.setName("documentId");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new RegexpFieldValidator(".{0,10}", "Поле id документа не должно превышать 10 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование документа");
		fieldBuilder.setName("documentName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new RegexpFieldValidator(".{0,128}", "Поле Наименование документа не должно превышать 128 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер документа");
		fieldBuilder.setName("documentNumber");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
		new RegexpFieldValidator("\\d*", "Поле Номер документа должно содержать только цифры"),
        new RegexpFieldValidator(".{0,16}", "Поле Номер документа не должно превышать 16 цифр"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия документа");
		fieldBuilder.setName("documentSeries");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,16}", "Поле Серия документа не должно превышать 16 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Кем выдан документ");
		fieldBuilder.setName("documentIssueBy");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,128}", "Поле Кем выдан документ не должно превышать 128 символов"));

		fb.addField(fieldBuilder.build());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации");
		fieldBuilder.setName("registrationAddress");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,256}", "Поле Адрес регистрации не должно превышать 256 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фактический адрес");
		fieldBuilder.setName("residenceAddress");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,256}", "Поле Фактический адрес не должно превышать 256 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес электронной почты");
		fieldBuilder.setName("email");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,70}", "Поле Адрес электронной почты не должно превышать 70 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Домашний телефон");
		fieldBuilder.setName("homePhone");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,35}", "Поле Домашний телефон не должно превышать 35 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Рабочий телефон");
		fieldBuilder.setName("jobPhone");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,35}", "Поле Рабочий телефон не должно превышать 35 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Мобильный телефон");
		fieldBuilder.setName("mobilePhone");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,16}", "Поле Мобильный телефон не должно превышать 16 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ИНН");
		fieldBuilder.setName("INN");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator("\\d{10}(\\d{2}){0,1}", "Поле ИНН должно состоять из 10 или 12 цифр"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Гражданство");
		fieldBuilder.setName("citizenship");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,70}", "Поле Гражданство не должно превышать 70 символов"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес фактического проживания: индекс");
		fieldBuilder.setName("residenceAddress.postalCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,6}", "Поле индекс не должно превышать 6 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес фактического проживания: область");
		fieldBuilder.setName("residenceAddress.province");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле область не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес фактического проживания: район");
		fieldBuilder.setName("residenceAddress.district");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле район не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес фактического проживания: город");
		fieldBuilder.setName("residenceAddress.sity");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле город не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес фактического проживания: улица");
		fieldBuilder.setName("residenceAddress.street");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле улица не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес фактического проживания: дом");
		fieldBuilder.setName("residenceAddress.house");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "Поле дом не должно превышать 10 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес фактического проживания: корпус");
		fieldBuilder.setName("residenceAddress.building");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "Поле корпус не должно превышать 10 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес фактического проживания: квартира");
		fieldBuilder.setName("residenceAddress.flat");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "Поле квартира не должно превышать 10 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес регистрации : индекс");
		fieldBuilder.setName("registrationAddress.postalCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,6}", "Поле индекс не должно превышать 6 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес регистрации: область");
		fieldBuilder.setName("registrationAddress.province");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле область не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес регистрации: район");
		fieldBuilder.setName("registrationAddress.district");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле район не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес регистрации: город");
		fieldBuilder.setName("registrationAddress.city");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле город не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес регистрации: улица");
		fieldBuilder.setName("registrationAddress.street");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "Поле улица не должно превышать 100 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес регистрации: дом");
		fieldBuilder.setName("registrationAddress.house");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "Поле дом не должно превышать 10 символов"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес регистрации: корпус");
		fieldBuilder.setName("registrationAddress.building");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "Поле корпус не должно превышать 10 символов"));

		fb.addField(fieldBuilder.build());
        fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("Адрес регистрации: квартира");
		fieldBuilder.setName("registrationAddress.flat");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "Поле квартира не должно превышать 10 символов"));

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
