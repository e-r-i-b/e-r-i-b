package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.DepositProductRestriction;
import com.rssl.phizic.business.operations.restrictions.defaults.PersonDepartmentDepositProductRestriction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author Evgrafov
 * @ created 15.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1322 $
 */

public class DepositProductListBuilder
{
    private static final DepositProductService depositProductService = new DepositProductService();
	private DepositProductRestriction restriction = PersonDepartmentDepositProductRestriction.INSTANCE;

	private String instanceName = null; // ������� ��, � ������� �������� ������

	/**
	 * ������ �����������
	 */
	public DepositProductListBuilder()
	{
	}

	/**
	 * �����������
	 * @param restriction - ���������
	 * @param instanceName - ������� ��, � ������� �������� ������
	 */
	public DepositProductListBuilder(DepositProductRestriction restriction, String instanceName)
	{
		this.restriction = restriction;
		this.instanceName = instanceName;
	}

	public DepositProductRestriction getRestriction()
	{
		return restriction;
	}

	public DepositProductListBuilder setRestriction(DepositProductRestriction restriction)
	{
		this.restriction = restriction;
		return this;
	}

	/**
	 * ��������� �������� �� ������� ���� ���������� ���������
	 * @return �������� DOM
	 */
	public Document build() throws BusinessException
	{
		return build(depositProductService.getAllProducts(instanceName));
	}

	/**
	 * ��������� �������� �� ������� ���������� ���������, �������� ������� �������� nameFilter
	 * @param nameFilter - �������� (����� ��������) ������
	 * @return �������� DOM
	 */
	public Document build(String nameFilter) throws BusinessException
	{
		if (StringHelper.isEmpty(nameFilter))
			return build();
		return build(depositProductService.getProductsByName(nameFilter,instanceName));
	}

	/**
	 * ��������� �������� �� ������� ���� ���������� ���������, ������� ��������� ��� �������� ������
	 * @param tb �������
	 * @return �������� DOM
	 */
	public Document buildOnlineAvailable(String tb) throws BusinessException
	{
		return build(depositProductService.getOnlineAvailableList(tb));
	}

	private Document build(List<DepositProduct> products) throws BusinessException
    {
        try
        {
            DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
            Document listDoc = documentBuilder.newDocument();
            Element listRoot = listDoc.createElement("products");
            listDoc.appendChild(listRoot);

            for (DepositProduct product : products)
            {
	            if(!restriction.accept(product))
	                continue;

                Document productDoc = documentBuilder.parse(new InputSource(new StringReader(product.getDescription())));
                Element productRoot = (Element) listDoc.importNode(productDoc.getDocumentElement(), true);
                productRoot.setAttribute("id", String.valueOf(product.getId()));
	            productRoot.setAttribute("departmentId", String.valueOf(product.getDepartmentId()));
	            productRoot.setAttribute("available", String.valueOf(product.isAvailableOnline()));
                listRoot.appendChild(productRoot);
            }

            return listDoc;
        }
        catch (IOException e)
        {
            throw new BusinessException(e);
        }
        catch (SAXException e)
        {
            throw new BusinessException(e);
        }
    }
}
