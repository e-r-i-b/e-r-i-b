<%--
  User: IIvanova
  Date: 12.04.2006
  Time: 17:49:03
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
  <script type="text/javascript">document.imgPath="${imagePath}/";</script>
  <br/>
  <br/>
  <span class="infoTitle backTransparent">Любимые ссылки</span>
  <br/>
  <table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
  <tr><td><phiz:menuLink action="/private/accounts/abstract" id="f1" serviceId="Abstract">Выписка</phiz:menuLink></td></tr>
  <tr><td><phiz:menuLink action="/private/payments/payment" param="form=MobilePayment"   serviceId="MobilePayment"   id="f2">Оплата сотовой связи</phiz:menuLink></td></tr>
  <tr><td><phiz:menuLink action="/private/payments/payment" param="form=InternetPayment" serviceId="InternetPayment" id="f3">Оплата интернет</phiz:menuLink></td></tr>
  <tr><td><phiz:menuLink action="/private/payments/payment" param="form=InternalPayment" serviceId="InternalPayment" id="f4">Перевод между счетами</phiz:menuLink></td></tr>
  <tr><td><phiz:menuLink action="/private/payments/payment" param="form=ElectricPayment" serviceId="ElectricPayment" id="f5">Оплата электроэнергии</phiz:menuLink></td></tr>
  </table>