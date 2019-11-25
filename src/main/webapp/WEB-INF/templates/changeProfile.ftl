<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>

    <div class="my-container">
        <div class="reg-container">
            <h1>Изменение профиля</h1>

            <#if errors?has_content>
                <div id="error_list">
                    <#list errors as error>
                        <p>${error}</p>
                    </#list>
                </div>
            <#else>
            </#if>

            <form action="/changeProfile" method="post" enctype="multipart/form-data">



                <div id="namer">
                    <input type="hidden" name="user_id" id="user_id" value="${user.getId()}"/>
                    <input type="hidden" name="path" id="path" value="${path}"/>


                    <div id="namer-input">
                        <input type="text" id="nick" name="nick"  value="${user.getNick()}" placeholder="ник" required>
                    </div>

                    <div id="namer-input">
                        <input type="text" id="email" name="email" value="${user.getEmail()}" placeholder="email">
                    </div>

                    <div id="namer-input">
                        <p>Новый пароль</p>
                        <input type="password" id="password" name="password" placeholder="Новый пароль" required>
                    </div>

                    <div id="namer-input">
                        <p>Старый пароль</p>
                        <input type="password" id="old_password" name="old_password" placeholder="Старый пароль" required>
                    </div>

                    <div id="namer-input">
                        <input type="file" id="photo" name="photo">
                    </div>

                </div>



                <button type="submit" class="btn btn-danger btn-lg" >Сохранить
                </button>
            </form>
        </div>
    </div>

</#macro>
<#macro title>
    <title>CommentChange</title>
</#macro>
<@display_page />