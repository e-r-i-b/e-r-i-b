package com.rssl.phizic.business.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import static java.lang.String.format;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

/**
 * @author gladishev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕри изменении, добавлении, удалении свойств, необходимо
 * модифицировать метод loanProductEquals класса LoanProductHelper.
 */
public class LoanProduct extends LoanProductBase
{
	private static final String CONDITION_ID_XPATH = "//condition[./value[@name='currency']/text()= '%S' and ./value[@name='selected-office']/text()='%S']/value[@name='conditionId']";
	private static final String CURRENCY_XPATH = "//condition[./value[@name='conditionId' and text()='%S']]/value[@name=\"currency\"]";
	private static final String MIN_AMOUNT_XPATH = "//condition[./value[@name='conditionId' and text()='%S']]/value[@name=\"minAmount\"]";
	private static final String MAX_AMOUNT_XPATH = "//condition[./value[@name='conditionId' and text()='%S']]/value[@name=\"maxAmount\"]";
	private static final String MIN_INITAL_INSTALMENT_XPATH = "//condition[./value[@name='conditionId' and text()='%S']]/value[@name=\"minInitialInstalment\"]";
	private static final String OFFICE_ID_XPATH = "//condition/value[@name='selected-office']";
	private static final String DURATION_XPATH = "//condition[./value[@name='conditionId' and text()='%S']]/value[@name=\"duration\"]";
	private static final LoanOfficeService service = new LoanOfficeService();

	private String briefDescription;
	private String description;
	private Long packageTemplateId;
	private Long guarantorsCount;
	private boolean anonymousClaim = false;

	/**
	 * ѕакет документов
	 */
	public Long getPackageTemplateId()
	{
		return packageTemplateId;
	}

	public void setPackageTemplateId(Long packageTemplateId)
	{
		this.packageTemplateId = packageTemplateId;
	}

	/**
	 *  раткое описание кредитного продукта
	 */
	public String getBriefDescription()
	{
		return briefDescription;
	}

	public void setBriefDescription(String briefDescription)
	{
		this.briefDescription = briefDescription;
	}

	/**
	 * XML, описывающий услови€ кредитного продукта
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * ѕризнак доступности анонимным пользовател€м
	 */
	public Boolean getAnonymousClaim()
	{
		return anonymousClaim;
	}

	public void setAnonymousClaim(Boolean anonymousClaim)
	{
		this.anonymousClaim = anonymousClaim;
	}

	/**
	 *  оличество поручителей
	 */
	public Long getGuarantorsCount()
	{
		return guarantorsCount;
	}

	public void setGuarantorsCount(Long guarantorsCount)
	{
		this.guarantorsCount = guarantorsCount;
	}

	/**
	 * @return вернуть список офисов.
	 */
	public Set<LoanOffice> getOffices() throws BusinessException
	{
		if (description == null)
		{
			return null;
		}
		try
		{
			final Set<LoanOffice> offices = new HashSet<LoanOffice>();
			Document document = XmlHelper.parse(description);
			XmlHelper.foreach(document.getDocumentElement(), OFFICE_ID_XPATH, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String key = element.getTextContent();
					LoanOffice office = service.findSynchKey(new BigDecimal(key));//TODO ох уж мне эти стправочники, с их SynchKey
					offices.add(office);
				}
			}
			);
			return offices;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param officeExternalId внешний id офиса
	 * @return ¬нешний ID определ€ющий услови€ кредита
	 */
	public String getConditionId(String officeExternalId, Currency currency) throws BusinessException
	{
		if (description == null)
		{
			return null;
		}
		try
		{
			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression pathExpression = xpath.compile(format(CONDITION_ID_XPATH, currency.getCode(), officeExternalId));

			NodeList list = (NodeList) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODESET);
			if (list.getLength() != 1)
			{
				throw new BusinessException("Ќайдено 0 или более 1 'conditionId' дл€ выбранного офиса: " + officeExternalId+"и валюты: "+currency.getCode());
			}
			return list.item(0).getTextContent();
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (XPathExpressionException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
	}

	public Money getMinAmount(String conditionId) throws BusinessException
	{
		if (description == null)
		{
			return null;
		}
		try
		{
			Currency currency = getCurrency(conditionId);

			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(MIN_AMOUNT_XPATH, conditionId));

			Element amountElement = (Element) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODE);
			String amount = amountElement.getTextContent();
			return new Money(new BigDecimal(amount),currency);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (XPathExpressionException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
	}

	public Money getMaxAmount(String conditionId) throws BusinessException
	{
		if (description == null)
		{
			return null;
		}
		Currency currency = getCurrency(conditionId);
		try
		{
			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(MAX_AMOUNT_XPATH, conditionId));

			Element amountElement = (Element) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODE);
			String amount = amountElement.getTextContent();
			return new Money(new BigDecimal(amount),currency);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (XPathExpressionException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
	}

	public BigDecimal getMinInitialInstalment(String conditionId) throws BusinessException
	{
		if (description == null)
		{
			return null;
		}
		try
		{
			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(MIN_INITAL_INSTALMENT_XPATH, conditionId));

			Element minInitialInstalmentElement = (Element) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODE);
			if (minInitialInstalmentElement == null){
				return BigDecimal.ZERO;
			}
			String minInitialInstalment = minInitialInstalmentElement.getTextContent();
			if (minInitialInstalment == null || minInitialInstalment.length()==0){
				return BigDecimal.ZERO;
			}
			return new BigDecimal(minInitialInstalment);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (XPathExpressionException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
	}

	private Currency getCurrency(String conditionId) throws BusinessException
	{
		try
		{
			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(CURRENCY_XPATH, conditionId));

			Element currencyElement = (Element) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODE);
			String currency = currencyElement.getTextContent();
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		    return currencyService.findByAlphabeticCode(currency);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (XPathExpressionException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ¬озвращает список сроков кредитовани€ дл€ condition
	 * @param conditionId - id кондишиона
	 * @return список сроков кредитовани€
	 * @throws BusinessException
	 */
	public List<String> getDurations(String conditionId) throws BusinessException
	{
		if (description == null)
		{
			return null;
		}
		List<String> result = new ArrayList<String>();
		try
		{
			Document document = XmlHelper.parse(description);

			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(DURATION_XPATH, conditionId));

			NodeList durations = (NodeList) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODESET);
			for (int i = 0; i < durations.getLength(); i++)
			{
				Element element = (Element) durations.item(i);
				result.add(element.getTextContent());
			}
			return result;
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (XPathExpressionException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
	}
}
