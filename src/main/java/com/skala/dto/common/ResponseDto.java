/*
 * - 작성자 : 진실
 * - 작성일 : 2025.04.01
 * 
 * <클래스 개요>
 * API 응답을 위한 공통 DTO 클래스
 * 
 * - 모든 API 응답에 대한 일관된 형식 제공
 * - 상태 코드, 메시지, 데이터를 포함한 응답 구조
 * - 정적 팩토리 메소드를 사용하여 다양한 응답 유형 생성 지원
 */

 package com.skala.dto.common;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;
 
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
 public class ResponseDto<T> {
     private String status;  // 상태 코드 (success, error)
     private String message; // 응답 메시지
     private T data;         // 응답 데이터
 
     /**
      * 성공 응답 생성 (데이터 포함)
      */
     public static <T> ResponseDto<T> success(T data) {
         return ResponseDto.<T>builder()
                 .status("success")
                 .data(data)
                 .build();
     }
 
     /**
      * 성공 응답 생성 (메시지 및 데이터 포함)
      */
     public static <T> ResponseDto<T> success(String message, T data) {
         return ResponseDto.<T>builder()
                 .status("success")
                 .message(message)
                 .data(data)
                 .build();
     }
 
     /**
      * 성공 응답 생성 (메시지만 포함)
      */
     public static <T> ResponseDto<T> success(String message) {
         return ResponseDto.<T>builder()
                 .status("success")
                 .message(message)
                 .build();
     }
 
     /**
      * 에러 응답 생성
      */
     public static <T> ResponseDto<T> error(String message) {
         return ResponseDto.<T>builder()
                 .status("error")
                 .message(message)
                 .build();
     }

     public static <T> ResponseDto<T> error(String message, T data) {
        return ResponseDto.<T>builder()
                .status("error")
                .message(message)
                .data(data)
                .build();
    }
 }