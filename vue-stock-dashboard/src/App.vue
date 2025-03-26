<template>
  <main class="p-4">
    <h1 class="text-2xl font-bold mb-4">
      📈 Skala 주식 거래 <span v-if="playerId">- 👤 {{ playerId }}님 접속 중</span>
    </h1>

    <div class="mb-4">
      <input
        v-model="inputPlayerId"
        placeholder="플레이어 이름 입력"
        class="border p-2 rounded mr-2"
      />
      <button @click="login" class="bg-blue-500 text-white px-4 py-2 rounded">
        접속하기
      </button>
    </div>

    <!-- key로 강제 갱신 유도 -->
    <StockList
      :stocks="stocks"
      @buy="handleBuy"
      @sell="handleSell"
      @refresh="loadData"
    />
    <PlayerList :players="players" :key="JSON.stringify(players)" />
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import StockList from './components/StockList.vue'
import PlayerList from './components/PlayerList.vue'

const stocks = ref([])
const players = ref([])
const playerId = ref('')
const inputPlayerId = ref('')

// ✅ 서버 데이터 로드
const loadData = async () => {
  const stockRes = await axios.get('http://localhost:8080/api/stocks')
  const playerRes = await axios.get('http://localhost:8080/api/players')
  stocks.value = stockRes.data
  players.value = playerRes.data
}

// ✅ 매수 처리
const handleBuy = async (player, stock, quantity) => {
  if (!playerId.value) return alert('먼저 플레이어 이름을 입력하세요')
  await axios.post('http://localhost:8080/api/buy', null, {
    params: { player: playerId.value, stock, quantity }
  })
  await loadData() // 🔄 갱신
}

// ✅ 매도 처리
const handleSell = async (player, stock, quantity) => {
  if (!playerId.value) return alert('먼저 플레이어 이름을 입력하세요')
  await axios.post('http://localhost:8080/api/sell', null, {
    params: { player: playerId.value, stock, quantity }
  })
  await loadData() // 🔄 갱신
}

// ✅ 로그인
const login = () => {
  const trimmedName = inputPlayerId.value.trim()
  if (!trimmedName) {
    return alert('플레이어 이름을 입력하세요')
  }

  // 존재하는 플레이어인지 확인
  const exists = players.value.find(player => player.playerId === trimmedName)

  if (!exists) {
    return alert('존재하지 않는 플레이어입니다.')
  }

  // 접속 허용
  playerId.value = exists.playerId
  alert(`${playerId.value}님 접속 완료!`)
  loadData()
  inputPlayerId.value = ''
}

onMounted(loadData)
</script>

<style>
body {
  font-family: sans-serif;
  background-color: #f9fafb;
}
</style>
