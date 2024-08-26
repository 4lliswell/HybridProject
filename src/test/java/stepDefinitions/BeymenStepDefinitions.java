package stepDefinitions;

import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOG = LogManager.getLogger(BeymenStepDefinitions.class);

    String productPrice;
    String product;

    HomePage hp = new HomePage();
    SearchResultsPage sp = new SearchResultsPage();
    ProductDetailsPage pp = new ProductDetailsPage();
    CartPage cp = new CartPage();

    @And("ana sayfanin acildigi kontrol edilir {string}")
    public void anaSayfaninAcildigiKontrolEdilir(String url) {
        ReusableMethods.waitForVisibility(hp.cookiesAccept);
        hp.closeButton.click();

        String expectedUrl = ConfigReader.getProperty(url);
        String actualUrl = Driver.getDriver().getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
        LOG.info("Ana sayfanin acildigi kontrol edilir");
    }

    @And("arama kutucuguna excel {int} sutun {int} satirdan urun girilir")
    public void aramaKutucugunaExcelSutunSatirdanUrunGirilir(int colNum, int rowNum) {
        product = ReusableMethods.getCellData((rowNum - 1), (colNum - 1));
        hp.searchBox.click();
        hp.searchBox1.sendKeys(product);
        String a = hp.searchBox1.getText();
        ReusableMethods.wait(1);
        LOG.info("arama kutucuguna " + product + " girilir");
    }

    @And("arama kutucuguna girilen urun silinir")
    public void aramaKutucugunaGirilenUrunSilinir() {
        hp.vazgecButton.click();
        LOG.info("Arama kutucuguna girilen urun silinir");
    }

    @And("klavye uzerinden enter tusuna basilir")
    public void klavyeUzerindenEnterTusunaBasilir() {
        hp.searchBox1.sendKeys(Keys.ENTER);
        String resultTitle = sp.resultProductTitle.getText();
        assertTrue(resultTitle.contains(product));
        LOG.info("Klavye uzerinden enter tusuna basilir");
    }

    @And("listelenen urunlerden rastgele bir urun secilir")
    public void listelenenUrunlerdenRastgeleBirUrunSecilir() {
        int number = new Random().nextInt(sp.productList.size());
        sp.productList.get(number).click();
        LOG.info("Listelenen urunlerden rastgele bir urun secilir");
    }

    @And("secilen urunun urun bilgisi ve tutar txt dosyasina yazilir")
    public void secilenUrununUrunBilgisiVeTutarTxtDosyasinaYazilir() {

        String productDetails = pp.productDetail.getText();
        productPrice = pp.productPrice.getText();

        String filePath = "src/test/resources/productData.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(productDetails);
            writer.write("\n" + productPrice);

        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.info("Secilen urunun urun bilgisi ve tutar txt dosyasina yazilir");
    }

    @And("secilen urun sepete eklenir")
    public void secilenUrunSepeteEklenir() {

        int number = new Random().nextInt(pp.productSizeList.size());
        pp.productSizeList.get(number).click();
        ReusableMethods.wait(1);
        pp.addBasketButton.click();
        ReusableMethods.waitForVisibility(pp.myBasket);
        LOG.info("Secilen urun sepete eklenir");
    }

    @And("urun sayfasindaki fiyat ile sepette yer alan urun fiyatinin dogrulanmasi")
    public void urunSayfasindakiFiyatIleSepetteYerAlanUrunFiyatininDogrulanmasi() {

        String actualPrice = cp.cartProductPrice.getText().substring(0, 5);
        String expectedPrice = productPrice.substring(0, 5);

        assertEquals(expectedPrice, actualPrice);
        LOG.info("Urun sayfasindaki fiyat ile sepette yer alan urun fiyatinin dogrulanir");
    }

    @Then("adet arttirilarak urunun {string} oldugunun dogrulanmasi")
    public void adetArttirilarakUrununOldugununDogrulanmasi(String expected) {

        Select select = new Select(cp.productQuantity);
        select.selectByVisibleText("2 adet ");

        String actual = select.getFirstSelectedOption().getText();
        assertEquals(expected, actual);
        LOG.info("Sepette urun adedi arttirilarak dogrulama islemi yapilir");
    }

    @Then("urun sepetten silinerek sepetin bos oldugunun dogrulanmasi {string}")
    public void urunSepettenSilinerekSepetinBosOldugununDogrulanmasi(String expectedTitle) {
        cp.productClearButton.click();
        String actualTitle = cp.emptyCartTitile.getText();

        assertEquals(expectedTitle, actualTitle);
        LOG.info("Urun sepetten silinerek sepetin bos oldugunun dogrulanir");
    }
}
