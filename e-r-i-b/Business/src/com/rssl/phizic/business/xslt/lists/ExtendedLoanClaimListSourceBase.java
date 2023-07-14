package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestionService;
import com.rssl.phizic.gate.loanclaim.dictionary.AbstractDictionaryEntry;
import com.rssl.phizic.gate.loanclaim.type.SquareUnits;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Map;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author EgorovaA
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕолучение справочников дл€ за€вки по кредиту
 */
public class ExtendedLoanClaimListSourceBase implements EntityListSource
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final LoanClaimDictionaryService service = new LoanClaimDictionaryService();
	private static final LoanClaimQuestionService questionnaireService = new LoanClaimQuestionService();

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		return new StreamSource(new StringReader(getDictionaryList().toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			return XmlHelper.parse(getDictionaryList().toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private XmlEntityBuilder getDictionaryList() throws BusinessException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("config");

		getDictionary(service.getCategoryOfPositionDictionary(), "list-category-of-position", builder);
		getDictionary(service.getEducationDictionary(), "list-education", builder);
		getDictionary(service.getFamilyRelationDictionary(), "list-family-relation", builder);
		getDictionary(service.getFamilyStatusDictionary(), "list-family-status", builder);
		getDictionary(service.getFormOfIncorporationDictionary(), "list-form-of-incorporation", builder);
		getDictionary(service.getJobExperienceDictionary(), "list-experience-on-current-job", builder);
		getDictionary(service.getKindOfActivityDictionary(), "list-types-of-company", builder);
		getDictionary(service.getLoanPaymentMethodDictionary(), "list-loan-payment-method", builder);
		getDictionary(service.getLoanPaymentPeriodDictionary(), "list-loan-payment-period", builder);
		getDictionary(service.getNumberOfEmployeesDictionary(), "list-number-of-employees", builder);
		getDictionary(service.getRegionDictionary(), "list-regions", builder);
		getDictionary(service.getResidenceRightDictionary(), "list-residence-right", builder);
		getDictionary(service.getTypeOfAreaDictionary(), "list-types-of-regions", builder);
		getDictionary(service.getTypeOfCityDictionary(), "list-types-of-cities", builder);
		getDictionary(service.getTypeOfDebitDictionary(), "list-type-of-debit", builder);
		getDictionary(service.getTypeOfLocalityDictionary(), "list-types-of-localities", builder);
		getDictionary(service.getTypeOfRealtyDictionary(), "list-type-of-realty", builder);
		getDictionary(service.getTypeOfStreetDictionary(), "list-types-of-streets", builder);
		getDictionary(service.getTypeOfVehicleDictionary(), "list-type-of-vehicle", builder);
		getDictionary(service.getWorkOnContractDictionary(), "list-work-on-contract", builder);
		getDictionary(service.getLoanIssueMethodAvailable(), "list-loan-issue-method", builder);

		getSquareUnitsDictionary(builder);
		getCurrenciesDictionary(builder);

		builder.closeEntityTag("config");
		return builder;
	}

	private void getDictionary(Collection<? extends AbstractDictionaryEntry> values, String nodeListTitle, XmlEntityBuilder builder) throws BusinessException
	{
		if (values.isEmpty())
			return;

		try
		{
			builder.openEntityTag(nodeListTitle);
			for (AbstractDictionaryEntry value : values)
			{
				builder.append(JAXBUtils.marshalBean(value, null, false));
			}
			builder.closeEntityTag(nodeListTitle);

		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * —формировать справочник единиц измерени€ площади
	 * @param builder
	 * @throws BusinessException
	 */
	private void getSquareUnitsDictionary(XmlEntityBuilder builder) throws BusinessException
	{
		SquareUnits[] squareUnits = SquareUnits.values();

		builder.openEntityTag("square-units");
		for (SquareUnits squareUnit : squareUnits)
		{
			builder.openEntityTag("square-unit");
			builder.createEntityTag("code", squareUnit.toString());
			builder.createEntityTag("name", squareUnit.getDescription());
			builder.closeEntityTag("square-unit");
		}
		builder.closeEntityTag("square-units");
	}

	/**
	 * —формировать справочник валют
	 * @param builder
	 * @throws BusinessException
	 */
	private void getCurrenciesDictionary(XmlEntityBuilder builder) throws BusinessException
	{
		builder.openEntityTag("currencies");

		builder.openEntityTag("currency");
		builder.createEntityTag("code", "RUR");
		builder.createEntityTag("name", CurrencyUtils.getCurrencySign("RUR"));
		builder.closeEntityTag("currency");

		builder.openEntityTag("currency");
		builder.createEntityTag("code", "USD");
		builder.createEntityTag("name", CurrencyUtils.getCurrencySign("USD"));
		builder.closeEntityTag("currency");

		builder.openEntityTag("currency");
		builder.createEntityTag("code", "EUR");
		builder.createEntityTag("name", CurrencyUtils.getCurrencySign("EUR"));
		builder.closeEntityTag("currency");

		builder.closeEntityTag("currencies");
	}

}
