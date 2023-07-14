package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import org.apache.struts.action.ActionForm;

import java.util.*;

/**
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class CellTypeListForm extends ListFormBase
{
	public static Form EDIT_FORM = createForm();
	private List<Office>                     offices                   = new ArrayList<Office>();
	private String                           newCellTypeDescription;
	private CellType                         selectedCellType;   //TODO DROP

	public List<Office> getOffices()
	{
		return offices;
	}

	public void setOffices(List<Office> offices)
	{
		this.offices = offices;
	}

	public String getNewCellTypeDescription()
	{
		return newCellTypeDescription;
	}

	public void setNewCellTypeDescription(String newCellType)
	{
		this.newCellTypeDescription = newCellType;
	}

	public CellType getSelectedCellType()
	{
		return selectedCellType;
	}

	public void setSelectedCellType(CellType selectedCellType)
	{
		this.selectedCellType = selectedCellType;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		// ����� ��� �������� ������
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("newCellTypeDescription");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(),
				                   new RegexpFieldValidator(".{0,60}", "����� ���� [��������] �� ������ ��������� 60 ��������"));

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
