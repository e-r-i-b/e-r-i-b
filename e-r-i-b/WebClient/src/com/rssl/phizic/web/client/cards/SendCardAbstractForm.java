package com.rssl.phizic.web.client.cards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.bankroll.SendedAbstract;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author egorova
 * @ created 07.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class SendCardAbstractForm extends EditFormBase
{
	public static final String FORMAT_FIELD_NAME = "format";
	public static final String LANG_FIELD_NAME = "lang";
	public static final String FILL_REPORT_FIELD_NAME = "fillReport";
	public static final String TO_DATE_FIELD_NAME = "toEmail";
	public static final String FROM_DATE_FIELD_NAME = "fromEmail";
	public static final String TYPE_FIELD_NAME = "typeEmail";
	public static final String E_MAIL_FIELD_NAME = "email";

	public static final Form EDIT_FORM = createForm();
	public static final Form EDIT_FORM_EXT = createFormExt();
	private CardLink cardLink;
	private List<SendedAbstract> sendedAbstract;
	private boolean asVypiskaActive;
	private boolean activePDF_RUS;
	private boolean activePDF_ENG;
	private boolean activeHTML_RUS;
	private boolean activeHTML_ENG;
	private List<String[]> history;
	private boolean timeoutError = false;
	private boolean otherError = false;
	private String inactiveESError; //сообщение о тех. перерыве

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public List<SendedAbstract> getSendedAbstract()
	{
		return sendedAbstract;
	}

	public void setSendedAbstract(List<SendedAbstract> sendedAbstract)
	{
		this.sendedAbstract = sendedAbstract;
	}

	public boolean isAsVypiskaActive()
	{
		return asVypiskaActive;
	}

	public void setAsVypiskaActive(boolean asVypiskaActive)
	{
		this.asVypiskaActive = asVypiskaActive;
	}

	public boolean isActiveHTML_ENG()
	{
		return activeHTML_ENG;
	}

	public void setActiveHTML_ENG(boolean activeHTML_ENG)
	{
		this.activeHTML_ENG = activeHTML_ENG;
	}

	public boolean isActiveHTML_RUS()
	{
		return activeHTML_RUS;
	}

	public void setActiveHTML_RUS(boolean activeHTML_RUS)
	{
		this.activeHTML_RUS = activeHTML_RUS;
	}

	public boolean isActivePDF_ENG()
	{
		return activePDF_ENG;
	}

	public void setActivePDF_ENG(boolean activePDF_ENG)
	{
		this.activePDF_ENG = activePDF_ENG;
	}

	public boolean isActivePDF_RUS()
	{
		return activePDF_RUS;
	}

	public void setActivePDF_RUS(boolean activePDF_RUS)
	{
		this.activePDF_RUS = activePDF_RUS;
	}

	public int getSize()
	{
		return sendedAbstract.size();
	}

	public List<String[]> getHistory()
	{
		return history;
	}

	public void setHistory(List<String[]> history)
	{
		this.history = history;
	}

	public int getHistorySize()
	{
		return this.history.size();
	}

	public boolean isOtherError()
	{
		return otherError;
	}

	public void setOtherError(boolean otherError)
	{
		this.otherError = otherError;
	}

	public boolean isTimeoutError()
	{
		return timeoutError;
	}

	public void setTimeoutError(boolean timeoutError)
	{
		this.timeoutError = timeoutError;
	}

	public String getInactiveESError()
	{
		return inactiveESError;
	}

	public void setInactiveESError(String inactiveESError)
	{
		this.inactiveESError = inactiveESError;
	}

	private static Form createForm()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeEmail == 'period' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(format);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(TYPE_FIELD_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"week", "month", "period"}))
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FROM_DATE_FIELD_NAME);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TO_DATE_FIELD_NAME);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
		formBuilder.addField(fieldBuilder.build());

		String email = "e-mail";
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(E_MAIL_FIELD_NAME);
		fieldBuilder.setDescription(email);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new EmailFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE_FIELD_NAME);
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	private static Form createFormExt()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeEmail == 'period' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(format);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(TYPE_FIELD_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"week", "month", "period"}))
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FROM_DATE_FIELD_NAME);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dataValidator, dateNotInFutureValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TO_DATE_FIELD_NAME);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dataValidator, dateNotInFutureValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FORMAT_FIELD_NAME);
		fieldBuilder.setDescription("Формат документа");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators();
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LANG_FIELD_NAME);
		fieldBuilder.setDescription("Язык документа");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators();
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FILL_REPORT_FIELD_NAME);
		fieldBuilder.setDescription("Тип отчета");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators();
		formBuilder.addField(fieldBuilder.build());

		String email = "e-mail";
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(E_MAIL_FIELD_NAME);
		fieldBuilder.setDescription(email);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new EmailFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE_FIELD_NAME);
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}


}
