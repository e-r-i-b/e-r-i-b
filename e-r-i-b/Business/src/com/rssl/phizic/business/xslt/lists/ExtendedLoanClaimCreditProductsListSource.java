package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Balovtsev
 * @since 04.02.2015.
 */
public class ExtendedLoanClaimCreditProductsListSource implements EntityListSource
{
	private static final CreditProductConditionService creditConditionService = new CreditProductConditionService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final DepartmentService departmentService = new DepartmentService();

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		return new StreamSource(new StringReader(getEntityList().toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			return XmlHelper.parse(getEntityList().toString());
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

	private EntityList getEntityList()
	{
		EntityList                   entityList = new EntityList();
		List<CreditProductCondition> conditions = new ArrayList<CreditProductCondition>();

		try
		{
			Department department = PersonContext.getPersonDataProvider().getPersonData().getTb();
			String personsTB = department != null ?  department.getRegion() : null;
			conditions = creditConditionService.getAvaliableConditions();
			List<String> terbankNumbers =  departmentService.getTerbanksNumbers();
			for (CreditProductCondition condition : conditions)
			{
				if (!CreditProductConditionHelper.isCreditProductConditionAvailable(personsTB, condition, terbankNumbers))
				{
					continue;
				}

				CreditProductType creditProductType = condition.getCreditProductType();
				CreditProduct     creditProduct     = condition.getCreditProduct();

				Entity conditionEntity = new Entity("CONDITION", null);
				conditionEntity.addField(new Field("id", String.valueOf(condition.getId())));
				conditionEntity.addField(new Field("creditTypeId", String.valueOf(creditProductType.getId())));
				conditionEntity.addField(new Field("creditProductId", String.valueOf(creditProduct.getId())));
				conditionEntity.addField(new Field("includeMaxDuration", String.valueOf(condition.isMaxRangeInclude())));
				conditionEntity.addField(new Field("minDuration", String.valueOf(condition.getMinDuration().getDuration())));
				conditionEntity.addField(new Field("maxDuration", String.valueOf(condition.getMaxDuration().getDuration())));
				conditionEntity.addField(new Field("additionalConditions", condition.getAdditionalConditions()));
				conditionEntity.addField(new Field("json",                 LoanClaimHelper.getJsonLoanField(condition)));

				Entity creditTypeEntity = new Entity("CREDIT_PRODUCT_TYPE", null);
				creditTypeEntity.addField(new Field("id",   String.valueOf(creditProductType.getId())));
				creditTypeEntity.addField(new Field("name", creditProductType.getName()));
				creditTypeEntity.addField(new Field("code", creditProductType.getCode()));
				conditionEntity.addEntity(creditTypeEntity);

				Entity creditProductEntity = new Entity("CREDIT_PRODUCT", null);
				creditProductEntity.addField(new Field("creditTypeId", String.valueOf(creditProductType.getId())));
				creditProductEntity.addField(new Field("id",           String.valueOf(creditProduct.getId())));
				creditProductEntity.addField(new Field("name",         creditProduct.getName()));
				creditProductEntity.addField(new Field("ensuring",     String.valueOf(creditProduct.isEnsuring())));
				conditionEntity.addEntity(creditProductEntity);

				EntityList currencyConditions = new EntityList();
				for(CurrencyCreditProductCondition currencyCondition : condition.getCurrConditions())
				{
					Entity currencyConditionEntity = new Entity("CURRENCY_CONDITION", null);
					currencyConditionEntity.addField(new Field("conditionId",      String.valueOf(condition.getId())));
					currencyConditionEntity.addField(new Field("currConditionId",  String.valueOf(currencyCondition.getId())));
					currencyConditionEntity.addField(new Field("currencyCode",     String.valueOf(currencyCondition.getCurrency().getCode())));
					currencyConditionEntity.addField(new Field("maxPercentRate",   String.valueOf(currencyCondition.getMaxPercentRate())));
					currencyConditionEntity.addField(new Field("minAmount",        String.valueOf(currencyCondition.getMinLimitAmount().getDecimal())));
					currencyConditionEntity.addField(new Field("includeMaxAmount", String.valueOf(currencyCondition.isMaxLimitInclude())));
					currencyConditionEntity.addField(new Field("percentageOfCost", String.valueOf(currencyCondition.isMaxLimitPercentUse())));
					currencyConditionEntity.addField(new Field("maxLimitPercent",  String.valueOf(currencyCondition.getMaxLimitPercent())));
					currencyConditionEntity.addField(new Field("json",             LoanClaimHelper.getJsonLoanField(currencyCondition)));

					Money maxLimitAmount = currencyCondition.getMaxLimitAmount();
					if (maxLimitAmount != null)
					{
						currencyConditionEntity.addField(new Field("maxAmount", String.valueOf(maxLimitAmount.getDecimal())));
					}

					BigDecimal minPercentRate = currencyCondition.getMinPercentRate();
					if (minPercentRate != null)
					{
						currencyConditionEntity.addField(new Field("minPercentRate", String.valueOf(minPercentRate)));
					}

					currencyConditions.addEntity(currencyConditionEntity);
				}

				if (!currencyConditions.isEmpty())
				{
					conditionEntity.addEntityList(currencyConditions);
				}

				entityList.addEntity(conditionEntity);
			}
		}
		catch (BusinessException e)
		{
			log.error("Возникла ошибка при построении справочника loan-claim-loan-conditions.xml", e);
		}

		return entityList;
	}
}
