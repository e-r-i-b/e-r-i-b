package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

import java.util.*;

/**
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class ChangeCellTypeTermsOfLeaseForm extends EditFormBase
{
	private List<TermOfLease>  termOfLeases = new ArrayList<TermOfLease>();
	private String[]           selectedIds  = new String[0];


	/**
	 * @return список сроков аренды
	 */
	public List<TermOfLease> getTermsOfLease()
	{
		return Collections.unmodifiableList(termOfLeases);
	}

	/**
	 * @param termOfLeases список сроков аренды
	 */
	public void setTermsOfLease(List<TermOfLease> termOfLeases)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.termOfLeases = termOfLeases;
	}


	/**
	 * @return выбранные Ids
	 */
	public String[] getSelectedIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return selectedIds;
	}

	/**
	 * @param selectedIds выбранные Ids
	 */
	public void setSelectedIds(String[] selectedIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.selectedIds = selectedIds;
	}

	public static Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		// “ип сейфовой €чейки
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ќписание");
		fieldBuilder.setName("сellTypeDescription");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator(".{0,60}", "ƒлина пол€ [ќписание] не должна превышать 60 символов") );

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
