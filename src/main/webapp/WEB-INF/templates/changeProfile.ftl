<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <#if errors?has_content>
    <#list errors as error>
        <p>${error}</p>
    </#list>
    <#else>
    </#if>
    <form action="/changeProfile/" method="post" enctype="multipart/form-data">
        <img src="${user.getPhotoPath()}" width="150"/>
        <p><input type="file" name="photo" />Вы можете выбрать новою картинку</p>
        <input type="hidden" name="user_id" id="user_id" value="${user.getId()}"/>
        <input type="hidden" name="path" id="path" value="${path}"/>
        <p><input type="text" name="nick" value="${user.getNick()}"/></p>
        <p><input type="text" name="mail" value="${user.getEmail()}"/></p>
        <p>Введите новый пароль</p>
        <input type="password" id="password" name="password"/>
        <p>Введие старый пароль</p>
        <input type="password" id="old_password" name="old_password"/>
        <p><input type="submit" value="Сохранить"/></p>
    </form>
</#macro>
<#macro title>
    <title>CommentChange</title>
</#macro>
<@display_page />