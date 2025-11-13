package com.arclearn.community.service.feishu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.JsonParser;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.contact.v3.model.BatchGetIdUserReq;
import com.lark.oapi.service.contact.v3.model.BatchGetIdUserReqBody;
import com.lark.oapi.service.contact.v3.model.BatchGetIdUserResp;
import com.lark.oapi.service.im.v1.model.CreateMessageReq;
import com.lark.oapi.service.im.v1.model.CreateMessageReqBody;
import com.lark.oapi.service.im.v1.model.CreateMessageResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequestMapping("/user")
@Tag(name = "用户接口", description = "获取用户信息")
public class UserController {


    @Resource
    private ApiService apiService;

//    http://localhost:8080/user/getTenantAccessToken?appId=cli_a8654a6ab138500e&appSecret=RUdihiVhtXa9BM3gHiIrwNi6Vyi56PJn
    @GetMapping ("/getTenantAccessToken")
    @Operation(summary = "获取tenant_access_token", description = "通过应用唯一标识，应用秘钥获取TenantAccessToken")
    public String getTenantAccessToken(@Parameter(description = "应用唯一标识", example = "cli_slkdjalasdkjasd") @RequestParam String appId,
                                          @Parameter(description = "应用秘钥", example = "dskLLdkasdjlasdKK") @RequestParam String appSecret){
        JSONObject jsonObject = JSONUtil.createObj().set("app_id", appId).set("app_secret", appSecret);
        String resp = HttpUtil.post("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal", jsonObject);
        return resp;
    }
    @GetMapping ("/getUserAccessToken")
    @Operation(summary = "获取user_access_token", description = "通过手机号或邮箱获取用户ID")
    public String getUserAccessToken(@Parameter(description = "应用唯一标识", example = "cli_slkdjalasdkjasd") @RequestParam String appId,
                                          @Parameter(description = "应用秘钥", example = "dskLLdkasdjlasdKK") @RequestParam String appSecret){
        JSONObject jsonObject = JSONUtil.createObj()
                .set("grant_type", "authorization_code")
                .set("app_secret", appSecret)
                .set("app_id", appId);

        String resp = HttpUtil.post("https://open.feishu.cn/open-apis/authen/v2/oauth/token", jsonObject);
        return resp;
    }

//    http://localhost:8080/user/getUserIdByPhoneOrEmail?userIdType=open_id&mobiles=15629820477&includeResigned=true
    @GetMapping ("/getUserIdByPhoneOrEmail")
    @Operation(summary = "通过手机号或邮箱获取用户ID", description = "通过手机号或邮箱获取用户ID")
    public String getUserIdByPhoneOrEmail(@Parameter(description = "用户 ID 类型:open_id：标识一个用户在某个应用中的身份。同一个用户在不同应用中的 Open ID 不同。了解更多：如何获取 Open ID\n" +
            "union_id：标识一个用户在某个应用开发商下的身份。同一用户在同一开发商下的应用中的 Union ID 是相同的，在不同开发商下的应用中的 Union ID 是不同的。通过 Union ID，应用开发商可以把同个用户在多个应用中的身份关联起来。了解更多：如何获取 Union ID？\n" +
            "user_id：标识一个用户在某个租户内的身份。同一个用户在租户 A 和租户 B 内的 User ID 是不同的。在同一个租户内，一个用户的 User ID 在所有应用（包括商店应用）中都保持一致。User ID 主要用于在不同的应用间打通用户数据。了解更多：如何获取 User ID？\n" +
            "email：以用户的真实邮箱来标识用户。\n" +
            "chat_id：以群 ID 来标识群聊。了解更多：如何获取群 ID", example = "open_id")  @RequestParam String userIdType,
                                          @Parameter(description = "邮箱",required = false) @RequestParam(required = false) List<String> emails,
                                          @Parameter(description = "手机号码", example = "15629820477",required = false) @RequestParam(required = false) List<String> mobiles,
                                          @Parameter(description = "查询结果是否包含离职员工的用户信息", example = "true") @RequestParam boolean includeResigned){
        BatchGetIdUserReqBody.Builder builder = BatchGetIdUserReqBody.newBuilder()
                .includeResigned(includeResigned);

        if (CollectionUtil.isNotEmpty(emails)) {
            builder.emails(ArrayUtil.toArray(emails,String.class));
        }
        if (CollectionUtil.isNotEmpty(mobiles)) {
            builder.mobiles(ArrayUtil.toArray(mobiles,String.class));
        }
        BatchGetIdUserReq req = BatchGetIdUserReq.newBuilder()
                .userIdType(userIdType)
                .batchGetIdUserReqBody(builder.build()).build();
        // 发起请求
        BatchGetIdUserResp resp = null;
        try {
            resp = apiService.getClient().contact().v3().user().batchGetId(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return Jsons.DEFAULT.toJson(resp.getData());
        }else {
            System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
            return Jsons.DEFAULT.toJson(resp.getData());
        }

    }
    @PostMapping("/CreateMessageReq")
    @Operation(summary = "创建消息接口", description = "创建消息")
    public String createdCalender(@Parameter(description = "用户 ID 类型:open_id：标识一个用户在某个应用中的身份。同一个用户在不同应用中的 Open ID 不同。了解更多：如何获取 Open ID\n" +
            "union_id：标识一个用户在某个应用开发商下的身份。同一用户在同一开发商下的应用中的 Union ID 是相同的，在不同开发商下的应用中的 Union ID 是不同的。通过 Union ID，应用开发商可以把同个用户在多个应用中的身份关联起来。了解更多：如何获取 Union ID？\n" +
            "user_id：标识一个用户在某个租户内的身份。同一个用户在租户 A 和租户 B 内的 User ID 是不同的。在同一个租户内，一个用户的 User ID 在所有应用（包括商店应用）中都保持一致。User ID 主要用于在不同的应用间打通用户数据。了解更多：如何获取 User ID？\n" +
            "email：以用户的真实邮箱来标识用户。\n" +
            "chat_id：以群 ID 来标识群聊。了解更多：如何获取群 ID", example = "open_id") @RequestParam String receiveIdType) {
        // 创建请求对象
        CreateMessageReq req = CreateMessageReq.newBuilder()
                .receiveIdType(receiveIdType)
                .createMessageReqBody(CreateMessageReqBody.newBuilder()
                        .receiveId("ou_7d8a6e6df7621556ce0d21922b676706ccs")
                        .msgType("text")
                        .content("{\"text\":\"test content\"}")
                        //随机id，选填
                        .uuid(UUID.fastUUID().toString())
                        .build())
                .build();
        // 发起请求
        CreateMessageResp resp = null;
        try {
            resp = apiService.getClient().im().v1().message().create(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 处理服务端错误
        if(!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return "失败";
        }else {
            System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
            return "成功";
        }
    }
}
