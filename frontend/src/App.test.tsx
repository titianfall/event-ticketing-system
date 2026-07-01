import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, expect, it } from 'vitest'
import App from './App'

describe('App', () => {
  it('초기 화면을 렌더링한다', () => {
    // given
    // App은 props 없이 렌더링된다.

    // when
    render(<App />)

    // then
    expect(screen.getByRole('heading', { name: /get started/i })).toBeInTheDocument()
    expect(screen.getByRole('button', { name: /count is 0/i })).toBeInTheDocument()
  })

  it('카운터 버튼을 클릭하면 숫자가 증가한다', async () => {
    // given
    const user = userEvent.setup()
    render(<App />)

    const countButton = screen.getByRole('button', { name: /count is 0/i })

    // when
    await user.click(countButton)

    // then
    expect(screen.getByRole('button', { name: /count is 1/i })).toBeInTheDocument()
  })
})