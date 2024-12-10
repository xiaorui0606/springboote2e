Feature: Language Tests

  @testcase_1184854_LT_en_US @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1184854_LT_en_US : Uni-Lang - English_en-US
    When  Placing 1 call with language set en-US to target number {$}LT_en_US_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "You can say Pay bill, Transfer funds, or End call"
    Then  Wait for welcome message to be played: {$}LT_en_US_MenuPrompt
    # C2 chooses to "Pay bill"
    Then  Wait for send TTS in en-US: {$}LT_en_US_UserPayBill
    # Bot asks for bill type: "Which bill you want to pay? Electricity bill or telephone bill",
    Then  Wait for response: {$}LT_en_US_AskBillType
    # C2 chooses "Electricity bill" or "telephone bill"
    Then  Wait for send TTS in en-US: {$}LT_en_US_UserChooseBillType
    # Bot asks for user account number: "Enter your 5 digit account number"
    Then  Wait for response: {$}LT_en_US_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Paying electricity bill with account 12345"
    Then  Wait for response: {$}LT_en_US_PayBillConfirmation
    # Bot returns to main menu: "back to main menu"
    Then  Wait for response: {$}LT_en_US_BackToMainMenu
    # Bot plays main menu again: "You can say Pay bill, Transfer funds, or End call"
    Then  Wait for response: {$}LT_en_US_MenuPrompt
    # C2 chooses to "End call"
    Then  Wait for send TTS in en-US: {$}LT_en_US_UserEndCall
    # Bot confirms that call will be ended: "Your conversation will end"
    Then  Wait for response: {$}LT_en_US_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1184869_LT_es_ES @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1184869_LT_es_ES : Uni-Lang - Spanish_es-ES
    When  Placing 1 call with language set es-ES to target number {$}LT_es_ES_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Puede decir Pagar factura, Transferir fondos o Finalizar llamada"
    #                      "You can say Pay bill, Transfer funds or End call"
    Then  Wait for welcome message to be played: {$}LT_es_ES_MenuPrompt
    # C2 chooses to "paga factura" ("Pay bill")
    Then  Wait for send TTS in es-ES: {$}LT_es_ES_UserPayBill
    # Bot asks for bill type: "¿Qué factura quieres pagar? ¿Factura de electricidad o factura de teléfono?"
    #                         "Which bill you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_es_ES_AskBillType
    # C2 chooses "electricidad" ("Electricity bill")
    Then  Wait for send TTS in es-ES: {$}LT_es_ES_UserChooseBillType
    # Bot asks for user account number: "Ingrese su número de cuenta de 5 dígitos" ("Enter your 5 digit account number")
    Then  Wait for response: {$}LT_es_ES_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Pagar Factura de electricidad factura con 12345 cuenta"
    #                                 "Paying electricity bill with 12345 account"
    Then  Wait for response: {$}LT_es_ES_PayBillConfirmation
    # Bot returns to main menu: "regresar al menú principal" ("back to main menu")
    Then  Wait for response: {$}LT_es_ES_BackToMainMenu
    # Bot plays main menu again: "Puede decir Pagar factura, Transferir fondos o Finalizar llamada"
    #                            "You can say Pay bill, Transfer funds or End call"
    Then  Wait for response: {$}LT_es_ES_MenuPrompt
    # C2 chooses to "Fin de la llamada" ("End call")
    Then  Wait for send TTS in es-ES: {$}LT_es_ES_UserEndCall
    # Bot confirms that call will be ended: "Tu conversación terminará" ("Your conversation will end")
    Then  Wait for response: {$}LT_es_ES_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1184878_LT_nb_NO @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1184878_LT_nb_NO : Uni-Lang - Norwegian_nb-NO
    When  Placing 1 call with language set nb-NO to target number {$}LT_nb_NO_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Du kan si Betale regning, Overfør midler, eller Avslutt samtale."
    #                      "You can say Pay bill, Transfer funds, or End call."
    Then  Wait for welcome message to be played: {$}LT_nb_NO_MenuPrompt
    # C2 chooses to "Betale regning" ("Pay bill")
    Then  Wait for send TTS in nb-NO: {$}LT_nb_NO_UserPayBill
    # Bot asks for bill type: "Hvilken regning vil du betale? elektrisitet regning eller telefon regning?"
    #                         "Which bill you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_nb_NO_AskBillType
    # C2 chooses "elektrisitet regning" ("Electricity bill")
    Then  Wait for send TTS in nb-NO: {$}LT_nb_NO_UserChooseBillType
    # Bot asks for user account number: "Skriv inn et 5-sifret kontonummer." ("Enter your 5 digit account number")
    Then  Wait for response: {$}LT_nb_NO_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Betale elektrisitet regning med 12345 konto."
    #                                 "Paying electricity bill with 12345 account"
    Then  Wait for response: {$}LT_nb_NO_PayBillConfirmation
    # Bot returns to main menu: "Tilbake til hovedmenyen." ("back to main menu")
    Then  Wait for response: {$}LT_nb_NO_BackToMainMenu
    # Bot plays main menu again: "Du kan si Betale regning, Overfør midler, eller Avslutt samtale."
    #                            "You can say Pay bill, Transfer funds or End call"
    Then  Wait for response: {$}LT_nb_NO_MenuPrompt
    # C2 chooses to "Avslutt samtale" ("End call")
    Then  Wait for send TTS in nb-NO: {$}LT_nb_NO_UserEndCall
    # Bot confirms that call will be ended: "Ingen intensjon. Samtalen din avsluttes." ("Your conversation will end")
    Then  Wait for response: {$}LT_nb_NO_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1223617_LT_de_DE @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1223617_LT_de_DE : Uni-Lang - German_de-DE
    When  Placing 1 call with language set de-DE to target number {$}LT_de_DE_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Sie können Rechnung bezahlen, Gelder überweisen, oder Anruf beenden sagen."
    #                      "You can say Pay bill, Transfer funds, or End call."
    Then  Wait for welcome message to be played: {$}LT_de_DE_MenuPrompt
    # C2 chooses to "Rechnung bezahlen" ("Pay bill")
    Then  Wait for send TTS in de-DE: {$}LT_de_DE_UserPayBill
    # Bot asks for bill type: "Welche Rechnung möchten Sie bezahlen? Stromrechnung oder Telefonrechnung?"
    #                         "Which bill you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_de_DE_AskBillType
    # C2 chooses "Stromrechnung" ("Electricity bill")
    Then  Wait for send TTS in de-DE: {$}LT_de_DE_UserChooseBillType
    # Bot asks for user account number: "Geben Sie eine 5-stellige Kontonummer ein." ("Enter your 5 digit account number")
    Then  Wait for response: {$}LT_de_DE_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Stromrechnung mit 12345 Konto bezahlen."
    #                                 "Paying electricity bill with 12345 account"
    Then  Wait for response: {$}LT_de_DE_PayBillConfirmation
    # Bot returns to main menu: "Zurück zum Hauptmenü." ("back to main menu")
    Then  Wait for response: {$}LT_de_DE_BackToMainMenu
    # Bot plays main menu again: "Sie können Rechnung bezahlen, Gelder überweisen, oder Anruf beenden sagen."
    #                            "You can say Pay bill, Transfer funds or End call"
    Then  Wait for response: {$}LT_de_DE_MenuPrompt
    # C2 chooses to "Anruf beenden sagen" ("End call")
    Then  Wait for send TTS in de-DE: {$}LT_de_DE_UserEndCall
    # Bot confirms that call will be ended: "Keine Absicht. Ihr Gespräch wird beendet." ("Your conversation will end")
    Then  Wait for response: {$}LT_de_DE_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1223638_LT_nl_NL @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1223638_LT_nl_NL : Uni-Lang - Dutch_nl-NL
    When  Placing 1 call with language set nl-NL to target number {$}LT_nl_NL_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "U kunt Rekening betalen, Overboekingskosten, of Ophangen zeggen.."
    #                      "You can say Pay bill, Transfer funds, or End call."
    Then  Wait for welcome message to be played: {$}LT_nl_NL_MenuPrompt
    # C2 chooses to "Rekening betalen" ("Pay bill")
    Then  Wait for send TTS in nl-NL: {$}LT_nl_NL_UserPayBill
    # Bot asks for bill type: "Welke rekening wilt u betalen? Elektriciteitsrekening of telefoonrekening?"
    #                         "Which bill you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_nl_NL_AskBillType
    # C2 chooses "Elektriciteitsrekening" ("Electricity bill")
    Then  Wait for send TTS in nl-NL: {$}LT_nl_NL_UserChooseBillType
    # Bot asks for user account number: "Voer een 5 cijferig rekeningnummer in." ("Enter your 5 digit account number")
    Then  Wait for response: {$}LT_nl_NL_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "elektriciteitsrekening betalen met rekening 12345."
    #                                 "Paying electricity bill with 12345 account"
    Then  Wait for response: {$}LT_nl_NL_PayBillConfirmation
    # Bot returns to main menu: "Terug naar het hoofdmenu." ("back to main menu")
    Then  Wait for response: {$}LT_nl_NL_BackToMainMenu
    # Bot plays main menu again: "U kunt Rekening betalen, Overboekingskosten, of Ophangen zeggen."
    #                            "You can say Pay bill, Transfer funds or End call"
    Then  Wait for response: {$}LT_nl_NL_MenuPrompt
    # C2 chooses to "Ophangen zeggen." ("End call")
    Then  Wait for send TTS in nl-NL: {$}LT_nl_NL_UserEndCall
    # Bot confirms that call will be ended: "Geen bedoeling. Je gesprek zal eindigen." ("Your conversation will end")
    Then  Wait for response: {$}LT_nl_NL_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1267129_LT_fi_FI @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1267129_LT_fi_FI : Uni-Lang - Finnish_fi-FI
    When  Placing 1 call with language set fi-FI to target number {$}LT_fi_FI_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Voit sanoa Maksaa lasku, Siirtää varoja, tai Lopeta puhelu "
    #                      "You can say Pay bill, Transfer funds, or End call."
    Then  Wait for welcome message to be played: {$}LT_fi_FI_MenuPrompt
    # C2 chooses to "Maksaa lasku" ("Pay bill")
    Then  Wait for send TTS in fi-FI: {$}LT_fi_FI_UserPayBill
    # Bot asks for bill type: "Minkä laskun haluat maksaa? Sähkölasku vai puhelinlasku?"
    #                         "Which bill you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_fi_FI_AskBillType
    # C2 chooses "Sähkölasku" ("Electricity bill")
    Then  Wait for send TTS in fi-FI: {$}LT_fi_FI_UserChooseBillType
    # Bot asks for user account number: "Syötä viisinumeroinen tilinumero" ("Enter the five-digit account number")
    Then  Wait for response: {$}LT_fi_FI_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Sähkölasku maksaminen 12345 tilillä"
    #                                 "Electricity bill payment with 12345 account"
    Then  Wait for response: {$}LT_fi_FI_PayBillConfirmation
    # Bot returns to main menu: "Takaisin päävalikkoon." ("back to main menu")
    Then  Wait for response: {$}LT_fi_FI_BackToMainMenu
    # Bot plays main menu: "Voit sanoa Maksaa lasku, Siirtää varoja, tai Lopeta puhelu "
    #                      "You can say Pay bill, Transfer funds, or End call."
    Then  Wait for response: {$}LT_fi_FI_MenuPrompt
    # C2 chooses to "Lopeta puhelu." ("End the call")
    Then  Wait for send TTS in fi-FI: {$}LT_fi_FI_UserEndCall
    # Bot confirms that call will be ended: "Ei aikomusta. Keskustelusi päättyy." ("Your conversation ends")
    Then  Wait for response: {$}LT_fi_FI_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1267144_LT_sv_SE @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1267144_LT_sv_SE : Uni-Lang - Swedish_sv-SE
    When  Placing 1 call with language set sv-SE to target number {$}LT_sv_SE_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Du kan säga betala räkning, överföra pengar eller avsluta samtal"
    #                      "You can say pay bill, transfer money or end call."
    Then  Wait for welcome message to be played: {$}LT_sv_SE_MenuPrompt
    # C2 chooses to "betala räkning" ("Pay bill")
    Then  Wait for send TTS in sv-SE: {$}LT_sv_SE_UserPayBill
    # Bot asks for bill type: "Vilken räkning vill du betala? Elräkning eller telefonräkning?"
    #                         "Which bill do you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_sv_SE_AskBillType
    # C2 chooses "Elräkning" ("Electricity bill")
    Then  Wait for send TTS in sv-SE: {$}LT_sv_SE_UserChooseBillType
    # Bot asks for user account number: "Ange femsiffrigt kontonummer" ("Enter five-digit account number")
    Then  Wait for response: {$}LT_sv_SE_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Betalar elräkning med 12345 konto"
    #                                 "Paying electricity bill with 12345 account"
    Then  Wait for response: {$}LT_sv_SE_PayBillConfirmation
    # Bot returns to main menu: "Tillbaka till huvudmenyn." ("back to main menu")
    Then  Wait for response: {$}LT_sv_SE_BackToMainMenu
    # Bot plays main menu: "Du kan säga betala räkning, överföra pengar eller avsluta samtal"
    #                      "You can say pay bill, transfer money or end call."
    Then  Wait for response: {$}LT_sv_SE_MenuPrompt
    # C2 chooses to "avsluta samtal." ("End call")
    Then  Wait for send TTS in sv-SE: {$}LT_sv_SE_UserEndCall
    # Bot confirms that call will be ended: "Ingen avsikt. Din konversation kommer att avslutas." ("No intention. Your conversation will end.")
    Then  Wait for response: {$}LT_sv_SE_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1267155_LT_fr_FR @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1267155_LT_fr_FR : Uni-Lang - French_fr-FR
    When  Placing 1 call with language set fr-FR to target number {$}LT_fr_FR_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Vous pouvez dire Payer la facture, Transfert de fonds ou Fin d'appel"
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for welcome message to be played: {$}LT_fr_FR_MenuPrompt
    # C2 chooses to "Payer la facture" ("Pay bill")
    Then  Wait for send TTS in fr-FR: {$}LT_fr_FR_UserPayBill
    # Bot asks for bill type: "Quelle facture souhaitez-vous payer ? Facture d'électricité ou facture de téléphone?"
    #                         "Which bill do you want to pay? Electricity bill or phone bill?"
    Then  Wait for response: {$}LT_fr_FR_AskBillType
    # C2 chooses "Facture d'électricité" ("Electricity bill")
    Then  Wait for send TTS in fr-FR: {$}LT_fr_FR_UserChooseBillType
    # Bot asks for user account number: "Entrez le numéro de compte à cinq chiffres." ("Enter the five-digit account number")
    Then  Wait for response: {$}LT_fr_FR_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Payer la facture électricité avec le compte 12345."
    #                                 "Pay the electricity bill with account 12345."
    Then  Wait for response: {$}LT_fr_FR_PayBillConfirmation
    # Bot returns to main menu: "Retour au menu principal." ("Return to main menu.")
    Then  Wait for response: {$}LT_fr_FR_BackToMainMenu
    # Bot plays main menu: "Vous pouvez dire Payer la facture, Transfert de fonds ou Fin d'appel"
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for response: {$}LT_fr_FR_MenuPrompt
    # C2 chooses to "Fin d'appel." ("End of call")
    Then  Wait for send TTS in fr-FR: {$}LT_fr_FR_UserEndCall
    # Bot confirms that call will be ended: "Aucune intention. Votre conversation prendra fin." ("No intention. Your conversation will end.")
    Then  Wait for response: {$}LT_fr_FR_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1267225_LT_it_IT @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1267225_LT_it_IT : Uni-Lang - Italian_it-IT
    When  Placing 1 call with language set it-IT to target number {$}LT_it_IT_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Puoi dire pagare il conto, trasferimento fondi o fine chiamata."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for welcome message to be played: {$}LT_it_IT_MenuPrompt
    # C2 chooses to "Pagare il conto" ("Pay bill")
    Then  Wait for send TTS in it-IT: {$}LT_it_IT_UserPayBill
    # Bot asks for bill type: "Quale fattura vuoi pagare? Bolletta elettrica o telefono?"
    #                         "Which bill do you want to pay? Electricity bill or phone bill?"
    Then  Wait for response: {$}LT_it_IT_AskBillType
    # C2 chooses "Bolletta elettrica" ("Electricity bill")
    Then  Wait for send TTS in it-IT: {$}LT_it_IT_UserChooseBillType
    # Bot asks for user account number: "Inserisci il numero di conto a cinque cifre." ("Enter your five-digit account number")
    Then  Wait for response: {$}LT_it_IT_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Pagare la bolletta elettrica con conto 12345."
    #                                 "Pay the electricity bill with account 12345."
    Then  Wait for response: {$}LT_it_IT_PayBillConfirmation
    # Bot returns to main menu: "Indietro al menù principale." ("Back to main menu.")
    Then  Wait for response: {$}LT_it_IT_BackToMainMenu
    # Bot plays main menu: "Puoi dire pagare il conto, trasferimento fondi o fine chiamata."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for response: {$}LT_it_IT_MenuPrompt
    # C2 chooses to "Fine chiamata." ("End call")
    Then  Wait for send TTS in it-IT: {$}LT_it_IT_UserEndCall
    # Bot confirms that call will be ended: "Nessun intento. La tua conversazione finirà." ("No intent. Your conversation will end.")
    Then  Wait for response: {$}LT_it_IT_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1275242_LT_cs_CZ @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1275242_LT_cs_CZ : Uni-Lang - Czech_cs-CZ
    When  Placing 1 call with language set cs-CZ to target number {$}LT_cs_CZ_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Můžete říct Zaplatit účet, Převést prostředky nebo Ukončit hovor."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for welcome message to be played: {$}LT_cs_CZ_MenuPrompt
    # C2 chooses to "Zaplatit účet" ("Pay bill")
    Then  Wait for send TTS in cs-CZ: {$}LT_cs_CZ_UserPayBill
    # Bot asks for bill type: "Jaký účet chcete zaplatit? Účet za elektřinu nebo telefon?"
    #                         "What bill do you want to pay? Electricity or phone bill?"
    Then  Wait for response: {$}LT_cs_CZ_AskBillType
    # C2 chooses "Účet za elektřinu" ("Electricity bill")
    Then  Wait for send TTS in cs-CZ: {$}LT_cs_CZ_UserChooseBillType
    # Bot asks for user account number: "Zadejte pětimístné číslo účtu." ("Enter the five-digit account number.")
    Then  Wait for response: {$}LT_cs_CZ_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Placení účet za elektřinu pomocí účtu 12345."
    #                                 "Paying the electricity bill using account 12345."
    Then  Wait for response: {$}LT_cs_CZ_PayBillConfirmation
    # Bot returns to main menu: "Zpět do hlavní nabídky." ("Back to main menu.")
    Then  Wait for response: {$}LT_cs_CZ_BackToMainMenu
    # Bot plays main menu: "Můžete říct Zaplatit účet, Převést prostředky nebo Ukončit hovor."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for response: {$}LT_cs_CZ_MenuPrompt
    # C2 chooses to "Ukončit hovor." ("End call")
    Then  Wait for send TTS in cs-CZ: {$}LT_cs_CZ_UserEndCall
    # Bot confirms that call will be ended: "Žádný záměr. Váš rozhovor skončí." ("No intent. Your conversation will end.")
    Then  Wait for response: {$}LT_cs_CZ_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1275246_LT_da_DK @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1275246_LT_da_DK : Uni-Lang - Danish_da-DK
    When  Placing 1 call with language set da-DK to target number {$}LT_da_DK_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Du kan sige Betal regning, Overfør penge eller Afslut opkald."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for welcome message to be played: {$}LT_da_DK_MenuPrompt
    # C2 chooses to "Betal regning" ("Pay bill")
    Then  Wait for send TTS in da-DK: {$}LT_da_DK_UserPayBill
    # Bot asks for bill type: "Hvilken regning vil du betale? Elregning eller telefonregning?"
    #                         "Which bill will you pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_da_DK_AskBillType
    # C2 chooses "telefonregning" ("telephone bill")
    Then  Wait for send TTS in da-DK: {$}LT_da_DK_UserChooseBillType
    # Bot asks for user account number: "Indtast femcifret kontonummer." ("Enter the five-digit account number.")
    Then  Wait for response: {$}LT_da_DK_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Betaler telefonregning med 12345 konto."
    #                                 "Pays telephone bill with 12345 account."
    Then  Wait for response: {$}LT_da_DK_PayBillConfirmation
    # Bot returns to main menu: "Tilbage til hovedmenuen." ("Back to main menu.")
    Then  Wait for response: {$}LT_da_DK_BackToMainMenu
    # Bot plays main menu: "Du kan sige Betal regning, Overfør penge eller Afslut opkald."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for response: {$}LT_da_DK_MenuPrompt
    # C2 chooses to "Afslut opkald." ("End call")
    Then  Wait for send TTS in da-DK: {$}LT_da_DK_UserEndCall
    # Bot confirms that call will be ended: "Ingen hensigt. Din samtale afsluttes." ("No intention. Your conversation ends.")
    Then  Wait for response: {$}LT_da_DK_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1329884_LT_el_GR @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1329884_LT_el_GR : Uni-Lang - Greek_el-GR
    When  Placing 1 call with language set el-GR to target number {$}LT_el_GR_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Μπορείτε να πείτε Πληρώνω το λογαριασμό, Μεταφορά κεφαλαίων ή Τέλος κλήσης."
    #                      "You can say Pay Bill, Transfer Funds or End Call."
    Then  Wait for welcome message to be played: {$}LT_el_GR_MenuPrompt
    # C2 chooses to "Πληρώνω το λογαριασμό" ("Pay bill")
    Then  Wait for send TTS in el-GR: {$}LT_el_GR_UserPayBill
    # Bot asks for bill type: "Ποιον λογαριασμό θέλετε να πληρώσετε; Λογαριασμός ρεύματος ή τηλεφώνου?"
    #                         "Which bill do you want to pay? Electricity or phone bill?"
    Then  Wait for response: {$}LT_el_GR_AskBillType
    # C2 chooses "Λογαριασμός ρεύματος" ("Electricity bill")
    Then  Wait for send TTS in el-GR: {$}LT_el_GR_UserChooseBillType
    # Bot asks for user account number: "Εισαγάγετε πενταψήφιο αριθμό λογαριασμού." ("Enter five-digit account number.")
    Then  Wait for response: {$}LT_el_GR_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Πληρωμή Λογαριασμός ρεύματος με λογαριασμό 12345."
    #                                 "Paying Electricity bill with account 12345."
    Then  Wait for response: {$}LT_el_GR_PayBillConfirmation
    # Bot returns to main menu: "Επιστροφή στο κύριο μενού." ("Back to main menu.")
    Then  Wait for response: {$}LT_el_GR_BackToMainMenu
    # Bot plays main menu: "Μπορείτε να πείτε Πληρώνω το λογαριασμό, Μεταφορά κεφαλαίων ή Τέλος κλήσης."
    #                      "You can say Pay Bill, Transfer Funds or End Call."
    Then  Wait for response: {$}LT_el_GR_MenuPrompt
    # C2 chooses to "Τέλος κλήσης." ("End call")
    Then  Wait for send TTS in el-GR: {$}LT_el_GR_UserEndCall
    # Bot confirms that call will be ended: "Καμία πρόθεση. Η συνομιλία σας θα τελειώσει." ("No intention. Your conversation will end.")
    Then  Wait for response: {$}LT_el_GR_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1329889_LT_pl_PL @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1329889_LT_pl_PL : Uni-Lang - Polish_pl-PL
    When  Placing 1 call with language set pl-PL to target number {$}LT_pl_PL_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Możesz powiedzieć Zapłać rachunek, Przelej środki lub Zakończ połączenie."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for welcome message to be played: {$}LT_pl_PL_MenuPrompt
    # C2 chooses to "Zapłać rachunek" ("Pay bill")
    Then  Wait for send TTS in pl-PL: {$}LT_pl_PL_UserPayBill
    # Bot asks for bill type: "Który rachunek chcesz zapłacić? Rachunek za energię elektryczną czy rachunek za telefon?"
    #                         "Which bill do you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_pl_PL_AskBillType
    # C2 chooses "Rachunek za energię elektryczną" ("Electricity bill")
    Then  Wait for send TTS in pl-PL: {$}LT_pl_PL_UserChooseBillType
    # Bot asks for user account number: "Wprowadź pięciocyfrowy numer konta." ("Enter your five-digit account number.")
    Then  Wait for response: {$}LT_pl_PL_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Płacenie rachunek za energię elektryczną pomocą konta 12345."
    #                                 "Paying your electricity bill using account 12345."
    Then  Wait for response: {$}LT_pl_PL_PayBillConfirmation
    # Bot returns to main menu: "Powrót do menu głównego." ("Return to the main menu.")
    Then  Wait for response: {$}LT_pl_PL_BackToMainMenu
    # Bot plays main menu: "Możesz powiedzieć Zapłać rachunek, Przelej środki lub Zakończ połączenie."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for response: {$}LT_pl_PL_MenuPrompt
    # C2 chooses to "Zakończ połączenie." ("End call")
    Then  Wait for send TTS in pl-PL: {$}LT_pl_PL_UserEndCall
    # Bot confirms that call will be ended: "Bez zamiaru. Twoja rozmowa dobiegnie końca." ("No intention. Your conversation will end.")
    Then  Wait for response: {$}LT_pl_PL_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1329891_LT_ru_RU @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1329891_LT_ru_RU : Uni-Lang - Russian_ru-RU
    When  Placing 1 call with language set ru-RU to target number {$}LT_ru_RU_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Вы можете сказать «Оплатить счет», «Перевести средства» или «Завершить вызов»"
    #                      "You can say Pay bill, Transfer funds, or End call"
    Then  Wait for welcome message to be played: {$}LT_ru_RU_MenuPrompt
    # C2 chooses to "Оплатить счет" ("Pay bill")
    Then  Wait for send TTS in ru-RU: {$}LT_ru_RU_UserPayBill
    # Bot asks for bill type: "Какой счет вы хотите оплатить? Счет за электричество или телефонный счет?"
    #                         "What bill do you want to pay? Electric bill or phone bill?"
    Then  Wait for response: {$}LT_ru_RU_AskBillType
    # C2 chooses "Счет за электричество" ("Electricity bill")
    Then  Wait for send TTS in ru-RU: {$}LT_ru_RU_UserChooseBillType
    # Bot asks for user account number: "Введите пятизначный номер счета." ("Enter your five-digit account number.")
    Then  Wait for response: {$}LT_ru_RU_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Оплата счет за электричество со счета 12345."
    #                                 "Pay your electricity bill from account 12345."
    Then  Wait for response: {$}LT_ru_RU_PayBillConfirmation
    # Bot returns to main menu: "Вернуться в главное меню." ("Return to main menu.")
    Then  Wait for response: {$}LT_ru_RU_BackToMainMenu
    # Bot plays main menu: "Вы можете сказать «Оплатить счет», «Перевести средства» или «Завершить вызов»"
    #                      "You can say Pay bill, Transfer funds, or End call"
    Then  Wait for response: {$}LT_ru_RU_MenuPrompt
    # C2 chooses to "Завершить вызов." ("End call")
    Then  Wait for send TTS in ru-RU: {$}LT_ru_RU_UserEndCall
    # Bot confirms that call will be ended: "Никакого намерения. Ваш разговор закончится." ("No intention. Your conversation will end.")
    Then  Wait for response: {$}LT_ru_RU_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1331496_LT_pt_BR @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1331496_LT_pt_BR : Uni-Lang - Portuguese_pt-BR
    When  Placing 1 call with language set pt-BR to target number {$}LT_pt_BR_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 2 seconds
    # Bot plays main menu: "Você pode dizer Pagar conta, Transferir fundos ou Encerrar chamada."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for welcome message to be played: {$}LT_pt_BR_MenuPrompt
    # C2 chooses to "Pagar conta" ("Pay bill")
    Then  Wait for send TTS in pt-BR: {$}LT_pt_BR_UserPayBill
    # Bot asks for bill type: "Qual conta você deseja pagar? Conta de eletricidade ou conta de telefone?"
    #                         "Which bill do you want to pay? Electricity bill or telephone bill?"
    Then  Wait for response: {$}LT_pt_BR_AskBillType
    # C2 chooses "Conta de eletricidade" ("Electricity bill")
    Then  Wait for send TTS in pt-BR: {$}LT_pt_BR_UserChooseBillType
    # Bot asks for user account number: "Insira o número da conta de cinco dígitos." ("Enter your five-digit account number.")
    Then  Wait for response: {$}LT_pt_BR_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "Pagando conta de eletricidade com conta 12345."
    #                                 "Paying electricity bill with account 12345."
    Then  Wait for response: {$}LT_pt_BR_PayBillConfirmation
    # Bot returns to main menu: "Voltar ao menu principal." ("Return to main menu.")
    Then  Wait for response: {$}LT_pt_BR_BackToMainMenu
    # Bot plays main menu: "Você pode dizer Pagar conta, Transferir fundos ou Encerrar chamada."
    #                      "You can say Pay Bill, Transfer Funds, or End Call."
    Then  Wait for response: {$}LT_pt_BR_MenuPrompt
    # C2 chooses to "Encerrar chamada." ("End call")
    Then  Wait for send TTS in pt-BR: {$}LT_pt_BR_UserEndCall
    # Bot confirms that call will be ended: "Sem intenção. Sua conversa terminará." ("No intention. Your conversation ends.")
    Then  Wait for response: {$}LT_pt_BR_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1331501_LT_tr_TR @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1331501_LT_tr_TR : Uni-Lang - Turkish_tr-TR
    When  Placing 1 call with language set tr-TR to target number {$}LT_tr_TR_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "Ödeme faturası, Para transferi veya Aramayı Sonlandırma diyebilirsiniz."
    #                      "You can call it Payment invoice, Money transfer or End Call."
    Then  Wait for welcome message to be played: {$}LT_tr_TR_MenuPrompt
    # C2 chooses to "Ödeme faturası" ("payment invoice")
    Then  Wait for send TTS in tr-TR: {$}LT_tr_TR_UserPayBill
    # Bot asks for bill type: "Hangi faturayı ödemek istiyorsunuz? Elektrik faturası mı, telefon faturası mı?"
    #                         "Which bill do you want to pay? Electric bill or phone bill?"
    Then  Wait for response: {$}LT_tr_TR_AskBillType
    # C2 chooses "Elektrik faturası" ("Electricity bill")
    Then  Wait for send TTS in tr-TR: {$}LT_tr_TR_UserChooseBillType
    # Bot asks for user account number: "Beş haneli hesap numarasını girin." ("Enter the five-digit account number.")
    Then  Wait for response: {$}LT_tr_TR_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "12345 hesabıyla elektrik faturasıödeme."
    #                                 "Paying electricity bill with account 12345."
    Then  Wait for response: {$}LT_tr_TR_PayBillConfirmation
    # Bot returns to main menu: "Ana menüye dön." ("Return to main menu.")
    Then  Wait for response: {$}LT_tr_TR_BackToMainMenu
    Then Sleep for 2 seconds
    # Bot plays main menu: "Ödeme faturası, Para transferi veya Aramayı Sonlandırma diyebilirsiniz."
    #                      "You can call it Payment invoice, Money transfer or End Call."
    Then  Wait for response: {$}LT_tr_TR_MenuPrompt
    # C2 chooses to "Aramayı sonlandırın." ("End call")
    Then  Wait for send TTS in tr-TR: {$}LT_tr_TR_UserEndCall
    # Bot confirms that call will be ended: "Niyet yok. Konuşmanız sona erecek." ("No intention. Your conversation ends.")
    Then  Wait for response: {$}LT_tr_TR_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1331506_LT_zh_CN @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1331506_LT_zh_CN : Uni-Lang - Chinese_zh-CN
    When  Placing 1 call with language set zh-CN to target number {$}LT_zh_CN_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "您可以说'支付账单'、'转账资金' 或 '结束通话'."
    #                      "You can say 'pay bill'," 'transfer funds' or 'end call'."
    Then  Wait for welcome message to be played: {$}LT_zh_CN_MenuPrompt
    # C2 chooses to "支付账单" ("pay bill")
    Then  Wait for send TTS in zh-CN: {$}LT_zh_CN_UserPayBill
    # Bot asks for bill type: "您想支付哪个账单？电费账单还是电话账单?"
    #                         "Which bill do you want to pay? Electric bill or phone bill?"
    Then  Wait for response: {$}LT_zh_CN_AskBillType
    # C2 chooses "电费账单" ("Electricity bill")
    Then  Wait for send TTS in zh-CN: {$}LT_zh_CN_UserChooseBillType
    # Bot asks for user account number: "输入五位数的帐号." ("Enter the five-digit account number.")
    Then  Wait for response: {$}LT_zh_CN_AskAccountNumber
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "使用12345账号缴纳电费账单"
    #                                 "Use account number 12345 to pay your electricity bill."
    Then  Wait for response: {$}LT_zh_CN_PayBillConfirmation
