package com.rssl.phizic.web.common.socialApi.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositHtmlConverter;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.deposits.DepositDetailsOperation;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.deposits.DepositDetailsForm;
import com.rssl.phizic.web.common.client.ext.sbrf.deposits.DepositDetailsExtendedAction;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 * @author Pankin
 * @ created 13.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class DepositDetailsMobileAction extends DepositDetailsExtendedAction
{
	protected Source getTemplateSource(DepositDetailsOperation op) throws BusinessException
	{
		return op.getMobileTemplateSource();
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		DepositDetailsMobileForm form =  (DepositDetailsMobileForm)frm;
		DepositDetailsOperation operation = createOperation("DepositDetailsOperation", "AccountOpeningClaim");
		operation.initialize(form.getDepositId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		DepositDetailsForm frm = (DepositDetailsForm)form;
		DepositDetailsOperation op = (DepositDetailsOperation)operation;
		DepositProduct depositProduct = (DepositProduct) operation.getEntity();
		if (depositProduct != null)
		{
				frm.setName(depositProduct.getName());

			try
			{
				Source templateSource = getTemplateSource(op);
				TransformerFactory fact = TransformerFactory.newInstance();
				fact.setURIResolver(new StaticResolver());
				Templates templates = fact.newTemplates(templateSource);
				XmlConverter converter = new DepositHtmlConverter(templates);
				frm.setHtml( getHtml(op, converter) );
			}
			catch (TransformerConfigurationException ex)
			{
				throw new BusinessException(ex);
			}
		}
	}

}
