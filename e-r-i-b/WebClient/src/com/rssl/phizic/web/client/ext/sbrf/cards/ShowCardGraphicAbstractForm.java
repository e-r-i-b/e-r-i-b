package com.rssl.phizic.web.client.ext.sbrf.cards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.client.cards.CardInfoForm;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Erkin
 * @ created 28.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardGraphicAbstractForm extends ListFormBase implements CardInfoForm
{
	static final long MAX_PERIOD = 185 * 24 * 60 * 60 * 1000L; // 185 дней или чуть больше, чем полгода

	public static final Form FILTER_FORM  = createFilterForm();

	/**
	 * ID карты (ID кард-линка)
	 */
	private Long id;

	private CardLink cardLink;

//	private Map<CardLink, CardInfo> additionalCardInfoMap = new HashMap<CardLink, CardInfo>();

	private List<CardLink> additionalCards;

	private List<CardLink> otherCards;

	// Выписка по СКС
	private AccountAbstract cardAccountAbstract;

	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
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

	public AccountAbstract getCardAccountAbstract()
	{
		return cardAccountAbstract;
	}

	public void setCardAccountAbstract(AccountAbstract cardAccountAbstract)
	{
		this.cardAccountAbstract = cardAccountAbstract;
	}

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createFilterForm()
	{
		List<Field> fields = new ArrayList<Field>();
		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		Expression periodDatesExpression = new RhinoExpression("form.typeDate == 'period'");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typeDate");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"month", "period", "week"}))
		);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareDateValidator.setMessage("Конечная дата должна быть больше начальной!");

		DatePeriodMultiFieldValidator datePeriodValidator = new DatePeriodMultiFieldValidator();
		datePeriodValidator.setMaxTimeSpan(MAX_PERIOD);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, "fromDate");
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, "toDate");
		datePeriodValidator.setMessage("Период для формирования графической выписки должен быть не более полугода. " +
				"Пожалуйста, измените период.");

		formBuilder.setFormValidators(compareDateValidator, datePeriodValidator);

		return formBuilder.build();
	}
}