#    # Bot returns to main menu: "返回主菜单." ("Return to main menu.")
#    Then  Wait for response: {$}LT_zh_CN_BackToMainMenu
    # Bot plays main menu: "您可以说'支付账单'、'转账资金' 或 '结束通话'."
    #                      "You can say 'pay bill'," 'transfer funds' or 'end call'."
    Then  Wait for response: {$}LT_zh_CN_MenuPrompt
    # C2 chooses to "结束通话." ("End call")
    Then  Wait for send TTS in zh-CN: {$}LT_zh_CN_UserEndCall
    # Bot confirms that call will be ended: "没有意图。你们的谈话将结束." ("No intention. Your conversation will be over.")
    Then  Wait for response: {$}LT_zh_CN_EndCallConfirmation
    Then  Hangup the call in test session

  @testcase_1331512_LT_ja_JP @skip-corp-cd1-mix @skip-tst-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1331512_LT_ja_JP : Uni-Lang - Japanese_ja-JP
    When  Placing 1 call with language set ja-JP to target number {$}LT_ja_JP_Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 3 seconds
    # Bot plays main menu: "請求書を支払う」、「資金を転送する」、または「通話を終了」と言うことができます。"
    #                      "You can say 'pay bill'," 'transfer funds' or 'end call'."
    Then  Wait for welcome message to be played: {$}LT_ja_JP_MenuPrompt
    Then Sleep for 1 seconds
    # C2 chooses to "請求書を支払う" ("pay the bill")
    Then  Wait for send TTS in ja-JP: {$}LT_ja_JP_UserPayBill
    # Bot asks for bill type: "どの請求書を支払いたいですか?電気代とか電話代とか？"
    #                         "Which bill do you want to pay? Electric bill or phone bill?"
    Then  Wait for response: {$}LT_ja_JP_AskBillType
    # C2 chooses "電気代" ("Electricity bill")
    Then  Wait for send TTS in ja-JP: {$}LT_ja_JP_UserChooseBillType
    # Bot asks for user account number: "五桁の口座番号を入力してください." ("Please enter your five-digit account number.")
