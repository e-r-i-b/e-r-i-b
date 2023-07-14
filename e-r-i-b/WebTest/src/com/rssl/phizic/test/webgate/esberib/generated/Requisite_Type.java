/**
 * Requisite_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Дополнительный реквизит
 */
public class Requisite_Type  implements java.io.Serializable {
    /* Наименование дополнительного реквизита для отображения пользователю */
    private java.lang.String nameVisible;

    /* Идентификатор поля состоящий из цифр и букв английского алфавита */
    private java.lang.String nameBS;

    /* Правила заполнения поля. */
    private java.lang.String description;

    /* Комментарий к полю */
    private java.lang.String comment;

    /* Тип поля */
    private java.lang.String type;

    /* Точность числового значения (количество знаков после запятой).
     * Если = 0, то допустимы только целые числа */
    private java.math.BigInteger numberPrecision;

    /* Признак «Дополнительный реквизит обязательно должен быть указан
     * при формировании платежа» */
    private java.lang.Boolean isRequired;

    /* Признак «Общая сумма платежа». В том случае, когда БС ТБ возвращает
     * не только общую сумму задолженности, но и расшифровку этой суммы (в
     * разбивке по видам начислений), то этот признак указывает, какое поле
     * следует считать общей суммой задолженности. В реквизитах, полученных
     * от БС ТБ, может быть только одно поле с таким признаком. */
    private java.lang.Boolean isSum;

    /* Признак «Данный реквизит является одним из ключевых, т.е. индетифицирующий
     * абонента».
     * Реквизиты с таким признаками будут отображаться в чеках, отправляться
     * в подтверждающих смс вне зависимости от значения признаков isForBill
     * и includeInSMS.
     * Признак не может быть передан одновременно с  IsVisible = false, IsSum */
    private java.lang.Boolean isKey;

    /* Признак «Редактируемого поля». Поля предоставляет возможность
     * ввода данных */
    private java.lang.Boolean isEditable;

    /* Признак видимости поля клиентом. Признак не может быть равен
     * false одновременно с передачей  IsSum для всех случаев, кроме оформления
     * подписки на автоплатежи */
    private java.lang.Boolean isVisible;

    /* Признак «Включать в счет» */
    private java.lang.Boolean isForBill;

    /* Признак «Включать в SMS» */
    private java.lang.Boolean includeInSMS;

    /* Признак «Сохранять в шаблоне платежа» */
    private java.lang.Boolean saveInTemplate;

    private java.lang.String[] requisiteTypes;

    /* Признак «Скрывать реквизит на форме подтверждения» */
    private java.lang.Boolean hideInConfirmation;

    /* Описание длины поля */
    private com.rssl.phizic.test.webgate.esberib.generated.AttributeLength_Type attributeLength;

    /* Валидаторы (контроли), используемые для дополнительного атрибута.
     * Неприменимо для типов set и list. */
    private com.rssl.phizic.test.webgate.esberib.generated.Validator_Type[] validators;

    /* Список значений для выбора клиентом при для выбора клиентом
     * при Type=list и set */
    private java.lang.String[] menu;

    /* Введенные клиентом данные. */
    private java.lang.String[] enteredData;

    /* Значение по умолчанию для поля.
     * Клиент передает в этом поле, тоже значение, что пришло от сервера.
     * Не используется для типа set. */
    private java.lang.String defaultValue;

    /* Текст ошибки на русском языке для пользователя, в случае нахождения
     * ошибки на сервере */
    private java.lang.String error;

    public Requisite_Type() {
    }

    public Requisite_Type(
           java.lang.String nameVisible,
           java.lang.String nameBS,
           java.lang.String description,
           java.lang.String comment,
           java.lang.String type,
           java.math.BigInteger numberPrecision,
           java.lang.Boolean isRequired,
           java.lang.Boolean isSum,
           java.lang.Boolean isKey,
           java.lang.Boolean isEditable,
           java.lang.Boolean isVisible,
           java.lang.Boolean isForBill,
           java.lang.Boolean includeInSMS,
           java.lang.Boolean saveInTemplate,
           java.lang.String[] requisiteTypes,
           java.lang.Boolean hideInConfirmation,
           com.rssl.phizic.test.webgate.esberib.generated.AttributeLength_Type attributeLength,
           com.rssl.phizic.test.webgate.esberib.generated.Validator_Type[] validators,
           java.lang.String[] menu,
           java.lang.String[] enteredData,
           java.lang.String defaultValue,
           java.lang.String error) {
           this.nameVisible = nameVisible;
           this.nameBS = nameBS;
           this.description = description;
           this.comment = comment;
           this.type = type;
           this.numberPrecision = numberPrecision;
           this.isRequired = isRequired;
           this.isSum = isSum;
           this.isKey = isKey;
           this.isEditable = isEditable;
           this.isVisible = isVisible;
           this.isForBill = isForBill;
           this.includeInSMS = includeInSMS;
           this.saveInTemplate = saveInTemplate;
           this.requisiteTypes = requisiteTypes;
           this.hideInConfirmation = hideInConfirmation;
           this.attributeLength = attributeLength;
           this.validators = validators;
           this.menu = menu;
           this.enteredData = enteredData;
           this.defaultValue = defaultValue;
           this.error = error;
    }


