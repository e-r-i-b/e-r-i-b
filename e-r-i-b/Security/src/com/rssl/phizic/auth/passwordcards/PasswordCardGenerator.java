package com.rssl.phizic.auth.passwordcards;

/**
 * @author Evgrafov
 * @ created 22.12.2005
 * @ $Author: Evgrafov $
 * @ $Revision: 3375 $
 */

public interface PasswordCardGenerator
{
    void setPasswordCount(int count);

    PasswordCard generate() throws Exception;
}
