package com.rssl.phizic.test.mbvmock;


import java.util.Set;

/**
 * User: Moshenko
 * Date: 13.09.13
 * Time: 15:44
 * объект запроса cetClientByPhoneRq.
 */
public class GetClientByPhone
{
    private Long id;
    /**
     * @return код возврата
     */
    private String retCode;
    /**
     * Номер телефона
     */
    private String phoneNumber;

    /**
     * данные по найденным клиентам  запроса GetClientByPhoneRq
     */
    private Set<GetClientByPhoneClientIdintity> clientIdentity;
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

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<GetClientByPhoneClientIdintity> getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(Set<GetClientByPhoneClientIdintity> clientIdentity) {
        this.clientIdentity = clientIdentity;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
