package com.rssl.phizic.business.forms;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;

/**
 * @author Kidyaev
 * @ created 19.02.2007
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
@ExcludeTest(configurations = "russlav")
public class AccountClosingClaimMetadataLoaderTest extends BusinessTestCaseBase
{
/*
	public void test()
	{
		ClaimMetadataLoader loader              = new ClaimMetadataLoader();
		Metadata            metadata            = loader.load("AccountClosingClaim");
		AccountClosingClaim accountClosingClaim = (AccountClosingClaim) metadata.createDocument();

		AccountClosingClaimMetadataLoader extendedLoader    = new AccountClosingClaimMetadataLoader();
		ExtendedMetadata                  extendedMetadata1 = (ExtendedMetadata) extendedLoader.load(metadata, accountClosingClaim);

		checkAssertions(extendedMetadata1);

		ExtendedMetadata extendedMetadata2 = (ExtendedMetadata) extendedLoader.load(metadata, accountClosingClaim);
		checkAssertions(extendedMetadata2);

		List<Field> fields1 = extendedMetadata1.getExtendedForm().getFields();
		List<Field> fields2 = extendedMetadata2.getExtendedForm().getFields();

		assertTrue(fields2.size() > fields1.size() );
	}
*/

	private void checkAssertions(ExtendedMetadata extendedMetadata)
	{
		assertNotNull(extendedMetadata);
		assertNotNull(extendedMetadata.getName());
		assertNotNull(extendedMetadata.getExtendedForm());
		assertNotNull(extendedMetadata.getDictionaries());
		assertNotNull(extendedMetadata.createConverter("html"));
		assertNotNull(extendedMetadata.createDocument());
	}
}
