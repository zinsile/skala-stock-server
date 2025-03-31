/*
 * - 작성자 : 진실
 * - 작성일 : 2025.03.30
 * - 수정일 : 2025.03.31
 * 
 * <클래스 개요>
 * 게임 내 플레이어 정보를 관리하는 클래스
 * 
 * - 플레이어 ID, 보유 자금, 보유 주식 목록을 관리
 * - Lombok 어노테이션을 사용하여 getter, setter 및 기본 생성자 자동 생성
 * - 플레이어는 기본적으로 10,000원의 초기 자금을 가지고 시작
 * - 다양한 주식을 구매하여 playerStocks 리스트에 보관 가능
 */
package com.skala.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Player {
    private String playerId;                            // 플레이어 ID
    private int playerMoney;                            // 플레이어 자금
    private ArrayList<PlayerStock> playerStocks = new ArrayList<>();  // 보유 주식 목록

    // 플레이어 ID를 받아 초기 자금으로 생성
    public Player(String id) {
        this.playerId = id;
        this.playerMoney = 10000;
    }
}