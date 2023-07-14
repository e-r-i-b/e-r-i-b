package com.rssl.phizic.common.types.collection;

import java.util.Collection;
import java.util.EmptyStackException;

/**
 * @author Erkin
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Стек
 */
public interface Stack<E> extends Collection<E>
{
	void push(E elem);

	E pop() throws EmptyStackException;

	E peek() throws EmptyStackException;
}