    /**
     * Gets the nameVisible value for this Requisite_Type.
     * 
     * @return nameVisible   * Наименование дополнительного реквизита для отображения пользователю
     */
    public java.lang.String getNameVisible() {
        return nameVisible;
    }


    /**
     * Sets the nameVisible value for this Requisite_Type.
     * 
     * @param nameVisible   * Наименование дополнительного реквизита для отображения пользователю
     */
    public void setNameVisible(java.lang.String nameVisible) {
        this.nameVisible = nameVisible;
    }


    /**
     * Gets the nameBS value for this Requisite_Type.
     * 
     * @return nameBS   * Идентификатор поля состоящий из цифр и букв английского алфавита
     */
    public java.lang.String getNameBS() {
        return nameBS;
    }


    /**
     * Sets the nameBS value for this Requisite_Type.
     * 
     * @param nameBS   * Идентификатор поля состоящий из цифр и букв английского алфавита
     */
    public void setNameBS(java.lang.String nameBS) {
        this.nameBS = nameBS;
    }


    /**
     * Gets the description value for this Requisite_Type.
     * 
     * @return description   * Правила заполнения поля.
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Requisite_Type.
     * 
     * @param description   * Правила заполнения поля.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the comment value for this Requisite_Type.
     * 
     * @return comment   * Комментарий к полю
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this Requisite_Type.
     * 
     * @param comment   * Комментарий к полю
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the type value for this Requisite_Type.
     * 
     * @return type   * Тип поля
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Requisite_Type.
     * 
     * @param type   * Тип поля
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the numberPrecision value for this Requisite_Type.
     * 
     * @return numberPrecision   * Точность числового значения (количество знаков после запятой).
     * Если = 0, то допустимы только целые числа
     */
    public java.math.BigInteger getNumberPrecision() {
        return numberPrecision;
    }


    /**
     * Sets the numberPrecision value for this Requisite_Type.
     * 
     * @param numberPrecision   * Точность числового значения (количество знаков после запятой).
     * Если = 0, то допустимы только целые числа
     */
    public void setNumberPrecision(java.math.BigInteger numberPrecision) {
        this.numberPrecision = numberPrecision;
    }


    /**
     * Gets the isRequired value for this Requisite_Type.
     * 
     * @return isRequired   * Признак «Дополнительный реквизит обязательно должен быть указан
     * при формировании платежа»
     */
    public java.lang.Boolean getIsRequired() {
        return isRequired;
    }


    /**
     * Sets the isRequired value for this Requisite_Type.
     * 
     * @param isRequired   * Признак «Дополнительный реквизит обязательно должен быть указан
     * при формировании платежа»
     */
    public void setIsRequired(java.lang.Boolean isRequired) {
        this.isRequired = isRequired;
    }


    /**
     * Gets the isSum value for this Requisite_Type.
     * 
     * @return isSum   * Признак «Общая сумма платежа». В том случае, когда БС ТБ возвращает
     * не только общую сумму задолженности, но и расшифровку этой суммы (в
     * разбивке по видам начислений), то этот признак указывает, какое поле
     * следует считать общей суммой задолженности. В реквизитах, полученных
     * от БС ТБ, может быть только одно поле с таким признаком.
     */
    public java.lang.Boolean getIsSum() {
        return isSum;
    }


    /**
     * Sets the isSum value for this Requisite_Type.
     * 
     * @param isSum   * Признак «Общая сумма платежа». В том случае, когда БС ТБ возвращает
     * не только общую сумму задолженности, но и расшифровку этой суммы (в
     * разбивке по видам начислений), то этот признак указывает, какое поле
     * следует считать общей суммой задолженности. В реквизитах, полученных
     * от БС ТБ, может быть только одно поле с таким признаком.
     */
    public void setIsSum(java.lang.Boolean isSum) {
        this.isSum = isSum;
    }


