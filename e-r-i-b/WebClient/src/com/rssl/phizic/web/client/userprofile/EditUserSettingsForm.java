package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EmailFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.basket.links.LinkInfo;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.persons.validators.*;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gulov
 * @ created 21.05.2010
 * @ $Authors$
 * @ $Revision$
 */
public class EditUserSettingsForm extends EditFormBase
{
	private Map<String,Object> chengedFields = new HashMap<String, Object>();
    private ConfirmStrategy confirmStrategy;
	private MailFormat mailFormat;
	private boolean clientUDBO;
	private boolean hasAvatar;
	private List<PersonDocument> personDocumentList; //ДУЛы клиента
	private List<UserDocument> userDocumentList; //идентификаторы корзины
	private Map<String, List<LinkInfo>> links; //связанные документы
	private boolean accessINN; //
	private boolean accessDL; //
	private boolean accessRC; //
	private Map<String, BasketIndetifierType> ident;

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public Map<String, Object> getChengedFields()
	{
		return chengedFields;
	}

	public void setChengedFields(Map<String, Object> chengedFields)
	{
		this.chengedFields = chengedFields;
	}

	public void setMailFormat(MailFormat mailFormat)
	{
		this.mailFormat = mailFormat;
	}

	public MailFormat getMailFormat()
	{
		return mailFormat;
	}

	public void setClientUDBO(boolean clientUDBO)
	{
		this.clientUDBO = clientUDBO;
	}

	public boolean isClientUDBO()
	{
		return clientUDBO;
	}

	public void setHasAvatar(boolean hasAvatar)
	{
		this.hasAvatar = hasAvatar;
	}

	public boolean getHasAvatar()
	{
		return hasAvatar;
	}

	public List<UserDocument> getUserDocumentList()
	{
		return userDocumentList;
	}

	public void setUserDocumentList(List<UserDocument> userDocumentList)
	{
		this.userDocumentList = userDocumentList;
	}

	public List<PersonDocument> getPersonDocumentList()
	{
		return personDocumentList;
	}

	public void setPersonDocumentList(List<PersonDocument> personDocumentList)
	{
		this.personDocumentList = personDocumentList;
	}

	public Map<String, List<LinkInfo>> getLinks()
	{
		return links;
	}

	public void setLinks(Map<String, List<LinkInfo>> links)
	{
		this.links = links;
	}

	public boolean isAccessINN()
	{
		return accessINN;
	}

	public void setAccessINN(boolean accessINN)
	{
		this.accessINN = accessINN;
	}

	public boolean isAccessDL()
	{
		return accessDL;
	}

	public void setAccessDL(boolean accessDL)
	{
		this.accessDL = accessDL;
	}

	public boolean isAccessRC()
	{
		return accessRC;
	}

	public void setAccessRC(boolean accessRC)
	{
		this.accessRC = accessRC;
	}

	public Map<String, BasketIndetifierType> getIdent()
	{
		return ident;
	}

	public void setIdent(Map<String, BasketIndetifierType> ident)
	{
		this.ident = ident;
	}

	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		LeadingEndingSpacesValidator leadingEndingSpacesValidator = new LeadingEndingSpacesValidator();
		DoubleSpacesValidator doubleSpacesValidator = new DoubleSpacesValidator();
		WordSpaceHyphenValidator wordSpaceHyphenValidator = new WordSpaceHyphenValidator();

		FieldValidator[] standartValidators = new FieldValidator[]{
		    new MaxFieldSizeValidator(), new DoubleHyphenValidator(), doubleSpacesValidator,
			wordSpaceHyphenValidator, leadingEndingSpacesValidator
		};

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("regionId");
		fieldBuilder.setDescription("Регион обслуживания");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("email");
		fieldBuilder.setDescription("E-mail");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(standartValidators);
		fieldBuilder.addValidators(
				new EmailFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("mailFormat");
		fieldBuilder.setDescription("Формат отправки оповещений");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("homePhone");
		fieldBuilder.setDescription("Домашний телефон");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		fieldBuilder.addValidators(standartValidators);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("jobPhone");
		fieldBuilder.setDescription("Рабочий телефон");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(standartValidators);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("SNILS");
		fieldBuilder.setDescription("Страховой Номер Индивидуального Лицевого Счёта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{3}-\\d{3}-\\d{3} \\d{2}",
				"Страховой Номер Индивидуального Лицевого Счёта должен содержать только цифры по маске XXX-XXX-XXX XX"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
