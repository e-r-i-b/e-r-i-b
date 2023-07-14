
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип ответа для интерфейса SrvXferOperStatusInfo. Запрос на уточнение статуса операции из ЕРИБ
 * 
 * <p>Java class for XferOperStatusInfoRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XferOperStatusInfoRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;element name="StatusOriginalRequest" type="{}Status_Type" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="TDDO">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
 *                     &lt;element ref="{}AgreemtInfoClose" minOccurs="0"/>
 *                     &lt;element ref="{}DstCurAmt" minOccurs="0"/>
 *                     &lt;element ref="{}SrcCurAmt" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="TCDO">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{}CardAuthorization"/>
 *                     &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="TDIO">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="TCIO">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{}CardAuthorization"/>
 *                     &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XferOperStatusInfoRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "statusOriginalRequest",
    "tcio",
    "tdio",
    "tcdo",
    "tddo"
})
@XmlRootElement(name = "XferOperStatusInfoRs")
public class XferOperStatusInfoRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "StatusOriginalRequest")
    protected StatusType statusOriginalRequest;
    @XmlElement(name = "TCIO")
    protected XferOperStatusInfoRs.TCIO tcio;
    @XmlElement(name = "TDIO")
    protected XferOperStatusInfoRs.TDIO tdio;
    @XmlElement(name = "TCDO")
    protected XferOperStatusInfoRs.TCDO tcdo;
    @XmlElement(name = "TDDO")
    protected XferOperStatusInfoRs.TDDO tddo;

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
     * Gets the value of the statusOriginalRequest property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatusOriginalRequest() {
        return statusOriginalRequest;
    }

    /**
     * Sets the value of the statusOriginalRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatusOriginalRequest(StatusType value) {
        this.statusOriginalRequest = value;
    }

    /**
     * Gets the value of the tcio property.
     * 
     * @return
     *     possible object is
     *     {@link XferOperStatusInfoRs.TCIO }
     *     
     */
    public XferOperStatusInfoRs.TCIO getTCIO() {
        return tcio;
    }

    /**
     * Sets the value of the tcio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferOperStatusInfoRs.TCIO }
     *     
     */
    public void setTCIO(XferOperStatusInfoRs.TCIO value) {
        this.tcio = value;
    }

    /**
     * Gets the value of the tdio property.
     * 
     * @return
     *     possible object is
     *     {@link XferOperStatusInfoRs.TDIO }
     *     
     */
    public XferOperStatusInfoRs.TDIO getTDIO() {
        return tdio;
    }

    /**
     * Sets the value of the tdio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferOperStatusInfoRs.TDIO }
     *     
     */
    public void setTDIO(XferOperStatusInfoRs.TDIO value) {
        this.tdio = value;
    }

    /**
     * Gets the value of the tcdo property.
     * 
     * @return
     *     possible object is
     *     {@link XferOperStatusInfoRs.TCDO }
     *     
     */
    public XferOperStatusInfoRs.TCDO getTCDO() {
        return tcdo;
    }

    /**
     * Sets the value of the tcdo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferOperStatusInfoRs.TCDO }
     *     
     */
    public void setTCDO(XferOperStatusInfoRs.TCDO value) {
        this.tcdo = value;
    }

    /**
     * Gets the value of the tddo property.
     * 
     * @return
     *     possible object is
     *     {@link XferOperStatusInfoRs.TDDO }
     *     
     */
    public XferOperStatusInfoRs.TDDO getTDDO() {
        return tddo;
    }

    /**
     * Sets the value of the tddo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferOperStatusInfoRs.TDDO }
     *     
     */
    public void setTDDO(XferOperStatusInfoRs.TDDO value) {
        this.tddo = value;
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
     *         &lt;element ref="{}CardAuthorization"/>
     *         &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
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
        "cardAuthorization",
        "agreemtInfo"
    })
    public static class TCDO {

        @XmlElement(name = "CardAuthorization", required = true)
        protected CardAuthorization cardAuthorization;
        @XmlElement(name = "AgreemtInfo", required = true)
        protected AgreemtInfoResponseType agreemtInfo;

        /**
         * Gets the value of the cardAuthorization property.
         * 
         * @return
         *     possible object is
         *     {@link CardAuthorization }
         *     
         */
        public CardAuthorization getCardAuthorization() {
            return cardAuthorization;
        }

        /**
         * Sets the value of the cardAuthorization property.
         * 
         * @param value
         *     allowed object is
         *     {@link CardAuthorization }
         *     
         */
        public void setCardAuthorization(CardAuthorization value) {
            this.cardAuthorization = value;
        }

        /**
         * Gets the value of the agreemtInfo property.
         * 
         * @return
         *     possible object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public AgreemtInfoResponseType getAgreemtInfo() {
            return agreemtInfo;
        }

        /**
         * Sets the value of the agreemtInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public void setAgreemtInfo(AgreemtInfoResponseType value) {
            this.agreemtInfo = value;
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
     *         &lt;element ref="{}CardAuthorization"/>
     *         &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
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
        "cardAuthorization",
        "agreemtInfo"
    })
    public static class TCIO {

        @XmlElement(name = "CardAuthorization", required = true)
        protected CardAuthorization cardAuthorization;
        @XmlElement(name = "AgreemtInfo", required = true)
        protected AgreemtInfoResponseType agreemtInfo;

        /**
         * Gets the value of the cardAuthorization property.
         * 
         * @return
         *     possible object is
         *     {@link CardAuthorization }
         *     
         */
        public CardAuthorization getCardAuthorization() {
            return cardAuthorization;
        }

        /**
         * Sets the value of the cardAuthorization property.
         * 
         * @param value
         *     allowed object is
         *     {@link CardAuthorization }
         *     
         */
        public void setCardAuthorization(CardAuthorization value) {
            this.cardAuthorization = value;
        }

        /**
         * Gets the value of the agreemtInfo property.
         * 
         * @return
         *     possible object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public AgreemtInfoResponseType getAgreemtInfo() {
            return agreemtInfo;
        }

        /**
         * Sets the value of the agreemtInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public void setAgreemtInfo(AgreemtInfoResponseType value) {
            this.agreemtInfo = value;
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
     *         &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
     *         &lt;element ref="{}AgreemtInfoClose" minOccurs="0"/>
     *         &lt;element ref="{}DstCurAmt" minOccurs="0"/>
     *         &lt;element ref="{}SrcCurAmt" minOccurs="0"/>
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
        "agreemtInfo",
        "agreemtInfoClose",
        "dstCurAmt",
        "srcCurAmt"
    })
    public static class TDDO {

        @XmlElement(name = "AgreemtInfo", required = true)
        protected AgreemtInfoResponseType agreemtInfo;
        @XmlElement(name = "AgreemtInfoClose")
        protected AgreemtInfoType agreemtInfoClose;
        @XmlElement(name = "DstCurAmt")
        protected BigDecimal dstCurAmt;
        @XmlElement(name = "SrcCurAmt")
        protected BigDecimal srcCurAmt;

        /**
         * Gets the value of the agreemtInfo property.
         * 
         * @return
         *     possible object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public AgreemtInfoResponseType getAgreemtInfo() {
            return agreemtInfo;
        }

        /**
         * Sets the value of the agreemtInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public void setAgreemtInfo(AgreemtInfoResponseType value) {
            this.agreemtInfo = value;
        }

        /**
         * Gets the value of the agreemtInfoClose property.
         * 
         * @return
         *     possible object is
         *     {@link AgreemtInfoType }
         *     
         */
        public AgreemtInfoType getAgreemtInfoClose() {
            return agreemtInfoClose;
        }

        /**
         * Sets the value of the agreemtInfoClose property.
         * 
         * @param value
         *     allowed object is
         *     {@link AgreemtInfoType }
         *     
         */
        public void setAgreemtInfoClose(AgreemtInfoType value) {
            this.agreemtInfoClose = value;
        }

        /**
         * Gets the value of the dstCurAmt property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getDstCurAmt() {
            return dstCurAmt;
        }

        /**
         * Sets the value of the dstCurAmt property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setDstCurAmt(BigDecimal value) {
            this.dstCurAmt = value;
        }

        /**
         * Gets the value of the srcCurAmt property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSrcCurAmt() {
            return srcCurAmt;
        }

        /**
         * Sets the value of the srcCurAmt property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSrcCurAmt(BigDecimal value) {
            this.srcCurAmt = value;
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
     *         &lt;element name="AgreemtInfo" type="{}AgreemtInfoResponse_Type"/>
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
        "agreemtInfo"
    })
    public static class TDIO {

        @XmlElement(name = "AgreemtInfo", required = true)
        protected AgreemtInfoResponseType agreemtInfo;

        /**
         * Gets the value of the agreemtInfo property.
         * 
         * @return
         *     possible object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public AgreemtInfoResponseType getAgreemtInfo() {
            return agreemtInfo;
        }

        /**
         * Sets the value of the agreemtInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link AgreemtInfoResponseType }
         *     
         */
        public void setAgreemtInfo(AgreemtInfoResponseType value) {
            this.agreemtInfo = value;
        }

    }

}
