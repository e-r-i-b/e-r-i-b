package com.rssl.phizic.business.profile.images;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.MathHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

/**
 * Сервис по работе с аватарами.
 *
 * @author bogdanov
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class AvatarService extends UserImageService
{
	private static volatile AvatarService self = null;
	private final AvatarJurnalService jurnalService = new AvatarJurnalService();

	/**
	 * @return сервис по работе с аватарами.
	 */
	@SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
	public static AvatarService get()
	{
	    if (self != null)
		    return self;

		synchronized (AvatarService.class)
		{
			if (self != null)
				return self;

			self = new AvatarService();
			return self;
		}
	}

	private AvatarService()
	{
	}

	/**
	 * Создание информации об аватаре.
	 *
	 * @param image картинка.
	 * @param avatarType тип аватара.
	 * @param fileType тип файла.
	 * @return информация об аватаре.
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public AvatarInfo createAvatarInfo(Image image, AvatarType avatarType, String fileType) throws BusinessException, BusinessLogicException
	{
		ImageContainer container = createImage(image, fileType);
		return createAvatarInfo(image, container, avatarType);
	}

	/**
	 * Создание информации об аватаре по ImageInfo.
	 *
	 * @param image картинка.
	 * @param container информация о картинкею.
	 * @param avatarType тип аватара.
	 * @return информация об аватаре.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public AvatarInfo createAvatarInfo(Image image, ImageContainer container, AvatarType avatarType) throws BusinessException, BusinessLogicException
	{
		AvatarInfo avatarInfo = new AvatarInfo(container);
		avatarInfo.setType(avatarType);
		avatarInfo.setImage(image);

		if (avatarType == AvatarType.TEMP)
		{
			int min = Math.min(image.getWidth(null), image.getHeight(null));
			if (ConfigFactory.getConfig(ProfileConfig.class).getMinAvatarShortSize() > min)
				throw new BusinessLogicException("Слишком маленькое изображение");
		}

		return avatarInfo;
	}

	/**
	 * @param type тип аватара.
	 * @param imageId идентификатор записи.
	 * @return информация об аватаре.
	 * @throws BusinessException
	 */
	public AvatarInfo getAvatarInfo(AvatarType type, long imageId) throws BusinessException
	{
		AvatarInfo ainfo = new AvatarInfo();
		ainfo.setType(type);
		ainfo.setImageInfo(UserImageService.get().getImageInfo(imageId));

		if (ConfigFactory.getConfig(UserPropertiesConfig.class).isFirstAvatarShow())
			actualize(ainfo.getImageInfo().getPath());

		return ainfo;
	}

	/**
	 * Актуализация аватаров при первом входе клиента.
	 *
	 * @param avatarPath путь к аватару, если указан null, то аватар удалится.
	 */
	public void actualize(String avatarPath) throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return;

		try
		{
			Collection<String> phones = PersonContext.getPersonDataProvider().getPersonData().getPhones(false);
			long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
			//удалим старые аватары.
			jurnalService.deleteByLoginId(loginId, phones);
			//добавим новые.
			jurnalService.setAvatar(phones, avatarPath, loginId);

			ConfigFactory.getConfig(UserPropertiesConfig.class).setFirstAvatarShow(false);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить информацию об аватаре по логину
	 * @param type - тип аватара
	 * @param loginId - идентификатор логина
	 * @return информацию об аватаре
	 * @throws BusinessException
	 */
	public AvatarInfo getAvatarInfoByLoginId(AvatarType type, long loginId) throws BusinessException
	{
		AvatarInfo ainfo = new AvatarInfo();
		ainfo.setImageInfo(UserImageService.get().getImageInfoByLoginId(loginId));
		ainfo.setType(type);
		return ainfo;
	}

	/**
	 * Сохранение аватара.
	 * Сохраняет 4 различных картинки. (все типы аватра)
	 *
	 * @param info информация об аватаре.
	 */
	public void saveImageInfo(AvatarInfo info) throws IOException, BusinessException, BusinessLogicException
	{
		//Если сохраняем картинку во временное хранилище.
		if (info.getType() == AvatarType.TEMP)
		{
			//сохраняем во временное хранилище.
			saveImage(info, info.getType().name(), true);
			return;
		}

		//Если сохраняем в основное хранилище.
		if (info.getType() != AvatarType.SOURCE)
			throw new BusinessException("У сохраняемого аватара должен быть тип SOURCE");
		//Сохраняем в основное хранилище.
		saveImage(info, info.getType().name(), false);
		//Сохраняем аватарки других размеров.
		for (AvatarType type : AvatarType.values())
		{
			if (type == AvatarType.TEMP || type == AvatarType.SOURCE)
				continue;
			AvatarInfo avatarInfo = createAvatarInfo(
					imageService.resizeImage(info.getImage(), type.getImageSize(), type.getImageSize()),
					info, type
			);
			saveImage(avatarInfo, type.name(), false);
		}
	}

	/**
	 * Удаление аватара.
	 * Удаляет 4 различных картинки. (все типы аватра)
	 *
	 * @param info информация об аватаре.
	 * @param loginId логинид.
	 */
	public void deleteImageInfo(AvatarInfo info, Long loginId) throws IOException, BusinessException
	{
		if (info.getType() == AvatarType.TEMP)
		{
			deleteImageInfo(info.getImageInfo(), info.getType().name(), true);
			return;
		}

		if (info.getType() != AvatarType.SOURCE)
		{
			throw new IllegalArgumentException("Неверный тип аватарки для удаления. Должен быть тип SOURCE или TEMP");
		}

		deleteImageInfo(info.getImageInfo(), info.getType().name(), false);
		for (AvatarType type : AvatarType.values())
		{
			if (type == AvatarType.TEMP || type == info.getType())
				continue;
			deleteImageInfo(info.getImageInfo(), type.name(), false);
		}
		if (loginId != null)
			jurnalService.deleteByLoginId(loginId, Arrays.<String>asList());
	}

	/**
	 * Обрезка изображение.
	 *
	 * @param info информация об аватаре.
	 * @param x x координата верхнего левого угла.
	 * @param y y координата верхнего левого угла.
	 * @param width ширина нового изображения.
	 * @param height высота нового изображения.
	 * @throws BusinessException
	 */
	public void clip(AvatarInfo info, int x, int y, int width, int height) throws BusinessException
	{
		info.setImage(ImageService.get().clipImage(info.getImage(), x, y, width, height));
		checkImage(info);
	}

	/**
	 * Проверка изображения.
	 *
	 * Проверяем изображение на
	 *  - Размер длинной стороны должен быть не больше указанного в настройках.
	 *  - Размер файла после сохранение должен быть не больше указанного в настройках.
	 *
	 * После проверки изображение создается изображение попадающие под ограничения.
	 * Оно записывается в переменную image.
	 *
	 * @param info информация об аватаре.
	 * @throws BusinessException
	 */
	private void checkImage(AvatarInfo info) throws BusinessException
	{
		if (info.getImage() instanceof NotLoadedImage)
			return;

		try
		{
			BufferedImage image = (BufferedImage) info.getImage();
			ProfileConfig config = ConfigFactory.getConfig(ProfileConfig.class);
			BufferedImage img = checkImageSideSize(image, 0);
			long fs0 = imageService.getImageFileSize(img, info.getImageInfo().getPath());
			long fsy = config.getAvatarFileMaxSize() << 10;
			/*
				Алгоритм получения необходимого размера файла:
				1. Считаем размеры фвыходных файлов для стороны x.
				2. Строим через эту точку и начало координат параболу.
				3. Решаем квадратное уравнение: по известному размеру необходимо найти длину строны
				4. Масштабируем изображение до найденой длины стороны.
			 */
			if (fs0 > fsy)
			{
				int ss0 = Math.max(img.getWidth(), img.getHeight());
				double ssx = MathHelper.getApproxValue(ss0, fs0, ss0/2.0, fs0/4.0, 0, 0, fsy);
				info.setImage(checkImageSideSize(img, (int)Math.round(ssx)));
			}
			else
			{
				info.setImage(img);
			}
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private BufferedImage checkImageSideSize(BufferedImage img, int longSideLength)
	{
		int maxSideLength = longSideLength == 0 ? ConfigFactory.getConfig(ProfileConfig.class).getMaxAvatarLongSize() : longSideLength;
		//Если изображение больше допустимого по размеру.
		if (maxSideLength < Math.max(img.getWidth(), img.getHeight()))
		{
			if (img.getWidth() < img.getHeight())
				return imageService.resizeImage(img, ((double)img.getWidth() * maxSideLength) / img.getHeight(), maxSideLength);
			else
				return imageService.resizeImage(img, maxSideLength, ((double)img.getHeight() * maxSideLength) / img.getWidth());
		}
		return img;
	}
}
