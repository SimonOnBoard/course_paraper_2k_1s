<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>



    <div class="my-container">
        <div class="reg-container">

            <form action="/changeComment" method="post">
                <h1>Изменить комментарий</h1>

                <input type="hidden" name="comment_id" id="comment_id" value="${comment.getId()}"/>
                <input type="hidden" name="path" id="path" value="${path}"/>

                <div id="namer">
                    <div id="namer-input">
                        <textarea class="comment-area" rows="5" cols="45" name="text" id="text">${comment.getText()}</textarea>
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