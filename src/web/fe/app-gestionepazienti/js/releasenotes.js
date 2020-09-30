var Releasenotes = Releasenotes || {};

Releasenotes["1.1"] = {
  "version": "1.1",
  "releasedate": "06/04/2020",
  "improvements": [
	  "<strong>Evento POST RICOVERO disabilitato</strong> Utilizzare direttamente gli eventi QUARANTENA corretti<br>" +
	  "<i>Si ricorda di aggiornare lo stato dei soggetto attualmente in POST RICOVERO. Possono essere ritrovati utilizzando il filtro nella pagina principale</i>",
	  "<strong>Scelta strutture per quarantena</strong> E' ora possibile indicare qualsiasi struttura intermedia post acuzie come destinazione per la quarantena",
	  "<strong>Manuale Utente</strong> E' ora possibile consultare il manuale utente direttamente cliccando sul bottone <span class='releasenotes-improvements-link'><i class='fa fa-question-circle-o'></i></span>  in testa alla pagina "
  ],
  "fixes": new Array()
}
Releasenotes["1.2"] = {
  "version": "1.2",
  "releasedate": "09/04/2020",
  "improvements": [
	  "<strong>Nuovo filtro per segnalazioni da MMG e PLS</strong>"+
	  " E' stato introdotto un nuovo Filtro che consente ai SISP di visualizzare e di prendere in carico le segnalazioni effettuate da MMG e PLS"
  ],
  "fixes": new Array()
}
Releasenotes["1.3"] = {
  "version": "1.3",
  "releasedate": "11/04/2020",
  "improvements": [
	  "<strong>Possibilità di richiedere informazioni sulle segnalazioni</strong>"+
	  "In fase di presa in carico della Segnalazione da parte di MMG o PLS  è stata introdotta, con inserimento di apposito pulsante, la possibilità di richiedere a MMG o PLS di integrare la Segnalazione  "
  ],
  "fixes": new Array()
}

Releasenotes["1.4"] = {
  "version": "1.4",
  "releasedate": "16/04/2020",
  "improvements": [
	  "<strong>Possibilità di specificare il tipo paziente</strong>"+
	  "In fase di inserimento/modifica paziente è stata introdotta la possibilità di specificare se si tratta di un operatore sanitario, o un ospite struttura"
  ],
  "fixes": new Array()
}

Releasenotes["1.5"] = {
  "version": "1.5",
  "releasedate": "23/04/2020",
  "improvements": [
	  "Funzione di ricerca pazienti per chiavi (Cognome, Nome, ecc.) che consente l'accesso alle informazioni dei soggetti registrati in piattaforma " +
	  "indipendentemente dall'ASR che lo ha in carico e dell'ASL di domicilio o residenza. la ricerca è attivabile selezionando il tab <b>\"Cerca\"</b> ",
	  "Su singolo soggetto individuato, accesso a tutti gli eventi del suo percorso di cura effettuati" +
	  " (ad es. il tampone, ricovero, post ricovero, quarantena, ecc.) e registrazione di nuovi eventi come ad es. la richiesta del tampone"	  
  ],
  "fixes": new Array()
}

Releasenotes["1.6"] = {
  "version": "1.6",
  "releasedate": "25/04/2020",
  "improvements": [
	  "<strong>Bottone Aggiungi Diario</strong>per la creazione del diario o l'inserimento di un nuovo record se già presente",
	  "<strong>Interventi USCA</strong>mostra l'elenco degli interventi richiesti dagli MMG ed eseguiti dalle USCA",
	  "<strong>Diario</strong>mostra i record di rilevazione della sintomatologia effettuati sul singolo paziente e consente di inserirne uno nuovo"
  ],
  "fixes": new Array()
}



Releasenotes["1.7"] = {
  "version": "1.7",
  "releasedate": "01/05/2020",
  "improvements": [
	  "<strong>Visualizzazione identificativo decorso</strong>inserito nella visualizzazione del decorso l'identificativo del decorso necessario " +
	  "per richiedere interventi sulla modifica dei dati dell'assistenza"
	],
  "fixes": new Array()
}

Releasenotes["1.8"] = {
  "version": "1.8",
  "releasedate": "05/05/2020",
  "improvements": [
	  "<strong>Cancellazione Decorso e cancellazione Tampone</strong> "+
    "sono state inserite due nuove funzionalità grazie alle quali non sarà più necessario richiedere interventi di assistenza in caso di errori di imputazione sui decorsi e tamponi. "
	],
  "fixes": new Array()
}

Releasenotes["1.9"] = {
  "version": "1.9",
  "releasedate": "17/05/2020",
  "improvements": [
	  "<strong>Notifiche Isolamento fiduciario</strong> "+
    "aggiunta gestione notifiche da medici in seguito a disposizioni di isolamento fiduciario",

    "<strong>Comunicazioni MMG</strong> "+
    "aggiunta gestione comunicazioni di aggravamento/miglioramento da parte dei MMG/PLS",

    "<strong>Contatti a rischio</strong> "+
    "aggiunta gestione contatti a rischio",

    "<strong>Scarico Excel pazienti</strong> "+
    "modificata la funzione di scarico Excel per conteplare i soli pazienti visualizzati in base ai filtri di ricerca impostati",

    "<strong>Filtri ricerca pazienti</strong> "+
    "aggiunta funzione di supporto: filtri e ricerche",

	],
  "fixes": new Array()
  
}

Releasenotes["1.10"] = {
  "version": "1.10",
  "releasedate": "22/05/2020",
  "improvements": [
	  "<strong>Gestione quarantena</strong> "+
    "aggiunti due nuovi eventi di quarantena: <ul><li>disposta quarantena a domicilio</li><li>disposta quarantena in struttura extra-domiciliare</li></ul>"+
    "in aggiunta a quelli già dispoinbili: <ul><li>dimesso in quarantena a domicilio</li><li>dimesso in struttura extra-domiciliare</li></ul>"+
    "da utilizzare nei casi in cui l'attivazione della quarantena non coincide con una dimissione. "
	],
  "fixes": new Array()
}

