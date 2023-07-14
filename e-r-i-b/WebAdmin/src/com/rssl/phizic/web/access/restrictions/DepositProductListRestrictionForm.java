package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.business.deposits.DepositProduct;

import java.util.List;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1379 $
 */
public class DepositProductListRestrictionForm extends RestrictionFormBase
{
    private List<DepositProduct> products;

    public List<DepositProduct> getProducts()
    {
        return products;
    }

    public void setProducts(List<DepositProduct> products)
    {
        this.products = products;
    }

}