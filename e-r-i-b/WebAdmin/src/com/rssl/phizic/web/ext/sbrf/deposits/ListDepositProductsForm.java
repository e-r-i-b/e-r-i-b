package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author Pankin
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListDepositProductsForm extends ListFormBase
{
	private String listHtml;
	private Map<String, List<DepositProductEntity>> depositProducts;
	private boolean noDepositListAccess;

	public String getListHtml()
	{
		return listHtml;
	}

	public void setListHtml(String listHtml)
	{
		this.listHtml = listHtml;
	}

	public Map<String, List<DepositProductEntity>> getDepositProducts()
	{
		return depositProducts;
	}

	public void setDepositProducts(Map<String, List<DepositProductEntity>> depositProducts)
	{
		this.depositProducts = depositProducts;
	}

	public void setNoDepositListAccess(boolean noDepositListAccess)
	{
		this.noDepositListAccess = noDepositListAccess;
	}

	public boolean isNoDepositListAccess()
	{
		return noDepositListAccess;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,100}", "Наименование вклада не должно превышать 100 симоволов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("tariff");
		fieldBuilder.setDescription("Тарифный план");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("group");
		fieldBuilder.setDescription("Группа");
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
