package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
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
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductSource implements EntityListSource
{
	private static final ModifiableLoanProductService service = new ModifiableLoanProductService();

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

	    EntityListBuilder builder = new EntityListBuilder();
	    if (StringHelper.isNotEmpty(conditionId))
	    {
	        LoanCondition  loanCondition = service.findLoanConditionById(Long.valueOf(conditionId));
	        ModifiableLoanProduct  product = service.findById(loanCondition.getProductId());

	        builder.openEntityListTag();

	        if (loanCondition != null)
	        {
	            builder.openEntityTag(loanCondition.getId().toString());
	            builder.appentField("name", product.getName());
	            builder.appentField("currency", loanCondition.getCurrency().getCode());
			    builder.appentField("kind", product.getLoanKind().getName());
			    builder.appentField("changeDate", changeDate);
			    builder.appentField("productId", product.getId().toString());
			    builder.closeEntityTag();
	        }

	        builder.closeEntityListTag();
	    }

        return builder;
    }
}
