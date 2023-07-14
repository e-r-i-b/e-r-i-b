package com.rssl.phizic.web.gate.services.documents.types;

/**
 * @author egorova
 * @ created 25.05.2009
 * @ $Author$
 * @ $Revision$
 */
public interface Office
{
    Code getCode();
    void setCode(Code code);

	void setSynchKey(String synchKey);
    String getSynchKey();

	String getBIC();
    void setBIC(String BIC);

    String getAddress();
    void setAddress(String address);

    String getPostIndex();
    void setPostIndex(String postIndex);

    String getTelephone();
    void setTelephone(String telephone);

    String getName ();
    void setName ( String name );

	String getSbidnt();
    void setSbidnt( String name );

}
