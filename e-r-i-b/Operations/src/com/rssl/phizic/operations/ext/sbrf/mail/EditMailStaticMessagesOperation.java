package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.ImageMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.PropertyConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gladishev
 * @ created 22.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditMailStaticMessagesOperation extends OperationBase implements EditEntityOperation
{
	private static final String CREATE_MAIL_FORM_KEY = "com.rssl.iccs.CREATE_MAIL_FORM";
	private static final String SEND_MAIL_KEY = "com.rssl.iccs.SEND_MAIL";

	public static final String IMAGE_URL = "/" + Application.PhizIC.toString() + "/images?id=%s";
	public static final String IMAGE_URL_REG = "/PhizIC/images\\?id=%s";
	public static final String IMAGE_TAG = "[img]%s[/img]";
	public static final String IMAGE_TAG_REG = "\\[img\\]%s\\[/img\\]";
	public static final Pattern IMG_NAME_PATTERN = Pattern.compile(String.format(IMAGE_TAG_REG, "(\\w+\\.\\w+)"));
	public static final Pattern IMG_URL_PATTERN = Pattern.compile(String.format(IMAGE_TAG_REG, String.format(IMAGE_URL_REG, "(\\d+)")));

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final StaticMessagesService service = new StaticMessagesService();
	private static final ImageService imageService = new ImageService();

	private StaticMessage createMailFormMessage;
	private Map<Long, Image> attachedImages;
	private StaticMessage sendMailMessage;

	public void initialize() throws BusinessException
	{

		createMailFormMessage = service.findByKey(CREATE_MAIL_FORM_KEY);
		if (createMailFormMessage == null)
			createMailFormMessage = new StaticMessage(CREATE_MAIL_FORM_KEY, ConfigFactory.getConfig(PropertyConfig.class).getProperty(CREATE_MAIL_FORM_KEY));
		attachedImages = service.getAttachedImages(createMailFormMessage.getKey());

		sendMailMessage = service.findByKey(SEND_MAIL_KEY);
		if(sendMailMessage == null)
			sendMailMessage = new StaticMessage(SEND_MAIL_KEY, ConfigFactory.getConfig(PropertyConfig.class).getProperty(SEND_MAIL_KEY));
	}

	public Pair<StaticMessage, StaticMessage> getEntity()
	{
		return new Pair<StaticMessage, StaticMessage>(createMailFormMessage, sendMailMessage);
	}

	public Map<Long, Image> getAttachedImages()
	{
		return attachedImages;
	}

	/**
	 * Сохранить изображение в БД
	 * @param data - данные
	 * @return сохраненное изображение
	 * @throws BusinessException
	 */
	public Image saveImage(byte[] data) throws BusinessException
	{
		Image image = new Image();
		image = imageService.addOrUpdateImageAndImageData(image, data, getInstanceName());
		service.add(new ImageMessage(image, CREATE_MAIL_FORM_KEY));
		return image;
	}

	public void save() throws BusinessException
	{
		service.addOrUpdate(createMailFormMessage);
		service.addOrUpdate(sendMailMessage);

		removeExcessImages();

	}

	/**
	 * Удаляет из таблиц картинки, на которых нет ссылок в тексте сообщения
	 */
	public void removeExcessImages() throws BusinessException
	{
		Matcher matcher = IMG_URL_PATTERN.matcher(createMailFormMessage.getText());
		List<Long> currentAttachedImagesIds = new ArrayList<Long>();
		while (matcher.find())
		{
            String imageId = matcher.group(1);
			try
			{
				currentAttachedImagesIds.add(Long.parseLong(imageId));
			}
			catch (NumberFormatException e)
			{
				//ничего не делаем
			}
		}

		for(Long imageId : attachedImages.keySet())
		{
			if (!currentAttachedImagesIds.contains(imageId))
			{
				try
				{
					imageService.remove(attachedImages.get(imageId), null);
				}
				catch (BusinessException e)
				{
					log.error("Ошибка при удалении картинки " + imageId, e);
				}

			}
		}
	}

	/**
	 * Возвращает список id картинок указанных в сообщении но не привязанных к нему
	 * @param text - текст сообщения
	 * @return список id картинок 
	 */
	public List<String> getNotAttachedImagesIds(String text)
	{
		Matcher urlMatcher = IMG_URL_PATTERN.matcher(text);
		List<String> notAttachedImagesIds = new ArrayList<String>();
		Map<Long, Image> currentAttachedImages = getAttachedImages();
		while (urlMatcher.find())
		{
			String imgId = urlMatcher.group(1);
			try
			{
				Long imageId = Long.parseLong(imgId);
				if (currentAttachedImages.get(imageId) == null)
					notAttachedImagesIds.add(Long.toString(imageId));
			}
			catch (NumberFormatException e)
			{
				notAttachedImagesIds.add(imgId);
			}
		}

		return notAttachedImagesIds;
	}

	/**
	 * Сохраняет добавленные в сообщение картинки
	 * @param text - текст сообщения
	 * @param filesData - картинки
	 * @return строка с замененными названиями картинок на url'ы к ним
	 */
	public String saveNewImages(String text, Map<String, byte[]> filesData) throws BusinessException
	{
		StringBuilder resultText = new StringBuilder(text);
		Matcher nameMatcher = IMG_NAME_PATTERN.matcher(text);

		List<String> savedFiles = new ArrayList<String>();
		while (nameMatcher.find())
		{
            String fileName = nameMatcher.group(1);
			if (savedFiles.contains(fileName))
				continue;

			//сохраняем изображение и заменяем в formText имя сохраненного изображения на нужный url
			byte[] fileData = filesData.get(fileName);
			if (fileData == null)
				continue;
			
			Long imageId = saveImage(fileData).getId();
			String oldText = String.format(IMAGE_TAG, fileName);
			String newText = String.format(IMAGE_TAG, String.format(IMAGE_URL, imageId));
			while (true)
			{
				int ind = resultText.indexOf(oldText);
				if (ind == -1)
					break;

				resultText.replace(ind, ind+oldText.length(), newText);
			}

			savedFiles.add(fileName);
        }

		return resultText.toString();
	}
}
