package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

/**
 * Базовый класс для загрузки расширенных метаданных
 * @author niculichev
 * @ created 15.05.2013
 * @ $Author$
 * @ $Revision$
 */
abstract public class ExtendedMetadataLoaderBase implements ExtendedMetadataLoader
{
	/**
	 * загрузить расширенные метаданные
	 * @param metadata оригинальные метаданные
	 * @param fields поля логическоей формы
	 * @param dictionaries справочники
	 * @param formDescription описание формы, может отсутсвовать. если отсутсвует испотзуется название из оригинальной формы
	 * @return расширенные метаданные
	 */
	protected Metadata load(Metadata metadata, List<Field> fields, Map<String, Element> dictionaries, String formDescription)
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);
		newMetadata.addAllDictionaries(metadata.getDictionaries());
		if(MapUtils.isNotEmpty(dictionaries))
			newMetadata.addAllDictionaries(dictionaries);

		Form form = metadata.getForm();
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(StringHelper.isEmpty(formDescription) ? form.getDescription() : formDescription);

		formBuilder.addFields(fields);
		formBuilder.addFormValidators(form.getFormValidators());
		newMetadata.setExtendedForm(formBuilder.build());
		return newMetadata;
	}
}
