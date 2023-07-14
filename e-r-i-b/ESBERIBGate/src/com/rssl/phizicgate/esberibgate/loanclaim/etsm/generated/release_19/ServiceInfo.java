
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for ServiceInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProdType" type="{}String"/>
 *         &lt;element name="RegionNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="BranchNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="AgencyNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="AgreementNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="StartDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="EndDt" type="{}Date" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceInfo_Type", propOrder = {
    "prodType",
    "regionNum",
    "branchNum",
    "agencyNum",
    "agreementNum",
    "startDt",
    "endDt",
    "bankInfo",
    "status"
})
@XmlRootElement(name = "ServiceInfo")
public class ServiceInfo {

    @XmlElement(name = "ProdType", required = true)
    protected String prodType;
    @XmlElement(name = "RegionNum")
    protected String regionNum;
    @XmlElement(name = "BranchNum")
    protected String branchNum;
    @XmlElement(name = "AgencyNum")
    protected String agencyNum;
    @XmlElement(name = "AgreementNum")
    protected String agreementNum;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "EndDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar endDt;
    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;
    @XmlElement(name = "Status")
    protected String status;

    /**
     * Gets the value of the prodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdType() {
        return prodType;
    }

    /**
     * Sets the value of the prodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdType(String value) {
        this.prodType = value;
    }

    /**
     * Gets the value of the regionNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionNum() {
        return regionNum;
    }

    /**
     * Sets the value of the regionNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionNum(String value) {
        this.regionNum = value;
    }

    /**
     * Gets the value of the branchNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchNum() {
        return branchNum;
    }

    /**
     * Sets the value of the branchNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchNum(String value) {
        this.branchNum = value;
    }

    /**
     * Gets the value of the agencyNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyNum() {
        return agencyNum;
    }

    /**
     * Sets the value of the agencyNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyNum(String value) {
        this.agencyNum = value;
    }

    /**
     * Gets the value of the agreementNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementNum() {
        return agreementNum;
    }

    /**
     * Sets the value of the agreementNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementNum(String value) {
        this.agreementNum = value;
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
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDt(Calendar value) {
        this.endDt = value;
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

}
