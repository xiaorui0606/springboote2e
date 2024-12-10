Feature: PVA Parity Golden bot

  @testcase_1273716 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1273716 : Verify DTMF termination character-PVA
    # Note: preset termination character for frequent flyer ID DTMF input: "#"
    Given  Placing 1 call to target number {$}PVA_Golden from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then  Wait for response: {$}Golden_MenuPromptEnd
    # C2 choose DTMF "one" to book flight
    Then  Wait for send DTMF: one
    # Bot asks DTMF input of "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotAskFrequentFlierID
    # Note: preset termination character for DTMF input: "#"
    # C2 DTMF Input "frequent flyer ID" with "incorrect" termination character "*": "12 12 34 *"
    Then  Wait for send DTMF: one,two,one,two,three,four,asterisk
    Then Sleep for 2 seconds
    # Expect reprompt message for DTMF input of "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotAskFrequentFlierID
    Then Sleep for 2 seconds
    # C2 DTMF Input "frequent flyer ID" again with preset termination character "#": "12 12 34 #"
    Then  Wait for send DTMF: one,two,one,two,three,four,pound
    # Bot confirms the "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotConfirmFrequentFlierID
    Then  Wait for response: {$}Golden_NiceToSeeYouAgain
    Then  Hangup the call in test session

  @testcase_1274008 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1274008 : Verify No speech timeout re-prompt for PVA
    Given  Placing 1 call to target number {$}PVA_Golden from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then  Wait for response: {$}Golden_MenuPromptStart
    Then  Wait for response: {$}Golden_MenuPromptEnd
    Then Sleep for 5 seconds
    # re-prompt 1
    Then  Wait for response: {$}Golden_MenuPromptStart
    Then  Wait for response: {$}Golden_MenuPromptEnd
    Then Sleep for 5 seconds
    # re-prompt 2
    Then  Wait for response: {$}Golden_MenuPromptStart
    Then  Wait for response: {$}Golden_MenuPromptEnd
    Then Sleep for 5 seconds
    # Escalate chat after 2 re-prompt
    Then  Wait for response: {$}Golden_EscalationMessage
    Then  Hangup the call in test session

  @testcase_1275106 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1275106 : DTMF with specified Timeouts - PVA bot
    Given  Placing 1 call to target number {$}PVA_Golden from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then  Wait for response: {$}Golden_MenuPromptEnd
    # C2 wait for the DTMF Terminating timeout for Single digit DTMF (set to 2s)
    Then  Sleep for 4 seconds
    # re-prompt after timeout
    Then  Wait for response: {$}Golden_MenuPromptStart
    Then  Wait for response: {$}Golden_MenuPromptEnd
    # C2 choose DTMF "one" to book flight
    Then  Wait for send DTMF: one
    # Bot asks DTMF input of "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotAskFrequentFlierID
    # Note: preset termination character for DTMF input: "#"
    # DTMF Inter-digit timeout (set to 4s)
    # DTMF Termination timeout (set to 3s)
    # C2 DTMF Input "frequent flyer ID: 121234" without using termination character "#",
    # when the DTMF termination timeout is reached, the inputs will be taken
    # C2 send first batch of DTMF digits: "12"
    Then  Wait for send DTMF: one,two
    Then  Sleep for 2 seconds
    # C2 send second batch of DTMF digits without termination character "#": "1234"
    Then  Wait for send DTMF: one,two,three,four
    # Bot confirms the "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotConfirmFrequentFlierID
    Then  Wait for response: {$}Golden_NiceToSeeYouAgain
    Then  Hangup the call in test session

  @testcase_1280196 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1280196 : [DTMF Only Menu-PVA Bot]Main Menu prompt that accept DTMF only, Voice Input is disabled
    Given  Placing 1 call to target number {$}PVA_Golden2 from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then  Wait for response: {$}Golden_MenuPromptStart
    Then  Wait for response: {$}Golden2_MenuPromptEnd
    Then  Wait for send TTS: {$}Golden_bookFlightVoice
    Then Sleep for 5 seconds
    # C2 voice input will be ignored and expect Menu re-prompt
    Then  Wait for response: {$}Golden2_MenuRePromptWhatToDo
    Then  Wait for response: {$}Golden2_MenuPromptEnd
    Then Sleep for 1 seconds
    # C2 choose DTMF "one" to book flight
    Then  Wait for send DTMF: one
    # Bot asks DTMF input of "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotAskFrequentFlierID
    Then  Hangup the call in test session

  @testcase_1291171 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1291171 : Verify Max Speech Timeout for PVA bot
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}PVA_Golden from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then  Wait for response: {$}Golden_MenuPromptStart
    Then  Wait for response: {$}Golden_MenuPromptEnd
    Then  Set transcript poll interval to 2 seconds
    Then  Wait for send DTMF: four
    Then Wait for response: {$}GoldenReplyToDtmfFour
    Then Wait for response: {$}GoldenFlyToMessage

    # C2 break the answer into multiple chunks of TTS to trigger the Speech recognition timeout
    Then  Wait for send TTS: I want to fly to
    Then Sleep for 2 seconds
    Then  Wait for send TTS: new york
	# Expect Speech recognition timeout 1st time then menu re-prompt
    Then Sleep for 6 seconds
    Then Wait for response: {$}GoldenFlyToMessage

    # C2 retry answer with multiple chunks of TTS to trigger the Speech recognition timeout again
    Then  Wait for send TTS: I want to fly to
    Then Sleep for 2 seconds
    Then  Wait for send TTS: new york
	# Expect Speech recognition timeout 2nd time then menu re-prompt again
    Then Sleep for 6 seconds
    Then Wait for response: {$}GoldenFlyToMessage

	# C2 retry answer with multiple chunks of TTS to trigger the Speech recognition timeout again
    Then  Wait for send TTS: I want to fly to
    Then Sleep for 2 seconds
    Then  Wait for send TTS: new york
	# Expect Speech recognition timeout 3rd time => Escalation
    Then Sleep for 6 seconds
    Then Wait for response: {$}Golden_EscalationMessage
    Then  Hangup the call in test session

  @testcase_1291270 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1291270 : Max Speech Timeout Happy Path-PVA: C2 provide answer before Speech recognition timeout
    Given Set call duration to 200 seconds
    When  Placing 1 call to target number {$}PVA_Golden from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then  Wait for response: {$}Golden_MenuPromptStart
    Then  Wait for response: {$}Golden_MenuPromptEnd
    Then  Set transcript poll interval to 2 seconds
    Then  Wait for send DTMF: four
    Then Wait for response: {$}GoldenReplyToDtmfFour
    Then Wait for response: {$}GoldenFlyToMessage
    Then  Wait for send TTS: New York
    Then Wait for response: {$}GoldenConfirmFlightDestination
    Then  Hangup the call in test session

  @testcase_1302523 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1302523 : [DTMF/Voice Menu, Barge-In off, PVA] DTMF input in middle of Menu Prompt(BNA), DTMF Input Ignored, Menu re-prompt
    Given  Placing 1 call to target number {$}PVA_Golden from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then Set transcript poll interval to 3 seconds
    Then  Wait for response: {$}Golden_MenuPromptEnd
    # C2 choose DTMF "one" to book flight
    Then  Wait for send DTMF: one
    # Bot asks DTMF input of "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotAskFrequentFlierID
    # C2 DTMF Input "frequent flyer ID: 121234#"
    Then  Wait for send DTMF: one,two,one,two,three,four,pound
    Then Sleep for 12 seconds

    # Skip the two verifications steps below since the three responses for
    # "Golden_BotConfirmFrequentFlierID" + "Golden_NiceToSeeYouAgain" + "Golden_BotAskWhereToGo"
    # were returned together in a single transcriptValue
