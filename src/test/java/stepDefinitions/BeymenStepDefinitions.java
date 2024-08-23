package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

public class BeymenStepDefinitions {

    String productPrice;

    HomePage hp = new HomePage();
    SearchResultsPage sp = new SearchResultsPage();
    ProductDetailsPage pp = new ProductDetailsPage();
    CartPage cp = new CartPage();


    @And("ana sayfanin acildigi kontrol edilir {string}")
    public void anaSayfaninAcildigiKontrolEdilir(String url) {

        ReusableMethods.waitForVisibility(hp.cookiesAccept);
        hp.closeButton.click();
        //ReusableMethods.waitForVisibility(hp.allertCloseButton);

        String expectedUrl = ConfigReader.getProperty(url);
        String actualUrl = Driver.getDriver().getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
    }

    @And("arama kutucuguna excel {int} sutun {int} satirdan urun girilir")
    public void aramaKutucugunaExcelSutunSatirdanUrunGirilir(int colNum, int rowNum) {

        String product = ReusableMethods.getCellData((rowNum - 1), (colNum - 1));
        hp.searchBox.click();
        hp.searchBox1.sendKeys(product);
        System.out.println("product = " + product);
    }

    @And("arama kutucuguna girilen urun silinir")
    public void aramaKutucugunaGirilenUrunSilinir() {

        hp.vazgecButton.click();
    }

    @And("klavye uzerinden enter tusuna basilir")
    public void klavyeUzerindenEnterTusunaBasilir() {

        hp.searchBox1.sendKeys(Keys.ENTER);
    }


    @And("listelenen urunlerden rastgele bir urun secilir")
    public void listelenenUrunlerdenRastgeleBirUrunSecilir() {

        int number = new Random().nextInt(sp.productList.size());
        sp.productList.get(number).click();
    }

    @And("secilen urunun urun bilgisi ve tutar txt dosyasina yazilir")
    public void secilenUrununUrunBilgisiVeTutarTxtDosyasinaYazilir() {

        String productDetails = pp.productDetail.getText();
        productPrice = pp.productPrice.getText();

        String filePath = "src/test/resources/productData.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(productDetails);
            writer.write("\n" + productPrice);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("secilen urun sepete eklenir")
    public void secilenUrunSepeteEklenir() {

        int number = new Random().nextInt(pp.productSizeList.size());
        pp.productSizeList.get(number).click();

        pp.addBasketButton.click();
        pp.myBasket.click();
    }

    @And("urun sayfasindaki fiyat ile sepette yer alan urun fiyatinn dogrulanmasi")
    public void urunSayfasindakiFiyatIleSepetteYerAlanUrunFiyatinnDogrulanmasi() {

        String actualPrice = cp.cartProductPrice.getText();
        actualPrice = actualPrice.substring(0, 5);

        String expectedPrice = productPrice.substring(0, 5);
        assertEquals(expectedPrice, actualPrice);
    }

    @Then("adet arttirilarak urunun {string} oldugunun dogrulanmasi")
    public void adetArttirilarakUrununOldugununDogrulanmasi(String expected) {

        Select select = new Select(cp.productQuantity);
        select.selectByVisibleText("2 adet ");

        String actual = select.getFirstSelectedOption().getText();
        assertEquals(expected, actual);
    }

    @Then("urun sepetten silinerek sepetin bos oldugunun dogrulanmasi {string}")
    public void urunSepettenSilinerekSepetinBosOldugununDogrulanmasi(String expectetTitle) {

        cp.productClearButton.click();
        String actualTitle = cp.emptyCartTitile.getText();

        assertEquals(expectetTitle, actualTitle);
    }
}
