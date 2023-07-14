
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Информация по счету депо
 * 
 * <p>Java class for DepoAcctInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoAcctInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{}String" minOccurs="0"/>
 *         &lt;element name="isOperationAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtNum" minOccurs="0"/>
 *         &lt;element ref="{}StartDt" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoAcctInfo_Type", propOrder = {
    "status",
    "isOperationAllowed",
    "agreemtNum",
    "startDt",
    "bankInfo"
})
public class DepoAcctInfoType {

    @XmlElement(name = "Status")
    protected String status;
    protected Boolean isOperationAllowed;
    @XmlElement(name = "AgreemtNum")
    protected String agreemtNum;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfo bankInfo;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the isOperationAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsOperationAllowed() {
        return isOperationAllowed;
    }

    /**
     * Sets the value of the isOperationAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOperationAllowed(Boolean value) {
        this.isOperationAllowed = value;
    }

    /**
     * Gets the value of the agreemtNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreemtNum() {
        return agreemtNum;
    }

    /**
     * Sets the value of the agreemtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreemtNum(String value) {
        this.agreemtNum = value;
    }

    /**
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDt(Calendar value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfo }
     *     
     */
    public BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfo }
     *     
     */
    public void setBankInfo(BankInfo value) {
        this.bankInfo = value;
    }

}
