package com.rssl.phizic.gate.dictionaries;

import org.xml.sax.XMLReader;

/**
 * @author Kosyakov
 * @ created 22.09.2006
 * @ $Author: kosyakov $
 * @ $Revision: 2231 $
 */
public interface XmlReplicaSource extends ReplicaSource
{
	XMLReader chainXMLReader ( XMLReader xmlReader );
}
