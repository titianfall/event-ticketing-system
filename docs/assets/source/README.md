# Source Images

README 구조 흐름도(`../structure.svg`)에서 사용하는 기술 로고 원본을 보관한다.

현재 파일은 Simple Icons CDN에서 SVG 형식으로 내려받았다.

구조 흐름도는 [generate-structure.mjs](generate-structure.mjs)로 생성하며, README 렌더링 안정성을 위해 이 디렉토리의 로고 SVG를 data URI로 인라인한다.

## 갱신 방법

```bash
node docs/assets/source/generate-structure.mjs
```

구조(요청 흐름, 포트, 컨트롤러 등)가 바뀌면 `generate-structure.mjs`를 수정한 뒤 위 명령으로 `../structure.svg`를 다시 생성한다. 새 로고가 필요하면 이 디렉토리에 원본 SVG를 먼저 저장한다.
