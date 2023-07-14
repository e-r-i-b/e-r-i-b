package com.rssl.phizic.web.client.loans.products;

import com.rssl.phizic.operations.loans.products.GetLoanProductListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.business.loans.LoanHtmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author gladishev
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanProductAction extends ListActionBase
{
	private static final String FORWARD_DETAILS = "Details";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetLoanProductListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException, BusinessLogicException, TransformerConfigurationException
	{
		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException, TransformerConfigurationException
	{
		HttpServletRequest request = currentRequest();
		GetLoanProductListOperation op = (GetLoanProductListOperation) operation;

		Source templateSource = op.getTemplateSource();
		TransformerFactory fact = TransformerFactory.newInstance();
		fact.setURIResolver(new StaticResolver());
		Templates templates = null;
		templates = fact.newTemplates(templateSource);
		XmlConverter converter = new LoanHtmlConverter(templates);
		converter.setData(getDataSource(op));
		converter.setParameter("webRoot", request.getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
		converter.setParameter("detailsUrl", request.getContextPath() + getCurrentMapping().findForward(FORWARD_DETAILS).getPath());
		ListLoanProductForm frm = (ListLoanProductForm) form;
		String html = converter.convert().toString();
		frm.setListHtml(html);
	}

	private Source getDataSource(GetLoanProductListOperation operation) throws BusinessException
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
        Document resultDoc = documentBuilder.newDocument();
		Element dataElement = resultDoc.createElement("data");
		resultDoc.appendChild(dataElement);
		Element productsRoot = (Element) resultDoc.importNode(operation.getProductListDocument().getDocumentElement(), true);
		dataElement.appendChild(productsRoot);
		Element kindsRoot = (Element) resultDoc.importNode(operation.getKindListDocument().getDocumentElement(), true);
		dataElement.appendChild(kindsRoot);

		return new DOMSource(resultDoc);
	}
}
