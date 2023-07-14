package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositGlobal;
import com.rssl.phizic.business.deposits.DepositProductListBuilder;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.operations.restrictions.defaults.NullDepositProductRestriction;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.StringReader;
import java.util.*;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Evgrafov
 * @ created 05.04.2007
 * @ $Author: egorovaav $
 * @ $Revision: 61369 $
 */

public class GetDepositProductsListOperationBase extends OperationBase implements ListEntitiesOperation
{
	private static final NullDepositProductRestriction NULL_DEPOSIT_PRODUCT_RESTRICTION = new NullDepositProductRestriction();

	protected static final DepositProductService depositProductService = new DepositProductService();

	public Source getTemplateSource() throws BusinessException
	{
		DepositGlobal global = depositProductService.getGlobal();
		return new StreamSource(new StringReader(global.getListTransformation()));
	}

	public Source getMobileTemplateSource() throws BusinessException
	{
		DepositGlobal global = depositProductService.getGlobal();
		return new StreamSource(new StringReader(global.getMobileListTransformation()));
	}

	public Source getListSource(String departmentId) throws BusinessException
	{
		DepositProductListBuilder builder = new DepositProductListBuilder();
		builder.setRestriction(NULL_DEPOSIT_PRODUCT_RESTRICTION);
        return new DOMSource(builder.build());
	}

	/**
	 * Получить сорс документа со списком продуктов
	 * @param needFilterAvailable нужно ли фильтровать доступные для открытия онлайн виды депозитов при построении списка
	 * @param tb Тербанк. Игнорируется, если не нужно фильтровать по доступности.
	 * @return Source
	 */
	public Source getListSource(boolean needFilterAvailable, String tb) throws BusinessException
	{
		DepositProductListBuilder builder = new DepositProductListBuilder(NULL_DEPOSIT_PRODUCT_RESTRICTION, getInstanceName());
		Document list = needFilterAvailable ? builder.buildOnlineAvailable(tb) : builder.build();
		NodeList products = null;
		try
		{
			products = XmlHelper.selectNodeList(list.getDocumentElement(), "//product");
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		return products.getLength() == 0 ? null : new DOMSource(list);
	}

}
