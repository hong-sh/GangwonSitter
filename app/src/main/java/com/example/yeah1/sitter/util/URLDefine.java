package com.example.yeah1.sitter.util;

/**
 * Created by YEAH1 on 2017. 9. 23..
 */
public class URLDefine {


    //JOB OFFER FUNCTION//

    /*
    * FUNCTION : APPLY SITTER
    * METHOD : POST
    * PARAMETER : user_email, job_offer_idx, hope_money_for_hour, appeal
    * RETURN : true/false
    * TEST URL : www.5minute-word.com/android/sitter/job_offer/apply_test.php
    * */
    public static final String APPLY_URL = "http://www.5minute-word.com/android/sitter/job_offer/apply.php";

    /*
    * FUNCTION : APPLY DETAIL
    * METHOD : GET
    * PARAMETER : user_email (sitter email)
    * RETURN : total_rate(float/double), introduce(string), appeal(string)
    * TEST URL : www.5minute-word.com/android/sitter/job_offer/apply_detail.php?user_email=test1
    * */
    public static final String APPLY_DETAIL_URL = "http://www.5minute-word.com/android/sitter/job_offer/apply_detail.php?";


    /*
    * FUNCTION : APPLY LIST
    * METHOD : GET
    * PARAMETER : user_email (mom email)
    * RETURN : LIST OF user_email(string), user_name(string), career(int), age(int),
    * household_cap(int), play_cap(int), edu_cap(int), help_cap(int), language_cap(int), hope_money_for_hour(int)
    * TEST URL : www.5minute-word.com/android/sitter/job_offer/apply_list.php?user_email=test
    * */
    public static final String APPLY_LIST_URL = "http://www.5minute-word.com/android/sitter/job_offer/apply_list.php?";

    /*
    * FUNCTION : CHOICE
    * METHOD : POST
    * PARAMETER : user_email(string), sitter_email(string), job_offer_idx(int), money_for_hour(int)
    * RETURN : true/false
    * TEST URL : www.5minute-word.com/android/sitter/job_offer/choice_test.php
    * */
    public static final String CHOICE_URL = "http://www.5minute-word.com/android/sitter/job_offer/choice.php";

    /*
    *  FUNCTION : CHOICE LIST
    *  METHOD : GET
    *  PARAMETER : user_email
    *  RETURN : LIST OF user_name, choice info , job offer info
    *  TEST URL : www.5minute-word.com/android/sitter/job_offer/choice_list.php?user_email=test
    * */
    public static final String CHOICE_LIST_URL = "http://www.5minute-word.com/android/sitter/job_offer/choice_list.php?";
    public static final String SITTER_CHOICE_LIST_URL = "http://www.5minute-word.com/android/sitter/job_offer/sitter_choice_list.php?";
    /*
    * FUNCTION : JOB OFFER LIST
    * METHOD : GET
    * PARAMETER : NO PARAMETER
    * RETURN : LIST OF user_name, job offer info
    * TEST URL : www.5minute-word.com/android/sitter/job_offer/job_offer_list.php
    * */
    public static final String JOB_OFFER_LIST_URL = "http://www.5minute-word.com/android/sitter/job_offer/job_offer_list.php?";

    /*
    * FUNCTION : OFFER STATE
    * METHOD : GET
    * PARAMETER : user_email (mom email)
    * RETURN : offer_state (0 --> need regist / 1 --> call apply list function)
    * TEST URL : www.5minute-word.com/android/sitter/job_offer/offer_state.php?user_email=test
    * */
    public static final String OFFER_STATE_URL = "http://www.5minute-word.com/android/sitter/job_offer/offer_state.php?";

    /*
    *  FUNCTION : REGIST OFFER
    *  METHOD : POST
    *  PARAMETER : user_email(string), mon_hope(int 0/1), tue_hope(int 0/1), wed_hope(int 0/1), thu_hope(int 0/1), fri_hope(int 0/1),
    *  sat_hope(int 0/1), sun_hope(int 0/1), baby_age(int), start_time(string 09:00) , end_time(string 18:00), money_for_hour(int),
    *  household_cap(int 0/1), play_cap(int 0/1), edu_cap(int 0/1), help_cap(int 0/1), language_cap(int 0/1), etc_hope(string), period(int)
    *  RETURN : true/false
    *  TEST URL : www.5minute-word.com/android/sitter/job_offer/regist_offer_test.php
    * */
    public static final String REGIST_JOB_OFFER_URL = "http://www.5minute-word.com/android/sitter/job_offer/regist_offer.php";


    //REVIEW FUNCTION//

    /*
    * FUNCTION : REVIEW LIST
    * METHOD : GET
    * PARAMETER : user_email (sitter email)
    * RETURN : LIST OF user_email(mom email), sitter email, review_contents, review_rate, write_date
    * TEST URL : www.5minute-word.com/android/sitter/review/review_list.php?user_email=test1
    * */
    public static final String REVIEW_LIST_URL = "http://www.5minute-word.com/android/sitter/review/review_list.php?";

