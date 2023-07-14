package com.rssl.phizic.logging.system;

/**
 * @author Erkin
 * @ created 31.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ВАЖНО! При перемещении/переименовании класса необходимо поменять
 * константу com.rssl.phizic.logging.system.PhizICLogFactory.LOG_CONSTRUCTOR_IMPL_CLASSNAME
 */
class LogConstructorImpl implements LogConstructor
{
	public Log newInstance(LogModule name)
	{
		return new LogImpl(name);
	}
}
