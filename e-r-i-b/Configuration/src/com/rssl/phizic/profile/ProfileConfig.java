package com.rssl.phizic.profile;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Конфиг для хранения настроек профиля
 * @author miklyaev
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ProfileConfig extends Config
{
	/**
	 * Префикс для поиска параметров.
	 */
	public static final String AVATAR_AVAILABLE_FILES = "com.rssl.profile.avatarAvailableFiles";
	public static final String MAX_AVATAR_LONG_SIZE = "com.rssl.profile.maxAvatarLongSize";
	private static final String MIN_AVATAR_SHORT_SIZE = "com.rssl.profile.minAvatarShortSize";
	public static final String MAX_LOADED_IMAGE_LONG_SIDE_SIZE = "com.rssl.profile.maxLoadedImageLongSize";
	public static final String AVATAR_FILE_MAX_SIZE = "com.rssl.profile.avatarFileMaxSize";
	public static final String KADR_AVATAR_FILE_MAX_SIZE = "com.rssl.profile.kadrAvatarFileMaxSize";
	private static final String USER_DOCUMENTS_PREFIX = "com.rssl.profile.userDocument.";
	private static final String SAVE_IMAGE_PATH_PREFIXES = "com.rssl.profile.images.save.path";
	private static final String SAVE_IMAGE_TEMP_PATH = "com.rssl.profile.images.save.temppath";
	private static final String DELETE_IMAGE_FILE_SIZE = "com.rssl.profile.images.save.delete_file_size";
	private static final String LOCAL_IMAGE_SERVLET_URI = "com.rssl.profile.images.servletpath";
	private static final String STATIC_IMAGE_URI = "com.rssl.profile.images.staticpath";
	private static final String LOCAL_IMAGE_TIME_MOVED = "com.rssl.profile.images.time.moved";
	private static final String SOCIAL_NET_PROVIDER_ID = "com.rssl.profile.socialnet.odnoklassniki.prodiverExternalId";
	private static final String SOCIAL_NET_USER_ID_FIELD_NAME = "com.rssl.profile.socialnet.odnoklassniki.fieldName";
	private static final String USER_DOCUMENTS_ACCESS_DL = "com.rssl.profile.userDocument.access.DL";
	private static final String USER_DOCUMENTS_ACCESS_RC = "com.rssl.profile.userDocument.access.RC";
	private static final String USER_DOCUMENTS_ACCESS_INN = "com.rssl.profile.userDocument.access.INN";
	private static final String REQ_FIELD_NAMES_PREFIX = "com.rssl.profile.REQ.fieldName.";
	private static final String REQ_MAX_LENGTH_PREFIX = "com.rssl.profile.REQ.maxLength.";
	/**
	 * Допустимые типы файлов для аватара.
	 */
	private Set<String> avatarAvailableFiles;

	/**
	 * Максимальный размер изображения по длинной стороне.
	 */
	private Integer maxAvatarLongSize;

	/**
	 * Максимальный размер загружаемого изображения по длинной стороне.
	 */
	private Integer maxLoadedImageLongSideSize;

	/**
	 * Минимальный размер изображения по меньшей стороне.
	 */
	private Integer minAvatarShortSize;

	/**
	 * Максимально допустимый размер файла изображения (в кб).
	 */
	private Integer avatarFileMaxSize;

	/**
	 * Максимально допустимый размер файла изображения после кадрирования (в кб).
	 */
	private Integer kadrAvatarFileMaxSize;

	/**
	 * Названия документов по умолчанию.
	 */
	private Map<String, String> documentTypeNames = new HashMap<String, String>();

	/**
	 * Директории для сохранения картинок.
	 */
	private List<String> imageSavePaths = new LinkedList<String>();

	/**
	 * Временная папка для хранения картинок.
	 */
	private String tempImageSavePath;

	/**
	 * Размер изображения, помеченного для удаления в DMZ.
	 */
	private long deleteImageFileSize;

	/**
	 * Путь до внутреннего сервлета, выдающего пользовательские картинки.
	 */
	private String localImageServletUri;

	/**
	 * путь до изображения в статических ресурсах.
	 */
	private String staticImageUri;

	/**
	 * Время, после которого изображение должно быть перенесено в DMZ.
	 */
	private int localImageTimeMoved;

	/**
	 * доступность Свидетельства о регистрации транспортного средства как идентификатора корзины
	 */
	private boolean accessUserDocumentDL;

	/**
	 * доступность водительского удостоверения как идентификатора корзины
	 */
	private boolean accessUserDocumentRC;

	/**
	 * доступность СНИЛС как идентификатора корзины
	 */
	private boolean accessUserDocumentINN;

	/**
	 * EXTERNAL_ID поставщика услуг социальных сетей.
	 */
	private String socialNetProviderId;
	/**
	 * Название поля, в котором будет записываться идентификатор пользователя.
	 */
	private String socialNetUserIdFieldName;

	/**
	 * Названия полей для реквизитов корзины.
	 */
	private Map<String, String> reqFieldNames = new HashMap<String, String>();;

	/**
	 * Длина полей для реквизитов корзины.
	 */
	private Map<String, Integer> reqMaxLength = new HashMap<String, Integer>();

	/**
	 * @return Допустимые типы файлов для аватара.
	 */
	public Set<String> getAvatarAvailableFiles()
	{
		return Collections.unmodifiableSet(avatarAvailableFiles);
	}

	/**
	 * @return Максимальный размер загружаемого изображения по длинной стороне.
	 */
	public Integer getMaxLoadedImageLongSideSize()
	{
		return maxLoadedImageLongSideSize;
	}

	/**
	 * @return Максимальный размер изображения по длинной стороне.
	 */
	public Integer getMaxAvatarLongSize()
	{
		return maxAvatarLongSize;
	}

	/**
	 * @return Минимальный размер изображения по меньшей стороне.
	 */
	public Integer getMinAvatarShortSize()
	{
		return minAvatarShortSize;
	}

	/**
	 * @return Максимально допустимый размер файла изображения (в кб).
	 */
	public Integer getAvatarFileMaxSize()
	{
		return avatarFileMaxSize;
	}

	/**
	 * @return Максимально допустимый размер файла изображения после кадрирования (в кб).
	 */
	public Integer getKadrAvatarFileMaxSize()
	{
		return kadrAvatarFileMaxSize;
	}

	/**
	 * @param documentType тип документа.
	 * @return Название документов по умолчанию.
	 */
	public String getDocumentDefaultName(String documentType)
	{
		return documentTypeNames.get(documentType);
	}

	/**
	 * @param key тип поля.
	 * @return Название поля для реквизитов корзины.
	 */
	public String getFieldNameForReq(String key)
	{
		return reqFieldNames.get(key);
	}

	/**
	 * @param key тип поля.
	 * @return Длина поля для INN и DL.
	 */
	public Integer getMaxLengthForReq(String key)
	{
		return reqMaxLength.get(key);
	}

	/**
	 * @return Директории для сохранения картинок.
	 */
	public List<String> getImageSavePaths()
	{
		return imageSavePaths;
	}

	/**
	 * @return Временная папка для хранения картинок.
	 */
	public String getTempImageSavePath()
	{
		return tempImageSavePath;
	}

	/**
	 * @return путь до изображения в статических ресурсах.
	 */
	public String getStaticImageUri()
	{
		return staticImageUri;
	}

	/**
	 * @return число вложенных папок для хранения изображений.
	 */
	public int getNumOfNestedFolder()
	{
		return 4;
	}

	/**
	 * @return Размер изображения, помеченного для удаления в DMZ.
	 */
	public long getDeleteImageFileSize()
	{
		return deleteImageFileSize;
	}

	/**
	 * @return Путь до внутреннего сервлета, выдающего пользовательские картинки.
	 */
	public String getLocalImageServletUri()
	{
		return localImageServletUri;
	}

	/**
	 * @return Время, после которого изображение должно быть перенесено в DMZ.
	 */
	public int getLocalImageTimeMoved()
	{
		return localImageTimeMoved;
	}

	/**
	 * @return доступность водительского удостоверения как идентификатора корзины
	 */
	public boolean isAccessUserDocumentDL()
	{
		return accessUserDocumentDL;
	}

	/**
	 * @return доступность Свидетельства о регистрации транспортного средства как идентификатора корзины
	 */
	public boolean isAccessUserDocumentRC()
	{
		return accessUserDocumentRC;
	}

	/**
	 * @return доступность СНИЛС как идентификатора корзины
	 */
	public boolean isAccessUserDocumentINN()
	{
		return accessUserDocumentINN;
	}

	/**
	 * @return EXTERNAL_ID поставщика услуг социальных сетей.
	 */
	public String getSocialNetProviderId()
	{
		return socialNetProviderId;
	}

	/**
	 * @return Название поля, в котором будет записываться идентификатор пользователя.
	 */
	public String getSocialNetUserIdFieldName()
	{
		return socialNetUserIdFieldName;
	}

	/**
	 * Конструктор.
	 * @param reader ридер.
	 */
	public ProfileConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		avatarAvailableFiles = getAvailableFiles();
		maxAvatarLongSize = getIntProperty(MAX_AVATAR_LONG_SIZE);
		minAvatarShortSize = getIntProperty(MIN_AVATAR_SHORT_SIZE);
		avatarFileMaxSize = getIntProperty(AVATAR_FILE_MAX_SIZE);
		maxLoadedImageLongSideSize = getIntProperty(MAX_LOADED_IMAGE_LONG_SIDE_SIZE);
		kadrAvatarFileMaxSize = getIntProperty(KADR_AVATAR_FILE_MAX_SIZE);

		Properties properties = getProperties(USER_DOCUMENTS_PREFIX);
		for (Map.Entry entry : properties.entrySet())
		{
			documentTypeNames.put(((String)(entry.getKey())).substring(USER_DOCUMENTS_PREFIX.length()), (String)entry.getValue());
		}

		Properties imageDirectory = getProperties(SAVE_IMAGE_PATH_PREFIXES);
		for (Map.Entry entry : imageDirectory.entrySet())
		{
			imageSavePaths.add((String) entry.getValue());
		}

		tempImageSavePath = getProperty(SAVE_IMAGE_TEMP_PATH);
		deleteImageFileSize = getLongProperty(DELETE_IMAGE_FILE_SIZE);
		localImageServletUri = getProperty(LOCAL_IMAGE_SERVLET_URI);
		staticImageUri = getProperty(STATIC_IMAGE_URI);
		localImageTimeMoved = getIntProperty(LOCAL_IMAGE_TIME_MOVED);
		accessUserDocumentDL = getBoolProperty(USER_DOCUMENTS_ACCESS_DL);
		accessUserDocumentRC = getBoolProperty(USER_DOCUMENTS_ACCESS_RC);
		accessUserDocumentINN =  getBoolProperty(USER_DOCUMENTS_ACCESS_INN);
		socialNetProviderId = getProperty(SOCIAL_NET_PROVIDER_ID);
		socialNetUserIdFieldName = getProperty(SOCIAL_NET_USER_ID_FIELD_NAME);

		Properties innDlFieldNamesProperties = getProperties(REQ_FIELD_NAMES_PREFIX);
		for (Map.Entry entry : innDlFieldNamesProperties.entrySet())
		{
			reqFieldNames.put(((String)(entry.getKey())).substring(REQ_FIELD_NAMES_PREFIX.length()), (String)entry.getValue());
		}

		Properties innDlMaxLengthProperties = getProperties(REQ_MAX_LENGTH_PREFIX);
		for (Map.Entry entry : innDlMaxLengthProperties.entrySet())
		{
			reqMaxLength.put(((String)(entry.getKey())).substring(REQ_MAX_LENGTH_PREFIX.length()), Integer.valueOf((String)entry.getValue()));
		}
	}

	/**
	 * @return допустимые файлы для аватара
	 */
	private Set<String> getAvailableFiles()
	{
		String strAvatarAvailableFiles = getProperty(AVATAR_AVAILABLE_FILES);
		String[] availableFiles = strAvatarAvailableFiles.split(",");
		if(ArrayUtils.isEmpty(availableFiles))
			return Collections.emptySet();
		Set<String> result = new HashSet<String>();
		result.addAll(Arrays.asList(availableFiles));
		return result;
	}
}
