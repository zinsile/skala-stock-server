<template>
  <section class="mb-6">
    <h2 class="text-xl font-semibold mb-2">📈 현재 주식 목록</h2>
    <ul class="bg-white shadow p-4 rounded">
      <li
        v-for="stock in stocks"
        :key="stock.stockName"
        class="flex items-center justify-between mb-2"
      >
        <span>{{ stock.stockName }} - {{ stock.stockPrice }}원</span>
        <div>
          <button
            class="ml-2 border px-2 py-1 rounded bg-green-500 text-white"
            @click="buy(stock.stockName)"
          >
            매수
          </button>
          <button
            class="ml-2 border px-2 py-1 rounded bg-red-500 text-white"
            @click="sell(stock.stockName)"
          >
            매도
          </button>
        </div>
      </li>
    </ul>

    <!-- ✅ 주식 추가 폼 -->
    <div class="mt-4 bg-gray-100 p-4 rounded">
      <h3 class="font-semibold mb-2">새로운 주식 종목 추가</h3>
      <input
        v-model="newStockName"
        placeholder="종목명"
        class="mr-2 border rounded px-2 py-1"
      />
      <input
        v-model.number="newStockPrice"
        type="number"
        placeholder="주당 가격"
        class="mr-2 border rounded px-2 py-1"
      />
      <button
        class="border px-3 py-1 rounded bg-blue-500 text-white"
        @click="addStock"
      >
        추가
      </button>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { defineProps, defineEmits } from 'vue'

const props = defineProps(['stocks'])
const emit = defineEmits(['buy', 'sell', 'refresh'])

const newStockName = ref('')
const newStockPrice = ref(0)

const buy = (stockName) => {
  const quantity = prompt('몇 주를 매수할까요?')
  if (quantity) emit('buy', null, stockName, parseInt(quantity))
}

const sell = (stockName) => {
  const quantity = prompt('몇 주를 매도할까요?')
  if (quantity) emit('sell', null, stockName, parseInt(quantity))
}

const addStock = async () => {
  if (!newStockName.value || newStockPrice.value <= 0) {
    alert('종목명과 유효한 가격을 입력하세요.')
    return
  }

  try {
    await axios.post('http://localhost:8080/api/addStock', null, {
      params: {
        name: newStockName.value,
        price: newStockPrice.value
      }
    })
    alert('주식이 추가되었습니다!')
    newStockName.value = ''
    newStockPrice.value = 0
    emit('refresh') // 🔄 상위 컴포넌트에 목록 갱신 요청
  } catch (error) {
    alert('주식 추가 실패: 이미 존재하는 종목일 수 있어요.')
  }
}
</script>
