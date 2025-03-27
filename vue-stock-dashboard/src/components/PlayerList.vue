<template>
  <div class="player-list">
    <h2 class="title">👥 플레이어 목록</h2>

    <div v-if="players.length === 0" class="empty">등록된 플레이어가 없습니다</div>

    <div class="player-card" v-for="player in players" :key="player.playerId">
      <div class="player-header">
        <span class="player-name">{{ player.playerId }}</span>
        <span class="player-money">{{ player.playerMoney?.toLocaleString() || 0 }} 원</span>
      </div>

      <div class="player-stocks">
        <template v-if="player.playerStocks && player.playerStocks.length">
          <div
            v-for="stock in player.playerStocks"
            :key="stock.stockName"
            class="stock"
          >
            <span class="stock-name">{{ stock.stockName }}</span>
            <span class="stock-quantity">{{ stock.stockQuantity }}주</span>
            <span class="stock-price">({{ stock.stockPrice.toLocaleString() }}원)</span>
          </div>
        </template>
        <div v-else class="text-gray-400 italic">보유 주식 없음</div>
      </div>

      <button
        @click="removePlayer(player.playerId)"
        class="remove-btn"
      >
        탈퇴
      </button>
      <button
        @click="openAddMoneyPrompt(player.playerId)"
        class="add-money-btn"
      >
        투자금 추가
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const props = defineProps(['players'])

const removePlayer = async (playerId) => {
  const player = props.players.find(p => p.playerId === playerId)
  if (!player) return

  const ownedStocks = player.playerStocks?.filter(stock => stock.stockQuantity > 0) || []

  if (ownedStocks.length > 0) {
    alert(`보유한 주식이 남아 있으면 탈퇴할 수 없습니다.\n\n남아 있는 주식: ${ownedStocks.map(s => `${s.stockName} (${s.stockQuantity}주)`).join(', ')}`)
    return
  }

  if (confirm(`${playerId} 플레이어를 정말 탈퇴시키겠습니까?`)) {
    try {
      await axios.post('http://localhost:8080/api/removePlayer', null, {
        params: { player: playerId }
      })
      window.location.reload()
    } catch (error) {
      alert('플레이어 탈퇴 중 오류가 발생했습니다.')
    }
  }
}

const openAddMoneyPrompt = async (playerId) => {
  const amountStr = prompt('얼마를 추가하시겠습니까?', '10000')
  const amount = parseInt(amountStr)
  if (isNaN(amount) || amount <= 0) {
    alert('올바른 금액을 입력해주세요.')
    return
  }

  try {
    await axios.post('http://localhost:8080/api/addMoney', null, {
      params: {
        player: playerId,
        amount: amount
      }
    })

    // ✅ player의 금액만 업데이트 (새로고침 없이)
    const target = props.players.find(p => p.playerId === playerId)
    if (target) {
      target.playerMoney += amount
    }

    alert(`₩${amount.toLocaleString()} 이(가) 추가되었습니다!`)
  } catch (error) {
    alert('투자금 추가 중 오류가 발생했습니다.')
  }
}

</script>

<style scoped>
.player-list {
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
}

.title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 16px;
  color: #1f2937;
}

.empty {
  color: #888;
  font-size: 14px;
  padding: 16px;
  text-align: center;
  border: 1px dashed #ccc;
  border-radius: 8px;
  background: #f9fafb;
}

.player-card {
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 16px;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.player-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
}

.player-name {
  font-size: 16px;
  font-weight: 600;
  color: #111827;
}

.player-money {
  font-size: 14px;
  font-weight: 500;
  color: #2563eb;
}

.player-stocks {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 12px;
}

.stock {
  background-color: #e0f2fe;
  color: #0369a1;
  font-size: 13px;
  padding: 4px 8px;
  border-radius: 9999px;
}

.stock-price {
  margin-left: 4px;
  font-size: 12px;
  color: #6b7280;
}

.remove-btn {
  padding: 6px 12px;
  background-color: #fee2e2;
  color: #b91c1c;
  border: 1px solid #fca5a5;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.remove-btn:hover {
  background-color: #fecaca;
}

.add-money-btn {
  padding: 6px 12px;
  margin-left: 8px;
  background-color: #d1fae5;
  color: #047857;
  border: 1px solid #6ee7b7;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.add-money-btn:hover {
  background-color: #a7f3d0;
}

</style>
