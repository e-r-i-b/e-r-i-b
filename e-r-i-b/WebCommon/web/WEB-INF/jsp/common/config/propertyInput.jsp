<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<%--
Шаблон для отображения конкретной настройки.
fieldName - имя поля, для извлечения настройки.
fieldDesc - название настройки, отображаемое пользователю.
disabled - запретить редактирование настройки.

fieldType - тип параметра (text/select)
textSize - размер текстового поля
textMaxLength - максимальная длина текстового поля.
selectItems - список селектов в формате <option>@<отображаемое имя>|<option>@<отображаемое имя>|...|<option>@<отображаемое имя>
format - форматирование текста.

--%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="inputName" value="field(${fieldName})"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="showCheckbox" value="${form.replication}"/>

<c:if test="${showCheckbox == 'true'}">
    <div>
        <html:checkbox styleId="property${fieldName}" property="selectedProperties" value="${fieldName}"/>
        <label for="property${fieldName}"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
        <html:hidden property="${inputName}"/>
    </div>
</c:if>
<c:choose>
    <c:when test="${fieldType == 'text' && not empty format}">
        <input type="text" size="${textSize}" maxlength="${textMaxLength}" name="${inputName}" value="<bean:write name="form" property="${inputName}" format="${format}"/>" class="${inputClass}"  <c:if test="${disabled or showCheckbox == 'true'}"> disabled </c:if>/>  ${fieldDesc}
    </c:when>
    <c:when test="${fieldType == 'text'}">
        <html:text property="${inputName}" size="${textSize}" maxlength="${textMaxLength}" disabled="${disabled or showCheckbox == 'true'}" readonly="${readonly}"/>   ${fieldDesc}
    </c:when>
    <c:when test="${fieldType == 'textarea'}">
        <html:textarea property="${inputName}" rows="${textSize}" cols="${textMaxLength}" disabled="${disabled or showCheckbox == 'true'}"/>
    </c:when>
    <c:when test="${fieldType == 'checkbox' && onclickFunc != ''}" >
        <html:checkbox property="${inputName}" disabled="${disabled or showCheckbox == 'true'}" onclick="${onclickFunc}"/> ${fieldDesc}
    </c:when>
    <c:when test="${fieldType == 'checkbox' or (fieldType == 'switcher' and (disabled or showCheckbox == 'true'))}" >
        <html:checkbox property="${inputName}" disabled="${disabled or showCheckbox == 'true'}"/> ${fieldDesc}
    </c:when>
    <c:when test="${fieldType == 'select'}">
        <html:select property="${inputName}" disabled="${disabled or showCheckbox == 'true'}" styleClass="${inputClass}">
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
            <div class="radioBtn"><html:radio property="${inputName}" value="${radioValue}"  disabled="${disabled or showCheckbox == 'true'}" styleClass="float"/> <label class="radioLabel">${radioShowName}</label></div>
        </c:forTokens>
    </c:when>
    <c:when test="${fieldType == 'switcher'}">
        <c:set var="propertySwitchId" value="${phiz:generateUuid(8)}"/>
        <div class="switchery-container-css3" id="propSwitch${propertySwitchId}">
            <div class="switchery-container-css3 ">
                <html:checkbox property="${inputName}" disabled="${disabled or showCheckbox == 'true'}"/>
                <div class="switchery-css3 css3 ${fieldName}">
                    <small class="switchery-control-css3"></small>
                    <div class="switchery-text onText">вкл</div>
                    <div class="switchery-text offText">выкл</div>
                </div>
            </div>
        </div>
        ${fieldDesc}
        <script type="text/javascript">
            doOnLoad(function(){
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
                <c:if test="${not empty onclickFunc}">
                    var func${propertySwitchId} = ${onclickFunc};
                    $("#propSwitch${propertySwitchId}").click(function(){
                        func${propertySwitchId}($('[name=${inputName}]')[0].checked);
                    });
                </c:if>
            })
        </script>
    </c:when>
    <c:otherwise><p color="red">Не указан тип поля для заполнения</p></c:otherwise>
</c:choose>