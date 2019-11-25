<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>

    <div class="my-container">
        <div class="reg-container">

            <form action="/changePost/" method="post" enctype="multipart/form-data">
                <h1>изменить пост</h1>

                <input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/>
                <input type="hidden" name="path" id="path" value="${path}"/>

                <div id="namer">
                    <div id="namer-input">
                        <input type="text" id="name" name="name" value="${post.getName()}" placeHolder="Name" required>
                    </div>


                    <div class="form-group">
                        <select class="form-control" id="categories" name="categories" style="height:1.8em;">
                            <#list categories as category>
                                <option value="${category}">${category}</option>
                            </#list>
                        </select>
                    </div>

                    <div id="namer-input">
                        <textarea class="comment-area" name=text id="text" rows="3" placeholder="Text">${post.getText()}</textarea>
                    </div>

                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" name="showAuth" value="0" style="height:auto;">
                        <label class="form-check-label" for="showAuth">Отображать имя автора</label>
                    </div>


                    <div id="namer-input">
                        <input type="file" id="photo" name="photo">
                    </div>
                </div>

                <button type="submit" class="btn btn-danger btn-lg">Сохранить</button>
            </form>
        </div>
    </div>

</#macro>
<#macro title>
    <title>CommentChange</title>
</#macro>
<@display_page />