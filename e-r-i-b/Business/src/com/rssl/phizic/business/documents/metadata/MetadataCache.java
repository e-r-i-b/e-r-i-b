package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.FormException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Evgrafov
 * @ created 09.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */
public class MetadataCache
{
	private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);

	private static final Map<String, Cache> cachesByCategory = new HashMap<String, Cache>();
	private static final Map<String, MetadataLoader> metadataLoaderByCategory = new HashMap<String, MetadataLoader>();

	public static final String CACHE_CATEGORY_PAYMENT_INTERNET = "payments-internet";
	public static final String CACHE_CATEGORY_PAYMENT_SMS = "payments-sms";

	static
	{
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("iccs.properties");
		Properties iccs = new Properties();
		try
		{
			iccs.load(stream);
			String categories = iccs.getProperty("com.rssl.iccs.forms.categories");
			String[] categoriesArray = categories.replace(" ", "").split(",");
			for (String category : categoriesArray)
			{
				registerCategory(category, iccs);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void registerCategory(String category, Properties iccs)
	{
		Cache cache = null;
		try
		{
			cache = CacheProvider.getCache("forms-cache-" + category);
		}
		catch (CacheException e)
		{
			throw new FormException(e);
		}

		if (cache == null)
			throw new RuntimeException("�� ������ ��� ��� ��������� " + category);

		String formsLoader = iccs.getProperty("com.rssl.iccs.forms." + category + "." + "forms-loader");

		if (formsLoader == null)
			throw new FormException("����������� ��������� forms-loader ��� ��������� " + category);

		MetadataLoader metadataLoader;
		try
		{
			Class<?> loaderClazz = Thread.currentThread().getContextClassLoader().loadClass(formsLoader);
			metadataLoader = (MetadataLoader) loaderClazz.newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new FormException(e);
		}

		cachesByCategory.put(category, cache);
		metadataLoaderByCategory.put(category, metadataLoader);
	}

	/**
	 * ��������� ���������� ��� ���������, ������� �������� ������ � ���� ����� ���������� ��������
	 * @param document ��������, ��� �������� ����������� ����������
	 * @return ����������
	 */
	public static Metadata getExtendedMetadata(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		String formName = document.getFormName();

		Metadata formMetadata = getBasicMetadata(formName);
		ExtendedMetadataLoader extendedMetadataLoader = formMetadata.getExtendedMetadataLoader();

		if (extendedMetadataLoader == null)
			return formMetadata;
		DocumentFieldValuesSource fieldValuesSource = new DocumentFieldValuesSource(formMetadata, document);
		if (extendedMetadataLoader.getExtendedMetadataKey(fieldValuesSource) == null)
		{
			//������ ���� � ����� ����
			return extendedMetadataLoader.load(formMetadata, document);
		}

		String metadataKey = formMetadata.getMetadataKey(fieldValuesSource);
		rwl.readLock().lock();
		try
		{
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
		}
		finally
		{
			rwl.readLock().unlock();
		}
		rwl.writeLock().lock();
		try
		{
			//DCP
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
			return cacheMetadata(metadataKey, extendedMetadataLoader.load(formMetadata, document));
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	/**
	 * �������� ����������� ����������
	 * @param template ������ �������
	 * @return ����������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static Metadata getExtendedMetadata(TemplateDocument template) throws BusinessLogicException, BusinessException
	{
		Metadata formMetadata = getBasicMetadata(template.getFormType().getName());
		ExtendedMetadataLoader extendedMetadataLoader = formMetadata.getExtendedMetadataLoader();
		if (extendedMetadataLoader == null)
		{
			return formMetadata;
		}

		FieldValuesSource source = new TemplateFieldValueSource(template);
		if (extendedMetadataLoader.getExtendedMetadataKey(source) == null)
		{
			//������ ���� � ����� ����
			return extendedMetadataLoader.load(formMetadata, template);
		}

		String metadataKey = formMetadata.getMetadataKey(source);
		rwl.readLock().lock();
		try
		{
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
		}
		finally
		{
			rwl.readLock().unlock();
		}
		rwl.writeLock().lock();
		try
		{
			//DCP
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
			return cacheMetadata(metadataKey, extendedMetadataLoader.load(formMetadata, template));
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	/**
	 * �������� ����������� ����������
	 * @param document ��������
	 * @param template ������ �������
	 * @return ����������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static Metadata getExtendedMetadata(BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		Metadata formMetadata = getBasicMetadata(template.getFormType().getName());
		ExtendedMetadataLoader extendedMetadataLoader = formMetadata.getExtendedMetadataLoader();
		if (extendedMetadataLoader == null)
		{
			return formMetadata;
		}

		FieldValuesSource source = new TemplateFieldValueSource(template);
		if (extendedMetadataLoader.getExtendedMetadataKey(source) == null)
		{
			//������ ���� � ����� ����
			return extendedMetadataLoader.load(formMetadata, document, template);
		}

		String metadataKey = formMetadata.getMetadataKey(source);
		rwl.readLock().lock();
		try
		{
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
		}
		finally
		{
			rwl.readLock().unlock();
		}
		rwl.writeLock().lock();
		try
		{
			//DCP
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
			return cacheMetadata(metadataKey, extendedMetadataLoader.load(formMetadata, document, template));
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}


	/**
	 * ��������� ���������� ��� ������������ ���������
	 * @param formName ��� �����
	 * @param fieldValuesSource �������� �������� "���������������/����������������" ����������� ����:
	 * ��������, ��� ����������� �������� �������� ������ ��������� ����������� � ���������� �����.
	 * @return ����������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static Metadata getExtendedMetadata(String formName, FieldValuesSource fieldValuesSource) throws BusinessException, BusinessLogicException
	{
		Metadata formMetadata = getBasicMetadata(formName);
		ExtendedMetadataLoader extendedMetadataLoader = formMetadata.getExtendedMetadataLoader();

		if (extendedMetadataLoader == null)
			return formMetadata;

		if (extendedMetadataLoader.getExtendedMetadataKey(fieldValuesSource) == null)
		{
			//������ ���� � ����� ����
			return extendedMetadataLoader.load(formMetadata, fieldValuesSource);
		}
		String metadataKey = formMetadata.getMetadataKey(fieldValuesSource);

		rwl.readLock().lock();
		try
		{
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
		}
		finally
		{
			rwl.readLock().unlock();
		}
		rwl.writeLock().lock();
		try
		{
			//DCP
			Metadata metadata = getFromCache(metadataKey);
			if (metadata != null)
			{
				return metadata;
			}
			return cacheMetadata(metadataKey, extendedMetadataLoader.load(formMetadata, fieldValuesSource));
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	/**
	 * ��������� ���������� ��� �����, ������� �������� ������ � ���� ����� �������� �� ��
	 * @param formName ��� ����� ��� ������� ������ ����������
	 * @return ����������
	 */
	public static Metadata getBasicMetadata(String formName)
	{
		rwl.readLock().lock();
		try
		{
			//������� ���� � ���� (���� - ��� �����)
			Metadata metadata = getFromCache(formName);
			if (metadata != null)
			{
				return metadata;
			}
		}
		finally
		{
			rwl.readLock().unlock();
		}
		//�� ����� � ���� -> ������� ����.
		rwl.writeLock().lock();
		try
		{
			//DCP
			Metadata metadata = getFromCache(formName);
			if (metadata != null)
			{
				return metadata;
			}
			String category = getCategory();
			MetadataLoader loader = metadataLoaderByCategory.get(category);
			if (loader == null)
				throw new FormException("�� ��������� forms-loader ��� ��������� " + category + ". Check iccs.properties");
			//��������� � ����������
			return cacheMetadata(formName, loader.load(formName));
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	/**
	 * �������� ���������� �� ���� �� �����.
	 * @param metadataKey ���� ����������
	 * @return ���������� ��� null � ������ ��������� � ����.
	 */
	private static Metadata getFromCache(String metadataKey)
	{
		String category = getCategory();
		Cache cache = cachesByCategory.get(category);
		if (cache == null)
			throw new RuntimeException("�� ��������� ��� ��� ��������� " + category);
		Element element = null;

		try
		{
			element = cache.get(metadataKey);
		}
		catch (CacheException e)
		{
			throw new FormException(e);
		}

		if (element == null)
		{
			return null;
		}
		return (Metadata) element.getValue();
	}

	/**
	 * ��������� ���������� � ���.
	 * @param metadataKey ���� ��� �������� ����
	 * @param metadata ����������
	 * @return ����������� � ���� ����������(�� ��� �������� � ��������� metadata).
	 */
	private static Metadata cacheMetadata(String metadataKey, Metadata metadata)
	{
		String category = getCategory();
		Cache cache = cachesByCategory.get(category);
		if (cache == null)
			throw new RuntimeException("�� ��������� ��� ��� ��������� " + category);
		cache.put(new Element(metadataKey, metadata));
		return metadata;
	}

	private static String getCategory()
	{
		//TODO ����� ������ ���e��. ����������
		if (Application.SmsBanking == LogThreadContext.getApplication())
		{
			return CACHE_CATEGORY_PAYMENT_SMS;
		}
		return CACHE_CATEGORY_PAYMENT_INTERNET;
	}
}