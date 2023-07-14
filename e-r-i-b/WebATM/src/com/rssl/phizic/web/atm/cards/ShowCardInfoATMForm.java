package com.rssl.phizic.web.atm.cards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.atm.common.FilterFormBase;

import java.util.*;

/**
 * @author Krenev
 * @ created 09.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardInfoATMForm extends FilterFormBase
{
	public static final Form CARD_FORM  = createForm();
	public static final Form FILTER_FORM  = createFilterForm();
	private Card card;
	private CardLink cardLink;
	private Set<CardLink> additionalCardInfoSet = new HashSet<CardLink>();
	private Client cardClient;
	private Client cardAccountClient;
	private Long warningPeriod;
	private CardAbstract cardAbstract;
	private List<CardLink> additionalCards;
	private List<CardLink> otherCards;
	private List<TemplateDocument> templates;
	private boolean isBackError = false;
	private List<AutoSubscriptionLink> moneyBoxes;
	private String abstractMsgError;

	public boolean isBackError()
	{
		return isBackError;
	}

	public void setBackError(boolean backError)
	{
		isBackError = backError;
	}

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}

	public List<CardLink> getAdditionalCards()
	{
		return additionalCards;
	}

	public void setAdditionalCards(List<CardLink> additionalCards)
	{
		this.additionalCards = additionalCards;
	}

	public List<CardLink> getOtherCards()
	{
		return otherCards;
	}

	public void setOtherCards(List<CardLink> otherCards)
	{
		this.otherCards = otherCards;
	}

	public CardAbstract getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(CardAbstract cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}

	public Card getCard() {
	    return card;
	}

	public void setCard(Card card) {
	    this.card = card;
	}

	public CardLink getCardLink() {
	    return cardLink;
	}

	public void setCardLink(CardLink cardLink) {
	    this.cardLink = cardLink;
	}

	public Set<CardLink> getAdditionalCardInfoSet()
	{
		return additionalCardInfoSet;
	}

	public void setAdditionalCardInfoSet(Set<CardLink> additionalCardInfoSet)
	{
		this.additionalCardInfoSet = additionalCardInfoSet;
	}

	public Client getCardClient()
	{
		return cardClient;
	}

	public void setCardClient(Client cardClient)
	{
		this.cardClient = cardClient;
	}

	public Long getWarningPeriod()
	{
		return warningPeriod;
	}

	public void setWarningPeriod(Long warningPeriod)
	{
		this.warningPeriod = warningPeriod;
	}

	public Client getCardAccountClient()
	{
		return cardAccountClient;
	}

	public void setCardAccountClient(Client cardAccountClient)
	{
		this.cardAccountClient = cardAccountClient;
	}

	/**
	 * Получение списка копилок
	 * @return Список копилок
	 */
	public List<AutoSubscriptionLink> getMoneyBoxes()
	{
		return moneyBoxes;
	}

	/**
	 * Установка списка копилок
	 * @param moneyBoxes Список копилок
	 */
	public void setMoneyBoxes(List<AutoSubscriptionLink> moneyBoxes)
	{
		this.moneyBoxes = moneyBoxes;
	}

	public String getAbstractMsgError()
	{
		return abstractMsgError;
	}

	public void setAbstractMsgError(String abstractMsgError)
	{
		this.abstractMsgError = abstractMsgError;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("cardName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,25}", "Наименование должно быть не более 25 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createFilterForm()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeAbstract == 'period' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате "+format+".");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(format);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typeAbstract");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "week", "month", "period" } ))
		);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("fromAbstract");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("toAbstract");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
	    formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromAbstract");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toAbstract");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}
}