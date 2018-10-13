/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/21 下午5:39
 *
 *
 */
package com.feng.spring.entity;

/**
 * 公共类
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/21 下午5:39
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/21 下午5:39
 */
public class Constans {

    public static final String QR_PATH="/data/app/wechatservice/qrpath";

    public static final String REDIS_KEY="fyt:";

    public static final String UUID_KEY="_uuid";

    public static final String SEX_KEY="_sex";

    public static final String PROVIENCE_KEY="_province";

    public static final String SIGN_KEY="_sign";

    public static final int REDIS_TIME=60*60*2;

    public static String print(){
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("                   _ooOoo_\n");
        sb.append("                  o8888888o\n");
        sb.append("                  88\" . \"88\n");
        sb.append("                  (| -_- |)\n");
        sb.append("                  O\\  =  /O\n");
        sb.append("               ____/`---'\\____\n");
        sb.append("             .'  \\\\|     |//  `.\n");
        sb.append("            /  \\\\|||  :  |||//  \\ \n");
        sb.append("           /  _||||| -:- |||||-  \\ \n");
        sb.append("           |   | \\\\\\  -  /// |   |\n");
        sb.append("           | \\_|  ''\\---/''  |   |\n");
        sb.append("           \\  .-\\__  `-`  ___/-. /\n");
        sb.append("         ___`. .'  /--.--\\  `. . __\n");
        sb.append("      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n");
        sb.append("     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n");
        sb.append("     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n");
        sb.append("======`-.____`-.___\\_____/___.-`____.-'======\n");
        sb.append("                   `=---='\n");
        sb.append("...................................................\n");
        return sb.toString();
    }
}
