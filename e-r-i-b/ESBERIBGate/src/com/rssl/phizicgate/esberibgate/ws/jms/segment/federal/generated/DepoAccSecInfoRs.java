
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Получение информации о содержании счета ДЕПО
 * 
 * <p>Java class for DepoAccSecInfoRsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoAccSecInfoRsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status" minOccurs="0"/>
 *         &lt;element name="DepoAccSecInfoRec">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}DepoAcctId"/>
 *                   &lt;element name="DepoRec" type="{}DepoSecurityRec_Type" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element ref="{}Status"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoAccSecInfoRsType", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "depoAccSecInfoRec"
})
@XmlRootElement(name = "DepoAccSecInfoRs")
public class DepoAccSecInfoRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status")
    protected StatusType status;
    @XmlElement(name = "DepoAccSecInfoRec", required = true)
    protected DepoAccSecInfoRs.DepoAccSecInfoRec depoAccSecInfoRec;

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
    public String getRqTm() {
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
    public void setRqTm(String value) {
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the depoAccSecInfoRec property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccSecInfoRs.DepoAccSecInfoRec }
     *     
     */
    public DepoAccSecInfoRs.DepoAccSecInfoRec getDepoAccSecInfoRec() {
        return depoAccSecInfoRec;
    }

    /**
     * Sets the value of the depoAccSecInfoRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccSecInfoRs.DepoAccSecInfoRec }
     *     
     */
    public void setDepoAccSecInfoRec(DepoAccSecInfoRs.DepoAccSecInfoRec value) {
        this.depoAccSecInfoRec = value;
    }


    /**
     * Счета депо
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}DepoAcctId"/>
     *         &lt;element name="DepoRec" type="{}DepoSecurityRec_Type" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element ref="{}Status"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "depoAcctId",
        "depoRecs",
        "status"
    })
    public static class DepoAccSecInfoRec {

        @XmlElement(name = "DepoAcctId", required = true)
        protected DepoAcctId depoAcctId;
        @XmlElement(name = "DepoRec")
        protected List<DepoSecurityRecType> depoRecs;
        @XmlElement(name = "Status", required = true)
        protected StatusType status;

        /**
         * Gets the value of the depoAcctId property.
         * 
         * @return
         *     possible object is
         *     {@link DepoAcctId }
         *     
         */
        public DepoAcctId getDepoAcctId() {
            return depoAcctId;
        }

        /**
         * Sets the value of the depoAcctId property.
         * 
         * @param value
         *     allowed object is
         *     {@link DepoAcctId }
         *     
         */
        public void setDepoAcctId(DepoAcctId value) {
            this.depoAcctId = value;
        }

        /**
         * Gets the value of the depoRecs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the depoRecs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDepoRecs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DepoSecurityRecType }
         * 
         * 
         */
        public List<DepoSecurityRecType> getDepoRecs() {
            if (depoRecs == null) {
                depoRecs = new ArrayList<DepoSecurityRecType>();
            }
            return this.depoRecs;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link StatusType }
         *     
         */
        public StatusType getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link StatusType }
         *     
         */
        public void setStatus(StatusType value) {
            this.status = value;
        }

    }

}
