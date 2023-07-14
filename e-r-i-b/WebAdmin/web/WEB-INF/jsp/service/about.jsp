<%--
  Created by IntelliJ IDEA.
  User: lukina
  Date: 19.05.2009
  Time: 9:41:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/about">
   <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="logMain">
    <tiles:put name="submenu" type="string" value="About"/>
      <!-- заголовок -->
   <tiles:put name="pageTitle" type="string">О программе</tiles:put>

      <!-- данные -->
      <tiles:put name="data" type="string">
         <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="about"/>
            <tiles:put name="name" value="О программе «Интернет-клиент для физических лиц»."/>
            <tiles:put name="data">
              <tr>
                  <td>  Версия:  </td>
                  <td>
                      <c:out value="${form.version}"/>
                  </td>
              </tr>
              <tr>
                  <td>  Сборка: </td>
                  <td>
                      <c:out value="${form.revision}"/>
                  </td>
              </tr>
               <tr>
                  <td>  Разработчик: </td>
                  <td>
                     <a href="http://www.softlab.ru" target="_blank"> R-Style Softlab</a>
                  </td>
              </tr>
            </tiles:put>
            <tiles:put name="alignTable" value="center"/>
         </tiles:insert>
      </tiles:put>
   </tiles:insert>
</html:form>