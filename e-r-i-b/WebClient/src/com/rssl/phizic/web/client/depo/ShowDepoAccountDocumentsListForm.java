package com.rssl.phizic.web.client.depo;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 27.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountDocumentsListForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private Long id;
	private DepoAccountLink depoAccountLink;
	private List<DepoAccountLink> anotherDepoAccounts;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public DepoAccountLink getDepoAccountLink()
	{
		return depoAccountLink;
	}

	public void setDepoAccountLink(DepoAccountLink depoAccountLink)
	{
		this.depoAccountLink = depoAccountLink;
	}

	public List<DepoAccountLink> getAnotherDepoAccounts()
	{
		return anotherDepoAccounts;
	}

	public void setAnotherDepoAccounts(List<DepoAccountLink> anotherDepoAccounts)
	{
		this.anotherDepoAccounts = anotherDepoAccounts;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = FilterPeriodHelper.createFilterPeriodFormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер документа");
		fieldBuilder.setName("docNumber");
		fieldBuilder.setType("integer");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("docState");
		fieldBuilder.setDescription("Статус документа");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("docType");
		fieldBuilder.setDescription("Тип документа");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
