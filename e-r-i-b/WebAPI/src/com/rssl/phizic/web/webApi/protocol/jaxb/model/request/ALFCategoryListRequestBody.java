package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.IncomeType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Тело запроса на получение справочника категорий АЛФ
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "body")
public class ALFCategoryListRequestBody extends SimpleRequestBody
{
	private IncomeType incomeType;
	private Long paginationSize;
	private Long paginationOffset;

	public static final Form LIST_FORM = createForm();

	@XmlElement(name = "incomeType", required = true)
	public IncomeType getIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(IncomeType incomeType)
	{
		this.incomeType = incomeType;
	}

	@XmlElement(name = "paginationSize", required = true)
	public Long getPaginationSize()
	{
		return paginationSize;
	}

	public void setPaginationSize(Long paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	@XmlElement(name = "paginationOffset", required = true)
	public Long getPaginationOffset()
	{
		return paginationOffset;
	}

	public void setPaginationOffset(Long paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("incomeType");
		fieldBuilder.setDescription("Тип категории");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"income", "outcome"}))
		);
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