#    Then  Wait for response: {$}Golden_BotConfirmFrequentFlierID
#    Then  Wait for response: {$}Golden_NiceToSeeYouAgain

    # C2 try to barge-in DTMF input of flight destination for "New york"
    Then  Wait for send DTMF: one
    # The Bot should re-prompt the menu asking C2 to choose flight destination
    Then  Wait for response: {$}Golden_BotRePromptWhereToGo
    # C2 DTMF input of flight destination for "New york" again
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}GoldenConfirmFlightDestination
    Then  Hangup the call in test session

  @testcase_1302587 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1302587 : [DTMF/Voice Menu, Barge-In off, PVA] Voice input in middle of Menu Prompt(BNA), Voice Input Ignored, Menu re-prompt
    Given  Placing 1 call to target number {$}PVA_Golden from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then Set transcript poll interval to 3 seconds
    Then  Wait for response: {$}Golden_MenuPromptEnd
    # C2 choose to "book flight"
    Then  Wait for send TTS: {$}Golden_bookFlightVoice
    # Bot asks DTMF input of "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotAskFrequentFlierID
    # C2 DTMF Input "frequent flyer ID: 121234#"
    Then  Wait for send DTMF: one,two,one,two,three,four,pound
    Then Sleep for 12 seconds

    # Skip the two verifications steps below since the three responses for
    # "Golden_BotConfirmFrequentFlierID" + "Golden_NiceToSeeYouAgain" + "Golden_BotAskWhereToGo"
    # were returned together in a single transcriptValue
