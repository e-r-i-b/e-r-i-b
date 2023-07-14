/**
 * AutoSubscriptionInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Информация о подписке
 */
public class AutoSubscriptionInfo_Type  implements java.io.Serializable {
    /* Уникальный идентификатор заявки на создание подписки. Заполняет
     * ЕРИБ */
    private java.lang.String requestId;

    /* Номер Автоплатежа, печатаемый в чеке */
    private java.lang.String autopayNumber;

    /* Наименование подписки, введенное клиентом */
    private java.lang.String autopayName;

    /* Код события, который определяет исполнение подписки */
    private com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type exeEventCode;

    /* Алгоритм расчета суммы очередного автоплатежа */
    private com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type summaKindCode;

    private com.rssl.phizicgate.esberibgate.ws.generated.AutopayStatus_Type autopayStatus;

    /* Текстовое описание состояния подписки */
    private java.lang.String autopayStatusDesc;

    /* Дата и время создания подписки */
    private java.lang.String startDate;

    /* Дата и время изменения подписки. Обязательно при изменении */
    private java.lang.String updateDate;

    /* Максимальная сумма платежей по подписке в месяц. 
     * В случае если при исполнении очередного платежа в течение 1 месяца
     * общая сумма платежей превышает значение этого лимита – платеж исполнен
     * не будет */
    private java.math.BigDecimal maxSumWritePerMonth;

    /* Пороговый лимит проведения платежа. Если на счете сумма меньше
     * лимита, то производится пополнение счета. Обязателен для пороговых
     * подписок */
    private java.math.BigDecimal irreducibleAmt;

    /* Фиксированная сумма платежа (всегда в рублях). Обязателен для
     * пороговых и регулярных подписок */
    private java.math.BigDecimal curAmt;

    /* Процент от суммы. Передается для копилок от поступлений и копилок
     * от оборотов */
    private java.math.BigDecimal percent;

    /* Дата ближайшего исполнения подписки. При создании указывается
     * дата первого платежа. Обязателен для регулярных подписок и подписок
     * по счету */
    private java.lang.String nextPayDate;

    /* Расписание  исполнения АП. */
    private com.rssl.phizicgate.esberibgate.ws.generated.PayDay_Type payDay;

    /* Информация об изменении состояния подписки. Передается, если
     * было какое-то изменение */
    private com.rssl.phizicgate.esberibgate.ws.generated.ChangeStatus_Type changeStatus;

    /* Канал создания Подписки */
    private com.rssl.phizicgate.esberibgate.ws.generated.Channel_Type channelType;

    /* Направление перевода */
    private com.rssl.phizicgate.esberibgate.ws.generated.TransDirection_Type transDirection;

    /* Канал создания Подписки */
    private java.lang.String message;

    private java.lang.String SPNum;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    public AutoSubscriptionInfo_Type() {
    }

    public AutoSubscriptionInfo_Type(
           java.lang.String requestId,
           java.lang.String autopayNumber,
           java.lang.String autopayName,
           com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type exeEventCode,
           com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type summaKindCode,
           com.rssl.phizicgate.esberibgate.ws.generated.AutopayStatus_Type autopayStatus,
           java.lang.String autopayStatusDesc,
           java.lang.String startDate,
           java.lang.String updateDate,
           java.math.BigDecimal maxSumWritePerMonth,
           java.math.BigDecimal irreducibleAmt,
           java.math.BigDecimal curAmt,
           java.math.BigDecimal percent,
           java.lang.String nextPayDate,
           com.rssl.phizicgate.esberibgate.ws.generated.PayDay_Type payDay,
           com.rssl.phizicgate.esberibgate.ws.generated.ChangeStatus_Type changeStatus,
           com.rssl.phizicgate.esberibgate.ws.generated.Channel_Type channelType,
           com.rssl.phizicgate.esberibgate.ws.generated.TransDirection_Type transDirection,
           java.lang.String message,
           java.lang.String SPNum,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
           this.requestId = requestId;
           this.autopayNumber = autopayNumber;
           this.autopayName = autopayName;
           this.exeEventCode = exeEventCode;
           this.summaKindCode = summaKindCode;
           this.autopayStatus = autopayStatus;
           this.autopayStatusDesc = autopayStatusDesc;
           this.startDate = startDate;
           this.updateDate = updateDate;
           this.maxSumWritePerMonth = maxSumWritePerMonth;
           this.irreducibleAmt = irreducibleAmt;
           this.curAmt = curAmt;
           this.percent = percent;
           this.nextPayDate = nextPayDate;
           this.payDay = payDay;
           this.changeStatus = changeStatus;
           this.channelType = channelType;
           this.transDirection = transDirection;
           this.message = message;
           this.SPNum = SPNum;
           this.bankInfo = bankInfo;
    }


