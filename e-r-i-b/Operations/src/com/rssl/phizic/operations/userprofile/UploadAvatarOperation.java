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
 * �������� �������� �������.
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
			throw new BusinessException("�� ���������� �������� �������");

		return PersonContext.getPersonDataProvider().getPersonData().getAvatarInfoByType(type);
	}

	/**
	 * ���������� �� ������.
	 *
	 * @return ���������� ��� ���.
	 * @throws BusinessException
	 */
	public static boolean hasAvatar() throws BusinessException
	{
		return getAvatarInfo(AvatarType.SOURCE) != null;
	}

	/**
	 * ���� � �������
	 * @param type - ��� �������
	 * @return ����
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
			throw new BusinessException("�� ���������� �������� �������");

		AvatarInfo last = getAvatarInfo(info.getType());

		AvatarService.get().saveImageInfo(info);
		//������� �������� �� ������, ��� ������ �� �����.
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
	 * ������������� �������� �����������.
	 *
	 * @param imageData ������ �� �����������.
	 * @param fileType ��� �����.
	 */
	public void initialize(byte[] imageData, String fileType)
	{
		this.imageData = imageData;
		this.fileType = fileType;
	}

	/**
	 * �������� ��������.
	 * @return ����������� ��������.
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
	 * �������� �������� �������.
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
	 * �������� ���������� �������.
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
	 * ��������� �������.
	 *
	 * @param x x ������ ��������
	 * @param y y ������ ��������.
	 * @param width ������ ����� ��������.
	 * @param height ������ ����� ��������.
	 * @throws BusinessException
	 */
	public void setAvatar(int x, int y, int width, int height) throws BusinessException, BusinessLogicException
	{
		AvatarService avatarService = AvatarService.get();
		AvatarInfo info = getAvatarInfo(AvatarType.TEMP);
		if (info == null)
			return;

		if (Math.abs(width - height) > 2)
			throw new BusinessLogicException("����������� ������ ����������� ������ ���� 1:1");

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
	 * @param image ����������.
	 * @return �������.
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
