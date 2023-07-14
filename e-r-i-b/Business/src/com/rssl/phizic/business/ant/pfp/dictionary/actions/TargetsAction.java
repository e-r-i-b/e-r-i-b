package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга цели из xml
 */
public class TargetsAction extends DictionaryRecordsActionBase<Target>
{
	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String name = XmlHelper.getSimpleElementValue(element, "name");
		String imageUrl = XmlHelper.getSimpleElementValue(element, "imageUrl");
		boolean onlyOne = getBooleanValue(XmlHelper.getSimpleElementValue(element, "onlyOne"));
		boolean laterAll = getBooleanValue(XmlHelper.getSimpleElementValue(element, "laterAll"));
		boolean laterLoans = getBooleanValue(XmlHelper.getSimpleElementValue(element, "laterLoans"));

		//создание сущности
		Target target = new Target();
		target.setName(name);
		target.setOnlyOne(onlyOne);
		target.setLaterAll(laterAll);
		target.setLaterLoans(laterLoans);
		addRecord(name, target, getImageValue(imageUrl));
	}
}
