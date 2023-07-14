package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.business.skins.SkinConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author niculichev
 * @ created 07.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class ImageUtil
{                                                                    
	private static final ImageService imageService = new ImageService();

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ��������� ����� �����������, � ����������� �� ����, �������� �� ��� � ���� ��� ��� ������
	 * �� ������� ������
	 * @param image ������ �����������
	 * @param pageContext �������� ��������
	 * @return ����� �����������
	 */
	public static String getAddressImage(Image image, PageContext pageContext)
	{
		return getImageURL(image, ((HttpServletRequest)pageContext.getRequest()).getContextPath(), (String)pageContext.getRequest().getAttribute("globalUrl"));
	}

	/**
	 * ����� �������� �� id
	 * @param id ��������
	 * @return ������ � ��, ��������������� ������� id
	 * @throws BusinessException
	 */
	public static Image findById(Long id) throws BusinessException
	{
		return imageService.findById(id, null);
	}

	/**
	 * ����� �������� �� id ��������� ���������������, ������������ ��� �����������
	 * @param id ��������
	 * @return ������ � ��, ��������������� ������� id
	 * @throws BusinessException
	 */
	public static Image findByIdConsiderMultiBlock(Long id) throws BusinessException
	{
		return imageService.findById(id, MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * ��������� ����� �����������, � ����������� �� ����, �������� �� ��� � ���� ��� ��� ������
	 * �� ������� ������
	 * @param image ������ �����������
	 * @param pageContext �������� ��������
	 * @return ����� �����������
	 */
	public static String getAddressImageConsiderMultiBlock(Image image, PageContext pageContext)
	{
		try
		{
			String externalURL = image.getExtendImage();
			if(!StringHelper.isEmpty(externalURL))
			{
				return ConfigFactory.getConfig(SkinConfig.class).getGlobalSkin().getUrl() + externalURL;
			}

			if (image.isHaveImageBlob())
				return ((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/images?id="+image.getId();

			String innerURL = image.getInnerImage();
			if(!StringHelper.isEmpty(innerURL))
			{
				ServletRequest request = pageContext.getRequest();
				String globalUrl = (String) request.getAttribute("globalUrl");
				if (StringHelper.isEmpty(globalUrl))
					globalUrl = SkinHelper.getGlobalSkinUrl();
				return globalUrl + "/commonSkin/images" + innerURL;
			}

			return ((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/images?id="+image.getId();
		}
		catch(Exception e)
		{
			log.error("������ ��������� ������ �� ����������� " + e, e);
			return null;
		}
	}

	/**
	 * ��������� ����� �����������, � ����������� �� ����, �������� �� ��� � ���� ��� ��� ������
	 * �� ������� ������
	 * @param image ������ �����������
	 * @param webRoot �������� ��������
	 * @return ����� �����������
	 */
	public static String getAddressImage(Image image, String webRoot)
	{
		return getImageURL(image, webRoot, null);
	}

	private static String getImageURL(Image image, String webRoot, String globalUrl)
	{
		try
		{
			String externalURL = image.getExtendImage();
			if(!StringHelper.isEmpty(externalURL))
			{
				return ConfigFactory.getConfig(SkinConfig.class).getGlobalSkin().getUrl() + externalURL;
			}

			if (image.isHaveImageBlob())
				return webRoot +"/images?id=" + image.getId();

			String innerURL = image.getInnerImage();
			if(!StringHelper.isEmpty(innerURL))
			{
				if (StringHelper.isEmpty(globalUrl))
					globalUrl = SkinHelper.getGlobalSkinUrl();
				return globalUrl + "/commonSkin/images" + innerURL;
			}

			return webRoot +"/images?id="+image.getId();
		}
		catch(Exception e)
		{
			log.error("������ ��������� ������ �� ����������� " + e, e);
			return null;
		}
	}

	/**
     * �������� �� id ����������� ������ �� ������� ������
     * @param image ������ �����������
     * @return ������ ������ �� "����������" ������� ������
     */
    public static String getFullPathToExtendImage(Image image)
    {
        try
        {
            String externalURL = image.getExtendImage();
            if(!StringHelper.isEmpty(externalURL))
                return SkinHelper.getGlobalUrl() + externalURL;
            else
	            return null;
        }
        catch (Exception e)
        {
            log.error("������ ��������� ������ �� ����������� " + e, e);
			return null;
        }
    }
}
