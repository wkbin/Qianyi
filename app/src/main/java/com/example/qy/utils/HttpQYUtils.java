package com.example.qy.utils;

import android.util.Log;

public class HttpQYUtils {
    private static final String ipUrl = "http://192.168.10.6:8080/QianYi/";

//    private static final String ipUrl = "http://ruianshen.oicp.io/QianYi/";

    // 密码登录
    private static final String loginPassWord = "login?";
    // 验证码登录
    private static final String validationLogin = "validation?";
    // 账号注册
    private static final String registered = "registered?";
    // 完善资料
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
    // 查看粉丝用户信息
    private static final String findFansDetails = "findFansDetails?";
    // 获取城市地址api
    private static final String address = "city.json";
    // 统计关注数量
    private static final String attention = "countAttention?";
    // 查看关注用户信息
    private static final String showAttention = "showAttention?";
    // 获取积分详情
    private static final String findByIntegrals = "findbyIntegrals?";
    // 取消关注
    private static final String offAttention = "offAttention?";
    // 分页查询视频
    private static final String selectVideoWithLikesAndCommentsCount = "selectVideoWithLikesAndCommentsCount?";
    // 视频点赞
    private static final String addLike = "addLike?";
    // 取消点赞
    private static final String delLike = "delLike?";
    // 判断是否签到
    private static final String judgeDailyAttendance = "judgeDailyAttendance?";
    // 积分签到
    private static final String signin = "signin?";
    // 今日签到总积分
    private static final String statisticsIntegralToday = "statisticsIntegralToday?";

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
     * @param infoNickname 昵称
     * @param infoSex 性别
     * @param infoBirthday 生日
     * @param infoHome 家乡
     * @param infoSignature 签名
     * @return
     */
    public static String getMaterial(String loginPhone,String infoNickname,String infoSex,String infoBirthday,String infoHome,String infoSignature){
        String url = ipUrl+material
                +"loginPhone="+loginPhone
                +"&infoNickname="+infoNickname
                +"&infoSex="+infoSex
                +"&infoBirthday=" +infoBirthday
                +"&infoHome=" +infoHome
                +"&infoSignature="+infoSignature
                +"&infoLiveNumber=";
        Log.d("888","url == "+url);
        return url;
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

    public static String getFindFansDetails(int userId,int page){
        String url = ipUrl+findFansDetails+"userId="+userId+"&pn="+page;
        Log.d("888","url == "+url);
        return url;
    }

    public static String getAttention(String userId){
        return ipUrl+attention+"userId="+userId;
    }

    public static String getShowAttention(int followId,int page){
        return ipUrl+showAttention+"followId="+followId+"&page="+page;
    }

    public static String getFindByIntegrals(int userId){
        return ipUrl+findByIntegrals+"userId="+userId;
    }


    public static String getOffAttention(int userId,String users){
        String url = ipUrl+offAttention+"followId="+userId+"&userIdArrary="+users;
        Log.d("666","url = "+url);
        return url;
    }

    public static String getSelectVideo(int userId,int pn){
        String url = ipUrl+selectVideoWithLikesAndCommentsCount+"userId="+userId+"&pn="+pn;
        Log.d("666","url = "+url);
        return url;
    }

    public static String getAddLike(int user_id,String video_id){
        return ipUrl+addLike+"fromuid="+user_id+"&videoid="+video_id;
    }

    public static String getDelLike(int user_id,String video_id){
        return ipUrl+delLike+"fromuid="+user_id+"&videoid="+video_id;
    }

    public static String getJudgeDailyAttendance(int user_id,String integralType){
        return ipUrl+judgeDailyAttendance+"userId="+user_id+"&integralType="+integralType;
    }

    public static String getSignin(int userId,String integralNumber,String integralType){
        return ipUrl+signin+"userId="+userId+"&integralNumber="+integralNumber+"&integralType="+integralType;
    }

    public static String getStatisticsIntegralToday(){
        return "";
    }

    /**
     * 获取服务器视频
     */
    public static String getVideos(){
        return ipUrl+"queryVideo";
    }

}