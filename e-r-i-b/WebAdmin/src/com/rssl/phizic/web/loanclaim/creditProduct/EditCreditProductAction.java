package com.rssl.phizic.web.loanclaim.creditProduct;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.CreditProductEditOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loanclaim.Constants;

import java.util.*;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * Ёкшен редактировани€ кредитных продуктов.
 */
public class EditCreditProductAction extends EditActionBase
{
	private String[] currArr = new String[]{Constants.RUB, Constants.USD, Constants.EUR};

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditCreditProductForm form = (EditCreditProductForm) frm;
		CreditProductEditOperation op = createOperation(CreditProductEditOperation.class);
		if (form.getId() != null)
			op.initialize(form.getId());
		else
			op.initializeNew();
		form.setTbNumbers(op.getTbNumbers());
		return op;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditCreditProductForm)frm).createForm();
	}

	 protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> data) throws Exception
	{
		CreditProductEditOperation op =  (CreditProductEditOperation) editOperation;
		EditCreditProductForm form = (EditCreditProductForm) editForm;
		CreditProduct creditProduct = (CreditProduct) op.getEntity();
		creditProduct.setName((String)data.get(Constants.NAME));
		creditProduct.setCode((String) data.get(Constants.CODE));
		creditProduct.setCodeDescription((String) data.get(Constants.DESCRIPTION));
		creditProduct.setEnsuring((Boolean)data.get(Constants.ENSURING));

		Set<CreditSubProductType> addSubProductSet = op.getAddSubProducts();
		for (String tb:form.getTbNumbers())
		{
			for(String curr:currArr)
			{
				String fieldName = curr + tb;
				String code = (String)data.get(fieldName);
				CreditSubProductType subType = new CreditSubProductType();
				subType.setCurrency(op.getCurrency(curr));
				subType.setCreditProduct(creditProduct);
				subType.setTerbank(tb);
				subType.setCode(code);
				addSubProductSet.add(subType);
			}
		}
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		CreditProduct creditProduct = (CreditProduct) entity;
		frm.setField(Constants.NAME, creditProduct.getName());
		frm.setField(Constants.CODE, creditProduct.getCode());
		frm.setField(Constants.DESCRIPTION, creditProduct.getCodeDescription());
		frm.setField(Constants.ENSURING, creditProduct.isEnsuring());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		CreditProductEditOperation op = (CreditProductEditOperation)operation;
		EditCreditProductForm frm = (EditCreditProductForm) form;

		for (String tb: frm.getTbNumbers())
		{
			frm.setField(Constants.TB_NAME + tb, op.getTbName(tb));
			for(String curr:currArr)
			{
				frm.setField(curr + tb, op.getCodeByTbAndCurrency(tb,curr));
			}
		}
	}
}
