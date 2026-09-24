const STORAGE = {
  THEME: 'wc_theme',
  LANG: 'wc_lang',
  NOTIFY: 'wc_notify'
};

const matchesData = [
  {
    "name": "Round 1",
    "stadium": { "name": "Dragon Stadium", "image": "assets/dragon-stadium.png" },
    "team1": "🇧🇷",
    "team2": "🇷🇸",
    "date": "2030-06-14T17:00:00Z"
  },
  {
    "name": "Round 2",
    "stadium": { "name": "Luz Stadium", "image": "assets/luz-stadium.png" },
    "team1": "🇧🇷",
    "team2": "🇨🇭",
    "date": "2030-06-20T20:00:00Z"
  },
  {
    "name": "Round 3",
    "stadium": { "name": "Dragon Stadium", "image": "assets/dragon-stadium.png" },
    "team1": "🇨🇲",
    "team2": "🇧🇷",
    "date": "2030-06-25T17:00:00Z"
  },
  {
    "name": "Round of 32",
    "stadium": { "name": "Luz Stadium", "image": "assets/luz-stadium.png" },
    "team1": "🇦🇷",
    "team2": "🇧🇷",
    "date": "2030-07-04T16:00:00Z"
  },
  {
    "name": "Round of 16",
    "stadium": { "name": "Dragon Stadium", "image": "assets/dragon-stadium.png" },
    "team1": "🇧🇷",
    "team2": "🇮🇹",
    "date": "2030-07-08T16:00:00Z"
  },
  {
    "name": "Round of 8",
    "stadium": { "name": "Luz Stadium", "image": "assets/luz-stadium.png" },
    "team1": "🇫🇷",
    "team2": "🇧🇷",
    "date": "2030-07-12T16:00:00Z"
  },
  {
    "name": "Round of 4",
    "stadium": { "name": "Dragon Stadium", "image": "assets/dragon-stadium.png" },
    "team1": "🇧🇷",
    "team2": "🇩🇪",
    "date": "2030-07-16T16:00:00Z"
  },
  {
    "name": "FINAL",
    "stadium": { "name": "Luz Stadium", "image": "assets/luz-stadium.png" },
    "team1": "🇧🇷",
    "team2": "🇪🇸",
    "date": "2030-07-21T16:00:00Z"
  }
];

/* Translations */
const i18n = {
  "en-US": {
    headerSub: "Track matches and enable reminders",
    matchesTitle: "Matches",
    legendText: "Tap Notify to toggle a local reminder.",
    notify: "Notify",
    disable: "Disable",
    builtWith: "Developed by Nivaldo Beirão.",
    stadiumLabel: "Stadium"
  },
  "pt-BR": {
    headerSub: "Acompanhe partidas e ative lembretes",
    matchesTitle: "Partidas",
    legendText: "Toque em Notificar para alternar um lembrete local.",
    notify: "Notificar",
    disable: "Desativar",
    builtWith: "Desenvolvido por Nivaldo Beirão.",
    stadiumLabel: "Estádio"
  },
  "es": {
    headerSub: "Sigue los partidos y activa recordatorios",
    matchesTitle: "Partidos",
    legendText: "Pulse Notificar para alternar un recordatorio local.",
    notify: "Notificar",
    disable: "Desactivar",
    builtWith: "Desarrollado por Nivaldo Beirão.",
    stadiumLabel: "Estadio"
  }
};

/* Utilities */
const $ = sel => document.querySelector(sel);
const $$ = sel => Array.from(document.querySelectorAll(sel));

function save(key, value){ localStorage.setItem(key, JSON.stringify(value)); }
function load(key, fallback){ try { const v = JSON.parse(localStorage.getItem(key)); return v ?? fallback; } catch(e){ return fallback; } }

/* Theme */
function applyTheme(theme){
  if(theme === 'light'){
    document.documentElement.classList.add('light');
    $('#themeToggle').setAttribute('aria-pressed','false');
  } else {
    document.documentElement.classList.remove('light');
    $('#themeToggle').setAttribute('aria-pressed','true');
  }
  save(STORAGE.THEME, theme);
}

/* Language */
function applyLang(lang){
  document.documentElement.lang = lang;
  const t = i18n[lang] || i18n['en-US'];
  $('#header-sub').textContent = t.headerSub;
  $('#matches-title').textContent = t.matchesTitle;
  $('#legend-text').textContent = t.legendText;
  $('#footer-text').textContent = t.builtWith;
  // update buttons text
  $$('.notify-btn').forEach(btn => {
    const pressed = btn.getAttribute('aria-pressed') === 'true';
    btn.textContent = pressed ? t.disable : t.notify;
  });
  save(STORAGE.LANG, lang);
}

