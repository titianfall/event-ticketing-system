import { defineConfig } from 'vitest/config' // test 설정 타입 인식을 위함
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    environment: 'jsdom', // React 컴포넌트에서 DOM을 사용할 수 있게 한다.
    setupFiles: './src/setupTests.ts', // 테스트 실행 전 setupTests.ts를 먼저 불러옴
  },
})
