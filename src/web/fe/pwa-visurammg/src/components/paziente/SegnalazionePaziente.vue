<template>
  <modal name="segnalazione-paziente" :adaptive='true' height="auto" :scrollable="true"  width='650px'   :styles="'display: flex; height: auto;padding: 12px;background-color: transparent;box-shadow: none' " @before-open="beforeOpen">
    <div class='modal-content'>
        <div class='modal-title' @click="debug()">
          <template v-if="segnalazione">Nuovo paziente con segnalazione</template>
          <template v-else>Nuovo paziente senza segnalazione</template>
        </div>

        <div class='paziente' v-if='paziente && !finish'>
            <form class='form' >
              <template v-if="segnalazione">
                <div class='form-group-intro'>
                  <div class='info-important'  v-if='!paziente.idSoggetto'>
                    <font-awesome-icon :icon="iconInfo" class='info-important-icon'/>
                    <div class='info-important-text'>Si rammenta che le segnalazioni ai SISP vanno effettuate solo nel caso di prima segnalazione</div>
                  </div>
                </div>
              </template>

              <div class='form-section'>
                  <div class='form-group  form-group-end-section'>
                    <label class='label-column  control-label'  for='tipoSoggetto'>Tipo paziente</label>
                    <div class='control-column'>
                      <select v-model="paziente.idTipoSoggetto" class='form-control ' :disabled='paziente.idSoggetto' id='tipoSoggetto'>
                         <option v-for="t in allTipisoggetto" :value="t.idTipoSoggetto" :key="t.idTipoSoggetto">{{ t.descTipoSoggetto}}</option>
                      </select>
                    </div>
                    <div class='label-column'>&nbsp;</div>
                  </div>
              </div>
              <div v-if='!ricercaaura.eseguita && !cfAuraFeedback' class='form-group-intro'>
                <div class='alert info'>Effettuare la ricerca su Aura per proseguire con l 'inserimento del paziente</div>
              </div>
              <div v-if='cfAuraFeedback' class='form-group-intro'>
                  <div class='alert' v-bind:class='cfAuraFeedback.type' v-if='cfAuraFeedback'>{{cfAuraFeedback.message}}</div>
              </div>
              <div class='form-section'>
                <div class='form-group form-group-end-section'>
                  <label class='label-column  control-label'  for='pazienteCodicefiscale'>Codice Fiscale</label>
                  <div class='control-column'>
                    <input type='text' class='form-control ' id='pazienteCodicefiscale' placeholder='Codice Fiscale' maxlength='16'
                      v-model='paziente.codiceFiscale' :disabled='paziente.idSoggetto'>
                    <div class='toolbar-btn no-desktop btn-search' @click="auraSearchByCF()"  v-if='!paziente.idSoggetto'>
                      <font-awesome-icon :icon="iconSearch" v-if='!auraloading'/>
                      <font-awesome-icon :icon="iconSpinner" class='fa-spin' v-if='auraloading'/>
                    </div>
                  </div>
                  <div class='label-column  no-mobile' v-if='!paziente.idSoggetto'>
                    <a href='#' @click='auraSearchByCF()' v-if='!auraloading' class='btn btn-default' title='Cerca su Aura tramite Codice Fiscale'><span>Cerca su Aura</span></a>
                    <font-awesome-icon :icon="iconSpinner" class='fa-spin' v-if='auraloading'/>
                  </div>
                  <div class='label-column' v-if='paziente.idSoggetto' >&nbsp;</div>
                </div>
                <div class='form-group'>
                  <label class='label-column  control-label'  for='pazienteCognome'>Cognome</label>
                  <div class='control-column'>
                    <input type='text' class='form-control ' id='pazienteCognome' placeholder='Cognome'
                      v-model='paziente.cognome' :disabled='paziente.idSoggetto'>
                  </div>
                  <div class='label-column no-mobile' v-if='!paziente.idSoggetto' >&nbsp;
                  </div>
                  <div class='label-column' v-if='paziente.idSoggetto' >&nbsp;</div>
                </div>
                <div class='form-group'>
                  <label class='label-column  control-label'  for='pazienteNome'>Nome</label>
                  <div class='control-column'>
                    <input type='text' class='form-control ' id='pazienteNome' placeholder='Nome'
                      v-model='paziente.nome' :disabled='paziente.idSoggetto'>
                  </div>
                </div>
                <div class='form-group  form-group-end-section'>
                  <label class='label-column  control-label'  for='pazienteDatanascita'>Data di Nascita</label>
                  <div class='control-column'>
                    <input type='date' class='form-control ' id='pazienteDatanascita'
                      v-model='paziente.dataNascita' :disabled='paziente.idSoggetto'>
                    <div class='toolbar-btn no-desktop btn-search' @click="auraSearchByCognome()">
                      <font-awesome-icon :icon="iconSearch" v-if='!auraloading'/>
                      <font-awesome-icon :icon="iconSpinner" class='fa-spin' v-if='auraloading'/>
                    </div>
                  </div>
                  <div class='label-column'>
                    <a href='#' @click='auraSearchByCognome()' class='btn btn-default' v-if='!auraloading' title='Cerca su Aura tramite Nome Cognome e Data di Nascita'><span>Cerca su Aura</span></a>
                    <font-awesome-icon :icon="iconSpinner" class='fa-spin' v-if='auraloading'/>
                  </div>
                </div>
              </div>
              <div class='form-section-title' v-if='ricercaaura.eseguita'>Dati anagrafici</div>
              <div class='form-section' v-if='ricercaaura.eseguita'>
                <div v-if='ricercaaura.eseguita' class='form-group-intro'>
                  <!-- <div class='form-group'>
                    <label class='label-column  control-label'  for='aslResidenza'></label>
                    <div class='control-column  inline-controls'>
                      <div class='checkbox inline-checkbox'>
                        <label><input type='checkbox' v-model='modifica.enable'  />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Abilita modifica dati</span>
                        </label>
                      </div>
                    </div>
                  </div> -->
                  <div class='form-group-intro' v-if='!paziente.idSoggetto'>
                    <!-- <span class='form-group-warning'>Opzione consigliata solo se non sono presenti i dati su Aura</span><br> -->
                    <span class='form-group-warning'>Compiliare ASL domicilio ed eventualmente utilizzare EXTRA-REGIONE</span>
                  </div>
                  <div v-if="ricercaaura.esito == 'noals'" class='form-group-intro'><span v-if='!ricercaaura.esito'>Inserire le asl di residenza e domicilio</span></div>
                </div>
                <div class='form-section'>
                  <div class='form-group'>
                    <label class='label-column  control-label'  for='aslResidenza'>Asl di residenza</label>
                    <div class='control-column'>
                      <select v-model="paziente.aslResidenza" class='form-control ' :disabled='paziente.idSoggetto' >
                        <option v-for="(a, index) in allAsl" v-bind:value="a" :key="index">
                          {{ a }}
                        </option>
                      </select>
                    </div>
                    <div class='label-column'>&nbsp;</div>
                  </div>
                  <div class='form-group'>
                    <label class='label-column  control-label'  for='aslDomicilio'>Asl di domicilio</label>
                    <div class='control-column'>
                      <select v-model="paziente.aslDomicilio" class='form-control ' :disabled='paziente.idSoggetto'  @change="forceUpdate">
                        <option v-for="(a, index) in allAsl" v-bind:value="a" :key="index" >
                          {{ a }}
                        </option>
                      </select>
                    </div>
                    <div class='label-column'>&nbsp;</div>
                  </div>
                  <div class='form-group' v-if='paziente.aslDomicilio ==="EXTRA-REGIONE"'>
                    <label class='label-column  control-label'  for='asrDomicilio'>Asr di assegnazione</label>
                    <div class='control-column'>
                      <select v-model="paziente.idAsr" class='form-control ' :disabled='paziente.idSoggetto'>
                        <option v-for="(a, index) in allAsr" v-bind:value="a.idAsr" :key="index">
                          {{ a.descrizione }}
                        </option>
                      </select>
                    </div>
                    <div class='label-column'><small><i>Nel caso il paziente sia Extra Regione indicare l'Asr a cui deve essere assegnato</i></small></div>
                  </div>
                  <div class='form-group'>
                    <label class='label-column  control-label'  for='pazienteResidenza'>Comune di residenza</label>
                    <div class="control-column" v-if='paziente.nomeComuneResidenza || paziente.idSoggetto'>
                      <input type='text' class='form-control ' id='pazienteResidenza' placeholder='Comune Residenza'
                          v-model='paziente.nomeComuneResidenza'  disabled='disabled' >
                    </div>
                    <div class='control-column' v-if='comuni && paziente.nomeComuneResidenza==null && !paziente.idSoggetto'>
                      <autocomplete
                       placeholder='Comune residenza'
                        :source="comuni"
                        results-value="istatComune"
                        inputClass="form-control"
                        @selected="selectComuneResidenza"
                        results-display="nomeComune" :disabled='!modifica.enable'>
                      </autocomplete>
                    </div>
                    <div class='label-column'>
                     <!-- {{paziente.nomeComuneResidenza}}
                      <a href='#' @click='abilitaModificaComuneResidenza()' v-if='paziente.nomeComuneResidenza!=null' class='btn btn-default'><span>Modifica</span></a>
                      -->
                    </div>
                  </div>
                  <div class='form-group'>
                    <label class='label-column control-label'  for='pazienteDomicilio'>Comune di domicilio</label>
                    <div class="control-column" v-if='paziente.nomeComuneDomicilio  || paziente.idSoggetto'>
                      <input type='text' class='form-control ' id='pazienteDomicilio' placeholder='Comune Domicilio'
                          v-model='paziente.nomeComuneDomicilio' disabled='disabled' >
                    </div>
                    <div class='control-column' v-if='comuni && paziente.nomeComuneDomicilio==null  && !paziente.idSoggetto'>
                      <autocomplete
                       placeholder='Comune domicilio'
                        :source="comuni"
                        results-value="istatComune"
                        inputClass="form-control"
                        @selected="selectComuneDomicilio"
                        results-display="nomeComune">
                      </autocomplete>
                    </div>
                    <div class='label-column'>&nbsp;</div>
                  </div>
                  <div class='form-group'>
                    <label class='label-column  control-label'  for='pazienteIndirizzo'>Indirizzo domicilio</label>
                    <div class='control-column'>
                      <input type='text' class='form-control ' id='pazienteIndirizzo' placeholder='Indirizzo domicilio'
                        v-model='paziente.indirizzoDomicilio' :disabled='paziente.idSoggetto'>
                    </div>
                    <div class='label-column'>&nbsp;</div>
                  </div>
                  <div class='form-group'>
                    <label class='label-column  control-label'  for='pazienteTelefono'>Telefono recapito</label>
                    <div class='control-column'>
                      <input type='text' class='form-control ' id='pazienteTelefono' placeholder='Telefono recapito'
                        v-model='paziente.telefonoRecapito' :disabled='paziente.idSoggetto'>
                    </div>
                    <div class='label-column emphasis'>
                      <div class='emphasis-content' v-if='!paziente.idSoggetto'>
                        <div class='arrow-icon'><font-awesome-icon :icon="iconArrowLeft" /></div>
                        <div class='emphasis-text'><font-awesome-icon :icon="iconInfo" class='info-icon'/> Inserire il telefono cellulare</div>
                      </div>
                    </div>
                  </div>
                  <div class='form-group  form-group-end-section'>
                    <label class='label-column  control-label'  for='pazienteEmail'>Email</label>
                    <div class='control-column'>
                      <input type='text' class='form-control ' id='pazienteEmail' placeholder='Email'
                        v-model='paziente.email' :disabled='paziente.idSoggetto'>
                    </div>
                    <div class='label-column'>&nbsp;</div>
                  </div>
                </div>
              </div>

              <!-- INPUTS DECORSO -->
              <!-- ------------------------------------------------------------------------------------------------- -->
              <template v-if="segnalazione">
                <div class='form-section-title' v-if='ricercaaura.eseguita'>Sintomi</div>
                <div class='form-section '  v-if='ricercaaura.eseguita'>
                   <div class='form-group' v-bind:class="{ 'form-group-end-section': decorso.sintomi!='SI'}">
                    <label class='label-column-small  control-label'  for='decorso_sintomi'>Sintomi Covid</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='decorso.sintomi' value='NO' name='decorso_sintomi' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck" class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='decorso.sintomi'  value='SI' name='decorso_sintomi' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class='form-section form-section-sintomi' v-if='decorso.sintomi=="SI"'>
                  <!-- data -->
                  <div class='form-group '>
                    <label class='label-column-small  control-label'  for='decorsoDataInizioSintomatologia'>Data esordio sintomi malattia</label>
                    <div class='control-column-large'>
                      <input type='date' class='form-control ' id='decorsoDataInizioSintomatologia'
                        v-model='decorso.dataInizioSint'>
                    </div>
                  </div>

                  <!-- temperatura -->
                  <div class='form-group form-group-grey'>
                      <label class='label-column-small  control-label'  for='sintomiTemperatura'>Temperatura</label>
                      <div class='control-column-large'>
                        <input type='number' min='32' max='45' class='form-control ' id='sintomiTemperatura'
                          v-model='sintomo.temperatura'>
                      </div>
                  </div>
                  <!-- tosse -->
                  <div class='form-group'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_tosse'>Tosse</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgTosse' value='NO' name='sintomi_flg_tosse' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <!--
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgTosse' value='SI' name='sintomi_flg_tosse' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                      -->
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgTosse' value='NON_PERSISTENTE' name='sintomi_flg_tosse' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Non persistente</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgTosse' value='PERSISTENTE_GRASSA' name='sintomi_flg_tosse' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Persistente grassa</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgTosse' value='PERSISTENTE_SECCA' name='sintomi_flg_tosse' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Persistente secca</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- dispnea -->
                  <div class='form-group  form-group-grey'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_dispnea'>Dispnea</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgDispnea' value='NO' name='sintomi_flg_dispnea' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck" class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgDispnea'  value='SI' name='sintomi_flg_dispnea' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- diarrea -->
                  <div class='form-group'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_diarrea'>Diarrea</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgDiarrea' value='NO' name='sintomi_flg_diarrea' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgDiarrea' value='SI' name='sintomi_flg_diarrea' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- congiuntivite -->
                  <div class='form-group  form-group-grey'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_congiuntivite'>Congiuntivite</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgCongiuntivite' value='NO' name='sintomi_flg_congiuntivite' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck" class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgCongiuntivite'  value='SI' name='sintomi_flg_congiuntivite' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- dolori_musc -->
                  <div class='form-group'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_dolori_musc'>Dolori Muscolari</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgDoloriMusc' value='NO' name='sintomi_flg_dolori_musc' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <!--
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flg_dolori_musc' value='SI'  name='sintomi_flg_dolori_musc' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                      -->
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgDoloriMusc' value='PRIMA_EMERGENZA' name='sintomi_flg_dolori_musc' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Prima Emergenza</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgDoloriMusc' value='ULTIME_2_SETTIMANE' name='sintomi_flg_dolori_musc' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Ultime due settimane</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- gusto -->
                  <div class='form-group form-group-grey'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_gusto'>Ageusia</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgGusto' value='NO' name='sintomi_flg_gusto' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgGusto' value='SI' name='sintomi_flg_gusto' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- olfatto -->
                  <div class='form-group'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_olfatto'>Anosmia</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgOlfatto' value='NO' name='sintomi_flg_olfatto' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgOlfatto' value='SI' name='sintomi_flg_olfatto' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- raffreddore -->
                  <div class='form-group form-group-grey'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_raffreddore'>Raffreddore</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgRaffreddore' value='NO' name='sintomi_flg_raffreddore' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <!--
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flg_raffreddore' value='SI' name='sintomi_flg_raffreddore' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                      -->
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgRaffreddore' value='LEGGERO' name='sintomi_flg_raffreddore' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Leggero</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgRaffreddore' value='FORTE' name='sintomi_flg_raffreddore' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Forte</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- stanchezza -->
                  <div class='form-group'>
                    <label class='label-column-small  control-label'  for='sintomi_flg_stanchezza'>Stanchezza</label>
                    <div class='control-column-large  inline-controls'>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgStanchezza' value='NO' name='sintomi_flg_stanchezza' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>No</span>
                        </label>
                      </div>
                      <!--
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgStanchezza' value='SI' name='sintomi_flg_stanchezza' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Si</span>
                        </label>
                      </div>
                      -->
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgStanchezza' value='NELLA_NORMA' name='sintomi_flg_stanchezza' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Nella norma</span>
                        </label>
                      </div>
                      <div class='radio inline-radio'>
                        <label><input type='radio' v-model='sintomo.flgStanchezza' value='PIU_SOLITO' name='sintomi_flg_stanchezza' />
                          <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>
                          <span>Più del solito</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- note -->
                  <div class='form-group form-group-grey  form-group-end-section'>
                      <label class='label-column-small  control-label'  for='sintomiNote'>Altro</label>
                      <div class='control-column-large control-with-text-area'>
                        <div class='form-control'>
                          <textarea rows='3'  id='sintomiNote'
                            v-model='sintomo.noteSintomo' maxlength="500"></textarea>
                        </div>
                      </div>
                  </div>
                </div>
                <div class='form-section-title' v-if='ricercaaura.eseguita'>Decorso</div>
                <div class='form-section  '  v-if='ricercaaura.eseguita'>
                  <!-- note decorso -->
                  <div class='form-group'>
                    <label class='label-column-small  control-label'  for='decorsoCondizioni'>Condizioni cliniche</label>
                    <div class='control-column-large control-with-text-area'>
                      <div class='form-control'>
                        <textarea rows='3'   id='decorsoCondizioni '  maxlength="500"
                          v-model.trim='decorso.condizioniCliniche ' ></textarea>
                      </div>
                    </div>
                  </div>
                  <div class='form-group'>
                      <label class='label-column-small  control-label'  for='luogoPaziente '>Luogo paziente</label>
                      <div class='control-column-large control-with-text-area'>
                        <div class='form-control'>
                          <textarea rows='3'   id='luogoPaziente '  maxlength="500"
                            v-model.trim='decorso.luogoPaziente ' ></textarea>
                        </div>
                      </div>
                  </div>
                  <div class='form-group'>
                      <label class='label-column-small  control-label'  for='descrizioneContesto'>Contesto - Contatto</label>
                      <div class='control-column-large control-with-text-area'>
                        <div class='form-control'>
                          <textarea rows='3'   id='descrizioneContesto'  maxlength="500"
                            v-model.trim='decorso.descrizioneContesto' ></textarea>
                        </div>
                      </div>
                  </div>
                  <!-- <div class='form-group form-group-end-section'>
                      <label class='label-column-small  control-label'  for='sintomiNote'>Note al decorso</label>
                      <div class='control-column-large control-with-text-area'>
                        <div class='form-control'>
                          <textarea rows='3'   id='sintomiNote'
                            v-model='decorso.note' ></textarea>
                        </div>
                      </div>
                  </div>-->
                </div>
              </template>
            </form>
        </div>
        <div class='main-feedback' v-if='feedback'>
            <div class='alert' v-bind:class='feedback.type'>
                {{feedback.message}}
                <ul v-if="feedback.details">
                  <li v-for="(detail, index) in feedback.details" :key="index">{{detail}}</li>
                </ul>
            </div>
        </div>
        <div class='modal-footer ' >
          <div class='toolbar-left'  v-if='!finish'>
            <a href  @click.prevent="$modal.hide('segnalazione-paziente')" class='btn btn-default'>Annulla</a>
          </div>
          <div class='toolbar-right' v-if='ricercaaura.eseguita && !finish' >
            <a href='#' @click.prevent='savePaziente(true)' class='btn btn-primary' v-if='!loading'>Salva</a>
            <font-awesome-icon :icon="iconSpinner" class='fa-spin' v-if='loading'/>

          </div>
          <div class='toolbar-right' v-if='finish' >
            <a href  @click="$modal.hide('segnalazione-paziente')" class='btn btn-default'>Chiudi</a>
          </div>
        </div>
    </div>
  </modal>
