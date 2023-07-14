package com.rssl.phizicgate.mock.loans;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.LoanProductsService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.XmlFileReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.util.*;
import java.io.IOException;
import java.io.File;
import java.io.FilenameFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

/**
 * @author gladishev
 * @ created 05.09.2008
 * @ $Author$
 * @ $Revision$
 */

public class MockLoanProductsService extends AbstractService implements LoanProductsService
{
	private final String CONDITION_XPATH = "//condition[./value[@name='conditionId' and text()='%S']]";

	private static final String LOAN_PRODUCT_TAG = "loan-product";
	private static final String LOAN_CONDITIONS_TAG = "conditions";
	private static final String LOAN_DYNAMIC_TAG = "dynamic";
	private static final String LOANS_INFO_FILE_NAME = "com/rssl/phizicgate/mock/loans/xml/loans-info.xml";
	private static final String ALL_CONDITIONS_FILE_NAME = "com/rssl/phizicgate/mock/loans/xml/all-conditions.xml";

	public MockLoanProductsService(GateFactory factory)
    {
        super(factory);
    }

	/**
    * Получение информации о всех кредитных продуктах.
    * Возвращенный документ содержит информацию, необходимую для создания кредитного продукта.
    * Формат возвращенного xml документа определяется реализацией.
    *
    *
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   public Document getLoansInfo() throws GateException
   {
	   try
	   {
		   return XmlHelper.loadDocumentFromResource(LOANS_INFO_FILE_NAME);
	   }
	   catch(IOException e)
	   {
		   throw new GateException(e);
	   }
	   catch(SAXException e)
	   {
		   throw new GateException(e);
	   }
	   catch(ParserConfigurationException e)
	   {
		   throw new GateException(e);
	   }
   }

	/**
    * Получение информации о кредитном продукте.
    * Формат документа определяется реализацией.
    *
    * @param conditions Список ID условий (Domain: ExternalID)
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   public Document getLoanProduct(List<String> conditions) throws GateException
   {
	   try
	   {
           Document allConditions = XmlHelper.loadDocumentFromResource(ALL_CONDITIONS_FILE_NAME);

		   DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		   Document loanProductDocument = documentBuilder.newDocument();
		   Element rootElement = loanProductDocument.createElement(LOAN_PRODUCT_TAG);
		   loanProductDocument.appendChild(rootElement);

		   Element conditionsElement = loanProductDocument.createElement(LOAN_CONDITIONS_TAG);
		   rootElement.appendChild(conditionsElement);

		   Element dynamicElement = loanProductDocument.createElement(LOAN_DYNAMIC_TAG);
		   conditionsElement.appendChild(dynamicElement);

		   XPath xpath = XPathFactory.newInstance().newXPath();

		   for (String condition : conditions)
		   {
			   XPathExpression pathExpression = xpath.compile(String.format(CONDITION_XPATH, condition));
			   Element conditionElement = (Element) pathExpression.evaluate(allConditions.getDocumentElement(), XPathConstants.NODE);

			   if (conditionElement != null )
			   {
				   conditionElement = (Element) loanProductDocument.importNode(conditionElement.cloneNode(true), true);
				   dynamicElement.appendChild(conditionElement);
			   }
		   }

		   return loanProductDocument;
	   }
	   catch(IOException e)
	   {
		   throw new GateException(e);
	   }
	   catch(SAXException e)
	   {
		   throw new GateException(e);
	   }
	   catch(ParserConfigurationException e)
	   {
		   throw new GateException(e);
	   }
	   catch(XPathExpressionException e)
	   {
		   throw new GateException(e);
	   }
   }
}