    /**
     * Gets the requestId value for this AutoSubscriptionInfo_Type.
     * 
     * @return requestId   * Уникальный идентификатор заявки на создание подписки. Заполняет
     * ЕРИБ
     */
    public java.lang.String getRequestId() {
        return requestId;
    }


    /**
     * Sets the requestId value for this AutoSubscriptionInfo_Type.
     * 
     * @param requestId   * Уникальный идентификатор заявки на создание подписки. Заполняет
     * ЕРИБ
     */
    public void setRequestId(java.lang.String requestId) {
        this.requestId = requestId;
    }


    /**
     * Gets the autopayNumber value for this AutoSubscriptionInfo_Type.
     * 
     * @return autopayNumber   * Номер Автоплатежа, печатаемый в чеке
     */
    public java.lang.String getAutopayNumber() {
        return autopayNumber;
    }


    /**
     * Sets the autopayNumber value for this AutoSubscriptionInfo_Type.
     * 
     * @param autopayNumber   * Номер Автоплатежа, печатаемый в чеке
     */
    public void setAutopayNumber(java.lang.String autopayNumber) {
        this.autopayNumber = autopayNumber;
    }


    /**
     * Gets the autopayName value for this AutoSubscriptionInfo_Type.
     * 
     * @return autopayName   * Наименование подписки, введенное клиентом
     */
    public java.lang.String getAutopayName() {
        return autopayName;
    }


    /**
     * Sets the autopayName value for this AutoSubscriptionInfo_Type.
     * 
     * @param autopayName   * Наименование подписки, введенное клиентом
     */
    public void setAutopayName(java.lang.String autopayName) {
        this.autopayName = autopayName;
    }


    /**
     * Gets the exeEventCode value for this AutoSubscriptionInfo_Type.
     * 
     * @return exeEventCode   * Код события, который определяет исполнение подписки
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type getExeEventCode() {
        return exeEventCode;
    }


    /**
     * Sets the exeEventCode value for this AutoSubscriptionInfo_Type.
     * 
     * @param exeEventCode   * Код события, который определяет исполнение подписки
     */
    public void setExeEventCode(com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type exeEventCode) {
        this.exeEventCode = exeEventCode;
    }


    /**
     * Gets the summaKindCode value for this AutoSubscriptionInfo_Type.
     * 
     * @return summaKindCode   * Алгоритм расчета суммы очередного автоплатежа
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type getSummaKindCode() {
        return summaKindCode;
    }


    /**
     * Sets the summaKindCode value for this AutoSubscriptionInfo_Type.
     * 
     * @param summaKindCode   * Алгоритм расчета суммы очередного автоплатежа
     */
    public void setSummaKindCode(com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type summaKindCode) {
        this.summaKindCode = summaKindCode;
    }


    /**
     * Gets the autopayStatus value for this AutoSubscriptionInfo_Type.
     * 
     * @return autopayStatus
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutopayStatus_Type getAutopayStatus() {
        return autopayStatus;
    }


    /**
     * Sets the autopayStatus value for this AutoSubscriptionInfo_Type.
     * 
     * @param autopayStatus
     */
    public void setAutopayStatus(com.rssl.phizicgate.esberibgate.ws.generated.AutopayStatus_Type autopayStatus) {
        this.autopayStatus = autopayStatus;
    }


    /**
     * Gets the autopayStatusDesc value for this AutoSubscriptionInfo_Type.
     * 
     * @return autopayStatusDesc   * Текстовое описание состояния подписки
     */
    public java.lang.String getAutopayStatusDesc() {
        return autopayStatusDesc;
    }


    /**
     * Sets the autopayStatusDesc value for this AutoSubscriptionInfo_Type.
     * 
     * @param autopayStatusDesc   * Текстовое описание состояния подписки
     */
    public void setAutopayStatusDesc(java.lang.String autopayStatusDesc) {
        this.autopayStatusDesc = autopayStatusDesc;
    }


