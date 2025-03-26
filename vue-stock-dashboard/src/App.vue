<template>
  <main class="p-6 bg-gray-50 min-h-screen">
    <div class="max-w-7xl mx-auto">
      <!-- 헤더 섹션 -->
      <header class="mb-8 bg-white p-6 rounded-lg shadow-sm flex justify-between items-center">
        <h1 class="text-3xl font-bold text-gray-800">
          📈 Skala 주식 거래 <span v-if="playerId" class="text-blue-600">- 👤 {{ playerId }}님 접속 중</span>
        </h1>
        
        <div class="flex space-x-2">
          <input
            v-model="inputPlayerId"
            placeholder="플레이어 이름 입력"
            class="border border-gray-300 p-2 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
          />
          <button @click="login" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md transition-colors duration-200 font-medium">
            접속하기
          </button>
        </div>
      </header>

      <!-- 메인 콘텐츠 영역: 3단 레이아웃 -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- 첫 번째 섹션: 주식 목록 -->
        <div class="lg:col-span-2 bg-white p-6 rounded-lg shadow-sm">
          <StockList
            :stocks="stocks"
            @buy="handleBuy"
            @sell="handleSell"
            @refresh="loadData"
          />
        </div>

        <!-- 두 번째 섹션: 플레이어 목록 -->
        <div class="bg-white p-6 rounded-lg shadow-sm">
          <PlayerList :players="players" :key="JSON.stringify(players)" />
        </div>
      </div>
    </div>
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
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
  background-color: #f9fafb;
  color: #1f2937;
}

button {
  border-radius: 0.375rem;
  border: 1px solid transparent;
  transition: all 0.2s ease;
}

button:hover {
  transform: translateY(-1px);
}

button:focus {
  outline: 2px solid #3b82f6;
  outline-offset: 2px;
}

input {
  transition: all 0.2s ease;
}

.shadow-sm {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}
</style>