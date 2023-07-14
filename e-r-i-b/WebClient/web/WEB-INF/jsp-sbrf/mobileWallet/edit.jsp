<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<tiles:importAttribute/>
<html:form action="/private/mobilewallet/edit" onsubmit="return setEmptyAction()">
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    </c:if>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <tiles:insert definition="userProfile">
        <tiles:put name="submenu" type="String" value="EditMobileWallet"/>
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="pageTitle" type="String" value="Изменение мобильного кошелька"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Изменение мобильного кошелька"/>
                <tiles:put name="data">
                    <div id="profile" onkeypress="onEnterKey(event)">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/icon_mobileWallet.png"/>
                            <tiles:put name="description">
                                <h3>
                                    Укажите размер  мобильного  кошелька.  Максимально возможное значение - <bean:write name="form" property="limitAmountValue" format="0.00"/> руб.
                                    <br/>
                                    <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
                                    <span class="text-red">*</span>
                                    <span class="text-gray">.</span>
                                </h3>
                            </tiles:put>
                        </tiles:insert>
                        <div class="payments-tabs">
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title" value="Сумма:"/>
                                <tiles:put name="data">
                                    <html:text property="field(totalAmount)" styleClass="moneyField"/>&nbsp;руб.
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="fieldName" value="totalAmount"/>
                            </tiles:insert>

                            <div class="buttonsArea iebuttons">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"        value="button.cancel"/>
                                    <tiles:put name="commandHelpKey"        value="button.cancel.help"/>
                                    <tiles:put name="bundle"                value="commonBundle"/>
                                    <tiles:put name="action"                value="/private/accounts.do"/>
                                    <tiles:put name="viewType"              value="buttonGrey"/>
                                </tiles:insert>
                                <tiles:insert definition="confirmButtons" flush="false">
                                    <tiles:put name="ajaxUrl"               value="/private/async/mobilewallet/edit"/>
                                    <tiles:put name="confirmRequest"        beanName="confirmRequest"/>
                                    <tiles:put name="confirmStrategy"       beanName="confirmStrategy"/>
                                    <tiles:put name="preConfirmCommandKey"  value="button.preConfirm"/>
                                </tiles:insert>
                            </div>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>