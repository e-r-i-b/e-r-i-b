<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/basket/subscription/edit" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="paymentsBasket">
        <tiles:put name="submenu" type="string" value="EditInvoiceSubscription"/>
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="pageTitle" value="Редактирование услуги"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="activeItem" value="searchAccounts"/>
                <tiles:put name="data">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="data">
                            <div class="invoiceSubscription">
                                ${form.html}
                                <div class="buttonsArea iebuttons">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                        <tiles:put name="bundle" value="commonBundle"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                        <tiles:put name="action" value="/private/basket/subscription/view.do?id=${form.id}"/>
                                    </tiles:insert>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey" value="button.save"/>
                                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                                        <tiles:put name="bundle" value="commonBundle"/>
                                        <tiles:put name="isDefault" value="true"/>
                                    </tiles:insert>
                                </div>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>