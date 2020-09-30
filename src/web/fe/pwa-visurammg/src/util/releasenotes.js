const Releasenotes = {}

Releasenotes['1.1'] = {
  version: '1.1',
  releasedate: '06/04/2020',
  improvements: [{
    index: 0, title: 'Segnalazione nuovo paziente', text: 'E\' stata introdotta la funzionalità di Inserimento paziente con la possibilità di effettuare Segnalazioni al ASL/SISP', notes: ''
  }]
}

Releasenotes['1.2'] = {
  version: '1.2',
  releasedate: '11/04/2020',
  improvements: [{
    index: 0,
    title: 'Nuovo stato "Stato Incongruo o da completare"',
    text: 'Inserito lo "Stato Incongruo o da completare" indicato dalla ASL/SISP in fase di presa in carico della Segnalazione. Per procedere all\'integrazione è stato inserito il bottone Integra per procedere al completamento o integrazione della Segnalazione',
    notes: ''
  }]
}

Releasenotes['1.3'] = {
  version: '1.3',
  releasedate: '16/04/2020',
  improvements: [{
    index: 0,
    title: 'Possibilità di specificare il tipo paziente',
    text: 'In fase di inserimento/modifica paziente è stata introdotta la possibilità di specificare se si tratta di un operatore sanitario, o un ospite struttura',
    notes: ''
  }]
}

Releasenotes['1.4'] = {
  version: '1.4',
  releasedate: '24/04/2020',
  improvements: [{
    index: 0,
    title: 'Introdotta la possibilità di interagire con le USCA',
    text: '<ul><li><strong>INSERIMENTO</strong> della <strong>RICHIESTA di intervento</strong></li>' +
    '<li><strong>VERIFICA</strong> dello <strong>STATO di avanzamento</strong>: Inviata, Presa in carico, Evasa</li></ul>',
    notes: ''
  },
  {
    index: 0,
    title: 'Nuove voci nella visualizzazione dei Dettagli del Paziente ',
    text: '<ul><li><strong>INTERVENTI USCA</strong>  - Segnalazioni inoltrate alle USCA e il relativo dettaglio e Stato delle Segnalazioni</li>' +
    '<li><strong>DIARIO</strong> che riporta i dettagli del Monitoraggio del Paziente e consente di aggiungere eventuali Sintomi</li></ul>',
    notes: ''
  }]
}

Releasenotes['1.5'] = {
  version: '1.5',
  releasedate: '25/04/2020',
  improvements: [{
    index: 0,
    title: 'Introdotta la possibilità di interagire con le USCA',
    text: '<ul>' +
      '<li>Modificata l’interazione con i SISP:</li>' +
      '<li>Introdotta possibilità di creare un paziente senza inviare la segnalazione al <strong>SISP</strong></li>' +
      '<li>Nella parte in alto sono stati aggiunti:' +
      '  <ul>' +
      '    <li>I filtri sugli stati delle attività effettuate dagli USCA</li>' +
      '    <li>Il campo ricerca paziente</li>' +
      '    <li>Nel corpo centrale dell’interfaccia e in particolare nella colonna “<strong>SISP</strong>” è stato aggiunto il pulsante “<strong>Segnala</strong>” per i pazienti creati senza segnalazione al SISP</li>' +
      '  </ul>' +
      '</li>' +
      '</ul>',
    notes: ''
  }]
}

Releasenotes['1.6'] = {
  version: '1.6',
  releasedate: '30/04/2020',
  improvements: [{
    index: 0,
    title: 'Più interventi USCA sulla singola richiesta',
    text: '<ul>' +
      '<li>Introdotta la possibilità di inserire più interventi da parte del medico USCA per ogni singola richiesta pervenuta dagli MMG e di conseguenza è stata modificata la sezione di dettaglio della richiesta esponendo non più le informazioni del solo intervento effettuato ma l’elenco degli interventi con il dettaglio delle informazioni inserite e del medico USCA che ha operato.</li>' +
      '</ul>',
    notes: ''
  }]
}

export default {
  Releasenotes: Releasenotes
}
