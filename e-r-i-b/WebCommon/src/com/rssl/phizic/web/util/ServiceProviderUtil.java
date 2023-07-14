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
	 * �������� �� ��������� ��� �������� �������
	 * @param providerId - id ����������
	 * @return - true - ��������
	 */
	public static boolean allowedAnyRegions(long providerId)
	{
		try
		{
			//�������� ������� ������
			Region region = PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
			return region == null ? true : serviceProviderRegionService.providerAllowedInRegion(providerId, region.getId());
		}
		catch (Exception e)
		{
			log.error("�� ������ �������� ������ ��������", e);
			//���� �� ������ �������� ������ ��������, � ������� �������� ���������, �� ������� ��� �� �������� �����
			return true;
		}
	}

	/**
	 * �������� ������� ��������� ����� ���������
	 * @param providerId ������������� ���������
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
	 * ���������� ����� ������ ���������� ��� ������ ������
	 * @param providerId ������������� ����������
	 * @param webRoot �������� ��������
	 * @return url ������
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
	 * @param providerId ������������� ����������
	 * @param webRoot ��������
	 * @return ��� ��������
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
	 * ���������� ��� ���������� �� ��������������
	 * @param providerId �� ����������
	 * @return �������� ����������
	 */
	public static String getProviderName(Long providerId)
	{
		try
		{
			return serviceProviderService.findNameById(providerId, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (BusinessException be)
		{
			log.error("�� ������� �������� ��� ����������", be);
			return null;
		}
	}

	/**
	 * ����������� �� ��������� ����� ������ � ��������� ����.
	 * @param providerId - ������������� ��
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
			log.error("�� ������� �������� ��������� ������ � ��������� ���� ��� ����������" + providerId, e);
			return false;
		}
	}
}
