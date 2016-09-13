# mchatWe
一个聊天工具，可以实现从服务器更新消息列表，上传联系人列表，以及发布消息的。但是只有写了一个测试服务器，没有真正的服务器。


Question1:
the test server is base on  tomacat 7 and eclipse.It's file name is  TestChatApplication in https://github.com/xbmchina/TestChatApplication



Question2:
The Config.java has a setting on SERVER_URL ,you should change your local ip address.

this ip contained by the SEVER_URL,which  should be your computer's wifi address(best)  or you local computer ip address.

such as:
public class Config {

//    public static final String SEVER_URL="http://demo.eoeschool.com/api/v1/nimings/io";
    public static final String SEVER_URL="http://172.25.178.1:8080/TestChatApplication/ChatApi.jsp";
    public static final String APP_ID="chatapplication";
}
