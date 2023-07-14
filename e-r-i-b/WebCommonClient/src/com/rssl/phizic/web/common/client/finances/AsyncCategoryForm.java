package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.forms.types.IncomeTypeParser;

/**
 * @author Pankin
 * @ created 16.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class AsyncCategoryForm extends CategoriesListForm
{
    private Long id; //для удаления и редактирования
	private CardOperationCategory category;
	private String operationState;
	private String incomeType;

	public static final Form NEW_CATEGORY_FORM = createForm();

	private static Form createForm()
	{
		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		FormBuilder formBuilder = new FormBuilder();

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название категории");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, new RegexpFieldValidator(".{0,100}", "Название должно быть не более 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("incomeType");
		fieldBuilder.setDescription("Тип дохода");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setParser(new IncomeTypeParser());
		fieldBuilder.clearValidators();
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

	public CardOperationCategory getCategory()
	{
		return category;
	}

	public void setCategory(CardOperationCategory category)
	{
		this.category = category;
	}

	/**
	 * @return статус выполнения операции (добавление/удаление категории)
	 */
	public String getOperationState()
	{
		return operationState;
	}

	/**
	 * @param operationState - статус выполнения операции (добавление/удаление категории)
	 */
	public void setOperationState(String operationState)
	{
		this.operationState = operationState;
	}

	public String getIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(String incomeType)
	{
		this.incomeType = incomeType;
	}
}
