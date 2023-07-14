package com.rssl.phizic.test.mbvmock;

/**
 * User: Moshenko
 * Date: 11.09.13
 * Time: 16:35
 * Счета  для ClinetAcc
 */
public class ClinetAcc
{
    private Long id;
    /**
     * Номер счета
     */
    private String accNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }
}
