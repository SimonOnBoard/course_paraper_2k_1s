<#include "base.ftl"/>

<#macro content>

    <form method="post" enctype="multipart/form-data">
        <p><input type="text" name="name"/></p>
        <select id="categories" name="categories">
            <#list categories as category>
                <option value = "${category}">${category}</option>
            </#list>
        </select>
        <p><textarea rows="10" cols="45" name="text"></textarea></p>
        <p><input type="file" name="photo" /></p>
        <input name="showAuth" type="checkbox" value="0"/>Show author information
        <p><input type="submit" value="save"></p>
    </form>
</#macro>
<#macro title>
    <title>AddPostPage</title>
</#macro>
<@display_page />