#    Then  Wait for response: {$}Golden_BotConfirmFrequentFlierID
#    Then  Wait for response: {$}Golden_NiceToSeeYouAgain

    # C2 try to barge-in Voice input of flight destination for "seattle"
    Then  Wait for send TTS: seattle
    # The Bot should re-prompt the menu asking C2 to choose flight destination
    Then  Wait for response: {$}Golden_BotRePromptWhereToGo
    # C2 Voice input of flight destination for "seattle" again
    Then  Wait for send TTS: seattle
    Then  Wait for response: {$}GoldenConfirmFlightDestination
    Then  Hangup the call in test session

  @testcase_1305703 @skip-tst-mix @skip-corp-cd1-mix @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1305703 : [DTMF Only Menu, Barge-In off, PVA] DTMF input in middle of Menu Prompt(BNA), DTMF Input Ignored, Menu re-prompt
    Given  Placing 1 call to target number {$}PVA_Golden2 from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}Golden_welcomeMsg
    Then Set transcript poll interval to 3 seconds
    Then  Wait for response: {$}Golden2_MenuPromptEnd
    # C2 choose DTMF "one" to book flight
    Then  Wait for send DTMF: one
    # Bot asks DTMF input of "frequent flyer ID"
    Then  Wait for response: {$}Golden_BotAskFrequentFlierID
    # C2 DTMF Input "frequent flyer ID: 121234#"
    Then  Wait for send DTMF: one,two,one,two,three,four,pound
    Then Sleep for 12 seconds

    # Skip the two verifications steps below since the three responses for
    # "Golden_BotConfirmFrequentFlierID" + "Golden_NiceToSeeYouAgain" + "Golden_BotAskWhereToGo"
    # were returned together in a single transcriptValue
#    Then  Wait for response: {$}Golden_BotConfirmFrequentFlierID
#    Then  Wait for response: {$}Golden_NiceToSeeYouAgain

    # C2 try to barge-in DTMF input of flight destination for "New york"
    Then  Wait for send DTMF: one
    # The Bot should re-prompt the menu asking C2 to choose flight destination
    Then  Wait for response: {$}Golden_BotRePromptWhereToGo
    # C2 DTMF input of flight destination for "New york" again
    Then  Wait for send DTMF: one
    Then  Wait for response: {$}GoldenConfirmFlightDestination
    Then  Hangup the call in test session