    /**
     * Gets the isKey value for this Requisite_Type.
     * 
     * @return isKey   * Признак «Данный реквизит является одним из ключевых, т.е. индетифицирующий
     * абонента».
     * Реквизиты с таким признаками будут отображаться в чеках, отправляться
     * в подтверждающих смс вне зависимости от значения признаков isForBill
     * и includeInSMS.
     * Признак не может быть передан одновременно с  IsVisible = false, IsSum
     */
    public java.lang.Boolean getIsKey() {
        return isKey;
    }


    /**
     * Sets the isKey value for this Requisite_Type.
     * 
     * @param isKey   * Признак «Данный реквизит является одним из ключевых, т.е. индетифицирующий
     * абонента».
     * Реквизиты с таким признаками будут отображаться в чеках, отправляться
     * в подтверждающих смс вне зависимости от значения признаков isForBill
     * и includeInSMS.
     * Признак не может быть передан одновременно с  IsVisible = false, IsSum
     */
    public void setIsKey(java.lang.Boolean isKey) {
        this.isKey = isKey;
    }


    /**
     * Gets the isEditable value for this Requisite_Type.
     * 
     * @return isEditable   * Признак «Редактируемого поля». Поля предоставляет возможность
     * ввода данных
     */
    public java.lang.Boolean getIsEditable() {
        return isEditable;
    }


    /**
     * Sets the isEditable value for this Requisite_Type.
     * 
     * @param isEditable   * Признак «Редактируемого поля». Поля предоставляет возможность
     * ввода данных
     */
    public void setIsEditable(java.lang.Boolean isEditable) {
        this.isEditable = isEditable;
    }


    /**
     * Gets the isVisible value for this Requisite_Type.
     * 
     * @return isVisible   * Признак видимости поля клиентом. Признак не может быть равен
     * false одновременно с передачей  IsSum для всех случаев, кроме оформления
     * подписки на автоплатежи
     */
    public java.lang.Boolean getIsVisible() {
        return isVisible;
    }


    /**
     * Sets the isVisible value for this Requisite_Type.
     * 
     * @param isVisible   * Признак видимости поля клиентом. Признак не может быть равен
     * false одновременно с передачей  IsSum для всех случаев, кроме оформления
     * подписки на автоплатежи
     */
    public void setIsVisible(java.lang.Boolean isVisible) {
        this.isVisible = isVisible;
    }


    /**
     * Gets the isForBill value for this Requisite_Type.
     * 
     * @return isForBill   * Признак «Включать в счет»
     */
    public java.lang.Boolean getIsForBill() {
        return isForBill;
    }


    /**
     * Sets the isForBill value for this Requisite_Type.
     * 
     * @param isForBill   * Признак «Включать в счет»
     */
    public void setIsForBill(java.lang.Boolean isForBill) {
        this.isForBill = isForBill;
    }


    /**
     * Gets the includeInSMS value for this Requisite_Type.
     * 
     * @return includeInSMS   * Признак «Включать в SMS»
     */
    public java.lang.Boolean getIncludeInSMS() {
        return includeInSMS;
    }


    /**
     * Sets the includeInSMS value for this Requisite_Type.
     * 
     * @param includeInSMS   * Признак «Включать в SMS»
     */
    public void setIncludeInSMS(java.lang.Boolean includeInSMS) {
        this.includeInSMS = includeInSMS;
    }


    /**
     * Gets the saveInTemplate value for this Requisite_Type.
     * 
     * @return saveInTemplate   * Признак «Сохранять в шаблоне платежа»
     */
    public java.lang.Boolean getSaveInTemplate() {
        return saveInTemplate;
    }


    /**
     * Sets the saveInTemplate value for this Requisite_Type.
     * 
     * @param saveInTemplate   * Признак «Сохранять в шаблоне платежа»
     */
    public void setSaveInTemplate(java.lang.Boolean saveInTemplate) {
        this.saveInTemplate = saveInTemplate;
    }


    /**
     * Gets the requisiteTypes value for this Requisite_Type.
     * 
     * @return requisiteTypes
     */
    public java.lang.String[] getRequisiteTypes() {
        return requisiteTypes;
    }


    /**
     * Sets the requisiteTypes value for this Requisite_Type.
     * 
     * @param requisiteTypes
     */
    public void setRequisiteTypes(java.lang.String[] requisiteTypes) {
        this.requisiteTypes = requisiteTypes;
    }


