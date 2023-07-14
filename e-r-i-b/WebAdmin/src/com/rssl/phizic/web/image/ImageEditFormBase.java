package com.rssl.phizic.web.image;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.image.ImageSourceKind;
import com.rssl.phizic.business.image.configure.ImagesSettingsService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.validators.FileSizeLimitValidator;
import com.rssl.phizic.web.validators.FileValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.StringValueTransformer;
import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import java.util.*;

/**
 * @author akrenev
 * @ created 03.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * Базовая форма для работы с картинками
 */
public class ImageEditFormBase extends EditFormBase
{
	private static final RegexpFieldValidator DEFAULT_IMAGE_URL_LENGTH_VALIDATOR =
			new RegexpFieldValidator(".{0,250}", "Длина ссылки не должна превышать 250 символов");

	private static final RegexpFieldValidator DEFAULT_IMAGE_NAME_VALIDATOR =
			new RegexpFieldValidator(".+\\.(?i:jpg|jpeg|gif|png|bmp)",
									 "Вы указали неверный формат файла для загрузки иконки. Пожалуйста, выберите файл в формате .jpg, .jpeg, .gif, .bmp, .png.");

	private static final ChooseValueValidator SOURCE_KIND_VALIDATOR =
			new ChooseValueValidator(CollectionUtils.collect(new ArrayIterator(ImageSourceKind.values()), StringValueTransformer.getInstance()),
									 "Невозможно определить тип источника изображения.");

	private static final List<String> imageIds = Arrays.asList(StringUtils.EMPTY);

    public  static final String FILE_SOURCE_KIND_FIELD_NAME_PREFIX       = "imageSourceKind";
    private static final String IMAGE_DISC_SOURCE_FIELD_NAME_PREFIX      = "imageDiscSource";
    public  static final String IMAGE_NAME_DISC_SOURCE_FIELD_NAME_PREFIX = "imageNameDiscSource";
    public  static final String IMAGE_EXTERNAL_SOURCE_FIELD_NAME_PREFIX  = "imageExternalSource";
    public  static final String SET_NEW_FILE_FIELD_NAME_PREFIX           = "setNewFile";

	private Map<String, FormFile> images = new HashMap<String, FormFile>();

	/**
	 * @return мапа картинок
	 */
	public Map<String, FormFile> getImages()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return images;
	}

	/**
	 * задать мапу картинок
	 * @param images мапа
	 */
	public void setImages(Map<String, FormFile> images)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.images = images;
	}

	/**
	 * получить картинку по идентификатору
	 * @param key идентификатор
	 * @return картинка
	 */
	public FormFile getImage(String key)
	{
	    return images.get(IMAGE_DISC_SOURCE_FIELD_NAME_PREFIX + key);
	}

	/**
	 * @return список идентификаторов картинок
	 */
	public List<String> getImageIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return imageIds;
	}

	protected static List<Field> getImageFields(List<String> imageIds)
	{
		List<Field> imageFields = new ArrayList<Field>();
		for (String id : imageIds)
		{
			imageFields.addAll(getImageField(id));
		}
		return imageFields;
	}

	protected static List<Field> getImageField()
	{
		return getImageField(StringUtils.EMPTY);
	}

	/**
	 * проверка на то что картинка задана
	 * @param imageId идентификатор картинки
	 * @return true -- задана
	 */
	public boolean isEmptyImage(String imageId)
	{
		String imageSourceKind = (String) getField(FILE_SOURCE_KIND_FIELD_NAME_PREFIX + imageId);
		if (ImageSourceKind.DISC.name().equals(imageSourceKind))
		{
			FormFile image = getImage(imageId);
			String imageNameFieldValue = (String) getField(IMAGE_NAME_DISC_SOURCE_FIELD_NAME_PREFIX + imageId);
			return StringHelper.isEmpty(imageNameFieldValue) && (image == null || StringHelper.isEmpty(image.getFileName()));
		}
		else
		{
			String imageUrlValue = (String) getField(IMAGE_EXTERNAL_SOURCE_FIELD_NAME_PREFIX + imageId);
			return StringHelper.isEmpty(imageUrlValue);
		}
	}

	protected FileSizeLimitValidator getDefaultImageFileSizeLimitValidator(String key)
	{
		String maxSizePropertyValue = ConfigFactory.getConfig(ImagesSettingsService.class).getProperty(key);
		int maxSize = Integer.parseInt(maxSizePropertyValue) * FileSizeLimitValidator.BYTE_IN_KB;
		return new FileSizeLimitValidator(maxSize);
	}

	protected FileValidator getImageFileValidator(String imageId)
	{
		return null;
	}

	protected FieldValidator getImageFileNameValidator(String imageId)
	{
		return DEFAULT_IMAGE_NAME_VALIDATOR;
	}

	protected FieldValidator getImageUrlValidator(String imageId)
	{
		return DEFAULT_IMAGE_URL_LENGTH_VALIDATOR;
	}

	@SuppressWarnings({"ReuseOfLocalVariable"})
	protected static List<Field> getImageField(String imageCode)
	{
	    List<Field> result = new ArrayList<Field>();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FILE_SOURCE_KIND_FIELD_NAME_PREFIX + imageCode);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(SOURCE_KIND_VALIDATOR);
		result.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя файла");
		fieldBuilder.setName(IMAGE_NAME_DISC_SOURCE_FIELD_NAME_PREFIX + imageCode);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		result.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Путь к внешнему ресурсу");
		fieldBuilder.setName(IMAGE_EXTERNAL_SOURCE_FIELD_NAME_PREFIX + imageCode);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators();
		result.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SET_NEW_FILE_FIELD_NAME_PREFIX + imageCode);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		result.add(fieldBuilder.build());

		return result;
	}
}
