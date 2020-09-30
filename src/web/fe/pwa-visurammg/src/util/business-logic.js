import Constants from './constants'

const _aggiungiSintomoSeSelezionato = (result, vecchio, nuovo, misurabile = false) => {
  let daAggiungere = vecchio && nuovo
  if (!misurabile) daAggiungere = daAggiungere && vecchio !== 'NO'

  if (daAggiungere) {
    const item = { ...nuovo }
    item.valore = misurabile ? Number(vecchio) : null
    item.note = ''
    result.push(item)
  }
}

export const sintomiVecchi2Nuovi = (vecchiSintomi, nuoviSintomi) => {
  const CODICI = Constants.SINTOMO_NUOVO_CODICI
  const result = []

  const mappa = nuoviSintomi.reduce((out, el) => {
    const key = el.codice
    out[key] = { ...el }
    return out
  }, {})

  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.temperatura, mappa[CODICI.TEMPREATURA], true)
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgTosse, mappa[CODICI.TOSSE])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgDispnea, mappa[CODICI.DISPNEA])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgDiarrea, mappa[CODICI.DIARREA])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgCongiuntivite, mappa[CODICI.CONGIUNTIVITE])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgDoloriMusc, mappa[CODICI.DOLORI_MUSCOLARI])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgGusto, mappa[CODICI.GUSTO])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgOlfatto, mappa[CODICI.OLFATTO])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgRaffreddore, mappa[CODICI.RAFFREDDORE])
  _aggiungiSintomoSeSelezionato(result, vecchiSintomi.flgStanchezza, mappa[CODICI.STANCHEZZA])

  return result
}

export const getAutoreCambioStato = (scheda, stato) => {
  const cronologia = (scheda && scheda.cronologia) || []
  const item = cronologia.find(el => el.stato.codice === stato)
  if (!item) return

  return item.utente
}

export const validaCodiceFiscale = (cf) => {
  var validi, i, s, set1, set2, setpari, setdisp
  if (cf === '') return false

  cf = cf.toUpperCase()
  if (cf.length !== 16) { return false }

  validi = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  for (i = 0; i < 16; i++) {
    if (validi.indexOf(cf.charAt(i)) === -1) { return false }
  }

  set1 = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ'
  set2 = 'ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ'
  setpari = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
  setdisp = 'BAKPLCQDREVOSFTGUHMINJWZYX'
  s = 0

  for (i = 1; i <= 13; i += 2) { s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i)))) }
  for (i = 0; i <= 14; i += 2) { s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i)))) }

  if (s % 26 !== cf.charCodeAt(15) - 'A'.charCodeAt(0)) { return false }

  return true
}
