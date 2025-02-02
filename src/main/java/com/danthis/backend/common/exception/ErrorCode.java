package com.danthis.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
  KAKAO_USER_IMPORT_FAILED(HttpStatus.BAD_REQUEST, "카카오 사용자 정보를 가져오는데 실패했습니다."),
  NONE_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
  INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 엑세스 토큰입니다."),
  NOT_EXIST_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "존재하지 않는 리프레시 토큰입니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
  DANCE_CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "댄스 수업을 찾을 수 없습니다"),
  DANCER_NOT_FOUND(HttpStatus.NOT_FOUND, "댄서를 찾을 수 없습니다."),
  GENRE_NOT_FOUND(HttpStatus.NOT_FOUND, "댄스 장르를 찾을 수 없습니다."),
  USER_DANCER_NOT_FOUND(HttpStatus.NOT_FOUND, "찜이 되어있지 않은 댄서입니다."),
  REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다"),
  INVALID_SEARCH_QUERY(HttpStatus.BAD_REQUEST, "검색어를 입력하지 않았습니다."),
  INVALID_BOOKING(HttpStatus.BAD_REQUEST, "예약이 이미 됐거나, 유저와 채팅 기록이 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
