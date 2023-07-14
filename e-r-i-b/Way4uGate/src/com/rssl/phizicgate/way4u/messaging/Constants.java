package com.rssl.phizicgate.way4u.messaging;

/**
 * @author krenev
 * @ created 08.10.2013
 * @ $Author$
 * @ $Revision$
 */

public class Constants
{
	public static final String INPUT_QUEUE_CONNECTION_FACTORY_NAME = "jms/erib/way4u/InputQCF"; //Фабрика соединений входящей очереди(очереди запросов)
	public static final String INPUT_QUEUE_NAME = "jms/erib/way4u/InputQueue"; //Входящая очередь(очередь запросов)
	public static final String OUTPUT_QUEUE_CONNECTION_FACTORY_NAME = "jms/erib/way4u/OutputQCF"; //Фабрика соединений исходящей очереди(очереди ответов)
	public static final String OUTPUT_QUEUE_NAME = "jms/erib/way4u/OutputQueue"; //Исходящая очередь(очередь ответов)
}

