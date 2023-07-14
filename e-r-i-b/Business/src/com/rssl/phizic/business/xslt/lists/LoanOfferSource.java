package com.rssl.phizic.business.xslt.lists;

import com.rssl.ikfl.crediting.OfferCondition;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Mescheryakova
 * @ created 31.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfferSource implements EntityListSource
{


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
		String strId = params.get("offer");
		if(StringHelper.isEmpty(strId))
		{
			throw new BusinessException("Ќе задано предодобренное кредитное предложение");
		}

	    OfferId offerId = OfferId.fromString(strId);
	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    LoanOffer offer = personData.findLoanOfferById(offerId);

	    EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();

	    if (offer != null)
	    {
	        builder.openEntityTag(offer.getOfferId().toString());
	        builder.appentField("name", offer.getProductName());
	        builder.appentField("amount", offer.getMaxLimit().getDecimal().toString());
	        builder.appentField("currency", offer.getMaxLimit().getCurrency().getCode());
	        builder.appentField("duration", offer.getDuration().toString());
	        builder.appentField("percentRate", offer.getPercentRate().toString());
	        builder.appentField("pasportNumber", offer.getPasportNumber());
	        builder.appentField("pasportSeries", offer.getPasportSeries());


		    buildConditions(builder, offer.getConditions());
		    builder.appentField("tb", offer.getTerbank().toString());
		    builder.closeEntityTag();

	    }
	    
	    builder.closeEntityListTag();

        return builder;
    }

	private void buildConditions(EntityListBuilder builder, List<OfferCondition> conditions) throws BusinessException
	{
		builder.openEntityTag("offer-conditions");
		for (OfferCondition condition : conditions)
		{
			builder.openEntityTag("offer-condition");
			builder.appentField("duration", String.valueOf(condition.getPeriod()));
			builder.appentField("rate", String.valueOf(condition.getRate()));
			builder.appentField("amount", condition.getAmount().toPlainString());
			builder.closeEntityTag();
		}
		builder.closeEntityTag();
	}

}
