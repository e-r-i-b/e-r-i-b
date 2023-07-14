package com.rssl.phizic.web.util;

import com.rssl.phizic.common.types.collection.LinkedListStack;
import com.rssl.phizic.common.types.collection.Stack;

import java.io.Serializable;

/**
 * @author sergunin
 * @ created 20.05.15
 * @ $Author$
 * @ $Revision$
 */

public class TokenStack implements Serializable
{
    private Stack<String> stack;

    public TokenStack()
    {
        stack = new LinkedListStack<String>();
    }

    public void push(String elem)
    {
        stack.push(elem);
    }

    public String getPreLast()
    {
	    if (stack.isEmpty())
		    return null;
        return stack.pop();
    }

}
