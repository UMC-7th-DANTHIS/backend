package com.danthis.backend.api.chat;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.chat.response.ChatRoomCreateResponseDTO;
import com.danthis.backend.application.chat.ChatMessageService;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Tag(name = "채팅", description = "1:1 실시간 채팅방 관리 API")
public class ChatMessageController {

  private final ChatMessageService chatMessageService;

  /**
   * 유저가 댄서에게 1:1 채팅방을 생성 (이미 있으면 기존 방 반환)
   */
  @Operation(summary = "채팅방 생성(또는 기존 방 반환)", description = "유저가 특정 댄서와 1:1 채팅을 시작하거나 기존 채팅방을 조회합니다.")
  @PostMapping("/{dancerId}/start")
  @AssignCurrentUserInfo
  public ApiResponse<ChatRoomCreateResponseDTO> startChat(@PathVariable Long dancerId,
      CurrentUserInfo userInfo
  ) {
    ChatRoomCreateResponseDTO response = chatMessageService.startChat(userInfo.getUserId(), dancerId);
    return ApiResponse.OK(response);
  }
}
