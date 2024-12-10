Feature: Black Sabbath

  @blackSabbath @BS-smoke @testcase_682958 @sanity-mix @sanity-pva
  Scenario: testcase_682958: Demo-2-Flight booking and status checking
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 2 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then Sleep for 1 seconds
    Then  Wait for send TTS: {$}bookFlightVoice
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Wait for send TTS: {$}travelToAnswer
    Then  Wait for response: {$}seatAvailableForBooking
    Then  Wait for send TTS: {$}voiceConfirmationForYes
    Then  Wait for response: {$}flightBookingConfirmationMsgComplete
#    Then  Wait for response: {$}backToDtmfMenu
    Then  Sleep for 1 seconds
    Then  Wait for send TTS: {$}flightDetailsVoice
    Then  Wait for response: {$}flightStatusMsg
    Then  Hangup the call in test session

  @blackSabbath @testcase_729535
  Scenario: testcase_729535: Long audio
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send TTS: {$}longPromt
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Hangup the call in test session

  @blackSabbath @testcase_729538
  Scenario: testcase_729538: BS Happy Path
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Set transcript poll interval to 2 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send TTS: {$}bookFlightVoice
    Then  Wait for response: {$}travelToQuestion
    Then  Wait for send TTS: {$}travelToAnswer
    Then  Wait for response: {$}seatAvailableForBooking
    Then  Wait for send TTS: {$}voiceConfirmationForYes
    Then  Wait for response: {$}flightBookingConfirmationMsg
    Then  Hangup the call in test session

  @blackSabbath @BS-smoke @testcase_729540
  Scenario: testcase_729540: [DTMF,BA] DTMF input in the middle of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729541
  Scenario: testcase_729541: [DTMF,BA] DTMF input at the end of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @BS-smoke @testcase_729542
  Scenario: testcase_729542: [DTMF,BA] No Input, re-prompt, DTMF input in middle of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 2 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Set transcript poll interval to 1 seconds
