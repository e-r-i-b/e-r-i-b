
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for Markup_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Markup_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ContId"/>
 *         &lt;element ref="{}MarkupType"/>
 *         &lt;element ref="{}IdInList"/>
 *         &lt;element ref="{}SurnameEnglish" minOccurs="0"/>
 *         &lt;element ref="{}NameEnglish" minOccurs="0"/>
 *         &lt;element ref="{}MiddleNameEnglish" minOccurs="0"/>
 *         &lt;element ref="{}StatusInList"/>
 *         &lt;element ref="{}StatusOptions" minOccurs="0"/>
 *         &lt;element ref="{}StatusUpdateDate"/>
 *         &lt;element ref="{}Rank" minOccurs="0"/>
 *         &lt;element ref="{}RankStartDate" minOccurs="0"/>
 *         &lt;element ref="{}RankEndDate" minOccurs="0"/>
 *         &lt;element ref="{}RelativeType" minOccurs="0"/>
 *         &lt;element ref="{}RelativeIdInList" minOccurs="0"/>
 *         &lt;element ref="{}OperatorAction" minOccurs="0"/>
 *         &lt;element ref="{}MarkupComment" minOccurs="0"/>
 *         &lt;element ref="{}InactiveDate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Markup_Type", propOrder = {
    "contId",
    "markupType",
    "idInList",
    "surnameEnglish",
    "nameEnglish",
    "middleNameEnglish",
    "statusInList",
    "statusOptions",
    "statusUpdateDate",
    "rank",
    "rankStartDate",
    "rankEndDate",
    "relativeType",
    "relativeIdInList",
    "operatorAction",
    "markupComment",
    "inactiveDate"
})
@XmlRootElement(name = "Markup")
public class Markup {

    @XmlElement(name = "ContId")
    protected long contId;
    @XmlElement(name = "MarkupType")
    protected long markupType;
    @XmlElement(name = "IdInList")
    protected long idInList;
    @XmlElement(name = "SurnameEnglish")
    protected String surnameEnglish;
    @XmlElement(name = "NameEnglish")
    protected String nameEnglish;
    @XmlElement(name = "MiddleNameEnglish")
    protected String middleNameEnglish;
    @XmlElement(name = "StatusInList")
    protected long statusInList;
    @XmlElement(name = "StatusOptions")
    protected String statusOptions;
    @XmlElement(name = "StatusUpdateDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar statusUpdateDate;
    @XmlElement(name = "Rank")
    protected String rank;
    @XmlElement(name = "RankStartDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rankStartDate;
    @XmlElement(name = "RankEndDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rankEndDate;
    @XmlElement(name = "RelativeType")
    protected String relativeType;
    @XmlElement(name = "RelativeIdInList")
    protected Long relativeIdInList;
    @XmlElement(name = "OperatorAction")
    protected String operatorAction;
    @XmlElement(name = "MarkupComment")
    protected String markupComment;
    @XmlElement(name = "InactiveDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar inactiveDate;

    /**
     * Gets the value of the contId property.
     * 
     */
    public long getContId() {
        return contId;
    }

    /**
     * Sets the value of the contId property.
     * 
     */
    public void setContId(long value) {
        this.contId = value;
    }

    /**
     * Gets the value of the markupType property.
     * 
     */
    public long getMarkupType() {
        return markupType;
    }

    /**
     * Sets the value of the markupType property.
     * 
     */
    public void setMarkupType(long value) {
        this.markupType = value;
    }

    /**
     * Gets the value of the idInList property.
     * 
     */
    public long getIdInList() {
        return idInList;
    }

    /**
     * Sets the value of the idInList property.
     * 
     */
    public void setIdInList(long value) {
        this.idInList = value;
    }

    /**
     * Gets the value of the surnameEnglish property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurnameEnglish() {
        return surnameEnglish;
    }

    /**
     * Sets the value of the surnameEnglish property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurnameEnglish(String value) {
        this.surnameEnglish = value;
    }

    /**
     * Gets the value of the nameEnglish property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameEnglish() {
        return nameEnglish;
    }

    /**
     * Sets the value of the nameEnglish property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameEnglish(String value) {
        this.nameEnglish = value;
    }

    /**
     * Gets the value of the middleNameEnglish property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleNameEnglish() {
        return middleNameEnglish;
    }

    /**
     * Sets the value of the middleNameEnglish property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleNameEnglish(String value) {
        this.middleNameEnglish = value;
    }

    /**
     * Gets the value of the statusInList property.
     * 
     */
    public long getStatusInList() {
        return statusInList;
    }

    /**
     * Sets the value of the statusInList property.
     * 
     */
    public void setStatusInList(long value) {
        this.statusInList = value;
    }

    /**
     * Gets the value of the statusOptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusOptions() {
        return statusOptions;
    }

    /**
     * Sets the value of the statusOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusOptions(String value) {
        this.statusOptions = value;
    }

    /**
     * Gets the value of the statusUpdateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStatusUpdateDate() {
        return statusUpdateDate;
    }

    /**
     * Sets the value of the statusUpdateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusUpdateDate(Calendar value) {
        this.statusUpdateDate = value;
    }

    /**
     * Gets the value of the rank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRank(String value) {
        this.rank = value;
    }

    /**
     * Gets the value of the rankStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRankStartDate() {
        return rankStartDate;
    }

    /**
     * Sets the value of the rankStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRankStartDate(Calendar value) {
        this.rankStartDate = value;
    }

    /**
     * Gets the value of the rankEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRankEndDate() {
        return rankEndDate;
    }

    /**
     * Sets the value of the rankEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRankEndDate(Calendar value) {
        this.rankEndDate = value;
    }

    /**
     * Gets the value of the relativeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelativeType() {
        return relativeType;
    }

    /**
     * Sets the value of the relativeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelativeType(String value) {
        this.relativeType = value;
    }

    /**
     * Gets the value of the relativeIdInList property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRelativeIdInList() {
        return relativeIdInList;
    }

    /**
     * Sets the value of the relativeIdInList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRelativeIdInList(Long value) {
        this.relativeIdInList = value;
    }

    /**
     * Gets the value of the operatorAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorAction() {
        return operatorAction;
    }

    /**
     * Sets the value of the operatorAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorAction(String value) {
        this.operatorAction = value;
    }

    /**
     * Gets the value of the markupComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarkupComment() {
        return markupComment;
    }

    /**
     * Sets the value of the markupComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarkupComment(String value) {
        this.markupComment = value;
    }

    /**
     * Gets the value of the inactiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getInactiveDate() {
        return inactiveDate;
    }

    /**
     * Sets the value of the inactiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInactiveDate(Calendar value) {
        this.inactiveDate = value;
    }

}
