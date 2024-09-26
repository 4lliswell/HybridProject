@Trello
Feature: Kullanici Board Olusturup, Kart Ekleyebilmeli

  Scenario: Kullanici Board Olusturup, Kart Ekler
    Given kullanici "NEWBoard" isminde "boards" olusturur
    Then kullanici "NEWBoard" isminde olusturdugu "boards" dogrular
    And kullanici olusturdugu boarddan list idlerini alir
    When kullanici olusturulan borda "cards" ekler
      | CARD01 |
      | CARD02 |
    Then kullanici olusturdugu kartlari dogrular
      | CARD01 |
      | CARD02 |
    And kullanici kartlardan birini "UpdateCard" olarak gunceller
    Then kullanici guncelledigi "UpdateCard" karti dogrular
    And kullanici olusturulan kartlari siler
    Then kullanici kartlari sildigini dogrular
    Then kullanici olusturulan boardi siler
    Then kullanici boardi sildigini dogrular