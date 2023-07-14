package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Форма создания/редактирования правила включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 05.12.13
 * @ $Author$
 * @ $Revision$
 */
public class ErmbBankrollProductRuleEditForm extends EditFormBase
{
	private Long[] selectedTerbankIds = new Long[]{};
	private List<Long> selectedTBIds;
	private Set<Department>  terbanks;
	private boolean cardsVisibility;
	private boolean cardsNotification;
	private boolean depositsVisibility;
	private boolean depositsNotification;
	private boolean loansVisibility;
	private boolean loansNotification;
	private boolean newProductsVisibility;
	private boolean newProductsNotification;
	private List<ErmbTariff> tariffs;
	private Long selectedTariff;
	private boolean state;

	public Long[] getSelectedTerbankIds()
	{
		return selectedTerbankIds;
	}

	public void setSelectedTerbankIds(Long[] selectedTerbankIds)
	{
		this.selectedTerbankIds = selectedTerbankIds;
	}

	public List<Long> getSelectedTBIds()
	{
		return selectedTBIds;
	}

	public void setSelectedTBIds(List<Long> selectedTBIds)
	{
		this.selectedTBIds = selectedTBIds;
	}

	public Set<Department> getTerbanks()
	{
		return terbanks;
	}

	public void setTerbanks(Set<Department> terbanks)
	{
		this.terbanks = terbanks;
	}

	public boolean isCardsVisibility()
	{
		return cardsVisibility;
	}

	public void setCardsVisibility(boolean cardsVisibility)
	{
		this.cardsVisibility = cardsVisibility;
	}

	public boolean isCardsNotification()
	{
		return cardsNotification;
	}

	public void setCardsNotification(boolean cardsNotification)
	{
		this.cardsNotification = cardsNotification;
	}

	public boolean isDepositsVisibility()
	{
		return depositsVisibility;
	}

	public void setDepositsVisibility(boolean depositsVisibility)
	{
		this.depositsVisibility = depositsVisibility;
	}

	public boolean isDepositsNotification()
	{
		return depositsNotification;
	}

	public void setDepositsNotification(boolean depositsNotification)
	{
		this.depositsNotification = depositsNotification;
	}

	public boolean isLoansVisibility()
	{
		return loansVisibility;
	}

	public void setLoansVisibility(boolean loansVisibility)
	{
		this.loansVisibility = loansVisibility;
	}

	public boolean isLoansNotification()
	{
		return loansNotification;
	}

	public void setLoansNotification(boolean loansNotification)
	{
		this.loansNotification = loansNotification;
	}

	public boolean isNewProductsVisibility()
	{
		return newProductsVisibility;
	}

	public void setNewProductsVisibility(boolean newProductsVisibility)
	{
		this.newProductsVisibility = newProductsVisibility;
	}

	public boolean isNewProductsNotification()
	{
		return newProductsNotification;
	}

	public void setNewProductsNotification(boolean newProductsNotification)
	{
		this.newProductsNotification = newProductsNotification;
	}

	public List<ErmbTariff> getTariffs()
	{
		return tariffs;
	}

	public void setTariffs(List<ErmbTariff> tariffs)
	{
		this.tariffs = tariffs;
	}

	public Long getSelectedTariff()
	{
		return selectedTariff;
	}

	public void setSelectedTariff(Long selectedTariff)
	{
		this.selectedTariff = selectedTariff;
	}

	public boolean isState()
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;
	}

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "Название должно быть не более 50 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("ageFrom");
		fieldBuilder.setDescription("минимальный возраст");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "Поле минимальный возраст может содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("ageUntil");
		fieldBuilder.setDescription("максимальный возраст");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "Поле максимальный возраст может содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Категория клиента");
		fieldBuilder.setName("clientCategory");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Условие наличия кредитных карт у клиента");
		fieldBuilder.setName("creditCardsCriteria");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Условие наличия депозитов у клиента");
		fieldBuilder.setName("depositsCriteria");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Условие наличия кредитов у клиента");
		fieldBuilder.setName("loansCriteria");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		List<MultiFieldsValidator> multiFieldsValidators = new ArrayList<MultiFieldsValidator>(2);
		CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "ageFrom");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "ageUntil");
	    compareValidator.setMessage("Вы неправильно указали минимальный возраст. Минимальный возраст не должен превышать максимальный возраст.");
		multiFieldsValidators.add(compareValidator);

		CompositeRequiredFieldValidator requiredAgeValidator = new CompositeRequiredFieldValidator("Укажите хотя бы одно значение возраста");
		requiredAgeValidator.setParameter("param1", "ageFrom");
		requiredAgeValidator.setParameter("param2", "ageUntil");
		multiFieldsValidators.add(requiredAgeValidator);

		formBuilder.setFormValidators(multiFieldsValidators);

		return formBuilder.build();
	}
}