#    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729543 @sanity-mix @sanity-pva
  Scenario: testcase_729543: [DTMF,BA] No Input, re-prompt, DTMF at end of menu prompt, success
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
#    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729544
  Scenario: testcase_729544: [DTMF,BA] No Input, re-prompt, No Input, goodbye
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
#    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for DTMF response: {$}timeoutGoodbyeMsg
    Then  Hangup the call in test session

  @blackSabbath @BS-smoke @testcase_729545
  Scenario: testcase_729545: [DTMF,BA] Out of range DTMF input in middle of menu prompt, prompt play terminated, re-prompt DTMF input in middle of menu prompt, prompt play terminated
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Set transcript poll interval to 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: six
    Then  Sleep for 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729546
  Scenario: testcase_729546: [DTMF,BA] Out of range DTMF input at end of menu prompt, re-prompt DTMF input at end of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: six
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729547
  Scenario: testcase_729547: [DTMF,BA] Out of range DTMF symbol (#) input in middle of menu prompt, prompt play terminated, re-prompt DTMF input in middle of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Set transcript poll interval to 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: pound
    Then  Sleep for 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729548
  Scenario: testcase_729548: [DTMF,BA] Out of range DTMF symbol (#) input at end of menu prompt, re-prompt DTMF, input at end of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: pound
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729549
  Scenario: testcase_729549: [DTMF,BA] [DTMF Menu, Barge-in on] Multiple DTMF inputs in middle of menu prompt, prompt play terminated, First DTMF entered is used First
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Set transcript poll interval to 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: one
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: two
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: three
    Then  Wait for DTMF response: {$}replyToDtmfOneStart
    Then  Hangup the call in test session

  @blackSabbath @testcase_729550
  Scenario: testcase_729550: [DTMF,BA] [DTMF Menu, Barge-in on] Multiple DTMF inputs at end of menu prompt, prompt play terminated, First DTMF entered is used First
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: two
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: three
    Then  Wait for DTMF response: {$}replyToDtmfOneStart
    Then  Hangup the call in test session

  @blackSabbath @testcase_729551
  Scenario: testcase_729551: [DTMF,BA] [DTMF Menu, Barge-in on] Multiple (Many) DTMF inputs in middle of menu prompt, prompt play terminated, First DTMF entered is used first, no DTMF buffer overflow
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Set transcript poll interval to 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: one
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: two
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: three
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOneStart
    Then  Hangup the call in test session

  @blackSabbath @testcase_729552
  Scenario: testcase_729552: [DTMF,BA] [DTMF Menu, Barge-in on] Multiple (Many) DTMF inputs at end of menu prompt, prompt play terminated, First DTMF entered is used first, no DTMF buffer overflow
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Sleep for 2 seconds
    Then  Wait for send DTMF: two
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: three
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOneStart
    Then  Hangup the call in test session

  @blackSabbath @testcase_729553 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729553: [DTMF, BNA] DTMF input in middle of welcome prompt(BNA), DTMF Ignored, DTMF input again at end of DTMF menu prompt
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 1 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    # DTMF input in the middle of welcome prompt (BagreIn Not-Allowed) should be ignored
    Then  Wait for send DTMF: one
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729554 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729554: [DTMF, BNA] DTMF input at the end of menu prompt
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
#    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729555 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729555: [DTMF, BNA] No Input, re-prompt,  DTMF at end of menu prompt
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
#    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729556 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729556: [DTMF, BNA] Out of range DTMF input at end of menu prompt, re-prompt, DTMF input at end of menu prompt
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: five
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @BS-smoke @testcase_729557 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729557: [DTMF Menu, Barge-in off] Out of range DTMF symbol (#) input at end of menu prompt, re-prompt DTMF input at end of menu prompt, success
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: pound
#    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @BS-smoke @testcase_729558 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729558: [DTMF Menu, Barge-in off] Multiple DTMF input at end of menu prompt, First DTMF entered is used first, success
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Sleep for 2 seconds
    Then  Wait for send DTMF: two
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: three
    Then  Wait for DTMF response: {$}replyToDtmfOneStart
    Then  Hangup the call in test session

  @blackSabbath @testcase_729559 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-pva
  Scenario: testcase_729559: [DTMF, BNA] Multiple (Many) DTMF input at end of menu prompt, First DTMF entered is used first, no DTMF buffer overflow.
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Sleep for 2 seconds
    Then  Wait for send DTMF: two
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: three
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: two
    Then  Wait for DTMF response: {$}replyToDtmfOneStart
    Then  Hangup the call in test session

  @blackSabbath @testcase_729560
  Scenario: testcase_729560: [Hybrid Menu (DTMF and ASR), BA] DTMF input in the middle of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Set transcript poll interval to 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729561
  Scenario: testcase_729561: [Hybrid Menu (DTMF and ASR), BA] DTMF input at end of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
#    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729562
  Scenario: testcase_729562: [Hybrid Menu (DTMF and ASR), BA] Barge-in voice in middle of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 2 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
#    Then  Set transcript poll interval to 1 seconds
#    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send TTS: {$}bookFlightVoice
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729563
  Scenario: testcase_729563: [Hybrid Menu (DTMF and ASR), BA] Barge-in voice at end of menu prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send TTS: {$}bookFlightVoice
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session

  @blackSabbath @testcase_729564
  Scenario: testcase_729564: [Hybrid Menu (DTMF and ASR), BA] Barge-in voice invalid menu selection in middle of menu prompt, re-prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 3 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Set transcript poll interval to 1 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send TTS: coffee
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Hangup the call in test session

  @blackSabbath @testcase_729565
  Scenario: testcase_729565: [Hybrid Menu (DTMF and ASR), BA] Barge-in voice invalid menu selection at end of menu prompt, re-prompt
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send TTS: pizza
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Hangup the call in test session

  @blackSabbath @testcase_729577
  Scenario: testcase_729577: [Demo-2-Luggage Lost] C2 reports luggage lost with barge-in voice
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for send TTS: {$}lostLuggageVoice
    Then  Wait for DTMF response: {$}replyToDtmfThree
    Then  Hangup the call in test session

  @blackSabbath @BS-smoke @testcase_738267
  Scenario: testcase_738267: Global DTMF menus happy path
    Given Set call duration to 200 seconds
    When Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Set transcript poll interval to 1 seconds
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Wait for send TTS: {$}travelToAnswer
    Then  Wait for response: {$}seatAvailableForBooking
    Then  Wait for send TTS: {$}voiceConfirmationForYes
    Then  Wait for response: {$}flightBookingConfirmationMsgComplete
    Then  Wait for response: {$}backToDtmfMenu
    Then  Wait for send DTMF: asterisk
    Then  Sleep for 2 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: two
    Then  Wait for DTMF response: {$}replyToDtmfTwo
    Then  Wait for response: {$}backToDtmfMenu
    Then  Sleep for 1 seconds
    Then  Wait for send DTMF: asterisk
    Then  Sleep for 2 seconds
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: three
    Then  Wait for DTMF response: {$}replyToDtmfThree
    Then  Hangup the call in test session

  @blackSabbath @BS-smoke @testcase_738333 @sanity-mix @sanity-pva
  Scenario: testcase_738333: Call Transfer happy path
    Given Placing 1 call to target number {$}demo2Bot from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send DTMF: three
    Then  Wait for DTMF response: {$}replyToDtmfThree
    Then  Wait for DTMF response: {$}botReceivedTransfer
    Then  Hangup the call in test session

  @blackSabbath @testcase_738327_Phone-2 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-pva @skip-hm-pp-pva @skip-prd-can-pva @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_738327_Phone-2: Multiple phone numbers mapped to a single bot
    # This testcase repeats testcase_682958: Demo-2-Flight booking and status checking
    Given Placing 1 call to target number {$}demo2Bot2ndNumber from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptStart
    Then  Wait for send TTS: {$}bookFlightVoice
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Wait for send TTS: {$}travelToAnswer
    Then  Wait for response: {$}seatAvailableForBooking
    Then  Wait for send TTS: {$}voiceConfirmationForYes
    Then  Wait for response: {$}flightBookingConfirmationMsg
    Then  Wait for response: {$}backToDtmfMenu
    Then  Wait for send TTS: {$}flightDetailsVoice
    Then  Wait for response: {$}flightStatusMsg
    Then  Hangup the call in test session

  @blackSabbath @testcase_1280181 @skip-tst-pva @skip-corp-cd1-pva @skip-ppe-mix @skip-ppe-pva @skip-hm-pp-mix @skip-hm-pp-pva @skip-prd-can-mix @skip-prd-can-pva @skip-prd-eu-mix @skip-prd-eu-pva @skip-gcc-mix @skip-gcc-pva
  Scenario: testcase_1280181: [DTMF Only Menu-Mix Bot]Main Menu prompt that accept DTMF only, Voice Input is disabled
    Given Placing 1 call to target number {$}demo2BotBargeInOff from source number {$}sourceNumber
    Then  Wait for calls to be connected every 5 seconds
    Then  Wait for welcome message to be played: {$}welcomeMsg
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then  Wait for send TTS: {$}bookFlightVoice
    Then Set transcript poll interval to 1 seconds
    # The C2 voice input will be ignored and expect Menu re-prompt
    Then  Wait for DTMF prompt: {$}dtmfPromptEnd
    Then Sleep for 1 seconds
    Then  Wait for send DTMF: one
    Then  Wait for DTMF response: {$}replyToDtmfOne
    Then  Hangup the call in test session