    /**
     * Gets the startDate value for this AutoSubscriptionInfo_Type.
     * 
     * @return startDate   * Дата и время создания подписки
     */
    public java.lang.String getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this AutoSubscriptionInfo_Type.
     * 
     * @param startDate   * Дата и время создания подписки
     */
    public void setStartDate(java.lang.String startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the updateDate value for this AutoSubscriptionInfo_Type.
     * 
     * @return updateDate   * Дата и время изменения подписки. Обязательно при изменении
     */
    public java.lang.String getUpdateDate() {
        return updateDate;
    }


    /**
     * Sets the updateDate value for this AutoSubscriptionInfo_Type.
     * 
     * @param updateDate   * Дата и время изменения подписки. Обязательно при изменении
     */
    public void setUpdateDate(java.lang.String updateDate) {
        this.updateDate = updateDate;
    }


    /**
     * Gets the maxSumWritePerMonth value for this AutoSubscriptionInfo_Type.
     * 
     * @return maxSumWritePerMonth   * Максимальная сумма платежей по подписке в месяц. 
     * В случае если при исполнении очередного платежа в течение 1 месяца
     * общая сумма платежей превышает значение этого лимита – платеж исполнен
     * не будет
     */
    public java.math.BigDecimal getMaxSumWritePerMonth() {
        return maxSumWritePerMonth;
    }


    /**
     * Sets the maxSumWritePerMonth value for this AutoSubscriptionInfo_Type.
     * 
     * @param maxSumWritePerMonth   * Максимальная сумма платежей по подписке в месяц. 
     * В случае если при исполнении очередного платежа в течение 1 месяца
     * общая сумма платежей превышает значение этого лимита – платеж исполнен
     * не будет
     */
    public void setMaxSumWritePerMonth(java.math.BigDecimal maxSumWritePerMonth) {
        this.maxSumWritePerMonth = maxSumWritePerMonth;
    }


    /**
     * Gets the irreducibleAmt value for this AutoSubscriptionInfo_Type.
     * 
     * @return irreducibleAmt   * Пороговый лимит проведения платежа. Если на счете сумма меньше
     * лимита, то производится пополнение счета. Обязателен для пороговых
     * подписок
     */
    public java.math.BigDecimal getIrreducibleAmt() {
        return irreducibleAmt;
    }


    /**
     * Sets the irreducibleAmt value for this AutoSubscriptionInfo_Type.
     * 
     * @param irreducibleAmt   * Пороговый лимит проведения платежа. Если на счете сумма меньше
     * лимита, то производится пополнение счета. Обязателен для пороговых
     * подписок
     */
    public void setIrreducibleAmt(java.math.BigDecimal irreducibleAmt) {
        this.irreducibleAmt = irreducibleAmt;
    }


    /**
     * Gets the curAmt value for this AutoSubscriptionInfo_Type.
     * 
     * @return curAmt   * Фиксированная сумма платежа (всегда в рублях). Обязателен для
     * пороговых и регулярных подписок
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this AutoSubscriptionInfo_Type.
     * 
     * @param curAmt   * Фиксированная сумма платежа (всегда в рублях). Обязателен для
     * пороговых и регулярных подписок
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the percent value for this AutoSubscriptionInfo_Type.
     * 
     * @return percent   * Процент от суммы. Передается для копилок от поступлений и копилок
     * от оборотов
     */
    public java.math.BigDecimal getPercent() {
        return percent;
    }


    /**
     * Sets the percent value for this AutoSubscriptionInfo_Type.
     * 
     * @param percent   * Процент от суммы. Передается для копилок от поступлений и копилок
     * от оборотов
     */
    public void setPercent(java.math.BigDecimal percent) {
        this.percent = percent;
    }


    /**
     * Gets the nextPayDate value for this AutoSubscriptionInfo_Type.
     * 
     * @return nextPayDate   * Дата ближайшего исполнения подписки. При создании указывается
     * дата первого платежа. Обязателен для регулярных подписок и подписок
     * по счету
     */
    public java.lang.String getNextPayDate() {
        return nextPayDate;
    }


    /**
     * Sets the nextPayDate value for this AutoSubscriptionInfo_Type.
     * 
     * @param nextPayDate   * Дата ближайшего исполнения подписки. При создании указывается
     * дата первого платежа. Обязателен для регулярных подписок и подписок
     * по счету
     */
    public void setNextPayDate(java.lang.String nextPayDate) {
        this.nextPayDate = nextPayDate;
    }


    /**
     * Gets the payDay value for this AutoSubscriptionInfo_Type.
     * 
     * @return payDay   * Расписание  исполнения АП.
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PayDay_Type getPayDay() {
        return payDay;
    }


    /**
     * Sets the payDay value for this AutoSubscriptionInfo_Type.
     * 
     * @param payDay   * Расписание  исполнения АП.
     */
    public void setPayDay(com.rssl.phizicgate.esberibgate.ws.generated.PayDay_Type payDay) {
        this.payDay = payDay;
    }


    /**
     * Gets the changeStatus value for this AutoSubscriptionInfo_Type.
     * 
     * @return changeStatus   * Информация об изменении состояния подписки. Передается, если
     * было какое-то изменение
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ChangeStatus_Type getChangeStatus() {
        return changeStatus;
    }


    /**
     * Sets the changeStatus value for this AutoSubscriptionInfo_Type.
     * 
     * @param changeStatus   * Информация об изменении состояния подписки. Передается, если
     * было какое-то изменение
     */
    public void setChangeStatus(com.rssl.phizicgate.esberibgate.ws.generated.ChangeStatus_Type changeStatus) {
        this.changeStatus = changeStatus;
    }


    /**
     * Gets the channelType value for this AutoSubscriptionInfo_Type.
     * 
     * @return channelType   * Канал создания Подписки
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Channel_Type getChannelType() {
        return channelType;
    }


    /**
     * Sets the channelType value for this AutoSubscriptionInfo_Type.
     * 
     * @param channelType   * Канал создания Подписки
     */
    public void setChannelType(com.rssl.phizicgate.esberibgate.ws.generated.Channel_Type channelType) {
        this.channelType = channelType;
    }


    /**
     * Gets the transDirection value for this AutoSubscriptionInfo_Type.
     * 
     * @return transDirection   * Направление перевода
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.TransDirection_Type getTransDirection() {
        return transDirection;
    }


    /**
     * Sets the transDirection value for this AutoSubscriptionInfo_Type.
     * 
     * @param transDirection   * Направление перевода
     */
    public void setTransDirection(com.rssl.phizicgate.esberibgate.ws.generated.TransDirection_Type transDirection) {
        this.transDirection = transDirection;
    }


    /**
     * Gets the message value for this AutoSubscriptionInfo_Type.
     * 
     * @return message   * Канал создания Подписки
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this AutoSubscriptionInfo_Type.
     * 
     * @param message   * Канал создания Подписки
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the SPNum value for this AutoSubscriptionInfo_Type.
     * 
     * @return SPNum
     */
    public java.lang.String getSPNum() {
        return SPNum;
    }


    /**
     * Sets the SPNum value for this AutoSubscriptionInfo_Type.
     * 
     * @param SPNum
     */
    public void setSPNum(java.lang.String SPNum) {
        this.SPNum = SPNum;
    }


    /**
     * Gets the bankInfo value for this AutoSubscriptionInfo_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this AutoSubscriptionInfo_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoSubscriptionInfo_Type)) return false;
        AutoSubscriptionInfo_Type other = (AutoSubscriptionInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.requestId==null && other.getRequestId()==null) || 
             (this.requestId!=null &&
              this.requestId.equals(other.getRequestId()))) &&
            ((this.autopayNumber==null && other.getAutopayNumber()==null) || 
             (this.autopayNumber!=null &&
              this.autopayNumber.equals(other.getAutopayNumber()))) &&
            ((this.autopayName==null && other.getAutopayName()==null) || 
             (this.autopayName!=null &&
              this.autopayName.equals(other.getAutopayName()))) &&
            ((this.exeEventCode==null && other.getExeEventCode()==null) || 
             (this.exeEventCode!=null &&
              this.exeEventCode.equals(other.getExeEventCode()))) &&
            ((this.summaKindCode==null && other.getSummaKindCode()==null) || 
             (this.summaKindCode!=null &&
              this.summaKindCode.equals(other.getSummaKindCode()))) &&
            ((this.autopayStatus==null && other.getAutopayStatus()==null) || 
             (this.autopayStatus!=null &&
              this.autopayStatus.equals(other.getAutopayStatus()))) &&
            ((this.autopayStatusDesc==null && other.getAutopayStatusDesc()==null) || 
             (this.autopayStatusDesc!=null &&
              this.autopayStatusDesc.equals(other.getAutopayStatusDesc()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.updateDate==null && other.getUpdateDate()==null) || 
             (this.updateDate!=null &&
              this.updateDate.equals(other.getUpdateDate()))) &&
            ((this.maxSumWritePerMonth==null && other.getMaxSumWritePerMonth()==null) || 
             (this.maxSumWritePerMonth!=null &&
              this.maxSumWritePerMonth.equals(other.getMaxSumWritePerMonth()))) &&
            ((this.irreducibleAmt==null && other.getIrreducibleAmt()==null) || 
             (this.irreducibleAmt!=null &&
              this.irreducibleAmt.equals(other.getIrreducibleAmt()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.percent==null && other.getPercent()==null) || 
             (this.percent!=null &&
              this.percent.equals(other.getPercent()))) &&
            ((this.nextPayDate==null && other.getNextPayDate()==null) || 
             (this.nextPayDate!=null &&
              this.nextPayDate.equals(other.getNextPayDate()))) &&
            ((this.payDay==null && other.getPayDay()==null) || 
             (this.payDay!=null &&
              this.payDay.equals(other.getPayDay()))) &&
            ((this.changeStatus==null && other.getChangeStatus()==null) || 
             (this.changeStatus!=null &&
              this.changeStatus.equals(other.getChangeStatus()))) &&
            ((this.channelType==null && other.getChannelType()==null) || 
             (this.channelType!=null &&
              this.channelType.equals(other.getChannelType()))) &&
            ((this.transDirection==null && other.getTransDirection()==null) || 
             (this.transDirection!=null &&
              this.transDirection.equals(other.getTransDirection()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.SPNum==null && other.getSPNum()==null) || 
             (this.SPNum!=null &&
              this.SPNum.equals(other.getSPNum()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRequestId() != null) {
            _hashCode += getRequestId().hashCode();
        }
        if (getAutopayNumber() != null) {
            _hashCode += getAutopayNumber().hashCode();
        }
        if (getAutopayName() != null) {
            _hashCode += getAutopayName().hashCode();
        }
        if (getExeEventCode() != null) {
            _hashCode += getExeEventCode().hashCode();
        }
        if (getSummaKindCode() != null) {
            _hashCode += getSummaKindCode().hashCode();
        }
        if (getAutopayStatus() != null) {
            _hashCode += getAutopayStatus().hashCode();
        }
        if (getAutopayStatusDesc() != null) {
            _hashCode += getAutopayStatusDesc().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getUpdateDate() != null) {
            _hashCode += getUpdateDate().hashCode();
        }
        if (getMaxSumWritePerMonth() != null) {
            _hashCode += getMaxSumWritePerMonth().hashCode();
        }
        if (getIrreducibleAmt() != null) {
            _hashCode += getIrreducibleAmt().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getPercent() != null) {
            _hashCode += getPercent().hashCode();
        }
        if (getNextPayDate() != null) {
            _hashCode += getNextPayDate().hashCode();
        }
        if (getPayDay() != null) {
            _hashCode += getPayDay().hashCode();
        }
        if (getChangeStatus() != null) {
            _hashCode += getChangeStatus().hashCode();
        }
        if (getChannelType() != null) {
            _hashCode += getChannelType().hashCode();
        }
        if (getTransDirection() != null) {
            _hashCode += getTransDirection().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getSPNum() != null) {
            _hashCode += getSPNum().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoSubscriptionInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autopayNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autopayName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exeEventCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExeEventCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExeEventCodeASAP_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summaKindCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaKindCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaKindCodeASAP_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autopayStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatus_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autopayStatusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UpdateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxSumWritePerMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaxSumWritePerMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("irreducibleAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IrreducibleAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Percent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nextPayDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextPayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayDay_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeStatus_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChannelType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Channel_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transDirection");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransDirection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransDirection_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPNum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
