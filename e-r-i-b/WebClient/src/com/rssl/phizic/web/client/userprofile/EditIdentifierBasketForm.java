package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.basket.links.LinkInfo;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author lukina
 * @ created 18.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditIdentifierBasketForm  extends EditFormBase
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	private UserDocument userDocument;
	private String documentType;
	private String actionType;
	private List<LinkInfo> links;
	private ConfirmableObject confirmableObject;
	private ConfirmStrategy confirmStrategy;
	private BasketIndetifierType basketIndetifierType;

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public UserDocument getUserDocument()
	{
		return userDocument;
	}

	public void setUserDocument(UserDocument userDocument)
	{
		this.userDocument = userDocument;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	public String getActionType()
	{
		return actionType;
	}

	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}

	public List<LinkInfo> getLinks()
	{
		return links;
	}

	public void setLinks(List<LinkInfo> links)
	{
		this.links = links;
	}

	public BasketIndetifierType getBasketIndetifierType()
	{
		return basketIndetifierType;
	}

	public void setBasketIndetifierType(BasketIndetifierType basketIndetifierType)
	{
		this.basketIndetifierType = basketIndetifierType;
	}

	public Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название документа");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,100}", "Название документа не должно превышать 100 символов"));
		formBuilder.addField(fieldBuilder.build());

		if (documentType.equals("SNILS"))
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("number");
			fieldBuilder.setDescription("Номер СНИЛС");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d{3}-\\d{3}-\\d{3} \\d{2}",
					"Номер СНИЛС должен содержать только цифры по маске XXX-XXX-XXX XX"));
			formBuilder.addField(fieldBuilder.build());
		}

		if (documentType.equals("INN"))
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("number");
			fieldBuilder.setDescription("Номер ИНН");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}

		if (documentType.equals("DL"))
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("number");
			fieldBuilder.setDescription("Номер");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("series");
			fieldBuilder.setDescription("Серия");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.setName("issueDate");
			fieldBuilder.setDescription("Дата выдачи");
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.setName("expireDate");
			fieldBuilder.setDescription("Действует до");
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("issueBy");
			fieldBuilder.setDescription("Кем выдан");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}
		if (documentType.equals("RC"))
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("number");
			fieldBuilder.setDescription("Номер");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("series");
			fieldBuilder.setDescription("Серия");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}
		return formBuilder.build();
	}
}
