package com.rssl.phizic.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 05.04.2012
 * @ $Author$
 * @ $Revision$
 * ������� �������� ��� ���������� �����������
 */
public abstract class SaveImageOperationBase<R extends Restriction> extends OperationBase<R>
{
	private static final ImageService imageService = new ImageService();
	private Map<String, Image> images = new HashMap<String, Image>();
	private Map<String, byte[]> imagesData = new HashMap<String, byte[]>();

	/**
	 * ������ �����������
	 * @param imageId - ���������� �����
	 * @throws BusinessException
	 */
	public void setImage(Long imageId) throws BusinessException
	{
		addImage(StringUtils.EMPTY, imageId);
	}

	/**
	 * �������� �����������
	 * @param imageId - ������������� ��� ��������� �� �����
	 * @param imageEntityId - ������������� � ��
	 * @throws BusinessException
	 */
	public void addImage(String imageId, Long imageEntityId) throws BusinessException
	{
		Image image;
		byte[] imageData;
		if (imageEntityId == null)
		{	image = new Image();
			imageData = new byte[0];
		}
		else
		{
			image = imageService.findByIdForUpdate(imageEntityId, getInstanceName());
			imageData = imageService.loadImageData(image, getInstanceName());
		}

		if (image == null)
			throw new ResourceNotFoundBusinessException("�������� � ��������������� " + imageId + " �� �������������������.", Image.class);

		images.put(imageId, image);
		imagesData.put(imageId, imageData);
	}

	/**
	 * �������� �����������
	 * @param imageId - ������������� ��� ��������� �� �����
	 * @param isUpdatedImageData - ��������� �� ���������� ���� Image.image (�������� ���� ��� ������� ���)
	 * @param imageName - ��� ��������
	 * @param imageData - ���������� �����
	 */
	public void updateDiskImage(String imageId, boolean isUpdatedImageData, String imageName, byte[] imageData)
	{
		updateImage(imageId, isUpdatedImageData, imageName, imageData, null);
	}

	/**
	 * �������� ������ �� ������� �����������
	 * @param imageId - ������������� ��� ��������� �� �����
	 * @param imageLink - ������
	 */
	public void updateExternalImage(String imageId, String imageLink)
	{
		updateImage(imageId, true, null, null, imageLink);
	}

	private void updateImage(String imageId, boolean isUpdatedImageData, String imageName, byte[] imageData, String imageLink)
	{
		Image image = images.get(imageId);
		if (image == null)
			return;

		image.setExtendImage(imageLink);
		if (!isUpdatedImageData)
			return;

		image.setHaveImageBlob(ArrayUtils.isNotEmpty(imageData));
		image.setName(imageName);
		imagesData.put(imageId, imageData);
	}

	/**
	 * @param imageId ������������� ��� ��������� �� �����
	 * @return ��������
	 */
	public Image getImage(String imageId) throws BusinessException 
	{
		Image image = images.get(imageId);
		if (image == null)
			throw new ResourceNotFoundBusinessException("�������� � ��������������� " + imageId + " �� �������������������.", Image.class);

		return image;
	}

	/**
	 * ��������� ��������
	 * @return ������������� ��������
	 * @throws BusinessException
	 */
	public Long saveImage() throws BusinessException
	{
		Image image = saveImage(StringUtils.EMPTY);
		return image != null ? image.getId() : null;
	}
	/**
	 * ��������� ��������
	 * @param imageId ������������� ��� ��������� �� �����
	 * @return ��������
	 * @throws BusinessException
	 */
	public Image saveImage(String imageId) throws BusinessException
	{
		Image image = images.get(imageId);
		//���� ����� �������� ������, �� �� ���������.
		if (image == null)
			throw new ResourceNotFoundBusinessException("�������� � ��������������� " + imageId + " �� �������������������.", Image.class);

		if (image.getId() == null && image.isEmpty())
			return null;

		//���� ����������� �������� ������, �� ������� ��.
		if (image.isEmpty())
		{
			imageService.remove(image, getInstanceName());
			return null;
		}

		//�������� �� �����, ���������
		if (image.isHaveImageBlob())
			image = imageService.addOrUpdateImageAndImageData(image, imagesData.get(imageId), getInstanceName());
		else
			image = imageService.addOrUpdate(image, getInstanceName());
		return image;
	}

	/**
	 * ������� �������� �� ��
	 * @throws BusinessException
	 */
	public void removeImage() throws BusinessException
	{
		removeImage(StringUtils.EMPTY);
	}

	/**
	 * ������� �������� �� ��
	 * @param imageId ������������� ��� ��������� �� �����
	 * @throws BusinessException
	 */
	public void removeImage(String imageId) throws BusinessException
	{
		Image image = images.get(imageId);
		if (image != null && image.getId() != null)
			imageService.remove(image, getInstanceName());
	}
}
