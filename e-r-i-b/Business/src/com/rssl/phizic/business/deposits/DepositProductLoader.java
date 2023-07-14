package com.rssl.phizic.business.deposits;

import com.rssl.phizic.utils.xml.XmlFileReader;
import com.rssl.phizic.utils.files.FolderTreeScaner;
import com.rssl.phizic.utils.files.FolderScanAction;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Загрузчик описаний депозитных продуктов
 * @author Kidyaev
 * @ created 05.05.2006
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductLoader
{
    private static final String DESCRIPTION_FILE_NAME = "description.xml";
	private static final String DETAILS_FILE_NAME = "details.xslt";

    private List<DepositProduct> products;

	private FolderTreeScaner scaner;

	public DepositProductLoader(String path)
    {
	    scaner = new FolderTreeScaner(path);
    }

	/**
	 * Прочитать список депозитных продуктов.
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 */
	public List<DepositProduct> load() throws IOException, SAXException
    {
        products = new ArrayList<DepositProduct>();
	    scaner.scan(new FolderScanAction()
	    {
		    public void execute(File currentDir) throws IOException
		    {
			    DepositProduct product;
			    try
			    {
				    product = read(currentDir);
			    }
			    catch (SAXException e)
			    {
				    throw new RuntimeException(e);
			    }

			    if ( product != null )
				     products.add( product );
		    }
	    }
	    );

	    return products;
    }

    private DepositProduct read(File currentDir) throws IOException, SAXException
    {
        File descriptionFile = readFile(currentDir, DESCRIPTION_FILE_NAME);
	    File detailsFile = readFile(currentDir, DETAILS_FILE_NAME);

	    if (( descriptionFile == null )||(detailsFile==null))
            return null;

        DepositProduct product = new DepositProduct();
        product.setName( currentDir.getName() );
        product.setDescription( new XmlFileReader(descriptionFile).readString() );
	    product.setDetails(new XmlFileReader(detailsFile).readString());

	    return product;
    }

    private File readFile(File currentDir, final String fileName)
    {
        File[] files = currentDir.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.equals(fileName);
            }
        });

        return files.length != 0 ? files[0] : null;
    }

}
