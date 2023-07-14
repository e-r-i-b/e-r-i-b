package com.rssl.phizic.test.mbvmock;

/**
 * User: Moshenko
 * Date: 14.09.13
 * Time: 14:52
 * объект запроса DiscByPhoneRq.
 */
public class DiscByPhone
{
    private Long id;
    /**
     * Номер телефона
     */
    private String phoneNumber;
    /**
     * @return код возврата
     */
    private String retCode;
    /**
     * Дополнительная информация о результате операции.
     */
    private String resultMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
