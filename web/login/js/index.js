var colorArray = [
    '#FF6633', '#FFB399', '#FF33FF', '#FFFF99', '#00B3E6',
    '#E6B333', '#3366E6', '#999966', '#99FF99', '#B34D4D',
    '#80B300', '#809900', '#E6B3B3', '#6680B3', '#66991A',
    '#FF99E6', '#CCFF1A', '#FF1A66', '#E6331A', '#33FFCC',
    '#66994D', '#B366CC', '#4D8000', '#B33300', '#CC80CC',
    '#66664D', '#991AFF', '#E666FF', '#4DB3FF', '#1AB399',
    '#E666B3', '#33991A', '#CC9999', '#B3B31A', '#00E680',
    '#4D8066', '#809980', '#E6FF80', '#1AFF33', '#999933',
    '#FF3380', '#CCCC00', '#66E64D', '#4D80CC', '#9900B3',
    '#E64D66', '#4DB380', '#FF4D4D', '#99E6E6', '#6666FF'];


var burst = new mojs.Burst({
    radius: {600: 10},
    duration: 3000,
    count: 80,
    children: {
        shape: 'polygon',
        points: 8,
        scale: {'rand(0, 5)': 0},
        fill: colorArray,
        angle: {'rand(0, 360)': 'rand(0, 360)'},
        duration: 'rand(1000, 7000)',
        delay: 'rand(0, 2000)'
    },

    onComplete: function onComplete() {
        this.generate().replay();
    }
}).replay();


$("#icon1").click(function () {
    var password = document.getElementById("password");
    if (password.type == "password") {
        password.setAttribute("type", "text");
    } else {
        password.setAttribute("type", "password");
    }
});

function fun() {
    var phone = $("#phonenumber").val();
    var pwd = $("#password").val();
    $.ajax({
        url: "/LoginInServlet",
        type: "get",
        datatype: "json",
        data: {
            loginId: phone,
            password: pwd
        },
        success: function (text) {
            var eval1 = eval(text);
            if (eval1.msg === "密码错误") {
                alert(eval1.msg);
                return;
            }
            window.location.href = eval1.path;
            alert(eval1.msg);
        }
    })
}