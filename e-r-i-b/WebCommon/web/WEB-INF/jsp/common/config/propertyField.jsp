<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<%--
Шаблон для отображения конкретной настройки.
Для корректного отображения шаблона рекомендуется помещать его внутри таблицы.
fieldName - имя поля, для извлечения настройки.
fieldDescription - название настройки, отображаемое пользователю.
fieldHint - подсказка по настройке.
showHint - "bottom" - отображать подсказку под полем ввода; "right" справа от поля ввода.
disabled - запретить редактирование настройки.
inputDesc - комментарий после поля (например Кб)
inputDescLeft - комментарий перед полем (например "Не отображать, если требуемая доходность более ")
fieldType - тип параметра (text/textarea/select/checkbox/date/radio/custom)
textSize - размер текстового поля
textMaxLength - максимальная длина текстового поля.
selectItems - список селектов в формате <option>@<отображаемое имя>|<option>@<отображаемое имя>|...|<option>@<отображаемое имя>
onclick - действие при смене значения
onchange - действие при смене значения поля выбора
trStyle - стиль строки
requiredField - обязательно для заполнения (к названию добавляется *)

styleClass - класс стиля инпута (moneyField - для денежного)
imagePath - путь к изображениеям.
format - форматирование текста.
emptyFieldDescription - true, если нужно пустое название и выравнивание всех полей в столбик (работает при пустом fieldDescription)

--%>
<c:if test="${imagePath == ''}">
    <c:set var="imagePath" value="${skinUrl}/images"/>
</c:if>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="inputName" value="field(${fieldName})"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="showCheckbox" value="${form.replication}"/>
<c:set var="blackListFieldForReplication" value="${form.blackListFieldForReplication}"/>
<c:set var="fieldId" value="id_${fieldName}"/>

