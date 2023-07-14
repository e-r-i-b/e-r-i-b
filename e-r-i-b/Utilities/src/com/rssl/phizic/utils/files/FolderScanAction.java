package com.rssl.phizic.utils.files;

import java.io.File;
import java.io.IOException;

/**
 * @author Roshka
 * @ created 16.05.2006
 * @ $Author$
 * @ $Revision$
 */

public interface FolderScanAction
{
	void execute(File currentDir) throws IOException;
}