package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapperMultiImage;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapperOneImage;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * базовый экшен для парсинга записей из xml
 */
public abstract class DictionaryRecordsActionBase<P extends PFPDictionaryRecord> implements ForeachElementAction
{
	private static final String GLOBAL_IMAGE_PATH_PREFIX = "${global_image_path}";
	private Map<String, PFPDictionaryRecordWrapper<P>> records = new HashMap<String, PFPDictionaryRecordWrapper<P>>();

	/**
	 * проинициализировать экшен
	 * @param allRecords загруженные записи
	 */
	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords){}

	protected void addRecord(String key, P value) throws BusinessLogicException
	{
		if (StringHelper.isEmpty(key))
			return;

		if (records.put(key, new PFPDictionaryRecordWrapper<P>(value)) != null)
			throw new BusinessLogicException("Ключ " + key + " не уникален.");

	}

	protected void addRecord(String key, P value, Image image) throws BusinessLogicException
	{
		if (StringHelper.isEmpty(key))
			return;

		if (records.put(key, new PFPDictionaryRecordWrapperOneImage<P>(value, image)) != null)
			throw new BusinessLogicException("Ключ " + key + " не уникален.");

	}

	protected void addRecord(String key, P value, Map<String,Image> images) throws BusinessLogicException
	{
		if (StringHelper.isEmpty(key))
			return;

		if (records.put(key, new PFPDictionaryRecordWrapperMultiImage<P>(value, images)) != null)
			throw new BusinessLogicException("Ключ " + key + " не уникален.");

	}

	/**
	 * @return записи для текущего справочника
	 */
	public Map<String, PFPDictionaryRecordWrapper<P>> getRecords()
	{
		return records;
	}

	protected boolean getBooleanValue(String value)
	{
		return BooleanUtils.toBoolean(value, "yes", "no");
	}

	protected BigDecimal getBigDecimalValue(String value)
	{
		if (StringHelper.isEmpty(value))
			return null;

		return new BigDecimal(value);
	}

	protected Long getLongValue(String value)
	{
		if (StringHelper.isEmpty(value))
			return null;

		return Long.valueOf(value);
	}

	protected Image getImageValue(String imageUrl)
	{
		if (StringHelper.isEmpty(imageUrl))
			return null;

		Image image = new Image();
		if (imageUrl.startsWith(GLOBAL_IMAGE_PATH_PREFIX))
			image.setInnerImage(imageUrl.substring(GLOBAL_IMAGE_PATH_PREFIX.length()));
		else
			image.setExtendImage(imageUrl);
		image.setName(imageUrl.substring(imageUrl.lastIndexOf("/")+1)); //проставляем названия для того, чтоб картинки отображались в админке

		return image;
	}
}