    /**
     * Gets the hideInConfirmation value for this Requisite_Type.
     * 
     * @return hideInConfirmation   * Признак «Скрывать реквизит на форме подтверждения»
     */
    public java.lang.Boolean getHideInConfirmation() {
        return hideInConfirmation;
    }


    /**
     * Sets the hideInConfirmation value for this Requisite_Type.
     * 
     * @param hideInConfirmation   * Признак «Скрывать реквизит на форме подтверждения»
     */
    public void setHideInConfirmation(java.lang.Boolean hideInConfirmation) {
        this.hideInConfirmation = hideInConfirmation;
    }


    /**
     * Gets the attributeLength value for this Requisite_Type.
     * 
     * @return attributeLength   * Описание длины поля
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AttributeLength_Type getAttributeLength() {
        return attributeLength;
    }


    /**
     * Sets the attributeLength value for this Requisite_Type.
     * 
     * @param attributeLength   * Описание длины поля
     */
    public void setAttributeLength(com.rssl.phizic.test.webgate.esberib.generated.AttributeLength_Type attributeLength) {
        this.attributeLength = attributeLength;
    }


    /**
     * Gets the validators value for this Requisite_Type.
     * 
     * @return validators   * Валидаторы (контроли), используемые для дополнительного атрибута.
     * Неприменимо для типов set и list.
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Validator_Type[] getValidators() {
        return validators;
    }


    /**
     * Sets the validators value for this Requisite_Type.
     * 
     * @param validators   * Валидаторы (контроли), используемые для дополнительного атрибута.
     * Неприменимо для типов set и list.
     */
    public void setValidators(com.rssl.phizic.test.webgate.esberib.generated.Validator_Type[] validators) {
        this.validators = validators;
    }


    /**
     * Gets the menu value for this Requisite_Type.
     * 
     * @return menu   * Список значений для выбора клиентом при для выбора клиентом
     * при Type=list и set
     */
    public java.lang.String[] getMenu() {
        return menu;
    }


    /**
     * Sets the menu value for this Requisite_Type.
     * 
     * @param menu   * Список значений для выбора клиентом при для выбора клиентом
     * при Type=list и set
     */
    public void setMenu(java.lang.String[] menu) {
        this.menu = menu;
    }


    /**
     * Gets the enteredData value for this Requisite_Type.
     * 
     * @return enteredData   * Введенные клиентом данные.
     */
    public java.lang.String[] getEnteredData() {
        return enteredData;
    }


    /**
     * Sets the enteredData value for this Requisite_Type.
     * 
     * @param enteredData   * Введенные клиентом данные.
     */
    public void setEnteredData(java.lang.String[] enteredData) {
        this.enteredData = enteredData;
    }


    /**
     * Gets the defaultValue value for this Requisite_Type.
     * 
     * @return defaultValue   * Значение по умолчанию для поля.
     * Клиент передает в этом поле, тоже значение, что пришло от сервера.
     * Не используется для типа set.
     */
    public java.lang.String getDefaultValue() {
        return defaultValue;
    }


    /**
     * Sets the defaultValue value for this Requisite_Type.
     * 
     * @param defaultValue   * Значение по умолчанию для поля.
     * Клиент передает в этом поле, тоже значение, что пришло от сервера.
     * Не используется для типа set.
     */
    public void setDefaultValue(java.lang.String defaultValue) {
        this.defaultValue = defaultValue;
    }


