// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.dictionaries.generated;

public class Bank
{
    protected String BIC;
    protected String account;
    protected Long id;
    protected String name;
    protected String place;
    protected String shortName;
    protected String synchKey;

    public Bank() {
    }

    public Bank(String BIC, String account, Long id, String name, String place, String shortName, String synchKey) {
        this.BIC = BIC;
        this.account = account;
        this.id = id;
        this.name = name;
        this.place = place;
        this.shortName = shortName;
        this.synchKey = synchKey;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSynchKey() {
        return synchKey;
    }

    public void setSynchKey(String synchKey) {
        this.synchKey = synchKey;
    }
}