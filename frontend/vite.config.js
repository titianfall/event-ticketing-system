import { defineConfig } from 'vitest/config' // vite가 아닌 vitest/config에서 가져와야 test 설정을 인식한다
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    environment: 'jsdom', // React 컴포넌트에서 DOM을 사용할 수 있게 한다.
    setupFiles: './src/setupTests.js', // 테스트 실행 전 setupTests.js를 먼저 불러옴
  },
})
