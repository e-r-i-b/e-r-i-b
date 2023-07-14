package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.profile.images.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Операция загрузки аватара.
 *
 * @author bogdanov
 * @ created 25.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UploadAvatarOperation extends OperationBase
{
	private byte[] imageData;
	private String fileType;
	private ProfileService profileService = new ProfileService();

	private static AvatarInfo getAvatarInfo(AvatarType type) throws BusinessException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("Не установлен контекст клиента");

		return PersonContext.getPersonDataProvider().getPersonData().getAvatarInfoByType(type);
	}

	/**
	 * Существует ли аватар.
	 *
	 * @return существует или нет.
	 * @throws BusinessException
	 */
	public static boolean hasAvatar() throws BusinessException
	{
		return getAvatarInfo(AvatarType.SOURCE) != null;
	}

	/**
	 * Путь к аватару
	 * @param type - тип аватара
	 * @return путь
	 * @throws BusinessException
	 */
	public String getAvatarPath(AvatarType type) throws BusinessException
	{
		AvatarInfo avatarInfo = getAvatarInfo(type);
		if (avatarInfo != null)
			return avatarInfo.getImageInfo().getPath();

		return null;
	}

	private void setAvatarInfo(AvatarInfo info) throws BusinessException, IOException, BusinessLogicException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("Не установлен контекст клиента");

		AvatarInfo last = getAvatarInfo(info.getType());

		AvatarService.get().saveImageInfo(info);
		//Удаляем картинку из памяти, она больше не нужна.
		info.setImage(null);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		personData.setAvatarInfo(info);
		if (info.getType() == AvatarType.SOURCE)
		{
			personData.getProfile().setAvatarImageId(info.getImageInfo().getId());
			profileService.update(personData.getProfile());
			personData.setAvatarInfo(new AvatarInfo(AvatarType.AVATAR, info));
			personData.setAvatarInfo(new AvatarInfo(AvatarType.SMALL, info));
			personData.setAvatarInfo(new AvatarInfo(AvatarType.ICON, info));
		}

		if (last != null && last.getImageInfo() != null)
		{
			AvatarService.get().deleteImageInfo(last, null);
		}

		if (info.getType() == AvatarType.SOURCE)
		{
			AvatarService.get().actualize(info.getImageInfo().getPath());
		}
	}

	/**
	 * Инициализации загрузки ищображения.
	 *
	 * @param imageData данные по изображению.
	 * @param fileType тип файла.
	 */
	public void initialize(byte[] imageData, String fileType)
	{
		this.imageData = imageData;
		this.fileType = fileType;
	}

	/**
	 * Загрузка картинки.
	 * @return загруженная картинка.
	 * @throws BusinessException
	 */
	public NotLoadedImage upload() throws BusinessException, BusinessLogicException
	{
		NotLoadedImage image = UserImageService.get().upload(imageData);
		try
		{
			setAvatarInfo(AvatarService.get().createAvatarInfo(image, AvatarType.TEMP, fileType));
			return image;
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление текущего аватара.
	 * @throws BusinessException
	 */
	public void delete() throws BusinessException
	{
		try
		{
			for (AvatarType type : AvatarType.values())
			{
				if (type == AvatarType.TEMP)
					continue;
				final AvatarInfo info = getAvatarInfo(type);
				if (info != null && type == AvatarType.SOURCE)
				{
					Profile profile = PersonContext.getPersonDataProvider().getPersonData().getProfile();
					profile.setAvatarImageId(null);
					profileService.update(profile);
					AvatarService.get().deleteImageInfo(info, profile.getLoginId());
				}
				PersonContext.getPersonDataProvider().getPersonData().deleteAvatarInfo(type);
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление временного аватара.
	 * @throws BusinessException
	 */
	public void deleteTemp() throws BusinessException
	{
		AvatarInfo info = getAvatarInfo(AvatarType.TEMP);
		try
		{
			if (info != null)
			{
				AvatarService.get().deleteImageInfo(info, null);
				PersonContext.getPersonDataProvider().getPersonData().deleteAvatarInfo(info.getType());
			}
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Установка аватара.
	 *
	 * @param x x начала картинки
	 * @param y y начала картинки.
	 * @param width ширина новой картинки.
	 * @param height высота новой картинки.
	 * @throws BusinessException
	 */
	public void setAvatar(int x, int y, int width, int height) throws BusinessException, BusinessLogicException
	{
		AvatarService avatarService = AvatarService.get();
		AvatarInfo info = getAvatarInfo(AvatarType.TEMP);
		if (info == null)
			return;

		if (Math.abs(width - height) > 2)
			throw new BusinessLogicException("Соотношение сторон изображения должно быть 1:1");

		try
		{
			AvatarInfo source = avatarService.createAvatarInfo(
					avatarService.loadImage(info.getImageInfo().getPath(), info.getType().name(), true), info, AvatarType.SOURCE
			);
			double scale = getImageScale(source.getImage());

			if (width == 0 || height == 0)
			{
				int min = Math.min(source.getImage().getWidth(null), source.getImage().getHeight(null));
				x = (source.getImage().getWidth(null) - min)/2;
				y = (source.getImage().getHeight(null) - min)/2;
				height = min;
				width = min;
				scale = 1;
			}

			avatarService.clip(source, (int) (scale * x), (int) (scale * y), (int) (scale * width), (int) (scale * height));
			deleteTemp();
			setAvatarInfo(source);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param image изображние.
	 * @return масштаб.
	 */
	public static double getImageScale(Image image)
	{
		int max = Math.max(image.getWidth(null), image.getHeight(null));
		if (max > AvatarType.AVATAR.getImageSize())
		{
			return max == image.getWidth(null) ? (image.getWidth(null) + 0.0) / AvatarType.AVATAR.getImageSize() : (image.getHeight(null) + 0.0) / AvatarType.AVATAR.getImageSize();
		}
		else
		{
			return 1.0;
		}
	}
}
