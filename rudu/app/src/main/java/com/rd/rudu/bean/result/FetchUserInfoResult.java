package com.rd.rudu.bean.result;

public class FetchUserInfoResult extends BaseResultBean<FetchUserInfoResult.UserInfoResult>
{

    public class UserInfoResult
    {
        public String id;
        public String telephone;
        public String disabled;
        public String wxId;
        public String zfbId;
        public String nickName;
        public String gender;
        public String avatar;
        public String birthday;
    }
}
