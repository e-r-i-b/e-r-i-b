package com.rssl.phizic.business.operations.builder;

import net.sf.cglib.proxy.NoOp;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 11.09.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обертка для того чтоб сделать Serializable
 */
public interface NoOpWraper extends NoOp, Serializable
{
	public static final NoOpWraper INSTANCE = new NoOpWraper() { };
}