<template>
  <div class="bg-white shadow-lg rounded-lg overflow-hidden">
    <div class="bg-indigo-700 text-white p-4">
      <h2 class="text-xl font-semibold flex items-center">
        <span class="text-2xl mr-2">👥</span> 플레이어 목록
      </h2>
    </div>

    <div class="overflow-x-auto">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">잔액</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">보유 주식</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">액션</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="player in players" :key="player.playerId" class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
              {{ player.playerId }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-green-600 font-semibold">
              {{ player.playerMoney?.toLocaleString() || 0 }}원
            </td>
            <td class="px-6 py-4 text-sm text-gray-700">
              <div v-if="player.playerStocks && player.playerStocks.length" class="space-y-1">
                <div 
                  v-for="stock in player.playerStocks" 
                  :key="stock.stockName"
                  class="bg-indigo-50 rounded px-2 py-1 inline-block mr-2 mb-1"
                >
                  <span class="font-medium">{{ stock.stockName }}</span>
                  <span class="text-gray-500 mx-1">|</span>
                  <span class="text-indigo-600">{{ stock.stockQuantity }}주</span>
                  <span class="text-gray-400 text-xs">({{ stock.stockPrice.toLocaleString() }}원)</span>
                </div>
              </div>
              <div v-else class="text-gray-400 italic">보유 주식 없음</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <button 
                @click="removePlayer(player.playerId)" 
                class="px-3 py-1 bg-red-50 text-red-600 hover:bg-red-100 rounded border border-red-200 transition-colors duration-200"
              >
                탈퇴
              </button>
            </td>
          </tr>
          <tr v-if="players.length === 0">
            <td colspan="4" class="px-6 py-4 text-center text-gray-500 italic">
              등록된 플레이어가 없습니다
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const props = defineProps(['players']);
const newPlayer = ref('');

const fetchPlayers = async () => {
  const response = await axios.get('http://localhost:8080/api/players');
  return response.data;
};

const addPlayer = async () => {
  if (!newPlayer.value) {
    alert('플레이어 이름을 입력해주세요.');
    return;
  }

  try {
    // 플레이어 추가를 buy API로 대체
    await axios.post('http://localhost:8080/api/buy', null, {
      params: {
        player: newPlayer.value,
        stock: '',
        quantity: 0
      }
    });

    newPlayer.value = '';
    window.location.reload(); // 전체 페이지 새로고침
  } catch (error) {
    alert('플레이어 추가 중 오류가 발생했습니다.');
  }
};

const removePlayer = async (playerId) => {
  const player = props.players.find(p => p.playerId === playerId);
  if (!player) return;

  const ownedStocks = player.playerStocks?.filter(stock => stock.stockQuantity > 0) || [];

  if (ownedStocks.length > 0) {
    alert(`보유한 주식이 남아 있으면 탈퇴할 수 없습니다.\n\n남아 있는 주식: ${ownedStocks.map(s => `${s.stockName} (${s.stockQuantity}주)`).join(', ')}`);
    return;
  }

  if (confirm(`${playerId} 플레이어를 정말 탈퇴시키겠습니까?`)) {
    try {
      await axios.post('http://localhost:8080/api/removePlayer', null, {
        params: { player: playerId }
      });
      window.location.reload(); // 전체 페이지 새로고침
    } catch (error) {
      alert('플레이어 탈퇴 중 오류가 발생했습니다.');
    }
  }
};
</script>