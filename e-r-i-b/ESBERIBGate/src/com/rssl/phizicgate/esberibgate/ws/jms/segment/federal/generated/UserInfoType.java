
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация о пользователе осуществившим изменения
 * 
 * <p>Java class for UserInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserCategory" type="{}UserCategoryType"/>
 *         &lt;element name="Login" type="{}C" minOccurs="0"/>
 *         &lt;element name="Fio" type="{}C"/>
 *         &lt;element name="BankInfo" type="{}BankInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInfo_Type", propOrder = {
    "userCategory",
    "login",
    "fio",
    "bankInfo"
})
public class UserInfoType {

    @XmlElement(name = "UserCategory", required = true)
    protected UserCategoryType userCategory;
    @XmlElement(name = "Login")
    protected String login;
    @XmlElement(name = "Fio", required = true)
    protected String fio;
    @XmlElement(name = "BankInfo")
    protected BankInfoType bankInfo;

    /**
     * Gets the value of the userCategory property.
     * 
     * @return
     *     possible object is
     *     {@link UserCategoryType }
     *     
     */
    public UserCategoryType getUserCategory() {
        return userCategory;
    }

    /**
     * Sets the value of the userCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserCategoryType }
     *     
     */
    public void setUserCategory(UserCategoryType value) {
        this.userCategory = value;
    }

    /**
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the value of the login property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Gets the value of the fio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFio() {
        return fio;
    }

    /**
     * Sets the value of the fio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFio(String value) {
        this.fio = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoType }
     *     
     */
    public BankInfoType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoType }
     *     
     */
    public void setBankInfo(BankInfoType value) {
        this.bankInfo = value;
    }

}
