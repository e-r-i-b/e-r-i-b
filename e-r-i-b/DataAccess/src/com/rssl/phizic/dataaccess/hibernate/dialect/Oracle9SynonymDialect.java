package com.rssl.phizic.dataaccess.hibernate.dialect;

import org.hibernate.dialect.Oracle9Dialect;

/**
 * @author Omeliyanchuk
 * @ created 27.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class Oracle9SynonymDialect extends Oracle9Dialect 
{
	public Oracle9SynonymDialect() {
	    super();
	}

	public String getQuerySequencesString() {
	    return "select sequence_name from user_sequences "
	        + "union "
	        + "select synonym_name from user_synonyms us "
	        + "where exists (select 1 from all_objects ao where object_type='SEQUENCE' and "
	        + "us.table_name = ao.object_name)";
	}

}
