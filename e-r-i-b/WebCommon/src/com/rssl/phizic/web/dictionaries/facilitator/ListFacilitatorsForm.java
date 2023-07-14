package com.rssl.phizic.web.dictionaries.facilitator;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * ����� ��� ������ �� ����������� �������������
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListFacilitatorsForm extends ListFormBase<Object[]>
{
	public static final Form FILTER_FORM = createForm();

	public static final int DEFAULT_SIZE_VALUE = 10;
	public static final int DEFAULT_OFFSET_VALUE = 0;

	private static final String NAME_FIELD_NAME = "name";
	private static final String INN_FIELD_NAME = "inn";

	private boolean searchByFacilitator = true;
	private int paginationOffset = DEFAULT_OFFSET_VALUE;
	private int paginationSize = DEFAULT_SIZE_VALUE;

	/**
	 * @return true - ����� �� ������������, false - ����� �� ���
	 */
	public boolean isSearchByFacilitator()
	{
		return searchByFacilitator;
	}

	/**
	 * ���������� ��� ������ (true - ����� �� ������������, false - ����� �� ���)
	 * @param searchByFacilitator - �������
	 */
	public void setSearchByFacilitator(boolean searchByFacilitator)
	{
		this.searchByFacilitator = searchByFacilitator;
	}

	/**
	 * @return �������� ���������
	 */
	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	/**
	 * ������ �������� ���������
	 * @param paginationOffset - ��������
	 */
	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	/**
	 * @return ������ ���������
	 */
	public int getPaginationSize()
	{
		return paginationSize;
	}

	/**
	 * ������ ������ ���������
	 * @param paginationSize - ������
	 */
	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName(NAME_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName(INN_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,12}", StrutsUtils.getMessage("facilitators.wrongFilterFields.message", "providerBundle"))
		);
		formBuilder.addField(fieldBuilder.build());

		RequiredMultiFieldValidator multiFieldValidator = new RequiredMultiFieldValidator();
		multiFieldValidator.setBinding(NAME_FIELD_NAME, NAME_FIELD_NAME);
		multiFieldValidator.setBinding(INN_FIELD_NAME, INN_FIELD_NAME);
		multiFieldValidator.setMessage(StrutsUtils.getMessage("facilitators.wrongFilterFields.message", "providerBundle"));

		formBuilder.addFormValidators(multiFieldValidator);
		return formBuilder.build();
	}
}
