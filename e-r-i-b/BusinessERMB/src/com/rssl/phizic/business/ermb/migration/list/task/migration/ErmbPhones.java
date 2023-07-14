package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Puzikov
 * @ created 03.12.14
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings("PackageVisibleField")
class ErmbPhones
{
	Phone active;
	Set<Phone> phones = new HashSet<Phone>();
}
