<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>


    <div class="my-container">
        <div class="reg-container">

            <form action="/changeProfile/" method="post" enctype="multipart/form-data">
                <h1>Sign in</h1>
                <small class="form-text text-white" style="margin-bottom: 1.7rem;"><a href="/registration">Sign
                        up</a></small>

                <#if errors?has_content>
                    <div id="error_list">
                    <#list errors as error>
                        <p>${error}</p>
                    </#list>
                    </div>
                <#else>
                </#if>


                <div id="namer">
                    <input type="hidden" name="user_id" id="user_id" value="${user.getId()}"/>
                    <input type="hidden" name="path" id="path" value="${path}"/>


                    <div id="namer-input">
                        <input type="text" id="nick" name="nick"  value="${user.getNick()}" placeholder="Ник" required>
                    </div>

                    <div id="namer-input">
                        <input type="text" id="email" name="email" value="${user.getEmail()}" placeholder="Email">
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



                <button type="button" class="btn btn-danger btn-lg" id="test_ajax" onclick="f()" value="Sign Up">Sign
                    in
                </button>
            </form>
        </div>
    </div>

</#macro>
<#macro title>
    <title>CommentChange</title>
</#macro>
<@display_page />