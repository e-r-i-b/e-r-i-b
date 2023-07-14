package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Тело запроса на установку бюджета для расходной категории
 * @author Jatsky
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "body")
public class ALFCategoryBudgetsetRequestBody extends SimpleRequestBody
{
	public static final Form SAVE_BUDGET_FORM = createForm();

	private Long   id;
	private String budget;

	@XmlElement(name = "id", required = false)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@XmlElement(name = "budget", required = false)
	public String getBudget()
	{
		return budget;
	}

	public void setBudget(String budget)
	{
		this.budget = budget;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		MoneyFieldValidator moneyValidator = new MoneyFieldValidator();
		moneyValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, "0");
		moneyValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, "99999999999.99");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Бюджет");
		fieldBuilder.setName("budget");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				moneyValidator
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
