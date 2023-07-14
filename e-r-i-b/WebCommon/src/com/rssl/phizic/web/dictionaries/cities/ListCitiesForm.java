package com.rssl.phizic.web.dictionaries.cities;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;

/**
 * @author lepihina
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCitiesForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();
	private int searchPage; //����� �������� ������
	private int resOnPage; //���������� ����� �� ��������

	public int getSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(int searchPage)
	{
		this.searchPage = searchPage;
	}

	public int getResOnPage()
	{
		return resOnPage;
	}

	public void setResOnPage(int resOnPage)
	{
		this.resOnPage = resOnPage;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ������");
		fieldBuilder.setName("cityName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
