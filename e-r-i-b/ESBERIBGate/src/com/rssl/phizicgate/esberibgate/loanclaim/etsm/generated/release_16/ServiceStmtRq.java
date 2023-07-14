
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


/**
 * Запрос интерфейса ESK_SAS получения графика исполнения длительного поручения
 * 
 * <p>Java class for ServiceStmtRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceStmtRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}SvcAcctId"/>
 *         &lt;element name="DtBegin" type="{}Date"/>
 *         &lt;element name="DtEnd" type="{}Date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceStmtRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "bankInfo",
    "svcAcctId",
    "dtBegin",
    "dtEnd"
})
@XmlRootElement(name = "ServiceStmtRq")
public class ServiceStmtRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfo bankInfo;
    @XmlElement(name = "SvcAcctId", required = true)
    protected SvcAcctId svcAcctId;
    @XmlElement(name = "DtBegin", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dtBegin;
    @XmlElement(name = "DtEnd", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dtEnd;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(Calendar value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
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
     * Gets the value of the svcAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link SvcAcctId }
     *     
     */
    public SvcAcctId getSvcAcctId() {
        return svcAcctId;
    }

    /**
     * Sets the value of the svcAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcAcctId }
     *     
     */
    public void setSvcAcctId(SvcAcctId value) {
        this.svcAcctId = value;
    }

    /**
     * Gets the value of the dtBegin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDtBegin() {
        return dtBegin;
    }

    /**
     * Sets the value of the dtBegin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtBegin(Calendar value) {
        this.dtBegin = value;
    }

    /**
     * Gets the value of the dtEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDtEnd() {
        return dtEnd;
    }

    /**
     * Sets the value of the dtEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtEnd(Calendar value) {
        this.dtEnd = value;
    }

}
