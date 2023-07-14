
package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeadResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeadResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MessUID">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MessageDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="FromAbonent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OperUID" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OperId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="STAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="LTDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MessType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RequestType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TargetSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Error">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ErrCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ErrMes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="parentId" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MessageDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="FromAbonent" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "HeadResponseType", propOrder = {
    "messUID",
    "operUID",
    "messType",
    "version",
    "requestType",
    "targetSystem",
    "error",
    "parentId"
})
public class HeadResponseType {

    @XmlElement(name = "MessUID", required = true)
    protected HeadResponseType.MessUID messUID;
    @XmlElement(name = "OperUID")
    protected HeadResponseType.OperUID operUID;
    @XmlElement(name = "MessType", required = true)
    protected String messType;
    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(name = "RequestType")
    protected String requestType;
    @XmlElement(name = "TargetSystem")
    protected String targetSystem;
    @XmlElement(name = "Error", required = true)
    protected HeadResponseType.Error error;
    protected HeadResponseType.ParentId parentId;

    /**
     * Gets the value of the messUID property.
     * 
     * @return
     *     possible object is
     *     {@link HeadResponseType.MessUID }
     *     
     */
    public HeadResponseType.MessUID getMessUID() {
        return messUID;
    }

    /**
     * Sets the value of the messUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadResponseType.MessUID }
     *     
     */
    public void setMessUID(HeadResponseType.MessUID value) {
        this.messUID = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link HeadResponseType.OperUID }
     *     
     */
    public HeadResponseType.OperUID getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadResponseType.OperUID }
     *     
     */
    public void setOperUID(HeadResponseType.OperUID value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the messType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessType() {
        return messType;
    }

    /**
     * Sets the value of the messType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessType(String value) {
        this.messType = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestType(String value) {
        this.requestType = value;
    }

    /**
     * Gets the value of the targetSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetSystem() {
        return targetSystem;
    }

    /**
     * Sets the value of the targetSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetSystem(String value) {
        this.targetSystem = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link HeadResponseType.Error }
     *     
     */
    public HeadResponseType.Error getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadResponseType.Error }
     *     
     */
    public void setError(HeadResponseType.Error value) {
        this.error = value;
    }

    /**
     * Gets the value of the parentId property.
     * 
     * @return
     *     possible object is
     *     {@link HeadResponseType.ParentId }
     *     
     */
    public HeadResponseType.ParentId getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the parentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadResponseType.ParentId }
     *     
     */
    public void setParentId(HeadResponseType.ParentId value) {
        this.parentId = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ErrCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ErrMes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "errCode",
        "errMes"
    })
    public static class Error {

        @XmlElement(name = "ErrCode", required = true)
        protected String errCode;
        @XmlElement(name = "ErrMes")
        protected String errMes;

        /**
         * Gets the value of the errCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getErrCode() {
            return errCode;
        }

        /**
         * Sets the value of the errCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setErrCode(String value) {
            this.errCode = value;
        }

        /**
         * Gets the value of the errMes property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getErrMes() {
            return errMes;
        }

        /**
         * Sets the value of the errMes property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setErrMes(String value) {
            this.errMes = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MessageDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="FromAbonent" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "messageId",
        "messageDate",
        "fromAbonent"
    })
    public static class MessUID {

        @XmlElement(name = "MessageId", required = true)
        protected String messageId;
        @XmlElement(name = "MessageDate", required = true)
        protected String messageDate;
        @XmlElement(name = "FromAbonent", required = true)
        protected String fromAbonent;

        /**
         * Gets the value of the messageId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageId() {
            return messageId;
        }

        /**
         * Sets the value of the messageId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageId(String value) {
            this.messageId = value;
        }

        /**
         * Gets the value of the messageDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageDate() {
            return messageDate;
        }

        /**
         * Sets the value of the messageDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageDate(String value) {
            this.messageDate = value;
        }

        /**
         * Gets the value of the fromAbonent property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFromAbonent() {
            return fromAbonent;
        }

        /**
         * Sets the value of the fromAbonent property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFromAbonent(String value) {
            this.fromAbonent = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="OperId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="STAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="LTDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "operId",
        "stan",
        "ltdt",
        "rrn"
    })
    public static class OperUID {

        @XmlElement(name = "OperId")
        protected String operId;
        @XmlElement(name = "STAN")
        protected String stan;
        @XmlElement(name = "LTDT")
        protected String ltdt;
        @XmlElement(name = "RRN")
        protected String rrn;

        /**
         * Gets the value of the operId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOperId() {
            return operId;
        }

        /**
         * Sets the value of the operId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOperId(String value) {
            this.operId = value;
        }

        /**
         * Gets the value of the stan property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSTAN() {
            return stan;
        }

        /**
         * Sets the value of the stan property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSTAN(String value) {
            this.stan = value;
        }

        /**
         * Gets the value of the ltdt property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLTDT() {
            return ltdt;
        }

        /**
         * Sets the value of the ltdt property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLTDT(String value) {
            this.ltdt = value;
        }

        /**
         * Gets the value of the rrn property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRRN() {
            return rrn;
        }

        /**
         * Sets the value of the rrn property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRRN(String value) {
            this.rrn = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MessageDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="FromAbonent" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "messageId",
        "messageDate",
        "fromAbonent"
    })
    public static class ParentId {

        @XmlElement(name = "MessageId", required = true)
        protected String messageId;
        @XmlElement(name = "MessageDate", required = true)
        protected String messageDate;
        @XmlElement(name = "FromAbonent", required = true)
        protected String fromAbonent;

        /**
         * Gets the value of the messageId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageId() {
            return messageId;
        }

        /**
         * Sets the value of the messageId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageId(String value) {
            this.messageId = value;
        }

        /**
         * Gets the value of the messageDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageDate() {
            return messageDate;
        }

        /**
         * Sets the value of the messageDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageDate(String value) {
            this.messageDate = value;
        }

        /**
         * Gets the value of the fromAbonent property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFromAbonent() {
            return fromAbonent;
        }

        /**
         * Sets the value of the fromAbonent property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFromAbonent(String value) {
            this.fromAbonent = value;
        }

    }

}
