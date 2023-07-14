<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<div class="windowEditFields" id="rcView" <c:if test="${form.actionType!='view'}"> style="display:none" </c:if> >
    <div class="documentNameView">
        <span class="documentName">${docName}</span>
    </div>
    <div class="clear"></div>

    <tiles:insert definition="invoicesSticker">
        <tiles:put name="cssClass" value="usrDocLinkList"/>
    </tiles:insert>

    <div class="windowEditFieldsContainer">
        <table>
            <tr>
                <c:if test="${not empty form.basketIndetifierType.attributes['SERIES']}"><td class="fieldName">${form.basketIndetifierType.attributes['SERIES'].name}</td></c:if>
                <c:if test="${not empty form.basketIndetifierType.attributes['NUMBER']}"><td class="fieldName">${form.basketIndetifierType.attributes['NUMBER'].name}</td></c:if>
            </tr>
            <tr>
                <c:if test="${not empty form.basketIndetifierType.attributes['SERIES']}"><td class="fieldData">${form.fields.series}</td></c:if>
                <c:if test="${not empty form.basketIndetifierType.attributes['NUMBER']}"><td class="fieldData">${form.fields.number}</td></c:if>
            </tr>
        </table>
    </div>

    <c:choose>
        <c:when test="${isConfirmPage}">
            <div class="saveDocumentButtonBlock">
                <tiles:insert definition="confirmButtons" flush="false">
                    <tiles:put name="ajaxUrl" value="/private/async/userprofile/editIdentifier"/>
                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                    <tiles:put name="anotherStrategy" value="false"/>
                    <tiles:put name="needWindow" value="false"/>
                    <tiles:put name="winConfirmName" value="addIdetnifierBasketRC"/>
                </tiles:insert>
            </div>
        </c:when>
        <c:otherwise>
            <div class="buttonsArea">
                <div class="editButtonContainer float">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.changeInfo"/>
                        <tiles:put name="commandHelpKey" value="button.changeInfo"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                        <tiles:put name="onclick" value="changeViewMode('rc');"/>
                        <tiles:put name="viewType" value="lightGrayProfileButton"/>
                    </tiles:insert>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<div class="windowEditFields" id="rcEdit" <c:if test="${form.actionType=='view'}"> style="display:none" </c:if> >
    <div class="documentNameView">
        <span id="documentName">${docName}</span>
        <a class="productTitleDetailInfoEditBullet" onclick="showEditDocumentName();"></a>
    </div>
    <div class="documentNameEdit">
        <html:text property="field(name)" size="30" maxlength="100" styleId="fieldNameRC"  value="${docName}"/>
    </div>
    <div class="clear"></div>
    <c:if test="${form.id == null}">
        <div class="documentDesc">
            <bean:message key="label.document.add.hint" bundle="userprofileBundle"/><c:if test="${accessBasket}"> <bean:message key="label.document.add.hint.with.basket" bundle="userprofileBundle"/></c:if>
        </div>
    </c:if>
    <div class="windowEditFieldsContainer">
        <table>
            <tr>
                <c:if test="${not empty form.basketIndetifierType.attributes['SERIES']}"><td class="fieldName">${form.basketIndetifierType.attributes['SERIES'].name}</td></c:if>
                <c:if test="${not empty form.basketIndetifierType.attributes['NUMBER']}"><td class="fieldName">${form.basketIndetifierType.attributes['NUMBER'].name}</td></c:if>
            </tr>
            <tr>
                <c:if test="${not empty form.basketIndetifierType.attributes['SERIES']}"><td class="fieldData"><div class="fieldInput"><html:text property="field(series)" size="10" styleClass="docNumberInput"/>  <div class="errorPofileDiv" style="display: none"></div></div></td></c:if>
                <c:if test="${not empty form.basketIndetifierType.attributes['NUMBER']}"><td class="fieldData"><div class="fieldInput"><html:text property="field(number)" size="30" styleClass="docNumberInput"/>  <div class="errorPofileDiv" style="display: none"></div></div></td></c:if>
            </tr>
        </table>
    </div>
    <div class="clear"></div>
    <div class="saveDocumentButtonBlock">
        <div class="float">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandHelpKey" value="button.save"/>
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="saveIdentifier('RC');"/>
            </tiles:insert>
        </div>
        <div class="hintSms">
            Для сохранения изменений необходимо подтверждение <b>SMS-паролем</b>
        </div>
        <c:if test="${not empty form.id and form.id != 0}">
            <tiles:insert definition="profileButton" flush="false">
                <tiles:put name="commandText" value="Удалить документ"/>
                <tiles:put name="onclick" value="removeIdentifier('${form.id}', 'RC');"/>
                <tiles:put name="viewType" value="removeIdentButton"/>
                <tiles:put name="icon" value="removeIcon"/>
            </tiles:insert>
        </c:if>
    </div>
</div>