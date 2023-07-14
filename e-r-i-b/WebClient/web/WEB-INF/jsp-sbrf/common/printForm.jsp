<%--
  Created by IntelliJ IDEA.
  User: lukina
  Date: 10.06.2009
  Time: 12:11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/printForm" onsubmit="return setEmptyAction(event)">

<tiles:insert definition="accountInfo">
    <tiles:put name="mainmenu" value="Info"/>
    <tiles:put name="submenu" value="PrintForm"/>
    <tiles:put name="pageTitle">Печать бланков</tiles:put>

    <tiles:put name="data" type="string">

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="name" value="Печать бланков"/>
            <tiles:put name="data" type="string">
                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    Печать формы ПД-4
                </phiz:link>
                <p>Вы можете заполнить и распечатать платежный документ ПД-4 для оплаты в одном из подразделений Сберегательного банка.</p>

                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    <phiz:param name="page" value="nalog"/>
                    Печать формы ПД-4сб (налог)
                </phiz:link>
                <p>Вы можете заполнить и распечатать платежный документ ПД-4сб (налог) для оплаты в одном из подразделений Сберегательного банка.</p>

                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    <phiz:param name="page" value="pay"/>
                    Печать платежного поручения
                </phiz:link>
                <p>Вы можете заполнить и распечатать платежное поручение формы 0401060 для предъявления его в подразделение Сберегательного банка для исполнения операций по текущему счету или специальному счету нерезидента.</p>

                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    <phiz:param name="page" value="letter"/>
                    Печать инкассового поручения
                </phiz:link>
                 <p>Вы можете заполнить и распечатать инкассовое поручение формы 0401071 для предъявления его в подразделение Сберегательного банка для исполнения операций по текущему счету или специальному счету нерезидента.</p>
            </tiles:put>
         </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>