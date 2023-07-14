package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.cardAmountStep.CardAmountStepComparator;
import com.rssl.phizic.business.cardAmountStep.CardAmountStepService;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Mescheryakova
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardAmountStepSource implements EntityListSource
{
	private static final CardAmountStepService serviceCardAmountStep = new CardAmountStepService();
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

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
		if(StringHelper.isEmpty(conditionId))
			throw new BusinessException("Не задана кредитная карта");

	    Long condition = Long.valueOf(conditionId);
	    CreditCardCondition cardCondition = creditCardProductService.findCreditCardConditionById(condition);

	    if (cardCondition == null)
	        throw new BusinessException("Не удалось получить условия по кредитной для построения списка доступных лимитов");

	    String maxCreditCardLimit = params.get("amount");
	    List<CardAmountStep> cardAmountSteps = null;
	    if(StringHelper.isEmpty(maxCreditCardLimit))
	        cardAmountSteps = serviceCardAmountStep.getRangeLimit(cardCondition.getMinCreditLimit().getValue(), cardCondition.getMaxCreditLimit().getValue(), cardCondition.isMaxCreditLimitInclude());
	    else
	        cardAmountSteps = serviceCardAmountStep.getRangeLimit(cardCondition.getMinCreditLimit().getValue(), new BigDecimal(maxCreditCardLimit), true);

	    EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();

	    Collections.sort(cardAmountSteps, new CardAmountStepComparator());
	    Collections.reverse(cardAmountSteps);

	    for (CardAmountStep cardAmountStep : cardAmountSteps)
	    {
		    builder.openEntityTag(cardAmountStep.getId().toString());
	        builder.appentField("decimal", cardAmountStep.getValue().getDecimal().toBigInteger().toString());
	        builder.appentField("currency", cardAmountStep.getValue().getCurrency().getCode());
		    builder.closeEntityTag();
	    }

	    builder.closeEntityListTag();

        return builder;
    }
}