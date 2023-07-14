package com.rssl.phizic.web.dictionaries.cells;

import org.apache.struts.action.ActionForm;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Kidyaev
 * @ created 14.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class TermOfLeaseListForm extends ListFormBase<TermOfLease>
{
	public static final Form EDIT_FORM = getForm();

	private String            newTermOfLeaseDescription;
	private Long              newTermOfLeaseSortOrder;

	public Long getNewTermOfLeaseSortOrder()
	{
		return newTermOfLeaseSortOrder;
	}

	public void setNewTermOfLeaseSortOrder(Long newTermOfLeaseSortOrder)
	{
		this.newTermOfLeaseSortOrder = newTermOfLeaseSortOrder;
	}

	public String getNewTermOfLeaseDescription()
	{
		return newTermOfLeaseDescription;
	}

	public void setNewTermOfLeaseDescription(String newCellType)
	{
		this.newTermOfLeaseDescription = newCellType;
	}

	private static Form getForm()
	{
		FormBuilder fb = new FormBuilder();

		// Новый срок аренды сейфовой ячейки
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setName("newTermOfLeaseDescription");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(),
		                           new RegexpFieldValidator(".{0,60}", "Длина поля \\u0022Описание\\u0022 не должна превышать 60 символов"));

		fb.addField(fieldBuilder.build());

            NumericRangeValidator numericRangeValidatorCount = new NumericRangeValidator();
		numericRangeValidatorCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "-2147483648");
		numericRangeValidatorCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "2147483647");
		numericRangeValidatorCount.setMessage("Неверный формат данных в поле \\u0022Порядок сортировки\\u0022, введите числовое значение в диапазоне -2147483648...2147483647");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер для сортировки");
		fieldBuilder.setName("newTermOfLeaseSortOrder");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(), numericRangeValidatorCount);

		fb.addField(fieldBuilder.build());
		return fb.build();
	}

}
