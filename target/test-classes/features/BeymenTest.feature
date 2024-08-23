Feature:Kullanici Beymen Sitesinde Sepete Urun Ekleyip, Cikarabilmeli

  Scenario:Kullanici Beymen Sitesinde Sepete Urun Ekleyip, Cikararir
    Given kullanici URL'e gider "beymenUrl"
    Then ana sayfanin acildigi kontrol edilir "beymenUrl"
    When arama kutucuguna excel 1 sutun 1 satirdan urun girilir
    And arama kutucuguna girilen urun silinir
    And arama kutucuguna excel 1 sutun 2 satirdan urun girilir
    And klavye uzerinden enter tusuna basilir
    And listelenen urunlerden rastgele bir urun secilir
    And secilen urunun urun bilgisi ve tutar txt dosyasina yazilir
    And secilen urun sepete eklenir
    Then urun sayfasindaki fiyat ile sepette yer alan urun fiyatinn dogrulanmasi
    Then adet arttirilarak urunun "2 adet" oldugunun dogrulanmasi
    Then urun sepetten silinerek sepetin bos oldugunun dogrulanmasi "SEPETINIZDE ÜRÜN BULUNMAMAKTADIR"
