package com.rssl.phizic.web.pfp;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetCountService;
import com.rssl.phizic.business.pfp.PersonLoan;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.List;

/**
 * @author mihaylov
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowPfpFinancePlanForm extends EditPersonalFinanceProfileForm
{
	public static final Integer MAX_CREDIT_AGE = 60;
	public static final Form CHANGE_PORTFOLIO_AMOUNT_FORM = getChangePortfolioAmountForm();
	public static final Form CHANGE_PORTFOLIO_INCOME_FORM = getChangePortfolioIncomeForm();

	private Calendar executionDate;
	private Calendar lastTargetDate; // дата достижени€ последней(по времени) цели
	private PersonPortfolio startCapital; //портфель стартовый капитал клиента
	private PersonPortfolio quarterlyInvest; //портфель ежеквартальные вложени€ клиента
	private List<PersonTarget> personTargetList;//цели клиента
	private List<PersonLoan> personLoanList;
	private boolean elderPerson;

	private List<LoanKindProduct> dictionaryLoanList;//справочник кредитных продуктов

	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	public boolean isElderPerson()
	{
		return elderPerson;
	}

	public void setElderPerson(boolean elderPerson)
	{
		this.elderPerson = elderPerson;
	}

	public PersonPortfolio getStartCapital()
	{
		return startCapital;
	}

	public void setStartCapital(PersonPortfolio startCapital)
	{
		this.startCapital = startCapital;
	}

	public PersonPortfolio getQuarterlyInvest()
	{
		return quarterlyInvest;
	}

	public void setQuarterlyInvest(PersonPortfolio quarterlyInvest)
	{
		this.quarterlyInvest = quarterlyInvest;
	}

	/**
	 * @return ¬озвращаем максимальное количество целей клиента
	 */
	public Long getMaxTargetCount()
	{
		return TargetCountService.getTargetCountSafe();
	}

	public List<PersonTarget> getPersonTargetList()
	{
		return personTargetList;
	}

	public void setPersonTargetList(List<PersonTarget> personTargetList)
	{
		this.personTargetList = personTargetList;
	}

	/**
	 * @return дата достижени€ последней(по времени) цели
	 */
	public Calendar getLastTargetDate()
	{
		return lastTargetDate;
	}

	/**
	 * ”становить дату достижени€ последней(по времени) цели
	 * @param lastTargetDate - дата достижени€ последней цели
	 */
	public void setLastTargetDate(Calendar lastTargetDate)
	{
		this.lastTargetDate = lastTargetDate;
	}

	/**
	 * @return —правочник кредитных продуктов
	 */
	public List<LoanKindProduct> getDictionaryLoanList()
	{
		return dictionaryLoanList;
	}

	/**
	 * @param dictionaryLoanList —правочник кредитных продуктов
	 */
	public void setDictionaryLoanList(List<LoanKindProduct> dictionaryLoanList)
	{
		this.dictionaryLoanList = dictionaryLoanList;
	}

	public List<PersonLoan> getPersonLoanList()
	{
		return personLoanList;
	}

	public void setPersonLoanList(List<PersonLoan> personLoanList)
	{
		this.personLoanList = personLoanList;
	}

	private static Form getChangePortfolioAmountForm()
	{
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getChangedPortfolioField().build());

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ƒополнительные вложени€");
		fieldBuilder.setName("addAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form getChangePortfolioIncomeForm()
	{
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getChangedPortfolioField().build());

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Ќеобходима€ доходность портфел€");
		fieldBuilder.setName("wantedIncome");
		fieldBuilder.setParser(new BigDecimalParser(1));
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static FieldBuilder getChangedPortfolioField()
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("»змен€емый портфель");
		fieldBuilder.setName("changedPortfolio");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),new EnumFieldValidator<PortfolioType>(PortfolioType.class, "Ќекорректный тип измен€емого портфел€."));
		return fieldBuilder;
	}
}

