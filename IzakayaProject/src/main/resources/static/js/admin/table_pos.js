/**
 *
 */
const STORE_ID = window.STORE_ID ?? 0;

(() => {
  const yen = (n) => '¥' + Number(n || 0).toLocaleString('ja-JP');

  const getCsrf = () => {
    const token = document.querySelector('meta[name="_csrf"]')?.getAttribute('content') || '';
    const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || '';
    return { token, header };
  };

  // ===== CLOCK =====
  const clockEl = document.getElementById('clock');
  const tickClock = () => {
    const d = new Date();
    const yy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const dd = String(d.getDate()).padStart(2, '0');
    const hh = String(d.getHours()).padStart(2, '0');
    const mi = String(d.getMinutes()).padStart(2, '0');
    const ss = String(d.getSeconds()).padStart(2, '0');
    clockEl.textContent = `${yy}-${mm}-${dd} ${hh}:${mi}:${ss}`;
  };
  tickClock();
  setInterval(tickClock, 1000);

  // ===== TOPBAR =====
  const elCashSales = document.getElementById('cashSales');
  const elCardSales = document.getElementById('cardSales');
  const elTotalSales = document.getElementById('totalSales');

  const loadTopbar = async () => {
    const res = await fetch(`/api/pos/${STORE_ID}/topbar`, { cache: 'no-store', credentials: 'same-origin' });
    if (!res.ok) throw new Error('topbar failed');
    return await res.json();
  };

  const renderTopbar = (data) => {
    const cash = Number(data.cashSales || 0);
    const card = Number(data.cardSales || 0);
    const total = (data.totalSales !== undefined && data.totalSales !== null)
      ? Number(data.totalSales || 0)
      : (cash + card);

    elCashSales.textContent = yen(cash);
    elCardSales.textContent = yen(card);
    elTotalSales.textContent = yen(total);
  };

  const tickTopbar = async () => {
    try { renderTopbar(await loadTopbar()); } catch (e) { }
  };

  // ===== RESERVATIONS =====
  const resvPendingList = document.getElementById('resvPendingList');
  const resvAcceptedList = document.getElementById('resvAcceptedList');
  const fmt = (isoLike) => String(isoLike || '').replace('T', ' ').slice(0, 16);

  // ✅✅ [추가] 다양한 키를 허용해서 이름/전화가 '-'로 뜨는 문제 방지
  const pickFirst = (obj, keys) => {
    for (const k of keys) {
      const v = obj?.[k];
      if (v !== null && v !== undefined && String(v).trim() !== '') return v;
    }
    return null;
  };
  const getResvName = (r) => pickFirst(r, ['customerName', 'name', 'customer', 'userName', 'nickname']);
  const getResvPhone = (r) => pickFirst(r, ['phone', 'phoneNumber', 'tel', 'mobile']);

  // ✅ 현재 화면 상태 캐시
  let PENDING_CACHE = [];

  // ✅✅ accepted는 "덮어쓰기" 대신 Map으로 누적 유지
  const ACCEPTED_KEEP_MS = 10 * 60 * 1000; // visitAt + 10분까지 좌측에 보이게
  const ACCEPTED_MAP = new Map();          // id -> reservation
  let ACCEPTED_CACHE = [];                // Map values -> array (정렬용)

  // ✅✅ 예약 5분 전 알림 중복 방지
  const NOTIFIED_IDS = new Set();

  const visitMs = (r) => {
    const t = new Date(r?.visitAt).getTime();
    return isNaN(t) ? NaN : t;
  };

  const isExpiredForLeftPanel = (r, nowMs) => {
    const t = visitMs(r);
    if (isNaN(t)) return false;
    return nowMs > (t + ACCEPTED_KEEP_MS);
  };

  const rebuildAcceptedAndRender = () => {
    const nowMs = Date.now();

    // ❌ 여기서 ACCEPTED_MAP.delete()로 데이터 자체를 지우면 안 됩니다.
    // ✅ ACCEPTED_MAP은 "유지"하고, 좌측 패널에서만 숨김 처리합니다.

    ACCEPTED_CACHE = Array.from(ACCEPTED_MAP.values())
      .sort((a,b) => (visitMs(a) || 0) - (visitMs(b) || 0));

    // ✅ 좌측(受付済み予約)에는 10분 지난 건 숨김
    const acceptedForLeft = ACCEPTED_CACHE.filter(r => !isExpiredForLeftPanel(r, nowMs));

    renderReservationsAccepted(acceptedForLeft);

    // ✅ 알림은 "다가오는 예약"만 체크하므로 ACCEPTED_CACHE 유지해도 괜찮습니다.
    checkReservationAlarm();
  };

  // ✅ pending
  const loadReservationsPending = async () => {
    const res = await fetch(`/admin/store/${STORE_ID}/reservations/pending`, {
      credentials: 'same-origin',
      cache: 'no-store'
    });
    if (!res.ok) throw new Error('resv pending load failed');
    return await res.json();
  };

  // ✅ accepted (수락된 예약)
  const loadReservationsAccepted = async () => {
    const res = await fetch(`/admin/store/${STORE_ID}/reservations/accepted`, {
      credentials: 'same-origin',
      cache: 'no-store'
    });
    if (!res.ok) throw new Error('resv accepted load failed');
    return await res.json();
  };

  const resvAction = async (type, id) => {
    const { token, header } = getCsrf();
    const headers = {};
    if (token && header) headers[header] = token;

    const url = `/admin/store/${STORE_ID}/reservations/${id}/${type}`;
    const res = await fetch(url, { method: 'POST', credentials: 'same-origin', headers });
    if (!res.ok) throw new Error('resv action failed');
  };

  // ✅✅ [추가] pending에서 이름/전화가 안 내려오는 경우가 있어, 상세 조회로 보정
  const toYmdLocal = (d) => {
    const yy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const dd = String(d.getDate()).padStart(2, '0');
    return `${yy}-${mm}-${dd}`;
  };

  const updatePendingCardDom = (id, name, phone) => {
    const card = resvPendingList?.querySelector(`.resv-item[data-id="${id}"]`);
    if (!card) return;
    const nameEl = card.querySelector('.js-name');
    const phoneEl = card.querySelector('.js-phone');
    if (nameEl && name) nameEl.textContent = String(name);
    if (phoneEl && phone) phoneEl.textContent = String(phone);
  };

  const normalizeDetail = (obj) => {
    if (!obj || typeof obj !== 'object') return null;
    const name = getResvName(obj);
    const phone = getResvPhone(obj);
    const id = Number(obj.id ?? obj.reservationId ?? obj.idx);
    return { id, name, phone, raw: obj };
  };

  const fetchJsonIfOk = async (url) => {
    try {
      const res = await fetch(url, { credentials: 'same-origin', cache: 'no-store' });
      if (!res.ok) return null;
      return await res.json();
    } catch (e) {
      return null;
    }
  };

  // ✅✅ 가능한 상세 엔드포인트들을 "있으면" 사용 (없으면 조용히 실패)
  const tryFetchReservationDetail = async (id, visitAt) => {
    // 1) 흔히 있는 단건 상세
    const d1 = await fetchJsonIfOk(`/admin/store/${STORE_ID}/reservations/${id}`);
    if (d1) return d1;

    // 2) detail 변형
    const d2 = await fetchJsonIfOk(`/admin/store/${STORE_ID}/reservations/detail?id=${encodeURIComponent(id)}`);
    if (d2) return d2;

    // 3) history로 보정 (visitAt 날짜 기준)
    const base = visitAt ? new Date(visitAt) : null;
    const ymd = (base && !isNaN(base.getTime())) ? toYmdLocal(base) : toYmdLocal(new Date());
    const arr = await fetchJsonIfOk(`/admin/store/${STORE_ID}/reservations/history?date=${encodeURIComponent(ymd)}`);
    if (Array.isArray(arr)) {
      const found = arr.find(x => Number(x?.id) === Number(id));
      if (found) return found;
    }

    return null;
  };

  // ✅✅ pending 아이템 중 name/phone 비어있는 애만 상세 조회해서 채움
  const PENDING_DETAIL_LOADING = new Set(); // 중복 호출 방지
  const enrichPendingDetails = async (items) => {
    if (!items || items.length === 0) return;

    for (const r of items) {
      const id = Number(r?.id);
      if (!id) continue;

      const hasName = !!getResvName(r);
      const hasPhone = !!getResvPhone(r);
      if (hasName && hasPhone) continue;

      if (PENDING_DETAIL_LOADING.has(id)) continue;
      PENDING_DETAIL_LOADING.add(id);

      (async () => {
        const detail = await tryFetchReservationDetail(id, r?.visitAt);
        const norm = normalizeDetail(detail);

        if (norm && norm.id) {
          // ✅ 캐시 갱신
          const idx = PENDING_CACHE.findIndex(x => Number(x?.id) === norm.id);
          if (idx >= 0) {
            const merged = { ...PENDING_CACHE[idx] };
            if (norm.name) merged.customerName = norm.name; // 통일 저장
            if (norm.phone) merged.phone = norm.phone;      // 통일 저장
            PENDING_CACHE[idx] = merged;
          }

          // ✅ DOM 갱신
          updatePendingCardDom(norm.id, norm.name, norm.phone);
        }

        PENDING_DETAIL_LOADING.delete(id);
      })();
    }
  };

  const renderReservationsPending = (items) => {
    resvPendingList.innerHTML = '';
    if (!items || items.length === 0) return;

    for (const r of items) {
      const id = Number(r?.id);
      const div = document.createElement('div');
      div.className = 'resv-item';
      if (id) div.setAttribute('data-id', String(id));

      div.innerHTML = `
        <div class="resv-meta">
          <div><b>人数</b>: ${r.headcount ?? '-'}</div>
          <div><b>来店</b>: ${fmt(r.visitAt)}</div>
          <div><b>名前</b>: <span class="js-name">${getResvName(r) ?? '-'}</span></div>
          <div><b>電話</b>: <span class="js-phone">${getResvPhone(r) ?? '-'}</span></div>
          <div class="resv-id"><b>ID</b>: ${r.id}</div>
        </div>
        <div class="resv-actions">
          <button class="btn-ok" data-ract="accept" data-id="${r.id}">承認</button>
          <button class="btn-no" data-ract="reject" data-id="${r.id}">拒否</button>
        </div>
      `;
      resvPendingList.appendChild(div);
    }

    // ✅✅ [추가] 렌더 후에 missing name/phone만 상세조회로 채우기
    enrichPendingDetails(items);
  };

  const renderReservationsAccepted = (items) => {
    resvAcceptedList.innerHTML = '';
    if (!items || items.length === 0) return;

    for (const r of items) {
      const div = document.createElement('div');
      div.className = 'resv-item';
      div.innerHTML = `
        <div class="resv-meta">
          <div><b>人数</b>: ${r.headcount ?? '-'}</div>
          <div><b>来店</b>: ${fmt(r.visitAt)}</div>
          <div><b>名前</b>: ${getResvName(r) ?? '-'}</div>
          <div><b>電話</b>: ${getResvPhone(r) ?? '-'}</div>
          <div class="resv-id"><b>ID</b>: ${r.id}</div>
        </div>
      `;
      resvAcceptedList.appendChild(div);
    }
  };

  // ✅✅ 예약 5분 전 POS 알림
  const checkReservationAlarm = () => {
    const now = new Date();

    for (const r of ACCEPTED_CACHE) {
      if (!r || !r.id || !r.visitAt) continue;
      if (NOTIFIED_IDS.has(r.id)) continue;

      const visit = new Date(r.visitAt);
      if (isNaN(visit.getTime())) continue;

      if (visit <= now) continue;

      const diffMin = (visit - now) / 60000;

      if (diffMin <= 5 && diffMin > 4) {
        NOTIFIED_IDS.add(r.id);
        alert(
          `【予約通知】\n` +
          `来店まで5分です\n` +
          `名前: ${getResvName(r) ?? '-'}\n` +
          `人数: ${r.headcount ?? '-'}名\n` +
          `時間: ${fmt(r.visitAt)}`
        );
      }
    }
  };

  const refreshReservations = async () => {
    try {
      PENDING_CACHE = await loadReservationsPending();
      renderReservationsPending(PENDING_CACHE);
    } catch (e) { }

    try {
      const fetched = await loadReservationsAccepted();
      const arr = Array.isArray(fetched) ? fetched : [];

      for (const r of arr) {
        const id = Number(r?.id);
        if (!id) continue;
        ACCEPTED_MAP.set(id, r);
      }

      rebuildAcceptedAndRender();
    } catch (e) {
      rebuildAcceptedAndRender();
    }
  };

  // ✅ pending 목록에서 클릭(수락/거절)
  resvPendingList.addEventListener('click', async (e) => {
    const b = e.target.closest('button[data-ract][data-id]');
    if (!b) return;

    const act = b.getAttribute('data-ract');
    const id = Number(b.getAttribute('data-id'));

    const picked = PENDING_CACHE.find(x => Number(x.id) === id);

    try {
      await resvAction(act, id);

      PENDING_CACHE = PENDING_CACHE.filter(x => Number(x.id) !== id);
      renderReservationsPending(PENDING_CACHE);

      if (act === 'accept' && picked) {
        const pid = Number(picked.id);
        if (pid) ACCEPTED_MAP.set(pid, picked);
        rebuildAcceptedAndRender();
      }

      await refreshReservations();

    } catch (err) {
      alert('処理に失敗しました');
    }
  });

  document.getElementById('resvRefresh')?.addEventListener('click', async () => {
    await refreshReservations();
  });

  /* ======================================================
     ✅✅ 예약 전체 목록(수락/거절 포함) + 달력 조회
     ====================================================== */
  const resvHistoryModal = document.getElementById('resvHistoryModal');
  const btnResvHistory = document.getElementById('btnResvHistory');
  const resvHistoryClose = document.getElementById('resvHistoryClose');
  const resvHistoryDate = document.getElementById('resvHistoryDate');
  const resvHistoryList = document.getElementById('resvHistoryList');
  const resvHistoryDetail = document.getElementById('resvHistoryDetail');

  // ✅✅✅ [핵심 수정] 예약 필터/요약 DOM + 상태값은 여기(전역 스코프)에서 잡아야 함
  const resvStatusFilter = document.getElementById('resvStatusFilter');
  const resvSumAccepted = document.getElementById('resvSumAccepted');
  const resvSumRejected = document.getElementById('resvSumRejected');
  const resvSumTotal = document.getElementById('resvSumTotal');
  let RESV_FILTER = 'ALL'; // ALL / ACCEPTED / REJECTED

  const statusLabel = (s) => {
    const k = String(s || '').toUpperCase();
    if (k === 'ACCEPTED') return { text: '受付', cls: 'st-accepted' };
    if (k === 'REJECTED') return { text: '拒否', cls: 'st-rejected' };
    if (k === 'PENDING') return { text: '承認待ち', cls: 'st-pending' };
    return { text: k || '-', cls: 'st-other' };
  };
  const normResvStatus = (s) => String(s || '').toUpperCase();

  const filterResvItems = (items, filterKey) => {
    const fk = String(filterKey || 'ALL').toUpperCase();
    if (fk === 'ALL') return items || [];
    return (items || []).filter(r => normResvStatus(r.status) === fk);
  };

  const sumResvCounts = (items) => {
    let accepted = 0, rejected = 0, total = 0;
    for (const r of (items || [])) {
      total += 1;
      const st = normResvStatus(r.status);
      if (st === 'ACCEPTED') accepted += 1;
      else if (st === 'REJECTED') rejected += 1;
    }
    return { accepted, rejected, total };
  };

  const renderResvSummary = (sum) => {
    if (resvSumAccepted) resvSumAccepted.textContent = String(sum?.accepted ?? 0);
    if (resvSumRejected) resvSumRejected.textContent = String(sum?.rejected ?? 0);
    if (resvSumTotal) resvSumTotal.textContent = String(sum?.total ?? 0);
  };

  const applyResvFilterAndRender = () => {
    const filtered = filterResvItems(HISTORY_CACHE, RESV_FILTER);
    renderResvSummary(sumResvCounts(filtered));
    renderHistoryList(filtered);
    renderHistoryDetail(null);
  };

  const safe = (v) => (v === null || v === undefined || v === '') ? '-' : String(v);

  let HISTORY_CACHE = [];
  let HISTORY_ACTIVE_ID = null;

  const openHistory = async () => {
    resvHistoryModal.style.display = 'flex';
    resvHistoryModal.setAttribute('aria-hidden', 'false');
    const ymd = resvHistoryDate.value || toYmdLocal(new Date());
    resvHistoryDate.value = ymd;

    // ✅ 모달 열 때 항상 기본은 ALL
    RESV_FILTER = 'ALL';
    if (resvStatusFilter) {
      resvStatusFilter.value = 'ALL';
      resvStatusFilter.onchange = () => {
        RESV_FILTER = (resvStatusFilter.value || 'ALL').toUpperCase();
        applyResvFilterAndRender();
      };
    }

    await loadHistory(ymd);
  };

  const closeHistory = () => {
    resvHistoryModal.style.display = 'none';
    resvHistoryModal.setAttribute('aria-hidden', 'true');
    HISTORY_ACTIVE_ID = null;
  };

  btnResvHistory?.addEventListener('click', openHistory);
  resvHistoryClose?.addEventListener('click', closeHistory);
  resvHistoryModal?.addEventListener('click', (e) => {
    if (e.target === resvHistoryModal) closeHistory();
  });

  const loadHistory = async (ymd) => {
    try {
      const res = await fetch(`/admin/store/${STORE_ID}/reservations/history?date=${encodeURIComponent(ymd)}`, {
        credentials: 'same-origin',
        cache: 'no-store'
      });
      if (!res.ok) throw new Error('history load failed');

      const arr = await res.json();
      HISTORY_CACHE = Array.isArray(arr) ? arr : [];
      applyResvFilterAndRender();
    } catch (e) {
      HISTORY_CACHE = [];
      if (resvHistoryList) resvHistoryList.innerHTML =
        '<div style="padding:10px;opacity:.75;font-size:13px;">読み込みに失敗しました。</div>';
      renderResvSummary({ accepted: 0, rejected: 0, total: 0 });
      renderHistoryDetail(null);
    }
  };

  const renderHistoryList = (items) => {
    resvHistoryList.innerHTML = '';
    HISTORY_ACTIVE_ID = null;

    if (!items || items.length === 0) {
      resvHistoryList.innerHTML = '<div style="padding:10px;opacity:.75;font-size:13px;">予約がありません。</div>';
      return;
    }

    const sorted = [...items].sort((a, b) => {
      const av = new Date(a.visitAt || 0).getTime();
      const bv = new Date(b.visitAt || 0).getTime();
      return av - bv;
    });

    for (const r of sorted) {
      const st = statusLabel(r.status);
      const div = document.createElement('div');
      div.className = 'resv-h-item';
      div.setAttribute('data-id', String(r.id));

      const time = fmt(r.visitAt);
      div.innerHTML = `
        <div class="resv-h-top">
          <div class="resv-h-time">${time}</div>
          <div class="resv-h-status ${st.cls}">${st.text}</div>
        </div>
        <div class="resv-h-name">${safe(getResvName(r) ?? r.customerName)}</div>
        <div class="resv-h-mini">
          <div>人数: <b>${safe(r.headcount)}</b></div>
          <div>電話: <b>${safe(getResvPhone(r) ?? r.phone)}</b></div>
          <div>ID: <b>${safe(r.id)}</b></div>
		  <div>予約作成: <b>${fmt(r.createdAt)}</b></div>

        </div>
      `;

      div.addEventListener('click', () => {
        HISTORY_ACTIVE_ID = r.id;
        for (const n of resvHistoryList.querySelectorAll('.resv-h-item')) n.classList.remove('active');
        div.classList.add('active');
        renderHistoryDetail(r);
      });

      resvHistoryList.appendChild(div);
    }
  };

  const renderHistoryDetail = (r) => {
    if (!r) {
      resvHistoryDetail.innerHTML = '予約を選択してください。';
      resvHistoryDetail.style.opacity = '.75';
      return;
    }
    resvHistoryDetail.style.opacity = '1';

    const st = statusLabel(r.status);

    resvHistoryDetail.innerHTML = `
      <div class="resv-d-row"><div class="resv-d-k">状態</div><div class="resv-d-v"><span class="${st.cls}" style="padding:3px 10px;border-radius:999px;display:inline-block;">${st.text}</span></div></div>
      <div class="resv-d-row"><div class="resv-d-k">来店</div><div class="resv-d-v">${fmt(r.visitAt)}</div></div>
      <div class="resv-d-row"><div class="resv-d-k">名前</div><div class="resv-d-v">${safe(getResvName(r) ?? r.customerName)}</div></div>
      <div class="resv-d-row"><div class="resv-d-k">電話</div><div class="resv-d-v">${safe(getResvPhone(r) ?? r.phone)}</div></div>
      <div class="resv-d-row"><div class="resv-d-k">人数</div><div class="resv-d-v">${safe(r.headcount)}</div></div>
      <div class="resv-d-row"><div class="resv-d-k">予約ID</div><div class="resv-d-v">${safe(r.id)}</div></div>
	  <div class="resv-d-row"><div class="resv-d-k">予約作成</div><div class="resv-d-v">${fmt(r.createdAt)}</div></div>

    `;
  };

  resvHistoryDate?.addEventListener('change', async () => {
    const ymd = resvHistoryDate.value;
    if (!ymd) return;

    // ✅ 날짜 바꾸면 기본은 ALL
    RESV_FILTER = 'ALL';
    if (resvStatusFilter) resvStatusFilter.value = 'ALL';

    await loadHistory(ymd);
  });

  /* ======================================================
     ✅✅ 매출 목록 + 달력 조회 (추가)
     ====================================================== */
  const salesHistoryModal = document.getElementById('salesHistoryModal');
  const btnSalesHistory = document.getElementById('btnSalesHistory');
  const salesHistoryClose = document.getElementById('salesHistoryClose');
  const salesHistoryDate = document.getElementById('salesHistoryDate');
  const salesHistoryList = document.getElementById('salesHistoryList');
  const salesHistoryDetail = document.getElementById('salesHistoryDetail');

  const salesSumCash = document.getElementById('salesSumCash');
  const salesSumCard = document.getElementById('salesSumCard');
  const salesSumTotal = document.getElementById('salesSumTotal');

  let salesHistoryFilter = document.getElementById('salesMethodFilter');
  let SALES_FILTER = 'ALL'; // ALL / CASH / CARD

  let SALES_CACHE = null; // { summary, items }
  let SALES_ACTIVE_ID = null;

  const openSalesHistory = async () => {
    if (!salesHistoryModal) return;
    salesHistoryModal.style.display = 'flex';
    salesHistoryModal.setAttribute('aria-hidden', 'false');

    const ymd = (salesHistoryDate?.value) || toYmdLocal(new Date());
    if (salesHistoryDate) salesHistoryDate.value = ymd;

    // ✅✅ 모달 열 때마다 최신 DOM 다시 잡기 + 이벤트 보장
    salesHistoryFilter = document.getElementById('salesMethodFilter');

    if (salesHistoryFilter) {
      if (!salesHistoryFilter.value) salesHistoryFilter.value = 'ALL';
      SALES_FILTER = (salesHistoryFilter.value || 'ALL').toUpperCase();

      salesHistoryFilter.onchange = () => {
        SALES_FILTER = (salesHistoryFilter.value || 'ALL').toUpperCase();
        applySalesFilterAndRender();
      };
    } else {
      SALES_FILTER = 'ALL';
    }

    await loadSalesHistory(ymd);
  };

  const closeSalesHistory = () => {
    if (!salesHistoryModal) return;
    salesHistoryModal.style.display = 'none';
    salesHistoryModal.setAttribute('aria-hidden', 'true');
  };

  if (btnSalesHistory && salesHistoryModal) btnSalesHistory.addEventListener('click', openSalesHistory);
  if (salesHistoryClose && salesHistoryModal) salesHistoryClose.addEventListener('click', closeSalesHistory);

  if (salesHistoryModal) {
    salesHistoryModal.addEventListener('click', (e) => {
      if (e.target === salesHistoryModal) closeSalesHistory();
    });
  }

  const payMethodLabel = (saleOrMethod) => {
    const method = (typeof saleOrMethod === 'string') ? saleOrMethod : saleOrMethod?.method;
    const m = String(method || '').toUpperCase();

    if (m === 'CASH') return '現金';
    if (m === 'CARD') return 'カード';

    const card = Number(
      saleOrMethod?.cardAmount ??
      saleOrMethod?.cardSales ??
      saleOrMethod?.card ??
      0
    );
    return (card > 0) ? 'カード' : '現金';
  };

  const filterSalesItems = (items, filterKey) => {
    const fk = String(filterKey || 'ALL').toUpperCase();
    if (fk === 'ALL') return items || [];

    return (items || []).filter(s => {
      const label = payMethodLabel(s);
      if (fk === 'CASH') return label === '現金';
      if (fk === 'CARD') return label === 'カード';
      return true;
    });
  };

  const sumFromItems = (items) => {
    let cash = 0, card = 0, total = 0;
    for (const s of (items || [])) {
      cash += Number(s.cashAmount || 0);
      card += Number(s.cardAmount || 0);
      total += Number(s.totalAmount || 0);
    }
    return { cash, card, total };
  };

  const renderSalesSummary = (summary) => {
    const cash = Number(summary?.cash ?? 0);
    const card = Number(summary?.card ?? 0);
    const total = (summary?.total !== undefined && summary?.total !== null)
      ? Number(summary.total)
      : (cash + card);

    if (salesSumCash) salesSumCash.textContent = yen(cash);
    if (salesSumCard) salesSumCard.textContent = yen(card);
    if (salesSumTotal) salesSumTotal.textContent = yen(total);
  };

  const renderSalesDetail = (s) => {
    if (!s) {
      if (salesHistoryDetail) {
        salesHistoryDetail.innerHTML = '売上を選択してください。';
        salesHistoryDetail.style.opacity = '.75';
      }
      return;
    }
    if (!salesHistoryDetail) return;

    salesHistoryDetail.style.opacity = '1';

    const menus = Array.isArray(s.menus) ? s.menus : [];

    const menuLines = menus.length
      ? menus.map(it => {
        const qty = Number(it.qty ?? 1);
        const price = Number(it.price ?? 0);
        const line = price * qty;
        return `
            <div class="resv-d-row">
              <div class="resv-d-k" style="min-width:0;">${safe(it.name)}</div>
              <div class="resv-d-v" style="text-align:right;">
                ×${qty}　<b>${yen(line)}</b>
              </div>
            </div>
          `;
      }).join('')
      : `<div style="padding:8px 0;opacity:.75;">メニュー情報がありません。</div>`;

    salesHistoryDetail.innerHTML = `
      <div class="resv-d-row">
        <div class="resv-d-k">決済時間</div>
        <div class="resv-d-v">${fmt(s.paidAt)}</div>
      </div>

      <div class="resv-d-row">
        <div class="resv-d-k">テーブル</div>
        <div class="resv-d-v"><b>TABLE ${safe(s.tableNo)}</b></div>
      </div>

      <div class="resv-d-row">
        <div class="resv-d-k">決済方法</div>
        <div class="resv-d-v"><b>${payMethodLabel(s)}</b></div>
      </div>

      <div class="resv-d-row">
        <div class="resv-d-k">現金</div>
        <div class="resv-d-v">${yen(s.cashAmount)}</div>
      </div>

      <div class="resv-d-row">
        <div class="resv-d-k">カード</div>
        <div class="resv-d-v">${yen(s.cardAmount)}</div>
      </div>

      <div class="resv-d-row">
        <div class="resv-d-k">合計</div>
        <div class="resv-d-v"><b>${yen(s.totalAmount)}</b></div>
      </div>

      <div style="margin-top:12px;font-weight:900;">メニュー</div>
      <div style="margin-top:6px;">${menuLines}</div>
    `;
  };

  const renderSalesList = (items) => {
    if (!salesHistoryList) return;

    salesHistoryList.innerHTML = '';
    SALES_ACTIVE_ID = null;

    if (!items || items.length === 0) {
      salesHistoryList.innerHTML = '<div style="padding:10px;opacity:.75;font-size:13px;">売上がありません。</div>';
      renderSalesDetail(null);
      return;
    }

    const sorted = [...items].sort((a, b) => {
      const av = new Date(a.paidAt || 0).getTime();
      const bv = new Date(b.paidAt || 0).getTime();
      return bv - av;
    });

    for (const s of sorted) {
      const saleId = s.saleId ?? s.id ?? '';
      const div = document.createElement('div');
      div.className = 'sales-h-item';
      div.setAttribute('data-id', String(saleId));

      div.innerHTML = `
        <div class="sales-h-top" style="display:flex;justify-content:space-between;align-items:center;">
          <div style="display:flex;gap:10px;align-items:center;">
            <div class="sales-h-method" style="font-weight:900;">${payMethodLabel(s)}</div>
            <div class="sales-h-table" style="font-weight:900;opacity:.85;">TABLE ${safe(s.tableNo)}</div>
          </div>
          <div class="sales-h-total" style="font-weight:900;">${yen(s.totalAmount)}</div>
        </div>
        <div style="margin-top:6px;opacity:.75;font-size:12px;">
          ${fmt(s.paidAt)}
        </div>
      `;

      div.addEventListener('click', () => {
        SALES_ACTIVE_ID = saleId;
        for (const n of salesHistoryList.querySelectorAll('.sales-h-item')) n.classList.remove('active');
        div.classList.add('active');
        renderSalesDetail(s);
      });

      salesHistoryList.appendChild(div);
    }

    const first = salesHistoryList.querySelector('.sales-h-item');
    if (first) first.click();
  };

  const applySalesFilterAndRender = () => {
    const items = SALES_CACHE?.items || [];
    const filtered = filterSalesItems(items, SALES_FILTER);

    renderSalesSummary(sumFromItems(filtered));
    renderSalesList(filtered);
  };

  const loadSalesHistory = async (ymd) => {
    try {
      const res = await fetch(`/admin/store/${STORE_ID}/sales/history?date=${encodeURIComponent(ymd)}`, {
        credentials: 'same-origin',
        cache: 'no-store'
      });
      if (!res.ok) throw new Error('sales history load failed');

      SALES_CACHE = (await res.json()) || {};
      applySalesFilterAndRender();
    } catch (e) {
      SALES_CACHE = null;
      renderSalesSummary({ cash: 0, card: 0, total: 0 });
      if (salesHistoryList) salesHistoryList.innerHTML = '<div style="padding:10px;opacity:.75;font-size:13px;">読み込みに失敗しました。</div>';
      renderSalesDetail(null);
    }
  };

  salesHistoryDate?.addEventListener('change', async () => {
    const ymd = salesHistoryDate.value;
    if (!ymd) return;

    SALES_FILTER = 'ALL';
    const sel = document.getElementById('salesMethodFilter');
    if (sel) sel.value = 'ALL';

    await loadSalesHistory(ymd);
  });

  // ===== TABLES =====
  const tableGrid = document.getElementById('tableGrid');
  const TABLES = Array.from({ length: 9 }, (_, i) => ({ no: i + 1 }));

  const TABLE_CARTS = new Map();
  const getTableCart = (tableNo) => {
    if (!TABLE_CARTS.has(tableNo)) TABLE_CARTS.set(tableNo, new Map());
    return TABLE_CARTS.get(tableNo);
  };
  // ✅✅ [추가] TABLE_CARTS 영구 저장(localStorage) - 페이지 나갔다 와도 유지
  const STORAGE_KEY = `pos.tableCarts.v1.store.${STORE_ID}`;

  const saveTableCarts = () => {
    try {
      const obj = {};
      for (const [tableNo, cartMap] of TABLE_CARTS.entries()) {
        // cartMap: Map(menuId -> {menuId, menuCode, name, price, qty})
        obj[String(tableNo)] = Array.from(cartMap.values());
      }
      localStorage.setItem(STORAGE_KEY, JSON.stringify(obj));
    } catch (e) { /* ignore */ }
  };

  const loadTableCarts = () => {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      if (!raw) return;

      const obj = JSON.parse(raw);
      if (!obj || typeof obj !== 'object') return;

      TABLE_CARTS.clear();

      for (const [tableNoStr, arr] of Object.entries(obj)) {
        const tableNo = Number(tableNoStr);
        if (!tableNo || !Array.isArray(arr)) continue;

        const m = new Map();
        for (const it of arr) {
          const menuId = Number(it?.menuId);
          if (!menuId && menuId !== 0) continue;

          m.set(menuId, {
            menuId,
            menuCode: it?.menuCode ?? null,
            name: String(it?.name ?? ''),
            price: Number(it?.price ?? 0),
            qty: Number(it?.qty ?? 0)
          });
        }
        TABLE_CARTS.set(tableNo, m);
      }
    } catch (e) { /* ignore */ }
  };
  const cloneCartMap = (srcMap) => {
    const m = new Map();
    for (const [k, v] of srcMap.entries()) {
      m.set(k, { ...v });
    }
    return m;
  };

  const commitDraftToTable = (tableNo, draftMap) => {
    TABLE_CARTS.set(tableNo, cloneCartMap(draftMap));
    saveTableCarts(); // ✅✅ [추가] 저장
  };

  const calcTotal = (cartMap) => {
    let t = 0;
    for (const it of cartMap.values()) t += (it.price * it.qty);
    return t;
  };

  let PAY_INTENT = null;

  const btnCash = document.getElementById('btnCash');
  const btnCard = document.getElementById('btnCard');

  btnCash.addEventListener('click', (e) => { e.stopPropagation(); PAY_INTENT = 'CASH'; });
  btnCard.addEventListener('click', (e) => { e.stopPropagation(); PAY_INTENT = 'CARD'; });

  document.addEventListener('click', (e) => {
    const inTable = e.target.closest('.table-btn');
    const inPayBtn = e.target.closest('#btnCash') || e.target.closest('#btnCard');
    const inModal = e.target.closest('#orderModal');
    const inHistoryModal = e.target.closest('#resvHistoryModal');
    const inSalesModal = e.target.closest('#salesHistoryModal');
    if (inModal || inHistoryModal || inSalesModal) return;
    if (!inTable && !inPayBtn) PAY_INTENT = null;
  });

  // ===== MODAL =====
  const modal = document.getElementById('orderModal');
  const modalClose = document.getElementById('modalClose');
  const activeTableBadge = document.getElementById('activeTableBadge');
  const activeTableSmall = document.getElementById('activeTableSmall');

  let ACTIVE_TABLE_NO = null;
  let DRAFT_CART = null;

  const openOrderForTable = (tableNo) => {
    ACTIVE_TABLE_NO = tableNo;
    activeTableBadge.textContent = `TABLE ${tableNo}`;
    activeTableSmall.textContent = String(tableNo);

    DRAFT_CART = cloneCartMap(getTableCart(tableNo));

    renderCart();
    modal.classList.add('show');
    modal.setAttribute('aria-hidden', 'false');
  };

  const closeOrder = (commit = true) => {
    // ✅✅ [추가] 모달을 닫을 때 현재 DRAFT_CART를 테이블에 자동 저장
    // - "注文取消" 버튼으로 DRAFT_CART를 비웠다면 그대로(빈 상태) 저장되어 주문이 사라짐
    // - 결제 후에는 이미 테이블 카트를 비우고 닫으므로 문제 없음
    if (commit && DRAFT_CART && ACTIVE_TABLE_NO) {
      commitDraftToTable(ACTIVE_TABLE_NO, DRAFT_CART);
      renderTables(); // ✅ 테이블 버튼에 주문 표시 즉시 반영
    }

    modal.classList.remove('show');
    modal.setAttribute('aria-hidden', 'true');
    DRAFT_CART = null;
    ACTIVE_TABLE_NO = null;
  };

  modalClose.addEventListener('click', closeOrder);
  modal.addEventListener('click', (e) => { if (e.target === modal) closeOrder(); });

  // ===== MENUS + CART =====
  const catTabs = document.getElementById('catTabs');
  const menuGrid = document.getElementById('menuGrid');

  const cartList = document.getElementById('cartList');
  const cartTotalEl = document.getElementById('cartTotal');
  
  const escapeHtml = (s) => String(s ?? '')
      .replaceAll('&', '&amp;')
      .replaceAll('<', '&lt;')
      .replaceAll('>', '&gt;')
      .replaceAll('"', '&quot;')
      .replaceAll("'", '&#39;');
  // ✅ 하이볼 선택 모달 (JS만으로 간단 구현)
  const openHighballPicker = (highballMenus) => {
    // 기존 열려있던 모달 제거
    document.getElementById('highballPicker')?.remove();

    const wrap = document.createElement('div');
    wrap.id = 'highballPicker';
    wrap.style.position = 'fixed';
    wrap.style.inset = '0';
    wrap.style.background = 'rgba(0,0,0,0.45)';
    wrap.style.zIndex = '9999';
    wrap.style.display = 'flex';
    wrap.style.alignItems = 'center';
    wrap.style.justifyContent = 'center';
    wrap.style.padding = '16px';

    const box = document.createElement('div');
    box.style.width = 'min(520px, 96vw)';
    box.style.maxHeight = '80vh';
    box.style.overflow = 'auto';
    box.style.background = '#fff';
    box.style.borderRadius = '14px';
    box.style.boxShadow = '0 10px 30px rgba(0,0,0,0.25)';
    box.style.padding = '14px';

    box.innerHTML = `
      <div style="display:flex;justify-content:space-between;align-items:center;gap:12px;">
        <div style="font-weight:900;font-size:16px;">ハイボール</div>
        <button type="button" id="hbClose"
          style="border:none;background:#f2f2f2;border-radius:10px;padding:6px 10px;cursor:pointer;">
          閉じる
        </button>
      </div>
      <div style="margin-top:12px;display:grid;grid-template-columns:1fr;gap:8px;" id="hbList"></div>
    `;

    const list = box.querySelector('#hbList');

    for (const m of highballMenus) {
      const btn = document.createElement('button');
      btn.type = 'button';
      btn.style.border = '1px solid rgba(0,0,0,0.12)';
      btn.style.borderRadius = '12px';
      btn.style.background = '#fff';
      btn.style.padding = '12px';
      btn.style.cursor = 'pointer';
      btn.style.display = 'flex';
      btn.style.justifyContent = 'space-between';
      btn.style.alignItems = 'center';
      btn.innerHTML = `
        <div style="font-weight:800;">${escapeHtml(m.name)}</div>
        <div style="font-weight:900;opacity:.9;">${yen(m.price)}</div>
      `;

      btn.addEventListener('click', () => {
        // ✅ 선택하면 장바구니에 담고 닫기
        addToCart(m);
        wrap.remove();
      });

      list.appendChild(btn);
    }

    wrap.appendChild(box);
    document.body.appendChild(wrap);

    // 바깥 클릭 닫기
    wrap.addEventListener('click', (e) => {
      if (e.target === wrap) wrap.remove();
    });
    box.querySelector('#hbClose').addEventListener('click', () => wrap.remove());
  };
  const ALL_MENUS = [];
  let ACTIVE_CAT = null;

  

	// ✅ 하이볼 종류는 따로 분리
	const HIGHBALL_VARIANTS = [
	  { code: 'HB_KAKUBIN', name: '角瓶ハイボール', price: 500 },
	  { code: 'HB_YUZU', name: '柚子ハイボール', price: 550 },
	  { code: 'HB_UME', name: '梅ハイボール', price: 550 },
	  { code: 'HB_EARL_GREY', name: 'アールグレイハイボール', price: 600 },
	  { code: 'HB_YANTAI', name: '煙台ハイボール', price: 650 },
	  { code: 'HB_MIKAN', name: 'みかんハイボール', price: 650 },
	  { code: 'HB_JACK', name: 'ジャックダニエルハイボール', price: 650 },
	  { code: 'HB_HAKATA', name: '博多ハイボール', price: 700 },
	  { code: 'HB_MEGA', name: 'メガハイボール', price: 950 },
	];
	
	// ===============================
	// ✅ サワー (Sour)
	// ===============================
	const SOUR_VARIANTS = [
	  { code: 'SOUR_LEMON_HOUSE', name: '自家製レモンサワー', price: 600 },
	  { code: 'SOUR_GRAPEFRUIT', name: 'グレープフルーツサワー', price: 550 },
	  { code: 'SOUR_GREEN_APPLE', name: '青リンゴサワー', price: 550 },
	  { code: 'SOUR_UMEBOSHI', name: '梅干しサワー', price: 600 },
	  { code: 'SOUR_CALPIS', name: 'カルピスサワー', price: 550 },
	];

	// ===============================
	// ✅ ビール (Beer)
	// ===============================
	const BEER_VARIANTS = [
	  { code: 'BEER_DRAFT_KIRIN', name: '生ビール(キリン)', price: 600 },
	  { code: 'BEER_DRAFT_ASAHI', name: '生ビール(アサヒ)', price: 600 },
	  { code: 'BEER_MEGA_KIRIN', name: 'メガ生ビール(キリン)', price: 950 },
	  { code: 'BEER_BOTTLE_SAPPORO', name: '瓶ビール(サッポロ)', price: 700 },
	  { code: 'BEER_GUINNESS', name: 'ギネス', price: 750 },
	  { code: 'BEER_BUDWEISER', name: 'バドワイザー', price: 650 },
	];

	// ===============================
	// ✅ 日本酒 (Sake)
	// ===============================
	const SAKE_VARIANTS = [
	  { code: 'SAKE_AMAGIRI', name: '天霧 (結霧)', price: 650 },
	  { code: 'SAKE_DEWAZAKURA', name: '出羽桜 (桜山)', price: 650 },
	  { code: 'SAKE_HOOH', name: '鳳凰 (美田)', price: 800 },
	  { code: 'SAKE_HIROKI', name: '廣喜 (大代)', price: 900 },
	  { code: 'SAKE_YONEYAMA', name: '米鶴 (超辛)', price: 950 },
	  { code: 'SAKE_HITAKAMI', name: '日高見 (真鯛)', price: 950 },
	];

	// ✅ HARDCODED_MENUS에는 하이볼 말고 나머지만(참이슬)
	const HARDCODED_MENUS = [
	  { code: 'SOJU_CHAMISUL', category: 'DRINK', name: 'チャミスル', price: 500 },
	 
	];

  const CART_SCROLL_THRESHOLD = 4;

  const renderCart = () => {
    if (!DRAFT_CART) return;

    cartList.innerHTML = '';
    const items = Array.from(DRAFT_CART.values());

    for (const it of items) {
      const div = document.createElement('div');
      div.className = 'cart-item';
      div.innerHTML = `
        <div class="cart-row">
          <div class="cart-title">${escapeHtml(it.name)}</div>
        </div>
        <div class="cart-row cart-row2">
          <div class="qty">
            <button class="qty-btn" data-act="dec" data-id="${it.menuId}">-</button>
            <div class="qty-val">${it.qty}</div>
            <button class="qty-btn" data-act="inc" data-id="${it.menuId}">+</button>
          </div>
          <div class="cart-line">${yen(it.price * it.qty)}</div>
        </div>
      `;
      cartList.appendChild(div);
    }

    cartList.classList.toggle('cart-scroll-on', items.length > CART_SCROLL_THRESHOLD);
    cartTotalEl.textContent = yen(calcTotal(DRAFT_CART));
  };

  const addToCart = (menu) => {
    if (!DRAFT_CART) return;

    const cur = DRAFT_CART.get(menu.id);
    if (!cur) 	DRAFT_CART.set(menu.id, {
	  menuId: menu.id,
	  menuCode: menu.code ?? null,   // ✅ 추가
	  name: menu.name,
	  price: Number(menu.price || 0),
	  qty: 1
	});
    else { cur.qty += 1; DRAFT_CART.set(menu.id, cur); }

    renderCart();
  };

  cartList.addEventListener('click', (e) => {
    const b = e.target.closest('button.qty-btn[data-act][data-id]');
    if (!b || !DRAFT_CART) return;

    const act = b.getAttribute('data-act');
    const id = Number(b.getAttribute('data-id'));
    const cur = DRAFT_CART.get(id);
    if (!cur) return;

    if (act === 'inc') cur.qty += 1;
    if (act === 'dec') cur.qty -= 1;

    if (cur.qty <= 0) DRAFT_CART.delete(id);
    else DRAFT_CART.set(id, cur);

    renderCart();
  });

  document.getElementById('payCancel').addEventListener('click', () => {
    if (!DRAFT_CART) return;
    DRAFT_CART.clear();
    renderCart();
  });

  document.getElementById('orderCommit').addEventListener('click', () => {
    if (!DRAFT_CART || !ACTIVE_TABLE_NO) return;
    commitDraftToTable(ACTIVE_TABLE_NO, DRAFT_CART);
    renderTables();
    closeOrder(false); // ✅ 이미 저장했으니 닫기만
  });

  const loadMenus = async () => {
    const res = await fetch(`/admin/api/menus/all`, { cache: 'no-store', credentials: 'same-origin' });
    if (!res.ok) throw new Error('menus api failed');
    return await res.json();
  };

  

  const catLabel = (c) => {
    switch (String(c || '')) {
      case 'SINGLE': return '単品';
      case 'MAIN': return '麺・ご飯';
      case 'FRIED_PAN': return '揚げ物・焼き';
      case 'SNACK': return 'おつまみ';
      case 'DRINK': return '飲み物';
      case 'ETC': return 'ETC';
      default: return String(c || 'ETC');
    }
  };

  const fallbackCategory = (m) => {
    const c = m && m.category ? String(m.category) : 'ETC';
    return c || 'ETC';
  };

  const catOrderKey = (c) => {
    const k = String(c || 'ETC');
    if (k === 'SINGLE') return 0;
    if (k === 'MAIN') return 1;
    if (k === 'FRIED_PAN') return 2;
    if (k === 'SNACK') return 3;
    if (k === 'DRINK') return 4;
    return 99;
  };

  const renderTabs = (menus) => {
    const set = new Set();
    for (const m of menus) set.add(String(m.category || 'ETC'));
    const cats = Array.from(set).sort((a, b) => catOrderKey(a) - catOrderKey(b));

    catTabs.innerHTML = '';

    for (const c of cats) {
      const t = document.createElement('div');
      t.className = 'tab' + (ACTIVE_CAT === c ? ' active' : '');
      t.setAttribute('data-cat', c);
      t.textContent = catLabel(c);
      catTabs.appendChild(t);
    }

    if (!ACTIVE_CAT || !cats.includes(ACTIVE_CAT)) {
      ACTIVE_CAT = cats[0] || 'ETC';
      const first = catTabs.querySelector('.tab[data-cat]');
      if (first) first.classList.add('active');
    }
  };

  const renderMenus = () => {
    menuGrid.innerHTML = '';
    const filtered = ALL_MENUS.filter(m => String(m.category || 'ETC') === String(ACTIVE_CAT || 'ETC'));

    for (const m of filtered) {
      const btn = document.createElement('button');
      btn.type = 'button';
      btn.className = 'menu-btn';
      btn.setAttribute('data-id', m.id);
      btn.innerHTML = `
        <div class="m-name">${escapeHtml(m.name)}</div>
        <div class="m-price">${yen(m.price)}</div>
      `;
      menuGrid.appendChild(btn);
    }
  };

  catTabs.addEventListener('click', (e) => {
    const t = e.target.closest('.tab[data-cat]');
    if (!t) return;
    ACTIVE_CAT = t.getAttribute('data-cat');
    for (const node of catTabs.querySelectorAll('.tab')) node.classList.remove('active');
    t.classList.add('active');
    renderMenus();
  });

  menuGrid.addEventListener('click', (e) => {
    const b = e.target.closest('button.menu-btn[data-id]');
    if (!b) return;
    const id = Number(b.getAttribute('data-id'));
    const menu = ALL_MENUS.find(x => Number(x.id) === id);
	if (!menu) return;

	// ✅ 하이볼 대표 버튼이면: 종류 선택 모달
	if (menu.__isGroup && Array.isArray(menu.__children)) {
	  openHighballPicker(menu.__children);
	  return;
	}

	// ✅ 일반 메뉴면 그대로 담기
	addToCart(menu);
  });

  // ===== PAY API =====
  const payApi = async (method, tableNo, cartMap) => {
	const items = Array.from(cartMap.values()).map(it => {
	  // ✅ 하드코딩 DRINK
	  if (it.menuId < 0) {
	    return {
	      menuCode: it.menuCode,  // 서버가 처리
	      qty: it.qty
	    };
	  }

	  // ✅ DB 메뉴
	  return {
	    menuId: it.menuId,
	    qty: it.qty
	  };
	});
    if (items.length === 0) throw new Error('empty cart');
    if (!tableNo) throw new Error('no table');

    const { token, header } = getCsrf();
    const headers = { 'Content-Type': 'application/json' };
    if (token && header) headers[header] = token;

    const res = await fetch(`/admin/store/${STORE_ID}/pos/pay`, {
      method: 'POST',
      credentials: 'same-origin',
      headers,
      body: JSON.stringify({ method, tableNo, items })
    });

    if (!res.ok) throw new Error('pay failed');
    return await res.json();
  };

  const flashTable = (tableNo) => {
    const el = document.querySelector(`.table-btn[data-table-no="${tableNo}"]`);
    if (!el) return;
    el.classList.remove('flash-paid');
    void el.offsetWidth;
    el.classList.add('flash-paid');
    setTimeout(() => el.classList.remove('flash-paid'), 800);
  };

  // ===== TABLE RENDER =====
  const MAX_VISIBLE_ITEMS = 5;

  const renderTables = () => {
    tableGrid.innerHTML = '';

    for (const t of TABLES) {
      const cart = getTableCart(t.no);
      const total = calcTotal(cart);

      const allItems = Array.from(cart.values());
      const list = allItems.slice(0, MAX_VISIBLE_ITEMS);
      const remain = Math.max(0, allItems.length - list.length);

      const btn = document.createElement('button');
      btn.type = 'button';
      btn.className = 'table-btn';
      btn.setAttribute('data-table-no', String(t.no));

      let itemsHtml = '';
      for (const it of list) {
        itemsHtml += `
          <div class="t-row">
            <div class="name">${escapeHtml(it.name)}</div>
            <div class="qty">×${it.qty}</div>
          </div>
        `;
      }
      if (remain > 0) {
        itemsHtml += `
          <div class="t-row">
            <div class="name">+${remain}</div>
            <div class="qty"></div>
          </div>
        `;
      }

      btn.innerHTML = `
        <div class="t-no">TABLE ${t.no}</div>
        <div class="t-items">${itemsHtml}</div>
        <div class="t-total">
          <div class="label">合計</div>
          <div class="amt">${yen(total)}</div>
        </div>
      `;

      btn.addEventListener('click', async (e) => {
        e.stopPropagation();

        if (PAY_INTENT) {
          try {
            await payApi(PAY_INTENT, t.no, cart);
            cart.clear();
			saveTableCarts(); // ✅✅ [추가]
            PAY_INTENT = null;
            renderTables();
            flashTable(t.no);
            await tickTopbar();
          } catch (err) {
            alert('決済に失敗しました');
          }
          return;
        }

        openOrderForTable(t.no);
      });

      tableGrid.appendChild(btn);
    }
  };

  document.getElementById('tablesRefresh')?.addEventListener('click', renderTables);

  document.getElementById('payCash').addEventListener('click', async () => {
    if (!DRAFT_CART || !ACTIVE_TABLE_NO) return;
    try {
      await payApi('CASH', ACTIVE_TABLE_NO, DRAFT_CART);
      TABLE_CARTS.set(ACTIVE_TABLE_NO, new Map());
	  saveTableCarts(); // ✅✅ [추가]
      DRAFT_CART.clear();
      closeOrder();

      renderTables();
      flashTable(ACTIVE_TABLE_NO);
      await tickTopbar();
    } catch (e) {
      alert('決済に失敗しました');
    }
  });

  document.getElementById('payCard').addEventListener('click', async () => {
    if (!DRAFT_CART || !ACTIVE_TABLE_NO) return;
    try {
      await payApi('CARD', ACTIVE_TABLE_NO, DRAFT_CART);
      TABLE_CARTS.set(ACTIVE_TABLE_NO, new Map());
      DRAFT_CART.clear();
      closeOrder();
      renderTables();
      flashTable(ACTIVE_TABLE_NO);
      await tickTopbar();
    } catch (e) {
      alert('決済に失敗しました');
    }
  });

  // ===== INIT =====
  const init = async () => {
	loadTableCarts(); // ✅✅✅ 반드시 renderTables()보다 먼저
    if (resvHistoryDate) resvHistoryDate.value = toYmdLocal(new Date());
    if (salesHistoryDate) salesHistoryDate.value = toYmdLocal(new Date());

    await refreshReservations();
    setInterval(async () => { await refreshReservations(); }, 5000);

    setInterval(checkReservationAlarm, 30_000);

    try {
		const arr = await loadMenus();
		ALL_MENUS.length = 0;

		// ✅ DB 메뉴 중 DRINK는 제외
		for (const m of arr) {
		  if (String(m.category).toUpperCase() === 'DRINK') continue;

		  ALL_MENUS.push({
		    id: m.id,
		    category: fallbackCategory(m),
		    name: m.name,
		    price: Number(m.price || 0)
		  });
		}

		// ✅ DRINK는 JS 하드코딩 메뉴만 사용
		// ✅ DRINK는 JS 하드코딩 메뉴만 사용
		let HARD_ID_SEQ = -1; // 음수 ID로 DB 메뉴와 충돌 방지

		// 1) 하이볼 실제 메뉴(장바구니에 담길 애들)
		const buildGroupMenu = (label, variants) => {
			const children = variants.map(v => ({
			  id: HARD_ID_SEQ--,      // UI용
			  category: 'DRINK',
			  name: v.name,
			  price: Number(v.price || 0),
			  code: v.code            // ✅ 서버용
			}));

		  return {
		    id: HARD_ID_SEQ--,
		    category: 'DRINK',
		    name: label,
		    price: 0,
		    __isGroup: true,
		    __children: children
		  };
		};

		// ===============================
		// ✅ 그룹 메뉴 추가
		// ===============================
		ALL_MENUS.push(buildGroupMenu('ハイボール', HIGHBALL_VARIANTS));
		ALL_MENUS.push(buildGroupMenu('サワー', SOUR_VARIANTS));
		ALL_MENUS.push(buildGroupMenu('ビール', BEER_VARIANTS));
		ALL_MENUS.push(buildGroupMenu('日本酒', SAKE_VARIANTS));

		// 3) 나머지 DRINK (생맥/레몬사와 등)
		// ✅ [수정] HARDCODED_MENUS → ALL_MENUS로 옮길 때 code도 같이 넣기
		for (const m of HARDCODED_MENUS) {
		  ALL_MENUS.push({
		    id: HARD_ID_SEQ--,
		    category: 'DRINK',
		    name: m.name,
		    price: Number(m.price || 0),
		    code: m.code ?? null   // ✅ 추가 (참이슬 결제 핵심)
		  });
		}
		renderTabs(ALL_MENUS);
		renderMenus();
    } catch (e) { }

    renderTables();
    await tickTopbar();
    setInterval(tickTopbar, 5000);
  };

  init();
})();