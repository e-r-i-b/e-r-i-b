<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--
Базовый элемент страницы оповещений для мобильных браузеров.
	title         	- заголовк страницы
	data          	- данные отображаемые в белом "всплывающем" окне
	footer      	- данные расположеные ниже background изображения
--%>
<tiles:importAttribute/>
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>

    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/mobileBrowsers.css"/>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>
<body>
    <tiles:insert definition="googleTagManager"/>
	<form action="" method="post">
			<div id="wrapper">
				<div id="content">
					<div class="enterBlock">
						<div class="shadow alignCenter">
                            <c:if test="${not empty data}">
                               <span class="titleText">${data}</span>
                            </c:if>
						</div>
                        <c:if test="${not empty footer}">
						    <span class="connect">${footer}</span>
                        </c:if>
					</div>
				</div>
			</div>
	</form>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
</body>
</html>