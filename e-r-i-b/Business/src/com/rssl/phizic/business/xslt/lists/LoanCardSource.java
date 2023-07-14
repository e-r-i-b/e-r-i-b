package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Mescheryakova
 * @ created 23.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardSource implements EntityListSource
{
	private static final CreditCardProductService service = new CreditCardProductService();

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		try
		{
			return XmlHelper.parse(builder.toString());
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
	private EntityListBuilder getEntityListBuilder( final Map<String,String> params ) throws BusinessException
    {
		String conditionId = params.get("loan");
	    String changeDate = params.get("changeDate");
		if(StringHelper.isEmpty(conditionId))
		{
			throw new BusinessException("Не задана кредитная карта");
		}

	    Long condition = Long.valueOf(conditionId);
	    CreditCardProduct cardProduct = service.findCreditCardProductByCreditCardConditionId(condition);
	    CreditCardCondition creditCardCondition =  service.findCreditCardConditionById(condition);

	    EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();

	    if (cardProduct != null)
	    {
	        builder.openEntityTag(cardProduct.getId().toString());
	        builder.appentField("name", cardProduct.getName());
		    builder.appentField("cardProductId", cardProduct.getId().toString());
	        builder.appentField("interestRate", creditCardCondition.getInterestRate().toString());
		    builder.appentField("firstYearPaymentDecimal", creditCardCondition.getFirstYearPayment().getDecimal().toString());
		    builder.appentField("firstYearPaymentCurrency", creditCardCondition.getFirstYearPayment().getCurrency().getCode());
		    builder.appentField("nextYearPaymentDecimal", creditCardCondition.getNextYearPayment().getDecimal().toString());
		    builder.appentField("nextYearPaymentCurrency", creditCardCondition.getNextYearPayment().getCurrency().getCode());
		    builder.appentField("currency", creditCardCondition.getCurrency().getCode());
		    builder.appentField("min-limit", creditCardCondition.getMinCreditLimit().getValue().getDecimal().toString());
		    builder.appentField("max-limit", creditCardCondition.getMaxCreditLimit().getValue().getDecimal().toString());
		    builder.appentField("max-limit-include", creditCardCondition.getMaxCreditLimitInclude().toString());
		    builder.appentField("additionalTerms", cardProduct.getAdditionalTerms());
		    builder.appentField("grace-period-duration", cardProduct.getGracePeriodDuration() == null ? null : cardProduct.getGracePeriodDuration().toString());
		    builder.appentField("grace-period-interest-rate",  cardProduct.getGracePeriodInterestRate() == null ? null : cardProduct.getGracePeriodInterestRate().toString());
		    builder.appentField("changeDate", changeDate);
		    builder.closeEntityTag();
	    }

	    builder.closeEntityListTag();

        return builder;
    }
}
