
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип ответного сообщения для интерфейса CEDBO - Проверка заключения УДБО клиентом
 * 
 * <p>Java class for CustAgreemtInqRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustAgreemtInqRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;element ref="{}CustAgreemtRec" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustAgreemtInqRs_Type", propOrder = {
    "rqUID",
    "status",
    "custAgreemtRec"
})
@XmlRootElement(name = "CustAgreemtInqRs")
public class CustAgreemtInqRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "CustAgreemtRec")
    protected CustAgreemtRec custAgreemtRec;

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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the custAgreemtRec property.
     * 
     * @return
     *     possible object is
     *     {@link CustAgreemtRec }
     *     
     */
    public CustAgreemtRec getCustAgreemtRec() {
        return custAgreemtRec;
    }

    /**
     * Sets the value of the custAgreemtRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustAgreemtRec }
     *     
     */
    public void setCustAgreemtRec(CustAgreemtRec value) {
        this.custAgreemtRec = value;
    }

}
