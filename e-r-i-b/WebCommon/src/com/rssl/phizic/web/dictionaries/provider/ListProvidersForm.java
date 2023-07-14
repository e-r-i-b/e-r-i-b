package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 15.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListProvidersForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();
	private static final int ITEMS_PER_PAGE=12;
	private Integer currentPage = 0;

	public void incCurrentPage()
	{
		currentPage++;
	}

	public void decCurrentPage()
	{
		currentPage--;
	}

	public Integer getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}

	public Integer getItemsPerPage()
	{
		return ITEMS_PER_PAGE;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Регион");
		fieldBuilder.setName("regionName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Регион");
		fieldBuilder.setName("regionId");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Услуга");
		fieldBuilder.setName("paymentServiceName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Услуга");
		fieldBuilder.setName("paymentServiceId");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Расчетный счет");
		fieldBuilder.setName("account");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ИНН");
		fieldBuilder.setName("INN");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование департамента");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор департамента");
		fieldBuilder.setName("departmentId");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование биллинговой системы");
		fieldBuilder.setName("billingName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор биллинговой системы");
		fieldBuilder.setName("billingId");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус поставщика услуг");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип поставщика");
		fieldBuilder.setName("kind");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());


		return formBuilder.build();
	}
}
