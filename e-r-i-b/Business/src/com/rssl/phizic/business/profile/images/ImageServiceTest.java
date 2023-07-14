package com.rssl.phizic.business.profile.images;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author bogdanov
 * @ created 27.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ImageServiceTest
{
	public static void main(String[] args) throws IOException, BusinessException, BusinessLogicException
	{
		ImageService imageService = ImageService.get();
		//Загружаем картинку.
		BufferedImage img = imageService.loadImage(new File("D:/DSC_0029.jpg"));

		AvatarService avatarService = AvatarService.get();
		//Создаем временный аватар.
		AvatarInfo info = avatarService.createAvatarInfo(img, AvatarType.TEMP, "jpg");
		//Сохраняем аватар в базе (также сохранится картинки во временной папке)
		avatarService.saveImageInfo(info);

		//Получаем информация по аватару (существуещему, и если она есть удаляем аватар вместе со всеми 4мя картинкаим)
		info = avatarService.getAvatarInfo(AvatarType.SOURCE, info.getImageInfo().getId());
		if (info != null)
			avatarService.deleteImageInfo(info, 1L);

		//Получаем временную картинку.
		AvatarInfo temp = avatarService.getAvatarInfo(AvatarType.TEMP, info.getImageInfo().getId());
		//Создаем аватар инфо и загружаем с диска временную картинку.
		info = avatarService.createAvatarInfo(avatarService.loadImage(temp.getImageInfo().getPath(), temp.getType().name(), true), AvatarType.SOURCE, "jpg");
		//Удаляем ее. Она больше не нужна.
		avatarService.deleteImageInfo(temp, 1L);
		//Обрезаем картинку.
		avatarService.clip(info, 135, 16, 255, 224);
		//Сохраняем 4 картинки аватарок.
		avatarService.saveImageInfo(info);
	}
}
