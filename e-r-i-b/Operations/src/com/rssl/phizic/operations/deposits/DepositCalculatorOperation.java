package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.DepositGlobal;
import com.rssl.phizic.business.deposits.DepositProductListBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.DepositProductRestriction;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

/**
 * Калькулятор депозитных продуктов
 * @author Evgrafov
 * @ created 15.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1322 $
 */

public class DepositCalculatorOperation extends OperationBase<DepositProductRestriction>
{
    private static final DepositProductService depositProductService = new DepositProductService();

    public Source getTemplateSource() throws BusinessException
    {
        DepositGlobal global = depositProductService.getGlobal();
        return new StreamSource( new StringReader(global.getCalculatorTransformation()) );
    }

    public Source getListSource() throws BusinessException
    {
        return new DOMSource(new DepositProductListBuilder().setRestriction(getRestriction()).build());
    }
}
