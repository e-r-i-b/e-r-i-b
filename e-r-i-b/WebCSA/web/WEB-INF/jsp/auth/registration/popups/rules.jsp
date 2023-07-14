<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<h2>ѕравила составлени€ логина и&nbsp;парол€</h2>
<br/>
<p><b>Ћќ√»Ќ:</b></p>
<ul>
    <li>длина от&nbsp;8 до&nbsp;30 символов</li>
    <li>должен содержать как минимум одну цифру и как минимум одну букву</li>
    <li>буквы должны быть только из латинского алфавита</li>
    <li>не может состо€ть из 10 цифр</li>
    <li>не должен содержать более 3-х одинаковых символов подр€д</li>
    <li>может содержать элементы пунктуации из списка Ц Ђ Ц @ _ -  .ї</li>
    <li>не чувствителен к регистру</li>
</ul>
<p><b>ѕј–ќЋ№:</b></p>
<ul>
    <li>длина не менее 8 символов</li>
    <li>должен содержать минимум 1 цифру</li>
    <li>не должен содержать более 3-х одинаковых символов подр€д</li>
    <li>не должен содержать более 3-х символов, расположенных р€дом в одном р€ду клавиатуры</li>
    <li>должен отличатьс€ от логина</li>
    <li>может содержать элементы пунктуации из списка Ђ Ц ! @ # $ % ^ & * ( ) _ - + : ; , .ї</li>
    <li>не должен повтор€ть старые пароли за последние 3 мес€ца</li>
</ul>