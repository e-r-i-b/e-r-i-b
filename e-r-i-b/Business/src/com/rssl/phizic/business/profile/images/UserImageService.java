package com.rssl.phizic.business.profile.images;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import java.awt.Image;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.UserImageUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Сервис для работы с пользовательскими картинками.
 *
 * @author bogdanov
 * @ created 18.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserImageService
{
	private static volatile UserImageService self = null;
	private final SimpleService simpleService = new SimpleService();
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);
	protected static final ImageService imageService = ImageService.get();
	private Cache userImageInfoCache;

	/**
	 * @return сервис по работе с пользовательскими изображениями.
	 */
	public static UserImageService get()
	{
	    if (self != null)
		    return self;

		synchronized (UserImageService.class)
		{
			if (self != null)
				return self;

			self = new UserImageService();
			return self;
		}
	}

	/**
	 * Создание сервиса.
	 */
	protected UserImageService()
	{
		userImageInfoCache = CacheProvider.getCache("UserImageInfo");
	}

	/**
	 * Вовзвращает uuid картинки.
	 *
	 * @return ее uuid.
	 */
	private String generateUuid()
	{
		RandomGUID randomGUID = new RandomGUID(true);
		return randomGUID.getStringValue();
	}

	/**
	 * Построение маршрута.
	 *
	 * Формат: /xx/xx/xx/xx/xxxxxxxxxxxxxxxxxxxxxxxx_99999.xxx
	 * где 99999 - текущий день*текущий час*текущая минута.
	 *
	 * @return маршрут.
	 */
	private String generatePath(String fileType)
	{
		String uuid = generateUuid();
		int numOfNestedFolders = ConfigFactory.getConfig(ProfileConfig.class).getNumOfNestedFolder();
		StringBuilder sb = new StringBuilder(uuid.length() + numOfNestedFolders + fileType.length());
		sb.append("/");
		int pos = 0;
		int numOfSymbolForCatalogName = 2;
		for (int i = 0; i < numOfNestedFolders; i++)
		{
			sb.append(uuid.substring(pos, pos + numOfSymbolForCatalogName)).append("/");
			pos += numOfSymbolForCatalogName;
		}
		sb.append(uuid.substring(pos)).append("_").append(generateDate()).append(".");
		if (fileType.equals("gif"))
			sb.append("png");
		else
			sb.append(fileType);
		return sb.toString();
	}

	private static int generateDate()
	{
		Calendar c = Calendar.getInstance();
		//noinspection MagicNumber
		return (c.get(Calendar.DAY_OF_MONTH) * 24 + c.get(Calendar.HOUR_OF_DAY)) * 60 + c.get(Calendar.MINUTE);
	}

	/**
	 * @param path путь к картинке.
	 * @return true - если файл уже точно должен быть перенесен в стат ресурсы (ДМЗ).
	 */
	public static boolean isMustMovedToStat(String path)
	{
		int date = Integer.parseInt(path.substring(path.lastIndexOf("_") + 1, path.lastIndexOf(".")));
		int curDate = generateDate();
		return date + ConfigFactory.getConfig(ProfileConfig.class).getLocalImageTimeMoved() < curDate || curDate < date;
	}

	/**
	 * Создание информации об изображении.
	 *
	 * @param image картинка.
	 * @param fileType тип файла.
	 * @return информация об изображении.
	 * @throws BusinessException
	 */
	public ImageContainer createImage(Image image, String fileType) throws BusinessException
	{
		ImageContainer container = new ImageContainer();
		ImageInfo info = new ImageInfo();
		info.setPath(generatePath(fileType));
		container.setImage(image);
		container.setImageInfo(info);

		return container;
	}

	/**
	 * Сохранение изображения.
	 *
	 * @param container информация о картинке.
	 * @param imageSelector Идентификатор изображения.
	 * @param temp временная папка или нет.
	 * @throws BusinessException
	 */
	public void saveImage(ImageContainer container, String imageSelector, boolean temp) throws BusinessException
	{
		//Изображение необходимо сохранить во все хранилища, указанные в настройках.
		for (File file : getImageFiles(container.getImageInfo().getPath(), imageSelector, temp))
		{
			try
			{
				file = createDir(file);
				imageService.saveImage(container.getImage(), file);
			}
			catch (IOException e)
			{
				deleteImageInfo(container.getImageInfo(), imageSelector, temp);
				throw new BusinessException("Ошибка при сохранении изображения в файл: " + file.getAbsolutePath(), e);
			}
		}

		if (!temp)
			addOrUpdate(container.getImageInfo());
	}

	private File createDir(File file) throws IOException
	{
		File dir = file.getParentFile();
		if (dir == null)
			throw new IOException("Невозможно определить родительский каталог для " + file.getAbsolutePath());
		LinkedList<File> files = new LinkedList<File>();
		while (dir != null && !dir.exists())
		{
			files.addFirst(dir);
			dir = dir.getParentFile();
		}

		for (File f : files)
		{
			if (!f.mkdir())
				throw new IOException("Невозможно создать папку: " + f.getAbsolutePath());
		}

		return file;
	}

	/**
	 * Получение информации об изображении.
	 * @param id изображения.
	 * @return информация об ищображении.
	 * @throws BusinessException
	 */
	public ImageInfo getImageInfo(final long id) throws BusinessException
	{
		Element element = userImageInfoCache.get(id);
		if (element != null)
			return (ImageInfo) element.getObjectValue();

		try
		{
			ImageInfo info= simpleService.findById(ImageInfo.class, id);
			if (info != null)
				userImageInfoCache.put(new Element(info.getId(), info));
			return info;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение информации об изображении по логину клиента
	 * @param loginId - идентификатор логина
	 * @return информацию об изображении
	 * @throws BusinessException
	 */
	public ImageInfo getImageInfoByLoginId(final long loginId) throws BusinessException
	{
		try
		{
			ImageInfo info = HibernateExecutor.getInstance(null).execute(new HibernateAction<ImageInfo>()
			{
				public ImageInfo run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.profile.images.getImageInfoByLoginId");
					query.setParameter("loginId", loginId);

					return (ImageInfo) query.uniqueResult();
				}
			});

			if (info != null)
				userImageInfoCache.put(new Element(info.getId(), info));
			return info;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаляем изображение.
	 *
	 * @param imageInfo информация об изображении.
	 * @param imageSelector селектор.
	 * @param temp временная папка или нет.
	 * @throws BusinessException
	 */
	public void deleteImageInfo(ImageInfo imageInfo, String imageSelector, boolean temp) throws BusinessException
	{
		//Удаляем изображение.
		for (File file : getImageFiles(imageInfo.getPath(), imageSelector, temp))
		{
			try
			{
				if (!file.exists() && !temp)
				{
					//Если файла не существует, значит он находится в статических ресурсах.
					//Для удаления файла из стат ресурсов необходимо создать файл определенного размера.
					createMinSizeFile(file);
				}
				else if (file.exists())
				{
					//Просто удаляем его.
					if (!file.delete())
					{
						if (PersonContext.isAvailable())
							PersonContext.getPersonDataProvider().getPersonData().markDeleteFile(file);
					}
				}
				else
					log.warn("Не найден файл для удаления: " + file.getAbsolutePath());
			}
			catch (IOException e)
			{
				log.error("Ошибка при удалении изображения: " + file.getAbsolutePath(), e);
			}
		}

		if (!temp)
			remove(imageInfo);
	}

	private void createMinSizeFile(File file) throws IOException
	{
		try
		{
			if (!file.createNewFile())
			{
				log.warn("Невозможно создать файл: " + file.getAbsolutePath());
				return;
			}
		}
		catch (IOException e)
		{
			log.error("Ошибка при удалении изображения: " + file.getAbsolutePath(), e);
			return;
		}

		FileOutputStream fos = new FileOutputStream(file);
		try
		{
			for (long i = 0; i < ConfigFactory.getConfig(ProfileConfig.class).getDeleteImageFileSize(); i++)
				fos.write(0);
			fos.flush();
		}
		catch (IOException e)
		{
			log.error("Ошибка при удалении изображения: " + file.getAbsolutePath(), e);
		}
		finally
		{
			fos.close();
		}
	}

	/**
	 * Возвращает полный путь к картинке.
	 *
	 * @param fileName имя файла.
	 * @param imageSelector префикс для различия картинок. Добавляется к файлу перед путем к картинке. Не может быть больше 10-ми символов.
	 * @param temp временная картинка или нет.
	 * @return полный путь к картинке, или null если картинки не существует.
	 */
	public List<File> getImageFiles(String fileName, String imageSelector, boolean temp) throws BusinessException
	{
		boolean hasSelector = !StringHelper.isEmpty(imageSelector);
		if (hasSelector && imageSelector.length() > 10)
			throw new BusinessException("Слишком большая длина селектора");

		List<String> files = new LinkedList<String>();
		if (temp)
		{
			files.add(ConfigFactory.getConfig(ProfileConfig.class).getTempImageSavePath());
		}
		else
		{
			files.addAll(ConfigFactory.getConfig(ProfileConfig.class).getImageSavePaths());
		}

		List<File> fls = new LinkedList<File>();
		String suffix = (hasSelector ? "/" + imageSelector : "") + fileName;
		if (temp)
			suffix = suffix.replaceAll("/", "");
		for (String prefix : files)
		{
			fls.add(new File(prefix, suffix));
		}
		return fls;
	}

	/**
	 * Загрузка изображения.
	 *
	 * @param fileName путь к картинке.
	 * @param imageSelector выборщик картинки.
	 * @param temp временная.
	 * @return загруженное изображение.
	 */
	public BufferedImage loadImage(String fileName, String imageSelector, boolean temp) throws BusinessException, BusinessLogicException
	{
		for(File file: getImageFiles(fileName, imageSelector, temp))
		{
			try
			{
				if (file.exists())
					return imageService.loadImage(file);
			}
			catch (IOException e)
			{
				log.error("Ошибка при загрузке изображения: " + file.getAbsolutePath(), e);
			}
		}

		return null;
	}

	protected void addOrUpdate(ImageInfo info) throws BusinessException
	{
		if (info.getId() != null)
		{
			Element el = userImageInfoCache.get(info.getId());
			if (el.getObjectValue() != null)
			{
				ImageInfo inf = (ImageInfo) el.getObjectValue();
				if (inf.getPath().equals(info.getPath()))
					return;
			}
		}

		simpleService.addOrUpdate(info);
		userImageInfoCache.put(new Element(info.getId(), info));
	}

	protected void remove(ImageInfo info) throws BusinessException
	{
		if (info.getId() == null)
			return;

		simpleService.remove(info);
		userImageInfoCache.remove(info.getId());
	}

	/**
	 * Загрузка картинки.
	 * @param imageData данные по загруженному изображению.
	 * @return загруженная картинка
	 * @throws BusinessLogicException сообщение об ошибке загрузки изображения.
	 */
	public NotLoadedImage upload(byte[] imageData) throws BusinessException, BusinessLogicException
	{
		if (imageData == null || imageData.length == 0)
			 throw new BusinessException("Нет данных для выгрузки");

		//noinspection AssignmentToMethodParameter
		imageData = UserImageUtils.decodeImage(imageData);
		if (imageData.length > ConfigFactory.getConfig(ProfileConfig.class).getAvatarFileMaxSize() << 10)
			throw new BusinessLogicException("Превышен допустимый размер файла");

		try
		{
			NotLoadedImage image = imageService.loadImage(new ByteArrayInputStream(imageData), ConfigFactory.getConfig(ProfileConfig.class).getMaxLoadedImageLongSideSize());

			if (image == null)
				throw new BusinessLogicException("Неверный формат файла");

			return image;
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}
}
