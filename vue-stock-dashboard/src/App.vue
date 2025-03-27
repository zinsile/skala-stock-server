<template>
  <main class="container">
    <!-- 상단: 타이틀 + 플레이어 입력 -->
    <div class="top-bar">
      <h1 class="title">
        📈 Skala 주식 거래
        <span v-if="playerId" class="connected">- 👤 {{ playerId }}님 접속 중</span>
      </h1>
      <div class="player-input">
        <input
          v-model="inputPlayerId"
          placeholder="플레이어 이름 입력"
        />
        <button @click="login">접속하기</button>
      </div>
    </div>

    <!-- 본문: 좌우 레이아웃 -->
    <div class="main-section">
      <div class="left">
        <StockList
          :stocks="stocks"
          @buy="handleBuy"
          @sell="handleSell"
          @refresh="loadData"
        />
      </div>
      <div class="right">
        <PlayerList :players="players" :key="JSON.stringify(players)" />
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import StockList from './components/StockList.vue'
import PlayerList from './components/PlayerList.vue'

const tab = ref('home')
const tabClass = (target) => (tab.value === target ? 'active' : '')

const stocks = ref([])
const players = ref([])
const playerId = ref('')
const inputPlayerId = ref('')
const newStockName = ref('')
const newStockPrice = ref('')

const loadData = async () => {
  const stockRes = await axios.get('http://localhost:8080/api/stocks')
  const playerRes = await axios.get('http://localhost:8080/api/players')
  stocks.value = stockRes.data
  players.value = playerRes.data
}

const handleBuy = async (player, stock, quantity) => {
  if (!playerId.value) return alert('먼저 플레이어 이름을 입력하세요')
  await axios.post('http://localhost:8080/api/buy', null, {
    params: { player: playerId.value, stock, quantity }
  })
  await loadData()
}

const handleSell = async (player, stock, quantity) => {
  if (!playerId.value) return alert('먼저 플레이어 이름을 입력하세요')
  await axios.post('http://localhost:8080/api/sell', null, {
    params: { player: playerId.value, stock, quantity }
  })
  await loadData()
}

const login = () => {
  if (!inputPlayerId.value.trim()) return alert('플레이어 이름을 입력하세요')
  playerId.value = inputPlayerId.value.trim()
  alert(`${playerId.value}님 접속 완료!`)
  loadData()
  inputPlayerId.value = ''
}

const createPlayer = async () => {
  const name = inputPlayerId.value.trim()
  if (!name) return alert('플레이어 이름을 입력하세요')
  const money = prompt('초기 자본금을 입력하세요')
  if (!money || isNaN(money)) return alert('숫자로 입력해주세요')

  try {
    await axios.post('http://localhost:8080/api/addPlayer', null, {
      params: { player: name, money: parseInt(money) }
    })

    playerId.value = name
    alert(`${name} 플레이어가 생성되었습니다!`)
    await loadData()

  } catch (error) {
    if (error.response && error.response.status === 409) {
      alert('⚠️ 이미 존재하는 플레이어입니다!')
    } else {
      alert('⚠️ 플레이어 생성 중 오류가 발생했습니다.')
    }
  }
}

const addStock = async () => {
  const name = newStockName.value.trim()
  const price = parseInt(newStockPrice.value)

  if (!name) return alert('주식 이름을 입력하세요')
  if (!price || price <= 0) return alert('유효한 가격을 입력하세요')

  const exists = stocks.value.find(s => s.stockName === name)
  if (exists) return alert('⚠️ 이미 존재하는 주식입니다!')

  try {
    await axios.post('http://localhost:8080/api/addStock', null, {
      params: { name, price }
    })
    alert(`${name} 주식이 추가되었습니다!`)
    newStockName.value = ''
    newStockPrice.value = ''
    await loadData()
  } catch (error) {
    if (error.response && error.response.status === 409) {
      alert('⚠️ 이미 존재하는 주식입니다!')
    } else {
      alert('⚠️ 주식 추가 중 오류가 발생했습니다.')
    }
  }
}

onMounted(loadData)
</script>

<style scoped>
.container {
  max-width: 1000px;
  margin: auto;
  padding: 20px;
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
  color: #1f2937;
}

.connected {
  font-size: 16px;
  font-weight: normal;
  color: #374151;
  margin-left: 10px;
}

.top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.top-bar .title {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 10px;
  color: #1f2937;
}

.player-input {
  display: flex;
  align-items: center;
}

.player-input input {
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  outline: none;
  transition: border-color 0.2s;
}

.player-input input:focus {
  border-color: #3b82f6;
}

.player-input button {
  padding: 8px 14px;
  margin-left: 8px;
  background-color: #dbeafe;
  color: #1d4ed8;
  border: 1px solid #93c5fd;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.player-input button:hover {
  background-color: #bfdbfe;
}

.main-section {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.left,
.right {
  flex: 1;
  min-width: 300px;
  border: 1px solid #e5e7eb;
  padding: 16px;
  border-radius: 12px;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}
</style>
