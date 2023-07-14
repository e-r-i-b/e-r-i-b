package com.rssl.phizic.web.client.loans;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Krenev
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimForm extends ActionFormBase
{
	public static final Form FORM =  createForm();

	private Map<String,Object>  fields    = new HashMap<String, Object>();
	private List<LoanOffice> loanOffices;
	private Map<String, LoanKind> loanKinds;
	private Map<String, LoanProduct> loanProducts;
	private boolean force;

	public List <LoanOffice> getLoanOffices()
	{
		return loanOffices;
	}

	public void setLoanOffices(List<LoanOffice> loanOffices)
	{
		this.loanOffices = loanOffices;
	}

	public Map<String, LoanKind> getLoanKinds()
	{
		return loanKinds;
	}

	public void setLoanKinds(Map<String, LoanKind> loanKinds)
	{
		this.loanKinds = loanKinds;
	}

	public Map<String, LoanProduct> getLoanProducts()
	{
		return loanProducts;
	}

	public void setLoanProducts(Map<String, LoanProduct> loanProducts)
	{
		this.loanProducts = loanProducts;
	}


	public Map<String, Object> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}

		public boolean getForce()
	{
		return force;
	}

	public void setForce(boolean force)
	{
		this.force = force;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Офис получения кредита");
		fieldBuilder.setName("office");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Вид кредита");
		fieldBuilder.setName("kind");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Кредитный продукт");
		fieldBuilder.setName("product");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
