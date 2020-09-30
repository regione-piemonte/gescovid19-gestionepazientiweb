const Releasenotes = {}

Releasenotes['1.1'] = {
  version: '1.1',
  releasedate: '17/04/2020',
  improvements: [{
    index: 0,
    title: 'Statische pazienti',
    text: 'Per i pazienti con tampone positivo vengono visualizzati i conteggi dei residenti/domiciliati/in quarantena'
  }]
}

Releasenotes['1.2'] = {
  version: '1.2',
  releasedate: '17/05/2020',
  improvements: [{
    index: 0,
    title: 'Pazienti visualizzati',
    text: 'Vengono visualizzati tutti i pazienti che hanno avuto una richiesta di tampone, indipendentemente dall\'esito'
  },
  {
    index: 1,
    title: 'Attualmente Positivi',
    text: 'Conteggio dei soggetti risultati  positivi al test Covid19 che non risultano, in data odierna, deceduti, guariti o in guarigione'
  },
  {
    index: 2,
    title: 'Nuovi Filtri',
    text: 'Possibilità di filtrare la lista pazienti visualizzando solo i residenti o i domiciliati o gli attualmente positivi o quelli in isolamento fiduciario'
  }]

}

export default {
  Releasenotes: Releasenotes
}
