package com.rssl.phizic.ant;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.locale.*;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.entities.LocaleState;
import com.rssl.phizic.locale.replicator.EribMessageReplicator;
import com.rssl.phizic.locale.replicator.PropertyDestenation;
import com.rssl.phizic.locale.replicator.SyncMode;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.utils.ArraysHelper;
import com.rssl.phizic.utils.HashHelper;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.apache.tools.ant.types.FileSet;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 24.08.14
 * @ $Author$
 * @ $Revision$
 */
public class LoadDefaultLocale extends SafeTaskBase
{
	private static final MultiInstanceEribLocaleService ERIB_LOCALE_SERVICE = new MultiInstanceEribLocaleService();
	private static final SimpleService SERVICE = new SimpleService();

	private String defaultLocaleName;
	private String bundleName;


	private FileSet strutsFiles = new FileSet();

	/**
	 * @return имя дефолтной локали
	 */
	public String getDefaultLocaleName()
	{
		return defaultLocaleName;
	}

	/**
	 * @param defaultLocaleName имя дефолтной локали
	 */
	public void setDefaultLocaleName(String defaultLocaleName)
	{
		this.defaultLocaleName = defaultLocaleName;
	}

	/**
	 * @return бандл для которого грузим текстовки
	 */
	public String getBundleName()
	{
		return bundleName;
	}

	/**
	 * @param bundleName бандл для которого грузим текстовки
	 */
	public void setBundleName(String bundleName)
	{
		this.bundleName = bundleName;
	}

	@Override
	public void safeExecute() throws Exception
	{

		String localeId = createDefaultLocale();

		new EribMessageReplicator(new PropertySource(strutsFiles, bundleName), new PropertyDestenation(localeId, bundleName, null), SyncMode.FULL_SYNC).replicate();
	}


	private String createDefaultLocale() throws BusinessException
	{
		String defaultLocaleId = ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId();
		ERIBLocale defaultLocale = null;
		try
		{
			defaultLocale = ERIB_LOCALE_SERVICE.getById(defaultLocaleId,null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}

		if(defaultLocale == null)
		{
			//noinspection ReuseOfLocalVariable
			defaultLocale = new ERIBLocale();
			defaultLocale.setId(defaultLocaleId);
			defaultLocale.setName(getDefaultLocaleName());
			defaultLocale.setAvailabilityAll(true);
			defaultLocale.setState(LocaleState.ENABLED);
			defaultLocale.setImageId(getImageId(defaultLocaleId));
			defaultLocale.setActualDate(Calendar.getInstance());
			try
			{
				ERIB_LOCALE_SERVICE.addOrUpdate(defaultLocale, null);
			}
			catch (SystemException e)
			{
				throw new BusinessException(e);
			}
		}
		return defaultLocaleId;
	}

	private Long getImageId(String defaultLocaleId) throws BusinessException
	{
		BuildContextConfig config = ConfigFactory.getConfig(BuildContextConfig.class);
		String version = config.getResourceAdditionalPath();
		Image img = new Image();
		img.setUpdateTime(Calendar.getInstance().getTime());
		img.setExtendImage("/" + version + "/commonSkin/images/locales/"+defaultLocaleId.toLowerCase()+".gif");
		img.setMd5(getImageHash(img, null, null));
		SERVICE.addOrUpdate(img, null);
		return img.getId();
	}

	/**
	 * @param fileset стратсовые файлы
	 */
	public void addFileset(FileSet fileset)
	{
		this.strutsFiles = fileset;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	/**
	 * Возвращает md5 картинки.
	 *
	 * @param image данные о картинке.
	 * @param data тело картинки.
	 * @return ее md5 (в формирование md5 входят параметры extendImage, InnerImage и image).
	 */
	private String getImageHash(Image image, byte[] data, String instanceName) throws BusinessException
	{
		try
		{
			byte[] bts = ArraysHelper.concat(
					ArraysHelper.getBytes(image.getExtendImage()),
					ArraysHelper.getBytes(image.getInnerImage()),
					data
			);

			return HashHelper.hash(bts);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}
}