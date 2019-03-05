package com.example.qy.utils;

public class HttpQYUtils {
    private static final String ipUrl = "http://192.168.10.6:8080/QianYi/";

    // 密码登录
    private static final String loginPassWord = "login?";
    // 验证码登录
    private static final String validationLogin = "validation?";
    // 账号注册
    private static final String registered = "registered?";
    // 注册时完善资料
    private static final String material = "material?";
    // 找回密码
    private static final String changePassword = "ChangePassword?";
    // 用户协议
    private static final String user_agreement = "user_agreement.txt";
    // 根据手机号查询个人信息
    private static final String findPersonInfo = "FindPersonaInfo?";
    // 根据id查询个人信息
    private static final String findPersonalnfoWithId = "FindPersonalnfoWithId?";
    // 单独插入头像
    private static final String updateIcon = "updateIcon?";
    // 获取头像上传token
    private static final String iconToken = "getIconToken?";
    // 查询粉丝信息
    private static final String findFansDetails = "findFansDetails?";
    // 获取城市地质api
    private static final String address = "city.json";
    // 查询关注信息
    private static final String attention = "countAttention?";

    public static String getLoginPassWord(String loginPhone,String loginPwd,String loginId){
        return ipUrl+loginPassWord+"loginPhone="+loginPhone+"&loginPwd="+loginPwd+"&MEID="+loginId;
    }
    public static String getValidationLogin(String loginPhone,String phoneId){
        return ipUrl+validationLogin+"loginPhone="+loginPhone+"&MEID="+phoneId;
    }
    public static String getRegistered(String loginPhone,String loginPwd){
        return ipUrl+ registered+"loginPhone="+loginPhone+"&loginPwd="+loginPwd;
    }
    public static String getIconToken(String iconName){
        return ipUrl + iconToken+"iconName="+iconName;
    }



    /**
     *
     * @param loginPhone 手机号
     * @param infoNickname 昵称
     * @param infoSex 性别
     * @param infoBirthday 生日
     * @param infoHome 家乡
     * @param infoManifesto 宣言
     * @param infoSignature 签名
     * @param infoLiveNumber 直播号
     * @return
     */
    public static String getMaterial(String loginPhone,String infoNickname,String infoSex,String infoBirthday,String infoHome,String infoManifesto,String infoSignature,Integer infoLiveNumber){
        return ipUrl+material
                +"loginPhone="+loginPhone
                +"&infoNickname="+infoNickname
                +"&infoSex="+infoSex
                +"&infoBirthday=" +infoBirthday
                +"&infoHome=" +infoHome
                +"&infoManifesto="+infoManifesto
                +"&infoSignature="+infoSignature
                +"&infoLiveNumber="+infoLiveNumber;
    }
    /**
     *
     * @param loginPhone 手机号
     * @param infoSex 性别
     * @param infoBirthday 生日
     * @param infoHome 家乡
     * @param infoManifesto 宣言
     * @return
     */
    public static String getMaterial(String loginPhone,String infoSex,String infoBirthday,String infoHome,String infoManifesto){
        return ipUrl+material
                +"loginPhone="+loginPhone
                +"&infoNickname="
                +"&infoSex="+infoSex
                +"&infoBirthday=" +infoBirthday
                +"&infoHome=" +infoHome
                +"&infoManifesto="+infoManifesto
                +"&infoSignature="
                +"&infoLiveNumber=";
    }

    public static String getUser_agreement() {
        return ipUrl+"views/"+user_agreement;
    }

    public static String getAddress(){
        return ipUrl+"views/"+address;
    }

    public static String getChangePassword(String loginPhone,String loginPwd) {
        return ipUrl+changePassword+"loginPhone="+loginPhone+"&loginPwd="+loginPwd;
    }

    public static String getFindPersonalnfoWithId(int loginId){
        return ipUrl+findPersonalnfoWithId+"loginId="+loginId;
    }
    public static String getFindPersonInfo(String loginPhone){
        return ipUrl+findPersonInfo+"loginPhone="+loginPhone;
    }

    public static String getUpdateIcon(String loginPhone,String infoIcon){
        return ipUrl+updateIcon+"loginPhone="+loginPhone+"&infoIcon="+infoIcon;
    }

    public static String getFindFansDetails(String loginPhone){
        return ipUrl+findFansDetails+"loginPhone="+loginPhone;
    }

    public static String getAttention(String userId){
        return ipUrl+attention+"userId="+userId;
    }

    /**
     * 获取服务器视频
     */
    public static String getVideos(){
        return ipUrl+"queryVideo";
    }
}