/* Render matches */
function renderMatches(locale){
  const list = $('#matchesList');
  list.innerHTML = '';
  const template = document.getElementById('match-template');

  matchesData.forEach((m, idx) => {
    const clone = template.content.cloneNode(true);
    const li = clone.querySelector('li');
    const card = clone.querySelector('.card');
    const media = clone.querySelector('.card-media');
    const name = clone.querySelector('.match-name');
    const timeEl = clone.querySelector('.match-date');
    const teamLeft = clone.querySelector('.team .team-code');
    const teamRight = clone.querySelector('.team-right .team-code-right');
    const notifyBtn = clone.querySelector('.notify-btn');
    const stadiumSpan = clone.querySelector('.stadium');

    // set background image (user should place images in assets/)
    media.style.backgroundImage = `url("${m.stadium.image}")`;
    media.style.backgroundColor = '#111';

    name.textContent = m.name;
    const date = new Date(m.date);
    timeEl.dateTime = date.toISOString();
    timeEl.textContent = new Intl.DateTimeFormat(locale, {
      day: '2-digit', month: '2-digit', hour: '2-digit', minute: '2-digit'
    }).format(date);

    teamLeft.textContent = m.team1;
    teamRight.textContent = m.team2;
    stadiumSpan.textContent = m.stadium.name;

    // notification state
    const notifyState = load(STORAGE.NOTIFY, {});
    const pressed = !!notifyState[m.name];
    notifyBtn.setAttribute('aria-pressed', pressed ? 'true' : 'false');

    // set button label according to language
    const t = i18n[load(STORAGE.LANG, 'en-US')];
    notifyBtn.textContent = pressed ? t.disable : t.notify;

    // accessible label
    notifyBtn.setAttribute('aria-label', `${t.notify} ${m.name}`);

    notifyBtn.addEventListener('click', () => {
      const current = notifyBtn.getAttribute('aria-pressed') === 'true';
      const newState = !current;
      notifyBtn.setAttribute('aria-pressed', newState ? 'true' : 'false');
      // update label
      notifyBtn.textContent = newState ? t.disable : t.notify;
      // persist
      const state = load(STORAGE.NOTIFY, {});
      if(newState) state[m.name] = true; else delete state[m.name];
      save(STORAGE.NOTIFY, state);
      // announce change for screen readers
      announce(`${m.name} ${newState ? 'notifications enabled' : 'notifications disabled'}`, 'polite');
    });

    // keyboard: Enter toggles notify
    card.addEventListener('keydown', (ev) => {
      if(ev.key === 'Enter' || ev.key === ' '){
        ev.preventDefault();
        notifyBtn.click();
      }
    });

    list.appendChild(clone);
  });
}

/* Live region announce */
function announce(message, mode='polite'){
  let region = document.getElementById('live-region');
  if(!region){
    region = document.createElement('div');
    region.id = 'live-region';
    region.setAttribute('aria-live', mode);
    region.className = 'visually-hidden';
    document.body.appendChild(region);
  }
  region.textContent = message;
}

/* Init */
document.addEventListener('DOMContentLoaded', () => {
  // restore theme
  const savedTheme = load(STORAGE.THEME, null);
  const prefersDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
  applyTheme(savedTheme || (prefersDark ? 'dark' : 'light'));

  // restore language
  const savedLang = load(STORAGE.LANG, 'en-US');
  $('#lang').value = savedLang;
  applyLang(savedLang);

  // render matches
  renderMatches(savedLang);

  // theme toggle
  $('#themeToggle').addEventListener('click', () => {
    const isDark = document.documentElement.classList.contains('light') ? false : true;
    applyTheme(isDark ? 'light' : 'dark');
  });

  // language change
  $('#lang').addEventListener('change', (e) => {
    const lang = e.target.value;
    applyLang(lang);
    renderMatches(lang);
  });

  // keyboard accessibility: allow theme toggle with Enter/Space
  $('#themeToggle').addEventListener('keydown', (ev) => {
    if(ev.key === 'Enter' || ev.key === ' '){
      ev.preventDefault();
      $('#themeToggle').click();
    }
  });
});
