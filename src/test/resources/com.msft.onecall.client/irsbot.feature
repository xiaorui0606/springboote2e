Feature: IRS Bot

  @irsBot @testcase_729566 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729566 : [DTMF Digit Collection, Barge-in on] DTMF input in the middle of menu prompt, prompt play terminated, success
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send TTS: {$}irs_CallerSaysOtherQuestions
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    Then  Wait for send TTS: {$}irs_CallerFiledStatus
    Then  Wait for response: {$}irs_BotAnswersForFiledStatus
    Then  Wait for send TTS: {$}irs_CallerAsksForRepresentative
    Then  Wait for response: {$}irs_BotTransfersToRepresentative
    Then  Hangup the call in test session

  @irsBot @testcase_729567 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva @sanity-mix
  Scenario: testcase_729567 : [DTMF Digit Collection, Barge-in on] No Input, re-prompt
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 10 seconds
    Then  Wait for response: {$}irs_BotRepeatsFirstQuestion
    Then  Wait for send DTMF: two
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    Then  Hangup the call in test session

  @irsBot @testcase_729568 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729568 : [DTMF Digit Collection, Barge-in on] Overflow DTMF buffer, re-prompt
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    And   Set recognition similarity percentage to 75
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send TTS: {$}irs_CallerSaysStatus
    Then  Wait for response: {$}irs_BotRepliesToStatusStart
    Then  Wait for send TTS: {$}irs_CallerSaysSSNOverflow
    Then  Wait for response: {$}irs_BotWrongSSN
    Then  Hangup the call in test session

  @irsBot @testcase_729569 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729569 : [DTMF Digit Collection, Barge-in on] Underflow DTMF buffer, re-prompt
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send TTS: {$}irs_CallerSaysStatus
    Then  Wait for response: {$}irs_BotRepliesToStatusStart
    Then  Wait for send TTS: {$}irs_CallerSaysSSNUnderflow
    Then  Wait for response: {$}irs_BotWrongSSN
    Then  Hangup the call in test session

  @irsBot @testcase_729570 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729570 : [IRS BOT] [DTMF Digit Collection, Barge-in on] Invalid (#), re-prompt
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send DTMF: pound
    Then  Wait for response: {$}irs_BotRetryRePrompt
    Then  Wait for send DTMF: two
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    Then  Hangup the call in test session

  @irsBot @testcase_729571 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729571 : [Hybrid Digit Collection (DTMF and ASR), Barge-in on] DTMF input in the middle of menu prompt
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send TTS: {$}irs_CallerSaysStatus
    Then  Wait for response: {$}irs_BotRepliesToStatusStart
    Then  Wait for send TTS: {$}irs_CallerSaysSSN
    Then  Wait for response: {$}irs_BotConfirmsSSN
    Then  Hangup the call in test session

  @irsBot @testcase_729573 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729573 : [Hybrid Digit Collection (DTMF and ASR), Barge-in on] No Input, re-prompt
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for DTMF prompt: {$}irs_BotNoInputRePromptStart
    Then  Wait for send DTMF: two
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    Then  Sleep for 2 seconds
    Then  Wait for response: {$}irs_BotNoInputRePromptStart
    Then  Wait for send TTS: {$}irs_CallerFiledStatus
    Then  Wait for response: {$}irs_BotAnswersForFiledStatus
    Then  Hangup the call in test session

  @irsBot @testcase_832833 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_832833 : [IRS BOT: Conversation#1-Voice-Input] C2 Calling IRS to check the status of his amended tax return / DTMF Single/Multiple Key inputs
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Wait for send TTS: {$}irs_CallerSaysStatus
    Then  Wait for response: {$}irs_BotRepliesToStatusStart
    Then  Wait for response: {$}irs_BotRepliesToStatusEnd
    Then  Wait for response: {$}irs_BotAsksSSN
    Then  Wait for send TTS: {$}irs_CallerSaysSSN
    Then  Wait for response: {$}irs_BotConfirmsSSN
    Then  Wait for send TTS: {$}irs_CallerConfirmation
    Then  Wait for response: {$}irs_BotAsksZipCode
    Then  Wait for send TTS: {$}irs_CallerSaysZipCode
    Then  Wait for response: {$}irs_BotConfirmsZipCode
    Then  Wait for send TTS: {$}irs_CallerConfirmation
    Then  Wait for response: {$}irs_BotAsksDOB
    Then  Wait for send TTS: {$}irs_CallerSaysDOB
    Then  Wait for response: {$}irs_BotConfirmsDOB
    Then  Wait for send TTS: {$}irs_CallerConfirmation
    Then  Wait for response: {$}irs_BotSharesStatusStart
    Then  Wait for response: {$}irs_BotAsksForRepresentativeIfNoLetter
    Then  Wait for send TTS: {$}irs_CallerSaysNo
    Then  Wait for response: {$}irs_BotSaysAlright
    Then  Hangup the call in test session

  @irsBot @testcase_905501 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva @sanity-mix
  Scenario: testcase_905501 : [IRS BOT: Conversation#1-DTMF-Input] C2 Calling IRS to check the status of his amended tax return with BargeIn DTMF Inputs of Multiple Keys
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then Set transcript poll interval to 1 seconds
    Then  Wait for calls to be connected every 3 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    # C2 choose DTMF "one" to check Status
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotRepliesToStatusStart
    # C2 barge-in DTMF input of Social Security Number: "123 12 1234"
    Then  Wait for send DTMF: one,two,three,one,two,one,two,three,four
    Then  Wait for response: {$}irs_BotConfirmsSSN
    # C2 confirm SSN with DTMF one rather than voice
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotAsksZipCode
    # C2 DTMF input ZipCode: "31102"
    Then  Wait for send DTMF: three,one,one,zero,two
    Then  Wait for response: {$}irs_BotConfirmsZipCode
    # C2 confirm ZipCode with DTMF one rather than voice
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotAsksDOB
    Then Sleep for 1 seconds
    # C2 DTMF input DOB: "06 12 1988"
    Then  Wait for send DTMF: zero,six,one,two,one,nine,eight,eight
    Then  Wait for response: {$}irs_BotConfirmsDOB
    # C2 confirm DOB with DTMF one rather than voice
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotSharesStatusStart
    Then  Wait for response: {$}irs_BotAsksForRepresentativeIfNoLetter
    # C2 answer "No" with DTMF 2
    Then  Wait for send DTMF: two
    Then  Wait for response: {$}irs_BotSaysAlright
    Then  Hangup the call in test session

  @irsBot @IRS-smoke @testcase_832834 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva @sanity-mix
  Scenario: testcase_832834 : [IRS BOT: Conversation#2] C2 Calling IRS for frequently asked questions/Voice inputs
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send TTS: {$}irs_CallerSaysOtherQuestions
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    Then  Wait for send TTS: {$}irs_CallerAnswerWhatInfo
    Then  Wait for response: {$}irs_BotReplyToWhatInfoAnswer
    Then  Wait for response: {$}irs_BotMsgContinue
    Then  Hangup the call in test session

  @irsBot @testcase_832835 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva @sanity-mix
  Scenario: testcase_832835 : [IRS BOT: Conversation#3_1] C2 Calling to speak with IRS Agent
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send TTS: {$}irs_CallerSaysOtherQuestions
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    Then  Wait for send TTS: {$}irs_CallerAsksForAgent
    Then  Wait for response: {$}irs_BotTransfersToRepresentative
    Then  Hangup the call in test session

  @irsBot @testcase_832836 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_832836 : [IRS BOT: Conversation#3_2] C2 Calling IRS for frequently asked questions/Talk to agent
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    Then  Wait for send TTS: {$}irs_CallerSaysOtherQuestions
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    Then  Wait for send TTS: {$}irs_CallerFiledStatus
    Then  Wait for response: {$}irs_BotAnswersForFiledStatus
    Then  Wait for response: {$}irs_BotMsgContinue
    Then  Wait for send TTS: {$}irs_CallerAsksForRepresentative
    Then  Wait for response: {$}irs_BotTransfersToRepresentative
    Then  Wait for response: {$}irs_BotReceivedTransfer
    Then  Hangup the call in test session

  @irsBot @testcase_738306 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_738306 : [IRS BOT] Verify Global DTMF menus don’t conflict with Local DTMF Menus
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then Set transcript poll interval to 2 seconds
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 1 seconds
    # C2 press key '2' (Local DTMF Option for "Other Questions")
    Then  Wait for send DTMF: two
    Then  Wait for response: {$}irs_BotAskingWhatInfo
    # C2 press key '0 ' (Global DTMF Option for "Agent assistance")
    Then  Wait for send DTMF: zero
    Then  Wait for response: {$}irs_BotTransfersToRepresentative
    Then  Hangup the call in test session

  @irsBot @testcase_738320 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_738320 : [IRS BOT] Verify Global DTMF menus don’t conflict with DTMF Digit collection
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    # C2 choose DTMF "one" to check Status
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotRepliesToStatusStart
    # C2 barge-in DTMF input of Social Security Number which contains Global DTMF Option "0": "012 10 1234"
    # Global DTMF Option "0": for "Agent assistance"
    Then  Wait for send DTMF: zero,one,two,one,zero,one,two,three,four
    Then  Wait for response: {$}irs_BotConfirmsSSN_ContainsGlobalDTMFOption
    Then  Hangup the call in test session

  @irsBot @testcase_895383 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_895383 : [IRS BOT] Percolation prompt
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsBot_PercolationPrompt from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksTransferOrDisconnect
    # C2 choose DTMF "one" to transfer the call
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_TransferringTheCall

    # During the first wait (Transfer in progress), the bot plays first PercolationPrompt repeatedly
    Then  Wait for response: {$}irs_PercolationPromptMesg-1
    Then  Wait for response: {$}irs_PercolationPromptMesg-1

    # During second wait (Transfer still in progress), the bot plays second PercolationPrompt repeatedly
    Then Sleep for 2 seconds
    Then  Wait for response: {$}irs_PercolationPromptMesg-2
    Then  Wait for response: {$}irs_PercolationPromptMesg-2
    Then  Wait for response: {$}irs_PercolationPromptMesg-2
    Then  Wait for response: {$}irs_PercolationPromptMesg-2

    # Execute next step after PercolationPrompt
    Then  Wait for response: {$}irs_CallTransferred
    Then  Hangup the call in test session

  @irsBot @testcase_926276 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_926276: [IRS BOT] DTMF with specified Timeouts
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsBot_DTMFTimeout from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    # C2 wait for the Terminating timeout for Single digit DTMF (set to 10s)
    Then  Sleep for 9 seconds
    # expect the re-prompt after timeout
    Then  Wait for welcome message to be played: {$}irs_BotNoInputRePrompt
    # After re-prompt, C2 wait a bit (within the Terminating Timeout:10s), then choose to "check Status" with DTMF "one"
    Then  Sleep for 5 seconds
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotRepliesToStatusEnd
    Then  Wait for response: {$}irs_BotAsksSSN
    # C2 wait for the Terminating timeout for SSN input (set to 10s)
    Then  Sleep for 9 seconds
    # Expect the re-prompt after timeout
    Then  Wait for response: {$}irs_BotNoInputRePromptStart
    Then  Sleep for 1 seconds
#    Then  Wait for send DTMF: one,two,three,one,two,one,two,three,four
    # C2 DTMF Input SSN with Interdigit delays: "123 12 1234"
    # Interdigit timeout (set to 3s)
    Then  Wait for send DTMF: one,two,three
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: one,two
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: one,two,three,four
    Then  Wait for response: {$}irs_BotConfirmsSSN
    Then  Hangup the call in test session

  @irsBot @testcase_895322 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_895322 : Verify DTMF termination character-MIX
    # Note: preset termination character for SSN DTMF input: "#"
    # Note: preset termination character for ZipCode DTMF input: "*"
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsBot_DTMFTimeout from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    # C2 choose DTMF "one" to check Status
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotRepliesToStatusEnd
    # Note: preset termination character for SSN DTMF input: "#"
    Then  Wait for response: {$}irs_BotAsksSSN
    # C2 DTMF Input SSN with "incorrect" termination character "*": "123 12 1234 *"
    Then  Wait for send DTMF: one,two,three,one,two,one,two,three,four,asterisk
    # Expect retry message for SSN input
    Then  Wait for response: {$}irs_BotRetryRePromptStart
    # C2 DTMF Input SSN again with preset termination character "#": "123 12 1234 #"
    Then  Wait for send DTMF: one,two,three,one,two,one,two,three,four,pound
    Then  Wait for response: {$}irs_BotConfirmsSSN
    # C2 confirm SSN with DTMF one rather than voice
    Then Sleep for 1 seconds
    Then  Wait for send DTMF: one
    # Note: preset termination character for ZipCode DTMF input: "*"
    Then  Wait for response: {$}irs_BotAsksZipCode
    # C2 DTMF input ZipCode with "incorrect" termination character "#": "31102 #"
    Then  Wait for send DTMF: three,one,one,zero,two,pound
    # Expect retry message for ZipCode input
    Then  Wait for response: {$}irs_BotRetryRePromptStart
    # C2 DTMF input ZipCode again with preset termination character "*": "31102 *"
    Then  Wait for send DTMF: three,one,one,zero,two,asterisk
    Then  Wait for response: {$}irs_BotConfirmsZipCode
    Then  Hangup the call in test session

  @irsBot @testcase_895358 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-hm-pp-mix @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_895358 : Multilingual bot - English (DTMF)
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsMultilingualBot from source number {$}sourceNumber
    Then Set transcript poll interval to 2 seconds
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgMultiLingual
    # C2 choose DTMF "1" to enter menu of user choice of language
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotAsksPreferredLanguage
    Then  Sleep for 1 seconds
    # C2 chooses "English" with DTMF "1"
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotConfirmEnglishChosen
    # Bot asks for account type: "Savings, deposit or salary?"
    Then  Wait for response: {$}irs_BotAsksAccountType
    Then  Sleep for 1 seconds
    # C2 chooses "Savings" account with DTMF "1"
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotConfirmSavingsChosen
    Then  Wait for response: {$}irs_BotExistingCall
    Then  Hangup the call in test session

  @irsBot @testcase_929164 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-hm-pp-mix @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_929164 : Multilingual bot - Spanish (DTMF)
    Given Set call duration to 200 seconds
    When  Placing 1 call with language set es-US to target number {$}irsMultilingualBot from source number {$}sourceNumber
    Then Set transcript poll interval to 2 seconds
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgMultiLingual
    # C2 choose DTMF "1" to enter menu of user choice of language
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotAsksPreferredLanguage
    Then  Sleep for 1 seconds
    # C2 chooses "Spanish" with DTMF "2"
    Then  Wait for send DTMF: two
    Then  Wait for response: {$}irs_BotConfirmSpanishChosen
    # Bot asks for account type in spanish: "Ahorro, depósito o sueldo?"
    Then  Wait for response: {$}irs_BotAsksAccountTypeSpanish
    Then  Sleep for 1 seconds
    # C2 chooses "Savings" account with DTMF "1"
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotConfirmSavingsChosenSpanish
    Then  Wait for response: {$}irs_BotExistingCallSpanish
    Then  Hangup the call in test session

  @irsBot @testcase_974722 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-hm-pp-mix @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_974722 : Multilingual bot - English (Voice)
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}irsMultilingualBot from source number {$}sourceNumber
    Then Set transcript poll interval to 2 seconds
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgMultiLingual
    # C2 choose DTMF "1" to enter menu of user choice of language
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotAsksPreferredLanguage
    Then  Sleep for 1 seconds
    # C2 chooses "English" with voice input
    Then  Wait for send TTS: {$}English
    Then  Wait for response: {$}irs_BotConfirmEnglishChosen
    # Bot asks for account type: "Savings, deposit or salary?"
    Then  Wait for response: {$}irs_BotAsksAccountType
    Then  Sleep for 1 seconds
    # C2 chooses "Savings" account with voice input
    Then  Wait for send TTS: {$}irs_CallerSaysSavings
    Then  Wait for response: {$}irs_BotConfirmSavingsChosen
    Then  Wait for response: {$}irs_BotExistingCall
    Then  Hangup the call in test session

  @irsBot @testcase_974723 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-hm-pp-mix @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_974723 : Multilingual bot - Spanish (Voice)
    Given Set call duration to 200 seconds
    When  Placing 1 call with language set es-US to target number {$}irsMultilingualBot from source number {$}sourceNumber
    Then Set transcript poll interval to 2 seconds
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgMultiLingual
    # C2 choose DTMF "1" to enter menu of user choice of language
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}irs_BotAsksPreferredLanguage
    Then  Sleep for 1 seconds
    # C2 chooses "Spanish" with voice input
    Then  Wait for send TTS: {$}Spanish
    Then  Wait for response: {$}irs_BotConfirmSpanishChosen
    # Bot asks for account type in spanish: "Ahorro, depósito o sueldo?"
    Then  Wait for response: {$}irs_BotAsksAccountTypeSpanish
    Then  Sleep for 1 seconds
    # C2 chooses "Savings" account with voice input
    Then  Wait for send TTS in es-US: {$}irs_CallerSaysSavingsSpanish
    Then  Wait for response: {$}irs_BotConfirmSavingsChosenSpanish
    Then  Wait for response: {$}irs_BotExistingCallSpanish
    Then  Hangup the call in test session

  @irsBot @testcase_1032702 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_1032702 : Language switching between English and Spanish
    Given Set call duration to 200 seconds
    When  Placing 1 call with language set es-US to target number {$}irsLanguageSwitchingBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    # System:Welcome to Nuance Mix. I'll switch you to Spanish
#    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgLanguageSwitch
    # System: Bienvenido a Nuance Mix. ¿Como puedo ayudarte? (Welcome to Nuance Mix. How can I help you?)
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgSpanish
    # C2: quiero Mi factura (I want to pay my bill)
    Then  Wait for send TTS in es-US: {$}payBillSpanish
    # System: Ok, ¿a quién quieres pagar? (Ok, who do you want to pay?)
    Then  Wait for response: {$}whoToPaySpanish
    # System: Bienvenido a Nuance Mix. ¿Como puedo ayudarte? (Welcome to Nuance Mix. How can I help you?)
    Then  Wait for response: {$}irs_WelcomeMsgSpanish
    # C2: por favor transfiera (please transfer)
    Then  Wait for send TTS in es-US: {$}transferSpanish
    # System: transferida (transferred)
    Then  Wait for response: {$}transferredSpanish
    # System: Welcome to Nuance Mix. How can I help you?
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgEnglish
    # C2: pay bill
    Then  Wait for send TTS: {$}payBillEnglish
    # System: Ok, who do you want to pay?
    Then  Wait for response: {$}whoToPayEnglish
    # System: Bienvenido a Nuance Mix. ¿Como puedo ayudarte? (Welcome to Nuance Mix. How can I help you?)
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgSpanish
    # C2: deberá Pago la cuenta  (I need to pay the bill)
    Then  Wait for send TTS in es-US: {$}payBillSpanish-2
    # System: u llamada va a terminar (your call is going to end)
    Then  Wait for response: {$}yourCallWillBeEndedSpanish
    Then  Hangup the call in test session

  @irsBot @testcase_1040320 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1040320 : Language switching between English and Norwegian
    Given Set call duration to 200 seconds
    When  Placing 1 call with language set nb-NO to target number {$}irsLanguageNorwegianBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    # System: Welcome to Nuance Mix. I'll swith you to Norwegian
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgNorwegianSwitch
    # System: Velkommen til Nuance Mix. Hvordan kan jeg hjelpe deg? (Welcome to Nuance Mix. How can I help you?)
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgNorwegian
    # C2: jeg ønsker å betale telefonregningen med mitt mastercard (I want to pay the phone bill with my mastercard)
    Then  Wait for send TTS in nb-NO: {$}payBillNorwegian
    # System: Ok, hvem vil du betale? (Ok, who do you want to pay?)
    Then  Wait for response: {$}whoToPayNorwegian
    # System: Velkommen til Nuance Mix. Hvordan kan jeg hjelpe deg? (Welcome to Nuance Mix. How can I help you?)
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgNorwegian
    # C2: jeg vil overføre (I want to transfer)
    Then  Wait for send TTS in nb-NO: {$}transferNorwegian
    # System: overført (transferred)
    Then  Wait for response: {$}transferredNorwegian
    # System: Welcome to Nuance Mix. How can I help you?
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgEnglish-2
    # C2: pay bill
    Then  Wait for send TTS in en-US: {$}payBillEnglish
    # System: Ok, who do you want to pay?
    Then  Wait for response: {$}whoToPayEnglish-2
    # System: Velkommen til Nuance Mix. Hvordan kan jeg hjelpe deg? (Welcome to Nuance Mix. How can I help you?)
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsgNorwegian
    # C2: Er det mulig å betale regningen  (Is it possible to pay the bill)
    Then  Wait for send TTS in nb-NO: {$}payBillNorwegian-2
    # System: samtalen din vil ende (your call will end)
    Then  Wait for response: {$}yourCallWillBeEndedNorwegian
    Then  Hangup the call in test session

  @irsBot @testcase_1050589 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-hm-pp-mix @skip-prd-can-mix @skip-prd-eu-mix @skip-gcc-mix
  Scenario: testcase_1050589 : PVA Spanish Bot
    Given Set call duration to 200 seconds
    When  Placing 1 call with language set es-US to target number {$}pva_SpanishBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    # Bot: Hola. Gracias por llamar a INT PVA Spanish PPE.Escuche atentamente las siguientes opciones.
    #     (Hello. Thank you for calling INT PVA Spanish PPE. Listen carefully to the following options)
    Then  Wait for welcome message to be played: {$}pva_Spanish_WelcomeMsg
    # bot: Para conocer el horario de la tienda, diga "horario de la tienda". (For store hours, say "store hours.")
    #      Para saber las ubicaciones de las tiendas, diga "ubicaciones de tiendas".
    #      Para comprar un artículo, diga "comprar artículos".
    #      Para volver a escuchar este menú, presione la tecla almohadilla.
    Then  Wait for DTMF prompt: {$}pva_Spanish_dtmfPromptStart
    # C2: horario de la tienda (C2 asks for store hours)
    Then  Wait for send TTS in es-US: {$}storeHourSpanish
    # Bot:Me encantará ayudarle con el horario de la tienda.
    #     El horario de Sevilla es (Service time is)
    Then  Wait for response: {$}serviceHourSpanish
    # ¿Ha respondido esto a su pregunta? (Has this answered your question?)
    Then  Wait for response: {$}questionAnsweredSpanish
    # C2: Press 1 (for Yes)
    Then  Wait for send DTMF: one
    # Bot: ¡Genial! Califique su experiencia del 1 (peor) al 5 (mejor)
    #      (Great! Rate your experience from 1 (worst) to 5 (best))
    Then  Wait for response: {$}rateYourExperienceSpanish
    # C2: Press 5 (to rate best)
    Then  Wait for send DTMF: five
    # Bot: ¿Puedo ayudarle con algo más? Puede presione 1 o diga Sí, y presione 2 o diga No.
    #      (Can I help you with anything else? You can press 1 or say Yes, and press 2 or say No.)
    Then  Wait for response: {$}anythingElseSpanish
     # C2 Says No
    Then  Wait for send TTS in es-US: {$}noSpanish
    # Bot: Vale, adiós. (Okay, bye.)
    Then  Wait for response: {$}goodbyeSpanish
    Then  Hangup the call in test session

  @irsBot @testcase_1050594 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-hm-pp-mix @skip-prd-can-mix @skip-prd-eu-mix @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1050594 : PVA Norwegian Bot
    Given Set call duration to 200 seconds
    When  Placing 1 call with language set nb-NO to target number {$}pva_NorwegianBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    # Bot: Hallo. Takk for at du ringte INT PVA Norwegian PPE. Lytt nøye til følgende alternativer.
    #     (Hello. Thank you for calling INT PVA Norwegian PPE. Listen carefully to the following options.)
    Then  Wait for welcome message to be played: {$}pva_Norwegian_WelcomeMsg
    # bot: For åpningstider kan du si «åpningstider». (For opening hours, you can say "opening hours".)
    #      Para saber las ubicaciones de las tiendas, diga "ubicaciones de tiendas".
    #      Hvis du vil kjøpe en vare, kan du si «kjøp varer».
    #      Trykk på pundtasten for å høre denne menyen på nytt.
    Then  Wait for DTMF prompt: {$}pva_Norwegian_dtmfPromptStart
    # C2: åpningstider (C2 asks for opening hours)
    Then  Wait for send TTS in nb-NO: {$}storeHourNorwegian
    # Bot:Jeg kan hjelpe deg med åpningstidene. (I can help you with the opening hours.)
    #     Åpningstidene i Sandvika er (The opening hours in Sandvika are)
    Then  Wait for response: {$}serviceHourNorwegian
    # Fikk du svar på spørsmålet ditt? (Did you get an answer to your question?)
    Then  Wait for response: {$}questionAnsweredNorwegian
    # C2: Press 1 (for Yes)
    Then  Wait for send DTMF: one
    # Bot: Flott! Vurder opplevelsen din fra 1 (verst) til 5 (best)
    #      (Great! Rate your experience from 1 (worst) to 5 (best))
    Then  Wait for response: {$}rateYourExperienceNorwegian
    # C2: Press 5 (to rate best)
    Then  Wait for send DTMF: five
    # Bot: Kan jeg hjelpe deg med noe annet? Du kan trykk på 1 eller si Ja, og trykk på 2 eller si Nei.
    #      (Can I help you with anything else? You can press 1 or say Yes, and press 2 or say No.)
    Then  Wait for response: {$}anythingElseNorwegian
    Then Sleep for 1 seconds
     # C2 Says Nei
    Then  Wait for send TTS in nb-NO: {$}noNorwegian
    # Bot: Ok, ha det bra. (Okay, have a good time)
    Then  Wait for response: {$}goodbyeNorwegian
    Then  Hangup the call in test session

  @irsBot @testcase_1050597 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1050597 : Time to Connect Latency
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber with V4 API
#    Given  Placing 1 call to target number {$}irsBot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_WelcomeMsg
    # Private Preview - P95 goal: 500ms | GA - P95 goal: 250ms
    Then  Time to connect latency should be less than 500 ms
    Then  Hangup the call in test session

  @irsBot @testcase_1050599 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1050599 : First Message Latency
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber with V4 API
    Then  Wait for calls to be connected every 5 seconds
    # Private Preview - P95 goal: 3000ms | GA - P95 goal: 1500ms
    Then  Time to hear welcome message: {$}irs_WelcomeMsg should be less than 3000 ms
    Then  Hangup the call in test session

  @irsBot @testcase_1050601 @skip-tst-pva @skip-corp-cd1-pva @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1050601 : Subsequent Message Latency
    Given Placing 1 call to target number {$}irsBot from source number {$}sourceNumber with V4 API
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}irs_BotAsksStatusOrQuestions
    Then  Sleep for 2 seconds
    # Private Preview - P95 goal: 2500ms + default/configured end of speech timeout
    # GA - P95 goal - P95 goal: 1500ms + default/configured end of speech timeout
    Then  Time after tts: {$}irs_CallerSaysOtherQuestions to hear bot response: {$}irs_BotAskingWhatInfo should be less than 2500 ms
    Then  Hangup the call in test session

  @testcase_1266731 @skip-tst-mix @skip-corp-cd1-mix @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1266731 : Dynamic Inline Wordsets from Server Side
    Given Placing 1 call to target number {$}inlineWordsets_Bot from source number {$}sourceNumber with V4 API
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}inlineWordsets_BotAsksWhatToDo
    # C2 says "pay bill"
    Then  Wait for send TTS: {$}inlineWordsets_Paybill
    Then  Wait for response: {$}inlineWordsets_BotAsksCompanyToPay
    # C2 choose to pay to "Visa"
    Then  Wait for send TTS: {$}inlineWordsets_UserChooseCompany
    Then  Wait for response: {$}inlineWordsets_BotConfirmCompany
    Then  Wait for response: {$}inlineWordsets_WordsetsCompleted
    Then  Hangup the call in test session

