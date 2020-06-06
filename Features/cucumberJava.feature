Feature: Test Facebook smoke scenerios

Scenario: Login functionality exists

Given I have open the browser

When I open Facebook website

When I enter "admin" and valid "admin"

Then wait for some minutes

Then search user

Then user should login successfully

