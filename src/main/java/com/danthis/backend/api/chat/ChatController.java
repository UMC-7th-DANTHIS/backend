package com.danthis.backend.api.chat;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.chat.response.ChatStartResponse;
import com.danthis.backend.application.chat.ChatService;
import com.danthis.backend.application.chat.response.DancerChatListServiceResponse;
import com.danthis.backend.application.chat.response.UserChatListServiceResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chats")
@RequiredArgsConstructor
@Tag(name = "채팅", description = "댄서와 유저 간의 채팅 관련 API")
public class ChatController {

  private final ChatService chatService;

  @Operation(summary = "댄서와 1:1 채팅 시작 API", description = "유저가 특정 댄서와 1:1 채팅을 시작합니다.")
  @PostMapping("/{dancerId}/start")
  @AssignCurrentUserInfo
  public ApiResponse<ChatStartResponse> startChatWithDancer(
      @PathVariable Long dancerId,
      CurrentUserInfo userInfo
  ) {
    ChatStartResponse response = chatService.startChatWithDancer(userInfo.getUserId(), dancerId);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄서가 채팅한 유저 목록 조회 API", description = "댄서가 채팅한 유저 목록을 조회합니다.")
  @GetMapping("/dancer")
  @AssignCurrentUserInfo
  public ApiResponse<DancerChatListServiceResponse> getDancerChatUsers(
      CurrentUserInfo userInfo,
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "10") @Min(1) int size
  ) {
    DancerChatListServiceResponse response = chatService.getDancerChatList(userInfo.getUserId(),
        page, size);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "유저가 채팅한 댄서 목록 조회 API", description = "유저가 채팅한 댄서 목록을 조회합니다.")
  @GetMapping("/user")
  @AssignCurrentUserInfo
  public ApiResponse<UserChatListServiceResponse> getUserChatList(
      CurrentUserInfo userInfo,
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "10") @Min(1) int size
  ) {
    UserChatListServiceResponse response = chatService.getUserChatList(userInfo.getUserId(), page, size);
    return ApiResponse.OK(response);
  }
}