</template>

<script>
import Constants from '@/util/constants'
import Autocomplete from 'vuejs-auto-complete'
import { faCircleNotch, faCheck, faInfoCircle, faLongArrowAltLeft, faSearch, faExclamationCircle } from '@fortawesome/free-solid-svg-icons'
import moment from 'moment'
import { sintomiVecchi2Nuovi, validaCodiceFiscale } from '../../util/business-logic'
import { creaDiarioPagina, getNuoviSintomi } from '../../util/api'

// const cfPattern = /^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$/
// const mobileItaliaPattern = /^(\((00|\+)39\)|(00|\+)39)?(38[890]|34[7-90]|36[680]|33[3-90]|32[89])\d{7}$/
// const precMobilePattern = /^([1-9][1-90][1-90])\d{7}$/
// const mobilePattern = /^((\+|00)([1-9][1-9]))?([1-9])\d{7,9}$/
const digitPattern = /^\d+$/

export default {
  name: 'SegnalazionePaziente',
  components: {
    Autocomplete
  },
  data: function () {
    return {
      iconSpinner: faCircleNotch,
      iconCheck: faCheck,
      iconInfo: faInfoCircle,
      iconArrowLeft: faLongArrowAltLeft,
      iconSearch: faSearch,
      iconImportant: faExclamationCircle,
      paziente: null,
      cfAuraFeedback: null,
      feedback: null,
      auraloading: false,
      ricercaaura: { eseguita: false, esito: null },
      modifica: { enable: false },
      comuni: [],
      allAsl: [],
      allAsr: [],
      allTipisoggetto: [],
      finish: false,
      decorso: { sintomi: 'NO' },
      sintomo: {},
      segnalazione: false
    }
  },
  // computed: {
  // paziente: function () {
  //    return this.paziente
  //  }
  // },
  methods: {
    debug () {
      console.log('paziente', this.paziente)
    },
    forceUpdate (event) {
      console.log('forceupdate', this.paziente)
      this.$forceUpdate()
    },
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.paziente = event.params.paziente
      if ('segnalazione' in event.params) this.segnalazione = event.params.segnalazione
      this.cfAuraFeedback = null
      this.feedback = null
      this.auraloading = false
      this.modifica = { enable: false }
      this.finish = false

      if (!this.paziente) {
        this.paziente = {}
        this.ricercaaura = { eseguita: false, esito: null }
      } else {
        if (event.params.paziente.dataNascita) {
          this.paziente.dataNascita = moment(event.params.paziente.dataNascita).format('YYYY-MM-DD')
        }
        if (event.params.comuneResidenza) {
          this.paziente.nomeComuneResidenza = event.params.paziente.comuneResidenza.nomeComune
        }
        if (event.params.paziente.comuneDomicilio) {
          this.paziente.nomeComuneDomicilio = event.params.paziente.comuneDomicilio.nomeComune
        }

        this.ricercaaura = { eseguita: true, esito: 'ok' }
      }
    },
    ricercaSoggettoEsistente (paziente, profiloAnagraficoAura, suAura = false) {
      console.log('ricercaSoggettoEsistente', paziente, profiloAnagraficoAura)
      this.cfAuraFeedback = null
      this.auraloading = true
      this.ricercaaura = { eseguita: false, esito: null }
      this.$http.get(Constants.VISURAMMG_BASE_URL + '/mmgvisura/soggetti/by-cf/' + paziente.codiceFiscale.toUpperCase()).then(response => {
        console.log('auraSearchByCf', response)
        this.finish = true
        this.feedback = { type: 'warning', message: 'Soggetto già presente sulla piattaforma, ora lo puoi trovare nella lista dei tuoi pazienti. Si rammenta che le segnalazioni ai SISP vanno effettuate solo nel caso di prima segnalazione' }
      }).catch(error => {
        console.log(error)
        if (error && error.response.status === 404) {
          console.log('Paziente non trovato')
          this.preparaPazienteCaricato(paziente)
          this.paziente.idAura = profiloAnagraficoAura
          if (!suAura) {
            this.ricercaaura.esito = 'ko'
            this.cfAuraFeedback = { type: 'warning', message: 'Paziente non trovato. Verificare i criteri di ricerca o inserire i dati anagrafici' }
          }
        } else {
          this.cfAuraFeedback = { type: 'warning', message: 'La ricerca dei dati del paziente non è andata a buon fine. Riprovare fra qualche minuto, o inserire i dati' }
        }
        this.ricercaaura.eseguita = true
        this.auraloading = false
      }).then(this.auraloading = false)
    },
    auraSearchByCF () {
      console.log('auraSearchByCF', this.paziente)
      this.cfAuraFeedback = null
      if (!this.paziente.codiceFiscale || !validaCodiceFiscale(this.paziente.codiceFiscale)) {
        this.cfAuraFeedback = { type: 'warning', message: 'Inserire un codice fiscale valido' }
      } else {
        this.auraloading = true
        this.ricercaaura = { eseguita: false, esito: null }
        this.$http.get(Constants.VISURAMMG_BASE_URL + '/aura/' + this.paziente.codiceFiscale).then(response => {
          console.log('auraSearchByCf', response)
          this.auraloading = false
          // this.ricercaaura.eseguita = true
          if (response && response.data && response.data.length > 0) {
            this.elaboraRisultatoAura(response.data)
          } else {
            this.ricercaSoggettoEsistente(this.paziente)
            // this.ricercaaura.esito = 'ko'
            // this.cfAuraFeedback = { type: 'warning', message: 'Paziente non trovato. Verificare i criteri di ricerca o inserire i dati anagrafici' }
          }
        }).catch(error => {
          console.log(error)
          this.ricercaaura.eseguita = true
          this.auraloading = false
          this.cfAuraFeedback = { type: 'warning', message: 'La ricerca dei dati del paziente non è andata a buon fine. Riprovare fra qualche minuto, o inserire i dati' }
        }).then(this.loading = false)
      }
    },
    auraSearchByCognome () {
      console.log('auraSearchByCognome ', this.paziente)
      this.cfAuraFeedback = null
      if (!this.paziente.cognome || !this.paziente.nome || !this.paziente.dataNascita) {
        this.cfAuraFeedback = { type: 'warning', message: 'Inserire Nome Cognome e Data di Nascita' }
      } else {
        this.auraloading = true
        this.ricercaaura = { eseguita: false, esito: null }
        console.log('data di nascita', this.paziente.dataNascita)
        const dataNascita = new Date(this.paziente.dataNascita)
        const mm = dataNascita.getMonth() + 1
        const dd = dataNascita.getDate()
        const yyyy = dataNascita.getFullYear()
        const url = '/aura/' + this.paziente.nome + '/' + this.paziente.cognome + '/' + (dd > 9 ? '' : '0') + dd + '/' + (mm > 9 ? '' : '0') + mm + '/' + yyyy
        this.$http.get(Constants.VISURAMMG_BASE_URL + url).then(response => {
          console.log('auraSearchByCognome', response)
          this.auraloading = false
          // this.ricercaaura.eseguita = true
          if (response && response.data && response.data.length > 0) {
            this.elaboraRisultatoAura(response.data)
          } else {
            this.ricercaaura.eseguita = true
            this.ricercaaura.esito = 'ko'
            this.cfAuraFeedback = { type: 'warning', message: 'Paziente non trovato. Verificare i criteri di ricerca o inserire i dati anagrafici' }
          }
        }).catch(error => {
          console.log(error)
          this.ricercaaura.eseguita = true
          this.auraloading = false
          this.cfAuraFeedback = { type: 'warning', message: 'La ricerca dei dati del paziente non è andata a buon fine. Riprovare fra qualche minuto, o inserire i dati' }
        }).then(this.loading = false)
      }
    },
    elaboraRisultatoAura (soggettiAura) {
      if (soggettiAura.length === 1) {
        this.caricaDettaglioPazienteDaAura(soggettiAura[0].profiloAnagrafico)
      } else {
        console.log('modale più soggetti', soggettiAura)
        this.caricaDettaglioPazienteDaAura(soggettiAura[0].profiloAnagrafico)
        /* var newSoggettiAuraModal = $uibModal.open({
          templateUrl: 'partials/pazienti/soggettiAura.html',
          controller: 'SoggettiAuraModalCtrl',
          backdrop: 'static',
          size:'lg',
          resolve: {
                soggettiAura: function () {return soggettiAura;},
              },});
        newSoggettiAuraModal.result.then(function (soggettoAura) {
          caricaDettaglioPazienteDaAura(soggettoAura.profiloAnagrafico);
            }, function () {});
          */
      }
    },
    preparaPazienteCaricato (paziente) {
      if (paziente.dataNascita) {
        paziente.dataNascita = new Date(paziente.dataNascita)
      }
      if (paziente.comuneResidenzaIstat) {
        paziente.comuneResidenzaIstatCompleto = paziente.comuneResidenza
        paziente.nomeComuneResidenza = paziente.comuneResidenza.nomeComune
      }
      if (paziente.comuneDomicilioIstat) {
        paziente.comuneDomicilioIstatCompleto = paziente.comuneDomicilio
        paziente.nomeComuneDomicilio = paziente.comuneDomicilio.nomeComune
      }
      if (!this.paziente) {
        this.paziente = {}
      }
      this.paziente.codiceFiscale = paziente.codiceFiscale
      this.paziente.idAsr = paziente.idAsr
      this.paziente.cognome = paziente.cognome
      this.paziente.nome = paziente.nome
      if (paziente.dataNascita) {
        this.paziente.dataNascita = moment(paziente.dataNascita).format('YYYY-MM-DD')
      }
      // this.paziente.dataNascita = paziente.dataNascita
      this.paziente.comuneResidenzaIstat = paziente.comuneResidenzaIstat
      this.paziente.nomeComuneResidenza = paziente.nomeComuneResidenza
      this.paziente.comuneDomicilioIstat = paziente.comuneDomicilioIstat
      this.paziente.nomeComuneDomicilio = paziente.nomeComuneDomicilio
      this.paziente.indirizzoDomicilio = paziente.indirizzoDomicilio
      this.paziente.telefonoRecapito = paziente.telefonoRecapito
      this.paziente.email = paziente.email
      this.paziente.idAsr = paziente.idAsr
      this.paziente.comuneResidenzaIstatCompleto = paziente.comuneResidenzaIstatCompleto
      this.paziente.comuneDomicilioIstatCompleto = paziente.comuneDomicilioIstatCompleto
      this.paziente.numeroCampioni = paziente.numeroCampioni
      this.paziente.numeroDecorsi = paziente.numeroDecorsi
      this.paziente.aslDomicilio = paziente.aslDomicilio
      this.paziente.aslResidenza = paziente.aslResidenza
      this.paziente.idAura = paziente.idAura
    },
    caricaDettaglioPazienteDaAura (profiloAnagraficoAura) {
      this.auraloading = true
      this.$http.get(Constants.VISURAMMG_BASE_URL + '/aura/find/' + profiloAnagraficoAura).then(response => {
        console.log('caricaDettaglioPazienteDaAura', response)
        this.auraloading = false
        if (response && response.data) {
          this.ricercaSoggettoEsistente(response.data, profiloAnagraficoAura, true)
          // this.preparaPazienteCaricato(response.data)
          // this.paziente.idAura = profiloAnagraficoAura
        } else {
          this.ricercaaura.eseguita = true
          this.ricercaaura.esito = 'ko'
          this.cfAuraFeedback = { type: 'warning', message: 'Paziente non trovato' }
        }
      }).catch(error => {
        console.log(error)
        this.ricercaaura.eseguita = false
        this.auraloading = false
        this.cfAuraFeedback = { type: 'danger', message: 'La ricerca dei dati del paziente non è andata a buon fine. Riprovare fra qualche minuto, o inserire i dati' }
      }).then(this.loading = false)
    },
    selectComuneResidenza (comune) {
      console.log('select', comune)
      this.paziente.comuneResidenzaIstatCompleto = { nomeComune: comune.display, istatComune: comune.value }
    },
    selectComuneDomicilio (comune) {
      console.log('select', comune)
      this.paziente.comuneDomicilioIstatCompleto = { nomeComune: comune.display, istatComune: comune.value }
    },
    abilitaModificaComuneResidenza () {
      delete this.paziente.nomeComuneResidenza
      // this.$set(this.paziente, 'nomeComuneResidenza', null)
      console.log('dopo', this.paziente)
    },
    validatePaziente () {
      const result = []
      if (!this.paziente.idSoggetto) {
        if (!this.paziente.cognome) {
          result.push('Cognome paziente obbligatorio')
        }
        if (!this.paziente.nome) {
          result.push('Nome paziente obbligatorio')
        }
        if (!this.paziente.dataNascita) {
          result.push('Data di nascita paziente obbligatoria')
        } else if (new Date(this.paziente.dataNascita).getTime() > new Date().getTime()) {
          result.push('Data di nascita paziente futura')
        }
        if (this.paziente.codiceFiscale) {
          if (!validaCodiceFiscale(this.paziente.codiceFiscale)) {
            result.push('Codice fiscale non corretto')
          }
        }
        if (!this.paziente.telefonoRecapito) {
          result.push('Recapito telefonico obbligatorio')
        }
        if (this.paziente.telefonoRecapito && this.paziente.telefonoRecapito.search(digitPattern) === -1) {
          result.push('Telefono non formattato correttamente')
        }
        if (this.paziente.aslDomicilio === 'EXTRA-REGIONE' && !this.paziente.idAsr) {
          result.push('Indicare l\'Asr di assegnazione')
        }
      }

      if (!this.decorso.sintomi) {
        result.push('Indicare se sono presenti sintomi Covid')
      }
      if (this.decorso.sintomi === 'SI' && !this.decorso.dataInizioSint) {
        result.push('Indicare la data di esordio dei sintomi')
      }
      if (this.decorso.sintomi === 'SI' && Object.keys(this.sintomo).length === 0 && this.sintomo.constructor === Object) {
        result.push('Indicare almeno un sintomo')
      }

      // In caso di segnalazione (cioè l'utente ha cliccato sul bottone "Segnala")
      // è necessario inserire o un sintomo o uno dei 3 campi note
      if (this.segnalazione && this.decorso.sintomi === 'NO' && !this.decorso.condizioniCliniche &&
        !this.decorso.luogoPaziente && !this.decorso.descrizioneContesto) {
        result.push('Indicare i sintomi oppure inserire almeno uno tra "Condizione clinica", "Luogo paziente" e "Contesto - Contatto"')
      }

      return result
    },
    savePaziente () {
      this.feedback = null
      if (this.decorso.sintomi === 'SI') {
        this.decorso.sintomo = this.sintomo
      }
      if (this.paziente.comuneResidenzaIstatCompleto) {
        this.paziente.comuneResidenzaIstat = this.paziente.comuneResidenzaIstatCompleto.istatComune
      }

      if (this.paziente.comuneDomicilioIstatCompleto) {
        this.paziente.comuneDomicilioIstat = this.paziente.comuneDomicilioIstatCompleto.istatComune
      }

      if (this.paziente.idTipoSoggetto === -1) {
        this.paziente.idTipoSoggetto = null
      }

      // Aggiungiamo il decorso solo se l'utente ha inserito dei sintomi
      // oppure ha inseriro uno dei campi note del decorso
      if (this.decorso.sintomi === 'SI' || this.decorso.condizioniCliniche ||
        this.decorso.luogoPaziente || this.decorso.descrizioneContesto) {
        this.paziente.decorso = this.decorso
      } else {
        this.paziente.decorso = null
      }

      const validateMessageDetail = this.validatePaziente()
      if (validateMessageDetail.length === 0) {
        this.loading = true

        this.paziente.sintomi = this.decorso ? this.decorso.sintomi : 'NO'

        // console.log(JSON.stringify(this.paziente, null, 4))
        // if (Math.random() !== 0) {
        //   this.loading = false
        //   return
        // }

        this.$http.post(Constants.VISURAMMG_BASE_URL + '/mmgvisura/soggetti', this.paziente).then(async response => {
          console.log('savePaziente', response)
          this.finish = true
          this.loading = false
          if (response && response.data && response.data.message) {
            this.feedback = { type: 'success', message: response.data.message }
          } else {
            this.feedback = { type: 'success', message: 'Segnalazione completata con successo' }
          }

          // Se l'utente ha aggiunto dei sintomi al decorso
          // => proviamo a salvare anche una pagina di diario e come va va
          // -------------------------------------------------------------------
          if (this.decorso && this.decorso.sintomi && this.decorso.sintomi === 'SI') {
            try {
              // Proviamo prima a prendere l'elenco dei nuovi sintomi
              const { data: nuoviSintomi } = await getNuoviSintomi()
              const vecchiSintomi = this.decorso.sintomo
              const sintomi = sintomiVecchi2Nuovi(vecchiSintomi, nuoviSintomi)
              const soggetto = response.data
              const payload = {
                soggetto: soggetto.idSoggetto,
                data: this.decorso.dataInizioSint,
                note: this.decorso.sintomo.noteSintomo || '',
                intervento: null,
                sintomi
              }

              console.log({ paziente: this.paziente, diario: payload })
              await creaDiarioPagina(payload)
            } catch (err) {
              console.error(err)
            }
          }
        }).catch(error => {
          console.log(error)
          this.loading = false
          if (error && error.response.status === 409) {
            this.finish = true
            this.feedback = { type: 'warning', message: 'Il paziente è già inserito a sistema, aggiungere segnalazione partendo dal paziente nella lista' }
          } else {
            this.feedback = { type: 'danger', message: 'Si è verificato un errore nel salvataggio dei dati' }
          }
          console.error('savePaziente  Error', error)
        }).then(this.loading = false)
      } else {
        this.feedback = { type: 'warning', message: 'Si è verificato un errore nel salvataggio dei dati' }
        this.feedback.type = 'warning'
        this.feedback.message = 'Per proseguire è necessario correggere le seguenti anomalie'
        this.feedback.details = validateMessageDetail
      }
    }
  },
  mounted () {
    // comuni
    console.log('loadComuni')
    this.$http.get(Constants.VISURAMMG_BASE_URL + '/decodifiche/comuni').then(response => {
      this.comuni = response.data
      console.log('getComuni', response.data)
    }).catch(error => {
      console.log(error)
    }).then(this.loading = false)

    // tiposoggetto
    console.log('tiposoggetto')
    this.$http.get(Constants.VISURAMMG_BASE_URL + '/decodifiche/tiposoggetto').then(response => {
      this.allTipisoggetto = response.data
      this.allTipisoggetto.unshift({ idTipoSoggetto: -1, descTipoSoggetto: 'cittadino' })

      console.log('tiposoggetto', this.allTipisoggetto)
    }).catch(error => {
      console.log(error)
    }).then(this.loading = false)

    // asl
    console.log('asl')
    this.$http.get(Constants.VISURAMMG_BASE_URL + '/decodifiche/asl').then(response => {
      for (let i = 0; i < response.data.length; i++) {
        if (response.data[i].descRegione === 'PIEMONTE') {
          this.allAsl.push(response.data[i].descAslEstesa)
        }
      }
      this.allAsl.push('EXTRA-REGIONE')
      console.log('getAsl', this.allAsl)
    }).catch(error => {
      console.log(error)
    }).then(this.loading = false)

    // asr
    console.log('loadAsr')
    this.allAsr = []
    this.$http.get(Constants.VISURAMMG_BASE_URL + '/decodifiche/asr').then(response => {
      console.log('loadAsr', response.data)
      if (response.data) {
        for (let i = 0; i < response.data.length; i++) {
          if (response.data[i].idEnte != null) {
            this.allAsr.push(response.data[i])
          }
        }
      }
    }).catch(error => {
      console.log(error)
    }).then(this.loading = false)
  }
}
</script>
<style scoped>
    .modal-content{font-size: .8em; display: table; border-spacing:0 16px;padding-bottom: 15px;padding-top:0}
    .modal-title{font-size: 1.6em;padding-bottom: 6px; border-bottom: solid 1px #ccc; margin-bottom: 6px; width:100%;}
    .arrow-icon{font-size: 2rem;  display: inline-block; padding-right: 6px;}
    .emphasis{position: relative; color: #1684b9;}
    .emphasis-content{display: flex; align-content: center; position: absolute; right: 0;}
    .emphasis-text{background-color: #bfe5f7;right: 0; font-size: .9rem;white-space: nowrap; line-height: 2.2rem; padding:0 6px; border-left: solid 1px #1ba1e2;}
    .no-desktop{display: none;}
    @media only screen and (max-width: 768px) {
      .modal-title{font-size: 1.5em;}
      .btn-search{flex-grow: 1; margin: 4px 0 4px 1.5em;}
      .no-desktop{display: inline-block;}
      .no-mobile{display: none;}
      .form-group-end-section{border-bottom: solid 1px #ccc; margin-bottom: 6px; padding-bottom: 12px;}
      .form-group .control-column-large{width: auto;}
      .form-section-sintomi .label-column-small{font-weight: bold;}
      .form-section-sintomi .radio{display: block;margin: 3px 0;}
      .emphasis-content{position: static;}
      .arrow-icon{display: none;}
      .info-icon{display: inline-block;}
      .emphasis-text{border-right: none; border-left: none; border-top: solid 1px #1ba1e2; border-bottom: solid 1px #1ba1e2}

    }
</style>
