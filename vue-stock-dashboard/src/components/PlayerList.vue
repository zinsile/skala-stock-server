<template>
  <div>
    <h2>플레이어 목록</h2>
    <table border="1">
      <tr>
        <th>ID</th>
        <th>잔액</th>
        <th>보유 주식</th>
        <th>액션</th> <!-- ✅ 탈퇴 버튼 컬럼 추가 -->
      </tr>
      <tr v-for="player in players" :key="player.playerId">
        <td>{{ player.playerId }}</td>
        <td>{{ player.playerMoney }}</td>
        <td>
          <ul>
            <li v-for="stock in player.playerStocks" :key="stock.stockId + stock.stockName">
              {{ stock.stockName }} ({{ stock.stockPrice }} 원): {{ stock.stockQuantity}}주
            </li>
          </ul>
        </td>
        <td>
          <!-- ✅ 탈퇴 버튼 -->
          <button @click="removePlayer(player.playerId)">탈퇴</button>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      players: [],
    };
  },
  methods: {
    fetchPlayers() {
      axios.get("http://localhost:8080/api/players")
        .then((res) => {
          this.players = res.data;
        });
    },
    removePlayer(playerId) {
      if (confirm(`${playerId} 님을 정말 탈퇴시키겠습니까?`)) {
        axios.post("http://localhost:8080/api/removePlayer", null, {
          params: { player: playerId }
        }).then(() => {
          alert("탈퇴 완료!");
          this.fetchPlayers(); // ✅ 목록 새로고침
        }).catch(() => {
          alert("탈퇴 실패: 존재하지 않는 플레이어거나 서버 오류입니다.");
        });
      }
    }
  },
  mounted() {
    this.fetchPlayers();
  }
};
</script>
