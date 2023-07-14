package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderRegionService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author gladishev
 * @ created 27.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderUtil
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	private static final ServiceProviderRegionService serviceProviderRegionService = new ServiceProviderRegionService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();;

	/**
	 * доступен ли поставщик для текущего региона
	 * @param providerId - id поставщика
	 * @return - true - доступен
	 */
	public static boolean allowedAnyRegions(long providerId)
	{
		try
		{
			//получаем текущий регион
			Region region = PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
			return region == null ? true : serviceProviderRegionService.providerAllowedInRegion(providerId, region.getId());
		}
		catch (Exception e)
		{
			log.error("Не смогли получить список регионов", e);
			//если не смогли получить список регионов, в которых доступен поставщик, то считаем что он доступен везде
			return true;
		}
	}

	/**
	 * Получить регионы постащика ввиде документа
	 * @param providerId идентификатор постащика
	 * @return Document
	 * @throws BusinessException
	 */
	public static Document getProviderRegions(long providerId) throws BusinessException
	{
		try
		{
			EntityList entityList = new EntityList();
			List<Region> regions = serviceProviderRegionService.getProviderRegions(providerId);
			if(CollectionUtils.isEmpty(regions))
				return XmlHelper.parse(entityList.toString());

			for(Region region : regions)
			{
				Entity entity = new Entity(region.getId().toString(), null);
				entity.addField(new Field("name", region.getName()));
				entity.addField(new Field("code", region.getSynchKey().toString()));
				entity.addField(new Field("codeTB", region.getCodeTB()));
				entityList.addEntity(entity);
			}

			return XmlHelper.parse(entityList.toString());
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

	/**
	 * Возвращает адрес иконки поставщика или пустую строку
	 * @param providerId идентификатор поставщика
	 * @param webRoot контекст страницы
	 * @return url иконки
	 * @throws BusinessException
	 */
	public static String getProviderIconUrl(String providerId, String webRoot) throws BusinessException
	{
		if (StringHelper.isEmpty(providerId))
			return "";
		long id = Long.valueOf(providerId);
		Image icon = BasketHelper.getProviderIcon(id);
		if (icon == null)
		{
			return "";
		}
		return ImageUtil.getAddressImage(icon, webRoot);
	}

	/**
	 * @param providerId идентификатор поставщика
	 * @param webRoot контекст
	 * @return урл картинки
	 * @throws BusinessException
	 */
	public static String getImageHelpUrl(long providerId, String webRoot) throws BusinessException
	{

		Long imageHelpId = serviceProviderService.findImageHelpById(providerId);
		if (imageHelpId == null)
			return "";
		Image image = ImageUtil.findById(imageHelpId);
		return ImageUtil.getAddressImage(image, webRoot);
	}

	/**
	 * Возвращает имя поставщика по идентификатору
	 * @param providerId ид поставщика
	 * @return название поставщика
	 */
	public static String getProviderName(Long providerId)
	{
		try
		{
			return serviceProviderService.findNameById(providerId, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (BusinessException be)
		{
			log.error("Не удалось получить имя поставщика", be);
			return null;
		}
	}

	/**
	 * Подерживает ли поставщик услуг оплату с кредитных карт.
	 * @param providerId - идентификатор ПУ
	 * @return true/false
	 */
	public static boolean isCreditCardSupported(long providerId)
	{
		try
		{
			return serviceProviderService.findCreditCardSupportedById(providerId);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось получить настройку оплаты с кредитных карт для поставщика" + providerId, e);
			return false;
		}
	}
}
