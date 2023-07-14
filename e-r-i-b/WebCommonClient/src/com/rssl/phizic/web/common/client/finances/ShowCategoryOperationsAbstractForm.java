package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author lepihina
 * @ created 20.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowCategoryOperationsAbstractForm extends FinanceFormBase implements ShowCategoryAbstractFormInterface
{
	public static final int MIN_RES_ON_PAGE = 10;

	private Long categoryId;
	private CardOperationCategory category;
	private List<CardOperationCategory> otherCategories; // список других категорий возможных дл€ опкераций
	private Map<String, String> newCategory = new HashMap<String, String>(); // мапа вида: ид_операции - ид_новой_категории

	public Long getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	public CardOperationCategory getCategory()
	{
		return category;
	}

	public void setCategory(CardOperationCategory category)
	{
		this.category = category;
	}

	public List<CardOperationCategory> getOtherCategories()
	{
		return otherCategories;
	}

	public void setOtherCategories(List<CardOperationCategory> otherCategories)
	{
		this.otherCategories = otherCategories;
	}

	public Map<String, String> getNewCategory()
    {
        return newCategory;
    }

	public void setNewCategory(Map<String, String> newCategory)
	{
		this.newCategory = newCategory;
	}

    public String getNewCategory(String key)
    {
        return newCategory.get(key);
    }

    public void setNewCategory(String key, String obj)
    {
        newCategory.put(key, obj);
    }

	public void addNewCategory(Map<String, String> newCategory)
	{
		this.newCategory.putAll(newCategory);
	}

	protected static Form createFilterForm()
	{
	    List<Field> fields = new ArrayList<Field>();

		// включать операции с наличными
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("generalCategory");
		fieldBuilder.setDescription("ќбща€ категори€");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
