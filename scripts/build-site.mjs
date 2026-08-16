// T2-2. 사이트가 읽을 데이터 번들을 만든다.
//   node scripts/build-site.mjs  ->  site/data/bundle.js
//
// 왜 .json 이 아니라 .js 인가:
// file:// 로 열면 브라우저가 fetch 를 막는다. <script src> 는 막지 않으므로,
// 번들을 전역 변수에 담아두면 GitHub Pages 에서도, 로컬에서 index.html 을 더블클릭해도 똑같이 돈다.
import fs from 'node:fs';
import path from 'node:path';
import { ROOT, authors, walkRepo, resolveSource } from './lib/map.mjs';

const tagDefs = JSON.parse(fs.readFileSync(path.join(ROOT, 'data/review-tags.json'), 'utf8')).tags;
const rotation = JSON.parse(fs.readFileSync(path.join(ROOT, 'data/rotation.json'), 'utf8'));

function readReview(rel) {
  const abs = path.join(ROOT, rel);
  if (!fs.existsSync(abs)) return null;
  const text = fs.readFileSync(abs, 'utf8').replace(/\r\n/g, '\n');
  const m = text.match(/^---\n([\s\S]*?)\n---\n?/);
  if (!m) return { meta: {}, body: text };

  const meta = {};
  for (const line of m[1].split('\n')) {
    const kv = line.match(/^(\w+):\s*(.*)$/);
    if (!kv) continue;
    const [, k, raw] = kv;
    const v = raw.trim().replace(/^["']|["']$/g, '');
    if (k === 'tags') meta.tags = v.replace(/^\[|\]$/g, '').split(',').map((s) => s.trim()).filter(Boolean);
    else if (v) meta[k] = v;
  }
  // complexity 는 중첩이라 별도로 긁는다
  const time = m[1].match(/^\s+time:\s*(.+)$/m)?.[1]?.trim();
  const space = m[1].match(/^\s+space:\s*(.+)$/m)?.[1]?.trim();
  if (time || space) meta.complexity = { time, space };

  return { meta, body: text.slice(m[0].length).trim() };
}

const solutions = walkRepo().map(resolveSource).filter((r) => r.ok);
const problems = new Map();

for (const s of solutions) {
  if (!problems.has(s.key)) {
    problems.set(s.key, {
      key: s.key, platform: s.platform, problemId: s.problemId,
      title: s.title, url: s.url, weeks: new Set(), entries: [],
    });
  }
  const p = problems.get(s.key);
  if (s.week) p.weeks.add(s.week);

  const review = readReview(s.reviewTarget);
  p.entries.push({
    author: s.author,
    authorName: s.authorName,
    week: s.week,
    variant: s.variant,
    source: s.source,
    lines: s.lines,
    code: fs.readFileSync(path.join(ROOT, s.source), 'utf8').replace(/\r\n/g, '\n').replace(/\s+$/, ''),
    review: review ? { ...review.meta, body: review.body } : null,
  });
}

const list = [...problems.values()]
  .map((p) => ({ ...p, weeks: [...p.weeks].sort((a, b) => a - b) }))
  .sort((a, b) => a.platform.localeCompare(b.platform) || Number(a.problemId) - Number(b.problemId));

// 작성자 순서를 고정해서 비교 뷰의 열 순서가 매번 같게 한다
const order = authors.map((a) => a.id);
for (const p of list) {
  p.entries.sort((a, b) => order.indexOf(a.author) - order.indexOf(b.author) || (a.variant ? 1 : -1));
}

const data = {
  generatedAt: new Date().toISOString().slice(0, 10),
  authors: authors.map(({ id, displayName }) => ({ id, displayName })),
  tags: tagDefs,
  rotation: { order: rotation.order, cadence: rotation.cadence, assignments: rotation.assignments },
  problems: list,
};

const outPath = path.join(ROOT, 'site/data/bundle.js');
fs.mkdirSync(path.dirname(outPath), { recursive: true });
fs.writeFileSync(outPath, `window.STUDY_DATA = ${JSON.stringify(data)};\n`);

const reviewed = list.flatMap((p) => p.entries).filter((e) => e.review).length;
const total = list.reduce((a, p) => a + p.entries.length, 0);
const kb = Math.round(fs.statSync(outPath).size / 1024);
console.log(`-> site/data/bundle.js  (${list.length}문제 / ${total}풀이 / 리뷰 ${reviewed}건 / ${kb}KB)`);
