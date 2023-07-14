<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<html:form action="/private/favourite/list" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="links" value="${form.links}"/>
    <c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/favourite/list')}"/>

    <tiles:insert definition="paymentMain">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <tiles:put name="mainmenu" value="Payments"/>

        <tiles:put name="data" type="string">
            <div id="payments-templates">
                <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="title"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                    <tiles:put name="data">
                        <img class="icon" src="${imagePath}/icon_paymentOperatoin.png" alt=""/>
                        <h3><p>На этой странице Вы можете оплачивать услуги, совершать переводы средств,
                            погашать кредиты и выполнять другие банковские операции.</p>
                            <p>Чтобы создать <a href="#">регулярный платеж</a> или <a href="#">автоплатеж</a>
                            для автоматического исполнения Банком заполните соответствующие реквизиты
                            операции и перейдите по соответствующей ссылке.</p>
                        </h3>
                        <div class="clear"></div>
                        <div class="tabContainer">
                            <tiles:insert definition="tabs" flush="false">
                                <tiles:put name="tabItems">
                                    <tiles:insert definition="tabItem" flush="false" service="RurPayJurSB">
                                        <tiles:put name="style" value="inactive"/>
                                        <tiles:put name="title" value="Все платежи и переводы"/>
                                        <tiles:put name="action" value="/private/payments"/>
                                    </tiles:insert>
                                    <tiles:insert definition="tabItem" flush="false" service="FavouriteManagment">
                                        <tiles:put name="style" value="inactive"/>
                                        <tiles:put name="title" value="Мои платежи и шаблоны"/>
                                        <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates"/>
                                    </tiles:insert>
                                    <tiles:insert definition="tabItem" flush="false">
                                        <tiles:put name="style" value="active"/>
                                        <tiles:put name="title" value="Мое избранное"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                         </div>
                         <div class="add-to-favourite">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cogwheel"/>
                                <tiles:put name="commandHelpKey" value="button.cogwheel.help"/>
                                <tiles:put name="image" value="tuneup.png"/>
                                <tiles:put name="bundle" value="commonBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="action" value="/private/favourite/list.do?option=Settings"/>
                            </tiles:insert>
                        </div>
                        <div class="clear"></div>
                        <div class="payment-operations">
                           <h1>Избранные операции</h1>
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="link" model="no-pagination" name="links">
                                        <sl:collectionItem styleClass="align-left">
                                            <a href='<bean:write name='link' property="link" ignore="true"/>'>
                                                <bean:write name='link' property="name" ignore="true"/>
                                            </a>
                                        </sl:collectionItem>
                                    </sl:collection>
                                </tiles:put>
                                <tiles:put name="isEmpty" value="${empty links}"/>
                                <tiles:put name="emptyMessage">
                                    Пока у Вас нет ни одной избранной операции. Добавьте сюда ссылки на часто выполняемые операции. Для этого используйте на страницах системы кнопку
                                    <b>Добавить в Избранное</b>.<br/>
                                    <a href="" onclick="openHelp('${helpLink}'); return false"><b>Подробнее»</b></a>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>

        </tiles:put>
    </tiles:insert>
</html:form>