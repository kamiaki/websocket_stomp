var stompClient = null;
//连接 websocket 地址
var connect_sw_url = "/connect_sw_url";
//订阅 接收 主地址
var main_receive_url = "/main_receive_url";
//订阅 接收 子地址1
var receive_url1 = "/receive_url1";
var receive_url2 = "/receive_url2";
var userTestUrl = "/userTestUrl";
var userTestName = "userTestName";
var userUrl = "/userUrl";
//发送 主地址
var main_send_url = "/main_send_url";
//发送 子地址1
var send_url1 = "/send_url1";

//连接后设置样式
function setConnected(connected) {
    //连接按钮不可用
    $("#connect").prop("disabled", connected);
    //断开按钮可用
    $("#disconnect").prop("disabled", !connected);
    //连接后消息接收表格显示,断开后隐藏
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    //清空消息接收内容
    $("#greetings").html("");
}

//连接websocket
function connect() {
    //new连接对象,设置连接地址
    var socket = new SockJS(connect_sw_url);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //设置样式
        setConnected(true);
        console.log('Connected: ' + frame);
        //设置订阅主地址+子地址1
        stompClient.subscribe(main_receive_url + receive_url1, function (greeting) {
            //打印文字到表格
            showGreeting("receive_url1");
            //将接受的str转为json对象.打印content这个对象属性内容
            showGreeting(JSON.parse(greeting.body).content);
        });
        //设置订阅主地址+子地址2
        stompClient.subscribe(main_receive_url + receive_url2, function (greeting) {
            showGreeting("receive_url2");
            showGreeting(greeting.body);
        });
        //设置用户订阅方式订阅的地址,要注意主地址和名字之间要要有斜杠隔开
        stompClient.subscribe(userTestUrl +"/"+ userTestName + userUrl, function (greeting) {
            showGreeting("receive_url3");
            showGreeting(greeting.body);
        });
    });
}

//断开连接
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

//发送消息
function sendName() {
    stompClient.send(main_send_url + send_url1, {}, JSON.stringify({'name': $("#name").val()}));
}

//表格里添加一条记录
function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

//jQyery初始化函数
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});

