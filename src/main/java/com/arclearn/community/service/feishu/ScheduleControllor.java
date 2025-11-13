package com.arclearn.community.service.feishu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.arclearn.community.entity.Dto.feishu.CreateCalendarEventRequest;
import com.google.gson.JsonParser;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.calendar.v4.model.Attachment;
import com.lark.oapi.service.calendar.v4.model.CalendarEvent;
import com.lark.oapi.service.calendar.v4.model.CheckInTime;
import com.lark.oapi.service.calendar.v4.model.CreateCalendarEventReq;
import com.lark.oapi.service.calendar.v4.model.CreateCalendarEventResp;
import com.lark.oapi.service.calendar.v4.model.DeleteCalendarEventReq;
import com.lark.oapi.service.calendar.v4.model.DeleteCalendarEventResp;
import com.lark.oapi.service.calendar.v4.model.EventCheckIn;
import com.lark.oapi.service.calendar.v4.model.EventLocation;
import com.lark.oapi.service.calendar.v4.model.ListCalendarEventReq;
import com.lark.oapi.service.calendar.v4.model.ListCalendarEventResp;
import com.lark.oapi.service.calendar.v4.model.MeetingSettings;
import com.lark.oapi.service.calendar.v4.model.Reminder;
import com.lark.oapi.service.calendar.v4.model.Schema;
import com.lark.oapi.service.calendar.v4.model.TimeInfo;
import com.lark.oapi.service.calendar.v4.model.Vchat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequestMapping("/schedule")
@Tag(name = "日程", description = "日程增删改查接口")
public class ScheduleControllor {
    @Resource
    private ApiService apiService;
//
//    curl -X 'POST' \
//            'http://localhost:8080/schedule/createSchedule?eventId=xxxxxxxxx_0&userIdType=open_id&calendarId=feishu.cn_7Cgoj6iMqQhgXDFC5Vza5e%40group.calendar.feishu.cn&userAccessToken=u-fnM5pkGpZ1lqrtGtOh3zohh5k_wkk0oprgw0h58w23kt&needNotification=false' \
//            -H 'accept: */*' \
//            -H 'Content-Type: application/json' \
//            -d '{
//            "summary": "日程标题2",
//            "description": "日程描述",
//            "needNotification": false,
//            "startTime": {
//        "date": "2025-11-07",
//                "timezone": "Asia/Shanghai"
//    },
//            "endTime": {
//        "date": "2025-11-07",
//                "timezone": "Asia/Shanghai"
//    },
//            "visibility": "default",
//            "attendeeAbility": "can_see_others",
//            "freeBusyStatus": "busy",
//            "location": {
//        "name": "地点名称",
//                "address": "地点地址",
//                "latitude": 1.100000023841858,
//                "longitude": 2.200000047683716
//    },
//            "color": -1,
//            "reminders": [
//    {
//        "minutes": 5
//    }
//  ],
//          "recurrence": "FREQ=DAILY;INTERVAL=1",
//          "schemas": [
//    {
//        "uiName": "ForwardIcon",
//            "uiStatus": "hide"
//    }
//  ],
//          "eventCheckIn": {
//        "enableCheckIn": true,
//                "checkInStartTime": {
//            "timeType": "before_event_start",
//                    "duration": 15
//        },
//        "checkInEndTime": {
//            "timeType": "after_event_end",
//                    "duration": 0
//        },
//        "needNotifyAttendees": false
//    }
//}'
    @Operation(summary = "创建日程", description = "创建日程")
    public String createSchedule(@Parameter(description = "用户类型", example = "open_id：标识一个用户在某个应用中的身份。同一个用户在不同应用中的 Open ID 不同。\\n\" +\n" +
                                         "                                      \"union_id：标识一个用户在某个应用开发商下的身份。同一用户在同一开发商下的应用中的 Union ID 是相同的，在不同开发商下的应用中的 Union ID 是不同的。通过 Union ID，应用开发商可以把同个用户在多个应用中的身份关联起来。\\n\" +\n" +
                                         "                                      \"user_id：标识一个用户在某个租户内的身份。同一个用户在租户 A 和租户 B 内的 User ID 是不同的。在同一个租户内，一个用户的 User ID 在所有应用（包括商店应用）中都保持一致。User ID 主要用于在不同的应用间打通用户数据。") @RequestParam String userIdType,
                                 @RequestBody CreateCalendarEventRequest request,
                                 @Parameter(description = "日历 ID", example = "feishu.cn_xxxxxxxxxx@group.calendar.feishu.cn") @RequestParam String calendarId,
                                 @Parameter(description = "用户token", example = "t-g1044qeGEDXTB6NDJOGV4JQCYDGHRBARFTGT123") @RequestParam String userAccessToken) {

        CalendarEvent.Builder builder = CalendarEvent.newBuilder();
        builder.summary(request.getSummary())
                .description(request.getDescription())
                .needNotification(request.getNeedNotification())
                .visibility(request.getVisibility())
                .attendeeAbility(request.getAttendeeAbility())
                .freeBusyStatus(request.getFreeBusyStatus())
                .location(EventLocation.newBuilder()
                        .name(request.getLocation().getName())
                        .address(request.getLocation().getAddress())
                        .latitude(request.getLocation().getLatitude())
                        .longitude(request.getLocation().getLongitude())
                        .build())
                .color(request.getColor())
                .recurrence(request.getRecurrence())
                .eventCheckIn(EventCheckIn.newBuilder()
                        .enableCheckIn(true)
                        .checkInStartTime(CheckInTime.newBuilder()
                                .timeType(request.getEventCheckIn().getCheckInStartTime().getTimeType())
                                .duration(request.getEventCheckIn().getCheckInStartTime().getDuration())
                                .build())
                        .checkInEndTime(CheckInTime.newBuilder()
                                .timeType(request.getEventCheckIn().getCheckInEndTime().getTimeType())
                                .duration(request.getEventCheckIn().getCheckInEndTime().getDuration())
                                .build())
                        .needNotifyAttendees(false)
                        .build());
        if (request.getStartTime().getDate() == null){
            builder.startTime(TimeInfo.newBuilder()
                    .timestamp(request.getStartTime().getTimestamp())
                    .timezone(request.getStartTime().getTimezone())
                    .build());
        }else {
            builder.startTime(TimeInfo.newBuilder()
                    .date(request.getStartTime().getDate())
                    .timezone(request.getStartTime().getTimezone())
                    .build());
        }
         if (request.getEndTime().getDate() == null) {
             builder.endTime(TimeInfo.newBuilder()
                     .timestamp(request.getEndTime().getTimestamp())
                     .timezone(request.getEndTime().getTimezone())
                     .build());
         }else {
             builder.endTime(TimeInfo.newBuilder()
                     .date(request.getEndTime().getDate())
                     .timezone(request.getEndTime().getTimezone())
                     .build());
         }

        if (request.getVchat() != null){
            builder.vchat(Vchat.newBuilder()
                    .vcType(request.getVchat().getVcType())
                    .iconType(request.getVchat().getIconType())
                    .description(request.getVchat().getDescription())
                    .meetingUrl(request.getVchat().getMeetingUrl())
                    .meetingSettings(MeetingSettings.newBuilder()
                            .ownerId(request.getVchat().getMeetingSettings().getOwnerId())
                            .joinMeetingPermission(request.getVchat().getMeetingSettings().getJoinMeetingPermission())
                            .assignHosts(ArrayUtil.toArray(request.getVchat().getMeetingSettings().getAssignHosts(), String.class))
                            .autoRecord(request.getVchat().getMeetingSettings().getAutoRecord())
                            .openLobby(request.getVchat().getMeetingSettings().getOpenLobby())
                            .allowAttendeesStart(request.getVchat().getMeetingSettings().getAllowAttendeesStart())
                            .build())
                    .build());
        }

        if (CollectionUtil.isNotEmpty(request.getAttachments())){
            List<Attachment> attachments = new ArrayList<>();
            request.getAttachments().stream().forEach( attachment -> {
                Attachment build = Attachment.newBuilder()
                        .fileToken(attachment.getFileToken())
                        .build();
                attachments.add(build);
            });
            builder.attachments(ArrayUtil.toArray(attachments, Attachment.class));
        }
        if (CollectionUtil.isNotEmpty(request.getReminders())){
            List<Reminder> reminders = new ArrayList<>();
            request.getReminders().stream().forEach( attachment -> {
                Reminder build = Reminder.newBuilder()
                        .minutes(5)
                        .build();
                reminders.add(build);
            });
        }

        if (CollectionUtil.isNotEmpty(request.getSchemas())){
            List<Schema> schemas = new ArrayList<>();
            request.getSchemas().stream().forEach( attachment -> {
                Schema build = Schema.newBuilder()
                        .uiName(attachment.getUiName())
                        .uiStatus(attachment.getUiStatus())
//                    .appLink(attachment.geta) 暂不支持
                        .build();
                schemas.add(build);
            });
        }

        CreateCalendarEventReq req = CreateCalendarEventReq.newBuilder()
                .calendarId(calendarId)
                .userIdType(userIdType)
                .calendarEvent(builder.build()).build();

        // 发起请求
        CreateCalendarEventResp resp = null;
        try {
             resp =  apiService.getClient().calendar().v4().calendarEvent().create(req, RequestOptions.newBuilder()
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
//    http://localhost:8080/schedule/deleteSchedule?eventId=c1b885ac-1b34-44f0-8fe8-d8013d02f16f_0&calendarId=feishu.cn_7Cgoj6iMqQhgXDFC5Vza5e%40group.calendar.feishu.cn&userAccessToken=u-fnM5pkGpZ1lqrtGtOh3zohh5k_wkk0oprgw0h58w23kt&needNotification=false
    @Operation(summary = "删除日程", description = "删除日程")
    public String deleteSchedule(@Parameter(description = "日程 ID", example = "xxxxxxxxx_0") @RequestParam String eventId,
                                 @Parameter(description = "日历 ID", example = "feishu.cn_xxxxxxxxxx@group.calendar.feishu.cn") @RequestParam String calendarId,
                                 @Parameter(description = "用户token", example = "t-g1044qeGEDXTB6NDJOGV4JQCYDGHRBARFTGT123") @RequestParam String userAccessToken,
                                 @Parameter(description = "通知", example = "删除日程是否给日程参与人发送 Bot 通知") @RequestParam Boolean needNotification) {
        // 创建请求对象
        DeleteCalendarEventReq req = DeleteCalendarEventReq.newBuilder()
                .calendarId(calendarId)
                .eventId(eventId)
                .needNotification(needNotification.toString())
                .build();
        // 发起请求
        DeleteCalendarEventResp resp = null;
        try {
             resp = apiService.getClient().calendar().v4().calendarEvent().delete(req, RequestOptions.newBuilder()
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
//    http://localhost:8080/schedule/getScheduleList?userAccessToken=u-fnM5pkGpZ1lqrtGtOh3zohh5k_wkk0oprgw0h58w23kt&calendarId=feishu.cn_7Cgoj6iMqQhgXDFC5Vza5e%40group.calendar.feishu.cn&startTime=1761926400&endTime=1764431999&pageSize=50&userIdType=open_id
    @Operation(summary = "日程查询列表接口", description = "日程查询列表接口")
    public String getScheduleList(@Parameter(description = "用户token", example = "t-g1044qeGEDXTB6NDJOGV4JQCYDGHRBARFTGT123") @RequestParam String userAccessToken,
                              @Parameter(description = "日历 ID", example = "feishu.cn_xxxxxxxxxx@group.calendar.feishu.cn") @RequestParam String calendarId,
                              @Parameter(description = "开始时间", example = "1761926400") @RequestParam String startTime,
                              @Parameter(description = "解释时间", example = "1764431999") @RequestParam String endTime,
                              @Parameter(description = "分页标记", example = "第一次请求不填，表示从头开始遍历；分页查询结果还有更多项时会同时返回新的 page_token，下次遍历可采用该 page_token 获取查询结果 ",required = false) @RequestParam(required = false) String pageToken,
                              @Parameter(description = "页面大小,取值范围：50 ～ 1000", example = "50") @RequestParam int pageSize,
                              @Parameter(description = "用户 ID 类型open_id：标识一个用户在某个应用中的身份。同一个用户在不同应用中的 Open ID 不同。\\n\" +\n" +
                                      "                                      \"union_id：标识一个用户在某个应用开发商下的身份。同一用户在同一开发商下的应用中的 Union ID 是相同的，在不同开发商下的应用中的 Union ID 是不同的。通过 Union ID，应用开发商可以把同个用户在多个应用中的身份关联起来。\\n\" +\n" +
                                      "                                      \"user_id：标识一个用户在某个租户内的身份。同一个用户在租户 A 和租户 B 内的 User ID 是不同的。在同一个租户内，一个用户的 User ID 在所有应用（包括商店应用）中都保持一致。User ID 主要用于在不同的应用间打通用户数据。",  example= "open_id") @RequestParam String userIdType) {
        // 创建请求对象
        ListCalendarEventReq.Builder builder = ListCalendarEventReq.newBuilder()
                .calendarId(calendarId)
                .pageSize(pageSize)
                .startTime(startTime)
                .endTime(endTime)
                .userIdType(userIdType);
        if (StrUtil.isNotBlank(pageToken)) {
            builder.pageToken(pageToken);
        }
        ListCalendarEventReq req = builder.build();
        // 发起请求
        ListCalendarEventResp resp = null;
        try {
            resp = apiService.getClient().calendar().v4().calendarEvent().list(req, RequestOptions.newBuilder()
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
}
