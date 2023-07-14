
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Расшифровка задолженности на счете ДЕПО
 * 
 * <p>Java class for DepoDeptsInfoRsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoDeptsInfoRsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status" minOccurs="0"/>
 *         &lt;element name="DepoAcctDeptInfoRec">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DepoDeptRes" type="{}DepoDeptRes_Type"/>
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
@XmlType(name = "DepoDeptsInfoRsType", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "depoAcctDeptInfoRec"
})
@XmlRootElement(name = "DepoDeptsInfoRs")
public class DepoDeptsInfoRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status")
    protected StatusType status;
    @XmlElement(name = "DepoAcctDeptInfoRec", required = true)
    protected DepoDeptsInfoRs.DepoAcctDeptInfoRec depoAcctDeptInfoRec;

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
     * Gets the value of the depoAcctDeptInfoRec property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDeptsInfoRs.DepoAcctDeptInfoRec }
     *     
     */
    public DepoDeptsInfoRs.DepoAcctDeptInfoRec getDepoAcctDeptInfoRec() {
        return depoAcctDeptInfoRec;
    }

    /**
     * Sets the value of the depoAcctDeptInfoRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDeptsInfoRs.DepoAcctDeptInfoRec }
     *     
     */
    public void setDepoAcctDeptInfoRec(DepoDeptsInfoRs.DepoAcctDeptInfoRec value) {
        this.depoAcctDeptInfoRec = value;
    }


    /**
     * Запись о счете депо
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
     *         &lt;element name="DepoDeptRes" type="{}DepoDeptRes_Type"/>
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
        "depoDeptRes"
    })
    public static class DepoAcctDeptInfoRec {

        @XmlElement(name = "DepoDeptRes", required = true)
        protected DepoDeptResType depoDeptRes;

        /**
         * Gets the value of the depoDeptRes property.
         * 
         * @return
         *     possible object is
         *     {@link DepoDeptResType }
         *     
         */
        public DepoDeptResType getDepoDeptRes() {
            return depoDeptRes;
        }

        /**
         * Sets the value of the depoDeptRes property.
         * 
         * @param value
         *     allowed object is
         *     {@link DepoDeptResType }
         *     
         */
        public void setDepoDeptRes(DepoDeptResType value) {
            this.depoDeptRes = value;
        }

    }

}
