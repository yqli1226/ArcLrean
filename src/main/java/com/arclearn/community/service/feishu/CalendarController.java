package com.arclearn.community.service.feishu;

import com.arclearn.community.entity.Dto.feishu.CreatedCalenderReq;
import com.google.gson.JsonParser;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.calendar.v4.model.Calendar;
import com.lark.oapi.service.calendar.v4.model.CreateCalendarReq;
import com.lark.oapi.service.calendar.v4.model.CreateCalendarResp;
import com.lark.oapi.service.calendar.v4.model.DeleteCalendarReq;
import com.lark.oapi.service.calendar.v4.model.DeleteCalendarResp;
import com.lark.oapi.service.calendar.v4.model.ListCalendarReq;
import com.lark.oapi.service.calendar.v4.model.ListCalendarResp;
import com.lark.oapi.service.calendar.v4.model.PatchCalendarReq;
import com.lark.oapi.service.calendar.v4.model.PatchCalendarResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Service
@Tag(name = "日历", description = "日历增删改查接口")
public class CalendarController {
    // 构建client
    @Resource
    private ApiService apiService;

    @Operation(summary = "创建共享日历", description = "创建共享日历")
    public String createdCalender(@Parameter(description = "用户token", example = "t-g1044qeGEDXTB6NDJOGV4JQCYDGHRBARFTGT123") @RequestParam String userAccessToken,
                                  @RequestBody CreatedCalenderReq createdCalenderReq) {
        // 创建请求对象
        CreateCalendarReq req = CreateCalendarReq.newBuilder()
                .calendar(Calendar.newBuilder()
                        .summary(createdCalenderReq.getSummary())
                        .description(createdCalenderReq.getDescription())
                        .permissions(createdCalenderReq.getPermissions())
                        .color(createdCalenderReq.getColor())
                        .summaryAlias(createdCalenderReq.getSummaryAlias())
                        .build())
                .build();
        // 发起请求
        CreateCalendarResp resp = null;
        try {
            resp = apiService.getClient().calendar().v4().calendar().create(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 处理服务端错误
        if(!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return Jsons.DEFAULT.toJson(resp.getData());
        }else {
            System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
            return "成功";
        }
    }
    @Operation(summary = "删除共享日历", description = "删除共享日历")
    public String deleteCalender(@Parameter(description = "用户token", example = "t-g1044qeGEDXTB6NDJOGV4JQCYDGHRBARFTGT123") @RequestParam String userAccessToken,
                                 @Parameter(description = "日历 ID", example = "feishu.cn_xxxxxxxxxx@group.calendar.feishu.cn") @RequestParam String calendarId) {
        // 创建请求对象
        DeleteCalendarReq req = DeleteCalendarReq.newBuilder()
                .calendarId(calendarId)
                .build();
        // 发起请求
        DeleteCalendarResp resp = null;
        try {
            resp = apiService.getClient().calendar().v4().calendar().delete(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 处理服务端错误
        if(!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return Jsons.DEFAULT.toJson(resp.getData());
        }else {
            System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
            return "成功";
        }
    }
    @Operation(summary = "查询日历列表", description = "查询日历列表")
    public String getCalenderList(@Parameter(description = "用户token", example = "t-g1044qeGEDXTB6NDJOGV4JQCYDGHRBARFTGT123") @RequestParam String userAccessToken,
            @Parameter(description = "页面大小", example = "500") @RequestParam int pageSize ) {
        // 创建请求对象
        ListCalendarReq req = ListCalendarReq.newBuilder()
                .pageSize(pageSize)
                .build();
        ListCalendarResp resp = null;

        try {
            // 发起请求
            resp = apiService.getClient().calendar().v4().calendar().list(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 处理服务端错误
        if(!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return Jsons.DEFAULT.toJson(resp.getData());
        }else {
            System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
            return Jsons.DEFAULT.toJson(resp.getData());
        }
    }
    @Operation(summary = "修改日历列表", description = "修改日历列表")
    public String updataCalender(@Parameter(description = "用户token", example = "t-g1044qeGEDXTB6NDJOGV4JQCYDGHRBARFTGT123") @RequestParam String userAccessToken
            ,@RequestBody CreatedCalenderReq createdCalenderReq) {
        // 创建请求对象
        PatchCalendarReq req = PatchCalendarReq.newBuilder()
                .calendar(Calendar.newBuilder()
                        .summary(createdCalenderReq.getSummary())
                        .description(createdCalenderReq.getDescription())
                        .permissions(createdCalenderReq.getPermissions())
                        .color(createdCalenderReq.getColor())
                        .summaryAlias(createdCalenderReq.getSummaryAlias())
                        .build())
                .build();

        // 发起请求
        PatchCalendarResp resp = null;
        try {
            resp = apiService.getClient().calendar().v4().calendar().patch(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 处理服务端错误
        if(!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return Jsons.DEFAULT.toJson(resp.getData());
        }else {
            System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
            return "成功";
        }
    }
}
