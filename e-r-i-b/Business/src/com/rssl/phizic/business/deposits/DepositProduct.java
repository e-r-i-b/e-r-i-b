package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.sun.org.apache.xerces.internal.dom.NodeImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import static java.lang.String.format;

/**
 * Описание депозитного продукта
 * @author Kidyaev
 * @ created 05.05.2006
 * @ $Author$
 * @ $Revision$
 */
public class DepositProduct extends DictionaryRecordBase implements MultiBlockDictionaryRecord
{
	private static final String CURRENCY_XPATH  = "//data/element/value[@field='currency']/text()";
	private static final String PERIOD_XPATH    = "//data/element[./value[@field='currency']/text()='%S']/value[@field='period']/text()";
	private static final String MINAMOUNT_XPATH = "//data/element[./value[@field='currency']/text()='%S' and ./value[@field='period']/text()='%S']/value[@field='minimumAmount']/text()";

	protected Long id;
	protected Long departmentId;
	protected String name;
	protected String productDescription;
	protected String description;
	protected String details;
	protected Long productId;
	protected boolean availableOnline;
	protected Calendar lastUpdateDate;
	protected List<String> allowedDepartments;
	protected boolean withMinimumBalance;
	protected boolean capitalization;

	public Long getId ()
	{
		return id;
	}

	public void setId ( Long id )
	{
		this.id = id;
	}

	/**
	 * Имя депозитного продукта
	 */
	public String getName ()
	{
		return name;
	}

	public void setName ( String name )
	{
		this.name = name;
	}

	/**
	 * Описание депозитного продукта
	 */

	public String getProductDescription()
	{
		return productDescription;
	}

	public void setProductDescription(String productDescription)
	{
		this.productDescription = productDescription;
	}

	/**
	 * XML представление депозитного продукта
	 */
	public String getDescription ()
	{
		return description;
	}

	public void setDescription ( String description )
	{
		this.description = description;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	/**
	 * Возможно ли «Открыть в системе» вклад по данному депозитному продукту.
	 */
	public boolean isAvailableOnline()
	{
		return availableOnline;
	}

	public void setAvailableOnline(boolean availableOnline)
	{
		this.availableOnline = availableOnline;
	}

	/**
	 * @return дата последнего обновления
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * установить дату последнего обновления
	 * @param lastUpdateDate дата последнего обновления
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * XSLT-преобразование для вывода подробной информации о продукте
	 */
	public String getDetails ()
	{
		return details;
	}

	public void setDetails ( String details )
	{
		this.details = details;
	}

	public String toString()
	{
		return name;
	}

	public BigDecimal getMinAmount(String currencyValue, String periodvalue) throws BusinessException
	{
		if (description == null)
		{
			return null;
		}
		try
		{
			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(MINAMOUNT_XPATH, currencyValue, periodvalue));

			NodeImpl amountElement = (NodeImpl) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODE);
			String amount = amountElement.getTextContent();

			return new BigDecimal(amount);
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

	public List<String> getCurrencies() throws BusinessException
	{
		List<String> result = new ArrayList<String>();
		try
		{
			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(CURRENCY_XPATH));

			NodeList currencyElement = (NodeList) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODESET);
			for (int i=0; i<currencyElement.getLength(); i++){
				result.add(currencyElement.item(i).getTextContent());
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

	public List<String> getPeriods(String currencyValue) throws BusinessException
	{
		List<String> result = new ArrayList<String>();
		try
		{
			Document document = XmlHelper.parse(description);
			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression pathExpression = xpath.compile(format(PERIOD_XPATH, currencyValue));

			NodeList periods = (NodeList) pathExpression.evaluate(document.getDocumentElement(), XPathConstants.NODESET);
			for (int i=0; i<periods.getLength(); i++){
				result.add(periods.item(i).getTextContent());
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

	public Comparable getSynchKey()
	{
		return productId;
	}

	/**
	 * @return номер вида вклада из внешнего справочника
	 */
	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	/**
	 * @return тербанки, в которых разрешено открытие вклада
	 */
	public List<String> getAllowedDepartments()
	{
		return allowedDepartments;
	}

	public void setAllowedDepartments(List<String> allowedDepartments)
	{
		this.allowedDepartments = allowedDepartments;
	}

	/**
	 * @return вклад с неснижаемым остатком или без
	 */
	public boolean isWithMinimumBalance()
	{
		return withMinimumBalance;
	}

	public void setWithMinimumBalance(boolean withMinimumBalance)
	{
		this.withMinimumBalance = withMinimumBalance;
	}

	/**
	 * @return доступна капитализация или нет
	 */
	public boolean isCapitalization()
	{
		return capitalization;
	}

	public void setCapitalization(boolean capitalization)
	{
		this.capitalization = capitalization;
	}

	public String getMultiBlockRecordId()
	{
		return productId.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositProduct) that).setId(getId());
		super.updateFrom(that);
		if (getDepartmentId().equals(0L))
		{
			// BeanHelper null превращает в 0, возвращаем обратно.
			setDepartmentId(null);
		}
	}
}
