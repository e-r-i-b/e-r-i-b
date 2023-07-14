package com.rssl.phizicgate.mdm.integration.mdm;

import com.rssl.phizicgate.mdm.integration.mdm.generated.ObjectFactory;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Интеграционная информация по сегменту мдм
 */

public class MDMSegmentInfo
{
	public static final String FACTORY_NAME              = "jms/esb/mdm/queueFactory";
	public static final String IN_QUEUE_NAME             = "jms/esb/mdm/inQueue";
	public static final String TICKET_QUEUE_NAME         = "jms/esb/mdm/ticketQueue";
	public static final String OUT_ONLINE_QUEUE_NAME     = "jms/esb/mdm/onlineOutQueue";
	public static final String XSD_SCHEMA_NAME           = "com/rssl/phizicgate/mdm/integration/mdm/MDMAdapter.xsd";
	public static final String JAXB_CLASSES_PACKAGE_NAME = ObjectFactory.class.getPackage().getName();
}
