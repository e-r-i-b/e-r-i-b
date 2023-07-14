package com.rssl.phizic.web.modulus;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author Erkin
 * @ created 31.05.2012
 * @ $Author$
 * @ $Revision$
 */
class WebPath
{
	private static final char PATH_SEPARATOR = '/';

	private final String[] nodes;

	///////////////////////////////////////////////////////////////////////////

	private WebPath(String[] nodes)
	{
		this.nodes = nodes;
	}

	WebPath(String pathString)
	{
		if (StringHelper.isEmpty(pathString))
			throw new IllegalArgumentException("Аргумент 'pathString' не может быть пустым");

		nodes = StringUtils.split(pathString, PATH_SEPARATOR);
	}

	int depth()
	{
		return nodes.length;
	}

	boolean startsWith(WebPath path)
	{
		if (path.nodes.length > this.nodes.length)
			return false;

		for (int i=0; i<path.nodes.length; i++)
		{
			String myNode = this.nodes[i];
			String hisNode = path.nodes[i];
			if (!myNode.equalsIgnoreCase(hisNode))
				return false;
		}

		return true;
	}

	/**
	 * Возвращает подпуть, начиная с заданной глубины
	 * @param start
	 * @return
	 */
	WebPath subpath(int start)
	{
		if (start<0 || start>=nodes.length)
			throw new IndexOutOfBoundsException();
		if (start == 0)
			return this;
		
		String[] subnodes = (String[]) ArrayUtils.subarray(nodes, start, nodes.length);
		return new WebPath(subnodes);
	}

	public String toString()
	{
		// Путь всегда начинается со слеша
		return PATH_SEPARATOR + StringUtils.join(nodes, PATH_SEPARATOR);
	}
}
