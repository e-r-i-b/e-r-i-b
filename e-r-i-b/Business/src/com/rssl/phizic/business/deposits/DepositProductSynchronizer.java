package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * DepositProductSynchronizer ��������� �������� ���������� ��������� � ��
 * @author Kidyaev
 * @ created 05.05.2006
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductSynchronizer
{
	private static DepositProductService service = new DepositProductService();

	private List<DepositProduct> descriptions;

    /**
     * @param descriptions - ������ �������� ���������� ��������� 
     */
    public DepositProductSynchronizer(List<DepositProduct> descriptions)
    {
        this.descriptions = descriptions;
    }

    /**
     * �������� �������� ���������� ��������� � ��
     * @throws BusinessException
     */
    public void update() throws BusinessException,DublicateDepositProductNameException
    {
        List<DepositProduct> dbDescriptions = getDbDescriptions();

        for (DepositProduct product : descriptions)
        {
            DepositProduct dbProduct = find(product, dbDescriptions);

            if ( dbProduct == null )
            {
                // �������� � ��
                service.add(product);
            }
            else
            {
                // ��������

	            dbProduct.setDescription(product.getDescription());
	            dbProduct.setDetails(product.getDetails());

                service.update(dbProduct);
            }
        }
    }

    private DepositProduct find(DepositProduct product, List<DepositProduct> products)
    {
        DepositProduct result = null;

        for ( DepositProduct current : products )
        {
            String name        = product.getName();
            String currentName = current.getName();

            if ( name.equals(currentName) )
            {
                result = current;
                break;
            }
        }

        return result;
    }

    private List<DepositProduct> getDbDescriptions() throws BusinessException
    {
        return service.getAllProducts();
    }
}
