// Frontend / Backend 구조 흐름도 생성기
//   - 내장 Tomcat 을 Spring Boot(dashed) 박스 "안쪽"에 배치 (내장 = Spring Boot 포함)
//   - 모든 텍스트를 최상위 레이어에 그림 → 선/박스에 글자가 깔리지 않음
//   - 요청/응답은 서버(Tomcat/Vite)를 관통하는 단일 화살표 유지
//   node docs/assets/source/generate-structure.mjs

import { readFileSync, writeFileSync } from "node:fs";
import { fileURLToPath } from "node:url";
import { dirname, join } from "node:path";

const HERE = dirname(fileURLToPath(import.meta.url));
const OUT = join(HERE, "..", "structure.svg");

function iconURI(name) {
  const raw = readFileSync(join(HERE, `${name}.svg`), "utf8").replace(/^﻿/, "");
  return "data:image/svg+xml;base64," + Buffer.from(raw, "utf8").toString("base64");
}
const springboot = iconURI("springboot");
const react = iconURI("react");
const vite = iconURI("vite");

const W = 1060;
const H = 700;
const esc = (s) => String(s).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");

const rect = (x, y, w, h, fill, stroke, sw = 2, dash) =>
  `<rect x="${x}" y="${y}" width="${w}" height="${h}" rx="6" fill="${fill}" stroke="${stroke}" stroke-width="${sw}"${dash ? ` stroke-dasharray="${dash}"` : ""}/>`;
const ell = (cx, cy, rx, ry, fill) => `<ellipse cx="${cx}" cy="${cy}" rx="${rx}" ry="${ry}" fill="${fill}"/>`;
const arrow = (x1, y1, x2, y2) =>
  `<line x1="${x1}" y1="${y1}" x2="${x2}" y2="${y2}" stroke="#111827" stroke-width="2.4" marker-end="url(#ar)"/>`;
const img = (href, x, y, s) => `<image href="${href}" x="${x}" y="${y}" width="${s}" height="${s}"/>`;
const t = (x, y, s, cls) => `<text x="${x}" y="${y}" class="${cls}">${esc(s)}</text>`;

const C = {
  feBand: "#eff6ff", feBandLine: "#bfdbfe",
  beBand: "#f0fdf4", beBandLine: "#bbf7d0",
  browser: "#f8fafc",
  vite: "#f5f3ff", viteLine: "#7c3aed",
  tomcat: "#fff7ed", tomcatLine: "#ea580c",
  appBox: "#ffffff", appLine: "#2563eb",
  feOval: "#2563eb",
  controller: "#15914f",
  jsonConv: "#d97706",
  springDash: "#16a34a",
};

// 레이어별로 모아서 마지막에 순서대로 합침
const bg = [];      // 배경 + 박스 fill/border (텍스트 없음)
const shapes = [];  // 타원/내부 화살표
const lines = [];   // 관통 화살표
const servers = []; // 서버 박스 (관통선 위에 덮음)
const texts = [];   // 모든 글자/로고 (최상위)

// ── 레인 배경 ─────────────────────────────────────────────────
bg.push(rect(24, 72, W - 48, 268, C.feBand, C.feBandLine, 1.5));
bg.push(rect(24, 384, W - 48, 300, C.beBand, C.beBandLine, 1.5));

// ══════════════════════════════════════════════════════════════
// ① FRONTEND
// ══════════════════════════════════════════════════════════════
bg.push(rect(40, 128, 150, 188, C.browser, "#111827"));
bg.push(rect(470, 128, 540, 180, C.appBox, C.appLine, 2.4));
shapes.push(ell(590, 206, 80, 32, C.feOval));
shapes.push(arrow(670, 206, 720, 206));
shapes.push(ell(800, 206, 80, 32, C.feOval));
// 요청: browser → (Vite 관통) → main.tsx
lines.push(arrow(190, 188, 508, 188));
// 응답: React 앱 → (Vite 관통) → browser
lines.push(arrow(470, 296, 190, 296));
// 서버 박스(관통선 위)
servers.push(rect(300, 128, 120, 180, C.vite, C.viteLine, 2.4));
// 텍스트/로고
texts.push(t(44, 106, "① Frontend — React SPA (Vite dev server)", "lane"));
texts.push(t(115, 228, "웹 브라우저", "boxlbl"));
texts.push(t(492, 158, "React 앱", "title"));
texts.push(img(react, 946, 138, 54));
texts.push(t(590, 212, "main.tsx", "oval"));
texts.push(t(800, 212, "App.tsx", "oval"));
texts.push(t(560, 280, "createRoot() → <App /> 컴포넌트 렌더링", "note"));
texts.push(img(vite, 340, 146, 40));
texts.push(t(360, 232, "Vite 개발", "boxlbl"));
texts.push(t(360, 256, "서버", "boxlbl"));
texts.push(t(245, 177, "localhost:5173", "small"));
texts.push(t(245, 286, "HTML + JS 번들 (응답)", "smaller"));

