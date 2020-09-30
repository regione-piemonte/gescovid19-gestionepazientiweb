import axios from 'axios'
import Constants from './constants'

export const creaSegnalazione = (payload, httpConfig = {}) => {
  const url = Constants.VISURAMMG_BASE_URL + '/mmgvisura/soggetti'
  return axios.post(url, payload, httpConfig)
}

export const creaDiarioPagina = (payload, httpConfig = {}) => {
  const url = Constants.VISURAMMG_EXTERNAL_URL + '/diario-dettagli'
  return axios.post(url, payload, httpConfig)
}

export const getNuoviSintomi = (httpConfig = {}) => {
  const url = Constants.VISURAMMG_EXTERNAL_URL + '/sintomi'
  return axios.get(url, httpConfig)
}

export const aggiornaContattiUtente = (cf, payload, httpConfig = {}) => {
  const url = Constants.VISURAMMG_EXTERNAL_URL + '/utenti/' + cf + '/contatti'
  return axios.put(url, payload, httpConfig)
}

export const getCurrentUserNew = (httpConfig = {}) => {
  const url = Constants.VISURAMMG_EXTERNAL_URL + '/currentUser'
  return axios.get(url, httpConfig)
}
