
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for AgreemtInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgreemtInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}AgreemtId" minOccurs="0"/>
 *         &lt;element ref="{}SPName" minOccurs="0"/>
 *         &lt;element name="StartDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="EndDt" type="{}Date" minOccurs="0"/>
 *         &lt;element ref="{}BranchNum"/>
 *         &lt;element name="Status" type="{}OpenEnum" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtNum"/>
 *         &lt;element ref="{}AgreemtType"/>
 *         &lt;element ref="{}Sum" minOccurs="0"/>
 *         &lt;element ref="{}ProductCurrency" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtAcct" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;sequence>
 *           &lt;element ref="{}LoanInfo" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}CardInfo" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}DepInfo" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ServiceInfo" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}CdboInfo" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}DepoInfo" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}CssInfo" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}TPInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgreemtInfo_Type", propOrder = {
    "agreemtId",
    "spName",
    "startDt",
    "endDt",
    "branchNum",
    "status",
    "agreemtNum",
    "agreemtType",
    "sum",
    "productCurrency",
    "agreemtAccts",
    "loanInfos",
    "cardInfos",
    "depInfos",
    "serviceInfos",
    "cdboInfos",
    "depoInfos",
    "cssInfos",
    "tpInfos"
})
@XmlRootElement(name = "AgreemtInfo")
public class AgreemtInfo {

    @XmlElement(name = "AgreemtId")
    protected String agreemtId;
    @XmlElement(name = "SPName")
    protected SPNameType spName;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "EndDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar endDt;
    @XmlElement(name = "BranchNum", required = true)
    protected String branchNum;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "AgreemtNum", required = true)
    protected String agreemtNum;
    @XmlElement(name = "AgreemtType", required = true)
    protected String agreemtType;
    @XmlElement(name = "Sum")
    protected String sum;
    @XmlElement(name = "ProductCurrency")
    protected String productCurrency;
    @XmlElement(name = "AgreemtAcct")
    protected List<AgreemtAcct> agreemtAccts;
    @XmlElement(name = "LoanInfo")
    protected List<LoanInfo> loanInfos;
    @XmlElement(name = "CardInfo")
    protected List<CardInfo> cardInfos;
    @XmlElement(name = "DepInfo")
    protected List<DepInfo> depInfos;
    @XmlElement(name = "ServiceInfo")
    protected List<ServiceInfo> serviceInfos;
    @XmlElement(name = "CdboInfo")
    protected List<CdboInfo> cdboInfos;
    @XmlElement(name = "DepoInfo")
    protected List<DepoInfo> depoInfos;
    @XmlElement(name = "CssInfo")
    protected List<CssInfo> cssInfos;
    @XmlElement(name = "TPInfo")
    protected List<TPInfo> tpInfos;

    /**
     * Gets the value of the agreemtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreemtId() {
        return agreemtId;
    }

    /**
     * Sets the value of the agreemtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreemtId(String value) {
        this.agreemtId = value;
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
     * № договора клиента со Сбербанком на предоставление продукта/услуги
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
     * Gets the value of the agreemtType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreemtType() {
        return agreemtType;
    }

    /**
     * Sets the value of the agreemtType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreemtType(String value) {
        this.agreemtType = value;
    }

    /**
     * Gets the value of the sum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSum() {
        return sum;
    }

    /**
     * Sets the value of the sum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSum(String value) {
        this.sum = value;
    }

    /**
     * Gets the value of the productCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCurrency() {
        return productCurrency;
    }

    /**
     * Sets the value of the productCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCurrency(String value) {
        this.productCurrency = value;
    }

    /**
     * Gets the value of the agreemtAccts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agreemtAccts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgreemtAccts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgreemtAcct }
     * 
     * 
     */
    public List<AgreemtAcct> getAgreemtAccts() {
        if (agreemtAccts == null) {
            agreemtAccts = new ArrayList<AgreemtAcct>();
        }
        return this.agreemtAccts;
    }

    /**
     * Gets the value of the loanInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loanInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoanInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LoanInfo }
     * 
     * 
     */
    public List<LoanInfo> getLoanInfos() {
        if (loanInfos == null) {
            loanInfos = new ArrayList<LoanInfo>();
        }
        return this.loanInfos;
    }

    /**
     * Gets the value of the cardInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cardInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCardInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CardInfo }
     * 
     * 
     */
    public List<CardInfo> getCardInfos() {
        if (cardInfos == null) {
            cardInfos = new ArrayList<CardInfo>();
        }
        return this.cardInfos;
    }

    /**
     * Gets the value of the depInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the depInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepInfo }
     * 
     * 
     */
    public List<DepInfo> getDepInfos() {
        if (depInfos == null) {
            depInfos = new ArrayList<DepInfo>();
        }
        return this.depInfos;
    }

    /**
     * Gets the value of the serviceInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceInfo }
     * 
     * 
     */
    public List<ServiceInfo> getServiceInfos() {
        if (serviceInfos == null) {
            serviceInfos = new ArrayList<ServiceInfo>();
        }
        return this.serviceInfos;
    }

    /**
     * Gets the value of the cdboInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cdboInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCdboInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CdboInfo }
     * 
     * 
     */
    public List<CdboInfo> getCdboInfos() {
        if (cdboInfos == null) {
            cdboInfos = new ArrayList<CdboInfo>();
        }
        return this.cdboInfos;
    }

    /**
     * Gets the value of the depoInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the depoInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepoInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepoInfo }
     * 
     * 
     */
    public List<DepoInfo> getDepoInfos() {
        if (depoInfos == null) {
            depoInfos = new ArrayList<DepoInfo>();
        }
        return this.depoInfos;
    }

    /**
     * Gets the value of the cssInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cssInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCssInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CssInfo }
     * 
     * 
     */
    public List<CssInfo> getCssInfos() {
        if (cssInfos == null) {
            cssInfos = new ArrayList<CssInfo>();
        }
        return this.cssInfos;
    }

    /**
     * Gets the value of the tpInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tpInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTPInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TPInfo }
     * 
     * 
     */
    public List<TPInfo> getTPInfos() {
        if (tpInfos == null) {
            tpInfos = new ArrayList<TPInfo>();
        }
        return this.tpInfos;
    }

}
