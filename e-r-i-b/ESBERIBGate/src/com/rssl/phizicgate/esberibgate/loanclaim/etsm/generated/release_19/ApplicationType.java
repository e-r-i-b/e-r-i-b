
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип - заявка
 * 
 * <p>Java class for Application_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Application_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Unit">
 *           &lt;simpleType>
 *             &lt;restriction base="{}String">
 *               &lt;length value="11"/>
 *               &lt;pattern value="\d{11}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LoginKI" type="{}Login_Type" minOccurs="0"/>
 *         &lt;element name="LoginTM" type="{}Login_Type" minOccurs="0"/>
 *         &lt;element name="FullnameTM" type="{}Fullname_Type" minOccurs="0"/>
 *         &lt;element name="CompleteAppFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Channel" type="{}Chanel_Type"/>
 *         &lt;element name="ChannelCBRegAApprove" type="{}ChannelCBRegAApprove_Type" minOccurs="0"/>
 *         &lt;element name="ChannelPFRRegAApprove" type="{}ChannelPFRRegAApprove_Type" minOccurs="0"/>
 *         &lt;element name="Product" type="{}ProductData_Type"/>
 *         &lt;element name="InterestRate" type="{}Rate_Type" minOccurs="0"/>
 *         &lt;element name="Applicant" type="{}ApplicantData_Type"/>
 *         &lt;element name="TopUpList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RepayLoanListCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="RepayLoanList">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="RepayLoan" type="{}RepayLoan_Type" maxOccurs="5"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "Application_Type", propOrder = {
    "unit",
    "loginKI",
    "loginTM",
    "fullnameTM",
    "completeAppFlag",
    "channel",
    "channelCBRegAApprove",
    "channelPFRRegAApprove",
    "product",
    "interestRate",
    "applicant",
    "topUpList"
})
public class ApplicationType {

    @XmlElement(name = "Unit", required = true)
    protected String unit;
    @XmlElement(name = "LoginKI")
    protected String loginKI;
    @XmlElement(name = "LoginTM")
    protected String loginTM;
    @XmlElement(name = "FullnameTM")
    protected String fullnameTM;
    @XmlElement(name = "CompleteAppFlag")
    protected Boolean completeAppFlag;
    @XmlElement(name = "Channel", required = true)
    protected String channel;
    @XmlElement(name = "ChannelCBRegAApprove")
    protected String channelCBRegAApprove;
    @XmlElement(name = "ChannelPFRRegAApprove")
    protected String channelPFRRegAApprove;
    @XmlElement(name = "Product", required = true)
    protected ProductDataType product;
    @XmlElement(name = "InterestRate")
    protected BigDecimal interestRate;
    @XmlElement(name = "Applicant", required = true)
    protected ApplicantDataType applicant;
    @XmlElement(name = "TopUpList")
    protected ApplicationType.TopUpList topUpList;

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the loginKI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginKI() {
        return loginKI;
    }

    /**
     * Sets the value of the loginKI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginKI(String value) {
        this.loginKI = value;
    }

    /**
     * Gets the value of the loginTM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginTM() {
        return loginTM;
    }

    /**
     * Sets the value of the loginTM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginTM(String value) {
        this.loginTM = value;
    }

    /**
     * Gets the value of the fullnameTM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullnameTM() {
        return fullnameTM;
    }

    /**
     * Sets the value of the fullnameTM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullnameTM(String value) {
        this.fullnameTM = value;
    }

    /**
     * Gets the value of the completeAppFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getCompleteAppFlag() {
        return completeAppFlag;
    }

    /**
     * Sets the value of the completeAppFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCompleteAppFlag(Boolean value) {
        this.completeAppFlag = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the channelCBRegAApprove property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelCBRegAApprove() {
        return channelCBRegAApprove;
    }

    /**
     * Sets the value of the channelCBRegAApprove property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelCBRegAApprove(String value) {
        this.channelCBRegAApprove = value;
    }

    /**
     * Gets the value of the channelPFRRegAApprove property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelPFRRegAApprove() {
        return channelPFRRegAApprove;
    }

    /**
     * Sets the value of the channelPFRRegAApprove property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelPFRRegAApprove(String value) {
        this.channelPFRRegAApprove = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductDataType }
     *     
     */
    public ProductDataType getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductDataType }
     *     
     */
    public void setProduct(ProductDataType value) {
        this.product = value;
    }

    /**
     * Gets the value of the interestRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the value of the interestRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInterestRate(BigDecimal value) {
        this.interestRate = value;
    }

    /**
     * Gets the value of the applicant property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantDataType }
     *     
     */
    public ApplicantDataType getApplicant() {
        return applicant;
    }

    /**
     * Sets the value of the applicant property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantDataType }
     *     
     */
    public void setApplicant(ApplicantDataType value) {
        this.applicant = value;
    }

    /**
     * Gets the value of the topUpList property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationType.TopUpList }
     *     
     */
    public ApplicationType.TopUpList getTopUpList() {
        return topUpList;
    }

    /**
     * Sets the value of the topUpList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationType.TopUpList }
     *     
     */
    public void setTopUpList(ApplicationType.TopUpList value) {
        this.topUpList = value;
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
     *         &lt;element name="RepayLoanListCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="RepayLoanList">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="RepayLoan" type="{}RepayLoan_Type" maxOccurs="5"/>
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
    @XmlType(name = "", propOrder = {
        "repayLoanListCount",
        "repayLoanList"
    })
    public static class TopUpList {

        @XmlElement(name = "RepayLoanListCount")
        protected int repayLoanListCount;
        @XmlElement(name = "RepayLoanList", required = true)
        protected ApplicationType.TopUpList.RepayLoanList repayLoanList;

        /**
         * Gets the value of the repayLoanListCount property.
         * 
         */
        public int getRepayLoanListCount() {
            return repayLoanListCount;
        }

        /**
         * Sets the value of the repayLoanListCount property.
         * 
         */
        public void setRepayLoanListCount(int value) {
            this.repayLoanListCount = value;
        }

        /**
         * Gets the value of the repayLoanList property.
         * 
         * @return
         *     possible object is
         *     {@link ApplicationType.TopUpList.RepayLoanList }
         *     
         */
        public ApplicationType.TopUpList.RepayLoanList getRepayLoanList() {
            return repayLoanList;
        }

        /**
         * Sets the value of the repayLoanList property.
         * 
         * @param value
         *     allowed object is
         *     {@link ApplicationType.TopUpList.RepayLoanList }
         *     
         */
        public void setRepayLoanList(ApplicationType.TopUpList.RepayLoanList value) {
            this.repayLoanList = value;
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
         *         &lt;element name="RepayLoan" type="{}RepayLoan_Type" maxOccurs="5"/>
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
            "repayLoen"
        })
        public static class RepayLoanList {

            @XmlElement(name = "RepayLoan", required = true)
            protected List<RepayLoanType> repayLoen;

            /**
             * Gets the value of the repayLoen property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the repayLoen property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRepayLoen().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RepayLoanType }
             * 
             * 
             */
            public List<RepayLoanType> getRepayLoen() {
                if (repayLoen == null) {
                    repayLoen = new ArrayList<RepayLoanType>();
                }
                return this.repayLoen;
            }

        }

    }

}