    /*
    *  FUNCTION : WRITE REVIEW
    *  METHOD : POST
    *  PARAMETER : sitter_email, user_email, review_contents, review_rate
    *  RETURN : true/false
    *  TEST URL : www.5minute-word.com/android/sitter/review/write_review_test.php
    * */
    public static final String WRITE_REVIEW_URL = "http://www.5minute-word.com/android/sitter/review/write_review.php";


    //USER FUNCTION//

    /*
    * FUNCTION : INSERT SITTER INFO
    * METHOD : POST
    * PARAMETER : user_email, career, age, household_cap, play_cap, edu_cap, language_cap, introduce
    * RETURN : true/false
    * TEST URL : www.5minute-word.com/android/sitter/user/insert_sitter_info_test.php
    * */
    public static final String INSERT_SITTER_INFO_URL = "http://www.5minute-word.com/android/sitter/user/insert_sitter_info.php";

    /*
    * FUNCTION : JOIN
    * METHOD : POST
    * PARAMETER : user_email, user_pass, user_name, user_phone, user_addr, is_sitter(0/1)
    * RETURN : true/false
    * TEST URL : www.5minute-word.com/android/sitter/user/join_test.php
    * */
    public static final String JOIN_URL = "http://www.5minute-word.com/android/sitter/user/join.php";

    /*
    * FUNCTION : LOGIN
    * METHOD : POST
    * PARAMETER : user_email, user_pass
    * RETURN : USER INFO —> user_email, user_pass, user_name, user_phone, user_addr, is_sitter
    * TEST_URL : www.5minute-word.com/android/sitter/user/login_test.php
    * */
    public static final String LOGIN_URL = "http://www.5minute-word.com/android/sitter/user/login.php";

    /*
    * FUNCTION : SITTER INFO
    * METHOD : GET
    * PARAMETER : user_email
    * RETURN : SITTER INFO —> user_email ,total_rate , career , age , household_cap , play_cap , edu_cap , help_cap ,language_cap ,introduce
    * TEST URL : www.5minute-word.com/android/sitter/user/sitter_info.php?user_email=test1
    * */
    public static final String SITTER_INFO_URL = "http://www.5minute-word.com/android/sitter/user/sitter_info.php?";

    //SURVEY FUNCTION//

    /*
    * FUNCTION : SURVEY LIST
    * METHOD : GET
    * PARAMETER : NULL
    * RETURN : SURVEY LIST
    * TEST URL : www.5minute-word.com/android/sitter/survey/survey_list.php
    * */
    public static final String SURVEY_LIST_URL = "http://www.5minute-word.com/android/sitter/survey/survey_list.php";

    /*
    * FUNCTION : INSERT USER SURVEY
    * METHOD : POST
    * PARAMETER : user_email, user_answer1, user_answer2, user_answer3, … , user_answer19
    * RETURN : true / false
    * TEST URL : www.5minute-word.com/android/sitter/survey/insert_user_survey_test.php
    * */
    public static final String INSERT_USER_SURVEY_URL = "http://www.5minute-word.com/android/sitter/survey/insert_user_survey.php";

    //SITTER OFFER COMPANY URL LIST//
    public static final String API_KEY = "4e53615264726b7337317261676773";
    public static final String[] OPENDATA_URLS =
            {
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-chuncheon/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-gangneung/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-sokcho/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-wonju/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-pyeongchang/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-hongcheon/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-yangyang/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-hoengseong/1/100/",
                    "http://data.gwd.go.kr/apiservice/" + API_KEY + "/json/gwcgcom-postpartum_caretaker_provides-donghae/1/100/"
            };

    public static final String[] RESULTKEY =
            {
                    "gwcgcom-postpartum_caretaker_provides-chuncheon",
                    "gwcgcom-postpartum_caretaker_provides-gangneung",
                    "gwcgcom-postpartum_caretaker_provides-sokcho",
                    "gwcgcom-postpartum_caretaker_provides-wonju",
                    "gwcgcom-postpartum_caretaker_provides-pyeongchang",
                    "gwcgcom-postpartum_caretaker_provides-hongcheon",
                    "gwcgcom-postpartum_caretaker_provides-yangyang",
                    "gwcgcom-postpartum_caretaker_provides-hoengseong",
                    "gwcgcom-postpartum_caretaker_provides-donghae"
            };

//    public static final String YANGYANG_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-yangyang/1/100/";
//    public static final String PYEONGCHANG_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-pyeongchang/1/100/";
//    public static final String HOENGSEONG_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-hoengseong/1/100/";
//    public static final String HONGCHEON_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-hongcheon/1/100/";
//    public static final String SOKCHO_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-sokcho/1/100/";
//    public static final String GANGNEUNG_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-gangneung/1/100/";
//    public static final String WONJU_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-wonju/1/100/";
//    public static final String CHUNCHEON_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-chuncheon/1/100/";
//    public static final String DONGHAE_URL = "http://data.gwd.go.kr/apiservice/"+API_KEY+"/json/gwcgcom-postpartum_caretaker_provides-donghae/1/100/";


}