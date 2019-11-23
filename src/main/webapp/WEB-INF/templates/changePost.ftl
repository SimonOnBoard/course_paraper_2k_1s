<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <form action="/changePost/" method="post" enctype="multipart/form-data">
        <img src="${post.getPhotoPath()}" width="150"/>
        <p><input type="file" name="photo" />Вы можете выбрать новою картинку</p>
        <input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/>
        <input type="hidden" name="path" id="path" value="${path}"/>
        <p><input type="text" name="name" value="${post.getName()}"/></p>
        <select id="categories" name="categories">
            <#list categories as category>
                <option value = "${category}">${category}</option>
            </#list>
            <option selected value="${categoryA}">${categoryA}</option>
        </select>
        <p><textarea rows="10" cols="45" name="text" id="text">${post.getText()}</textarea></p>
        <input name="showAuth" type="checkbox" value="0"/>Show author information
        <p><input type="submit" value="Сохранить"/></p>
    </form>
</#macro>
<#macro title>
    <title>CommentChange</title>
</#macro>
<@display_page />