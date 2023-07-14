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
 * Базовая операция для сохранения изображений
 */
public abstract class SaveImageOperationBase<R extends Restriction> extends OperationBase<R>
{
	private static final ImageService imageService = new ImageService();
	private Map<String, Image> images = new HashMap<String, Image>();
	private Map<String, byte[]> imagesData = new HashMap<String, byte[]>();

	/**
	 * Задать пиктограмму
	 * @param imageId - содержимое файла
	 * @throws BusinessException
	 */
	public void setImage(Long imageId) throws BusinessException
	{
		addImage(StringUtils.EMPTY, imageId);
	}

	/**
	 * Добавить пиктограмму
	 * @param imageId - идентификатор для обработки во вьюве
	 * @param imageEntityId - идентификатор в БД
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
			throw new ResourceNotFoundBusinessException("Картинка с идентификатором " + imageId + " не проинициализирована.", Image.class);

		images.put(imageId, image);
		imagesData.put(imageId, imageData);
	}

	/**
	 * Добавить пиктограмму
	 * @param imageId - идентификатор для обработки во вьюве
	 * @param isUpdatedImageData - обновлять ли содержимое поля Image.image (выбирали файл или удалили его)
	 * @param imageName - имя картинки
	 * @param imageData - содержимое файла
	 */
	public void updateDiskImage(String imageId, boolean isUpdatedImageData, String imageName, byte[] imageData)
	{
		updateImage(imageId, isUpdatedImageData, imageName, imageData, null);
	}

	/**
	 * Добавить ссылку на внешнюю пиктограмму
	 * @param imageId - идентификатор для обработки во вьюве
	 * @param imageLink - ссылка
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
	 * @param imageId идентификатор для обработки во вьюве
	 * @return картинка
	 */
	public Image getImage(String imageId) throws BusinessException 
	{
		Image image = images.get(imageId);
		if (image == null)
			throw new ResourceNotFoundBusinessException("Картинка с идентификатором " + imageId + " не проинициализирована.", Image.class);

		return image;
	}

	/**
	 * сохранить картинку
	 * @return идентификатор картинки
	 * @throws BusinessException
	 */
	public Long saveImage() throws BusinessException
	{
		Image image = saveImage(StringUtils.EMPTY);
		return image != null ? image.getId() : null;
	}
	/**
	 * сохранить картинку
	 * @param imageId идентификатор для обработки во вьюве
	 * @return картинка
	 * @throws BusinessException
	 */
	public Image saveImage(String imageId) throws BusinessException
	{
		Image image = images.get(imageId);
		//если новая картинка пустая, то не загружаем.
		if (image == null)
			throw new ResourceNotFoundBusinessException("Картинка с идентификатором " + imageId + " не проинициализирована.", Image.class);

		if (image.getId() == null && image.isEmpty())
			return null;

		//если сохраненная картинка пустая, то удаляем ее.
		if (image.isEmpty())
		{
			imageService.remove(image, getInstanceName());
			return null;
		}

		//картинка не пуста, сохраняем
		if (image.isHaveImageBlob())
			image = imageService.addOrUpdateImageAndImageData(image, imagesData.get(imageId), getInstanceName());
		else
			image = imageService.addOrUpdate(image, getInstanceName());
		return image;
	}

	/**
	 * удалить картинку из БД
	 * @throws BusinessException
	 */
	public void removeImage() throws BusinessException
	{
		removeImage(StringUtils.EMPTY);
	}

	/**
	 * удалить картинку из БД
	 * @param imageId идентификатор для обработки во вьюве
	 * @throws BusinessException
	 */
	public void removeImage(String imageId) throws BusinessException
	{
		Image image = images.get(imageId);
		if (image != null && image.getId() != null)
			imageService.remove(image, getInstanceName());
	}
}
