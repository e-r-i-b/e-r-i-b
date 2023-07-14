package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.dictionaries.DictionaryConfig;
import com.rssl.phizic.business.dictionaries.DictionaryDescriptor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.phizic.gate.dictionaries.SynchronizeResult;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshka
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

@IncludeTest(configurations = "sbrf")
public class LoadXMLDictionariesTest extends BusinessTestCaseBase
{
	public void testLoadXmlDictionaries() throws Exception
	{
		initializeRsV51Gate();
		DictionaryConfig dictionaryConfig = ConfigFactory.getConfig(DictionaryConfig.class);
		List<DictionaryDescriptor> dictionaryDescriptors = dictionaryConfig.getDescriptors();
		List<String> names = new ArrayList();
//		names.addAll(dictionaryDescriptors.keySet());
		names.add("Справочник банков");
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("banks.xml");
		SynchronizeDictionariesOperation operation = new SynchronizeDictionariesOperation();
		List<SynchronizeResult> errors = operation.synchronizeXml(names, new ByteArrayInputStream(IOUtils.toByteArray(stream)), "banks.xml", false);
		assertTrue(errors.isEmpty());
	}
}