// ══════════════════════════════════════════════════════════════
// ② BACKEND  (내장 Tomcat 이 Spring Boot dashed 박스 안쪽)
// ══════════════════════════════════════════════════════════════
bg.push(rect(40, 452, 150, 200, C.browser, "#111827"));
bg.push(rect(360, 430, 660, 240, "none", C.springDash, 2.4, "8 6")); // 스프링 부트 경계
bg.push(rect(540, 478, 460, 176, "#ffffff", "#111827", 2));          // 스프링 컨테이너
shapes.push(ell(660, 546, 84, 30, C.controller));
shapes.push(arrow(660, 576, 660, 590));
shapes.push(ell(660, 616, 94, 30, C.jsonConv));
// 요청: browser → (Tomcat 관통) → healthController
lines.push(arrow(190, 546, 574, 546));
// 응답: JSON 변환 → (Tomcat 관통) → browser
lines.push(arrow(566, 616, 190, 616));
// 내장 Tomcat 박스 (Spring Boot 안쪽, 관통선 위)
servers.push(rect(382, 486, 118, 156, C.tomcat, C.tomcatLine, 2.4));
// 텍스트/로고
texts.push(t(44, 418, "② Backend — REST API (Spring Boot · 내장 Tomcat)", "lane"));
texts.push(img(springboot, 650, 440, 34));
texts.push(t(692, 464, "스프링 부트", "brand"));
texts.push(t(115, 558, "웹 브라우저", "boxlbl"));
texts.push(t(770, 504, "스프링 컨테이너", "title"));
texts.push(t(660, 552, "healthController", "oval"));
texts.push(t(660, 622, "JSON 변환 (Jackson)", "oval"));
texts.push(t(766, 540, '@GetMapping("/api/health")', "codeGray"));
texts.push(t(766, 586, 'return Map.of("status","UP")', "codeBlue"));
texts.push(t(766, 620, "HttpMessageConverter 처리", "codeGray"));
texts.push(t(441, 570, "내장", "boxlbl"));
texts.push(t(441, 594, "톰캣 서버", "boxlbl"));
texts.push(t(275, 536, "localhost:8080/api/health", "smaller"));
texts.push(t(275, 606, 'JSON (응답)  {"status":"UP"}', "smaller"));

// ══════════════════════════════════════════════════════════════
const body = [...bg, ...shapes, ...lines, ...servers, ...texts].join("\n  ");
const svg = `<svg width="${W}" height="${H}" viewBox="0 0 ${W} ${H}" fill="none" xmlns="http://www.w3.org/2000/svg">
  <defs>
    <marker id="ar" markerWidth="11" markerHeight="11" refX="8" refY="3" orient="auto" markerUnits="strokeWidth">
      <path d="M0,0 L0,6 L9,3 z" fill="#111827"/>
    </marker>
    <style>
      .header { font: 700 28px 'Malgun Gothic', Arial, sans-serif; fill: #111827; }
      .lane { font: 700 16px 'Malgun Gothic', Arial, sans-serif; fill: #475569; }
      .boxlbl { font: 700 18px 'Malgun Gothic', Arial, sans-serif; fill: #111827; text-anchor: middle; }
      .title { font: 700 18px 'Malgun Gothic', Arial, sans-serif; fill: #111827; }
      .brand { font: 700 21px 'Malgun Gothic', Arial, sans-serif; fill: #111827; }
      .oval { font: 700 15px 'Malgun Gothic', Arial, sans-serif; fill: #ffffff; text-anchor: middle; }
      .small { font: 600 14px 'Malgun Gothic', Arial, sans-serif; fill: #334155; text-anchor: middle; }
      .smaller { font: 600 13px 'Malgun Gothic', Arial, sans-serif; fill: #334155; text-anchor: middle; }
      .note { font: 500 14px 'Malgun Gothic', Arial, sans-serif; fill: #64748b; text-anchor: middle; }
      .codeGray { font: 600 14px Consolas, 'Malgun Gothic', monospace; fill: #64748b; }
      .codeBlue { font: 700 15px Consolas, 'Malgun Gothic', monospace; fill: #1d4ed8; }
    </style>
  </defs>

  <rect width="${W}" height="${H}" fill="#ffffff"/>
  <text x="40" y="52" class="header">Frontend / Backend 구조</text>

  ${body}
</svg>
`;

writeFileSync(OUT, svg, "utf8");
console.log(`Wrote ${OUT} (${svg.length} bytes)`);
