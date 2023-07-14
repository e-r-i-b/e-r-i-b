package com.rssl.phizic.profile;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * ������ ��� �������� �������� �������
 * @author miklyaev
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ProfileConfig extends Config
{
	/**
	 * ������� ��� ������ ����������.
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
	 * ���������� ���� ������ ��� �������.
	 */
	private Set<String> avatarAvailableFiles;

	/**
	 * ������������ ������ ����������� �� ������� �������.
	 */
	private Integer maxAvatarLongSize;

	/**
	 * ������������ ������ ������������ ����������� �� ������� �������.
	 */
	private Integer maxLoadedImageLongSideSize;

	/**
	 * ����������� ������ ����������� �� ������� �������.
	 */
	private Integer minAvatarShortSize;

	/**
	 * ����������� ���������� ������ ����� ����������� (� ��).
	 */
	private Integer avatarFileMaxSize;

	/**
	 * ����������� ���������� ������ ����� ����������� ����� ������������ (� ��).
	 */
	private Integer kadrAvatarFileMaxSize;

	/**
	 * �������� ���������� �� ���������.
	 */
	private Map<String, String> documentTypeNames = new HashMap<String, String>();

	/**
	 * ���������� ��� ���������� ��������.
	 */
	private List<String> imageSavePaths = new LinkedList<String>();

	/**
	 * ��������� ����� ��� �������� ��������.
	 */
	private String tempImageSavePath;

	/**
	 * ������ �����������, ����������� ��� �������� � DMZ.
	 */
	private long deleteImageFileSize;

	/**
	 * ���� �� ����������� ��������, ��������� ���������������� ��������.
	 */
	private String localImageServletUri;

	/**
	 * ���� �� ����������� � ����������� ��������.
	 */
	private String staticImageUri;

	/**
	 * �����, ����� �������� ����������� ������ ���� ���������� � DMZ.
	 */
	private int localImageTimeMoved;

	/**
	 * ����������� ������������� � ����������� ������������� �������� ��� �������������� �������
	 */
	private boolean accessUserDocumentDL;

	/**
	 * ����������� ������������� ������������� ��� �������������� �������
	 */
	private boolean accessUserDocumentRC;

	/**
	 * ����������� ����� ��� �������������� �������
	 */
	private boolean accessUserDocumentINN;

	/**
	 * EXTERNAL_ID ���������� ����� ���������� �����.
	 */
	private String socialNetProviderId;
	/**
	 * �������� ����, � ������� ����� ������������ ������������� ������������.
	 */
	private String socialNetUserIdFieldName;

	/**
	 * �������� ����� ��� ���������� �������.
	 */
	private Map<String, String> reqFieldNames = new HashMap<String, String>();;

	/**
	 * ����� ����� ��� ���������� �������.
	 */
	private Map<String, Integer> reqMaxLength = new HashMap<String, Integer>();

	/**
	 * @return ���������� ���� ������ ��� �������.
	 */
	public Set<String> getAvatarAvailableFiles()
	{
		return Collections.unmodifiableSet(avatarAvailableFiles);
	}

	/**
	 * @return ������������ ������ ������������ ����������� �� ������� �������.
	 */
	public Integer getMaxLoadedImageLongSideSize()
	{
		return maxLoadedImageLongSideSize;
	}

	/**
	 * @return ������������ ������ ����������� �� ������� �������.
	 */
	public Integer getMaxAvatarLongSize()
	{
		return maxAvatarLongSize;
	}

	/**
	 * @return ����������� ������ ����������� �� ������� �������.
	 */
	public Integer getMinAvatarShortSize()
	{
		return minAvatarShortSize;
	}

	/**
	 * @return ����������� ���������� ������ ����� ����������� (� ��).
	 */
	public Integer getAvatarFileMaxSize()
	{
		return avatarFileMaxSize;
	}

	/**
	 * @return ����������� ���������� ������ ����� ����������� ����� ������������ (� ��).
	 */
	public Integer getKadrAvatarFileMaxSize()
	{
		return kadrAvatarFileMaxSize;
	}

	/**
	 * @param documentType ��� ���������.
	 * @return �������� ���������� �� ���������.
	 */
	public String getDocumentDefaultName(String documentType)
	{
		return documentTypeNames.get(documentType);
	}

	/**
	 * @param key ��� ����.
	 * @return �������� ���� ��� ���������� �������.
	 */
	public String getFieldNameForReq(String key)
	{
		return reqFieldNames.get(key);
	}

	/**
	 * @param key ��� ����.
	 * @return ����� ���� ��� INN � DL.
	 */
	public Integer getMaxLengthForReq(String key)
	{
		return reqMaxLength.get(key);
	}

	/**
	 * @return ���������� ��� ���������� ��������.
	 */
	public List<String> getImageSavePaths()
	{
		return imageSavePaths;
	}

	/**
	 * @return ��������� ����� ��� �������� ��������.
	 */
	public String getTempImageSavePath()
	{
		return tempImageSavePath;
	}

	/**
	 * @return ���� �� ����������� � ����������� ��������.
	 */
	public String getStaticImageUri()
	{
		return staticImageUri;
	}

	/**
	 * @return ����� ��������� ����� ��� �������� �����������.
	 */
	public int getNumOfNestedFolder()
	{
		return 4;
	}

	/**
	 * @return ������ �����������, ����������� ��� �������� � DMZ.
	 */
	public long getDeleteImageFileSize()
	{
		return deleteImageFileSize;
	}

	/**
	 * @return ���� �� ����������� ��������, ��������� ���������������� ��������.
	 */
	public String getLocalImageServletUri()
	{
		return localImageServletUri;
	}

	/**
	 * @return �����, ����� �������� ����������� ������ ���� ���������� � DMZ.
	 */
	public int getLocalImageTimeMoved()
	{
		return localImageTimeMoved;
	}

	/**
	 * @return ����������� ������������� ������������� ��� �������������� �������
	 */
	public boolean isAccessUserDocumentDL()
	{
		return accessUserDocumentDL;
	}

	/**
	 * @return ����������� ������������� � ����������� ������������� �������� ��� �������������� �������
	 */
	public boolean isAccessUserDocumentRC()
	{
		return accessUserDocumentRC;
	}

	/**
	 * @return ����������� ����� ��� �������������� �������
	 */
	public boolean isAccessUserDocumentINN()
	{
		return accessUserDocumentINN;
	}

	/**
	 * @return EXTERNAL_ID ���������� ����� ���������� �����.
	 */
	public String getSocialNetProviderId()
	{
		return socialNetProviderId;
	}

	/**
	 * @return �������� ����, � ������� ����� ������������ ������������� ������������.
	 */
	public String getSocialNetUserIdFieldName()
	{
		return socialNetUserIdFieldName;
	}

	/**
	 * �����������.
	 * @param reader �����.
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
	 * @return ���������� ����� ��� �������
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
