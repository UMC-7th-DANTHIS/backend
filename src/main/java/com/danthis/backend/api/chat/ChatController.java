package com.danthis.backend.api.chat;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.chat.request.ChatBookingRequest;
import com.danthis.backend.application.chat.ChatService;
import com.danthis.backend.application.chat.response.ChatBookingServiceResponse;
import com.danthis.backend.application.chat.response.DancerChatListServiceResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chats")
@RequiredArgsConstructor
@Tag(name = "채팅", description = "댄서와 유저 간의 채팅 관련 API")
public class ChatController {

  private final ChatService chatService;

  @Operation(summary = "1:1 채팅 시작 (댄스 수업 예약 생성)", description = "댄서와의 채팅을 시작하면 수업 예약이 생성됩니다.")
  @PostMapping("/booking")
  @AssignCurrentUserInfo
  public ApiResponse<ChatBookingServiceResponse> createChatBooking(
      @RequestBody @Valid ChatBookingRequest request) {
    ChatBookingServiceResponse response = chatService.createChatBooking(request);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄서가 채팅한 유저 목록 조회", description = "댄서가 아닌 사용자는 접근할 수 없습니다.")
  @GetMapping("/dancer/{dancerId}")
  @AssignCurrentUserInfo
  public ApiResponse<DancerChatListServiceResponse> getDancerChatList(@PathVariable Long dancerId) {
    if (!chatService.isDancer(dancerId)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    DancerChatListServiceResponse response = chatService.getDancerChatList(dancerId);
    return ApiResponse.OK(response);
  }
}