#    Then  Wait for response: {$}LT_ja_JP_AskAccountNumber
    Then Sleep for 2 seconds
    # C2 DTMF input account number: "one,two,three,four,five"
    Then  Wait for send DTMF: one,two,three,four,five
    # Bot confirms pay bill progress: "12345アカウントで電気代を支払います。"
    #                                 "Pay the electricity bill with your 12345 account."
    Then  Wait for response: {$}LT_ja_JP_PayBillConfirmation
    # Bot returns to main menu: "メインメニューに戻ります。" ("Return to main menu.")
    Then  Wait for response: {$}LT_ja_JP_BackToMainMenu
    # Bot plays main menu: "請求書を支払う」、「資金を転送する」、または「通話を終了」と言うことができます。"
    #                      "You can say 'pay bill'," 'transfer funds' or 'end call'."
    Then  Wait for response: {$}LT_ja_JP_MenuPrompt
    # C2 chooses to "通話を終了" ("End call")
    Then  Wait for send TTS in ja-JP: {$}LT_ja_JP_UserEndCall
    # Bot confirms that call will be ended: "意図はありません。あなたの会話は終わります。" ("No intention. Your conversation ends.")
    Then  Wait for response: {$}LT_ja_JP_EndCallConfirmation
    Then  Hangup the call in test session