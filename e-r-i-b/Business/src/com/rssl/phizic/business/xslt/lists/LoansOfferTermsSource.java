package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.AgreeOnProcessPersonData;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.utils.xml.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

/**
 * @author Balovtsev
 * @since 16.01.14
 */
public class LoansOfferTermsSource implements EntityListSource
{
    public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
    {
        EntityListBuilder builder = getEntityListBuilder();
        return new StreamSource(new StringReader(builder.toString()));
    }

    public Document getDocument(Map<String, String> params) throws BusinessException
    {
        EntityListBuilder builder = getEntityListBuilder();
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

    private EntityListBuilder getEntityListBuilder() throws BusinessException
    {

        EntityListBuilder builder = new EntityListBuilder();
        builder.openEntityListTag();
        LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);

        AgreeOnProcessPersonData terms = config.getLatestLoanOffersTerm();
        if (terms != null)
        {
            builder.openEntityTag("termEntity");
            builder.appentField("term", terms.getConditionsText());
            builder.closeEntityTag();
        }

        builder.closeEntityListTag();
        return builder;
    }
}