    /**
     * Gets the error value for this Requisite_Type.
     * 
     * @return error   * Текст ошибки на русском языке для пользователя, в случае нахождения
     * ошибки на сервере
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this Requisite_Type.
     * 
     * @param error   * Текст ошибки на русском языке для пользователя, в случае нахождения
     * ошибки на сервере
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Requisite_Type)) return false;
        Requisite_Type other = (Requisite_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nameVisible==null && other.getNameVisible()==null) || 
             (this.nameVisible!=null &&
              this.nameVisible.equals(other.getNameVisible()))) &&
            ((this.nameBS==null && other.getNameBS()==null) || 
             (this.nameBS!=null &&
              this.nameBS.equals(other.getNameBS()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.numberPrecision==null && other.getNumberPrecision()==null) || 
             (this.numberPrecision!=null &&
              this.numberPrecision.equals(other.getNumberPrecision()))) &&
            ((this.isRequired==null && other.getIsRequired()==null) || 
             (this.isRequired!=null &&
              this.isRequired.equals(other.getIsRequired()))) &&
            ((this.isSum==null && other.getIsSum()==null) || 
             (this.isSum!=null &&
              this.isSum.equals(other.getIsSum()))) &&
            ((this.isKey==null && other.getIsKey()==null) || 
             (this.isKey!=null &&
              this.isKey.equals(other.getIsKey()))) &&
            ((this.isEditable==null && other.getIsEditable()==null) || 
             (this.isEditable!=null &&
              this.isEditable.equals(other.getIsEditable()))) &&
            ((this.isVisible==null && other.getIsVisible()==null) || 
             (this.isVisible!=null &&
              this.isVisible.equals(other.getIsVisible()))) &&
            ((this.isForBill==null && other.getIsForBill()==null) || 
             (this.isForBill!=null &&
              this.isForBill.equals(other.getIsForBill()))) &&
            ((this.includeInSMS==null && other.getIncludeInSMS()==null) || 
             (this.includeInSMS!=null &&
              this.includeInSMS.equals(other.getIncludeInSMS()))) &&
            ((this.saveInTemplate==null && other.getSaveInTemplate()==null) || 
             (this.saveInTemplate!=null &&
              this.saveInTemplate.equals(other.getSaveInTemplate()))) &&
            ((this.requisiteTypes==null && other.getRequisiteTypes()==null) || 
             (this.requisiteTypes!=null &&
              java.util.Arrays.equals(this.requisiteTypes, other.getRequisiteTypes()))) &&
            ((this.hideInConfirmation==null && other.getHideInConfirmation()==null) || 
             (this.hideInConfirmation!=null &&
              this.hideInConfirmation.equals(other.getHideInConfirmation()))) &&
            ((this.attributeLength==null && other.getAttributeLength()==null) || 
             (this.attributeLength!=null &&
              this.attributeLength.equals(other.getAttributeLength()))) &&
            ((this.validators==null && other.getValidators()==null) || 
             (this.validators!=null &&
              java.util.Arrays.equals(this.validators, other.getValidators()))) &&
            ((this.menu==null && other.getMenu()==null) || 
             (this.menu!=null &&
              java.util.Arrays.equals(this.menu, other.getMenu()))) &&
            ((this.enteredData==null && other.getEnteredData()==null) || 
             (this.enteredData!=null &&
              java.util.Arrays.equals(this.enteredData, other.getEnteredData()))) &&
            ((this.defaultValue==null && other.getDefaultValue()==null) || 
             (this.defaultValue!=null &&
              this.defaultValue.equals(other.getDefaultValue()))) &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError())));
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
        if (getNameVisible() != null) {
            _hashCode += getNameVisible().hashCode();
        }
        if (getNameBS() != null) {
            _hashCode += getNameBS().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getNumberPrecision() != null) {
            _hashCode += getNumberPrecision().hashCode();
        }
        if (getIsRequired() != null) {
            _hashCode += getIsRequired().hashCode();
        }
        if (getIsSum() != null) {
            _hashCode += getIsSum().hashCode();
        }
        if (getIsKey() != null) {
            _hashCode += getIsKey().hashCode();
        }
        if (getIsEditable() != null) {
            _hashCode += getIsEditable().hashCode();
        }
        if (getIsVisible() != null) {
            _hashCode += getIsVisible().hashCode();
        }
        if (getIsForBill() != null) {
            _hashCode += getIsForBill().hashCode();
        }
        if (getIncludeInSMS() != null) {
            _hashCode += getIncludeInSMS().hashCode();
        }
        if (getSaveInTemplate() != null) {
            _hashCode += getSaveInTemplate().hashCode();
        }
        if (getRequisiteTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequisiteTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequisiteTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHideInConfirmation() != null) {
            _hashCode += getHideInConfirmation().hashCode();
        }
        if (getAttributeLength() != null) {
            _hashCode += getAttributeLength().hashCode();
        }
        if (getValidators() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValidators());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValidators(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMenu() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMenu());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMenu(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEnteredData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEnteredData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEnteredData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDefaultValue() != null) {
            _hashCode += getDefaultValue().hashCode();
        }
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Requisite_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameVisible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NameVisible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameBS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NameBS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberPrecision");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NumberPrecision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isRequired");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsRequired"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isSum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsSum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isEditable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsEditable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isVisible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsVisible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isForBill");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsForBill"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("includeInSMS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IncludeInSMS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saveInTemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SaveInTemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requisiteTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequisiteTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequisiteType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hideInConfirmation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "HideInConfirmation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeLength");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AttributeLength"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AttributeLength_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validators");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validators"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validator_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validator"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("menu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Menu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MenuItem"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enteredData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EnteredData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DataItem"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DefaultValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
