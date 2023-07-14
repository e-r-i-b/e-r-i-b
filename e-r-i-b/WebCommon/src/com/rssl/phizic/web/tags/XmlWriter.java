package com.rssl.phizic.web.tags;

import java.io.IOException;
import java.util.Stack;
import javax.servlet.jsp.JspWriter;

/**
 * @author Erkin
* @ created 09.11.2011
* @ $Author$
* @ $Revision$
*/
class XmlWriter
{
	private final JspWriter out;

	private final Stack<String> tagStack = new Stack<String>();

	XmlWriter(JspWriter out)
	{
		this.out = out;
	}

	<T> void printTag(String tag, T value) throws IOException
	{
		out.print("<" + tag + ">");
		out.print(value);
		out.println("</" + tag + ">");
	}

	void openTag(String tag) throws IOException
	{
		out.println("<" + tagStack.push(tag) + ">");
	}

	void closeTag() throws IOException
	{
		out.println("</" + tagStack.pop() + ">");
	}
}
