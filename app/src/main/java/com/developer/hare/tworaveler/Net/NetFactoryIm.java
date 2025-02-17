package com.developer.hare.tworaveler.Net;


import com.developer.hare.tworaveler.Model.BagModel;
import com.developer.hare.tworaveler.Model.CityModel;
import com.developer.hare.tworaveler.Model.CommentModel;
import com.developer.hare.tworaveler.Model.FollowModel;
import com.developer.hare.tworaveler.Model.LikeModel;
import com.developer.hare.tworaveler.Model.Request.UserReqModel;
import com.developer.hare.tworaveler.Model.Response.ResponseArrayModel;
import com.developer.hare.tworaveler.Model.Response.ResponseModel;
import com.developer.hare.tworaveler.Model.ScheduleDayModel;
import com.developer.hare.tworaveler.Model.ScheduleModel;
import com.developer.hare.tworaveler.Model.UserModel;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetFactoryIm {

    // #############################################################################################
    // INSERT
    // #############################################################################################

    // 회원 가입
    @POST("/users/sign_up")
    Call<ResponseModel<UserModel>> userSignUp(@Body UserReqModel model);

    // 일정 등록
    @Multipart
    @POST("/trips/insert_trip")
    Call<ResponseModel<ScheduleModel>> insertSchedule(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    // 일정 등록
    @POST("/trips/insert_trip")
    Call<ResponseModel<ScheduleModel>> insertSchedule(@Body ScheduleModel scheduleModel);

    // 세부 일정 등록
    @Multipart
    @POST("/trips/insert_detailed_trip")
    Call<ResponseModel<ScheduleDayModel>> insertDaySchedule(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    // 세부 일정 등록
    @POST("/trips/insert_detailed_trip")
    Call<ResponseModel<ScheduleDayModel>> insertDaySchedule(@Body ScheduleDayModel scheduleDayModel);

    // 가방 등록
    @Multipart
    @POST("/backpack/add_trip_item")
    Call<ResponseModel<BagModel>> insertBack(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    // 댓글 등록
    @POST("/comment/add_comment")
    Call<ResponseModel<CommentModel>> commentUpload(@Body CommentModel model);

    // 세부일정 댓글 등록
    @POST("/detailedComment/add_comment")
    Call<ResponseModel<CommentModel>> commentDetailUpload(@Body CommentModel model);

    // #############################################################################################
    // MODIFY
    // #############################################################################################

    // 프로필 정보 수정
    @POST("/users/profile/modify")
    Call<ResponseModel<UserModel>> modifyProfile(@Body UserReqModel model);

    // 프로필 이미지 수정
    @Multipart
    @POST("/users/profile/modify")
    Call<ResponseModel<UserModel>> modifyProfile(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

  /*  // 프로필 이미지 수정
    @Multipart
    @POST("/users/profile/modify_profile_pic")
    Call<ResponseModel<UserModel>> modifyProfileImage(@Part MultipartBody.Part part);*/

    // 일정 수정
    @POST("/trips/update_trip")
    Call<ResponseModel<ScheduleModel>> modifySchedule(@Body ScheduleModel model);

    // 일정 수정
    @Multipart
    @POST("/trips/update_trip")
    Call<ResponseModel<ScheduleModel>> modifySchedule(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    // 세부 일정 수정
    @POST("/trips/update_dtrip")
    Call<ResponseModel<ScheduleDayModel>> modifyScheduleDetail(@Body ScheduleDayModel model);

    // 세부  일정 수정
    @Multipart
    @POST("/trips/update_dtrip")
    Call<ResponseModel<ScheduleDayModel>> modifyScheduleDetail(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    // 좋아요
    @FormUrlEncoded
    @POST("/feed/like")
    Call<ResponseModel<LikeModel>> modifyLike(@Field("user_no") int user_no, @Field("trip_no") int trip_no);

    // 세부일정 좋아요
    @FormUrlEncoded
    @POST("/feed/dtrip_like")
    Call<ResponseModel<LikeModel>> modifyDetailLike(@Field("user_no") int user_no, @Field("dtrip_no") int dtrip_no);

    // 좋아요 취소
    @FormUrlEncoded
    @POST("/feed/unlike")
    Call<ResponseModel<LikeModel>> modifyUnLike(@Field("user_no") int user_no, @Field("trip_no") int trip_no);

    // 세부일정 좋아요 취소
    @FormUrlEncoded
    @POST("/feed/dtrip_unlike")
    Call<ResponseModel<LikeModel>> modifyDetailUnLike(@Field("user_no") int user_no, @Field("dtrip_no") int dtrip_no);

    // 팔로우
    @FormUrlEncoded
    @POST("/trips/follow")
    Call<ResponseModel<FollowModel>> selectFollow(@Field("user_no") int user_no, @Field("fuser_no") int fuser_no);

    // 팔로우 취소
    @FormUrlEncoded
    @POST("/trips/unfollow")
    Call<ResponseModel<FollowModel>> selectUnFollow(@Field("user_no") int user_no, @Field("fuser_no") int fuser_no);

    // 댓글 수정
    @POST("/comment/modify")
    Call<ResponseModel<CommentModel>> commentModify(@Body CommentModel model);

    // 세부일정 댓글 수정
    @POST("/detailedComment/modify")
    Call<ResponseModel<CommentModel>> commentDetailModify(@Body CommentModel model);

    // #############################################################################################
    // SELECT
    // #############################################################################################

    // 로그인
    @POST("/users/email_login")
    Call<ResponseModel<UserModel>> userSignIn(@Body UserReqModel model);

    // 카카오 로그인
    @FormUrlEncoded
    @POST("/users/kakao_login")
    Call<ResponseModel<UserModel>> kakaoSignIn(@Field("kakao_token") String token);

    // 페이스북 로그인
    @FormUrlEncoded
    @POST("/users/facebook_login")
    Call<ResponseModel<UserModel>> facebookSignIn(@Field("facebook_token") String token);

    // 여행 가방 목록
    @GET("/backpack/get_category_backpack")
    Call<ResponseArrayModel<BagModel>> selectBagList(@Query("user_no") int user_no, @Query("category_theme") String category_theme);

    // 도시 검색
    @GET("/location/get_location")
    Call<ResponseArrayModel<CityModel>> searchCity(@Query("city") String city);

    // 닉네임 검색
    @GET("/feed/getNickname/{user_no}/{nickname}")
    Call<ResponseArrayModel<ScheduleModel>> searchNickname(@Path("user_no") int user_no, @Path("nickname") String nickname);

    // 피드 도시 검색
    @GET("/feed/searchCity/{user_no}/{city}")
    Call<ResponseArrayModel<ScheduleModel>> searchFeedCity(@Path("user_no") int user_no, @Path("city") String city);

    // 피드 닉네임 검색
    @GET("/feed/searchNickname/{user_no}/{suser_no}")
    Call<ResponseArrayModel<ScheduleModel>> searchFeedNickname(@Path("user_no") int user_no, @Path("suser_no") int suser_no);

    // 여행 별 상세일정 조회
    @GET("/trips/find_dtrip/{user_no}/{trip_no}/{trip_date}")
    Call<ResponseArrayModel<ScheduleDayModel>> selectDetailSchedule(@Path("user_no") int user_no, @Path("trip_no") int trip_no, @Path("trip_date") String trip_date);

    // 프로필 정보 조회
    @GET("/users/profile")
    Call<ResponseModel<UserModel>> selectUserInfo(@Query("user_no") int user_no);

    // 프로필 피드 정보 조회
    @GET("/users/profile")
    Call<ResponseModel<ScheduleModel>> selectFeedInfo(@Query("user_no") int user_no);


    // 내 여행 목록 조회
    @GET("/trips/mytrip/{user_no}")
    Call<ResponseArrayModel<ScheduleModel>> selectMyScheduleList(@Path("user_no") int user_no);

    // 피드 정보 조회
    @GET("/feed/look/{user_no}/{scrollCount}")
    Call<ResponseArrayModel<ScheduleModel>> selectFeedList(@Path("user_no") int user_no, @Path("scrollCount") int scrollCount);

    // 댓글 조회
    @GET("/comment/look/{trip_no}")
    Call<ResponseArrayModel<CommentModel>> commentList(@Path("trip_no") int trip_no);

    // 세부일정 댓글 조회
    @GET("/detailedComment/get_detailed_comment")
    Call<ResponseArrayModel<CommentModel>> commentDetailList(@Query("dtrip_no") int dtrip_no, @Query("dtrip_date ") String dtrip_date);

    // #############################################################################################
    // DELETE
    // #############################################################################################

    // 로그 아웃
    @GET("/users/logout")
    Call<ResponseModel<String>> userLogout();

    // 회원 탈퇴
    @POST("/users/sign_out")
    Call<ResponseModel<ResponseModel<String>>> userSignOut(@Body UserReqModel model);

    // 가방 아이템 삭제
    @FormUrlEncoded
    @POST("/backpack/delete_item")
    Call<ResponseModel<String>> deleteBagItemList(@Field("item_no") ArrayList<Integer> item_no);

    // 일정 삭제
    @FormUrlEncoded
    @POST("/trips/delete_trip")
    Call<ResponseModel<String>> deleteTirp(@Field("trip_no") int trip_no);

    // 세부 일정 삭제
    @FormUrlEncoded
    @POST("/trips/delete_dtrip")
    Call<ResponseModel<String>> deleteDetailTirp(@Field("dtrip_no") int dtrip_no);
    // 가방 아이템 1개 삭제
//    @FormUrlEncoded
//    @POST("/backpack/delete_item")
//    Call<ResponseArrayModel<String>> oneDeleteBagItemList(@Field("item_no") int item_no);


    // 댓글 삭제
    @POST("/comment/delete")
    Call<ResponseModel<String>> commentDelete(@Body CommentModel model);

    // 세부일정 댓글 삭제
    @POST("/detailedComment/delete")
    Call<ResponseModel<CommentModel>> commentDetailDelete(@Body CommentModel model);

}