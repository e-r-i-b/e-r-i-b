package com.rssl.phizic.utils.files;

import java.io.IOException;
import java.io.File;

/**
 * @author Roshka
 * @ created 16.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class FolderTreeScaner
{
	private String path;

	public FolderTreeScaner(String path)
	{
		this.path = path;
	}

	public void scan(FolderScanAction action) throws IOException
	{
		scan(new File(path), action);
	}

	/**
	 * Рекурсия по папкам и вызов пользовательского действия.
	 * @param current
	 * @param action
	 * @throws java.io.IOException
	 */
	private void scan(File current, FolderScanAction action) throws IOException
	{
		if (current.isDirectory())
		{
			// Act somthing
			action.execute(current);

			// Next directories
			File[] children = current.listFiles();

			for (File f : children)
				scan(f, action);
		}
	}
}