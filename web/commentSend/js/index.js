function CommentSend() {
    $.ajax({
        url: '/MyCommentServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.length; i++) {
                html +=
                    '<div class="part_1">' +
                    '<p class="content">' + data[i].commentContent + '</p>'
                    + '<div class="text"><p>' + data[i].formatCommentTime + ' 评论了 ' + data[i].nickname + '的微博: '
                    + data[i].weiboContent + '</p></div>' +
                    '</div>';
                noApplicationRecord.innerHTML = html
            }
        },
        async: true
    });
}

window.onload = CommentSend();