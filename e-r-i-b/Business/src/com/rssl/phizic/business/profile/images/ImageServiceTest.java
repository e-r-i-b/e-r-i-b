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
		//��������� ��������.
		BufferedImage img = imageService.loadImage(new File("D:/DSC_0029.jpg"));

		AvatarService avatarService = AvatarService.get();
		//������� ��������� ������.
		AvatarInfo info = avatarService.createAvatarInfo(img, AvatarType.TEMP, "jpg");
		//��������� ������ � ���� (����� ���������� �������� �� ��������� �����)
		avatarService.saveImageInfo(info);

		//�������� ���������� �� ������� (�������������, � ���� ��� ���� ������� ������ ������ �� ����� 4�� ����������)
		info = avatarService.getAvatarInfo(AvatarType.SOURCE, info.getImageInfo().getId());
		if (info != null)
			avatarService.deleteImageInfo(info, 1L);

		//�������� ��������� ��������.
		AvatarInfo temp = avatarService.getAvatarInfo(AvatarType.TEMP, info.getImageInfo().getId());
		//������� ������ ���� � ��������� � ����� ��������� ��������.
		info = avatarService.createAvatarInfo(avatarService.loadImage(temp.getImageInfo().getPath(), temp.getType().name(), true), AvatarType.SOURCE, "jpg");
		//������� ��. ��� ������ �� �����.
		avatarService.deleteImageInfo(temp, 1L);
		//�������� ��������.
		avatarService.clip(info, 135, 16, 255, 224);
		//��������� 4 �������� ��������.
		avatarService.saveImageInfo(info);
	}
}