<c:if test="${not (showCheckbox == 'true' && phiz:contains(blackListFieldForReplication, fieldName))}">
    <c:if test="${tdStyle == ''}"><c:set var="tdStyle" value="Width200 LabelAll"/> </c:if>
    <c:if test="${showCheckbox == 'true'}">
        <tr class="replicationCheckboxTR">
            <td colspan="2">
                <html:checkbox styleId="property${fieldName}" property="selectedProperties" value="${fieldName}"/>
                <label for="property${fieldName}"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
                <html:hidden property="${inputName}"/>
            </td>
        </tr>
    </c:if>
    <tr <c:if test="${trStyle != ''}">class="${trStyle}"</c:if>>
        <c:choose>
            <c:when test="${fieldDescription != ''}">
                <td class="${tdStyle}  alignTop">${fieldDescription}<c:if test="${requiredField}"><span class="asterisk">*</span></c:if>:</td>
            </c:when>
            <c:otherwise>
                <c:if test="${emptyFieldDescription}"><td></td></c:if>
            </c:otherwise>
        </c:choose>
            <c:choose>
                <c:when test="${showHint == 'bottom' and showCheckbox == 'false'}"><td class="alignTop" colspan="2"></c:when>
                <c:when test="${showHint == 'right' or showHint == 'bottom' or showHint == 'none'}"><td class="alignTop"></c:when>
                <c:otherwise><td style="color:#ff0000;">не указано положение подсказки. </c:otherwise>
            </c:choose>

        <span class="formElements">
            <c:if test="${inputDescLeft != ''}">&nbsp;${inputDescLeft}</c:if>
            <c:choose>
                <c:when test="${fieldType == 'text' and (empty format or (disabled or showCheckbox == 'true'))}">
                    <html:text property="${inputName}" size="${textSize}" maxlength="${textMaxLength}" styleClass="${styleClass}" disabled="${disabled or showCheckbox == 'true'}"/>
                </c:when>
                <c:when test="${fieldType == 'text'}">
                    <input type="text" size="${textSize}" maxlength="${textMaxLength}" class="${styleClass}" name="${inputName}" value="<bean:write name="form" property="${inputName}" format="${format}"/>"/>
                </c:when>
                <c:when test="${fieldType == 'textarea'}">
                    <html:textarea property="${inputName}" styleClass="${styleClass}" rows="${textSize}" cols="${textMaxLength}" disabled="${disabled or showCheckbox == 'true'}" styleId="${fieldId}"/>
                </c:when>
                <c:when test="${fieldType == 'checkbox' or (fieldType == 'switcher' and (disabled or showCheckbox == 'true'))}" >
                    <html:checkbox property="${inputName}" styleClass="${styleClass}" disabled="${disabled or showCheckbox == 'true'}" onclick="${onclick}"/>
                </c:when>
                <c:when test="${fieldType == 'date'}" >
                    <html:text property="${inputName}" size="10" styleClass="dot-date-pick" disabled="${disabled or showCheckbox == 'true'}"/>
                </c:when>
                <c:when test="${fieldType == 'select'}">
                    <html:select property="${inputName}" onchange="${onchange}" disabled="${disabled or showCheckbox == 'true'}">
                        <c:forTokens var="selectItem" items="${selectItems}" delims="|">
                            <c:set var="firstElement" value="true"/>
                            <c:set var="selectName" value=""/>
                            <c:set var="selectShowName" value=""/>
                            <c:forTokens var="value" items="${selectItem}" delims="@">
                                <c:choose>
                                    <c:when test="${firstElement == 'true'}">
                                        <c:set var="selectName" value="${value}"/>
                                        <c:set var="firstElement" value="false"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="selectShowName" value="${value}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:forTokens>
                            <html:option value="${selectName}">${selectShowName}</html:option>
                        </c:forTokens>
                    </html:select>
                </c:when>
                <c:when test="${fieldType == 'radio'}">
                    <c:forTokens var="selectItem" items="${selectItems}" delims="|">
                        <c:set var="firstElement" value="true"/>
                        <c:set var="radioValue" value=""/>
                        <c:set var="radioShowName" value=""/>
                        <c:forTokens var="value" items="${selectItem}" delims="@">
                            <c:choose>
                                <c:when test="${firstElement == 'true'}">
                                    <c:set var="radioValue" value="${value}"/>
                                    <c:set var="firstElement" value="false"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="radioShowName" value="${value}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:forTokens>
                        <html:radio property="${inputName}" value="${radioValue}"  disabled="${disabled or showCheckbox == 'true'}" onclick="${onclick}"/> ${radioShowName} <br/>
                    </c:forTokens>
                </c:when>
                <c:when test="${fieldType == 'switcher'}">
                    <c:set var="propertySwitchId" value="${phiz:generateUuid(8)}"/>

                    <div class="switchery-container-css3" id="propSwitch${propertySwitchId}">
                        <div class="switchery-container-css3">
                            <html:checkbox property="${inputName}" styleId="${fieldName}"/>
                            <div class="switchery-css3 css3 ${fieldName}">
                                <small class="switchery-control-css3"></small>
                                <div class="switchery-text onText">вкл</div>
                                <div class="switchery-text offText">выкл</div>
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        doOnLoad(function() {
                            new Switchery($('[name=${inputName}]')[0],
                                    {
                                        color          : '#5db446'
                                        , borderColor    : '#5db446'
                                        , secondaryColor : '#cccccc'
                                        , className      : 'switchery-css3 ${fieldName}'
                                        , disabled       : false
                                        , disabledOpacity: 1
                                        , speed          : '0.1s'
                                    }
                            );
                            if (window.PIE)
                            {
                                var css3elements = $('#propSwitch${propertySwitchId}').find('[class*=css3]');
                                for (var i = 0; css3elements.length > i; i++)
                                {
                                    PIE.attach(css3elements[i]);
                                }
                            }
                            <c:if test="${not empty onclick}">
                                var func${propertySwitchId} = ${onclick};
                                $("#propSwitch${propertySwitchId}").click(function(){
                                    func${propertySwitchId}($('[name=${inputName}]')[0].checked);
                                });
                            </c:if>
                        });
                    </script>
                </c:when>
                <c:when test="${fieldType == 'custom'}">
                    ${customField}
                </c:when>
                <c:otherwise><p color="red">Не указан тип поля для заполнения</p></c:otherwise>
            </c:choose>
            <c:if test="${inputDesc != ''}">&nbsp;${inputDesc}</c:if>
        </span>

            <c:if test="${showHint == 'bottom' and showCheckbox == 'false'}">
                <div class="clear"></div>
                <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" class="infoIconTbl"/>&nbsp;
                <span class="propertyDescText">${fieldHint}</span>
            </c:if>

        </td>
        <c:if test="${showHint == 'right' and showCheckbox == 'false'}">
            <td><span class="propertyDesc">${fieldHint}</span></td>
        </c:if>
    </tr>
</c:if>
