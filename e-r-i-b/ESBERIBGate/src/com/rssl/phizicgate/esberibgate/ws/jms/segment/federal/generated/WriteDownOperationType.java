
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип для микрооперации списания
 * 
 * <p>Java class for WriteDownOperation_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WriteDownOperation_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OperationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}CurAmt"/>
 *         &lt;element name="Turnover" type="{}Turnover_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WriteDownOperation_Type", propOrder = {
    "operationName",
    "curAmt",
    "turnover"
})
public class WriteDownOperationType {

    @XmlElement(name = "OperationName", required = true)
    protected String operationName;
    @XmlElement(name = "CurAmt", required = true)
    protected BigDecimal curAmt;
    @XmlElement(name = "Turnover", required = true)
    protected TurnoverType turnover;

    /**
     * Gets the value of the operationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * Sets the value of the operationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationName(String value) {
        this.operationName = value;
    }

    /**
     * Сумма микрооперации
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmt() {
        return curAmt;
    }

    /**
     * Sets the value of the curAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmt(BigDecimal value) {
        this.curAmt = value;
    }

    /**
     * Gets the value of the turnover property.
     * 
     * @return
     *     possible object is
     *     {@link TurnoverType }
     *     
     */
    public TurnoverType getTurnover() {
        return turnover;
    }

    /**
     * Sets the value of the turnover property.
     * 
     * @param value
     *     allowed object is
     *     {@link TurnoverType }
     *     
     */
    public void setTurnover(TurnoverType value) {
        this.turnover = value;
    }

}
