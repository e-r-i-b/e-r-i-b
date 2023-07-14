package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.XSLTBean;
import com.rssl.phizic.business.payments.forms.meta.XSLTService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author gulov
 * @ created 01.03.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRTemplateOperation extends OperationBase implements ViewEntityOperation
{
	private static final XSLTService xsltService = new XSLTService();

	private XSLTBean xsltBean;

	public XSLTBean getEntity() throws BusinessException, BusinessLogicException
	{
		return xsltBean;
	}

	public void initialize() throws BusinessException
	{
		xsltBean = xsltService.getBeanByName(XSLTService.PFR_STATEMENT_NAME);
	}

	public void xsltLoad(String content) throws BusinessException
	{
		xsltBean.setXsltTemplate(content);

		xsltService.addOrUpdate(xsltBean);
	}

	public void xsdLoad(String content) throws BusinessException
	{
		xsltBean.setXsd(content);

		xsltService.addOrUpdate(xsltBean);
	}
}
